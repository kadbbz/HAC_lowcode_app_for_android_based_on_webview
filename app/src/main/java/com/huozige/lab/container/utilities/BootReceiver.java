package com.huozige.lab.container.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huozige.lab.container.MainActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

            Intent currentIntent = new Intent(context, MainActivity.class);
            currentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(currentIntent);
        }
    }
}
