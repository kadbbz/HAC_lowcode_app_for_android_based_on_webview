package com.huozige.lab.container.proxy.support.pdf;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chiclaim.android.downloader.DownloadConstants;
import com.chiclaim.android.downloader.DownloadListener;
import com.chiclaim.android.downloader.DownloadRequest;
import com.github.barteksc.pdfviewer.PDFView;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.PermissionsUtility;

import org.apache.commons.io.FilenameUtils;

/**
 * 下载并预览PDF的页面
 */
public class PDFPreviewActivity extends AppCompatActivity {

    private String _url, _password, _fileName;

    private final String LOG_TAG = "HAC_PDFPreviewActivity";
    private static final int MENU_ID_DIR = 1;
    private static final int MENU_ID_CLOSE = 2;

    public static String EXTRA_KEY_URL = "url";
    public static String EXTRA_KEY_PASSWORD = "password";
    public static String EXTRA_KEY_FILENAME = "fileName";

    PDFView _pdfView;
    ProgressBar _pbDownload;

    /**
     * 启动下载
     */
    private void startDownload() {
        setTitle(R.string.title_pdf_downloading);

        // 准备下载目录吗，确保其存在
        boolean ready= Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        Log.v(LOG_TAG, "Download target dir is ready: "+ ready);

        // 创建下载请求，使用推荐的下载引擎
        DownloadRequest request = new DownloadRequest(this, _url, DownloadConstants.DOWNLOAD_ENGINE_EMBED);

        // 配置下载选项
        request.setIgnoreLocal(true)
                .setNotificationTitle(this.getString(R.string.app_name))
                .setNotificationContent(_fileName)
                .setNeedInstall(false) // 这个组件是为了下载apk使用的，默认会提示安装，这里需要关闭这个选项
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                .setShowNotificationDisableTip(false)
                .setDestinationUri(Uri.parse(FilenameUtils.concat(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath(), _fileName))) // 固定采用用户提供的文件名
                .registerListener(new DownloadListener() {
                    @Override
                    public void onDownloadStart() {
                        Log.v(LOG_TAG, "Download task started: " + _url);
                    }

                    @Override
                    public void onProgressUpdate(int i) {
                        setTitle(getString(R.string.title_pdf_downloading) + " （" + i + "/100）");
                    }

                    @Override
                    public void onDownloadComplete(@NonNull Uri uri) {
                        Log.v(LOG_TAG, "Download task completed: " + _url);

                        // 读取PDF之前需要先申请权限
                        PermissionsUtility.asyncRequirePermissions(PDFPreviewActivity.this, new String[]{
                                Permission.READ_EXTERNAL_STORAGE
                        }, () -> renderPDF(uri));
                    }

                    @Override
                    public void onDownloadFailed(@NonNull Throwable throwable) {
                        Log.e(LOG_TAG, "Download failed, Url: " + _url + " error: " + throwable);

                        // 提示错误消息后关闭预览窗口
                        Toast.makeText(PDFPreviewActivity.this, "下载过程中发生错误：" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        PDFPreviewActivity.this.finish();
                    }
                }).startDownload();
    }

    /**
     * 渲染PDF文件。
     * 该控件不支持直接加载远程的PDF，需要先拉回本地
     *
     * @param pdfFile 已经下载的PDF
     */
    private void renderPDF(Uri pdfFile) {

        // 使用文件名做标题，去掉后缀
        setTitle(FilenameUtils.getBaseName(_fileName));

        try {

            // 初始化PDF预览组件，设置文件、默认打开第一页、渲染标注和表单
            PDFView.Configurator config = _pdfView.fromUri(pdfFile)
                    .defaultPage(0)
                    .enableAnnotationRendering(true);

            // 如果有传入密码，则设置密码
            if (_password != null && !_password.isEmpty()) {
                config.password(_password);
            }

            // 开始展示PDF
            config.load();

            Log.v(LOG_TAG, "PDF file was rendered.");

        } catch (Exception ex) {

            // 提示错误消息后关闭窗口
            Log.e(LOG_TAG, "Error on rendering PDF file: " + ex);
            Toast.makeText(PDFPreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_preview);

        _pdfView = this.findViewById(R.id.pdfView);
        _pbDownload = this.findViewById(R.id.pbDownloading);

    }

    @Override
    protected void onResume() {
        super.onResume();

        _url = this.getIntent().getStringExtra(EXTRA_KEY_URL);
        _password = this.getIntent().getStringExtra(EXTRA_KEY_PASSWORD);
        _fileName = this.getIntent().getStringExtra(EXTRA_KEY_FILENAME);

        if (_fileName == null || _fileName.isEmpty()) {
            _fileName = getString(R.string.title_pdf_preview) + ".pdf";
        }

        startDownload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_ID_CLOSE, MENU_ID_CLOSE, getString(R.string.menu_pdf_close));
        menu.add(0, MENU_ID_DIR, MENU_ID_DIR, getString(R.string.menu_pdf_open_dir));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_DIR:
                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                break;
            case MENU_ID_CLOSE:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}