package com.huozige.lab.container.proxy.support.pdf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.github.barteksc.pdfviewer.PDFView;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.DeviceUtilities;
import com.huozige.lab.container.utilities.HACDownloadManager;
import com.huozige.lab.container.utilities.HACDownloadTask;

import org.apache.commons.io.FilenameUtils;

/**
 * 下载并预览PDF的页面
 */
public class PDFPreviewActivity extends AppCompatActivity {

    private String _url, _password, _fileName, _localFilePath;

    private static final int MENU_ID_CLOSE = 2;
    private static final int MENU_ID_PRINT = 1;

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

        HACDownloadManager.getInstance().startDownloadTask(_url, "application/pdf", new HACDownloadTask.IHACDownloadHandler() {
            @Override
            public void onSuccess(Uri localFileUri) {
                XLog.v("PDF文件下载完成，Url: " + _url + "，保存到：" + localFileUri);

                _localFilePath = DeviceUtilities.uriToPath(localFileUri);

                Toast.makeText(PDFPreviewActivity.this, R.string.ui_message_pdf_downloaded + _fileName, Toast.LENGTH_LONG).show();
                renderPDF(localFileUri);
            }

            @Override
            public void onError(String fileName, String url) {
                XLog.e("PDF文件下载失败，Url：" + url + "，文件名：" + fileName);
                Toast.makeText(PDFPreviewActivity.this, R.string.ui_message_pdf_download_failed + fileName, Toast.LENGTH_LONG).show();
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

            XLog.v("PDF文件渲染完成，文件：" + pdfFile);

        } catch (Exception ex) {

            // 提示错误消息后关闭窗口
            XLog.e("渲染PDF文件时出错，文件: " + pdfFile + " \r\n%s", ex);
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

        menu.add(0, MENU_ID_PRINT, MENU_ID_PRINT, getString(R.string.menu_pdf_print));
        menu.add(0, MENU_ID_CLOSE, MENU_ID_CLOSE, getString(R.string.menu_pdf_close));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ID_CLOSE) {
            this.finish();
        } else if (item.getItemId() == MENU_ID_PRINT) {
            this.print();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 来源：<a href="https://github.com/HarshitaBambure/AndroidPDFPrint">...</a>
     */
    private void print() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            XLog.v("调用系统服务打印文件：" + _localFilePath);
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(this, _localFilePath);
            printManager.print(_fileName, printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception ex) {
            XLog.e("打印PDF文件时出错，文件: " + _localFilePath + " \r\n" + ex);
            Toast.makeText(PDFPreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

}