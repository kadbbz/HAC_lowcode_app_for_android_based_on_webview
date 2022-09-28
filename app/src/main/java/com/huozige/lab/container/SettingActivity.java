package com.huozige.lab.container;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

import java.util.List;

/**
 * 系统设置页面
 * 不包含APP个性化配置（通过JS接口设置）
 */
public class SettingActivity extends BaseActivity {

    static final String LOG_TAG = "SettingActivity";

    ActivityResultLauncher<Intent> _arcZxingLite,_arc4QuickConfig;

    EditText _txtUrl, _txtScanAction, _txtScanExtra;
    CheckBox _cboHa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.ui_menu_settings));

        setContentView(R.layout.activity_setting);

        _txtUrl = findViewById(R.id.txtUrl);
        _txtScanAction = findViewById(R.id.txtAction);
        _txtScanExtra = findViewById(R.id.txtExtra);
        _cboHa = findViewById(R.id.cboHa);

        Button cmdReset = findViewById(R.id.cmdReset);
        cmdReset.setOnClickListener(reset);

        Button cmdSave = findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(save);

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

        _arc4QuickConfig = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> ConfigManager.restartApp()); // 打开设置页面，返回后重启应用

        // 设置初始值
        _txtUrl.setText(ConfigManager.getEntry());
        _txtScanAction.setText(ConfigManager.getScanAction());
        _txtScanExtra.setText(ConfigManager.getScanExtra());
        _cboHa.setChecked(ConfigManager.getHA());

        Log.v(LOG_TAG, "配置页面初始化完成。");

    }

    View.OnClickListener scanForUrl = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            XXPermissions.with(SettingActivity.this)
                    .permission(Permission.CAMERA)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            Toast.makeText(SettingActivity.this, "请允许应用利用摄像头扫描二维码", Toast.LENGTH_LONG).show();
                        }
                    });

            // 调用ZXingLite的扫码页面
            _arcZxingLite.launch(new Intent(SettingActivity.this, CaptureActivity.class));

        }
    };

    View.OnClickListener save = view -> {

        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setPositiveButton(SettingActivity.this.getString(R.string.ui_button_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存配置
                ConfigManager.upsertEntry(_txtUrl.getText().toString());
                ConfigManager.upsertScanAction(_txtScanAction.getText().toString());
                ConfigManager.upsertScanExtra(_txtScanExtra.getText().toString());
                ConfigManager.upsertHA(_cboHa.isChecked());

                Toast.makeText(SettingActivity.this, "设置保存成功。", Toast.LENGTH_LONG).show();

                Log.v(LOG_TAG, "配置更新完成。");

                // 重启生效
                ConfigManager.restartApp();
            }
        });

        ab.setNegativeButton(SettingActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_setting_save);
        ab.setTitle(R.string.ui_menu_settings);

        ab.show();

    };

    View.OnClickListener reset = view -> {

        // 重新开始
        _arc4QuickConfig.launch(new Intent(SettingActivity.this, QuickConfigActivity.class));

    };

}