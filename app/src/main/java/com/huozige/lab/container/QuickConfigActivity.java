package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.LifecycleUtility;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.king.camera.scan.CameraScan;

/**
 * 通过二维码完成快速配置
 */
public class QuickConfigActivity extends BaseActivity {

    ActivityResultLauncher<Intent> _arc4TCA; // 用来弹出配置页面。

    // 创建到ZXingLite的调用器
    ActivityResultLauncher<Intent> _arcZxingLite = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        XLog.v("扫描到配置码");

        // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
        Intent data = result.getData();

        if (null != data) {
            String json = CameraScan.parseScanResult(data);

            XLog.v("配置码的内容: " + json);

            AlertDialog.Builder ab = new AlertDialog.Builder(QuickConfigActivity.this);
            ab.setPositiveButton(QuickConfigActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

                // 执行配置过程
                Boolean isOk = ConfigManager.getInstance().quickConfig(json);

                if (isOk) {
                    

                    XLog.v("配置码格式正确，已完成初始化");

                    // 提示正确的信息
                    Toast.makeText(QuickConfigActivity.this, getString(R.string.ui_message_quick_config_done), Toast.LENGTH_LONG).show();

                    // 重启生效
                    LifecycleUtility.restart();
                } else {

                    XLog.e("配置码不正确或版本过旧，内容为：" + json);

                    // 仅提示错误信息
                    Toast.makeText(QuickConfigActivity.this, getString(R.string.ui_message_quick_config_broken), Toast.LENGTH_LONG).show();
                }

            }).setNegativeButton(QuickConfigActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
                // 什么都不干
            });

            ab.setMessage(R.string.ui_message_quick_config_confirm);
            ab.setTitle(R.string.ui_menu_settings);
            ab.show();
        } else {
            XLog.e("配置码格式不正确，无法读取有效内容");
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.ui_title_quick_config));

        setContentView(R.layout.activity_quick_config);

        ImageButton imgScan = findViewById(R.id.imgScanner);
        imgScan.setOnClickListener(scanForConfig);

        TextView lblHelp = findViewById(R.id.textTCHelp);
        lblHelp.setOnClickListener(gotoTextConfig);

        _arc4TCA = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> LifecycleUtility.restart()); // 打开设置页面，返回后刷新浏览器

        XLog.v("快速配置界面准备就绪");
    }

    View.OnClickListener gotoTextConfig = view -> _arc4TCA.launch(new Intent(this, TextConfigActivity.class));

    View.OnClickListener scanForConfig = view -> {

        // 申请摄像头权限，然后开始扫码
        PermissionsUtility.asyncRequirePermissions(QuickConfigActivity.this, new String[]{
                Permission.CAMERA,
                Permission.NOTIFICATION_SERVICE
        }, () -> {
            // 调用ZXingLite的扫码页面
            var intent = new Intent(QuickConfigActivity.this, HACQRCodeScanActivity.class);
            intent.putExtra(HACQRCodeScanActivity.EXTRA_KEY_SCAN_HINTS, HACQRCodeScanActivity.EXTRA_QR_CODE_HINTS);
            _arcZxingLite.launch(intent);
        });
    };

}