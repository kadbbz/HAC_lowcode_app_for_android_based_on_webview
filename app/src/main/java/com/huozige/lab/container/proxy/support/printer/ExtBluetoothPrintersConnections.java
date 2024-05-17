package com.huozige.lab.container.proxy.support.printer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;

/**
 * 为了支持商米等内置打印机，需要重写获取打印机的逻辑。<a href="https://github.com/DantSu/ESCPOS-ThermalPrinter-Android/issues/80">...</a>
 */
public class ExtBluetoothPrintersConnections extends BluetoothConnections {
    /**
     * Easy way to get the first bluetooth printer paired / connected.
     *
     * @return a EscPosPrinterCommands instance
     */
    public static BluetoothConnection selectFirstPaired() throws EscPosConnectionException {
        ExtBluetoothPrintersConnections printers = new ExtBluetoothPrintersConnections();
        BluetoothConnection[] bluetoothPrinters = printers.getList();

        if (bluetoothPrinters != null) {
            for (BluetoothConnection printer : bluetoothPrinters) {
                return printer.connect();
            }
        }
        return null;
    }


    /**
     * Get a list of bluetooth printers.
     *
     * @return an array of EscPosPrinterCommands
     */
    @SuppressLint("MissingPermission")
    public BluetoothConnection[] getList() {
        BluetoothConnection[] bluetoothDevicesList = super.getList();

        if (bluetoothDevicesList == null) {
            return null;
        }

        int i = 0;
        BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
        for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
            BluetoothDevice device = bluetoothConnection.getDevice();

            int majDeviceCl = device.getBluetoothClass().getMajorDeviceClass(),
                    deviceCl = device.getBluetoothClass().getDeviceClass();

            if ((majDeviceCl == BluetoothClass.Device.Major.IMAGING && (deviceCl == 1664 || deviceCl == BluetoothClass.Device.Major.IMAGING)) || device.getName().equals("InnerPrinter")) {
                printersTmp[i++] = new BluetoothConnection(device);
            }
        }
        BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
        System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);
        return bluetoothPrinters;
    }
}
