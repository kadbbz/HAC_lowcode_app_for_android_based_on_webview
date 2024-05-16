
package com.huozige.lab.container.proxy;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.printer.ExtBluetoothPrintersConnections;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 调用蓝牙打印机（ESC协议）
 * 1.16.0
 * escBtPrinter.scan(resultCell) ：扫描打印机，返回包含有设备名称和mac的信息列表
 * escBtPrinter.print(mac, printerDpi, printerWidthMM, printerNbrCharactersPerLine, template)：执行打印操作
 * 1.17.0
 * scBtPrinter.scanAsync(resultCell) ：扫描打印机，返回包含有设备名称和mac的信息列表
 */
public class EscBtPrinterProxy extends AbstractProxy {

    @Override
    public String getName() {
        return "escBtPrinter";
    }

    private void startScan() {

        writeInfoLog("开始扫描蓝牙的ESC打印机");

        asyncRequirePermissions(new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {

            final BluetoothConnection[] bluetoothDevicesList = (new ExtBluetoothPrintersConnections()).getList();

            if (bluetoothDevicesList != null) {
                writeInfoLog("ESC打印机扫描完毕，共发现：" + bluetoothDevicesList.length + "台");

                @SuppressLint("MissingPermission") Stream<EscPrinterInfo> result = Arrays.stream(bluetoothDevicesList).map((conn) -> {
                    EscPrinterInfo device = new EscPrinterInfo();
                    device.deviceName = conn.getDevice().getName();
                    device.mac = conn.getDevice().getAddress();
                    return device;
                });

                callback(CallbackParams.success(JSON.toJSONString(result.toArray(EscPrinterInfo[]::new))));

            } else {
                writeErrorLog("ESC打印机扫描过程出错");
                callback(CallbackParams.error("Esc printers scanning failed."));
            }
        });
    }

    /**
     * 扫描蓝牙打印机并将列表返回到单元格
     * 形如：[{"deviceName":"GP-M322-B982","mac":"DC:1D:30:FB:B9:82"}]
     *
     * @param resultCell 存放打印机信息列表的单元格
     */
    @JavascriptInterface
    public void scan(String resultCell) {
        registryForFeatureUsageAnalyze("use_esc_feature", "scan");

        registryPayloadCellLocation(resultCell);

        startScan();
    }

    /**
     * 扫描蓝牙打印机并将列表返回到单元格
     * 形如：[{"deviceName":"GP-M322-B982","mac":"DC:1D:30:FB:B9:82"}]
     *
     * @param ticket 回调
     */
    @JavascriptInterface
    public void scanAsync(String ticket) {
        registryForFeatureUsageAnalyze("use_esc_feature", "scanAsync");

        registryCallbackTicket(ticket);

        startScan();
    }

    /**
     * 执行打印
     *
     * @param mac                         打印机的MAC地址，留空则使用默认打印机
     * @param printerDpi                  打印机的DPI，通常是203
     * @param printerWidthMM              打印纸的宽度（mm）
     * @param printerNbrCharactersPerLine 每行打印的最大字符数
     * @param template                    需要打印的文字模板，参考 <a href="https://github.com/DantSu/ESCPOS-ThermalPrinter-Android/tree/master">GitHub</a>
     */
    @JavascriptInterface
    public void print(String mac, int printerDpi, float printerWidthMM, int printerNbrCharactersPerLine, String template) {

        registryForFeatureUsageAnalyze("use_esc_feature", "print");

        writeInfoLog("开始使用ESC协议打印，打印机的地址为：" + mac);

        try {
            writeInfoLog("打印模板：" + template + "，dpi: " + printerDpi + " width: " + printerWidthMM + " CPL: " + printerNbrCharactersPerLine);
            EscPosPrinter printer = new EscPosPrinter(findPrinter(mac), printerDpi, printerWidthMM, printerNbrCharactersPerLine, new EscPosCharsetEncoding("GBK", 16));
            printer.printFormattedText(template);
            writeInfoLog("打印完成");
            printer.disconnectPrinter();

            showLongToast("蓝牙打印完成，连接已断开。");

        } catch (Exception ex) {
            writeErrorLog("ESC协议打印出错：" + ex);
        }
    }

    private BluetoothConnection findPrinter(String mac) throws EscPosConnectionException {

        if (mac == null || mac.isEmpty()) {
            return ExtBluetoothPrintersConnections.selectFirstPaired();
        } else {
            final BluetoothConnection[] bluetoothDevicesList = (new ExtBluetoothPrintersConnections()).getList();
            if (bluetoothDevicesList != null) {
                var match = Arrays.stream(bluetoothDevicesList).filter((d) -> d.getDevice().getAddress().equalsIgnoreCase(mac)).findFirst();
                if (match.isPresent()) {
                    return match.get();
                }
            }
        }
        return null;
    }


    public static class EscPrinterInfo {
        public String mac;
        public String deviceName;
    }

}
