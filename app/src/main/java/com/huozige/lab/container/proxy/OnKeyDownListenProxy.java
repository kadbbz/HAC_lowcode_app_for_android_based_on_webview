package com.huozige.lab.container.proxy;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.huozige.lab.container.MainActivity;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.BroadcastDispatcher;
import com.huozige.lab.container.utilities.ConfigManager;

import java.util.Objects;

public class OnKeyDownListenProxy extends AbstractProxy {

    @Override
    public String getName() {
        return "onKeyDownListen";
    }

    @JavascriptInterface
    public void startOnKeyDownListenAsync(String ticket, String theKey) {

        registryForFeatureUsageAnalyze("listen_keydown_feature", "onKeyDownAsync");

        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_ON_KEY_DOWN_LISTEN, getConfigManager().getOnKeyDownListen() + theKey + ",");

        startReceiver(theKey, ticket);
    }

    @JavascriptInterface
    public void stopOnKeyDownListen(String theKey) {

        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_ON_KEY_DOWN_LISTEN, getConfigManager().getOnKeyDownListen().replace("," + theKey + ",", ","));

        stopReceiver(theKey);
    }

    @JavascriptInterface
    public void stopAllOnKeyDownListen() {

        String[] currentListen = getConfigManager().getOnKeyDownListen().split(",");

        for (String key : currentListen) {
            if (!Objects.equals(key, "")) {
                stopReceiver(key);
            }
        }

        getConfigManager().upsertStringEntry(ConfigManager.PREFERENCE_KEY_ON_KEY_DOWN_LISTEN, ",");
    }

    private void startReceiver(String theKey, String ticket) {

        BroadcastDispatcher.BroadcastHandler handler = new BroadcastDispatcher.BroadcastHandler() {

            @Override
            public String getAction() {

                return MainActivity.ON_KRY_DOWN_ACTION_STRING + theKey;
            }

            @Override
            public boolean handle(@Nullable Bundle extras) {

                // 调用注册的回调，不要自动派发的方式，需要显式设置回调的标识
                callback(ticket, CallbackParams.success(this.getAction()));

                return false;
            }
        };

        // 注册广播监听
        BroadcastDispatcher.getInstance().register(handler);

        // 记录日志
        writeInfoLog("物理按键" + MainActivity.ON_KRY_DOWN_ACTION_STRING + theKey + "监听已启动");
    }

    private void stopReceiver(String theKey) {

        BroadcastDispatcher.getInstance().unregister(MainActivity.ON_KRY_DOWN_ACTION_STRING + theKey);
        // 记录日志
        writeInfoLog(MainActivity.ON_KRY_DOWN_ACTION_STRING + theKey + "广播接收器已停止");
    }
}
