package com.huozige.lab.container;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.platform.AbstractStaticFilesCacheFilter;
import com.huozige.lab.container.platform.AbstractWebInterop;
import com.huozige.lab.container.platform.hzg.HZGCacheFilter;
import com.huozige.lab.container.platform.hzg.HZGWebInterop;
import com.huozige.lab.container.proxy.AbstractProxy;
import com.huozige.lab.container.proxy.ProxyRegister;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.LifecycleUtility;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.huozige.lab.container.webview.HACDownloadListener;
import com.huozige.lab.container.webview.HACWebChromeClient;
import com.huozige.lab.container.webview.HACWebView;
import com.huozige.lab.container.webview.HACWebViewClient;

/**
 * 主Activity，主要负责加载浏览器内核
 * 也需要作为其他功能的默认上下文
 */
public class MainActivity extends BaseActivity {

    // 构造Web平台相关的策略，用于组合
    AbstractWebInterop _webInterop = new HZGWebInterop();
    AbstractStaticFilesCacheFilter _cacheFilter = new HZGCacheFilter();

    HACWebView _webView; // 浏览器内核

    HACWebViewClient _webViewClient; // 页面事件处理器

    HACWebChromeClient _webChromeClient; // 浏览器事件处理器

    HACDownloadListener _downloadListener; // 下载分流处理器

    ActivityResultLauncher<Intent> _arc4QuickConfig; // 用来弹出配置页面。

    static final int MENU_ID_HOME = 0;
    static final int MENU_ID_REFRESH = 1;
    static final int MENU_ID_SETTINGS = 2;
    static final int MENU_ID_HELP = 3;
    static final int MENU_ID_ABOUT = 4;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 0. 申请权限
        PermissionsUtility.asyncRequirePermissions(this,new String[]{
                Permission.WRITE_EXTERNAL_STORAGE
        },()->XLog.v("日志记录的权限申请成功。"));

        // 1. 设置页面标题
        setTitle(getString(R.string.app_name));

        // 2. 创建浏览器
        try{

            _webView = new HACWebView(this);

            // 4. 创建并注册WebViewClient，处理页面事件
            _webViewClient = new HACWebViewClient(this);
            _webViewClient.setStaticFilesCacheFilter(_cacheFilter);
            _webView.setWebViewClient(_webViewClient);

            // 5. 创建并注册WebChromeClient，处理浏览器事件
            _webChromeClient = new HACWebChromeClient(this);
            _webView.setWebChromeClient(_webChromeClient);

            // 6. 注册下载分流处理器
            _downloadListener = new HACDownloadListener(_webView);
            _webView.setDownloadListener(_downloadListener);

            // 7. 将准备就绪的浏览器加载到页面
            setContentView(_webView);

            // 8. 使用WebView初始化WebInterop，处理和HTML的交互
            _webInterop.setWebView(_webView);

            // 9. 创建和初始化JS代理
            for (AbstractProxy br : ProxyRegister.getInstance().getAllProxies()
            ) {
                br.setConfigManager(ConfigManager.getInstance()); // 配置接口
                br.setInterop(_webInterop); // WebInterop
                _webView.addJavascriptInterface(br,br.getName()); // 注册到浏览器
                br.onActivityCreated(MainActivity.this); // 初始化以当前Activity为上下文的启动器，这一操作仅允许在当前阶段调用，否则会出错
            }

            // 10. 初始化启动器
            _webChromeClient.registryLaunchersOnCreated(); // ChromeClient的初始化
            _arc4QuickConfig = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> LifecycleUtility.restart(this)); // 打开设置页面，返回后重启应用

            // 11. 加载页面
            _webView.refreshWebView();
        }catch (Exception ex){

            XLog.e("WebView组件初始化失败。\r\n%s",ex);

            String message = "应用初始化失败，这通常是操作系统和运行环境不兼容导致的，请按照下方提示操作。如果遇到困难，可拍摄本界面或截屏后，与技术支持人员联系。";
            message+="\r\n\n";
            message+=ex.getMessage();

            Intent intent = new Intent(this, ShowErrorActivity.class);
            intent.putExtra(ShowErrorActivity.EXTRA_KEY_MESSAGE, message);

            this.startActivity(intent);
        }


    }

    /**
     * 销毁前的操作：取消监听器
     */
    @Override
    public void onDestroy() {

        // 销毁浏览器
        if(_webView!=null){
            _webView.removeAllViews();
            _webView.destroy();
        }

        super.onDestroy();
    }

    /**
     * 处理恢复时的特殊任务，如强制刷新
     */
    @Override
    public void onResume() {
        super.onResume();

        // 如果没有设置入口，视同”未配置“
        if (ConfigManager.getInstance().getEntry().length() == 0) {
            // 跳转到设置页面
            _arc4QuickConfig.launch(new Intent(this, QuickConfigActivity.class)); // 弹出设置页面
        }else{

            // 判断是否需要自动跳转到特定页面
            Intent intent = getIntent();

            // Action：com.huozige.lab.container.navigate
            // Extra：url
            if(intent!=null && intent.getAction()!=null && intent.getAction().equalsIgnoreCase("com.huozige.lab.container.navigate")){
                String url = intent.getStringExtra("url");

                if(url!=null){
                    Uri u = Uri.parse(url);

                    if(u!=null){

                        XLog.v("收到带跳转的Intent请求，URL："+url);
                        this._webView.loadUrl(url);
                    }
                }
            }

        }


    }

    /**
     * 处理“后退”按键，避免误触导致退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 仅处理后退键
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            // 避免误操作，不允许通过后退键退出应用
            if (_webView.canGoBack()) {
                _webView.goBack();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 处理配置变更的事件，这里仅做日志记录
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        XLog.v("HAC_BaseActivity","Config changed: "+ newConfig);

    }

    /**
     * 在ActionBar上创建菜单，提供回到首页、刷新页面两个常用功能
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // 首页
        menu.add(0, MENU_ID_HOME, MENU_ID_HOME, getString(R.string.ui_menu_home));

        // 刷新
        menu.add(0, MENU_ID_REFRESH, MENU_ID_REFRESH, getString(R.string.ui_menu_refresh));

        // 设置
        if (ConfigManager.getInstance().getSettingMenuVisible()) {
            menu.add(0, MENU_ID_SETTINGS, MENU_ID_SETTINGS, getString(R.string.ui_menu_settings));
        }

        // 帮助
        if (ConfigManager.getInstance().getHelpUrl().length() > 0) {
            menu.add(0, MENU_ID_HELP, MENU_ID_HELP, getString(R.string.ui_menu_help));
        }

        // 关于
        if (ConfigManager.getInstance().getAboutUrl().length() > 0) {
            menu.add(0, MENU_ID_ABOUT, MENU_ID_ABOUT, getString(R.string.ui_menu_about));
        }

        // 你可以在这里创建新的菜单

        return true;
    }

    /**
     * 处理菜单的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_HOME:
                XLog.v("点击菜单【首页】");
                _webView.refreshWebView(); // 页面初始化
                break;
            case MENU_ID_REFRESH:
                XLog.v("点击菜单【刷新】");
                _webView.reload(); // 仅刷新
                break;
            case MENU_ID_SETTINGS:
                _arc4QuickConfig.launch(new Intent(this, SettingActivity.class)); // 弹出设置页面
                break;
            case MENU_ID_HELP:
                XLog.v("点击菜单【帮助】");
                _webView.loadUrl(ConfigManager.getInstance().getHelpUrl());
                break;
            case MENU_ID_ABOUT:
                XLog.v("点击菜单【关于】");
                _webView.loadUrl(ConfigManager.getInstance().getAboutUrl());
                break;
            // 你可以在这里处理新创建菜单的点击事件
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 发起Activity调用请求后，处理收到的响应
     * 主要工作是将响应数据分发到以当前以Activity作为上下文的其他对象中，顺序为：
     * 1. 浏览器内核事件
     * 2. JS桥
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 优先分发给浏览器内核事件
        if (_webChromeClient.processActivityResult(requestCode, resultCode, data)) {
            return;
        }

        // 依次分发给所有JS桥
        for (AbstractProxy br :
                ProxyRegister.getInstance().getAllProxies()) {
            if (br.processActivityResult(requestCode, resultCode, data)) {
                break;
            }
        }
    }
}