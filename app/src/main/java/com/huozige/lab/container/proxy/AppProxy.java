package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.Permission;
import com.huozige.lab.container.OptionSettingsActivity;
import com.huozige.lab.container.QuickConfigActivity;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.LifecycleUtility;
import com.huozige.lab.container.utilities.DeviceUtilities;

/**
 * 让页面能对APP壳子进行操作
 * 1.0.0
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
 * 1.9.0
 * app.closeApp()：关闭应用
 * 1.12.0
 * app.dial(phoneNumber)：拨打电话
 * 1.14.2
 * app.toggleHardwareAccelerate(shouldEnable)：是否启用硬件加速，重启APP后生效
 * app.toggleBypassCompatibleCheck(shouldBypass)：是否跳过WebView版本检查，重启APP后生效
 * app.toggleLogAllEntries(shouldLog)：是否记录全部诊断日志，重启APP后生效
 * 1.15.0
 * app.toggleOfflineMode(shouldOffline)：启用或关闭离线模式
 * app.showToast(text)：提示消息
 * app.vibrate(duration)：持续震动
 * app.playNotification()：播放提示音
 * app.playAlarm()：播放闹钟
 * app.playRingtone()：播放铃声
 * 1.17.0
 * app.getVersion2()：获取版本号
 * app.getPackageName2()：获取入口的包名
 * app.getActionBarColor2()：获取标题栏颜色
 */
public class AppProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _arcWoCallback; // 用来弹出页面

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
    public void onActivityCreated(AppCompatActivity activity) {
        _arcWoCallback = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });
    }

    /**
     * 注册到页面的app.setScannerOptions(action,extra)方法
     * 设置扫描头的参数配置
     */
    @JavascriptInterface
    public void setScannerOptions(String action, String extra) {
        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_SCANNER_ACTION, action);
        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_SCANNER_EXTRA, extra);
    }


    /**
     * 注册到页面的app.closeApp()方法
     * 无需提示，直接关闭应用
     */
    @JavascriptInterface
    public void closeApp() {
        LifecycleUtility.close();
    }

    /**
     * 注册到页面的app.restartApp()方法
     * 无需提示，直接重启应用
     */
    @JavascriptInterface
    public void restartApp() {
        // 直接重启
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.openSettingPage()方法
     * 导航到设置页面
     */
    @JavascriptInterface
    public void openSettingPage() {

        runOnUiThread(() ->
                _arcWoCallback.launch(createIntent(OptionSettingsActivity.class))
        );
    }

    /**
     * 拨打电话
     */
    @JavascriptInterface
    public void dial(String phoneNumber) {

        asyncRequirePermissions(new String[]{
                Permission.CALL_PHONE
        }, () -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:" + phoneNumber));

            writeInfoLog("拨打电话：" + phoneNumber);
            startActivity(intent);
        });
    }

    /**
     * 注册到页面的app.openQuickConfigPage()方法
     * 导航到快速配置页面
     */
    @JavascriptInterface
    public void openQuickConfigPage() {
        runOnUiThread(() ->
                _arcWoCallback.launch(createIntent(QuickConfigActivity.class))
        );
    }

    /**
     * 注册到页面的app.toggleSettingMenu(shouldShow)方法
     * 设置是否显示设置菜单，传入空、0、no或者false意味着隐藏， 其他值都为显示
     */
    @JavascriptInterface
    public void toggleSettingMenu(String shouldShow) {

        getConfigManager().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_SHOW_SETTING_MENU, shouldShow);

        writeInfoLog("切换设置菜单的显示策略为：" + shouldShow);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.toggleActionBar(shouldShow)方法
     * 设置是否显示ActionBar，传入空、0、no或者false意味着隐藏， 其他值都为显示
     */
    @JavascriptInterface
    public void toggleActionBar(String shouldShow) {

        getConfigManager().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_SHOW_ACTION_BAR, shouldShow);

        writeInfoLog("切换标题栏的显示策略为：" + shouldShow);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 启用或关闭硬件加速
     *
     * @param shouldEnable 硬件加速
     */
    @JavascriptInterface
    public void toggleHardwareAccelerate(String shouldEnable) {

        getConfigManager().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE, shouldEnable);

        writeInfoLog("切换硬件加速的策略为：" + shouldEnable);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 启用或关闭WebView兼容性检查
     *
     * @param shouldBypass 跳过检查
     */
    @JavascriptInterface
    public void toggleBypassCompatibleCheck(String shouldBypass) {

        getConfigManager().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_BYPASS_COMPATIBLE_CHECK, shouldBypass);

        writeInfoLog("切换“跳过兼容性检测”的策略为：" + shouldBypass);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 设置是否记录诊断日志
     *
     * @param shouldLog 记录全部日志
     */
    @JavascriptInterface
    public void toggleLogAllEntries(String shouldLog) {

        getConfigManager().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_LOG_ALL_ENTRIES, shouldLog);

        writeInfoLog("切换诊断日志的记录策略为：" + shouldLog);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.setAboutUrl(url)方法
     * 设置关于菜单的跳转地址，传入空字符串则自动隐藏该菜单
     */
    @JavascriptInterface
    public void setAboutUrl(String url) {

        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_ABOUT_URL, url);

        writeInfoLog("修改关于菜单的地址为：" + url);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.setHelpUrl(url)方法
     * 设置关于菜单的跳转地址，传入空字符串则自动隐藏该菜单
     */
    @JavascriptInterface
    public void setHelpUrl(String url) {

        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_HELP_URL, url);

        writeInfoLog("修改帮助菜单的地址为：" + url);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.getActionBarColor(cell)方法
     * 获取APP ActionBar的颜色
     * 返回的数值是16进制，去掉透明度
     */
    @JavascriptInterface
    public void getActionBarColor(String cell) {

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

        callback(cell, sb.toString());
    }

    /**
     * 注册到页面的app.getActionBarColor()方法
     * 获取APP ActionBar的颜色
     *
     * @return 返回的数值是16进制，去掉透明度
     */
    @JavascriptInterface
    public String getActionBarColor2() {

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

        return sb.toString();
    }

    /**
     * 注册到页面的app.setActionBarColor(colorInteger)方法
     * 设置APP ActionBar的颜色
     * 输入的颜色是16进制，不需要透明度
     */
    @JavascriptInterface
    public void setActionBarColor(String colorInteger) {

        getConfigManager().upsertHexIntEntry(ConfigManager.PREFERENCE_KEY_ACTION_BAR_COLOR, colorInteger);

        writeInfoLog("修改标题栏的颜色为：" + colorInteger);

        // 重启生效
        LifecycleUtility.restart();
    }

    /**
     * 注册到页面的app.getPackageName(cell)方法
     * 获取APP入口的包名
     */
    @JavascriptInterface
    public void getPackageName(String cell) {
       callback(cell, DeviceUtilities.getPackageName());
    }

    /**
     * 注册到页面的app.getPackageName2()方法
     * 获取APP入口的包名
     *
     * @return 应用的包名
     */
    @JavascriptInterface
    public String getPackageName2() {
        return DeviceUtilities.getPackageName();
    }

    /**
     * 注册到页面的app.getVersion(cell)方法
     * 获取版本号
     */
    @JavascriptInterface
    public void getVersion(String cell) {
        String finalVersionName = DeviceUtilities.getPackageVersionName();
        callback(cell, finalVersionName);
    }

    /**
     * 注册到页面的app.getVersion()方法
     * 获取版本号
     *
     * @return 版本号
     */
    @JavascriptInterface
    public String getVersion2() {
        return DeviceUtilities.getPackageVersionName();
    }

    /**
     * 设置离线模式
     *
     * @param shouldOffline 是否为离线模式
     */
    @JavascriptInterface
    public void toggleOfflineMode(boolean shouldOffline) {
        DeviceUtilities.setOfflineMode(shouldOffline);
        writeInfoLog("离线模式已切换为：" + shouldOffline);
    }

    /**
     * 以Toast形式提示消息
     *
     * @param text 文本
     */
    @JavascriptInterface
    public void showToast(String text) {
        showLongToast(text);
    }

    /**
     * 持续震动
     *
     * @param duration 持续时间（秒）
     */
    @JavascriptInterface
    public void vibrate(long duration) {
        DeviceUtilities.vibrate(duration);
        writeInfoLog("执行震动：" + duration + "秒");
        registryForFeatureUsageAnalyze("use_vibrate_feature", "");
    }

    /**
     * 播放提示音
     */
    @JavascriptInterface
    public void playNotification() {
        DeviceUtilities.playRingtone(DeviceUtilities.RINGTONE_TYPE_NOTIFICATION);
        writeInfoLog("播放NOTIFICATION铃声");
        registryForFeatureUsageAnalyze("use_ring_feature", "Notification");
    }

    /**
     * 播放闹钟
     */
    @JavascriptInterface
    public void playAlarm() {
        DeviceUtilities.playRingtone(DeviceUtilities.RINGTONE_TYPE_ALARM);
        writeInfoLog("播放ALARM铃声");
        registryForFeatureUsageAnalyze("use_ring_feature", "Alarm");
    }

    /**
     * 播放铃声
     */
    @JavascriptInterface
    public void playRingtone() {
        DeviceUtilities.playRingtone(DeviceUtilities.RINGTONE_TYPE_RINGTONE);
        writeInfoLog("播放RINGTONE铃声");
        registryForFeatureUsageAnalyze("use_ring_feature", "Ringtone");
    }
}
