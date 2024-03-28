package com.huozige.lab.container.proxy;

import com.huozige.lab.container.utilities.EventUtility;
import com.huozige.lab.container.utilities.MiscUtilities;

import android.webkit.JavascriptInterface;

/**
 * 让页面能获取设备的唯一标识
 * 1.10.0
 * device.getSecureId()：获取当前设备的唯一标识
 */
public class DeviceInfoProxy extends AbstractProxy {

    @Override
    public String getName() {
        return "device";
    }

    /**
     * 获取设备安全ID，形式为：8634a46c0a38052e
     */
    @JavascriptInterface
    public String getSecureId() {
        logEvent("use_ssaid_feature", "getId");

        return MiscUtilities.getSSAID(this.getInterop().getActivityContext());
    }

}
