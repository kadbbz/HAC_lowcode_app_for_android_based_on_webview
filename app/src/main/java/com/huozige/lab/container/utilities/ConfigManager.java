package com.huozige.lab.container.utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.XLog;
import com.huozige.lab.container.R;

/**
 * 配置相关的帮助类
 */
public class ConfigManager {

    static final String PREFERENCE_NAME = "HAC";

    public static final String PREFERENCE_KEY_APP_ENTRY_URL = "E"; // 页面入口的地址
    public static final String PREFERENCE_KEY_SCANNER_ACTION = "SA"; // 扫描广播的Action
    public static final String PREFERENCE_KEY_SCANNER_EXTRA = "SE"; // 扫描广播的Extra
    public static final String PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE = "HA"; // 硬件加速
    public static final String PREFERENCE_KEY_ACTION_BAR_COLOR = "TCD"; // 主题色（暗色）
    public static final String PREFERENCE_KEY_SHOW_ACTION_BAR = "ABV"; // 是否显示动作栏
    public static final String PREFERENCE_KEY_SHOW_SETTING_MENU = "MSV"; // 是否显示设置菜单
    public static final String PREFERENCE_KEY_ABOUT_URL = "URLA"; // 关于页面的链接
    public static final String PREFERENCE_KEY_HELP_URL = "URLH"; // 帮助页面的链接

    public static final String PREFERENCE_KEY_CURRENT_USER = "USRN"; // 用户名

    public static final String PREFERENCE_KEY_CURRENT_PWD = "PWD"; // 用户密码

    public static final String PREFERENCE_KEY_BYPASS_COMPATIBLE_CHECK = "BCC"; // 是否忽略兼容性检查

    public static final String PREFERENCE_KEY_LOG_ALL_ENTRIES = "LAE"; // 是否记录全部日志

    public static final String PREFERENCE_KEY_UHF_ACTION = "USA"; // 扫描广播的Action
    public static final String PREFERENCE_KEY_UHF_EXTRA = "USE"; // 扫描广播的Extra

    public static final String PREFERENCE_KEY_BOOT_ON_RECEIVE = "BOR"; // 开机自启

    final Application _context;

    static ConfigManager __instance;

    SharedPreferences _sharedPref;

    private SharedPreferences getPref() {
        if (_sharedPref == null) {
            _sharedPref = _context.getSharedPreferences(
                    PREFERENCE_NAME, Activity.MODE_PRIVATE);
        }

        return _sharedPref;
    }

    public static void init(Application app) {
        if (null == __instance) {
            __instance = new ConfigManager(app);
        }
    }

    public static ConfigManager getInstance() {

        if (__instance == null) {
            throw new IllegalStateException("ConfigManager没有初始化，获取实例前需先调用init方法。");
        }

        return __instance;
    }

    private ConfigManager(Application context) {
        _context = context;
    }

    public boolean isAppReady() {
        return !this.getEntry().isEmpty();
    }

    // =========== 回写 ===========

    public void upsertBooleanEntry(String key, String propertyValue) {

        boolean value = StringConvertUtility.stringToBoolean(propertyValue);

        // 保存到配置库
        getPref().edit().putBoolean(key, value).apply();

        XLog.v("更新应用配置，键：" + key + "，值（boolean）：" + propertyValue);
    }

    public void upsertStringEntry(String key, String propertyValue) {

        // 保存到配置库
        getPref().edit().putString(key, propertyValue).apply();

        XLog.v("更新应用配置，键：" + key + "，值：" + propertyValue);
    }

    public void upsertHexIntEntry(String key, String propertyValue) {

        int value = 0;

        if (propertyValue != null) {
            try {
                value = StringConvertUtility.hexStringToInteger(propertyValue);
            } catch (NumberFormatException e) {
                // 不做
            }
        }

        // 保存到配置库
        getPref().edit().putInt(key, value).apply();

        XLog.v("更新应用配置，键：" + key + "，值（hex）：" + propertyValue);

    }

    public Boolean quickConfig(String json) {
        try {
            JSONObject config = (JSONObject) JSONObject.parse(json);

            if (config != null) {

                // 应用入口
                this.upsertStringEntry(PREFERENCE_KEY_APP_ENTRY_URL, config.getString(PREFERENCE_KEY_APP_ENTRY_URL));

                // 扫码Action
                this.upsertStringEntry(PREFERENCE_KEY_SCANNER_ACTION, config.getString(PREFERENCE_KEY_SCANNER_ACTION));

                // 扫码Extra
                this.upsertStringEntry(PREFERENCE_KEY_SCANNER_EXTRA, config.getString(PREFERENCE_KEY_SCANNER_EXTRA));

                // 关于菜单的地址
                this.upsertStringEntry(PREFERENCE_KEY_ABOUT_URL, config.getString(PREFERENCE_KEY_ABOUT_URL));

                // 帮助菜单的地址
                this.upsertStringEntry(PREFERENCE_KEY_HELP_URL, config.getString(PREFERENCE_KEY_HELP_URL));

                // 标题栏颜色
                this.upsertHexIntEntry(PREFERENCE_KEY_ACTION_BAR_COLOR, config.getString(PREFERENCE_KEY_ACTION_BAR_COLOR));

                // 菜单是否可见
                this.upsertBooleanEntry(PREFERENCE_KEY_SHOW_SETTING_MENU, config.getString(PREFERENCE_KEY_SHOW_SETTING_MENU));

                // 标题栏是否可见
                this.upsertBooleanEntry(PREFERENCE_KEY_SHOW_ACTION_BAR, config.getString(PREFERENCE_KEY_SHOW_ACTION_BAR));

                // 是否启用硬件加速
                this.upsertBooleanEntry(PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE, config.getString(PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE));

                // 是否跳过浏览器版本检查
                this.upsertBooleanEntry(PREFERENCE_KEY_BYPASS_COMPATIBLE_CHECK, config.getString(PREFERENCE_KEY_BYPASS_COMPATIBLE_CHECK));

                // 是否记录所有日志
                this.upsertBooleanEntry(PREFERENCE_KEY_LOG_ALL_ENTRIES, config.getString(PREFERENCE_KEY_LOG_ALL_ENTRIES));

                // UHF Action
                this.upsertStringEntry(PREFERENCE_KEY_UHF_ACTION, config.getString(PREFERENCE_KEY_UHF_ACTION));

                // UHF Extra
                this.upsertStringEntry(PREFERENCE_KEY_UHF_EXTRA, config.getString(PREFERENCE_KEY_UHF_EXTRA));

                // 是否开机自启
                this.upsertBooleanEntry(PREFERENCE_KEY_BOOT_ON_RECEIVE, config.getString(PREFERENCE_KEY_BOOT_ON_RECEIVE));

                XLog.v("应用初始化设置完成，配置数据：" + json);

                EventUtility.logEvent("app_quick_config", config.getString(PREFERENCE_KEY_APP_ENTRY_URL));

                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            XLog.e("存储配置信息时出错。 \r\n%s", ex);
            return false;
        }
    }

    // =========== 读取 ===========

    public String getEntry() {
        return getStringValue(_context, PREFERENCE_KEY_APP_ENTRY_URL, R.string.app_default_entry);
    }

    public String getScanAction() {
        return getStringValue(_context, PREFERENCE_KEY_SCANNER_ACTION, R.string.feature_scanner_broadcast_name);
    }

    public String getScanExtra() {
        return getStringValue(_context, PREFERENCE_KEY_SCANNER_EXTRA, R.string.feature_scanner_extra_key_barcode_broadcast);
    }

    public String getUHFAction() {
        return getStringValue(_context, PREFERENCE_KEY_UHF_ACTION, R.string.feature_uhf_broadcast_name);
    }

    public String getUHFExtra() {
        return getStringValue(_context, PREFERENCE_KEY_UHF_EXTRA, R.string.feature_uhf_extra_key_barcode_broadcast);
    }

    public Boolean getHA() {
        // 从数据库中加载，默认为软件加速
        return getPref().getBoolean(PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE, false);
    }

    public int getTCD() {


        int defaultColor = StringConvertUtility.hexStringToInteger(_context.getString(R.string.app_customize_action_bar_color));

        // 从数据库中加载，默认为配置的主题色
        return getPref().getInt(PREFERENCE_KEY_ACTION_BAR_COLOR, defaultColor);
    }

    public Boolean getSettingMenuVisible() {

        // 配置文件中的默认值
        boolean defaultVisible = Boolean.parseBoolean(_context.getString(R.string.app_customize_should_show_setting_menu));

        // 从数据库中加载
        return getPref().getBoolean(PREFERENCE_KEY_SHOW_SETTING_MENU, defaultVisible);
    }

    public Boolean getActionBarVisible() {

        // 配置文件中的默认值
        boolean defaultVisible = Boolean.parseBoolean(_context.getString(R.string.app_customize_should_show_action_bar));

        // 从数据库中加载
        return getPref().getBoolean(PREFERENCE_KEY_SHOW_ACTION_BAR, defaultVisible);
    }

    public Boolean getBypassCompatibleCheck() {

        // 配置文件中的默认值
        boolean defaultVisible = Boolean.parseBoolean(_context.getString(R.string.app_customize_should_bypass_compatible_check));

        // 从数据库中加载
        return getPref().getBoolean(PREFERENCE_KEY_BYPASS_COMPATIBLE_CHECK, defaultVisible);
    }

    public Boolean getShouldLogAllEntry() {


        // 从数据库中加载
        return getPref().getBoolean(PREFERENCE_KEY_LOG_ALL_ENTRIES, false);
    }

    public String getAboutUrl() {
        return getStringValue(_context, PREFERENCE_KEY_ABOUT_URL, R.string.app_customize_url_for_about_menu);
    }

    public String getHelpUrl() {
        return getStringValue(_context, PREFERENCE_KEY_HELP_URL, R.string.app_customize_url_for_help_menu);
    }

    public String getUserName() {
        return getStringValue(_context, PREFERENCE_KEY_CURRENT_USER, R.string.app_customize_url_for_help_menu);
    }

    public String getPassword() {
        return getStringValue(_context, PREFERENCE_KEY_CURRENT_PWD, R.string.app_customize_url_for_help_menu);
    }

    public String getStringValue(String key, String defValue) {
        return getPref().getString(key, defValue);
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        return getPref().getBoolean(key, defValue);
    }

    public Boolean getBOR() {
        // 从数据库中加载，默认为false
        return getPref().getBoolean(PREFERENCE_KEY_BOOT_ON_RECEIVE, false);
    }

    /**
     * 读取配置
     */
    private String getStringValue(Context context, String key, int defaultValueStringId) {

        return getPref().getString(key, context.getString(defaultValueStringId));
    }
}