package com.huozige.lab.container.proxy;

import android.annotation.SuppressLint;
import android.provider.Settings;
import com.elvishew.xlog.XLog;
import android.webkit.JavascriptInterface;

/**
 * 让页面能获取设备的唯一标识
 * 1.10.0
 * device.getSecureId()：获取当前设备的唯一标识
 */
public class DeviceInfoProxy extends AbstractProxy {

    static final String LOG_TAG = "HAC_DeviceInfoProxy";

    @Override
    public String getName() {
        return "device";
    }

    /**
     * 获取设备安全ID，形式为：8634a46c0a38052e
     */
    @JavascriptInterface
    public String getSecureId() {

        // 采用SSAID作为唯一标识，不再使用Mac：https://developer.android.com/training/articles/user-data-ids?hl=zh-cn#mac-11-plus
        @SuppressLint("HardwareIds") String id = Settings.Secure.getString(this.getInterop().getActivityContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        XLog.v(LOG_TAG,"SSAID: " + id);

       return id;
    }

}
