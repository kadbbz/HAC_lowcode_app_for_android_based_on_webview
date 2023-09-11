package com.huozige.lab.container;

import android.app.Application;
import android.util.Log;

import com.huozige.lab.container.proxy.AbstractProxy;
import com.huozige.lab.container.proxy.ProxyRegister;

/**
 * 应用的起点
 */
public class HACApplication extends Application {

    final static String LOG_TAG = "HACApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(LOG_TAG, ">>>>>>> 应用启动 <<<<<<<");

        HACCrashHandler handler = new HACCrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);

        Log.v(LOG_TAG, "全局异常处理挂载完成");

        // APP创建的时机
        for (AbstractProxy proxy : ProxyRegister.getInstance().getAllProxies()) {
            proxy.onApplicationCreated(this);
        }

        Log.v(LOG_TAG, "JS代理初始化完成");
    }
}
