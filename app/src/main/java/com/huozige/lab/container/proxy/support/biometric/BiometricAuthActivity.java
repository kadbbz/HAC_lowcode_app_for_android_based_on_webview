package com.huozige.lab.container.proxy.support.biometric;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;

import java.util.Objects;
import java.util.concurrent.Executor;

public class BiometricAuthActivity extends BaseActivityNoActionBar {

    public final static int AUTH_STATUS_OK = 0;
    public final static int AUTH_STATUS_FAIL = 1;
    public final static int AUTH_STATUS_ERROR = 2;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    private int authType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);

        initFingerprintAuth();
        showFingerprintDialog();
    }


    private void initFingerprintAuth() {
        executor = ContextCompat.getMainExecutor(this);

        // 创建 BiometricPrompt 实例
        biometricPrompt = new BiometricPrompt(this,
                executor,
                new BiometricPrompt.AuthenticationCallback(){
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);

                        switch (errorCode) {
                            case BiometricPrompt.ERROR_CANCELED:
                                // 用户取消认证
                                break;
                            case BiometricPrompt.ERROR_LOCKOUT:
                                Toast.makeText(BiometricAuthActivity.this, "认证失败次数过多，请稍后重试", Toast.LENGTH_SHORT).show();
                                break;
                            case BiometricPrompt.ERROR_TIMEOUT:
                                Toast.makeText(BiometricAuthActivity.this, "认证超时", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(BiometricAuthActivity.this, "认证错误: " + errString, Toast.LENGTH_SHORT).show();
                                break;
                        }

                        BiometricAuthActivity.this.setResult(AUTH_STATUS_ERROR, new Intent());
                        BiometricAuthActivity.this.finish();
                    }

                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(BiometricAuthActivity.this, "认证成功", Toast.LENGTH_SHORT).show();

                        BiometricAuthActivity.this.setResult(AUTH_STATUS_OK, new Intent());
                        BiometricAuthActivity.this.finish();

                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(BiometricAuthActivity.this, "认证失败", Toast.LENGTH_SHORT).show();

                        BiometricAuthActivity.this.setResult(AUTH_STATUS_FAIL, new Intent());
                        //FingerprintActivity.this.finish();
                    }
                });

        // 构建认证对话框配置
        promptInfo = buildPromptinfo();
    }

    private void showFingerprintDialog() {
        if (BiometricAuthUtils.isBiometricAvailable(this, authType).isAvailable) {
            // 延迟显示对话框，确保 UI 准备就绪
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    biometricPrompt.authenticate(promptInfo);
                }
            }, 500);
        } else {
            finish();
        }
    }

    private BiometricPrompt.PromptInfo buildPromptinfo() {

        String type = getIntent().getStringExtra(BiometricAuthUtils.AUTH_TYPE_KEY);

        authType = BiometricAuthUtils.parseBiometricAuthType(type);


        if (Objects.equals(type, BiometricAuthUtils.DEVICE_CREDENTIAL)) {
            return new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("身份认证")
                    .setSubtitle("请验证您的指身份以继续")
                    .setConfirmationRequired(false)
                    .setAllowedAuthenticators(authType)
                    .build();
        }

        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("身份认证")
                .setSubtitle("请验证您的指身份以继续")
                .setNegativeButtonText("取消")
                .setConfirmationRequired(false)
                .setAllowedAuthenticators(authType)
                .build();
    }

}
