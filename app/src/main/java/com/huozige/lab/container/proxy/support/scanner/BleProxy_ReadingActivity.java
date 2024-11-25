package com.huozige.lab.container.proxy.support.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;
import com.huozige.lab.container.utilities.StringConvertUtility;

import java.util.Base64;

public class BleProxy_ReadingActivity extends BaseActivityNoActionBar {

    public final static int STATUS_OK = 0;
    public final static int STATUS_ERROR = -10;
    public final static int STATUS_CANCEL = -1;
    public final static String BLE_OP_SCAN = "scan";
    public final static String BLE_OP_READ = "read";
    public final static String BLE_OP_WRITE = "write";
    public final static String BUNDLE_EXTRA_OP = "op";
    public final static String BUNDLE_EXTRA_MAC = "mac";
    public final static String BUNDLE_EXTRA_SERVICE = "service";
    public final static String BUNDLE_EXTRA_CHARACTER = "character";
    public final static String BUNDLE_EXTRA_PAYLOAD = "payload";
    public final static String BUNDLE_EXTRA_RAW_PAYLOAD = "raw";

    public final static String BUNDLE_EXTRA_ERROR = "error";


    View.OnClickListener _cancelButtonClick = view -> sendResultAndFinish(new BleHelper.BleError(-999, "USER_CANCEL"), true);

    BleHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _helper = BleHelper.getInstance(getApplication());

        setContentView(R.layout.activity_ble_proxy_reading);
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 先检查蓝牙是否可用，如果不能用直接返回
        if (!_helper.checkBleReady()) {
            Toast.makeText(this, R.string.ui_message_ble_na, Toast.LENGTH_LONG).show();
            sendResultAndFinish(new BleHelper.BleError(-1, "BLE_NOT_SUPPORTED"));

        } else {
            XLog.v("设备的BLE功能可正常使用");

            Intent intentR = getIntent();

            if (intentR != null) {

                String op = intentR.getStringExtra(BUNDLE_EXTRA_OP);
                if (op == null) op = BLE_OP_SCAN;

                switch (op) {
                    case BLE_OP_SCAN: {
                        this.startScan();
                        break;
                    }
                    case BLE_OP_READ: {
                        String mac = intentR.getStringExtra(BUNDLE_EXTRA_MAC);
                        String service = intentR.getStringExtra(BUNDLE_EXTRA_SERVICE);
                        String character = intentR.getStringExtra(BUNDLE_EXTRA_CHARACTER);

                        this.startRead(mac, service, character);
                        break;
                    }
                    case BLE_OP_WRITE: {
                        String mac = intentR.getStringExtra(BUNDLE_EXTRA_MAC);
                        String service = intentR.getStringExtra(BUNDLE_EXTRA_SERVICE);
                        String character = intentR.getStringExtra(BUNDLE_EXTRA_CHARACTER);
                        String payload = intentR.getStringExtra(BUNDLE_EXTRA_PAYLOAD);

                        this.startWrite(mac, service, character, payload);
                        break;
                    }
                }
            }
        }
    }

    private void sendResultAndFinish(BleHelper.BleError error) {
        sendResultAndFinish(error, false);
    }

    private void sendResultAndFinish(BleHelper.BleError error, boolean isCancel) {

        sendResultAndFinish(isCancel ? STATUS_CANCEL : STATUS_ERROR, "", "", error.toString());
    }

    private void sendResultAndFinish(byte[] payload) {
        sendResultAndFinish(STATUS_OK, Base64.getEncoder().encodeToString(payload), StringConvertUtility.byteArrayToCommaSeperatedString(payload), "");
    }

    private void sendResultAndFinish(String payload) {
        sendResultAndFinish(STATUS_OK, payload, payload, "");
    }

    private void sendResultAndFinish(int status, String base64, String raw, String error) {

        // 准备返回的数据
        Intent result = new Intent();
        result.putExtra(BUNDLE_EXTRA_ERROR, error);
        result.putExtra(BUNDLE_EXTRA_PAYLOAD, base64);
        result.putExtra(BUNDLE_EXTRA_RAW_PAYLOAD, raw);
        setResult(status, result);

        // 执行返回操作
        finish();
    }

    private void startScan() {

        _helper.startScan(this, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                sendResultAndFinish(data.payloadAsString);
            }

            @Override
            public void onError(BleHelper.BleError error) {
                sendResultAndFinish(error);
            }
        });
    }

    private void startRead(String mac, String uuid_service, String uuid_characteristic) {

        _helper.startRead(this, mac, uuid_service, uuid_characteristic, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                if (data.payloadAsByteArray != null) {
                    sendResultAndFinish(data.payloadAsByteArray);
                } else {
                    sendResultAndFinish("");
                }
            }

            @Override
            public void onError(BleHelper.BleError error) {
                sendResultAndFinish(error);
            }
        });

    }

    private void startWrite(String mac, String uuid_service, String uuid_characteristic, String stringValue) {

        _helper.startWrite(this, mac, uuid_service, uuid_characteristic, stringValue, new BleHelper.BleCallback() {
            @Override
            public void onSuccess(BleHelper.BleResultData data) {
                sendResultAndFinish("");
            }

            @Override
            public void onError(BleHelper.BleError error) {
                sendResultAndFinish(error);
            }
        });
    }
}