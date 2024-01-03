package com.huozige.lab.container.webview;

import android.app.Activity;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.HACDownloadManager;
import com.huozige.lab.container.utilities.HACDownloadTask;

/**
 * 处理不同MIME，下载或直接展示
 */
public class HACDownloadListener implements DownloadListener {
    HACWebView _webView;

    public HACDownloadListener(HACWebView webView) {
        _webView = webView;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        String oringTitle = this._webView.getTitle();
        ((Activity) this._webView.getContext()).setTitle(R.string.title_pdf_downloading);

        // 执行下载
        HACDownloadManager.getInstance(this._webView.getContext()).startDownloadTask(this._webView.getContext(), url,mimetype, new HACDownloadTask.IHACDownloadHandler() {
            @Override
            public void onSuccess(Uri targetFileUri) {
                Toast.makeText(_webView.getContext(), R.string.ui_message_download_to_dir, Toast.LENGTH_LONG).show();
                ((Activity) _webView.getContext()).setTitle(oringTitle);
            }

            @Override
            public void onError(String fileName, String url) {
                Toast.makeText(_webView.getContext(), R.string.ui_message_download_failed, Toast.LENGTH_LONG).show();
                ((Activity) _webView.getContext()).setTitle(oringTitle);
            }
        });

    }
}
