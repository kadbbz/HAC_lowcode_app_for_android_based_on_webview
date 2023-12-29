package com.huozige.lab.container.proxy;

import android.bluetooth.BluetoothGatt;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleMtuChangedCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.PermissionsUtility;

import java.util.List;

public class BLEProxy extends AbstractProxy {

    static final String LOG_TAG = "HAC_BLEProxy";

    @Override
    public String getName() {
        return "ble";
    }

    private boolean checkBleErrors(String cellError) {

        if (!BleManager.getInstance().isSupportBle()) {
            getInterop().setInputValue(cellError, "BLE_NOT_SUPPORTED");
            Toast.makeText(getInterop().getActivityContext(), R.string.ui_message_ble_na, Toast.LENGTH_LONG).show();
            return true;
        }

        if (!BleManager.getInstance().isBlueEnable()) {
            getInterop().setInputValue(cellError, "BLUETOOTH_DISABLED");
            Toast.makeText(getInterop().getActivityContext(), R.string.ui_message_ble_na, Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    @JavascriptInterface
    public void scan(String cellJSONList, String cellError) {

        PermissionsUtility.asyncRequirePermissions(getInterop().getActivityContext(), new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {
            if (checkBleErrors(cellError)) {
                return;
            }

            BleManager.getInstance().scan(new BleScanCallback() {
                @Override
                public void onScanStarted(boolean success) {
                    Log.v(LOG_TAG, "Start scanning BLE devices");
                }

                @Override
                public void onScanning(BleDevice bleDevice) {
                    Log.v(LOG_TAG, "BLE device was founded:" + bleDevice.getKey());
                }

                @Override
                public void onScanFinished(List<BleDevice> scanResultList) {
                    String json = "[]";
                    if (scanResultList != null) {
                        json = JSON.toJSONString(scanResultList);
                    }

                    Log.v(LOG_TAG, "Scan completed, BLE devices: " + json);

                    getInterop().setInputValue(cellJSONList, json);
                    getInterop().setInputValue(cellError, "");
                }
            });
        });
    }

    @JavascriptInterface
    public void read(String mac, String uuid_service, String uuid_characteristic_read, String cellJSONData, String cellError) {

        PermissionsUtility.asyncRequirePermissions(getInterop().getActivityContext(), new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {
                    if (checkBleErrors(cellError)) {
                        return;
                    }

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {

                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            Log.e(LOG_TAG, "Connecting failed, device: "+bleDevice+"  detail: " + exception);
                            getInterop().setInputValue(cellJSONData, "");
                            getInterop().setInputValue(cellError, exception.getDescription());

                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            Log.v(LOG_TAG, "Connected the device, ready for reading: " + bleDevice +" ,status: "+status);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            BleManager.getInstance().read(
                                    bleDevice,
                                    uuid_service,
                                    uuid_characteristic_read,
                                    new BleReadCallback() {
                                        @Override
                                        public void onReadSuccess(byte[] data) {
                                            String json = JSON.toJSONString(data);

                                            Log.v(LOG_TAG, "Reading completed, data: " + json);
                                            getInterop().setInputValue(cellJSONData, json);
                                            getInterop().setInputValue(cellError, "");
                                        }

                                        @Override
                                        public void onReadFailure(BleException exception) {
                                            Log.e(LOG_TAG, "Reading failed, detail: " + exception);
                                            getInterop().setInputValue(cellJSONData, "");
                                            getInterop().setInputValue(cellError, exception.getDescription());
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            Log.v(LOG_TAG, "Device disconnected: " + bleDevice +" ,status: "+status);
                        }
                    });
                }
        );
    }

    @JavascriptInterface
    public void write(String mac, String uuid_service, String uuid_characteristic_read, String arrayValue, String cellError) {

        Object[] source = JSONArray.parseArray(arrayValue).toArray();

        byte[] data = new byte[source.length];

        for(int i=0; i< source.length;i++){
            data[i] = (byte) source[i];
        }

        PermissionsUtility.asyncRequirePermissions(getInterop().getActivityContext(), new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {
                    if (checkBleErrors(cellError)) {
                        return;
                    }

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {

                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            Log.e(LOG_TAG, "Connecting failed, device: "+bleDevice+"  detail: " + exception);
                            getInterop().setInputValue(cellError, exception.getDescription());

                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            Log.v(LOG_TAG, "Connected the device, ready for writing: " + bleDevice +" ,status: "+status);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            BleManager.getInstance().write(
                                    bleDevice,
                                    uuid_service,
                                    uuid_characteristic_read,
                                    data,
                                    new BleWriteCallback() {
                                        @Override
                                        public void onWriteSuccess(int current, int total, byte[] justWrite) {

                                            Log.v(LOG_TAG, "Writing completed, total: " + total);
                                            getInterop().setInputValue(cellError, "");
                                        }

                                        @Override
                                        public void onWriteFailure(BleException exception) {
                                            Log.e(LOG_TAG, "Writing failed, detail: " + exception);

                                            getInterop().setInputValue(cellError, exception.getDescription());
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            Log.v(LOG_TAG, "Device disconnected: " + bleDevice +" ,status: "+status);
                        }
                    });
                }
        );
    }

    @JavascriptInterface
    public void setMTU(String mac,  int mtu, String cellError) {

        PermissionsUtility.asyncRequirePermissions(getInterop().getActivityContext(), new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {
                    if (checkBleErrors(cellError)) {
                        return;
                    }

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {

                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            Log.e(LOG_TAG, "Connecting failed, device: "+bleDevice+"  detail: " + exception);
                            getInterop().setInputValue(cellError, exception.getDescription());

                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            Log.v(LOG_TAG, "Connected the device, ready for writing: " + bleDevice +" ,status: "+status);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            BleManager.getInstance().setMtu(
                                    bleDevice,
                                    mtu,
                                    new BleMtuChangedCallback() {
                                        @Override
                                        public void onSetMTUFailure(BleException exception) {
                                            Log.e(LOG_TAG, "Setting MTU failed, detail: " + exception);

                                            getInterop().setInputValue(cellError, exception.getDescription());
                                        }

                                        @Override
                                        public void onMtuChanged(int mtu) {

                                        }

                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            Log.v(LOG_TAG, "Device disconnected: " + bleDevice +" ,status: "+status);
                        }
                    });
                }
        );
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {

        BleManager.getInstance().init(activity.getApplication());

        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);

        Log.v(LOG_TAG, "BLE manager is ready.");

    }

}
