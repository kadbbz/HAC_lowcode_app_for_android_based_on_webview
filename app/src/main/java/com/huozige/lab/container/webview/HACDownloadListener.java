package com.huozige.lab.container.webview;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.DownloadListener;

/**
 * 处理不同MIME，下载或直接展示
 */
public class HACDownloadListener implements DownloadListener {
    static final String LOG_TAG = "HAC_DownloadListener"; // 日志的标识
    HACWebView _webView;

    public HACDownloadListener(HACWebView webView) {
        _webView = webView;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        Log.v(LOG_TAG, "当前页面地址的MIME无法在浏览器中打开，即将开始下载：" + url +"（"+ mimetype+"）");

        // 浏览器无法直接渲染的MIME类型，调用系统浏览器进行下载
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        _webView.getContext().startActivity(intent);


    }
}
