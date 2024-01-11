package com.huozige.lab.container.proxy;

import android.content.Intent;

import com.elvishew.xlog.XLog;

import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.proxy.support.scanner.BleProxy_ReadingActivity;

/**
 * 操作蓝牙设备
 * 1.14.0
 * ble.scan(payloadLocation, errorLocation)：扫描周边的蓝牙设备并返回列表
 * ble.read(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)： 从指定MAC的蓝牙设备中读取指定特性的值
 * ble.notify(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)：在指定MAC的蓝牙设备上订阅指定特征值的变化
 * ble.indicate(mac, uuid_service, uuid_characteristic, payloadLocation, rawLocation, errorLocation)：在指定MAC的蓝牙设备上订阅指定特征值的变化，并自动发送回执
 * ble.write(mac, uuid_service, uuid_characteristic, hexOrBase64Value, errorLocation)：向指定MAC的蓝牙设备上指定的特性写入值
 */
public class BLEProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _scanner;
    String cellPayload, cellError, cellRaw;

    static final String LOG_TAG = "HAC_BLEProxy";

    @Override
    public String getName() {
        return "ble";
    }

    @JavascriptInterface
    public void scan(String payloadLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start BLE scanning.");

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_SCAN);
        _scanner.launch(request);
    }

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

    @JavascriptInterface
    public void notify(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String rawLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellRaw = rawLocation;
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start subscribe notify from BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_NOTIFY);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        _scanner.launch(request);
    }

    @JavascriptInterface
    public void indicate(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String rawLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellRaw = rawLocation;
        cellError = errorLocation;

        getInterop().writeLogIntoConsole("Start subscribe indicate from BLE device " + mac + " service: " + uuid_service + " characteristic: " + uuid_characteristic);

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_INDICATE);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        _scanner.launch(request);
    }

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

                XLog.v("[" + LOG_TAG + "]code -> " + code + " payload - > " + payload + " err -> " + error);

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

                XLog.e("[" + LOG_TAG + "]应用运行异常，蓝牙返回的结果中没有Intent数据，返回码为：" + code);

                if (cellError != null && !cellError.isEmpty()) {
                    getInterop().setInputValue(cellError, code);
                }
            }


        });
    }

}
