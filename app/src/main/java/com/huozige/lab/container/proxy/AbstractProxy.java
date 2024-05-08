package com.huozige.lab.container.proxy;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.AbstractWebInterop;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.EventUtility;
import com.huozige.lab.container.utilities.PermissionsUtility;

/**
 * JavaScript桥的抽象类
 * 未来可以做更多功能，但暂时没有具体的实现
 *
 * @noinspection unused
 */
public abstract class AbstractProxy {

    private AbstractWebInterop interop; // HTML页面交互器
    private ConfigManager configManager; // 配置管理器

    /**
     * 获取JS桥注册到页面时使用的名称
     */
    public abstract String getName();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public void onActivityCreated(AppCompatActivity activity) {

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

    public ConfigManager getConfigManager() {
        return ConfigManager.getInstance();
    }

    private String __ticket, __payloadCell, __payload2Cell, __errorCell;

    /**
     * 注册接收处理结果的回调
     *
     * @param ticket 处理数据的回调
     */
    public void registryCallbackTicket(String ticket) {
        __ticket = ticket;
    }

    /**
     * 注册接收错误信息的单元格
     *
     * @param errorCell 接受错误信息的单元格
     */
    public void registryErrorCellLocation(String errorCell) {
        __errorCell = errorCell;
    }

    /**
     * 注册接收处理结果的单元格
     *
     * @param payloadCell 接受处理结果的单元格
     */
    public void registryPayloadCellLocation(String payloadCell) {
        registryPayloadCellLocation(payloadCell, null);
    }

    /**
     * 注册接收处理结果的单元格
     *
     * @param payloadCell  接受处理结果的单元格
     * @param payload2Cell 接受处理结果（备选）的单元格
     */
    public void registryPayloadCellLocation(String payloadCell, String payload2Cell) {
        __payloadCell = payloadCell;
        __payload2Cell = payload2Cell;
    }

    /**
     * 记录FB日志
     *
     * @param name  日志名字
     * @param extra 附加信息
     */
    public void logEvent(String name, String extra) {
        EventUtility.logEvent(getInterop().getActivityContext(), name, extra);
    }

    /**
     * 申请权限
     * @param permissions 需要申请的权限列表
     * @param successAction 申请成功后需要执行的操作
     */
    public void asyncRequirePermissions(String[] permissions, Runnable successAction){
        PermissionsUtility.asyncRequirePermissions(this.getInterop().getActivityContext(), permissions,successAction);
    }

    public void runOnUiThread(Runnable action){
        getInterop().getActivityContext().runOnUiThread(action);
    }

    /**
     * 将处理结果返回到WebView
     *
     * @param params 参数
     */
    public void callback(CallbackParams params) {

        getInterop().writeLogIntoConsole("Data from the Android native function: "
                + (params.isSuccess ? "success" : "error")
                + " | " + params.payload
                + " | " + params.error);

        if (params.isSuccess && __payloadCell != null && !__payloadCell.isEmpty()) {
            getInterop().setInputValue(__payloadCell, params.payload);
            getInterop().setInputValue(__errorCell, "");
        }

        if (params.isSuccess && __payload2Cell != null && !__payload2Cell.isEmpty()) {
            getInterop().setInputValue(__payload2Cell, params.payload2);
            getInterop().setInputValue(__errorCell, "");
        }


        if (!params.isSuccess && __errorCell != null && !__errorCell.isEmpty()) {
            getInterop().setInputValue(__payloadCell, "");
            getInterop().setInputValue(__errorCell, params.error);
        }

        if (__ticket != null && !__ticket.isEmpty()) {
            getInterop().callback(__ticket, params);
        }
    }

}
