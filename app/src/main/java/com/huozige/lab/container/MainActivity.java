package com.huozige.lab.container;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.platform.AbstractStaticFilesCacheFilter;
import com.huozige.lab.container.platform.AbstractWebInterop;
import com.huozige.lab.container.platform.hzg.HZGCacheFilter;
import com.huozige.lab.container.platform.hzg.HZGWebInterop;
import com.huozige.lab.container.proxy.AbstractProxy;
import com.huozige.lab.container.proxy.ProxyRegister;
import com.huozige.lab.container.utilities.ColorUtility;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.DeviceUtility;
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

    public static final String INTENT_NAVIGATE_TO = "com.huozige.lab.container.navigate";
    public static final String EXTRA_URL = "url";

    public static final String ON_KRY_DOWN_ACTION_STRING = "On_Key_Down_";

    // 构造Web平台相关的策略，用于组合
    AbstractWebInterop _webInterop = new HZGWebInterop();
    AbstractStaticFilesCacheFilter _cacheFilter = new HZGCacheFilter();

    HACWebView _webView; // 浏览器内核

    HACWebViewClient _webViewClient; // 页面事件处理器

    HACWebChromeClient _webChromeClient; // 浏览器事件处理器

    HACDownloadListener _downloadListener; // 下载分流处理器

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
        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                Permission.WRITE_EXTERNAL_STORAGE
        }, () -> XLog.v("日志记录的权限申请成功。"));

        // 1. 设置页面标题
        setTitle(getString(R.string.app_name));

        // 2. 检查是否已经完成配置
        if (!ConfigManager.getInstance().isAppReady()) {
            XLog.v("APP尚未完成配置，即将跳转到初始化界面");
            // 跳转到设置页面
            startActivity(new Intent(this, QuickConfigActivity.class));
        } else {

            try {
                // 3. 创建浏览器并执行初始化
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
                    br.setInterop(_webInterop); // WebInterop
                    _webView.addJavascriptInterface(br, br.getName()); // 注册到浏览器
                    br.onActivityCreated(MainActivity.this); // 初始化以当前Activity为上下文的启动器，这一操作仅允许在当前阶段调用，否则会出错
                }

                // 10. 初始化ChromeClient的启动器
                _webChromeClient.registryLaunchersOnCreated(); // ChromeClient的初始化

                XLog.v("WebView初始化完成");

                // 11. 加载页面
                _webView.navigateToDefaultPage();
            } catch (Exception ex) {

                // 统一处理各种异常
                XLog.e("WebView组件初始化失败。\r\n%s", ex);

                String message = "应用初始化失败，这通常是操作系统和运行环境不兼容导致的，请按照下方提示操作。如果遇到困难，可拍摄本界面或截屏后，与技术支持人员联系。";
                message += "\r\n\n";
                message += ex.getMessage();

                Intent intent = new Intent(this, ShowErrorActivity.class);
                intent.putExtra(ShowErrorActivity.EXTRA_KEY_MESSAGE, message);

                this.startActivity(intent);
            }
        }

    }

    /**
     * 销毁前的操作：取消监听器
     */
    @Override
    public void onDestroy() {

        // 销毁浏览器
        if (_webView != null) {
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

        // 判断是否需要自动跳转到特定页面
        Intent intent = getIntent();

        // Action：com.huozige.lab.container.navigate
        // Extra：url
        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(INTENT_NAVIGATE_TO)) {
            String url = intent.getStringExtra(EXTRA_URL);

            if (url != null) {
                Uri u = Uri.parse(url);

                if (u != null) {

                    XLog.v("收到带跳转的Intent请求，URL：" + url);
                    this._webView.loadUrl(url);
                }
            }
        }

    }

    /**
     * 判断是否监听物理按键，并发送自定义广播
     * 处理“后退”按键，避免误触导致退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //当前监听按键
        String currentListen = ConfigManager.getInstance().getOnKeyDownListen();

        //判断是否包含当前按键
        if (currentListen.contains("," + keyCode + "-")) {

            if (currentListen.contains("," + keyCode + "-T")) {
                _webView.getContext().sendBroadcast(new Intent(ON_KRY_DOWN_ACTION_STRING + keyCode + "-T"));
                return true;
            }
            _webView.getContext().sendBroadcast(new Intent(ON_KRY_DOWN_ACTION_STRING + keyCode + "-F"));
        }

        // 仅处理后退键
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if (DeviceUtility.isOfflineMode()) {
                // 离线模式不允许执行后退操作
                Toast.makeText(this, "应用处于离线模式，无法执行页面导航。", Toast.LENGTH_LONG).show();
            } else {
                // 浏览器到最后一页时，不允许通过后退键退出应用
                if (_webView.canGoBack()) {
                    _webView.goBack();
                }
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 处理配置变更的事件，这里仅做日志记录
     *
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        XLog.v("HAC_BaseActivity", "Config changed: " + newConfig);

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
        if (!ConfigManager.getInstance().getHelpUrl().isEmpty()) {
            menu.add(0, MENU_ID_HELP, MENU_ID_HELP, getString(R.string.ui_menu_help));
        }

        // 关于
        if (!ConfigManager.getInstance().getAboutUrl().isEmpty()) {
            menu.add(0, MENU_ID_ABOUT, MENU_ID_ABOUT, getString(R.string.ui_menu_about));
        }

        // 你可以在这里创建新的菜单

        // 设置ActionBar的图标颜色，避免文字和背景颜色过于接近
        Toolbar aBar = findViewById(androidx.appcompat.R.id.action_bar);

        if (aBar != null) {
            Drawable oriIcon = aBar.getOverflowIcon();
            // 深色采用黑色文字，浅色用白色文字
            if (ColorUtility.getGrayScale(ConfigManager.getInstance().getTCD()) > ColorUtility.GRAYSCALE_THRESHOLD_BRIGHT_COLOR) {
                if (oriIcon != null) {
                    aBar.setOverflowIcon(ColorUtility.resetImageTintColorTo(oriIcon, Color.BLACK));
                }
            } else {
                if (oriIcon != null) {
                    aBar.setOverflowIcon(ColorUtility.resetImageTintColorTo(oriIcon, Color.WHITE));
                }
            }
        }

        return true;
    }

    /**
     * 处理菜单的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_HOME:
                if (DeviceUtility.isOfflineMode()) {
                    // 离线模式不允许执行后退操作
                    Toast.makeText(this, "应用处于离线模式，无法执行页面导航。", Toast.LENGTH_LONG).show();
                } else {
                    XLog.v("点击菜单【首页】");
                    _webView.navigateToDefaultPage(); // 页面初始化
                }
                break;
            case MENU_ID_REFRESH:
                if (DeviceUtility.isOfflineMode()) {
                    // 离线模式不允许执行后退操作
                    Toast.makeText(this, "应用处于离线模式，无法执行页面刷新。", Toast.LENGTH_LONG).show();
                } else {
                    XLog.v("点击菜单【刷新】");
                    _webView.reload(); // 仅刷新
                }
                break;
            case MENU_ID_SETTINGS:
                if (DeviceUtility.isOfflineMode()) {
                    // 离线模式不允许执行后退操作
                    Toast.makeText(this, "应用处于离线模式，无法打开【设置】页面。", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(this, OptionSettingsActivity.class));
                }
                break;
            case MENU_ID_HELP:
                if (DeviceUtility.isOfflineMode()) {
                    // 离线模式不允许执行后退操作
                    Toast.makeText(this, "应用处于离线模式，无法打开【帮助】页面。", Toast.LENGTH_LONG).show();
                } else {
                    XLog.v("点击菜单【帮助】");
                    _webView.loadUrl(ConfigManager.getInstance().getHelpUrl());
                }
                break;
            case MENU_ID_ABOUT:
                if (DeviceUtility.isOfflineMode()) {
                    // 离线模式不允许执行后退操作
                    Toast.makeText(this, "应用处于离线模式，无法打开【关于】页面。", Toast.LENGTH_LONG).show();
                } else {
                    XLog.v("点击菜单【关于】");
                    _webView.loadUrl(ConfigManager.getInstance().getAboutUrl());
                }
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