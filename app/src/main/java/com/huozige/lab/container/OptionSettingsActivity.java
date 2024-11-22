package com.huozige.lab.container;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebStorage;
import android.webkit.WebViewDatabase;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.utilities.ColorUtility;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.DeviceUtilities;
import com.huozige.lab.container.utilities.LifecycleUtility;

import javax.annotation.Nullable;

import io.realm.Realm;

/**
 * 应用选项设置界面
 */
public class OptionSettingsActivity extends AppCompatActivity {

    static final int MENU_ID_RESET = 1;
    static final int MENU_ID_CLEAN_CACHE = 2;
    static final int MENU_ID_RESTART = 3;
    static final int MENU_ID_ABOUT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        // 设置动作栏
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.show();

            // 设置状态栏颜色，做沉浸式体验
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ConfigManager.getInstance().getTCD());

            // 设置ActionBar的颜色，需要避免文字和背景颜色过于接近
            Toolbar aBar = findViewById(androidx.appcompat.R.id.action_bar);

            if (aBar != null) {

                aBar.setBackgroundColor(ConfigManager.getInstance().getTCD());
                // 深色采用黑色文字，浅色用白色文字
                if (ColorUtility.getGrayScale(ConfigManager.getInstance().getTCD()) > ColorUtility.GRAYSCALE_THRESHOLD_BRIGHT_COLOR) {
                    aBar.setTitleTextColor(Color.BLACK);
                } else {
                    aBar.setTitleTextColor(Color.WHITE);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_ID_RESET, MENU_ID_RESET, getString(R.string.menu_setting_reset));
        menu.add(0, MENU_ID_CLEAN_CACHE, MENU_ID_CLEAN_CACHE, getString(R.string.menu_settings_clear_cache));
        menu.add(1, MENU_ID_RESTART, MENU_ID_RESTART, getString(R.string.ui_button_restart));
        menu.add(2, MENU_ID_ABOUT, MENU_ID_ABOUT, getString(R.string.menu_setting_version));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_RESET: {
                startActivity(new Intent(this, QuickConfigActivity.class));
                break;
            }
            case MENU_ID_CLEAN_CACHE: {
                AlertDialog.Builder ab = new AlertDialog.Builder(OptionSettingsActivity.this);
                ab.setPositiveButton(OptionSettingsActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

                    XLog.v("开始清理数据。");

                    // 清理Realm数据库（异步）
                    Realm.getDefaultInstance().executeTransactionAsync(transactionRealm -> transactionRealm.deleteAll());

                    // 用户信息
                    ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_CURRENT_USER, "");
                    ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_CURRENT_PWD, "");

                    // 清理WebView的缓存
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().removeSessionCookies(null);
                    CookieManager.getInstance().flush();
                    WebStorage.getInstance().deleteAllData();
                    WebViewDatabase.getInstance(this).clearHttpAuthUsernamePassword();

                    GeolocationPermissions.getInstance().clearAll();

                    Toast.makeText(OptionSettingsActivity.this, "数据缓存已全部清理，请手动注销应用的用户。", Toast.LENGTH_LONG).show();

                    XLog.v("数据清理完成。");

                    // 重启生效
                    LifecycleUtility.restart();
                });

                ab.setNegativeButton(OptionSettingsActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
                    // 什么都不干
                });

                ab.setMessage(R.string.ui_message_setting_clear);
                ab.setTitle(R.string.title_activity_option_settings);

                ab.show();

                break;
            }
            case MENU_ID_RESTART: {
                AlertDialog.Builder ab = new AlertDialog.Builder(OptionSettingsActivity.this);
                ab.setPositiveButton(OptionSettingsActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {
                    // 重启生效
                    LifecycleUtility.restart();
                });

                ab.setNegativeButton(OptionSettingsActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
                    // 什么都不干
                });

                ab.setMessage(R.string.ui_message_setting_restart);
                ab.setTitle(R.string.title_activity_option_settings);

                ab.show();
                break;
            }
            case MENU_ID_ABOUT: {
                String info = "SSAID: " + DeviceUtilities.getSSAID() + " \r\nPackage Version: " + DeviceUtilities.getPackageVersionName() + " \r\nWebView Version: " + DeviceUtilities.getWebViewVersionName();
                Toast.makeText(OptionSettingsActivity.this, info, Toast.LENGTH_LONG).show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 用来适配自定义数据存储
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // 对接ConfigManager实现数据存取
            PreferenceManager preferenceManager = getPreferenceManager();
            preferenceManager.setPreferenceDataStore(new DataStoreViaConfigManager());

            // 默认逻辑
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    /**
     * 基于ConfigManager的数据存储
     */
    public static class DataStoreViaConfigManager extends PreferenceDataStore {

        @Override
        public void putBoolean(String key, boolean value) {
            ConfigManager.getInstance().upsertBooleanEntry(key, value ? "true" : "false");
        }

        @Override
        public void putString(String key, @Nullable String value) {
            // Save the value somewhere.
            ConfigManager.getInstance().upsertStringEntry(key, value);
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            // Retrieve the value.
            return ConfigManager.getInstance().getBooleanValue(key, defValue);
        }

        @Override
        @Nullable
        public String getString(String key, @Nullable String defValue) {
            // Retrieve the value.
            return ConfigManager.getInstance().getStringValue(key, defValue);
        }
    }
}