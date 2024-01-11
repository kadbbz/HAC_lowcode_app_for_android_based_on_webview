package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import com.elvishew.xlog.XLog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import com.hjq.permissions.Permission;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

/**
 * 通过二维码完成快速配置
 */
public class QuickConfigActivity extends BaseActivity {

    static final String LOG_TAG = "HAC_QuickConfigActivity";

    ActivityResultLauncher<Intent> _arc4TCA; // 用来弹出配置页面。

    // 创建到ZXingLite的调用器
    ActivityResultLauncher<Intent> _arcZxingLite = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
        Intent data = result.getData();

        if (null != data) {
            String json = CameraScan.parseScanResult(data);

            XLog.v(LOG_TAG,"Load config from QRCode : "+json);

            AlertDialog.Builder ab = new AlertDialog.Builder(QuickConfigActivity.this);
            ab.setPositiveButton(QuickConfigActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

                // 执行配置过程
                Boolean isOk= ConfigManager.getInstance().quickConfig(json);

                if(isOk){

                    XLog.v(LOG_TAG,"QRCode config applied.");

                    // 提示正确的信息
                    Toast.makeText(QuickConfigActivity.this, getString(R.string.ui_message_quick_config_done), Toast.LENGTH_LONG).show();

                    // 重启生效
                    restart();
                }else{

                    XLog.v(LOG_TAG,"QRCode config is broken.");

                    // 仅提示错误信息
                    Toast.makeText(QuickConfigActivity.this, getString(R.string.ui_message_quick_config_broken), Toast.LENGTH_LONG).show();
                }

            }).setNegativeButton(QuickConfigActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
                // 什么都不干
            });

            ab.setMessage(R.string.ui_message_quick_config_confirm);
            ab.setTitle(R.string.ui_menu_settings);
            ab.show();
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

        _arc4TCA =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> restart()); // 打开设置页面，返回后刷新浏览器
    }

    View.OnClickListener gotoTextConfig = view -> _arc4TCA.launch(new Intent(this,TextConfigActivity.class));

    View.OnClickListener scanForConfig = view -> {

        // 申请摄像头权限，然后开始扫码
        PermissionsUtility.asyncRequirePermissions(QuickConfigActivity.this, new String[]{
                Permission.CAMERA,
                Permission.NOTIFICATION_SERVICE
        },()->{
            // 调用ZXingLite的扫码页面
            _arcZxingLite.launch(new Intent(QuickConfigActivity.this, CaptureActivity.class));
        });
    };

    void restart(){
        Intent intentR = getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        if(intentR != null)
        {
            intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentR);
        }
    }
}