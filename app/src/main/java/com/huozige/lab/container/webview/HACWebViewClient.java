package com.huozige.lab.container.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import com.elvishew.xlog.XLog;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.platform.AbstractStaticFilesCacheFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理页面的事件，实现异常处理等功能
 */
public class HACWebViewClient extends WebViewClient {

    BaseActivity _context; // 包含有浏览器内核的上下文
    static final String LOG_TAG = "HAC_WebViewClient"; // 日志的标识
    private AbstractStaticFilesCacheFilter cacheFilter;

    private boolean _alreadyInjected = false;


    private final ActivityResultLauncher<Intent> _arc;
    private HttpAuthHandler _authHandler;

    private String _hacJsContent;

    private void loadJsContent() {
        try {
            InputStream in = this._context.getAssets().open("hac_inject_before_loaded.js");
            byte[] buff = new byte[1024];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = in.read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            _hacJsContent = fromFile.toString();
            in.close();
            fromFile.close();

            XLog.v("["+LOG_TAG+ "]hac_inject_before_loaded.js文件读取完毕，长度为" + _hacJsContent.length());

        } catch (IOException e) {
            XLog.e("["+LOG_TAG+ "]读取本地资源hac_inject_before_loaded.js时出错", e);
        }
    }

    /**
     * 简单的构造函数
     *
     * @param activity 上下文
     */
    public HACWebViewClient(BaseActivity activity) {
        _context = activity;

        // 创建读取页面
        _arc = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 获取页面返回的结果
            Intent data = result.getData();
            if (data != null) {
                String usr = data.getStringExtra(HttpAuthActivity.BUNDLE_EXTRA_RESULT_USER);
                String pwd = data.getStringExtra(HttpAuthActivity.BUNDLE_EXTRA_RESULT_PASSWORD);

                XLog.v("["+LOG_TAG+ "]HTTP Auth认证开始：" + usr);
                _authHandler.proceed(usr, pwd);
            } else {
                XLog.v("["+LOG_TAG+ "]用户没有按照页面提示输入用户信息，前页面传回的的用户认证信息为空，即将重试");
            }

        });

        loadJsContent();
    }

    /**
     * 对SSL证书异常做特殊处理，这在活字格的网站上是常见问题。
     * 如果希望强化安全，可以去掉这个重写
     */
    @SuppressLint("WebViewClientOnReceivedSslError")
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

        XLog.e("["+LOG_TAG+ "]SSL验证出错，这不是错误，应用将跳过该请求：" + error.toString());

        // 对SSL错误不予处理
        handler.proceed();
    }

    /**
     * 默认情况下，WebView不会处理页面导航，也就是说点击一个链接，会使用系统浏览器来处理
     * 我们的处理策略是，针对HTTP和HTTPS，在WebView中使用，其他Url schema（如tel:等）则转交给系统
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        XLog.v("["+LOG_TAG+ "]请求地址：" + request.getUrl());

        // 获取请求地址
        String reqUriSchema = request.getUrl().getScheme();

        // 特殊协议，直接跳过
        if(reqUriSchema == null){
            return true;
        }

        // 判断导航的协议，HTTP/HTTPS在当前WebView打开
        if ( reqUriSchema.equalsIgnoreCase("http") || reqUriSchema.equalsIgnoreCase("https")) {
            return false;
        } else {
            // 其他协议使用系统服务打开
            XLog.v("["+LOG_TAG+ "]导航到系统服务：" + request.getUrl());
            return true;
        }
    }

    /**
     * 处理页面加载异常
     * 替代原有逻辑
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        XLog.e("["+LOG_TAG+ "]页面加载出错，Url：" + request.getUrl() ,error);

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
        _alreadyInjected = false;
        XLog.v("["+LOG_TAG+ "]页面加载开始：" + url);
    }

    /**
     * 页面加载后的处理
     * 我们用这个时机来初始化权限，避免过早申请会导致界面“白板”的现象
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        XLog.v("["+LOG_TAG+ "]页面加载完成：" + url);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        XLog.v("["+LOG_TAG+ "]本页面需要HTTP认证，当前没有认证或认证失败，即将弹出认证窗口");
        _authHandler = handler;
        _arc.launch(new Intent(_context, HttpAuthActivity.class));
    }

    /**
     * 处理离线缓存：从离线缓存中加载部分资源，提升响应速度
     *
     * @param view    浏览器
     * @param request 请求信息
     * @return 响应信息
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {

        String path = request.getUrl().getPath();
        String schema = request.getUrl().getScheme();
        if (!_alreadyInjected && path!=null && path.toLowerCase().contains(".js") && _hacJsContent != null && !_hacJsContent.isEmpty()) {
            ((AppCompatActivity)view.getContext()).runOnUiThread(()-> view.evaluateJavascript(_hacJsContent, value -> XLog.v("["+LOG_TAG+ "]JS资源文件已注入页面：" + value)));
            _alreadyInjected = true;
        }

        if (request.getUrl() != null) {

            // 仅处理HTTP和HTTPS
            schema = (schema  == null)? "":schema.trim();
            if (schema.equalsIgnoreCase("http") || schema.equalsIgnoreCase("https")) {
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
                    XLog.e("["+LOG_TAG+ "]读取本地缓存文件资源是出错，Url：" + request.getUrl().toString() , e);
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
