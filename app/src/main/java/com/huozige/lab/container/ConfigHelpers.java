package com.huozige.lab.container;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * 配置相关的帮助类
 */
public class ConfigHelpers {

    static final String PREFERENCE_NAME = "MAIN";
    static final String PREFERENCE_KEY_ENTRY = "ENTRY"; // 页面入口的地址
    static final String PREFERENCE_KEY_HA = "HA"; // 是否启用硬件加速

    /**
     * 读取启动页
     */
    public static String GetEntryUrl(Activity context) {
        // 从配置库中读取启动地址
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        String url = sharedPref.getString(PREFERENCE_KEY_ENTRY, context.getString(R.string.app_default_entry));

        // 不合法的地址，采用默认接入点
        if (url.equals("")||!url.toLowerCase().startsWith("http")) {
            url = context.getString(R.string.app_default_entry);
        }

        return url;
    }

    /**
     * 读取硬件加速状态
     */
    public static Boolean GetHAEnabled(Activity context) {
        // 从配置库中读取启动地址
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        return  sharedPref.getBoolean(PREFERENCE_KEY_HA, true); // 默认启用硬件加速
}


    /**
     * 将启动页写入数据库
     */
    public static void UpsertEntryUrl(Activity context, String entry){
        // 打开配置库
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        // 保存到配置库
        sharedPref.edit().putString(PREFERENCE_KEY_ENTRY, entry).apply();
    }

    /**
     * 将是否启用硬件加速写入数据库
     */
    public static void UpsertHardwareAccelerateEnabled(Activity context, Boolean enabled){
        // 打开配置库
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        // 保存到配置库
        sharedPref.edit().putBoolean(PREFERENCE_KEY_HA, enabled).apply();
    }

}
