package com.huozige.lab.container.proxy.support.pdf;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.HACDownloadManager;
import com.huozige.lab.container.utilities.HACDownloadTask;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * 下载并预览PDF的页面
 */
public class PDFPreviewActivity extends AppCompatActivity {

    private String _url, _password, _fileName;

    private final String LOG_TAG = "HAC_PDFPreviewActivity";
    private static final int MENU_ID_CLOSE = 2;

    public static String EXTRA_KEY_URL = "url";
    public static String EXTRA_KEY_PASSWORD = "password";
    public static String EXTRA_KEY_FILENAME = "fileName";

    PDFView _pdfView;
    ProgressBar _pbDownload;

    Uri _uri;

    /**
     * 启动下载
     */
    private void startDownload() {
        setTitle(R.string.title_pdf_downloading);

        HACDownloadManager.getInstance(this).startDownloadTask(this, _url, "application/pdf",new HACDownloadTask.IHACDownloadHandler() {
            @Override
            public void onSuccess(File targetFile) {
                Log.v(LOG_TAG, "Download task completed: " + _url +" to: "+targetFile);
                Toast.makeText(PDFPreviewActivity.this,"PDF文件已成功下载，即将打开："+targetFile, Toast.LENGTH_LONG).show();
                _uri = Uri.fromFile(targetFile);
                renderPDF(_uri);
            }

            @Override
            public void onError(String fileName, String url) {
                Log.v(LOG_TAG, "Download task failed: " + url +" name: "+ fileName);
                Toast.makeText(PDFPreviewActivity.this,"PDF文件无法下载，请稍后重试："+fileName, Toast.LENGTH_LONG).show();
                PDFPreviewActivity.this.finish();
            }
        });
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ID_CLOSE) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}