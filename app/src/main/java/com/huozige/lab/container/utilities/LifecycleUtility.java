package com.huozige.lab.container.utilities;

import android.content.Context;
import android.content.Intent;

/**
 * 生命周期相关帮助类
 */
public class LifecycleUtility {

    /**
     * 重启APP
     * @param context 执行操作的上下文
     */
    public static void restart(Context context){
        Intent intentR = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intentR);
    }
}
