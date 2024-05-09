package com.huozige.lab.container.utilities;

import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.HACApplication;

/**
 * 生命周期相关帮助类
 */
public class LifecycleUtility {


    /**
     * 重启APP
     */
    public static void restart() {

        XLog.w("调用重启应用的方法");

        Intent intentR = HACApplication.getInstance().getPackageManager().getLaunchIntentForPackage(HACApplication.getInstance().getPackageName());
        if(intentR!=null){
            intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            HACApplication.getInstance().startActivity(intentR);
        }
    }

    /**
     * 关闭app
     */
    public static void close() {

        XLog.w("调用关闭应用的方法");

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
