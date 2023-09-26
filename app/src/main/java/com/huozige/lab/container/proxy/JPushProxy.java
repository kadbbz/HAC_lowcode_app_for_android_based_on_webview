package com.huozige.lab.container.proxy;

import android.util.Log;
import android.webkit.JavascriptInterface;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的操作接口
 * 1.13.0
 * jpush.getRegistrationID 获取用于推送的RID
 */
public class JPushProxy extends AbstractProxy {

    static final String LOG_TAG = "HAC_JPushProxy";

    @Override
    public String getName() {
        return "jpush";
    }

    @JavascriptInterface
    public String getRegistrationID() {

        String rid = JPushInterface.getRegistrationID(this.getInterop().getActivityContext());

        Log.v(LOG_TAG, "RegistrationId was found: " + rid);
        return rid;
    }

}
