package com.huozige.lab.container.pda;


import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.HzgWebInteropHelpers;

/**
 * 让页面具备操作扫码枪硬件的能力
 * pda.scan(cell)：Broadcast模式扫码
 */
public class HzgJsBridgePDA extends BaseBridge {

    ActivityResultLauncher<Intent> _arcScanner; // 用来弹出Broadcast模式扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    String _cell; // 用来接收扫码结果的单元格位置信息

    /**
     * 基础的构造函数
     *
     * @param context 上下文
     * @param webView 浏览器内核
     */
    public HzgJsBridgePDA(AppCompatActivity context, WebView webView) {
        super(context, webView);
    }


    /**
     * 注册的名称为：pda
     */
    @Override
    public String GetName() {
        return "pda";
    }

    /**
     * 初始化过程：创建调用器
     */
    @Override
    public void InitOnActivityCreated() {

        // 创建Broadcast模式扫码页面
        _arcScanner = ActivityContext.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 获取页面返回的结果
            Intent data = result.getData();

            if (null != data) {
                // 获取并判断返回码
                int code = result.getResultCode();
                if (code == WaitForScannerBroadcastActivity.SCAN_STATUS_OK) {

                    // 成功接收到返回的扫码结果
                    String resultS = data.getBundleExtra(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT).getString(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT);

                    // 记录日志
                    HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"PDA scan completed. Result is : " + resultS);

                    // 将结果写入单元格
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView,_cell,resultS);
                }else{
                    // 记录日志
                    HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"PDA scan canceled or failed. Return code is : "+code);

                    // 重置单元格
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView,_cell,"");
                }
            }else{
                // 记录日志
                HzgWebInteropHelpers.WriteErrorIntoConsole(CurrentWebView,"PDA scan failed.");

                // 重置单元格
                HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView,_cell,"");
            }
        });
    }

    @Override
    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data) {
        // 没有用到Activity调用，无需处理，自然无需中断
        return false;
    }

    /**
     * 注册到页面的pda.scan(cell)方法
     *
     * @param cellLocation 单元格的位置（为空意味着不需要写入单元格），如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     */
    @JavascriptInterface
    public void scan(String cellLocation) {

        // 记录传入的Cell信息
        _cell = cellLocation;

        // 调用Broadcast模式扫码页面
        _arcScanner.launch(new Intent(ActivityContext, WaitForScannerBroadcastActivity.class));
    }


}
