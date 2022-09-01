package com.huozige.lab.container;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.huozige.lab.container.compatible.HzgJsBridgeIndex;
import com.huozige.lab.container.pda.HzgJsBridgePDA;

public class MainActivity extends AppCompatActivity {

    WebView _webView;

    BaseBridge[] _bridges;

    HzgWebViewClient _webViewClient;

    HzgWebChromeClient _webChromeClient;

    static final int MENU_ID_HOME = 0;
    static final int MENU_ID_REFRESH = 1;
    static final int MENU_ID_HELP = 2;
    static final int MENU_ID_ABOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initWebView();
        initBridgesRegisters();
        setContentView(_webView);
    }

    private void initActionBar() {
        setTitle(getString(R.string.ui_title_loading));
    }

    private void initWebView() {

        //0. 创建
        _webView= new WebView(getApplicationContext());

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
        _webViewClient =  new HzgWebViewClient(this);
        _webView.setWebViewClient(_webViewClient);

        // 3. WebChromeClient
        _webChromeClient = new HzgWebChromeClient(this);
        _webChromeClient.RegistryLaunchersOnCreated();
        _webView.setWebChromeClient(_webChromeClient);

        // 4. WebView
        // 默认设置焦点
        _webView.requestFocusFromTouch();

        // 加载页面
        _webView.loadUrl(getString(R.string.huozige_app));

    }

    @SuppressLint("JavascriptInterface")
    private void initBridgesRegisters(){

        _bridges = new BaseBridge[]{
                new HzgJsBridgeIndex(this,_webView),
                new HzgJsBridgePDA(this,_webView)
        };

        for (BaseBridge br:_bridges
             ) {
            br.RegisterOnActivityCreated();
            _webView.addJavascriptInterface(br,br.Name);
        }

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
        menu.add(0, MENU_ID_ABOUT, 3, getString(R.string.ui_menu_about));
        menu.findItem(MENU_ID_REFRESH);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_HOME:
                initWebView();
                break;
            case MENU_ID_REFRESH:
                _webView.reload();
                break;
            case MENU_ID_HELP:
                _webView.loadUrl(getString(R.string.help_webpage));
                break;
            case MENU_ID_ABOUT:
                _webView.loadUrl("file:///android_asset/about/about.html");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(_webChromeClient.ProcessActivityResult(requestCode,resultCode,data)){
            return;
        }

        for (BaseBridge br:
           _bridges  ) {
            if(br.ProcessActivityResult(requestCode,resultCode,data)){
                break;
            }
        }
    }
}