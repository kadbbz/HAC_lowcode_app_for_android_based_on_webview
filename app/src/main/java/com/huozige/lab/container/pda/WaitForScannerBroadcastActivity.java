package com.huozige.lab.container.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.huozige.lab.container.R;

/**
 * 等待PDA扫码广播的页面，该页面支持用户自行取消
 */
public class WaitForScannerBroadcastActivity extends AppCompatActivity {

    final static int SCAN_STATUS_OK = 0;
    final static int SCAN_STATUS_CANCEL = -1;
    final static String BUNDLE_EXTRA_RESULT = "result";

    /**
     * 定义广播接收器
     * 支持扫码的PDA通常支持以广播的方式，将扫码的结果通知到各App
     * 广播的名称是通过内置应用来配置的，当用户按下扫码按钮时，该广播会以这个名称发出
     */
    BroadcastReceiver _scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 按照名称来过滤出需要处理的广播，再次确认。
            if (intent.getAction().equals(getString(R.string.feature_scanner_broadcast_name))) {

                // 按照厂商的文档，从广播中获取扫码结果
                String result = intent.getStringExtra(getString(R.string.feature_scanner_extra_key_barcode_broadcast));

                // 将其打包发给调用者
                Bundle res = new Bundle();
                res.putString(BUNDLE_EXTRA_RESULT, result);
                setResult(SCAN_STATUS_OK, BUNDLE_EXTRA_RESULT, res);

                // 关闭当前页面
                finish();
            }
        }
    };

    /**
     * 定义按钮的事件，处理用户主动取消
     */
    View.OnClickListener _cancelButtonClick = view -> {

        // 设置状态后，关闭当前页面
        Intent intentR = new Intent();
        setResult(SCAN_STATUS_CANCEL, intentR);
        finish();
    };

    /**
     * 页面初始化和扫码配置初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化界面
        setContentView(R.layout.activity_wait_for_bar_scanner_broadcast);
        setTitle(getString(R.string.feature_scanner_activity_title));
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);

        // 通过发送广播，配置扫码枪
        // 以下内容可能需要根据厂商文档进行调整
        Intent intent = new Intent(getString(R.string.feature_scanner_intent_action));
        //修改广播名称：修改扫描工具广播名，接收广播时也是这个广播名
        intent.putExtra(getString(R.string.feature_scanner_extra_def_action_barcode_broadcast), getString(R.string.feature_scanner_broadcast_name));
        //条码发送方式：广播（不改）
        intent.putExtra(getString(R.string.feature_scanner_extra_def_barcode_send_mode), getString(R.string.feature_scanner_extra_barcode_send_mode_broadcast));
        //键值：东集默认scannerdata
        intent.putExtra(getString(R.string.feature_scanner_extra_def_key_barcode_broadcast), getString(R.string.feature_scanner_extra_key_barcode_broadcast));
        //声音：启用
        intent.putExtra(getString(R.string.feature_scanner_extra_def_sound_play), true);
        //震动：启用
        intent.putExtra(getString(R.string.feature_scanner_extra_def_viberate), true);
        // 发送给扫码枪内置程序
        sendBroadcast(intent);
    }

    /**
     * 页面恢复时，启动监听
     */
    @Override
    protected void onResume() {

        // 按照名称来过滤出需要处理的广播
        IntentFilter intentFilter = new IntentFilter(getString(R.string.feature_scanner_broadcast_name));
        intentFilter.setPriority(Integer.MAX_VALUE);

        // 注册广播监听
        registerReceiver(_scanReceiver, intentFilter);

        super.onResume();
    }

    /**
     * 页面离开时，取消监听
     */
    @Override
    protected void onPause() {
        // 取消监听
        unregisterReceiver(_scanReceiver);

        super.onPause();
    }

}