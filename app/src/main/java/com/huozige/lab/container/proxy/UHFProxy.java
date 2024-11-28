package com.huozige.lab.container.proxy;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.BroadcastDispatcher;

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

    // 需要去重，采用HashSet而不是ArrayList
    HashSet<String> _scanResultCache = new HashSet<>();

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

            _scanResultCache.clear();

            writeInfoLog("异步连续读取UHF已启动，等待接收结果。广播：" + getConfigManager().getUHFAction() + "，键值：" + getConfigManager().getUHFExtra());

            var handler = new BroadcastDispatcher.BroadcastHandler() {
                @Override
                public String getAction() {
                    return getConfigManager().getUHFAction();
                }

                @Override
                public int getPriority() {
                    return Integer.MAX_VALUE;
                }

                @Override
                public boolean handle(@Nullable Bundle extras) {

                    writeInfoLog("收到持续UHF读取结果的广播");

                    try {

                        if (extras != null) {

                            // 按照厂商的文档，从广播中获取扫码结果
                            String result = extras.getString(getConfigManager().getUHFExtra());

                            if (result == null) {
                                // 预期外场景需要记录日志，忽略错误
                                writeErrorLog("UHF读取到数据，但Extra中没有找到指定Key的内容，请检查Extra配置。");
                                result = "";
                            }

                            // 多个结果按照回车来分隔，需要先拆解
                            result = result.replace("\n", ",");
                            result = result.replace("\r", ",");

                            // 与激光头不同，这里需要去重，所以用Hashset
                            _scanResultCache.addAll(Arrays.asList(result.split("\n")));

                            // 再去除因为上面处理导致的空值
                            _scanResultCache.removeIf((d) -> d.isBlank() || d.isEmpty());

                            writeInfoLog("当次读取结果是：" + result);

                            // 输出，按照HAC的惯例，逗号分隔
                            callback(CallbackParams.success(String.join(",", _scanResultCache), getID().toString()));
                        }

                    } catch (Exception ex) {
                        callback(CallbackParams.error(ex.toString()));
                    }

                    return false;
                }
            };

            // 注册广播处理器
            BroadcastDispatcher.getInstance().register(handler);

            // 记录日志
            writeInfoLog("UHF广播接收器已启动。广播：" + getConfigManager().getUHFAction() + "，键值：" + getConfigManager().getUHFExtra() + "，监听器ID：" + handler.getID());

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

        BroadcastDispatcher.getInstance().unregister(getConfigManager().getUHFAction());

        _scanResultCache.clear();

        // 记录日志
        writeInfoLog("UHF广播接收器已停止");
    }
}
