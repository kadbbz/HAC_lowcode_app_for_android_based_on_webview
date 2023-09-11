package com.huozige.lab.container.proxy;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.utilities.PermissionsUtility;

/**
 * 基于ESC/POS协议的标准打印机操作接口
 * 1.12.0
 * escPosPrinter.BluetoothQuickPrint()：使用默认的蓝牙打印机执行打印和切纸
 */
public class EscPosPrinterProxy extends AbstractProxy {

    private final static String LOG_TAG = "HAC_ECSPOSPrinterProxy";

    @Override
    public String getName() {
        return "escPosPrinter";
    }

    /**
     * 在蓝牙打印机上完成打印操作，自动切纸
     *
     * @param printerDpi           DPI
     * @param printingWidthMM      页面宽度（mm）
     * @param nbrCharactersPerLine 每行包含的文字数量
     * @param cmd                  结构化的打印文本
     */
    @JavascriptInterface
    public void BluetoothQuickPrint(int printerDpi, float printingWidthMM, int nbrCharactersPerLine, String cmd) {
        // 申请蓝牙权限
        PermissionsUtility.asyncRequirePermissions(this.getInterop().getActivityContext(), new String[]{
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_CONNECT
        }, () -> {

            try {
                // 获取默认的蓝牙打印机，即蓝牙配对列表中第一台蓝牙打印机
                DeviceConnection  printer = BluetoothPrintersConnections.selectFirstPaired();

                // 先检查打印机是否可用
                if (validateDefaultPrinter(printer)) {

                    Log.v(LOG_TAG, "即将使用蓝牙完成打印");
                    // 执行打印
                    print(printer, printerDpi, printingWidthMM, nbrCharactersPerLine, cmd);
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, e.toString());
                showToast(getInterop().getActivityContext().getString(R.string.ui_message_bluetooth_printer_error) + e.getMessage());
            }


        });
    }

    /**
     * 检查默认打印机的状态，不可用的话，弹出提示消息
     *
     * @param printer 打印机连接
     * @return 是否可用
     */
    private boolean validateDefaultPrinter(DeviceConnection printer) {

        // 匹配的打印机列表为空
        if (printer == null) {
            showToast(R.string.ui_message_bluetooth_printer_not_available);
            return false;
        }

        // 默认打印机已断开
        if (!printer.isConnected()) {
            showToast(R.string.ui_message_bluetooth_printer_not_connected);
            return false;
        }

        return true;
    }

    /**
     * 执行打印
     *
     * @param printer              打印机连接（支持蓝牙、USB、TCP等）
     * @param printerDpi           DPI
     * @param printingWidthMM      页面宽度（mm）
     * @param nbrCharactersPerLine 每行字数
     * @param cmd                  结构化打印内容
     */
    private void print(DeviceConnection printer, int printerDpi, float printingWidthMM, int nbrCharactersPerLine, String cmd) throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {

        Log.v(LOG_TAG, "设置蓝牙打印机参数（" + printerDpi + "/" + printingWidthMM + "/" + nbrCharactersPerLine + "）");

        // 参照文档：https://github.com/DantSu/ESCPOS-ThermalPrinter-Android
        EscPosPrinter epp = new EscPosPrinter(printer, printerDpi, printingWidthMM, nbrCharactersPerLine, new EscPosCharsetEncoding("GB2312", 0));
        Log.v(LOG_TAG, "蓝牙打印开始：\r\n" + cmd);

        epp.printFormattedTextAndCut(cmd);
        Log.v(LOG_TAG, "蓝牙打印完毕");

        epp.disconnectPrinter();
        Log.v(LOG_TAG, "蓝牙打印机断开连接");

        showToast(R.string.ui_message_bluetooth_printer_done);


    }
}

