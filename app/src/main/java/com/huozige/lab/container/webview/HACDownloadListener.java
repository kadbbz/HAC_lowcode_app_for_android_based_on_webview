package com.huozige.lab.container.webview;

import android.app.Activity;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.huozige.lab.container.utilities.HACDownloadManager;

import java.io.File;

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
        ((Activity) this._webView.getContext()).setTitle("文件下载中，请稍等……");

        // 执行下载
        HACDownloadManager.getInstance(this._webView.getContext()).startDownloadTask(this._webView.getContext(), url, new HACDownloadManager.IHACDownloadHandler() {
            @Override
            public void onSuccess(File targetFile) {
                Toast.makeText(_webView.getContext(), "文件下载成功，保存到" + targetFile, Toast.LENGTH_LONG).show();
                ((Activity) _webView.getContext()).setTitle(oringTitle);
            }

            @Override
            public void onError(String fileName, String url) {
                Toast.makeText(_webView.getContext(), "文件下载失败。", Toast.LENGTH_LONG).show();
                ((Activity) _webView.getContext()).setTitle(oringTitle);
            }
        });

    }
}
