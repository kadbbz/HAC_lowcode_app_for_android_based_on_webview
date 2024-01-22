package com.huozige.lab.container.proxy.support.scanner;

import com.clj.fastble.data.BleDevice;

public class BleDeviceInfo {
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


