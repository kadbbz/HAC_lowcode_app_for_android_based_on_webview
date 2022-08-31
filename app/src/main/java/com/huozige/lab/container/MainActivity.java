package com.huozige.lab.container;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    WebView _webView;

    static final int MENU_ID_HOME = 0;
    static final int MENU_ID_REFRESH = 1;
    static final int MENU_ID_HELP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _webView = new WebView(getApplicationContext());
        setContentView(_webView);

        initWebView();
    }

    private void initActionBar() {

    }

    private void initWebView() {

        // 1. WebSettings
        WebSettings settings = _webView.getSettings();

        // 权限
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setGeolocationEnabled(true);

        // 缓存
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());

        // 布局
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // 缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);

        // 2. WebViewClient
        _webView.setWebViewClient(new HzgWebViewClient());

        // 3. WebChromeClient
        _webView.setWebChromeClient(new HzgWebChromeClient(this));

        // 4. WebView

        // 默认设置焦点
        _webView.requestFocusFromTouch();

        // 注册交互对象
        _webView.addJavascriptInterface(new HzgJsBridgeIndex(this), "index");

        // 加载页面
        _webView.loadUrl(getString(R.string.huozige_app));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            // 避免误操作，不允许通过后退键退出应用
            if (_webView.canGoBack()) {
                _webView.goBack();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ID_HOME, 0, getString(R.string.ui_menu_home));
        menu.add(0, MENU_ID_REFRESH, 1, getString(R.string.ui_menu_refresh));
        menu.add(0, MENU_ID_HELP, 2, getString(R.string.ui_menu_help));
        menu.findItem(MENU_ID_REFRESH);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case MENU_ID_HOME:
                initWebView();
                break;
            case MENU_ID_REFRESH:
                _webView.reload();
                break;
            case MENU_ID_HELP:
                _webView.loadUrl(getString(R.string.help_webpage));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}