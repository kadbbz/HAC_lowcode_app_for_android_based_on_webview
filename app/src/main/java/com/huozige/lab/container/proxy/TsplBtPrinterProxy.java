package com.huozige.lab.container.proxy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.webkit.JavascriptInterface;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.printer.ExtBluetoothPrintersConnections;
import com.huozige.lab.container.proxy.support.printer.TSPLCommandBuilder;
import com.huozige.lab.container.proxy.support.printer.TSPLPrintRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 调用蓝牙打印机（TSPL协议）
 * TSPL 是 TSC 标签打印机的命令语言，用于打印标签、条码、二维码等
 *
 * 1.20.3
 * tsplBtPrinter.scan(resultCell) ：扫描打印机，返回包含有设备名称和mac的信息列表
 * tsplBtPrinter.scanAsync(ticket) ：扫描打印机，返回包含有设备名称和mac的信息列表
 * tsplBtPrinter.print(mac, commands) ：执行TSPL打印操作
 */
public class TsplBtPrinterProxy extends AbstractProxy {

    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public String getName() {
        return "tsplBtPrinter";
    }

    /**
     * 执行TSPL打印
     *
     * TSPL 命令示例：
     * SIZE 60 mm, 40 mm
     * GAP 2 mm, 0 mm
     * DIRECTION 0
     * REFERENCE 0,0
     * OFFSET 2 mm
     * CLS
     * TEXT 10,10,"TSS24.BF2",0,1,1,"Hello TSPL"
     * BARCODE 10,50,"128",100,1,0,2,4,"ABC123"
     * QRCODE 10,150,L,8,A,0,"QR Content"
     * PRINT 1,1
     *
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @JavascriptInterface
    public void print(String mac, String commands) {
        registryForFeatureUsageAnalyze("use_tspl_feature", "printStranded");

        startPrint(mac, commands);
    }



    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @JavascriptInterface
    public void print(String requestString) {

        registryForFeatureUsageAnalyze("use_tspl_feature", "printSimple");

        TSPLPrintRequest request = JSON.parseObject(requestString, TSPLPrintRequest.class);

        String mac = request.getMacAddress();

        //String commands = buildTSPLCommands("hello你好123+", "123456", 1);
        String commands = TSPLCommandBuilder.buildTSPLCommands(request);

        startPrint(mac, commands);
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private void startPrint(String mac, String commands) {

        writeInfoLog("开始使用TSPL协议打印，打印机的地址为：" + mac);

        BluetoothSocket socket = null;
        OutputStream outputStream = null;

        try {

            commands = commands.replace("\r\n", "\n").replace("\r", "\n");
            if (!commands.endsWith("\n")) {
                commands = commands + "\n";
            }

            writeInfoLog("打印命令：" + commands);

            BluetoothDevice printer = findPrinter(mac);
            if (printer == null) {
                writeErrorLog("未找到指定的打印机");
                showLongToast("未找到指定的打印机");
                return;
            }

            // 创建蓝牙socket连接

            socket = printer.createRfcommSocketToServiceRecord(SPP_UUID);
            socket.connect();

            outputStream = socket.getOutputStream();

            // 发送TSCL命令（使用GB2312编码以支持中文）
            // TSPL打印机通常使用GB2312编码，某些打印机不支持ENCODING命令
            outputStream.write(commands.getBytes("GBK"));
            outputStream.flush();

            writeInfoLog("TSPL打印命令发送完成");

            showLongToast("TSCL蓝牙打印完成，连接已断开。");

        } catch (Exception ex) {
            writeErrorLog("TSCL协议打印出错：" + ex.getMessage());
            showLongToast("TSCL打印失败：" + ex.getMessage());
        } finally {
            // 关闭连接
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                writeErrorLog("关闭连接时出错：" + e.getMessage());
            }
        }
    }

    /**
     * 查找指定的蓝牙打印机
     *
     * @param mac MAC地址，为空则返回第一个配对的打印机
     * @return 蓝牙设备
     */
    @SuppressLint("MissingPermission")
    private BluetoothDevice findPrinter(String mac) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            writeErrorLog("设备不支持蓝牙");
            return null;
        }

        if (mac == null || mac.isEmpty()) {
            // 获取第一个配对的打印机设备
            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
                if (isPrinterDevice(device)) {
                    return device;
                }
            }
        } else {
            // 根据MAC地址查找
            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
                if (device.getAddress().equalsIgnoreCase(mac) && isPrinterDevice(device)) {
                    return device;
                }
            }
        }
        return null;
    }

    /**
     * 判断是否为打印机设备
     *
     * @param device 蓝牙设备
     * @return 是否为打印机
     */
    @SuppressLint("MissingPermission")
    private boolean isPrinterDevice(BluetoothDevice device) {
        int majDeviceCl = device.getBluetoothClass().getMajorDeviceClass();
        int deviceCl = device.getBluetoothClass().getDeviceClass();

        // 打印机设备类型判断
        return (majDeviceCl == android.bluetooth.BluetoothClass.Device.Major.IMAGING
                && (deviceCl == 1664 || deviceCl == android.bluetooth.BluetoothClass.Device.Major.IMAGING))
                || device.getName().equals("InnerPrinter");
    }

    private static String buildTSPLCommands(String text, String barcode, int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("SIZE 40 mm, 30 mm\n");      // 标签尺寸
        sb.append("GAP 2 mm, 0\n");            // 间隙
        sb.append("CLS\n");                    // 清除缓冲区
        sb.append("TEXT 5,10,\"TSS24.BF2\",0,1,1,\"").append(text).append("\"\n");
        sb.append("BARCODE 20,50,\"128\",100,1,0,2,2,\"").append(barcode).append("\"\n");
        sb.append("PRINT ").append(count).append("\n");                // 打印1张
        return sb.toString();
    }

}
