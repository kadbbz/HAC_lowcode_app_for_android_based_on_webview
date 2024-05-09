package com.huozige.lab.container.proxy;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.scanner.BleHelper;
import com.huozige.lab.container.proxy.support.scanner.BleProxy_ReadingActivity;
import com.huozige.lab.container.utilities.StringConvertUtility;

import java.util.Base64;

/**
 * 操作蓝牙设备
 * 1.14.0
 * ble.scan(payloadLocation, errorLocation)：扫描周边的蓝牙设备并返回列表
 * ble.read(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)： 从指定MAC的蓝牙设备中读取指定特性的值
 * ble.notify(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)：在指定MAC的蓝牙设备上订阅指定特征值的变化
 * ble.indicate(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)：在指定MAC的蓝牙设备上订阅指定特征值的变化，并自动发送回执
 * ble.write(mac, uuid_service, uuid_characteristic, hexOrBase64Value, errorLocation)：向指定MAC的蓝牙设备上指定的特性写入值
 * 1.15.1
 * ble.unregisterNotify(mac, uuid_service, uuid_characteristic)：取消Notify监听
 * ble.unregisterIndicate(mac, uuid_service, uuid_characteristic)：取消Indicate监听
 * 1.17.0
 * ble.scanAsync(callbackTicket) ：异步扫描周边的蓝牙设备并返回列表
 * ble.readAsync(mac, uuid_service, uuid_characteristic, ticket)： 从指定MAC的蓝牙设备中异步读取指定特性的值
 * ble.writeAsync(mac, uuid_service, uuid_characteristic, hexOrBase64Value, ticket)：向指定MAC的蓝牙设备上指定的特性异步写入值
 * ble.notifyAsync(mac, uuid_service, uuid_characteristic, ticket)：在指定MAC的蓝牙设备上异步订阅指定特征值的变化
 * ble.indicateAsync(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)：在指定MAC的蓝牙设备上异步订阅指定特征值的变化，并自动发送回执
 */
public class BLEProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _scanner;

    @Override
    public String getName() {
        return "ble";
    }

    private void startScan() {
        writeInfoLog("开始扫描BLE设备。");

        Intent request = createIntent(BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_SCAN);
        _scanner.launch(request);
    }

    /**
     * 扫描周边可用的蓝牙设备，包含BLE和其他蓝牙设备，并将设备信息JSON序列化后填充到单元格
     *
     * @param payloadLocation 存放设备信息列表的单元格
     * @param errorLocation   存放错误信息的单元格
     */
    @JavascriptInterface
    public void scan(String payloadLocation, String errorLocation) {
        registryForFeatureUsageAnalyze("use_ble_feature", "scan");

        registryPayloadCellLocation(payloadLocation);
        registryErrorCellLocation(errorLocation);

        startScan();
    }

    /**
     * 异步扫描周边可用的蓝牙设备，包含BLE和其他蓝牙设备，并将设备信息JSON序列化后回调
     *
     * @param callbackTicket 回调
     */
    @JavascriptInterface
    public void scanAsync(String callbackTicket) {
        registryForFeatureUsageAnalyze("use_ble_feature", "scanAsync");

        registryCallbackTicket(callbackTicket);

        startScan();
    }

    private void startRead(String mac, String uuid_service, String uuid_characteristic) {

        writeInfoLog("开始从地址为" + mac + "的BLE设备读取数据，service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = createIntent(BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_READ);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        _scanner.launch(request);
    }

    /**
     * 执行对指定特征的READ操作，将结果返回到单元格
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param payloadLocation     存放设备返回数据（BASE64编码）的单元格
     * @param rawLocation         存放设备返回数据（整数数组）的单元格
     * @param errorLocation       存放错误信息的单元格
     */
    @JavascriptInterface
    public void read(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String rawLocation, String errorLocation) {
        registryForFeatureUsageAnalyze("use_ble_feature", "read");

        registryPayloadCellLocation(payloadLocation, rawLocation);
        registryErrorCellLocation(errorLocation);

        startRead(mac, uuid_service, uuid_characteristic);
    }

    /**
     * 执行对指定特征的READ操作，将结果返回到单元格
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param ticket              回调
     */
    @JavascriptInterface
    public void readAsync(String mac, String uuid_service, String uuid_characteristic, String ticket) {
        registryForFeatureUsageAnalyze("use_ble_feature", "readAsync");

        registryCallbackTicket(ticket);

        startRead(mac, uuid_service, uuid_characteristic);
    }

    private void startWrite(String mac, String uuid_service, String uuid_characteristic, String hexOrBase64Value) {
        writeInfoLog("开始将[" + hexOrBase64Value + "]写入地址为" + mac + "的设备，service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = createIntent(BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_WRITE);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD, hexOrBase64Value);
        _scanner.launch(request);
    }

    /**
     * 向指定特征写入数据
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param hexOrBase64Value    需要写入的数据，支持0x开头的十六进制数据字符串或Base64编码
     * @param errorLocation       存放错误信息的单元格
     */
    @JavascriptInterface
    public void write(String mac, String uuid_service, String uuid_characteristic, String hexOrBase64Value, String errorLocation) {

        registryForFeatureUsageAnalyze("use_ble_feature", "write");

        registryErrorCellLocation(errorLocation);

        startWrite(mac, uuid_service, uuid_characteristic, hexOrBase64Value);
    }

    /**
     * 向指定特征异步写入数据
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param hexOrBase64Value    需要写入的数据，支持0x开头的十六进制数据字符串或Base64编码
     * @param ticket              回调
     */
    @JavascriptInterface
    public void writeAsync(String mac, String uuid_service, String uuid_characteristic, String hexOrBase64Value, String ticket) {

        registryForFeatureUsageAnalyze("use_ble_feature", "writeAsync");

        registryCallbackTicket(ticket);

        startWrite(mac, uuid_service, uuid_characteristic, hexOrBase64Value);
    }

    private void startNotify(String mac, String uuid_service, String uuid_characteristic) {
        writeInfoLog("用NOTIFY方式监听地址为" + mac + "的BLE设备，service: " + uuid_service + " characteristic: " + uuid_characteristic);

        BleHelper.getInstance(((Activity)getWebView().getContext()).getApplication()).registryNotify(getWebView().getContext(), mac, uuid_service, uuid_characteristic, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                writeInfoLog("从地址为" + mac + "的设备上监听到数据，characteristic: " + uuid_characteristic + " data: " + StringConvertUtility.byteArrayToCommaSeperatedString(data.payloadAsByteArray));

                if (data.payloadAsByteArray != null && data.payloadAsByteArray.length > 0) {
                    callback(CallbackParams.success(Base64.getEncoder().encodeToString(data.payloadAsByteArray), StringConvertUtility.byteArrayToCommaSeperatedString(data.payloadAsByteArray)));
                }
            }

            @Override
            public void onError(BleHelper.BleError error) {

                writeErrorLog("蓝牙操作出错(NOTIFY)，错误码为：" + error.code + "，错误信息：" + error.description);

                callback(CallbackParams.error(error.code + " > " + error.description));
            }
        });
    }


    /**
     * 开始监听notify
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param payloadLocation     存放Base64格式的返回值
     * @param rawLocation         存放逗号分隔的返回值
     * @param errorLocation       存放错误信息
     */
    @JavascriptInterface
    public void notify(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String rawLocation, String errorLocation) {
        registryForFeatureUsageAnalyze("use_ble_feature", "notify");

        registryPayloadCellLocation(payloadLocation, rawLocation);
        registryErrorCellLocation(errorLocation);

        startNotify(mac, uuid_service, uuid_characteristic);
    }

    /**
     * 开始异步监听notify
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param ticket              回调
     */
    @JavascriptInterface
    public void notifyAsync(String mac, String uuid_service, String uuid_characteristic, String ticket) {
        registryForFeatureUsageAnalyze("use_ble_feature", "notifyAsync");

        registryCallbackTicket(ticket);

        startNotify(mac, uuid_service, uuid_characteristic);
    }

    /**
     * 停止监听notify
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     */
    @JavascriptInterface
    public void unregisterNotify(String mac, String uuid_service, String uuid_characteristic) {
        writeInfoLog("从地址为" + mac + "的设备上停止NOTIFY监听，" + uuid_service + " -> " + uuid_characteristic);

        boolean isAlive = BleHelper.getInstance(((Activity)getWebView().getContext()).getApplication()).unregisterNotify(mac, uuid_service, uuid_characteristic);

        if (!isAlive)
            writeErrorLog("在调用本方法停止监听之前，与该设备的连接已经中断。");
    }

    public void startIndicate(String mac, String uuid_service, String uuid_characteristic) {
        writeInfoLog("用INDICATE的方式监听地址为" + mac + "的BLE设备，service: " + uuid_service + " characteristic: " + uuid_characteristic);

        BleHelper.getInstance(((Activity)getWebView().getContext()).getApplication()).registryIndicate(getWebView().getContext(), mac, uuid_service, uuid_characteristic, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                writeInfoLog("从地址为" + mac + "的设备上监听到数据，characteristic: " + uuid_characteristic + " data: "  + StringConvertUtility.byteArrayToCommaSeperatedString(data.payloadAsByteArray));

                if (data.payloadAsByteArray != null && data.payloadAsByteArray.length > 0) {

                    callback(CallbackParams.success(Base64.getEncoder().encodeToString(data.payloadAsByteArray), StringConvertUtility.byteArrayToCommaSeperatedString(data.payloadAsByteArray)));
                }
            }

            @Override
            public void onError(BleHelper.BleError error) {

                writeErrorLog("蓝牙操作出错(INDICATE)，错误码为：" + error.code + "，错误信息：" + error.description);

                callback(CallbackParams.error(error.code + " > " + error.description));
            }
        });
    }

    /**
     * 开始监听indicate
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param payloadLocation     存放Base64格式的返回值
     * @param rawLocation         存放逗号分隔的返回值
     * @param errorLocation       存放错误信息
     */
    @JavascriptInterface
    public void indicate(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String rawLocation, String errorLocation) {
        registryForFeatureUsageAnalyze("use_ble_feature", "indicate");

        registryPayloadCellLocation(payloadLocation, rawLocation);
        registryErrorCellLocation(errorLocation);

        startIndicate(mac, uuid_service, uuid_characteristic);
    }

    /**
     * 开始监听indicate
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param ticket              回调
     */
    @JavascriptInterface
    public void indicateAsync(String mac, String uuid_service, String uuid_characteristic, String ticket) {
        registryForFeatureUsageAnalyze("use_ble_feature", "indicateAsync");

        registryCallbackTicket(ticket);

        startIndicate(mac, uuid_service, uuid_characteristic);
    }

    /**
     * 停止监听indicate
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     */
    @JavascriptInterface
    public void unregisterIndicate(String mac, String uuid_service, String uuid_characteristic) {
        writeInfoLog("从地址为" + mac + "的设备上停止INDICATE监听，" + uuid_service + " -> " + uuid_characteristic);

        boolean isAlive = BleHelper.getInstance(((Activity)getWebView().getContext()).getApplication()).unregisterIndicate(mac, uuid_service, uuid_characteristic);

        if (!isAlive)
            writeErrorLog("在调用本方法停止监听之前，与该设备的连接已经中断。");
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _scanner = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int code = result.getResultCode();
            Intent resp = result.getData();
            CallbackParams data;

            if (resp != null) {
                String payload = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD);
                String raw = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_RAW_PAYLOAD);
                String error = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_ERROR);


                if (error == null || error.isEmpty()) {
                    writeInfoLog("从BLE设备上成功完成数据读写：" + payload + " (" + raw + ")");
                    data = CallbackParams.success(payload, raw);
                } else {
                    writeErrorLog("从BLE上读取数据是发生错误：" + error);
                    data = CallbackParams.error(error);
                }

            } else {
                writeErrorLog("应用运行异常，蓝牙返回的结果中没有Intent数据，返回码为：" + code);

                data = CallbackParams.error("App error! Return without intent, code is " + code);
            }

            callback(data);
        });
    }

}
