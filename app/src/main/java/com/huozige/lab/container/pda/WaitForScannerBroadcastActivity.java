package com.huozige.lab.container.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.huozige.lab.container.ConfigManager;
import com.huozige.lab.container.R;

/**
 * 等待PDA扫码广播的页面，该页面支持用户自行取消
 */
public class WaitForScannerBroadcastActivity extends AppCompatActivity {

    final static int SCAN_STATUS_OK = 0;
    final static int SCAN_STATUS_CANCEL = -1;
    final static String BUNDLE_EXTRA_RESULT = "result";
    final static String LOG_TAG = "WaitForScannerBroadcastActivity";

    ConfigManager _cm = new ConfigManager(this);

    /**
     * 定义广播接收器
     * 支持扫码的PDA通常支持以广播的方式，将扫码的结果通知到各App
     * 广播的名称是通过内置应用来配置的，当用户按下扫码按钮时，该广播会以这个名称发出
     */
    BroadcastReceiver _scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v(LOG_TAG, "收到单次扫码结果的广播");

            // 按照厂商的文档，从广播中获取扫码结果
            String result = intent.getStringExtra( (null == _cm.GetScanExtra())? getString( R.string.feature_scanner_extra_key_barcode_broadcast):_cm.GetScanExtra());

            Log.v(LOG_TAG, "扫码结果是：" + result);

            // 将其打包发给调用者
            Intent res = new Intent();
            res.putExtra(BUNDLE_EXTRA_RESULT, result);
            WaitForScannerBroadcastActivity.this.setResult(SCAN_STATUS_OK, res);

            // 关闭当前页面
            WaitForScannerBroadcastActivity.this.finish();
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
        setTitle(getString(R.string.ui_title_scanner_activity));
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);
    }

    /**
     * 页面恢复时，启动监听
     */
    @Override
    protected void onResume() {

        super.onResume();

        String intentF = (_cm.GetScanAction() == null)?getString(R.string.feature_scanner_broadcast_name):_cm.GetScanAction();

        // 按照名称来过滤出需要处理的广播
        IntentFilter intentFilter = new IntentFilter(intentF);
        intentFilter.setPriority(Integer.MAX_VALUE);

        // 注册广播监听
        registerReceiver(_scanReceiver, intentFilter);

        Log.v(LOG_TAG, "扫码结果广播已注册");
    }

    /**
     * 页面离开时，取消监听
     */
    @Override
    protected void onPause() {
        // 取消监听
        unregisterReceiver(_scanReceiver);

        Log.v(LOG_TAG, "已取消注册扫码结果广播");

        super.onPause();
    }

}