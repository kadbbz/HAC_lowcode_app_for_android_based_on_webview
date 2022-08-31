package com.huozige.lab.container.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.huozige.lab.container.R;

public class WaitForScannerBroadcastActivity extends AppCompatActivity {

    final static  int SCAN_STATUS_OK = 0;
    final static int SCAN_STATUS_CANCEL = -1;
    final  static  String BUNDLE_EXTRA_RESULT ="result";

    BroadcastReceiver _scanReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(getString(R.string.feature_scanner_broadcast_name))) {
                String result = intent.getStringExtra(getString(R.string.feature_scanner_extra_key_barcode_broadcast));

                Bundle res = new Bundle();
                res.putString(BUNDLE_EXTRA_RESULT,result);
                setResult(SCAN_STATUS_OK, BUNDLE_EXTRA_RESULT,res);
                finish();
            }
        }
    };

    View.OnClickListener _cancelButtonClick = view -> {
        Intent intentR =new Intent();
        setResult(SCAN_STATUS_CANCEL, intentR);
        finish();
    };

    //修改扫描工具参数：广播名、条码发送方式、声音、震动等
    private void initBoardCastSetting()
    {
        Intent intent = new Intent(getString(R.string.feature_scanner_intent_action));
        //修改广播名称：修改扫描工具广播名，接收广播时也是这个广播名
        intent.putExtra(getString(R.string.feature_scanner_extra_def_action_barcode_broadcast),getString(R.string.feature_scanner_broadcast_name));
        //条码发送方式：广播（不改）
        intent.putExtra(getString(R.string.feature_scanner_extra_def_barcode_send_mode),getString(R.string.feature_scanner_extra_barcode_send_mode_broadcast));
        //键值：东集默认scannerdata
        intent.putExtra(getString(R.string.feature_scanner_extra_def_key_barcode_broadcast),getString(R.string.feature_scanner_extra_key_barcode_broadcast));
        //声音：启用
        intent.putExtra(getString(R.string.feature_scanner_extra_def_sound_play),true);
        //震动：启用
        intent.putExtra(getString(R.string.feature_scanner_extra_def_viberate),true);

        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_bar_scanner_broadcast);

        setTitle(getString(R.string.feature_scanner_activity_title));
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);

        initBoardCastSetting();
    }

    @Override
    protected void onResume() {
        // 注册广播接收器
        IntentFilter intentFilter=new IntentFilter( getString(R.string.feature_scanner_broadcast_name) );
        intentFilter.setPriority( Integer.MAX_VALUE );
        registerReceiver( _scanReceiver,intentFilter );
        super.onResume();
    }

    @Override
    protected void onPause() {
        //取消广播注册
        unregisterReceiver(_scanReceiver);
        super.onPause();
    }

}