package com.huozige.lab.container.proxy;


import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.scanner.PDAProxy_SingleScanActivity;
import com.huozige.lab.container.utilities.BroadcastDispatcher;
import com.huozige.lab.container.utilities.ConfigManager;

import java.util.ArrayList;

/**
 * 让页面具备操作扫码枪硬件的能力
 * 1.0.0
 * pda.modal_scan(cell): 带模态窗口的单次扫码
 * pda.continuous_scan(cell): 开始持续扫码
 * pda.continuous_scan_stop()： 停止持续扫码
 * 1.17.0
 * pda.modal_scanAsync(ticket): 带模态窗口的单次扫码
 * pda.continuous_scanAsync(ticket): 开始持续扫码
 */
public class PDAProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _arcScanner; // 用来弹出Broadcast模式扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    Boolean _continueScanOn = false;

    ArrayList<String> _resultCache = new ArrayList<>();
    Integer continueScanLimit;

    /**
     * 注册的名称为：pda
     */
    @Override
    public String getName() {
        return "pda";
    }

    /**
     * 初始化过程：创建调用器
     */
    @Override
    public void onActivityCreated(AppCompatActivity activity) {

        // 创建Broadcast模式扫码页面
        _arcScanner = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 获取页面返回的结果
            Intent data = result.getData();

            if (null != data) {
                // 获取并判断返回码
                int code = result.getResultCode();
                if (code == PDAProxy_SingleScanActivity.SCAN_STATUS_OK) {

                    // 成功接收到返回的扫码结果
                    String resultS = data.getStringExtra(PDAProxy_SingleScanActivity.BUNDLE_EXTRA_RESULT);

                    // 记录日志
                    writeInfoLog("扫描头的返回结果为：" + resultS);

                    // 将结果写入单元格
                    callback(CallbackParams.success(resultS));
                } else {
                    // 记录日志
                    writeErrorLog("扫描头操作出错，错误码为：" + code);

                    // 重置单元格
                    callback(CallbackParams.error("PDA scan canceled or failed. Return code is : " + code));
                }
            } else {
                // 记录日志
                writeErrorLog("扫描头操作出错，返回了空值");

                // 重置单元格
                callback(CallbackParams.error("PDA scan failed."));
            }
        });
    }

    /**
     * 停止监听器
     */
    @Override
    public void beforeActivityPause() {

        stopReceiver();
    }

    /**
     * 如果处在持续扫描状态，恢复监听器
     */
    @Override
    public void onActivityResumed() {
        if (_continueScanOn) {
            startReceiver();
        }
    }

    /**
     * 注册到页面的pda.modal_scan(cell)方法
     * 单次扫码
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void modal_scan(String cellLocation) {
        registryForFeatureUsageAnalyze("use_scanner_feature", "modalScan");

        registryPayloadCellLocation(cellLocation);

        // 记录日志
        writeInfoLog("单次扫码已启动，等待接收扫描结果。广播：" + getConfigManager().getScanAction() + "，键值：" + getConfigManager().getScanExtra());

        // 调用Broadcast模式扫码页面
        _arcScanner.launch(createIntent(PDAProxy_SingleScanActivity.class));
    }

    /**
     * 注册到页面的pda.modal_scan(cell)方法
     * 单次扫码
     *
     * @param ticket 回调
     */
    @JavascriptInterface
    public void modal_scanAsync(String ticket) {
        registryForFeatureUsageAnalyze("use_scanner_feature", "modalScanAsync");

        registryCallbackTicket(ticket);

        // 记录日志
        writeInfoLog("异步单次扫码已启动，等待接收扫描结果。广播：" + getConfigManager().getScanAction() + "，键值：" + getConfigManager().getScanExtra());

        // 调用Broadcast模式扫码页面
        _arcScanner.launch(createIntent(PDAProxy_SingleScanActivity.class));
    }

    /**
     * 注册到页面的pda.continuous_scan(cell)方法
     * 开始连续扫码
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void continuous_scan(String cellLocation, String limit) {
        registryForFeatureUsageAnalyze("use_scanner_feature", "continuousScan");

        writeInfoLog("连续扫码已启动，等待接收扫描结果，接收上限为：" + limit + "。广播：" + getConfigManager().getScanAction() + "，键值：" + getConfigManager().getScanExtra());

        // 记录传入参数
        registryPayloadCellLocation(cellLocation);
        continueScanLimit = (null == limit || Integer.decode(limit) <= 0) ? Integer.MAX_VALUE : Integer.decode(limit); // 传入0或者复数，则不限制最大次数

        // 清空缓存，仅在启动扫描时重置
        _continueScanOn = true;
        _resultCache.clear();

        // 开始监听
        startReceiver();
    }

    /**
     * 注册到页面的pda.continuous_scan(cell)方法
     * 开始连续扫码
     *
     * @param ticket 回调
     */
    @JavascriptInterface
    public void continuous_scanAsync(String ticket, String limit) {
        registryForFeatureUsageAnalyze("use_scanner_feature", "continuousScan");

        writeInfoLog("异步连续扫码已启动，等待接收扫描结果，接收上限为：" + limit + "。广播：" + getConfigManager().getScanAction() + "，键值：" + getConfigManager().getScanExtra());

        // 记录传入参数
        registryCallbackTicket(ticket);
        continueScanLimit = (null == limit || Integer.decode(limit) <= 0) ? Integer.MAX_VALUE : Integer.decode(limit); // 传入0或者复数，则不限制最大次数

        // 清空缓存，仅在启动扫描时重置
        _continueScanOn = true;
        _resultCache.clear();

        // 开始监听
        startReceiver();
    }

    /**
     * 注册到页面的pda.continuous_scan_stop()方法
     * 停止连续扫码
     */
    @JavascriptInterface
    public void continuous_scan_stop() {
        stopReceiver();
    }

    /**
     * 启动监听
     */
    private void startReceiver() {

        BroadcastDispatcher.BroadcastHandler handler = new BroadcastDispatcher.BroadcastHandler() {
            @Override
            public String getAction() {
                return getConfigManager().getScanAction();
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean handle(@Nullable Bundle extras) {

                writeInfoLog("收到激光头扫码结果的广播");

                if (extras != null) {

                    // 获取键值
                    String extraString = getConfigManager().getScanExtra();

                    //按照厂商的文档，从广播中获取扫码结果
                    String result = extras.getString(extraString);

                    // 当获取不到扫码结果时，尝试使用byte array的方式获取结果
                    if (result == null) {
                        byte[] byteArrayResult = extras.getByteArray(extraString);
                        result = byteArrayResult == null || byteArrayResult.length == 0 ? "" : new String(byteArrayResult);
                    }

                    // 需要剔除每次扫码结果后的回车按键
                    result = result.replace("\n", "");
                    result = result.replace("\r", "");

                    writeInfoLog("当次扫码结果是：" + result);

                    if (_continueScanOn && !result.isEmpty()) {

                        // 将当次扫描结果拼接到累计结果上
                        _resultCache.add(result);

                        // 返回到页面，分割符为半角逗号，与活字格的内置数组保持一致
                        String rc = String.join(",", _resultCache);

                        // 记录日志
                        writeInfoLog("当前处在连续扫描模式，收到了新的扫描结果，当次结果为：" + result + "，总体结果为：" + rc);

                        // 输出
                        callback(CallbackParams.success(rc));

                        if (_resultCache.size() >= continueScanLimit) {

                            stopReceiver();

                            // 记录日志
                            writeInfoLog("当前处在连续扫描模式，已经超过了预设的扫描次数上限（" + continueScanLimit + "），停止接受扫描结果");

                        }

                    } else {

                        // 预期外场景需要记录日志
                        writeErrorLog("应用运行异常，当前没有处在持续扫描模式，但监听器仍在运行。");
                    }
                }

                return false;
            }
        };

        // 注册广播监听
        BroadcastDispatcher.getInstance().register(handler);

        // 记录日志
        writeInfoLog("扫描头广播接收器已启动。广播：" + getConfigManager().getScanAction() + "，键值：" + getConfigManager().getScanExtra() + "，监听器ID：" + handler.getID());

    }

    /**
     * 暂停监听
     */
    private void stopReceiver() {

        BroadcastDispatcher.getInstance().unregister(getConfigManager().getScanAction());
        // 记录日志
        writeInfoLog("激光头广播接收器已停止");
    }

    /**
     * 销毁前，停止监听
     */
    @Override
    public void onActivityDestroy() {
        stopReceiver();
        super.onActivityDestroy();
    }
}
