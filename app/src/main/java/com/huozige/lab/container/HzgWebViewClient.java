package com.huozige.lab.container;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 处理页面的事件，实现异常处理等功能
 */
public class HzgWebViewClient extends WebViewClient {

    AppCompatActivity _context; // 包含有浏览器内核的上下文
    static final String LOG_TAG = "HzgWebViewClient"; // 日志的标识

    ConfigManager _cm;

    /**
     * 简单的构造函数
     *
     * @param activity 上下文
     */
    HzgWebViewClient(AppCompatActivity activity) {
        _context = activity;
        _cm = new ConfigManager(_context);
    }

    /**
     * 对SSL证书异常做特殊处理，这在活字格的网站上是常见问题。
     * 如果希望强化安全，可以去掉这个重写
     */
    @SuppressLint("WebViewClientOnReceivedSslError")
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

        Log.e(LOG_TAG, "SSL验证出错，应用将跳过：" + error.toString());

        // 对SSL错误不予处理
        handler.proceed();
    }

    /**
     * 默认情况下，WebView不会处理页面导航，也就是说点击一个链接，会使用系统浏览器来处理
     * 我们的处理策略是，针对HTTP和HTTPS，在WebView中使用，其他Url schema（如tel:等）则转交给系统
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        Log.v(LOG_TAG, "请求地址：" + request.getUrl());

        // 获取请求地址
        Uri reqUri = request.getUrl();

        // 判断导航的协议，HTTP/HTTPS在当前WebView打开
        if (reqUri.getScheme().equalsIgnoreCase("http") || reqUri.getScheme().equalsIgnoreCase("https")) {
            return false;
        } else {
            // 其他协议使用系统服务打开
            Log.v(LOG_TAG, "导航到系统服务：" + reqUri);
            return true;
        }

    }

    /**
     * 处理页面加载异常
     * 替代原有逻辑
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.e(LOG_TAG, "页面加载出错：" + request.getUrl() + " ，错误：" + error.getDescription());

        // 对超时错误做特殊处理
        if (error.getErrorCode() == WebViewClient.ERROR_CONNECT || error.getErrorCode() == WebViewClient.ERROR_TIMEOUT || error.getErrorCode() == WebViewClient.ERROR_HOST_LOOKUP) {
            if (request.getUrl().toString().equalsIgnoreCase(view.getUrl())) {

                // 只有页面出现异常才会提示这个错误，需要应对某些JS加载失败，但系统依然可用的场景
                // 网络异常专属错误页面
                view.loadUrl("file:///android_asset/error/timeout.html");
            }
        } else {

            // 其他异常的提示页面
            view.loadUrl("file:///android_asset/error/error.html");
        }
    }

    @Override
    public void onPageStarted(WebView view, String url,  Bitmap favicon) {
        super.onPageStarted(view,url,favicon);

        Log.v(LOG_TAG, "页面加载开始：" + url);
    }

    /**
     * 页面加载后的处理
     * 我们用这个时机来初始化权限，避免过早申请会导致界面“白板”的现象
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        Log.v(LOG_TAG, "页面加载完成：" + url);
    }

}
