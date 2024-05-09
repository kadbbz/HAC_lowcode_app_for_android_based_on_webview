package com.huozige.lab.container.proxy.support.scanner;

import android.app.Application;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;

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
import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.huozige.lab.container.utilities.StringConvertUtility;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class BleHelper {

    static BleHelper __instance;

    private final HashMap<String, BleDeviceCacheInfo> _connectedDevices = new HashMap<>();

    private BleHelper() {

    }

    /**
     * 获取Ble操作帮助类的实例，包含初始化和功能检查
     *
     * @param app 上下文
     * @return 用于操作的帮助类实例
     */
    public static BleHelper getInstance(Application app) {

        if (__instance == null) {

            BleManager.getInstance().enableLog(true)
                    .setReConnectCount(1, 5000)
                    .setSplitWriteNum(20)
                    .setConnectOverTime(10000)
                    .setOperateTimeout(5000)
                    .init(app);

            XLog.v("BLE操作接口已完成初始化");

            // 初始化
            __instance = new BleHelper();
        }

        return __instance;
    }

    /**
     * 检查BLE能力是否就绪
     *
     * @return 是否可用
     */
    public boolean checkBleReady() {
        if (!BleManager.getInstance().isSupportBle()) {
            XLog.e("当前设备的蓝牙设备不可用：BLE_NOT_SUPPORTED");

            return false;
        }

        if (!BleManager.getInstance().isBlueEnable()) {
            XLog.e("当前设备的蓝牙被禁用：BLE_DISABLED");
            return false;
        }

        return true;
    }

    public void startScan(Context context, BleCallback callback) {

        PermissionsUtility.asyncRequirePermissions(context, new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {

            XLog.v("开始扫描");

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

                    XLog.v("扫描完毕，开始整理数据");

                    // 默认返回空的数组
                    String json = "[]";

                    // 处理返回值，这里为了更多应用场景，返回所有可以获得的设备信息
                    // 这里避免权限问题，仅获取最基础的信息，包含name、mac、rssi、timestamp和key（name+mac）
                    if (scanResultList != null) {
                        // 过滤出有名称的，和系统设置页面保持一致
                        BleDeviceInfo[] devices = scanResultList.stream().filter((d) -> d.getName() != null).map(BleDeviceInfo::create).toArray(BleDeviceInfo[]::new);
                        json = JSON.toJSONString(devices);
                    }

                    XLog.v("即将返回有名称的蓝牙设备列表：" + json);

                    callback.onSuccess(new BleResultData(json, null));
                }
            });
        });
    }


    private void startConnect(Context context, String mac, BleGattCallback action) {

        PermissionsUtility.asyncRequirePermissions(context, new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {

            XLog.v("开始获取BLE设备的连接：" + mac);

            var deviceCache = _connectedDevices.get(mac);

            // 优先从缓存中读取
            if (deviceCache != null) {

                XLog.v("设备连接尚未断开，直接复用现有连接");

                action.onConnectSuccess(deviceCache.device, deviceCache.gatt, 0);

            } else {

                XLog.v("开始连接设备：" + mac);

                BleManager.getInstance().connect(mac, new BleGattCallback() {
                    @Override
                    public void onStartConnect() {
                        XLog.v("开始连接蓝牙设备：" + mac);

                        action.onStartConnect();
                    }

                    @Override
                    public void onConnectFail(BleDevice bleDevice, BleException exception) {
                        XLog.e("蓝牙设备连接过程中出错，设备: " + bleDevice.getName() + " \r\n%s", exception);

                        action.onConnectFail(bleDevice, exception);
                    }

                    @Override
                    public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                        XLog.v("蓝牙设备已成功连接：" + mac + " (" + status + ")");

                        // 加入缓存
                        _connectedDevices.put(mac, new BleDeviceCacheInfo(bleDevice, gatt));

                        // 添加100秒等待，避免蓝牙出错
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        action.onConnectSuccess(bleDevice, gatt, status);
                    }

                    @Override
                    public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {

                        XLog.v("设备（" + device.getName() + "）已断开连接，当前状态：" + status + "，类型：" + (isActiveDisConnected ? "主动断开" : "被动断开"));

                        // 从缓存中删除
                        _connectedDevices.remove(mac);

                        action.onDisConnected(isActiveDisConnected, device, gatt, status);
                    }
                });
            }
        });

    }

    /**
     * 开始执行读取操作
     *
     * @param context             上下文
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param callback            读取回调
     */
    public void startRead(Context context, String mac, String uuid_service, String uuid_characteristic, BleCallback callback) {

        XLog.v("开始执行Read：" + mac + " -> " + uuid_service + " -> " + uuid_characteristic);

        startConnect(context, mac, new BleGattCallback() {


            @Override
            public void onStartConnect() {

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                callback.onError(BleError.from(exception));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                var uuids = regularUUIDsViaGatt(gatt, uuid_service, uuid_characteristic);

                XLog.v("连接成功，开始读取。设备：" + bleDevice.getName() + "，服务：" + uuids.service + "，特性：" + uuids.characteristic);

                BleManager.getInstance().read(
                        bleDevice,
                        uuids.service,
                        uuids.characteristic,
                        new BleReadCallback() {
                            @Override
                            public void onReadSuccess(byte[] data) {

                                XLog.v("读取到数据");

                                if (data != null) {
                                    XLog.v("接收到有效数据，内容：" + StringConvertUtility.byteArrayToHexString(data));
                                    callback.onSuccess(new BleResultData(null, data));
                                } else {
                                    XLog.v("没有接收到有效数据");
                                    callback.onSuccess(new BleResultData(null, null));
                                }
                            }

                            @Override
                            public void onReadFailure(BleException exception) {
                                XLog.e("从蓝牙设备读取数据过程中出错，设备：" + bleDevice.getName() + " \r\n%s", exception);
                                callback.onError(BleError.from(exception));
                            }
                        });
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {

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
    public boolean unregisterNotify(String mac, String uuid_service, String uuid_characteristic) {

        var deviceCache = _connectedDevices.get(mac);

        if (deviceCache != null) {

            var uuids = regularUUIDsViaGatt(deviceCache.gatt, uuid_service, uuid_characteristic);

            BleManager.getInstance().stopNotify(deviceCache.device, uuids.service, uuids.characteristic);

            XLog.v("设备：" + mac + " 的Notify已停止监听，特征：" + uuids.characteristic);

            return true;
        } else {
            XLog.e("该设备：" + mac + " 已断开连接，无法停止监听Notify");
            return false;
        }
    }

    /**
     * 开始监听notify
     *
     * @param context             上下文
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param callback            notify的回调
     */
    public void registryNotify(Context context, String mac, String uuid_service, String uuid_characteristic, BleCallback callback) {

        XLog.v("开始订阅Notify：" + mac + " -> " + uuid_service + " -> " + uuid_characteristic);
        startConnect(context, mac, new BleGattCallback() {
            @Override
            public void onStartConnect() {

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                callback.onError(BleError.from(exception));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                var uuids = regularUUIDsViaGatt (gatt, uuid_service, uuid_characteristic);

                XLog.v("连接成功，开始订阅Notify。设备：" + bleDevice.getName() + "，服务：" + uuids.service + "，特性：" + uuids.characteristic);

                BleManager.getInstance().notify(
                        bleDevice,
                        uuids.service,
                        uuids.characteristic,
                        new BleNotifyCallback() {
                            @Override
                            public void onCharacteristicChanged(byte[] data) {

                                XLog.v("接受到Notify数据");

                                if (data != null) {
                                    XLog.v("接收到有效数据，内容：" + StringConvertUtility.byteArrayToHexString(data));

                                } else {
                                    XLog.v("没有接收到有效数据");
                                }

                                callback.onSuccess(new BleResultData(null, data));
                            }

                            @Override
                            public void onNotifyFailure(BleException exception) {
                                XLog.e("通过蓝牙订阅Notify时出错，设备：" + bleDevice.getName() + " \r\n%s", exception);
                                callback.onError(BleError.from(exception));
                            }

                            @Override
                            public void onNotifySuccess() {

                            }
                        });
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {

            }
        });

    }

    /**
     * 停止监听Indicate
     *
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     */
    public boolean unregisterIndicate(String mac, String uuid_service, String uuid_characteristic) {

        var deviceCache = _connectedDevices.get(mac);

        if (deviceCache != null ) {

            var uuids = regularUUIDsViaGatt(deviceCache.gatt, uuid_service, uuid_characteristic);

            BleManager.getInstance().stopIndicate(deviceCache.device, uuids.service, uuids.characteristic);

            XLog.v("设备：" + mac + " 的Indicate已停止监听，特征：" + uuids.characteristic);

            return true;
        } else {
            XLog.e("该设备：" + mac + " 已断开连接，无法停止监听Indicate");
            return false;
        }
    }

    /**
     * 开始监听Indicate
     *
     * @param context             上下文
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param callback            Indicate的回调
     */
    public void registryIndicate(Context context, String mac, String uuid_service, String uuid_characteristic, BleCallback callback) {

        XLog.v("开始订阅Notify：" + mac + " -> " + uuid_service + " -> " + uuid_characteristic);
        startConnect(context, mac, new BleGattCallback() {
            @Override
            public void onStartConnect() {

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                callback.onError(BleError.from(exception));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                var uuids = regularUUIDsViaGatt(gatt, uuid_service, uuid_characteristic);

                XLog.v("连接成功，开始订阅Indicate。设备：" + bleDevice.getName() + "，服务：" + uuids.service + "，特性：" + uuids.characteristic);

                BleManager.getInstance().indicate(
                        bleDevice,
                        uuids.service,
                        uuids.characteristic,
                        new BleIndicateCallback() {
                            @Override
                            public void onCharacteristicChanged(byte[] data) {

                                XLog.v("接受到Indicate数据");

                                if (data != null) {
                                    XLog.v("接收到有效数据，内容：" + StringConvertUtility.byteArrayToHexString(data));
                                } else {
                                    XLog.v("没有接收到有效数据");
                                }

                                callback.onSuccess(new BleResultData(null, data));
                            }

                            @Override
                            public void onIndicateFailure(BleException exception) {
                                XLog.e("通过蓝牙订阅Indicate时出错，设备：" + bleDevice.getName() + " \r\n%s", exception);
                                callback.onError(BleError.from(exception));
                            }

                            @Override
                            public void onIndicateSuccess() {

                            }
                        });
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {

            }
        });

    }

    /**
     * 开始写入
     *
     * @param context             上下午呢
     * @param mac                 MAC地址
     * @param uuid_service        服务ID
     * @param uuid_characteristic 特征ID
     * @param stringValue         需要写入的内容（支持0x开头的十六进制和BASE64编码两种形式）
     * @param callback            写入的回调
     */
    public void startWrite(Context context, String mac, String uuid_service, String uuid_characteristic, String stringValue, BleCallback callback) {

        // 解析写入蓝牙的负载
        if (stringValue == null || stringValue.isEmpty()) {
            callback.onError(new BleError(-110, "PAYLOAD_IS_EMPTY"));
            return;
        }

        byte[] parsedData;

        // 解析时，优先判断十六进制，然后再处理base64.如果都不是则直接报错
        if (stringValue.toLowerCase().startsWith("0x")) {
            parsedData = StringConvertUtility.hexStringToByteArray(stringValue);
        } else {
            try {
                parsedData = Base64.getDecoder().decode(stringValue);
            } catch (IllegalArgumentException ex) {
                callback.onError(new BleError(-110, "PAYLOAD_IS_NOT_HEX_OR_BASE64"));
                return;
            }
        }

        byte[] data = parsedData;

        startConnect(context, mac, new BleGattCallback() {
            @Override
            public void onStartConnect() {

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                callback.onError(BleError.from(exception));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                var uuids = regularUUIDsViaGatt(gatt, uuid_service, uuid_characteristic);

                XLog.v("连接成功，开始写入数据。设备：" + bleDevice.getName() + "，服务：" + uuids.service + "，特性：" + uuids.characteristic);

                BleManager.getInstance().write(
                        bleDevice,
                        uuids.service,
                        uuids.characteristic,
                        data,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {

                                XLog.v("数据写入完毕，总计：" + total);
                                callback.onSuccess(new BleResultData("", null));
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {
                                XLog.e("通过蓝牙发送数据时出错，设备：" + bleDevice.getName() + " \r\n%s", exception);
                                callback.onError(BleError.from(exception));
                            }
                        });
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {

            }
        });

    }

    private BleGattUUIDs regularUUIDsViaGatt(BluetoothGatt gatt, String uuid_service, String uuid_characteristic) {

        BleGattUUIDs result = new BleGattUUIDs(uuid_service, uuid_characteristic);

        List<BluetoothGattService> servicesList = gatt.getServices();

        // 处理Service
        if (uuid_service == null || uuid_service.isEmpty()) {
            Stream<String> ids = Arrays.stream(servicesList.toArray()).map((o) -> ((BluetoothGattService) o).getUuid().toString());

            String availableServices = String.join(",", ids.toArray(String[]::new));

            if (!servicesList.isEmpty()) {
                result.service = servicesList.get(0).getUuid().toString();

                XLog.v("当前设备的服务有: " + availableServices + "，选取第一个作为默认值：" + servicesList.get(0).getUuid());
            }
        } else if (uuid_service.length() == 4) // 处理短名如：180a （Device Information service）
        {
            // 蓝牙的UUID格式均为： 0000XXXX-0000-1000-8000-00805f9b34fb
            // https://stackoverflow.com/questions/47887029/bluetooth-low-energy-ble-how-to-get-uuids-of-service-characteristic-and-des
            // UUID查表：https://www.bluetooth.com/specifications/assigned-numbers/
            result.service = "0000" + uuid_service + "-0000-1000-8000-00805f9b34fb";
        } else if (uuid_service.length() == 6 && uuid_service.startsWith("0x")) // 处理短名如：0x180a
        {
            result.service = "0000" + uuid_service.substring(2, 4) + "-0000-1000-8000-00805f9b34fb";
        }


        // 处理Character
        if (uuid_characteristic == null || uuid_characteristic.isEmpty()) {

            BluetoothGattService selectedService = servicesList.get(0);

            var service = servicesList.stream().filter((s) -> s.getUuid().toString().equalsIgnoreCase(result.service)).findFirst();

            if (service.isPresent()) {
                selectedService = service.get();
            }

            List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = selectedService.getCharacteristics();

            Stream<String> ids = Arrays.stream(bluetoothGattCharacteristicList.toArray()).map((o) -> ((BluetoothGattCharacteristic) o).getUuid().toString());

            String availableChars = String.join(",", ids.toArray(String[]::new));

            if (!bluetoothGattCharacteristicList.isEmpty()) {
                result.characteristic = bluetoothGattCharacteristicList.get(0).getUuid().toString();

                XLog.v("服务（" + result.service + "）中的特性有: " + availableChars + "，选取第一个作为默认值：" + bluetoothGattCharacteristicList.get(0).getUuid());
            }
        } else if (uuid_characteristic.length() == 4) // 处理短名如：2a00 （Device Name）
        {
            // 蓝牙的UUID格式均为： 0000XXXX-0000-1000-8000-00805f9b34fb
            // https://stackoverflow.com/questions/47887029/bluetooth-low-energy-ble-how-to-get-uuids-of-service-characteristic-and-des
            // UUID查表：https://www.bluetooth.com/specifications/assigned-numbers/
            result.characteristic = "0000" + uuid_characteristic + "-0000-1000-8000-00805f9b34fb";
        } else if (uuid_characteristic.length() == 6 && uuid_characteristic.startsWith("0x")) // 处理短名如：0x2a00
        {
            result.characteristic = "0000" + uuid_characteristic.substring(2, 4) + "-0000-1000-8000-00805f9b34fb";
        }

        return result;
    }

    /**
     * 用于JSON序列化后返回的设备信息
     */
    public static class BleDeviceInfo {
        public String mac;
        public String name;
        public int rssi;
        public String key;
        public long timestampNanos;

        public static BleDeviceInfo create(BleDevice device) {

            BleDeviceInfo info = new BleDeviceInfo();

            info.key = device.getKey();
            info.name = device.getName();
            info.mac = device.getMac();
            info.rssi = device.getRssi();
            info.timestampNanos = device.getTimestampNanos();

            return info;

        }
    }

    /**
     * BLE相关的异常或错误
     */
    public static class BleError {
        public int code;
        public String description;

        public BleError(int code, String desc) {
            this.code = code;
            this.description = desc;
        }

        public static BleError from(BleException ex) {
            return new BleError(ex.getCode(), ex.getDescription());
        }

        @NonNull
        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    /**
     * Gatt中记述的服务和特征ID
     */
    public static class BleGattUUIDs {

        public BleGattUUIDs(String service, String characteristic) {
            this.characteristic = characteristic;
            this.service = service;
        }

        /**
         * 服务ID
         */
        public String service;

        /**
         * 特征ID
         */
        public String characteristic;
    }

    /**
     * 缓存的Ble设备信息
     */
    public static class BleDeviceCacheInfo {

        public BleDeviceCacheInfo(BleDevice device, BluetoothGatt gatt) {
            this.device = device;
            this.gatt = gatt;
        }

        /**
         * 设备对象
         */
        public final BleDevice device;

        /**
         * 设备Gatt信息
         */
        private final BluetoothGatt gatt;
    }

    /**
     * BLE操作中设备返回的结果
     */
    public static class BleResultData {

        public BleResultData(String string, byte[] byteArray) {
            this.payloadAsByteArray = byteArray;
            this.payloadAsString = string;
        }

        /**
         * 字符串类型的信息
         */
        public String payloadAsString;

        /**
         * byte数组类型的信息
         */
        public byte[] payloadAsByteArray;

    }

    /**
     * BLE操作相关回调
     */
    public abstract static class BleCallback {
        /**
         * 操作成功
         *
         * @param data 设备传来的数据
         */
        public abstract void onSuccess(BleResultData data);

        /**
         * 操作失败
         *
         * @param error 异常或者错误
         */
        public abstract void onError(BleError error);
    }

}
