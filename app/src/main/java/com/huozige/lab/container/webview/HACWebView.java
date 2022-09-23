package com.huozige.lab.container.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huozige.lab.container.BaseActivity;

/**
 * HAC定制化的WebView控件，内置配置选项和浏览器控制台（Console）操作能力
 */
public class HACWebView extends WebView {

    static final String LOG_TAG = "HACWebView";

    BaseActivity _context;
    ConfigManager _configManager;

    /**
     * 只是为了避免出现Warning，没用上。
     * @param context 上下文
     */
    public HACWebView(Context context){
        super(context);
    }

    /**
     * 初始化配置选项
     * @param context 浏览器所在的页面
     * @param wvc WebViewClient扩展
     * @param wcc WebChromeClient扩展
     * @param cm 配置操作接口
     */
    @SuppressLint("SetJavaScriptEnabled")
    public HACWebView(BaseActivity context, WebViewClient wvc, WebChromeClient wcc, ConfigManager cm){
        super(context);

        _configManager = cm;
        _context = context;

        this.setWebViewClient(wvc);
        this.setWebChromeClient(wcc);

        // 通过WebSettings设置策略
        WebSettings settings = this.getSettings();

        // 权限
        settings.setJavaScriptEnabled(true); // 执行JS
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true); // 支持file://，这个是文件访问的总开关
        settings.setAllowContentAccess(true); // 支持content://
        settings.setAllowFileAccessFromFileURLs(true); // loadUrl()方法和JS文件也能通过URL访问本地文件
        settings.setAllowUniversalAccessFromFileURLs(true); // 加载的本地的JS文件如果需要访问本地文件时，需要用到这个选项
        settings.setGeolocationEnabled(true); // 允许获取地理位置
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);  // 允许混用HTTP和HTTPS

        // 缓存
        settings.setDomStorageEnabled(true); // DOM缓存
        settings.setAppCacheEnabled(true); // 数据缓存（活字格采用的本地库就是这个）
        settings.setAppCachePath(context.getApplicationContext().getCacheDir().getAbsolutePath()); // 数据缓存路径
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 布局
        settings.setUseWideViewPort(true); // 默认全屏

        // 缩放
        settings.setBuiltInZoomControls(false); // 不显示缩放按钮
        settings.setSupportZoom(false); // 不允许缩放

        // 启用Debug（全局设置）
        WebView.setWebContentsDebuggingEnabled(true); // 使用Chrome调试网页，需要开启这个

    }

    /**
     * 根据配置文件，重新加载浏览器内核
     */
    public void refreshWebView() {

        // 根据选项决定是否启用硬件加速
        if (_configManager.GetHA()) {
            this.setLayerType(View.LAYER_TYPE_HARDWARE, null); // 硬件加速，性能更好，有兼容性风险
            Log.v(LOG_TAG, "Init：浏览器采用硬件加速" );
        } else {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 软件加速，兼容性更好
            Log.v(LOG_TAG, "Init：浏览器采用软件加速" );
        }

        String target = _configManager.GetEntry();
        this.loadUrl(target);
    }

    public BaseActivity getActivityContext(){
        return _context;
    }

    /**
     * 页面导航或加载脚本
     * 记录日志
     * @param url URL地址或JS脚本
     */
    @Override
    public void loadUrl(String url){
        super.loadUrl(url);

        Log.v(LOG_TAG,"导航到页面或执行脚本："+url);
    }

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void WriteLogIntoConsole( String logContent) {
        ExecuteJavaScript("console.log('" + removeJSKeyWords(logContent) + "')");

        Log.v(LOG_TAG,"写入浏览器日志："+logContent);
    }

    /**
     * 向浏览器输出错误信息，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void WriteErrorIntoConsole(String logContent) {
        ExecuteJavaScript("console.error('" + removeJSKeyWords(logContent) + "')");

        Log.v(LOG_TAG,"写入浏览器错误日志："+logContent);
    }

    /**
     * 在浏览器执行JavaScript语句
     *
     * @param jsSegment 自定义脚本
     */
    public void ExecuteJavaScript(String jsSegment) {
        ((Activity) this.getContext()).runOnUiThread(() -> this.loadUrl("javascript:"+jsSegment));

        Log.v(LOG_TAG,"在浏览器执行脚本："+jsSegment);
    }


    /**
     * 去掉JavaScript的关键字，替换为HTML编码
     * @param str 原始字符串
     * @return 可以直接拼接到JS的字符串
     */
    private String removeJSKeyWords(String str) {

        int length = str.length();
        int newLength = length;
        boolean someCharacterEscaped = false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case 11:
                    default:
                        newLength--;
                        someCharacterEscaped = true;
                        break;

                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        break;
                }
            else
                switch (c) {
                    case '"':
                        newLength += 5;
                        someCharacterEscaped = true;
                        break;

                    case '&':
                    case '\'':
                        newLength += 4;
                        someCharacterEscaped = true;
                        break;

                    case '<':
                    case '>':
                        newLength += 3;
                        someCharacterEscaped = true;
                        break;
                }
        }
        if (!someCharacterEscaped)
            return str;

        StringBuilder sb = new StringBuilder(newLength);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        sb.append(c);
                        break;
                }
            else
                switch (c) {
                    case '"':
                        sb.append("&quot;");
                        break;

                    case '\'':
                        sb.append("&apos;");
                        break;

                    case '&':
                        sb.append("&amp;");
                        break;

                    case '<':
                        sb.append("&lt;");
                        break;

                    case '>':
                        sb.append("&gt;");
                        break;

                    default:
                        sb.append(c);
                        break;
                }
        }
        return sb.toString();
    }


}
