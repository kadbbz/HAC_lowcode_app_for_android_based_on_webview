package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.BroadcastDispatcher;
import com.huozige.lab.container.utilities.StringConvertUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * 提供操作广播的基础能力
 * 1.19.0
 * broadcast.send(action, type, dataUri, extraInJSON)：发送广播
 * broadcast.receive(action, ticket)：接收广播
 * broadcast.stopReceiving(handlerId)：停止接收广播
 */
public class BroadcastProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "broadcast";
    }

    /**
     * 发送广播
     *
     * @param action      广播的Action
     * @param type        广播的类型（如MIME）
     * @param dataUri     广播的Data（Uri格式）
     * @param extraInJSON JSON格式的ExtraData数组，用来存放Extra信息
     */
    @JavascriptInterface
    public void send(String action, String type, String dataUri, String extraInJSON) {

        registryForFeatureUsageAnalyze("use_broadcast_feature", "send");

        Intent intent = new Intent(action);

        if (type != null && !type.isEmpty()) {
            intent.setType(type);
        }

        if (dataUri != null && !dataUri.isEmpty()) {
            intent.setData(Uri.parse(dataUri));
        }

        writeInfoLog("准备发送广播：" + action + "|" + type + "|" + dataUri + "|" + extraInJSON);

        this.getWebView().getContext().sendBroadcast(ExtraData.fromJSON(intent, extraInJSON));

        writeInfoLog("广播已发送：" + action);
    }

    /**
     * 接收广播
     *
     * @param action 监听的Action
     * @param ticket 回调处理
     */
    @JavascriptInterface
    public void receive(String action, String ticket) {

        registryForFeatureUsageAnalyze("use_broadcast_feature", "receive");
        // 记录传入参数
        registryCallbackTicket(ticket);

        writeInfoLog("广播监听器已经启动，等待接收结果。广播：" + action);

        BroadcastDispatcher.getInstance().register(new BroadcastDispatcher.BroadcastHandler() {

            @Override
            public String getAction() {
                return action;
            }

            @Override
            public boolean handle(@androidx.annotation.Nullable Bundle extras) {

                var extraJSON = ExtraData.toJSON(extras);

                writeInfoLog("收到通用广播：" + action + "，监听器ID：" + getID().toString() + "，Extras：" + extraJSON);

                callback(CallbackParams.success(extraJSON, getID().toString()));

                return false;
            }
        });
    }

    /**
     * 停止接收广播
     *
     * @param handlerId 监听器ID
     */
    @JavascriptInterface
    public void stopReceiving(String handlerId) {

        writeInfoLog("广播监听器已经被停止：" + handlerId);
        BroadcastDispatcher.getInstance().unregister(UUID.fromString(handlerId));
    }

    /**
     * 存放EXTRA数据的数据结构
     */
    public static class ExtraData {

        public static final String TYPE_STRING = "string";
        public static final String TYPE_INT = "int";
        public static final String TYPE_FLOAT = "float";
        public static final String TYPE_BOOL = "bool";

        /**
         * 使用JSON填充Intent的Extras
         *
         * @param intent      传入的Intent
         * @param extraInJSON 包含Extras的JSON字符串
         * @return 带有Extra的Intent
         */
        public static Intent fromJSON(Intent intent, String extraInJSON) {

            if (JSON.isValid(extraInJSON)) {
                JSONArray extraArr = JSONArray.parse(extraInJSON);

                List<ExtraData> list = extraArr.toJavaList(ExtraData.class);

                for (ExtraData data : list
                ) {
                    switch (data.type) {
                        case ExtraData.TYPE_BOOL: {
                            boolean value = StringConvertUtility.stringToBoolean(data.value);
                            intent.putExtra(data.name, value);
                            break;
                        }
                        case ExtraData.TYPE_FLOAT: {
                            float value = Float.parseFloat(data.value);
                            intent.putExtra(data.name, value);
                            break;
                        }
                        case ExtraData.TYPE_INT: {
                            int value = Integer.parseInt(data.value);
                            intent.putExtra(data.name, value);
                            break;
                        }
                        default: {
                            intent.putExtra(data.name, data.value);
                            break;
                        }
                    }
                }
            }

            return intent;
        }

        /**
         * 将Extra序列化为JSON（ExtraData数组）
         *
         * @param extras Extras信息
         * @return JSON字符串
         */
        public static String toJSON(@Nullable Bundle extras) {
            ArrayList<ExtraData> data = new ArrayList<>();
            if (extras != null) {
                for (String key : extras.keySet()
                ) {
                    Object value = extras.get(key);
                    data.add(ExtraData.fromObject(key, value));
                }

                return JSON.toJSONString(data.toArray());
            } else {
                return "[]";
            }
        }

        public static ExtraData fromObject(String name, @Nullable Object value) {

            ExtraData result = new ExtraData();
            result.name = name;

            if (value != null) {
                if (value.getClass() == Integer.class) {
                    result.type = TYPE_INT;
                } else if (value.getClass() == Double.class || value.getClass() == Float.class) {
                    result.type = TYPE_FLOAT;
                } else if (value.getClass() == Boolean.class) {
                    result.type = TYPE_BOOL;
                } else {
                    result.type = TYPE_STRING;
                }

                result.value = value.toString();
            }

            return result;
        }

        /**
         * 传递过来的数据类型
         */
        public String type;

        /**
         * Extra Name
         */
        public String name;

        /**
         * 字符串类型的Extra value
         */
        public String value;
    }
}

