package com.huozige.lab.container;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

import java.util.List;

/**
 * 系统设置页面
 */
public class SettingActivity extends AppCompatActivity {

    static final String LOG_TAG = "SettingActivity";
    ConfigManager _cm = new ConfigManager(this);
    ActivityResultLauncher<Intent> _arcZxingLite; // 用来弹出ZXingLite扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    EditText _txtUrl,_txtScanAction,_txtScanExtra;
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

        Button cmdRestart = findViewById(R.id.cmdRestart);
        cmdRestart.setOnClickListener(Restart);

        Button cmdSave = findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(Save);

        Button cmdScan = findViewById(R.id.cmdUrlQrCode);
        cmdScan.setOnClickListener(ScanForUrl);

        // 创建到ZXingLite的调用器
        _arcZxingLite = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
            Intent data = result.getData();

            if (null != data) {
                String resultS = CameraScan.parseScanResult(data);
                _txtUrl.setText(resultS);
            }
        });

        // 设置初始值
        _txtUrl.setText(_cm.GetEntry());
        _txtScanAction.setText(_cm.GetScanAction());
        _txtScanExtra.setText(_cm.GetScanExtra());
        _cboHa.setChecked(_cm.GetHA());

        Log.v(LOG_TAG, "配置页面初始化完成。");

    }
    View.OnClickListener ScanForUrl = new View.OnClickListener() {
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

    View.OnClickListener Save = view -> {

        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setPositiveButton(SettingActivity.this.getString(R.string.ui_button_ok),new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存配置
                _cm.UpsertEntry(_txtUrl.getText().toString());
                _cm.UpsertScanAction(_txtScanAction.getText().toString());
                _cm.UpsertScanExtra(_txtScanExtra.getText().toString());
                _cm.UpsertHA(_cboHa.isChecked());

                Toast.makeText(SettingActivity.this, "设置保存成功，点击右上角【首页】菜单即可生效。", Toast.LENGTH_LONG).show();

                Log.v(LOG_TAG, "配置更新完成。");

                finish();
            }
        });
        ab.setNegativeButton(SettingActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_setting_save);
        ab.setTitle(R.string.ui_menu_settings);
        ab.show();

    };

    View.OnClickListener Restart = view -> {

        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setPositiveButton(SettingActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

            // 重启应用
            Intent intentR = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentR);
        });
        ab.setNegativeButton(SettingActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_setting_restart);
        ab.setTitle(R.string.ui_menu_settings);
        ab.show();

    };

}