package com.huozige.lab.container.proxy.support.scanner;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.XLog;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleIndicateCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.MiscUtilities;
import com.huozige.lab.container.utilities.PermissionsUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

public class BleProxy_ReadingActivity extends AppCompatActivity {

    public final static int STATUS_OK = 0;
    public final static int STATUS_ERROR = -10;
    public final static int STATUS_CANCEL = -1;
    public final static String BLE_OP_SCAN = "scan";
    public final static String BLE_OP_READ = "read";
    public final static String BLE_OP_WRITE = "write";
    public final static String BLE_OP_NOTIFY = "notify";
    public final static String BLE_OP_INDICATE = "indicate";
    public final static String BUNDLE_EXTRA_OP = "op";
    public final static String BUNDLE_EXTRA_MAC = "mac";
    public final static String BUNDLE_EXTRA_SERVICE = "service";
    public final static String BUNDLE_EXTRA_CHARACTER = "character";
    public final static String BUNDLE_EXTRA_PAYLOAD = "payload";
    public final static String BUNDLE_EXTRA_RAW_PAYLOAD = "raw";

    public final static String BUNDLE_EXTRA_ERROR = "error";

    private final List<RegistryInfo> __notifyKeys = new ArrayList<>();

    private final List<RegistryInfo> __indicateKeys = new ArrayList<>();

    View.OnClickListener _cancelButtonClick = view -> sendResultAndFinish(new BleError(-999, "USER_CANCEL"), true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_proxy_reading);
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);

        // 初始化
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000)
                .init(getApplication());

        XLog.v("BLE操作接口已完成初始化");
    }

    @Override
    public void onResume() {
        super.onResume();

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
                case BLE_OP_NOTIFY: {
                    String mac = intentR.getStringExtra(BUNDLE_EXTRA_MAC);
                    String service = intentR.getStringExtra(BUNDLE_EXTRA_SERVICE);
                    String character = intentR.getStringExtra(BUNDLE_EXTRA_CHARACTER);

                    this.registryNotify(mac, service, character);
                    break;
                }
                case BLE_OP_INDICATE: {
                    String mac = intentR.getStringExtra(BUNDLE_EXTRA_MAC);
                    String service = intentR.getStringExtra(BUNDLE_EXTRA_SERVICE);
                    String character = intentR.getStringExtra(BUNDLE_EXTRA_CHARACTER);

                    this.registryIndicate(mac, service, character);
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

    @Override
    public void onDestroy() {

        for (RegistryInfo keys : __notifyKeys) {
            BleManager.getInstance().stopNotify(keys.device, keys.service, keys.characteristic);
        }

        __notifyKeys.clear();

        for (RegistryInfo keys : __indicateKeys) {
            BleManager.getInstance().stopIndicate(keys.device, keys.service, keys.characteristic);
        }

        __indicateKeys.clear();

        super.onDestroy();
    }

    public static class RegistryInfo {

        public RegistryInfo(BleDevice device, String service, String character) {
            this.characteristic = character;
            this.device = device;
            this.service = service;
        }

        public BleDevice device;
        public String service;
        public String characteristic;

    }

    private void sendResultAndFinish(BleError error) {
        sendResultAndFinish(error, false);
    }

    private void sendResultAndFinish(BleError error, boolean isCancel) {

        sendResultAndFinish(isCancel ? STATUS_CANCEL : STATUS_ERROR, "", "", error.toString());
    }

    private void sendResultAndFinish(byte[] payload) {
        sendResultAndFinish(STATUS_OK, Base64.getEncoder().encodeToString(payload), stringifyByteArray(payload), "");
    }

    private void sendResultAndFinish(String payload) {
        sendResultAndFinish(STATUS_OK, payload, payload, "");
    }

    private void sendResultAndFinish(int status, String base64, String raw, String error) {
        Intent result = new Intent();
        result.putExtra(BUNDLE_EXTRA_ERROR, error);
        result.putExtra(BUNDLE_EXTRA_PAYLOAD, base64);
        result.putExtra(BUNDLE_EXTRA_RAW_PAYLOAD, raw);
        setResult(status, result);
        finish();
    }

    private void checkBleErrors() {

        if (!BleManager.getInstance().isSupportBle()) {

            XLog.e("当前设备的蓝牙设备不可用：BLE_NOT_SUPPORTED");
            Toast.makeText(this, R.string.ui_message_ble_na, Toast.LENGTH_LONG).show();
            sendResultAndFinish(new BleError(-1, "BLE_NOT_SUPPORTED"));

        }

        if (!BleManager.getInstance().isBlueEnable()) {
            XLog.e("当前设备的蓝牙被禁用：BLE_DISABLED");
            Toast.makeText(this, R.string.ui_message_ble_na, Toast.LENGTH_LONG).show();
            sendResultAndFinish(new BleError(-1, "BLE_DISABLED"));
        }

        XLog.v("设备的BLE功能可正常使用");

    }

    private void fillDefaultServiceAndCharacters(BluetoothGatt gatt, String[] realServiceCharacterUuid, String uuid_service, String uuid_characteristic) {

        List<BluetoothGattService> servicesList = gatt.getServices();

        // 处理Service
        if (uuid_service == null || uuid_service.isEmpty()) {
            Stream<String> ids = Arrays.stream(servicesList.toArray()).map((o) -> ((BluetoothGattService) o).getUuid().toString());

            String availableServices = String.join(",", ids.toArray(String[]::new));

            if (servicesList.size() > 0) {
                realServiceCharacterUuid[0] = servicesList.get(0).getUuid().toString();

                XLog.v("当前设备的服务有: " + availableServices + "，选取第一个作为默认值：" + servicesList.get(0).getUuid());
            }
        } else if (uuid_service.length() == 4) // 处理短名如：180a （Device Information service）
        {
            // 蓝牙的UUID格式均为： 0000XXXX-0000-1000-8000-00805f9b34fb
            // https://stackoverflow.com/questions/47887029/bluetooth-low-energy-ble-how-to-get-uuids-of-service-characteristic-and-des
            // UUID查表：https://www.bluetooth.com/specifications/assigned-numbers/
            realServiceCharacterUuid[0] = "0000" + uuid_service + "-0000-1000-8000-00805f9b34fb";
        } else if (uuid_service.length() == 6 && uuid_service.startsWith("0x")) // 处理短名如：0x180a
        {
            realServiceCharacterUuid[0] = "0000" + uuid_service.substring(2, 4) + "-0000-1000-8000-00805f9b34fb";
        }


        // 处理Character
        if (uuid_characteristic == null || uuid_characteristic.isEmpty()) {

            BluetoothGattService selectedService = servicesList.get(0);

            var service = servicesList.stream().filter((s) -> s.getUuid().toString().equalsIgnoreCase(realServiceCharacterUuid[0])).findFirst();

            if (service.isPresent()) {
                selectedService = service.get();
            }

            List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = selectedService.getCharacteristics();

            Stream<String> ids = Arrays.stream(bluetoothGattCharacteristicList.toArray()).map((o) -> ((BluetoothGattCharacteristic) o).getUuid().toString());

            String availableChars = String.join(",", ids.toArray(String[]::new));

            if (bluetoothGattCharacteristicList.size() > 0) {
                realServiceCharacterUuid[1] = bluetoothGattCharacteristicList.get(0).getUuid().toString();

                XLog.v("服务（" + realServiceCharacterUuid[0] + "）中的特性有: " + availableChars + "，选取第一个作为默认值：" + bluetoothGattCharacteristicList.get(0).getUuid());
            }
        } else if (uuid_characteristic.length() == 4) // 处理短名如：2a00 （Device Name）
        {
            // 蓝牙的UUID格式均为： 0000XXXX-0000-1000-8000-00805f9b34fb
            // https://stackoverflow.com/questions/47887029/bluetooth-low-energy-ble-how-to-get-uuids-of-service-characteristic-and-des
            // UUID查表：https://www.bluetooth.com/specifications/assigned-numbers/
            realServiceCharacterUuid[1] = "0000" + uuid_characteristic + "-0000-1000-8000-00805f9b34fb";
        } else if (uuid_characteristic.length() == 6 && uuid_characteristic.startsWith("0x")) // 处理短名如：0x2a00
        {
            realServiceCharacterUuid[1] = "0000" + uuid_characteristic.substring(2, 4) + "-0000-1000-8000-00805f9b34fb";
        }
    }

    private String stringifyByteArray(byte[] data) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);

            if (i != data.length - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private void startScan() {

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {

            XLog.v("检查蓝牙功能");

            // 检查设备是否可用
            checkBleErrors();

            // 执行扫描
            BleManager.getInstance().scan(new BleScanCallback() {
                @Override
                public void onScanStarted(boolean success) {
                    XLog.v("开始扫描蓝牙设备");
                }

                @Override
                public void onScanning(BleDevice bleDevice) {
                    XLog.v("扫描到设备：" + bleDevice.getKey());
                }

                @Override
                public void onScanFinished(List<BleDevice> scanResultList) {

                    // 默认返回空的数组
                    String json = "[]";

                    // 处理返回值，这里为了更多应用场景，返回所有可以获得的设备信息
                    // 这里避免权限问题，仅获取最基础的信息，包含name、mac、rssi、timestamp和key（name+mac）
                    if (scanResultList != null) {
                        // 过滤出有名称的，和系统设置页面保持一致
                        BleDeviceInfo[] devices = scanResultList.stream().filter((d) -> d.getName() != null).map(BleDeviceInfo::create).toArray(BleDeviceInfo[]::new);
                        json = JSON.toJSONString(devices);
                    }

                    XLog.v("扫描完毕，发现的蓝牙设备列表：" + json);

                    sendResultAndFinish(json);
                }
            });
        });
    }

    private void startRead(String mac, String uuid_service, String uuid_characteristic) {

        final String[] realServiceCharacterUuid = new String[2];
        realServiceCharacterUuid[0] = uuid_service;
        realServiceCharacterUuid[1] = uuid_characteristic;

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {

                    XLog.v("检查蓝牙功能");

                    // 检查设备是否可用
                    checkBleErrors();

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {
                            XLog.v("开始连接蓝牙设备：" + mac);
                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            XLog.e("蓝牙设备连接过程中出错，设备: " + bleDevice.getName(), exception);

                            sendResultAndFinish(BleError.from(exception));
                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            fillDefaultServiceAndCharacters(gatt, realServiceCharacterUuid, uuid_service, uuid_characteristic);

                            XLog.v("连接成功，开始读取。设备：" + bleDevice.getName() + "，服务：" + realServiceCharacterUuid[0] + "，特性：" + realServiceCharacterUuid[1]);

                            BleManager.getInstance().read(
                                    bleDevice,
                                    realServiceCharacterUuid[0],
                                    realServiceCharacterUuid[1],
                                    new BleReadCallback() {
                                        @Override
                                        public void onReadSuccess(byte[] data) {

                                            XLog.v("读取到数据");

                                            if (data != null) {
                                                XLog.v("接收到有效数据，内容：" + MiscUtilities.bytesToHex(data));
                                                sendResultAndFinish(data);
                                            } else {
                                                XLog.v("没有接收到有效数据");
                                                sendResultAndFinish("");
                                            }
                                        }

                                        @Override
                                        public void onReadFailure(BleException exception) {
                                            XLog.e("从蓝牙设备读取数据过程中出错，设备：" + bleDevice.getName(), exception);
                                            sendResultAndFinish(BleError.from(exception));
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            XLog.v("设备（" + bleDevice.getName() + "）已断开连接，当前状态：" + status);
                        }
                    });
                }
        );
    }

    private void registryNotify(String mac, String uuid_service, String uuid_characteristic) {

        final String[] realServiceCharacterUuid = new String[2];
        realServiceCharacterUuid[0] = uuid_service;
        realServiceCharacterUuid[1] = uuid_characteristic;

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {

                    XLog.v("检查蓝牙功能");

                    // 检查设备是否可用
                    checkBleErrors();

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {
                            XLog.v("开始连接蓝牙设备：" + mac);
                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            XLog.e("蓝牙设备连接过程中出错，设备: " + bleDevice.getName(), exception);

                            sendResultAndFinish(BleError.from(exception));
                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            fillDefaultServiceAndCharacters(gatt, realServiceCharacterUuid, uuid_service, uuid_characteristic);

                            XLog.v("连接成功，开始订阅Notify。设备：" + bleDevice.getName() + "，服务：" + realServiceCharacterUuid[0] + "，特性：" + realServiceCharacterUuid[1]);

                            BleManager.getInstance().notify(
                                    bleDevice,
                                    realServiceCharacterUuid[0],
                                    realServiceCharacterUuid[1],
                                    new BleNotifyCallback() {
                                        @Override
                                        public void onCharacteristicChanged(byte[] data) {

                                            XLog.v("接受到Notify数据");

                                            if (data != null) {
                                                XLog.v("接收到有效数据，内容：" + MiscUtilities.bytesToHex(data));
                                                sendResultAndFinish(data);
                                            } else {
                                                XLog.v("没有接收到有效数据");
                                                sendResultAndFinish("");
                                            }
                                        }

                                        @Override
                                        public void onNotifyFailure(BleException exception) {
                                            XLog.e("通过蓝牙订阅Notify时出错，设备：" + bleDevice.getName(), exception);
                                            sendResultAndFinish(BleError.from(exception));
                                        }

                                        @Override
                                        public void onNotifySuccess() {
                                            // 打开通知操作成功
                                            __notifyKeys.add(new RegistryInfo(bleDevice, realServiceCharacterUuid[0], realServiceCharacterUuid[1]));
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            XLog.v("设备（" + bleDevice.getName() + "）已断开连接，当前状态：" + status);
                        }
                    });
                }
        );
    }

    private void registryIndicate(String mac, String uuid_service, String uuid_characteristic) {

        final String[] realServiceCharacterUuid = new String[2];
        realServiceCharacterUuid[0] = uuid_service;
        realServiceCharacterUuid[1] = uuid_characteristic;

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {

                    XLog.v("检查蓝牙功能");

                    // 检查设备是否可用
                    checkBleErrors();

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {
                            XLog.v("开始连接蓝牙设备：" + mac);
                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            XLog.e("蓝牙设备连接过程中出错，设备: " + bleDevice.getName(), exception);

                            sendResultAndFinish(BleError.from(exception));
                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            fillDefaultServiceAndCharacters(gatt, realServiceCharacterUuid, uuid_service, uuid_characteristic);

                            XLog.v("连接成功，开始订阅Indicate。设备：" + bleDevice.getName() + "，服务：" + realServiceCharacterUuid[0] + "，特性：" + realServiceCharacterUuid[1]);

                            BleManager.getInstance().indicate(
                                    bleDevice,
                                    realServiceCharacterUuid[0],
                                    realServiceCharacterUuid[1],
                                    new BleIndicateCallback() {
                                        @Override
                                        public void onCharacteristicChanged(byte[] data) {

                                            XLog.v("接受到Indicate数据");

                                            if (data != null) {
                                                XLog.v("接收到有效数据，内容：" + MiscUtilities.bytesToHex(data));
                                                sendResultAndFinish(data);
                                            } else {
                                                XLog.v("没有接收到有效数据");
                                                sendResultAndFinish("");
                                            }
                                        }

                                        @Override
                                        public void onIndicateFailure(BleException exception) {
                                            XLog.e("通过蓝牙订阅Indicate时出错，设备：" + bleDevice.getName(), exception);
                                            sendResultAndFinish(BleError.from(exception));
                                        }

                                        @Override
                                        public void onIndicateSuccess() {
                                            // 打开通知操作成功
                                            __indicateKeys.add(new RegistryInfo(bleDevice, realServiceCharacterUuid[0], realServiceCharacterUuid[1]));
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            XLog.v("设备（" + bleDevice.getName() + "）已断开连接，当前状态：" + status);
                        }
                    });
                }
        );
    }

    private void startWrite(String mac, String uuid_service, String uuid_characteristic, String stringValue) {

        final String[] realServiceCharacterUuid = new String[2];
        realServiceCharacterUuid[0] = uuid_service;
        realServiceCharacterUuid[1] = uuid_characteristic;

        // 解析写入蓝牙的负载
        if (stringValue == null || stringValue.isEmpty()) {
            sendResultAndFinish(new BleError(-110, "PAYLOAD_IS_EMPTY"));
            return;
        }

        byte[] parsedData;

        // 解析时，优先判断十六进制，然后再处理base64.如果都不是则直接报错
        if (stringValue.toLowerCase().startsWith("0x")) {
            parsedData = MiscUtilities.hexToByteArray(stringValue);
        } else {
            try {
                parsedData = Base64.getDecoder().decode(stringValue);
            } catch (IllegalArgumentException ex) {
                sendResultAndFinish(new BleError(-110, "PAYLOAD_IS_NOT_HEX_OR_BASE64"));
                return;
            }
        }

        byte[] data = parsedData;

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                }, () -> {

                    XLog.v("检查蓝牙功能");

                    // 检查设备是否可用
                    checkBleErrors();

                    BleManager.getInstance().connect(mac, new BleGattCallback() {
                        @Override
                        public void onStartConnect() {
                            XLog.v("开始连接蓝牙设备：" + mac);
                        }

                        @Override
                        public void onConnectFail(BleDevice bleDevice, BleException exception) {
                            XLog.e("通过蓝牙发送数据时出错，设备：" + bleDevice.getName(), exception);
                            sendResultAndFinish(BleError.from(exception));
                        }

                        @Override
                        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            fillDefaultServiceAndCharacters(gatt, realServiceCharacterUuid, uuid_service, uuid_characteristic);

                            XLog.v("连接成功，开始写入数据。设备：" + bleDevice.getName() + "，服务：" + realServiceCharacterUuid[0] + "，特性：" + realServiceCharacterUuid[1]);

                            BleManager.getInstance().write(
                                    bleDevice,
                                    realServiceCharacterUuid[0],
                                    realServiceCharacterUuid[1],
                                    data,
                                    new BleWriteCallback() {
                                        @Override
                                        public void onWriteSuccess(int current, int total, byte[] justWrite) {

                                            XLog.v("数据写入完毕，总计：" + total);
                                            sendResultAndFinish("");
                                        }

                                        @Override
                                        public void onWriteFailure(BleException exception) {
                                            XLog.e("通过蓝牙发送数据时出错，设备：" + bleDevice.getName(), exception);

                                            sendResultAndFinish(BleError.from(exception));
                                        }
                                    });
                        }

                        @Override
                        public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                            XLog.v("设备（" + bleDevice.getName() + "）已断开连接，当前状态：" + status);
                        }
                    });
                }
        );
    }
}