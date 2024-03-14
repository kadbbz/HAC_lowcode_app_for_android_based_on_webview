package com.huozige.lab.container.utilities;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Firebase事件的帮助类
 */
public class EventUtility {

    /**
     * 记录Firebase事件，没有附加数据
     *
     * @param context   上下文
     * @param eventName 事件名
     */
    public static void logEvent(Context context, String eventName) {
        FirebaseAnalytics.getInstance(context).logEvent(eventName, null);
    }

    /**
     * 记录Firebase事件
     *
     * @param context   上下文
     * @param eventName 事件名
     * @param extra     事件的附加数据（字符串格式）
     */
    public static void logEvent(Context context, String eventName, String extra) {
        Bundle payload = new Bundle();
        payload.putString("extra", extra);
        FirebaseAnalytics.getInstance(context).logEvent(eventName, payload);
    }

}
