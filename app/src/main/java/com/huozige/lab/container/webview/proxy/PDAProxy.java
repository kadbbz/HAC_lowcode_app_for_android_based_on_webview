package com.huozige.lab.container.webview.proxy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.SingleScanActivity;
import com.huozige.lab.container.webview.BaseHTMLInterop;
import com.huozige.lab.container.webview.BaseProxy;
import com.huozige.lab.container.ConfigManager;
import com.huozige.lab.container.R;
import com.huozige.lab.container.webview.HACWebView;

/**
 * 让页面具备操作扫码枪硬件的能力
 * pda.modal_scan(cell): 带模态窗口的单次扫码
 * pda.continuous_scan(cell): 开始持续扫码
 * pda.continuous_scan_stop()： 停止持续扫码
 *
 */
public class PDAProxy extends BaseProxy {

    ActivityResultLauncher<Intent> _arcScanner; // 用来弹出Broadcast模式扫码页面的调用器，用来代替旧版本的startActivityForResult方法。
    ConfigManager _cm;
    String _cell; // 用来接收扫码结果的单元格位置信息

    Boolean _cscanOn = false;
    String _cscanCell;
    String _cscanResultCache;
    Integer _cscanLimit, _cscanCount;


    static final String LOG_TAG = "HzgJsBridgePDA";

    /**
     * 基础的构造函数
     * @param webView 浏览器内核
     * @param interop HTML内容操作接口
     */
    public PDAProxy(HACWebView webView, BaseHTMLInterop interop) {
        super(webView, interop);

        _cm = new ConfigManager(webView.getActivityContext());
    }


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
    public void onActivityCreated() {

        // 创建Broadcast模式扫码页面
        _arcScanner = CurrentWebView.getActivityContext().registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 获取页面返回的结果
            Intent data = result.getData();

            if (null != data) {
                // 获取并判断返回码
                int code = result.getResultCode();
                if (code == SingleScanActivity.SCAN_STATUS_OK) {

                    // 成功接收到返回的扫码结果
                    String resultS = data.getStringExtra(SingleScanActivity.BUNDLE_EXTRA_RESULT);

                    // 记录日志
                    CurrentWebView.writeLogIntoConsole( "PDA scan completed. Result is : " + resultS);

                    // 将结果写入单元格
                    CurrentHTMLInterop.setInputValue( _cell, resultS);
                } else {
                    // 记录日志
                    CurrentWebView.writeLogIntoConsole( "PDA scan canceled or failed. Return code is : " + code);

                    // 重置单元格
                    CurrentHTMLInterop.setInputValue( _cell, "");
                }
            } else {
                // 记录日志
                CurrentWebView.writeErrorIntoConsole( "PDA scan failed.");

                // 重置单元格
                CurrentHTMLInterop.setInputValue( _cell, "");
            }
        });
    }

    /**
     * 无需操作
     */
    @Override
    public void beforeActivityDestroy() {

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
        if (_cscanOn) {
            startReceiver();
        }
    }

    /**
     * 无需操作
     *
     * @param requestCode 同onActivityResult
     * @param resultCode  同onActivityResult
     * @param data        同onActivityResult
     * @return 否，无需中断
     */
    @Override
    public Boolean processActivityResult(int requestCode, int resultCode, Intent data) {
        // 没有用到Activity调用，无需处理，自然无需中断
        return false;
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
            String result = intent.getStringExtra((null == _cm.getScanExtra()) ? CurrentWebView.getActivityContext().getString(R.string.feature_scanner_extra_key_barcode_broadcast) : _cm.getScanExtra());


            Log.v(LOG_TAG, "当次扫码结果是：" + result);

            if (_cscanOn) {

                // 将当次扫描结果拼接到累计结果上，一次刷新到页面，分割符为半角逗号，与活字格的内置数组保持一致
                _cscanResultCache = _cscanResultCache + result + ",";

                // 去掉多余的的逗号
                String rc = _cscanResultCache.endsWith(",") ? _cscanResultCache.substring(0, _cscanResultCache.length() - 1) : _cscanResultCache;

                // 记录日志
                CurrentWebView.writeLogIntoConsole( "PDA scan (Continues Mode) result received. Current scan is : " + result + " , total result is : " + rc);

                // 输出到界面
                CurrentHTMLInterop.setInputValue( _cscanCell, rc);

                _cscanCount++;

                if (_cscanCount >= _cscanLimit) {

                    stopReceiver();

                    // 记录日志
                    CurrentWebView.writeLogIntoConsole( "PDA scan (Continues Mode)'s count reach limit, stopping the broadcast receiver.");

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
        _arcScanner.launch(new Intent(CurrentWebView.getActivityContext(), SingleScanActivity.class));

        // 记录日志
        CurrentWebView.writeLogIntoConsole( "PDA scan (Single Mode) started.");

    }

    /**
     * 注册到页面的pda.continuous_scan(cell)方法
     * 开始连续扫码
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void continuous_scan(String cellLocation, String limit) {

        Log.v(LOG_TAG,"continuous_scan start with limit : "+limit);
        // 记录传入参数
        _cscanCell = cellLocation;
        _cscanLimit =(null==limit || Integer.decode(limit) <= 0) ? Integer.MAX_VALUE : Integer.decode(limit); // 传入0或者复数，则不限制最大次数

        // 重置临时变量
        _cscanOn = true;
        _cscanResultCache = ""; // 清空缓存
        _cscanCount = 0;

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
        String intentF = (_cm.getScanAction() == null) ? CurrentWebView.getActivityContext().getString(R.string.feature_scanner_broadcast_name) : _cm.getScanAction();

        // 按照名称来过滤出需要处理的广播
        IntentFilter intentFilter = new IntentFilter(intentF);

        intentFilter.setPriority(Integer.MAX_VALUE);

        // 注册广播监听
        CurrentWebView.getActivityContext().registerReceiver(_scanReceiver, intentFilter);

        // 记录日志
        CurrentWebView.writeLogIntoConsole( "PDA scan (Continues Mode) started.");

    }

    /**
     * 暂停监听
     */
    private void stopReceiver() {
        if (_cscanOn) {

            CurrentWebView.getActivityContext().unregisterReceiver(_scanReceiver);

            _cscanOn = false;

            // 记录日志
            CurrentWebView.writeLogIntoConsole( "PDA scan (Continues Mode) stopped.");

        }
    }
}