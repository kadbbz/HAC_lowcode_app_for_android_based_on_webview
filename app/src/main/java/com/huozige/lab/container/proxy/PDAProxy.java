package com.huozige.lab.container.proxy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.proxy.support.scanner.PDAProxy_SingleScanActivity;
import com.huozige.lab.container.utilities.MiscUtilities;

import java.util.ArrayList;

/**
 * 让页面具备操作扫码枪硬件的能力
 * pda.modal_scan(cell): 带模态窗口的单次扫码
 * pda.continuous_scan(cell): 开始持续扫码
 * pda.continuous_scan_stop()： 停止持续扫码
 */
public class PDAProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _arcScanner; // 用来弹出Broadcast模式扫码页面的调用器，用来代替旧版本的startActivityForResult方法。
    String _cell; // 用来接收扫码结果的单元格位置信息

    Boolean _continueScanOn = false;
    String continueScanCell;
    ArrayList<String> _resultCache = new ArrayList<>();
    Integer continueScanLimit;


    static final String LOG_TAG = "HAC_HzgJsBridgePDA";

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
                    getInterop().writeLogIntoConsole("PDA scan completed. Result is : " + resultS);

                    // 将结果写入单元格
                    getInterop().setInputValue(_cell, resultS);
                } else {
                    // 记录日志
                    getInterop().writeLogIntoConsole("PDA scan canceled or failed. Return code is : " + code);

                    // 重置单元格
                    getInterop().setInputValue(_cell, "");
                }
            } else {
                // 记录日志
                getInterop().writeErrorIntoConsole("PDA scan failed.");

                // 重置单元格
                getInterop().setInputValue(_cell, "");
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
     * 定义用于接收持续扫描的广播接收器
     * 支持扫码的PDA通常支持以广播的方式，将扫码的结果通知到各App
     * 广播的名称是通过内置应用来配置的，当用户按下扫码按钮时，该广播会以这个名称发出
     */
    BroadcastReceiver _scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v(LOG_TAG, "收到持续扫码结果的广播");

            // 按照厂商的文档，从广播中获取扫码结果
            String result = intent.getStringExtra(getConfigManager().getScanExtra());

            Log.v(LOG_TAG, "当次扫码结果是：" + result);

            if(result == null) result="";

            // 去除非ASCII字符
            result = MiscUtilities.removeNonASCIIChars(result);

            if (_continueScanOn && !result.isEmpty()) {

                // 将当次扫描结果拼接到累计结果上
                _resultCache.add(result);

                // 返回到页面，分割符为半角逗号，与活字格的内置数组保持一致
                String rc = String.join(",", _resultCache);

                // 记录日志
                getInterop().writeLogIntoConsole("PDA scan (Continues Mode) result received. Current scan is : " + result + " , total result is : " + rc);

                // 输出到界面
                getInterop().setInputValue(continueScanCell, rc);

                if (_resultCache.size() >= continueScanLimit) {

                    stopReceiver();

                    // 记录日志
                    getInterop().writeLogIntoConsole("PDA scan (Continues Mode)'s count reach limit, stopping the broadcast receiver.");

                }

            } else {

                // 预期外场景需要记录日志
                Log.e(LOG_TAG, "当前没有处在持续扫描模式，但监听器仍在运行。");
            }
        }
    };

    /**
     * 注册到页面的pda.modal_scan(cell)方法
     * 单次扫码
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void modal_scan(String cellLocation) {

        // 记录传入的Cell信息
        _cell = cellLocation;

        // 调用Broadcast模式扫码页面
        _arcScanner.launch(new Intent(getInterop().getActivityContext(), PDAProxy_SingleScanActivity.class));

        // 记录日志
        getInterop().writeLogIntoConsole("PDA scan (Single Mode) started.");

    }

    /**
     * 注册到页面的pda.continuous_scan(cell)方法
     * 开始连续扫码
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void continuous_scan(String cellLocation, String limit) {

        Log.v(LOG_TAG, "continuous_scan start with limit : " + limit);

        // 记录传入参数
        continueScanCell = cellLocation;
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

        // 按照名称来过滤出需要处理的广播
        String intentF = getConfigManager().getScanAction();

        // 按照名称来过滤出需要处理的广播
        IntentFilter intentFilter = new IntentFilter(intentF);

        intentFilter.setPriority(Integer.MAX_VALUE);

        // 注册广播监听
        getInterop().getActivityContext().registerReceiver(_scanReceiver, intentFilter);

        // 记录日志
        getInterop().writeLogIntoConsole("PDA scan (Continues Mode) started.");

    }

    /**
     * 暂停监听
     */
    private void stopReceiver() {
        if (_continueScanOn) {

            getInterop().getActivityContext().unregisterReceiver(_scanReceiver);

            _continueScanOn = false;

            // 记录日志
            getInterop().writeLogIntoConsole("PDA scan (Continues Mode) stopped.");

        }
    }
}
