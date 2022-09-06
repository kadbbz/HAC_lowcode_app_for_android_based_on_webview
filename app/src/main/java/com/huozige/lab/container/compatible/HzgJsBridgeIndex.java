package com.huozige.lab.container.compatible;

import android.content.Intent;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.HzgWebInteropHelpers;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;


import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.MainActivity;

/**
 * 兼容官方APP的页面端能力
 * index.ScanCode(cell)：调用手机摄像头实现扫码
 */
public class HzgJsBridgeIndex extends BaseBridge {

    ActivityResultLauncher<Intent> _arcZxingLite; // 用来弹出ZXingLite扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    String _scanResultCell; // 存放扫码结果的单元格位置

    /**
     * 构造函数，无需额外操作
     */
    public HzgJsBridgeIndex(MainActivity activity,WebView webView)
    {
        super(activity,webView);
    }

    /**
     * 注册到页面的index.ScanCode(cell)方法
     * @param cellLocation 存放结果的单元格位置与官方APP要求一致
     */
    @JavascriptInterface
    public void ScanCode(String cellLocation) {
        // 兼容官方版的做法
        // webview.loadUrl("javascript:Forguncy.Page.getCell('result').setValue('" + result + "')");

        // 存储单元格位置
        _scanResultCell = cellLocation;

        // 调用ZXingLite的扫码页面
        _arcZxingLite.launch(new Intent(ActivityContext, CaptureActivity.class));
    }

    /**
     * 注册的名称为：index
     */
    @Override
    public String GetName() {
        return "index";
    }

    /**
     * 初始化过程：创建调用器
     */
    @Override
    public void OnActivityCreated(){

        // 创建到ZXingLite的调用器
        _arcZxingLite= ActivityContext.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
            Intent data = result.getData();
            String resultS="";

            if( null != data ){
                resultS=CameraScan.parseScanResult(data);
                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"ZXing scan completed. Result is : "+ resultS);
            }else{
                // 记录日志
                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"ZXing scan canceled or failed.");
            }

            // 将结果写回到单元格
            HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView,_scanResultCell,resultS);
        });
    }

    /**
     * 无需操作
     */
    @Override
    public void BeforeActivityDestroy() {

    }

    /**
     * 无需操作
     */
    @Override
    public void BeforeActivityPause() {

    }

    /**
     * 无需操作
     */
    @Override
    public void OnActivityResumed() {

    }

    /**
     * 无需处理
     * @param requestCode 同onActivityResult
     * @param resultCode 同onActivityResult
     * @param data 同onActivityResult
     * @return 跳过这个JS桥，处理下一个
     */
    @Override
    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }


}
