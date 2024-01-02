package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.proxy.support.scanner.BleProxy_ReadingActivity;

public class BLEProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _scanner;
    String cellPayload, cellError;

    static final String LOG_TAG = "HAC_BLEProxy";

    @Override
    public String getName() {
        return "ble";
    }

    @JavascriptInterface
    public void scan(String payloadLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellError = errorLocation;

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_SCAN);
        _scanner.launch(request);
    }

    @JavascriptInterface
    public void read(String mac, String uuid_service, String uuid_characteristic, String payloadLocation, String errorLocation) {
        cellPayload = payloadLocation;
        cellError = errorLocation;

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_READ);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        _scanner.launch(request);
    }

    @JavascriptInterface
    public void write(String mac, String uuid_service, String uuid_characteristic, String base64Value, String errorLocation) {
        cellError = errorLocation;

        Intent request = new Intent(getInterop().getActivityContext(), BleProxy_ReadingActivity.class);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_OP, BleProxy_ReadingActivity.BLE_OP_WRITE);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_MAC, mac);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_SERVICE, uuid_service);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_CHARACTER, uuid_characteristic);
        request.putExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD, base64Value);
        _scanner.launch(request);

    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _scanner = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int code = result.getResultCode();
            Intent resp = result.getData();

            Log.v(LOG_TAG,"Result received with extras: "+ String.join(",", resp.getExtras().keySet().stream().toArray(String[]::new)));

            String payload = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_PAYLOAD);
            String error = resp.getStringExtra(BleProxy_ReadingActivity.BUNDLE_EXTRA_ERROR);

            Log.v(LOG_TAG, "code -> " + code + " payload - > " + payload + " err -> " + error);

            if (cellError != null && !cellError.isEmpty()) {
                getInterop().setInputValue(cellError, error);
            }
            if (cellPayload != null && !cellPayload.isEmpty()) {
                getInterop().setInputValue(cellPayload, payload);
            }

        });
    }

}
