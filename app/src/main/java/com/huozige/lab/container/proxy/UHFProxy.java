package com.huozige.lab.container.proxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.webkit.JavascriptInterface;

import com.huozige.lab.container.platform.CallbackParams;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 让页面具备操作UHF硬件的能力
 * 1.19.0
 * uhf.readAsync(tick)： 开始读取
 * uhf.stop(); 停止读取
 */
public class UHFProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "uhf";
    }


    HashSet<String> _scanResultCache = new HashSet<>();

    /**
     * 定义用于接收持续扫描的广播接收器
     * 支持扫码的PDA通常支持以广播的方式，将扫码的结果通知到各App
     * 广播的名称是通过内置应用来配置的，当用户按下扫码按钮时，该广播会以这个名称发出
     */
    BroadcastReceiver _scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            writeInfoLog("收到持续UHF读取结果的广播");

            try {
                // 按照厂商的文档，从广播中获取扫码结果
                String result = intent.getStringExtra(getConfigManager().getUHFExtra());

                if (result == null) {
                    // 预期外场景需要记录日志，忽略错误
                    writeErrorLog("UHF读取到数据，但Extra中没有找到指定Key的内容，请检查Extra配置。");
                    result = "";
                }

                // 多个结果按照回车来分隔，需要先拆解
                result = result.replace("\n",",");
                result = result.replace("\r",",");

                // 与激光头不同，这里需要去重，所以用Hashset
                _scanResultCache.addAll(Arrays.asList(result.split("\n")));

                // 再去除因为上面处理导致的空值
                _scanResultCache.removeIf((d)-> d.isBlank()|| d.isEmpty());

                writeInfoLog("当次读取结果是：" + result);

                // 输出，按照HAC的惯例，逗号分隔
                callback(CallbackParams.success(String.join(",", _scanResultCache)));

            } catch (Exception ex) {
                callback(CallbackParams.error(ex.toString()));
            }
        }
    };

    /**
     * 注册到页面的uhf.readAsync(ticket)方法
     * 开始连续扫码
     *
     * @param ticket 回调
     */
    @JavascriptInterface
    public void readAsync(String ticket) {

        try {
            registryForFeatureUsageAnalyze("use_uhf_feature", "readAsync");
            // 记录传入参数
            registryCallbackTicket(ticket);

            writeInfoLog("异步连续读取UHF已启动，等待接收结果。广播：" + getConfigManager().getUHFAction() + "，键值：" + getConfigManager().getUHFExtra());

            // 开始监听
            String intentF = getConfigManager().getUHFAction();

            // 按照名称来过滤出需要处理的广播
            IntentFilter intentFilter = new IntentFilter(intentF);

            intentFilter.setPriority(Integer.MAX_VALUE);

            _scanResultCache.clear();

            // 注册广播监听
            getWebView().getContext().registerReceiver(_scanReceiver, intentFilter);

            // 记录日志
            writeInfoLog("UHF广播接收器已启动。广播：" + getConfigManager().getUHFAction() + "，键值：" + getConfigManager().getUHFExtra());

        } catch (Exception ex) {
            callback(CallbackParams.error(ex.toString()));
        }
    }

    /**
     * 注册到页面的uhf.stop()方法
     * 停止连续扫码
     */
    @JavascriptInterface
    public void stop() {

        // 记录日志
        writeInfoLog("尝试停止UHF广播接收器");

        getWebView().getContext().unregisterReceiver(_scanReceiver);

        _scanResultCache.clear();

        // 记录日志
        writeInfoLog("UHF广播接收器已停止");
    }

    /**
     * 页面销毁前，先停掉监听器
     */
    @Override
    public void onActivityDestroy(){
        stop();
        super.onActivityDestroy();
    }
}
