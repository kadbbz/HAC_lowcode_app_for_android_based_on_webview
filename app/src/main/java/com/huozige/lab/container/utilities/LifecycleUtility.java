package com.huozige.lab.container.utilities;

import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;

/**
 * 生命周期相关帮助类
 */
public class LifecycleUtility {


    /**
     * 重启APP
     *
     * @param context 执行操作的上下文
     */
    public static void restart(Context context) {

        XLog.v("调用重启应用的方法");

        Intent intentR = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intentR);
    }

    /**
     * 关闭app
     */
    public static void close() {

        XLog.v("调用关闭应用的方法");

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
