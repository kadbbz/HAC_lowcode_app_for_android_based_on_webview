package com.huozige.lab.container.proxy;

import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;

/**
 * 让页面能操作德佟封装的蓝牙打印机
 * 1.7.0
 * dothanPrinter.getStatus()：获取当前连接的打印机的状态
 */
public class DothanPrinterProxy extends AbstractProxy{
    private LPAPI _lpApi;

    @Override
    public String getName() {
        return "dothanPrinter";
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity){
        _lpApi = LPAPI.Factory.createInstance(this.getInterop().getWebView());
    }

    /**
     * 获取打印机的状态
     * 0：正在连接打印机
     * 1：打印机已连接
     * 2：打印机已连接的情况下，再次连接该打印机以测试连接是否有效。如果有效的话，会发出该状态消息；
     * 3：正在打印
     * 4：除打印之外的其他工作
     * 5：打印机已断开
     */
    @JavascriptInterface
    public int getStatus() {
        IDzPrinter.PrinterState status = _lpApi.getPrinterState();
        return status.ordinal();
    }
}
