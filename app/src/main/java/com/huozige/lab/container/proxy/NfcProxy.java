package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.scanner.NfcProxy_ReadingActivity;

/**
 * 让页面能读取NFC标签
 * 1.8.0
 * nfc.readTagId(cellTag)：弹出模态窗口，读取NFC标签ID并返回到单元格
 * 1.17.0
 * nfc.readTagIdAsync(ticket)：弹出模态窗口，读取NFC标签的ID后触发回调
 */
public class NfcProxy extends AbstractProxy {
    ActivityResultLauncher<Intent> _arcScanner; // 用来弹出Broadcast模式扫码页面的调用器，用来代替旧版本的startActivityForResult方法。

    @Override
    public String getName() {
        return "nfc";
    }

    @JavascriptInterface
    public void readTagId(String cellTag) {

        // 记录传入的Cell信息
        registryPayloadCellLocation(cellTag);

        // 调用读取页面
        _arcScanner.launch(createIntent(NfcProxy_ReadingActivity.class));

        // 记录日志
        writeInfoLog("开始读取NFC");

        registryForFeatureUsageAnalyze("use_nfc_feature", "readTagId");

    }

    @JavascriptInterface
    public void readTagIdAsync(String ticket) {

        // 记录传入的Cell信息
        registryCallbackTicket(ticket);

        // 调用读取页面
        _arcScanner.launch(createIntent(NfcProxy_ReadingActivity.class));

        // 记录日志
        writeInfoLog("开始异步读取NFC");

        registryForFeatureUsageAnalyze("use_nfc_feature", "readTagIdAsync");

    }

    /**
     * 初始化过程：创建调用器
     */
    @Override
    public void onActivityCreated(AppCompatActivity activity) {

        // 创建读取页面
        _arcScanner = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 获取页面返回的结果
            Intent data = result.getData();

            if (null != data) {
                // 获取并判断返回码
                int code = result.getResultCode();
                if (code == NfcProxy_ReadingActivity.SCAN_STATUS_OK) {

                    // 成功接收到返回的扫码结果：标签和消息
                    String tag = data.getStringExtra(NfcProxy_ReadingActivity.BUNDLE_EXTRA_RESULT_TAG_ID);

                    // 记录日志
                    writeInfoLog("NFC读取成功，结果为：" + tag);

                    // 去除非ASCII字符
                    //tag = StringConvertUtility.removeNonASCIIChars(tag);

                    // 将结果返回
                    callback(CallbackParams.success(tag));
                } else if (code == NfcProxy_ReadingActivity.SCAN_STATUS_NA) {
                    // 记录日志
                    writeErrorLog("NFC读取失败，设备不支持NFC或禁用了NFC功能");

                    // 重置单元格
                    callback(CallbackParams.error("The NFC device is not ready due to not functional or disabled."));
                } else {
                    // 记录日志
                    writeErrorLog("NFC读取失败，错误码为：" + code);

                    // 重置单元格
                    callback(CallbackParams.error("NFC reading canceled or failed. Return code is : " + code));
                }
            } else {
                // 记录日志
                writeErrorLog("NFC读取失败，返回了空值");

                // 重置单元格
                callback(CallbackParams.error("NFC reading failed."));
            }
        });
    }


}
