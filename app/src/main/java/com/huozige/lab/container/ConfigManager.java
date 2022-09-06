package com.huozige.lab.container;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * 配置相关的帮助类
 */
public class ConfigManager {

    static final String PREFERENCE_NAME = "HAC";
    static final String PREFERENCE_KEY_ENTRY = "E"; // 页面入口的地址
    static final String PREFERENCE_KEY_SCAN_ACTION = "SA"; // 扫描广播的Action
    static final String PREFERENCE_KEY_SCAN_EXTRA = "SE"; // 扫描广播的Extra
    static final String PREFERENCE_KEY_HA = "HA"; // 硬件加速

    private final Activity _context;

    public ConfigManager(Activity context){
        _context = context;
    }

    public  String GetEntry(){
        return GetStringValue(_context, PREFERENCE_KEY_ENTRY, R.string.app_default_entry);
    }

    public  String GetScanAction(){
        return GetStringValue(_context, PREFERENCE_KEY_SCAN_ACTION, R.string.feature_scanner_broadcast_name);
    }

    public  String GetScanExtra(){
        return GetStringValue(_context, PREFERENCE_KEY_SCAN_EXTRA, R.string.feature_scanner_extra_key_barcode_broadcast);
    }

    public  Boolean GetHA(){
        // 打开配置库
        SharedPreferences sharedPref = _context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        // 从数据库中加载，默认为硬件加速
        return sharedPref.getBoolean(PREFERENCE_KEY_HA, true);
    }

    public  void UpsertEntry(String value){
        UpsertStringValue(_context,PREFERENCE_KEY_ENTRY,value);
    }

    public  void UpsertScanAction(String value){
        UpsertStringValue(_context,PREFERENCE_KEY_SCAN_ACTION,value);
    }

    public  void UpsertScanExtra(String value){
        UpsertStringValue(_context,PREFERENCE_KEY_SCAN_EXTRA,value);
    }

    public  void UpsertHA(Boolean enabled){
        // 打开配置库
        SharedPreferences sharedPref = _context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        // 保存到配置库
        sharedPref.edit().putBoolean(PREFERENCE_KEY_HA, enabled).apply();
    }

    /**
     * 读取配置
     */
    public static String GetStringValue(Activity context, String key, int defaultValueStringId) {
        // 从配置库中读取启动地址
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        return sharedPref.getString(key, context.getString(defaultValueStringId));
    }

    /**
     * 写入配置
     */
    public static void UpsertStringValue(Activity context, String key, String value){
        // 打开配置库
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFERENCE_NAME, Activity.MODE_PRIVATE);

        // 保存到配置库
        sharedPref.edit().putString(key, value).apply();
    }

}