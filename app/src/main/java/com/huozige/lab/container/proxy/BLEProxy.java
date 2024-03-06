package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.proxy.support.scanner.BleHelper;
import com.huozige.lab.container.proxy.support.scanner.BleProxy_ReadingActivity;
import com.huozige.lab.container.utilities.MiscUtilities;

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
 */
public class BLEProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _scanner;
    String cellPayload, cellError, cellRaw;

    @Override
    public String getName() {
        return "ble";
    }

    /**
     * 扫描周边可用的蓝牙设备，包含BLE和其他蓝牙设备，并将设备信息JSON序列化后填充到单元格
     *
     * @param payloadLocation 存放设备信息列表的单元格
     * @param errorLocation   存放错误信息的单元格
     */
    @JavascriptInterface
    public void scan(String payloadLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start BLE scanning.");

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_SCAN);
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
        cellPayload = payloadLocation;
        cellRaw = rawLocation;
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start reading via BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_READ);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
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
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start write data " + hexOrBase64Value + " to BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_WRITE);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD, hexOrBase64Value);
        _scanner.launch(request);

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
        getInterop().writeLogIntoConsole("Start watching NOTIFY from BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        BleHelper.getInstance(getInterop().getActivityContext().getApplication()).registryNotify(getInterop().getActivityContext(), mac, uuid_service, uuid_characteristic, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                getInterop().writeLogIntoConsole("Data received by NOTIFY from BLE " + mac + " characteristic: " + uuid_characteristic + " data: " + MiscUtilities.byteArrayToCommaSeperatedString(data.payloadAsByteArray));

                if (data.payloadAsByteArray != null && data.payloadAsByteArray.length > 0) {
                    if (payloadLocation != null && !payloadLocation.isEmpty()) {
                        getInterop().setInputValue(payloadLocation, Base64.getEncoder().encodeToString(data.payloadAsByteArray));
                    }

                    if (rawLocation != null && !rawLocation.isEmpty()) {
                        getInterop().setInputValue(rawLocation, MiscUtilities.byteArrayToCommaSeperatedString(data.payloadAsByteArray));
                    }
                }
            }

            @Override
            public void onError(BleHelper.BleError error) {

                getInterop().writeLogIntoConsole("BLE error from NOTIFY! Code is " + error.code + " , detail: " + error.description);

                XLog.e("蓝牙操作出错(NOTIFY)，错误码为：" + error.code + "，错误信息：" + error.description);

                if (errorLocation != null && !errorLocation.isEmpty()) {
                    getInterop().setInputValue(errorLocation, error.code);
                }
            }
        });

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
        getInterop().writeLogIntoConsole("Stop watching NOTIFY: " + mac + " -> " + uuid_service + " -> " + uuid_characteristic);

        boolean isAlive = BleHelper.getInstance(getInterop().getActivityContext().getApplication()).unregisterNotify(mac, uuid_service, uuid_characteristic);

        if (!isAlive)
            getInterop().writeLogIntoConsole("Warn: Device has been disconnected before unregisterNotify.");
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
        getInterop().writeLogIntoConsole("Start watching INDICATE from BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        BleHelper.getInstance(getInterop().getActivityContext().getApplication()).registryIndicate(getInterop().getActivityContext(), mac, uuid_service, uuid_characteristic, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                getInterop().writeLogIntoConsole("Data received by INDICATE from BLE " + mac + " characteristic: " + uuid_characteristic + " data: " + MiscUtilities.byteArrayToCommaSeperatedString(data.payloadAsByteArray));

                if (data.payloadAsByteArray != null && data.payloadAsByteArray.length > 0) {
                    if (payloadLocation != null && !payloadLocation.isEmpty()) {
                        getInterop().setInputValue(payloadLocation, Base64.getEncoder().encodeToString(data.payloadAsByteArray));
                    }

                    if (rawLocation != null && !rawLocation.isEmpty()) {
                        getInterop().setInputValue(rawLocation, MiscUtilities.byteArrayToCommaSeperatedString(data.payloadAsByteArray));
                    }
                }
            }

            @Override
            public void onError(BleHelper.BleError error) {

                getInterop().writeLogIntoConsole("BLE error from INDICATE! Code is " + error.code + " , detail: " + error.description);

                XLog.e("蓝牙操作出错(INDICATE)，错误码为：" + error.code + "，错误信息：" + error.description);

                if (errorLocation != null && !errorLocation.isEmpty()) {
                    getInterop().setInputValue(errorLocation, error.code);
                }
            }
        });
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
        getInterop().writeLogIntoConsole("Stop watching INDICATE: " + mac + " -> " + uuid_service + " -> " + uuid_characteristic);

        boolean isAlive = BleHelper.getInstance(getInterop().getActivityContext().getApplication()).unregisterIndicate(mac, uuid_service, uuid_characteristic);

        if (!isAlive)
            getInterop().writeLogIntoConsole("Warn: Device has been disconnected before unregisterNotify.");
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _scanner = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int code = result.getResultCode();
            Intent resp = result.getData();

            if (resp != null) {
                String payload = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD);
                String raw = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_RAW_PAYLOAD);
                String error = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_ERROR);

                if (error == null || error.isEmpty()) {
                    getInterop().writeLogIntoConsole("Data received from device: " + payload + " (" + raw + ")");
                } else {
                    getInterop().writeLogIntoConsole("Error occurred during data exchanging: " + error);
                }

                XLog.v("蓝牙操作的返回信息：code -> " + code + " payload - > " + payload + " err -> " + error);

                if (cellError != null && !cellError.isEmpty()) {
                    getInterop().setInputValue(cellError, error);
                }
                if (cellPayload != null && !cellPayload.isEmpty()) {
                    getInterop().setInputValue(cellPayload, payload);
                }
                if (cellRaw != null && !cellRaw.isEmpty()) {
                    getInterop().setInputValue(cellRaw, raw);
                }
            } else {

                getInterop().writeLogIntoConsole("App error! Return without intent, code is " + code);

                XLog.e("应用运行异常，蓝牙返回的结果中没有Intent数据，返回码为：" + code);

                if (cellError != null && !cellError.isEmpty()) {
                    getInterop().setInputValue(cellError, code);
                }
            }


        });
    }

}
