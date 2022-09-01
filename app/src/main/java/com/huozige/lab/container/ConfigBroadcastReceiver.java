package com.huozige.lab.container;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 接收配置变更的消息
 */
public class ConfigBroadcastReceiver extends BroadcastReceiver {
    static final String CONFIG_BROADCAST_EXTRA_ENTRY = "entry";
    static final String LOG_TAG = "ConfigBroadcastReceiver";

    public ConfigBroadcastReceiver(){
        super();
        Log.v(LOG_TAG, "广播接收已创建" );
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(LOG_TAG, "接收到广播：" + intent.getAction());
        // 读取入口消息
        String entry = intent.getStringExtra(CONFIG_BROADCAST_EXTRA_ENTRY);

        Log.v(LOG_TAG, "广播中接入点变更为：" + entry);

        if (null != entry && !entry.equals("")) {

            // 打开配置库
            SharedPreferences sharedPref = context.getSharedPreferences(
                    MainActivity.PREFERENCE_NAME, Activity.MODE_PRIVATE);

            // 保存到配置库
            sharedPref.edit().putString(MainActivity.PREFERENCE_KEY_ENTRY, entry).apply();


            Log.v(LOG_TAG, "接入点已保存至配置数据库。");

        }
    }
}