package com.huozige.lab.container.proxy;

import android.content.Intent;

import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

/**
 * 兼容官方APP的页面端能力
 * 1.0.0
 * index.ScanCode(cell)：调用手机摄像头实现扫码
 */
public class IndexProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _arcZxingLite; // 用来弹出ZXingLite扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    String _scanResultCell; // 存放扫码结果的单元格位置

    /**
     * 注册到页面的index.ScanCode(cell)方法
     * @param cellLocation 存放结果的单元格位置与官方APP要求一致
     */
    @JavascriptInterface
    public void ScanCode(String cellLocation) {

        // 存储单元格位置
        _scanResultCell = cellLocation;

        // 调用ZXingLite的扫码页面
        _arcZxingLite.launch(new Intent(getInterop().getActivityContext(), CaptureActivity.class));
    }

    /**
     * 注册的名称为：index
     */
    @Override
    public String getName() {
        return "index";
    }

    /**
     * 初始化过程：创建调用器
     */
    @Override
    public void onActivityCreated(AppCompatActivity activity){

        // 创建到ZXingLite的调用器
        _arcZxingLite= activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
            Intent data = result.getData();
            String resultS="";

            if( null != data ){
                resultS=CameraScan.parseScanResult(data);
                getInterop().writeLogIntoConsole("ZXing scan completed. Result is : "+ resultS);
            }else{
                // 记录日志
                getInterop().writeLogIntoConsole("ZXing scan canceled or failed.");
            }

            // 将结果写回到单元格
            getInterop().setInputValue(_scanResultCell,resultS);
        });
    }
}
