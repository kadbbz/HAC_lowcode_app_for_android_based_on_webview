package com.huozige.lab.container;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 接收配置变更的消息
 */
public class ConfigBroadcastReceiver extends BroadcastReceiver {

    static final String CONFIG_BROADCAST_FILTER = "com.android.server.hac";
    static final String CONFIG_BROADCAST_EXTRA_ENTRY = "entry";

    @Override
    public void onReceive(Context context, Intent intent) {

        // 按照名称来过滤出需要处理的广播，再次确认。
        if (intent.getAction().equals(CONFIG_BROADCAST_FILTER)) {

            // 读取入口消息
            String entry = intent.getStringExtra(CONFIG_BROADCAST_EXTRA_ENTRY);

            if (null != entry && !entry.equals("")) {

                // 打开配置库
                SharedPreferences sharedPref = context.getSharedPreferences(
                        MainActivity.PREFERENCE_NAME, Activity.MODE_PRIVATE);

                // 保存到配置库
                sharedPref.edit().putString(MainActivity.PREFERENCE_KEY_ENTRY, entry).apply();
            }
        }
    }
}