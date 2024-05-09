package com.huozige.lab.container.utilities;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huozige.lab.container.HACApplication;

/**
 * Firebase事件的帮助类
 */
public class EventUtility {

    /**
     * 记录Firebase事件
     *
     * @param eventName 事件名
     * @param extra     事件的附加数据（字符串格式）
     */
    public static void logEvent(String eventName, String extra) {
        Bundle payload = new Bundle();
        payload.putString("extra", extra);
        FirebaseAnalytics.getInstance(HACApplication.getInstance()).logEvent(eventName, payload);
    }

}
