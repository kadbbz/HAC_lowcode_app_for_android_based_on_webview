package com.huozige.lab.container.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.MainActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        XLog.v("BootOnReceive" + intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

            Intent currentIntent = new Intent(context, MainActivity.class);
            currentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(currentIntent);
            XLog.v("自启动成功");
        }
    }
}
