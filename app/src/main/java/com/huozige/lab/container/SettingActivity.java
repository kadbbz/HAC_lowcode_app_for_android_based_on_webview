package com.huozige.lab.container;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebStorage;
import android.webkit.WebViewDatabase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.LifecycleUtility;
import com.huozige.lab.container.utilities.MiscUtilities;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

import io.realm.Realm;

/**
 * 系统设置页面
 * 不包含APP个性化配置（通过JS接口设置）
 */
public class SettingActivity extends BaseActivity {


    ActivityResultLauncher<Intent> _arcZxingLite, _arc4QuickConfig;

    EditText _txtUrl, _txtScanAction, _txtScanExtra;
    CheckBox _cboHa, _cboLAE;
    TextView _lblInfo;

    private String getVersionInfo() {
        return "SSAID: " + MiscUtilities.getSSAID(this) + " \r\nPackage Version: " + MiscUtilities.getPackageVersionName(this) + " \r\nWebView Version: " + MiscUtilities.getWebViewVersionName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.ui_menu_settings));

        setContentView(R.layout.activity_setting);

        _txtUrl = findViewById(R.id.txtUrl);
        _txtScanAction = findViewById(R.id.txtAction);
        _txtScanExtra = findViewById(R.id.txtExtra);
        _cboHa = findViewById(R.id.cboHa);
        _cboLAE = findViewById(R.id.cboLAE);
        _lblInfo = findViewById(R.id.lblVersionInfo);

        Button cmdReset = findViewById(R.id.cmdReset);
        cmdReset.setOnClickListener(reset);

        Button cmdSave = findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(save);

        Button cmdClear = findViewById(R.id.cmdClear);
        cmdClear.setOnClickListener(clear);

        ImageButton imgScan = findViewById(R.id.imgScan);
        imgScan.setOnClickListener(scanForUrl);

        // 创建到ZXingLite的调用器
        _arcZxingLite = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
            Intent data = result.getData();

            if (null != data) {
                String resultS = CameraScan.parseScanResult(data);
                _txtUrl.setText(resultS);
            }
        });

        _arc4QuickConfig = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> LifecycleUtility.restart(this)); // 打开设置页面，返回后重启应用

        // 设置初始值
        _txtUrl.setText(ConfigManager.getInstance().getEntry());
        _txtScanAction.setText(ConfigManager.getInstance().getScanAction());
        _txtScanExtra.setText(ConfigManager.getInstance().getScanExtra());
        _cboHa.setChecked(ConfigManager.getInstance().getHA());
        _cboLAE.setChecked(ConfigManager.getInstance().getShouldLogAllEntry());

        _lblInfo.setText(getVersionInfo());
        XLog.v("配置页面初始化完成。");

    }

    View.OnClickListener scanForUrl = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // 申请摄像头权限，然后开始扫码
            PermissionsUtility.asyncRequirePermissions(SettingActivity.this, new String[]{
                    Permission.CAMERA
            }, () -> {
                // 调用ZXingLite的扫码页面
                _arcZxingLite.launch(new Intent(SettingActivity.this, CaptureActivity.class));
            });
        }
    };

    View.OnClickListener save = view -> {

        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setPositiveButton(SettingActivity.this.getString(R.string.ui_button_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存配置
                ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_APP_ENTRY_URL, _txtUrl.getText().toString());
                ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_SCANNER_ACTION, _txtScanAction.getText().toString());
                ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_SCANNER_EXTRA, _txtScanExtra.getText().toString());
                ConfigManager.getInstance().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_ENABLE_HARDWARE_ACCELERATE, _cboHa.isChecked() ? "true" : "false");
                ConfigManager.getInstance().upsertBooleanEntry(ConfigManager.PREFERENCE_KEY_LOG_ALL_ENTRIES, _cboLAE.isChecked() ? "true" : "false");

                Toast.makeText(SettingActivity.this, "设置保存成功。", Toast.LENGTH_LONG).show();

                XLog.v("配置更新完成。");

                // 重启生效
                LifecycleUtility.restart(SettingActivity.this);
            }
        });

        ab.setNegativeButton(SettingActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_setting_save);
        ab.setTitle(R.string.ui_menu_settings);

        ab.show();

    };

    View.OnClickListener clear = view -> {

        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setPositiveButton(SettingActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

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

            Toast.makeText(SettingActivity.this, "APP的数据缓存已全部清理，请手动注销应用的用户。", Toast.LENGTH_LONG).show();

            XLog.v("数据清理完成。");

            // 重启生效
            LifecycleUtility.restart(SettingActivity.this);
        });

        ab.setNegativeButton(SettingActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_setting_clear);
        ab.setTitle(R.string.ui_menu_settings);

        ab.show();

    };
    View.OnClickListener reset = view -> {

        // 重新开始
        _arc4QuickConfig.launch(new Intent(SettingActivity.this, QuickConfigActivity.class));

    };

}