package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.QuickConfigActivity;
import com.huozige.lab.container.SettingActivity;
import com.huozige.lab.container.utilities.LifecycleUtility;

/**
 * 让页面能对APP壳子进行操作
 * 1.1.0
 * app.getVersion(cell)：获取版本号
 * app.getPackageName(cell)：获取入口的包名
 * app.getActionBarColor(cell)：获取标题栏颜色
 * app.setActionBarColor(colorHex)：设置标题栏颜色
 * app.setScannerOptions(action,extra)：设置扫描头的参数
 * app.toggleSettingMenu(shouldShow)：是否隐藏设置菜单，重启APP后生效
 * app.setAboutUrl(url)：设置“关于”菜单的地址，重启APP后生效
 * app.setHelpUrl(url)：设置“帮助”菜单的地址，重启APP后生效
 * app.restartApp()：重启应用
 * 1.2.0
 * app.toggleActionBar(shouldShow)：是否隐藏ActionBar，重启APP后生效
 * app.openSettingPage()：打开设置页面
 * app.openQuickConfigPage()：打开快速配置页面
 */
public class AppProxy extends AbstractProxy {

    String _versionCell, _packageCell; // 单元格位置缓存

    ActivityResultLauncher<Intent> _arcWoCallback; // 用来弹出页面

    static final String LOG_TAG = "AppProxy";

    /**
     * 注册的名称为：app
     */
    @Override
    public String getName() {
        return "app";
    }

    /**
     * 无需操作
     */
    @Override
    public void onActivityCreated() {
        _arcWoCallback = getInterop().getActivityContext().registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });
    }

    /**
     * 无需操作
     */
    @Override
    public void beforeActivityPause() {

    }

    /**
     * 无需操作
     */
    @Override
    public void onActivityResumed() {

    }

    /**
     * 无需操作
     *
     * @param requestCode 同onActivityResult
     * @param resultCode  同onActivityResult
     * @param data        同onActivityResult
     * @return 跳过这个JS桥，处理下一个
     */
    @Override
    public Boolean processActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    /**
     * 注册到页面的app.setScannerOptions(action,extra)方法
     * 设置扫描头的参数配置
     */
    @JavascriptInterface
    public void setScannerOptions(String action, String extra) {

        // 更新配置项
        getConfigManager().upsertScanAction(action);
        getConfigManager().upsertScanExtra(extra);
    }

    /**
     * 注册到页面的app.restartApp()方法
     * 无需提示，直接重启应用
     */
    @JavascriptInterface
    public void restartApp() {
        // 直接重启
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.openSettingPage()方法
     * 导航到设置页面
     */
    @JavascriptInterface
    public void openSettingPage() {

        getInterop().getActivityContext().runOnUiThread(() ->
                _arcWoCallback.launch(new Intent(getInterop().getActivityContext(), SettingActivity.class))
        );
    }

    /**
     * 注册到页面的app.openQuickConfigPage()方法
     * 导航到快速配置页面
     */
    @JavascriptInterface
    public void openQuickConfigPage() {
        getInterop().getActivityContext().runOnUiThread(() ->
                _arcWoCallback.launch(new Intent(getInterop().getActivityContext(), QuickConfigActivity.class))
        );
    }

    /**
     * 注册到页面的app.toggleSettingMenu(shouldShow)方法
     * 设置是否显示设置菜单，传入空、0、no或者false意味着隐藏， 其他值都为显示
     */
    @JavascriptInterface
    public void toggleSettingMenu(String shouldShow) {

        getConfigManager().upsertSettingMenuVisible(shouldShow != null && shouldShow.length() > 0 && !shouldShow.equalsIgnoreCase("0") && !shouldShow.equalsIgnoreCase("false") && !shouldShow.equalsIgnoreCase("no"));

        // 重启生效
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.toggleActionBar(shouldShow)方法
     * 设置是否显示ActionBar，传入空、0、no或者false意味着隐藏， 其他值都为显示
     */
    @JavascriptInterface
    public void toggleActionBar(String shouldShow) {

        getConfigManager().upsertActionBarVisible(shouldShow != null && shouldShow.length() > 0 && !shouldShow.equalsIgnoreCase("0") && !shouldShow.equalsIgnoreCase("false") && !shouldShow.equalsIgnoreCase("no"));

        // 重启生效
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.setAboutUrl(url)方法
     * 设置关于菜单的跳转地址，传入空字符串则自动隐藏该菜单
     */
    @JavascriptInterface
    public void setAboutUrl(String url) {

        // 更新配置项
        getConfigManager().upsertAboutUrl(url);

        // 重启生效
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.setHelpUrl(url)方法
     * 设置关于菜单的跳转地址，传入空字符串则自动隐藏该菜单
     */
    @JavascriptInterface
    public void setHelpUrl(String url) {

        // 更新配置项
        getConfigManager().upsertHelpUrl(url);

        // 重启生效
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.getActionBarColor(cell)方法
     * 获取APP ActionBar的颜色
     * 返回的数值是16进制，去掉透明度
     */
    @JavascriptInterface
    public void getActionBarColor(String cell) {

        // 记录参数
        _packageCell = cell;
        int tcdColor = getConfigManager().getTCD();

        // 兼容Web的常规做法，不返回A，仅返回RGB
        String R, G, B;
        StringBuilder sb = new StringBuilder();
        R = Integer.toHexString(Color.red(tcdColor));
        G = Integer.toHexString(Color.green(tcdColor));
        B = Integer.toHexString(Color.blue(tcdColor));
        //判断获取到的A,R,G,B值的长度 如果长度等于1 给A,R,G,B值的前边添0
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("0x");
        sb.append(R);
        sb.append(G);
        sb.append(B);

        getInterop().setInputValue(_packageCell, sb.toString());
    }

    /**
     * 注册到页面的app.setActionBarColor(colorInteger)方法
     * 设置APP ActionBar的颜色
     * 输入的颜色是16进制，不需要透明度
     */
    @JavascriptInterface
    public void setActionBarColor(String colorInteger) {

        // 去掉可能误输入的#号和0x
        colorInteger = colorInteger.replace("#", "");
        colorInteger = colorInteger.replace("0x", "");

        // 更新配置项
        getConfigManager().upsertTCD(Integer.parseInt(colorInteger, 16) + 0xFF000000);

        // 重启生效
        LifecycleUtility.restart(getInterop().getActivityContext());
    }

    /**
     * 注册到页面的app.getPackageName(cell)方法
     * 获取APP入口的包名
     */
    @JavascriptInterface
    public void getPackageName(String cell) {

        // 记录参数
        _packageCell = cell;
        getInterop().setInputValue(_packageCell, getInterop().getActivityContext().getPackageName());
    }

    /**
     * 注册到页面的app.getVersion(cell)方法
     * 获取版本号
     */
    @JavascriptInterface
    public void getVersion(String cell) {

        // 记录参数
        _versionCell = cell;

        String versionName = "";

        try {
            PackageInfo pinfo = getInterop().getActivityContext().getPackageManager().getPackageInfo(getInterop().getActivityContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "获取应用版本信息出错：" + e);
            e.printStackTrace();
        }

        // 需要调度回主线程操作
        String finalVersionName = versionName;
        getInterop().setInputValue(_versionCell, finalVersionName);
    }
}
