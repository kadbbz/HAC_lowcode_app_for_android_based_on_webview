package com.huozige.lab.container;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * 接收配置变更的消息
 */
public class ConfigBroadcastReceiver extends BroadcastReceiver {

    static final String CONFIG_BROADCAST_EXTRA_ENTRY = "entry";
    static final String LOG_TAG = "ConfigBroadcastReceiver";
    static final  String CHANNEL_ID="hac_push";
    static final  Integer CONFIG_RELOAD_NITIFICATION=1001;

    Activity _activityContext;

    public ConfigBroadcastReceiver(Activity activity){
        super();

        _activityContext = activity;

        Log.v(LOG_TAG, "广播接收已创建" );
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(LOG_TAG, "接收到广播：" + intent.getAction());
        // 读取入口消息
        String entry = intent.getStringExtra(CONFIG_BROADCAST_EXTRA_ENTRY);

        Log.v(LOG_TAG, "广播中接入点变更为：" + entry);

        if (null != entry) {

            ConfigHelpers.UpsertEntryUrl(_activityContext, entry);

            Log.v(LOG_TAG, "接入点已保存至配置数据库。");

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = context.getString(R.string.ui_notification_channel_config_changed);
            String description = context.getString(R.string.ui_notification_config_changed);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager nmforChannel = context.getSystemService(NotificationManager.class);
            nmforChannel.createNotificationChannel(channel);

            // Create an explicit intent for an Activity in your app
            Intent intent2 = new Intent(context, MainActivity.class);
            intent2.putExtra(MainActivity.INTENT_EXTRA_IS_FORCE_RELOAD,true); // 要求首页自动刷新
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT); // 允许静音，多条推送使用更新模式

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.loading)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(context.getString(R.string.ui_notification_config_changed))
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(context);

            // notificationId is a unique int for each notification that you must define
            notificationManager2.notify(CONFIG_RELOAD_NITIFICATION, builder.build());

            Log.v(LOG_TAG, "已推送通知提醒。");
        }
    }
}