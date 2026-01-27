package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.biometric.BiometricAuthActivity;
import com.huozige.lab.container.proxy.support.biometric.BiometricAuthUtils;

import java.util.HashMap;


public class BiometricAuthProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _authChooser;

    @Override
    public String getName() {
        return "biometric";
    }

    @JavascriptInterface
    public void biometricAuthAsync(String type, String ticket) {

        registryCallbackTicket(ticket);

        HashMap<String, String> map = new HashMap<>();
        map.put(BiometricAuthUtils.AUTH_TYPE_KEY, type);

        _authChooser.launch(createIntent(BiometricAuthActivity.class, map));

        // 记录日志
        writeInfoLog("开始认证");

        //firebase
    }

    @JavascriptInterface
    public void isAvailableAuth(String type, String ticket) {

        BiometricAuthUtils.IsAvailableAuthResult result = BiometricAuthUtils.isBiometricAvailable(getWebView().getContext(), type);

        callback(ticket, CallbackParams.success(String.valueOf(result.isAvailable), result.verifyMessage));

    }


    
    
    @Override
    public void onActivityCreated(AppCompatActivity activity) {

        _authChooser = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            Intent data = result.getData();
            
            if (data != null) {
                // 获取并判断返回码
                int code = result.getResultCode();

                if (code == BiometricAuthActivity.AUTH_STATUS_OK) {
                    
                    writeInfoLog("认证成功");
                    
                    callback(CallbackParams.success("认证成功"));
                } else {

                    String message = code == BiometricAuthActivity.AUTH_STATUS_FAIL? "认证失败" : "认证失败次数过多或认证超时";

                    writeErrorLog(message);

                    callback(CallbackParams.error(message));
                }


            } else {
                // 记录日志
                writeErrorLog("认证错误，返回了空值");

                // 重置单元格
                callback(CallbackParams.error("认证错误"));
            }
        });
    }
}
