package com.huozige.lab.container;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class HzgWebViewClient extends WebViewClient {

    static final String LOG_TAG = "HzgWebViewClient";

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

        // 对SSL错误不予处理
        handler.proceed();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view,  WebResourceRequest request) {

        Uri reqUri = request.getUrl();

        // 判断导航的协议，HTTP/HTTPS在当前WebView打开
        if(reqUri.getScheme().equalsIgnoreCase("http")  || reqUri.getScheme().equalsIgnoreCase("https"))
        {
            return false;
        }
        else
        {
            // 其他协议使用系统服务打开
            Log.v(LOG_TAG,"导航到系统服务：" + reqUri);
            return true;
        }

    }
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.e(LOG_TAG,error.toString());
        view.loadUrl("file:///android_asset/error/error.html");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }


}
