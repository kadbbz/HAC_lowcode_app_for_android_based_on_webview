package com.huozige.lab.container.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.platform.AbstractStaticFilesCacheFilter;

import java.io.IOException;
import java.io.InputStream;

/**
 * 处理页面的事件，实现异常处理等功能
 */
public class HACWebViewClient extends WebViewClient {

    BaseActivity _context; // 包含有浏览器内核的上下文
    static final String LOG_TAG = "HAC_WebViewClient"; // 日志的标识
    private AbstractStaticFilesCacheFilter cacheFilter;


    private final ActivityResultLauncher<Intent> _arc;
    private HttpAuthHandler _authHandler;

    /**
     * 简单的构造函数
     *
     * @param activity 上下文
     */
    public HACWebViewClient(BaseActivity activity) {
        _context = activity;

        // 创建读取页面
        _arc = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.v(LOG_TAG, "Windows域认证开始");

            _authHandler.proceed(this._context.getConfigManager().getUserName(), this._context.getConfigManager().getPassword());
        });
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
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

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

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        Log.v(LOG_TAG,"本页面需要HTTP认证，即将弹出认证窗口");
        _authHandler = handler;
        _arc.launch(new Intent(_context, HttpAuthActivity.class));
    }

    /**
     * 处理离线缓存：从离线缓存中加载部分资源，提升响应速度
     * @param view 浏览器
     * @param request 请求信息
     * @return 响应信息
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {
        if (request != null && request.getUrl() != null) {

            // 仅处理HTTP和HTTPS
            String scheme = request.getUrl().getScheme().trim();
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                try {
                    // 调用缓存处理器
                    AbstractStaticFilesCacheFilter.CacheHint cacheFile = cacheFilter.filter(request.getUrl());

                    // 判断是否有可用的缓存
                    if (cacheFile != null) {

                        // 打开本地缓存文件，获取流
                        InputStream localCache = _context.getAssets().open(cacheFile.LocalFilePath);

                        // 将本地文件返回给浏览器
                        return new WebResourceResponse(cacheFile.MIME, cacheFile.Encoding, localCache);
                    }
                } catch (IOException e) {

                    // 仅记录日志
                    Log.e(LOG_TAG,"Error on loading cache for : " + request.getUrl().toString() +  " Error : "+ e);
                }
            }
        }

        // 不符合条件或没有命中缓存，则采用默认行为，从服务器获取
        return super.shouldInterceptRequest(view, request);
    }

    public void setStaticFilesCacheFilter(AbstractStaticFilesCacheFilter cacheFilter) {
        this.cacheFilter = cacheFilter;
    }
}
