
package com.huozige.lab.container.proxy;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.PermissionsUtility;

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

        getInterop().writeLogIntoConsole("Start ecs printer scanning.");

        PermissionsUtility.asyncRequirePermissions(this.getInterop().getActivityContext(), new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {

            final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

            if (bluetoothDevicesList != null) {
                XLog.v("Esc printers scanning completed, " + bluetoothDevicesList.length + " found.");

                @SuppressLint("MissingPermission") Stream<EscPrinterInfo> result = Arrays.stream(bluetoothDevicesList).map((conn) -> {
                    EscPrinterInfo device = new EscPrinterInfo();
                    device.deviceName = conn.getDevice().getName();
                    device.mac = conn.getDevice().getAddress();
                    return device;
                });

                callback(CallbackParams.success(JSON.toJSONString(result.toArray(EscPrinterInfo[]::new))));

            } else {
                XLog.e("Esc printers scanning failed.");
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
        logEvent("use_esc_feature", "scan");

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
        logEvent("use_esc_feature", "scanAsync");

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

        logEvent("use_esc_feature", "print");

        getInterop().writeLogIntoConsole("Start printing via ESC.");

        try {
            XLog.v("Print " + template + ", using printer " + mac + " dpi: " + printerDpi + " width: " + printerWidthMM + " CPL: " + printerNbrCharactersPerLine);
            EscPosPrinter printer = new EscPosPrinter(findPrinter(mac), printerDpi, printerWidthMM, printerNbrCharactersPerLine, new EscPosCharsetEncoding("GBK", 16));
            printer.printFormattedText(template);
            XLog.v("Template was printed via ESC.");
            printer.disconnectPrinter();

            getInterop().showToast("蓝牙打印完成，连接已断开。");
        } catch (Exception ex) {
            XLog.e("Esc print failed. Error: " + ex);
        }

        getInterop().writeLogIntoConsole("ESC template was printed.");
    }

    private BluetoothConnection findPrinter(String mac) {

        if (mac == null || mac.isEmpty()) {
            return BluetoothPrintersConnections.selectFirstPaired();
        } else {
            final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();
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
