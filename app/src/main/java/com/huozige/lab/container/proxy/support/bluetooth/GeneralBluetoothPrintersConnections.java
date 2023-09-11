package com.huozige.lab.container.proxy.support.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Set;

public class GeneralBluetoothPrintersConnections  {

    private final static String LOG_TAG = "HAC_GeneralBluetoothPrintersConnections";

    protected BluetoothAdapter bluetoothAdapter;

    /**
     * Create a new instance of BluetoothConnections
     */
    public GeneralBluetoothPrintersConnections() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Nullable
    public static BluetoothConnection selectFirstPaired() throws BluetoothConnectionException {
        GeneralBluetoothPrintersConnections printers = new GeneralBluetoothPrintersConnections();
        BluetoothConnection[] bluetoothPrinters = printers.getPrinterList();

        if (bluetoothPrinters != null && bluetoothPrinters.length>0) {
            return bluetoothPrinters[0];
        }else{
            return null;
        }

    }

    /**
     * Get a list of bluetooth printers.
     *
     * @return an array of EscPosPrinterCommands
     */
    @SuppressLint("MissingPermission")
    @Nullable
    public BluetoothConnection[] getPrinterList() {

        Log.v(LOG_TAG,"开始读取当前配对的蓝牙设备列表：");

        BluetoothConnection[] bluetoothDevicesList = getDeviceList();

        if (bluetoothDevicesList == null) {
            return null;
        }

        int i = 0;
        BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];

        Log.v(LOG_TAG,"蓝牙设备共计" + printersTmp.length +"个");

        for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
            BluetoothDevice device = bluetoothConnection.getDevice();

            int majDeviceCl = device.getBluetoothClass().getMajorDeviceClass(),
                    deviceCl = device.getBluetoothClass().getDeviceClass();

            Log.v(LOG_TAG," >> " + device.getName() +" MajorDeviceClass: " + majDeviceCl +" DeviceClass：" + deviceCl);

            if (majDeviceCl == BluetoothClass.Device.Major.IMAGING ) {
                Log.v(LOG_TAG,"发现匹配的打印机："+device.getName());
                printersTmp[i++] = new BluetoothConnection(device);
            }
        }
        BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
        System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);
        return bluetoothPrinters;
    }

    @SuppressLint("MissingPermission")
    @Nullable
    public BluetoothConnection[] getDeviceList() {
        if (this.bluetoothAdapter == null) {
            return null;
        }

        if(!this.bluetoothAdapter.isEnabled()) {
            return null;
        }

        Set<BluetoothDevice> bluetoothDevicesList = this.bluetoothAdapter.getBondedDevices();
        BluetoothConnection[] bluetoothDevices = new BluetoothConnection[bluetoothDevicesList.size()];

        if (bluetoothDevicesList.size() > 0) {
            int i = 0;
            for (BluetoothDevice device : bluetoothDevicesList) {
                bluetoothDevices[i++] = new BluetoothConnection(device);
            }
        }

        return bluetoothDevices;
    }
}
