package com.huozige.lab.container;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 应用的起点
 */
public class HACApplication extends Application {

    final static String LOG_TAG="HACApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(LOG_TAG,">>>>>>> 应用启动 <<<<<<<");

        HACCrashHandler handler = new HACCrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);

        Log.v(LOG_TAG,"全局异常处理挂载完成");

        // 初始化Realm
        Realm.init(this);

        // 升级数据结构时，丢弃原有数据
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Log.v(LOG_TAG,"Realm初始化完成");
    }
}
