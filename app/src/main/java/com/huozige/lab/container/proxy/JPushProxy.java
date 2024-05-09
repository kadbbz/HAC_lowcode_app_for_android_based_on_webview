package com.huozige.lab.container.proxy;

import android.webkit.JavascriptInterface;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的操作接口
 * 1.13.0
 * jpush.getRegistrationID()：获取用于推送的RID
 */
public class JPushProxy extends AbstractProxy {


    @Override
    public String getName() {
        return "jpush";
    }

    @JavascriptInterface
    public String getRegistrationID() {

        String rid = JPushInterface.getRegistrationID(getWebView().getContext());

        writeInfoLog("获取到极光推送的RegistrationId: " + rid);
        return rid;
    }

}
