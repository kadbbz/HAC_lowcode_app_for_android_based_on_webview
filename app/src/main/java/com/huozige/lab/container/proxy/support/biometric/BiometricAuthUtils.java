package com.huozige.lab.container.proxy.support.biometric;

import android.content.Context;

import androidx.biometric.BiometricManager;

import java.util.Objects;

public class BiometricAuthUtils {

    public static String AUTH_TYPE_KEY = "AUTH_TYPE";
    public static String BIOMETRIC_STRONG = "BIOMETRIC_STRONG";
    public static String BIOMETRIC_WEAK = "BIOMETRIC_WEAK";
    public static String DEVICE_CREDENTIAL = "DEVICE_CREDENTIAL";

    /**
     * 检查设备是否支持
     */
    public static IsAvailableAuthResult isBiometricAvailable(Context context, int authType) {
        BiometricManager biometricManager = BiometricManager.from(context);
        IsAvailableAuthResult result = new IsAvailableAuthResult();

        int aa = biometricManager.canAuthenticate(authType);

        switch (biometricManager.canAuthenticate(authType)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                result.isAvailable = true;
                result.verifyMessage = "设备可用";
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                result.isAvailable = false;
                result.verifyMessage = "设备不支持";
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                result.isAvailable = false;
                result.verifyMessage = "设备不支持或硬件损坏";
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                result.isAvailable = false;
                result.verifyMessage = "设备支持，但未在设置中录入";
                break;
            default:
                result.isAvailable = false;
                result.verifyMessage = "未知故障";
                break;
        }

        return result;
    }

    public static IsAvailableAuthResult isBiometricAvailable(Context context, String authType) {
        return isBiometricAvailable(context, parseBiometricAuthType(authType));
    }

    public static int parseBiometricAuthType(String type) {

        if (Objects.equals(type, BIOMETRIC_STRONG)) {
            return BiometricManager.Authenticators.BIOMETRIC_STRONG;
        }

        if (Objects.equals(type, BIOMETRIC_WEAK)) {
            return BiometricManager.Authenticators.BIOMETRIC_WEAK;
        }

        if (Objects.equals(type, DEVICE_CREDENTIAL)) {
            return BiometricManager.Authenticators.DEVICE_CREDENTIAL;
        }

        return BiometricManager.Authenticators.BIOMETRIC_STRONG;
    }

    public static class IsAvailableAuthResult {
        public boolean isAvailable;
        public String verifyMessage;
    }
}
