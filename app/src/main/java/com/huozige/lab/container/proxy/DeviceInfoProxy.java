package com.huozige.lab.container.proxy;

import android.os.Build;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.utilities.DeviceUtility;

/**
 * 让页面能获取设备的唯一标识
 * 1.10.0
 * device.getSecureId()：获取当前设备的唯一标识
 * device.getDeviceInfo()：获取当前设备基础信息
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
        registryForFeatureUsageAnalyze("use_ssaid_feature", "getId");

        return DeviceUtility.getSSAID();
    }

    /**
     * 获取设备基础信息，返回JSON字符串。
     */
    @JavascriptInterface
    public String getDeviceInfo() {
        registryForFeatureUsageAnalyze("use_device_info_feature", "getDeviceInfo");

        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("secureId", DeviceUtility.getSSAID());
        deviceInfo.put("manufacturer", Build.MANUFACTURER);
        deviceInfo.put("brand", Build.BRAND);
        deviceInfo.put("model", Build.MODEL);
        deviceInfo.put("device", Build.DEVICE);
        deviceInfo.put("product", Build.PRODUCT);
        deviceInfo.put("systemName", "Android");
        deviceInfo.put("androidVersion", Build.VERSION.RELEASE);
        deviceInfo.put("androidSdk", Build.VERSION.SDK_INT);
        deviceInfo.put("packageName", DeviceUtility.getPackageName());
        deviceInfo.put("appVersionName", DeviceUtility.getPackageVersionName());
        deviceInfo.put("webViewVersionName", DeviceUtility.getWebViewVersionName());
        return deviceInfo.toJSONString();
    }

}
