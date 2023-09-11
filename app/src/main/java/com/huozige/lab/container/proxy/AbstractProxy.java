package com.huozige.lab.container.proxy;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.huozige.lab.container.platform.AbstractWebInterop;
import com.huozige.lab.container.utilities.ConfigManager;

/**
 * JavaScript桥的抽象类
 * 未来可以做更多功能，但暂时没有具体的实现
 * @noinspection ALL
 */
public abstract class AbstractProxy {

    private AbstractWebInterop interop; // HTML页面交互器
    private ConfigManager configManager; // 配置管理器

    /**
     * 获取JS桥注册到页面时使用的名称
     */
    public abstract String getName();

    /**
     * 需要注册到应用的初始化操作
     * 此时不能调用getInterop、getConfigManager等与WebView交互的方法
     */
    public void onApplicationCreated(Application app) {

    }

    /**
     * 需要注册到上下文中的初始化操作
     */
    public void onActivityCreated(){

    }

    /**
     * 需要注册到上下文中的初始化操作
     */
    public void beforeActivityPause() {

    }

    /**
     * 需要注册到上下文中的初始化操作
     */
    public void onActivityResumed() {

    }

    /**
     * 需要注册到上下文中的初始化操作
     */
    public void onActivityDestroy() {

    }

    /**
     * 处理上下文转发来的Activity调用返回结果
     *
     * @param requestCode 同onActivityResult
     * @param resultCode  同onActivityResult
     * @param data        同onActivityResult
     * @return 返回true意味着不再调用后续的处理器
     */
    public Boolean processActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    public void setInterop(AbstractWebInterop interop) {
        this.interop = interop;
    }

    public AbstractWebInterop getInterop() throws IllegalStateException {
        if (null == interop) {
            throw new IllegalStateException("Interop has not be initialized.");
        }
        return interop;
    }

    public ConfigManager getConfigManager() throws IllegalStateException {

        if (null == configManager) {
            throw new IllegalStateException ("ConfigManager has not be initialized.");
        }
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void showToast(String message) {
        Toast.makeText(getInterop().getActivityContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showToast(int resId) {
        showToast(getInterop().getActivityContext().getString(resId));
    }
}
