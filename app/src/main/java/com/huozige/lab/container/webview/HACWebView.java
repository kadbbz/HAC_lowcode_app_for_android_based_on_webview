package com.huozige.lab.container.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.huozige.lab.container.utilities.ConfigManager;

/**
 * HAC定制化的WebView控件，内置配置选项和浏览器控制台（Console）操作能力
 */
public class HACWebView extends WebView {

    private ConfigManager configManager;

    static final String LOG_TAG = "HAC_WebView";

    Context _context;

    ProgressBar _progressBar;

    /**
     * 初始化配置选项
     *
     * @param context 浏览器所在的页面
     */
    @SuppressLint("SetJavaScriptEnabled")
    public HACWebView(Context context) {
        super(context);

        _context = context;

        // 先配置进度条
        _progressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8);

        _progressBar.setLayoutParams(layoutParams);

        addView(_progressBar);

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

        // 兼容性
        settings.setMediaPlaybackRequiresUserGesture(false); // 允许页面加载时自动播放音频

        // 启用Debug（全局设置）
        WebView.setWebContentsDebuggingEnabled(true); // 使用Chrome调试网页，需要开启这个

        // UA
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String versionName = pinfo.versionName;

            String ua = settings.getUserAgentString();//原来获取的UA
            settings.setUserAgentString(ua + " HAC/" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "获取应用版本信息出错：" + e);
            e.printStackTrace();
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) _progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        _progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 设置加载进度
     *
     * @param progressIn100 进度：0-100
     */
    public void setProgress(int progressIn100) {
        if (progressIn100 == 100) {
            _progressBar.setVisibility(GONE);
        } else {
            if (_progressBar.getVisibility() == GONE)
                _progressBar.setVisibility(VISIBLE);
            _progressBar.setProgress(progressIn100);
        }
    }

    /**
     * 根据配置文件，重新加载浏览器内核
     */
    public void refreshWebView() {

        // 根据选项决定是否启用硬件加速
        if (configManager.getHA()) {
            this.setLayerType(View.LAYER_TYPE_HARDWARE, null); // 硬件加速，性能更好，有兼容性风险
            Log.v(LOG_TAG, "Init：浏览器采用硬件加速");
        } else {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 软件加速，兼容性更好
            Log.v(LOG_TAG, "Init：浏览器采用软件加速");
        }

        String target = configManager.getEntry();
        this.loadUrl(target);
    }

    /**
     * 页面导航或加载脚本
     * 记录日志
     *
     * @param url URL地址或JS脚本
     */
    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);

        Log.v(LOG_TAG, "导航到页面或执行脚本：" + url);
    }

    public ConfigManager getConfigManager() throws IllegalStateException {

        if (null == configManager) {
            throw new IllegalStateException ("ConfigManager has not be initialized.");
        }
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
