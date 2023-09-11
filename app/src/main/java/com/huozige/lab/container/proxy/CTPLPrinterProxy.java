package com.huozige.lab.container.proxy;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Point;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ctaiot.ctprinter.ctpl.CTPL;
import com.ctaiot.ctprinter.ctpl.Device;
import com.ctaiot.ctprinter.ctpl.RespCallback;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.bluetooth.BluetoothConnection;
import com.huozige.lab.container.proxy.support.bluetooth.BluetoothConnectionException;
import com.huozige.lab.container.proxy.support.bluetooth.GeneralBluetoothPrintersConnections;
import com.huozige.lab.container.utilities.PermissionsUtility;

import java.util.HashMap;

public class CTPLPrinterProxy extends AbstractProxy{

    private final static String LOG_TAG = "HAC_CTPLPrinterProxy";

    @Override
    public String getName() {
        return "ctPrinter";
    }

    @Override
    public void onApplicationCreated(Application app){

        // 初始化CTPL
        CTPL.getInstance().init(app, new RespCallback() {

            @Override
            public void onConnectRespsonse(int port, int reason) {
                Log.v(LOG_TAG,"CTPL打印机组件收到onConnectRespsonse" );
                Log.v(LOG_TAG, "端口=" + port + ",结果=" + reason);
            }

            @Override
            public void onDataResponse(HashMap<String, String> hashMap) {
                Log.v(LOG_TAG,"CTPL打印机组件收到onDataResponse" );
            }

            @Override
            public boolean autoSPPBond() {
                return true;
            }
        });

        Log.v(LOG_TAG,"CTPL打印机组件已初始化");
    }

    @JavascriptInterface
    @SuppressLint("MissingPermission")
    public void PrintTestPage(){
        PermissionsUtility.asyncRequirePermissions(this.getInterop().getActivityContext(), new String[]{
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_CONNECT
        }, () -> {

            try {

                BluetoothConnection conn = GeneralBluetoothPrintersConnections.selectFirstPaired();

                if(conn ==null){
                    showToast(R.string.ui_message_bluetooth_printer_not_available);
                    return;
                }

                Device d = new Device();
                d.setPort(CTPL.Port.SPP);
                d.setBluetoothMacAddr(conn.getDevice().getAddress());

                Log.v(LOG_TAG,"默认打印机为" + conn.getDevice().getName()+" MAC：" +conn.getDevice().getAddress() );

                CTPL.getInstance().connect(d);

                if(CTPL.getInstance().isConnected()){
                    Log.v(LOG_TAG,"打印机连接完成" );
                }else{
                    Log.v(LOG_TAG,"打印机无法连接" );
                }


                CTPL.getInstance()
                        .setSize(38,30)//设置纸张尺寸,单位:毫米
                        .drawText(new Point(1,1), 1,1,"Hello world! 测试页")
                        .print(1)
                        .execute();//执行打印

                Log.v(LOG_TAG,"打印机打印完毕" );

            } catch (BluetoothConnectionException e) {
                Log.e(LOG_TAG, e.toString());
                showToast(getInterop().getActivityContext().getString(R.string.ui_message_bluetooth_printer_error) + e.getMessage());
            }

        });


    }

}
