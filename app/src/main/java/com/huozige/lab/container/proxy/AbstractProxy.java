package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
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

    private AbstractWebInterop _interop; // HTML页面交互器
    private ConfigManager _configManager; // 配置管理器

    private String _ticket, _payloadCell, _payload2Cell, _errorCell;

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
        this._interop = interop;
    }

    private AbstractWebInterop getInterop() throws IllegalStateException {
        if (null == _interop) {
            throw new IllegalStateException("Interop has not be initialized.");
        }
        return _interop;
    }

    protected ConfigManager getConfigManager() {
        return ConfigManager.getInstance();
    }

    /**
     * 注册接收处理结果的回调
     *
     * @param ticket 处理数据的回调
     */
    protected void registryCallbackTicket(String ticket) {
        _ticket = ticket;
    }

    /**
     * 注册接收错误信息的单元格
     *
     * @param errorCell 接受错误信息的单元格
     */
    protected void registryErrorCellLocation(String errorCell) {
        _errorCell = errorCell;
    }

    /**
     * 注册接收处理结果的单元格
     *
     * @param payloadCell 接受处理结果的单元格
     */
    protected void registryPayloadCellLocation(String payloadCell) {
        registryPayloadCellLocation(payloadCell, null);
    }

    /**
     * 注册接收处理结果的单元格
     *
     * @param payloadCell  接受处理结果的单元格
     * @param payload2Cell 接受处理结果（备选）的单元格
     */
    protected void registryPayloadCellLocation(String payloadCell, String payload2Cell) {
        _payloadCell = payloadCell;
        _payload2Cell = payload2Cell;
    }

    /**
     * 申请权限
     *
     * @param permissions   需要申请的权限列表
     * @param successAction 申请成功后需要执行的操作
     */
    protected void asyncRequirePermissions(String[] permissions, Runnable successAction) {
        PermissionsUtility.asyncRequirePermissions(this.getInterop().getActivityContext(), permissions, successAction);
    }

    /**
     * 调度回UI线程执行后续操作
     *
     * @param action 需要执行的操作
     */
    protected void runOnUiThread(Runnable action) {
        getInterop().getActivityContext().runOnUiThread(action);
    }


    protected void showLongToast(String message) {
        getInterop().showToast(message);
    }

    /**
     * 获取当前WebView
     *
     * @return 上下文
     */
    protected WebView getWebView() {
        return getInterop().getWebView();
    }

    /**
     * 弹出页面
     *
     * @param intent 参数
     */
    protected void startActivity(Intent intent) {
        this.getInterop().getActivityContext().startActivity(intent);
    }

    /**
     * 弹出页面
     *
     * @param targetActivityClass 目标页面的类型
     */
    protected void startActivity(Class<?> targetActivityClass) {
        this.getInterop().getActivityContext().startActivity(createIntent(targetActivityClass));
    }

    /**
     * 生成页面跳转用的Intent
     *
     * @param targetActivityClass 目标页面的类型
     * @return 到该页面的intent
     */
    protected Intent createIntent(Class<?> targetActivityClass) {
        return new Intent(this.getInterop().getActivityContext(), targetActivityClass);
    }

    /**
     * 将处理结果返回到WebView
     * 采用标准回调的方式来完成，支持写入单元格和回调函数
     *
     * @param params 参数
     */
    protected void callback(CallbackParams params) {

        if (_payloadCell != null && !_payloadCell.isEmpty()) {
            getInterop().setInputValue(_payloadCell, params.isSuccess ? params.payload : "");
        }

        if (params.isSuccess && _payload2Cell != null && !_payload2Cell.isEmpty()) {
            getInterop().setInputValue(_payload2Cell, params.isSuccess ? params.payload2 : "");
        }

        if (!params.isSuccess && _errorCell != null && !_errorCell.isEmpty()) {
            getInterop().setInputValue(_errorCell, !params.isSuccess ? params.error : "");
        }

        if (_ticket != null && !_ticket.isEmpty()) {
            getInterop().callback(_ticket, params);
        }
    }

    /**
     * 将处理结果返回到WebView
     * 仅支持派发到指定的异步回调
     *
     * @param params 参数
     * @param ticket 异步回调的标识
     */
    protected void callback(String ticket, CallbackParams params) {
        getInterop().callback(ticket, params);
    }

    /**
     * 将处理结果返回到WebView
     * 采用简化方式完成，仅支持写入单元格
     *
     * @param cellLocation 单元格名称
     * @param value        需要写入的值
     */
    protected void callback(String cellLocation, String value) {
        getInterop().setInputValue(cellLocation, value);
    }

    /**
     * 写入WebView控制台的普通日志，诊断使用
     *
     * @param log 日志内容
     */
    protected void writeInfoLog(String log) {

        // 先记录到APP端
        XLog.v(log);

        // 再记录到WebView的Console中
        getInterop().writeLogIntoConsole(log);
    }

    /**
     * 写入WebView控制台的错误日志，调查使用
     *
     * @param error 错误日志内容
     */
    protected void writeErrorLog(String error) {

        // 先记录到APP端
        XLog.e(error);

        // 再记录到WebView的Error Console中
        getInterop().writeErrorLogIntoConsole(error);
    }

    /**
     * 记录Firebase日志，用于分析功能用量
     *
     * @param featureName 功能名字
     * @param extra       操作方法
     */
    protected void registryForFeatureUsageAnalyze(String featureName, String extra) {
        EventUtility.logEvent(featureName, extra);
    }

}
