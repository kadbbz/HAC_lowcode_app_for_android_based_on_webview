package com.huozige.lab.container.compatible;

import android.content.Intent;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.R;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;


import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.MainActivity;


public class HzgJsBridgeIndex extends BaseBridge {

    ActivityResultLauncher _arcZxingLite;
    String _scanResultCell;

    public HzgJsBridgeIndex(MainActivity activity,WebView webView)
    {
        super(activity,webView);
        Name = "index";
    }

    @JavascriptInterface
    public void ScanCode(String cellLocation) {
        // 兼容官方版的做法
        // webview.loadUrl("javascript:Forguncy.Page.getCell('result').setValue('" + result + "')");
        _scanResultCell = cellLocation;

        _arcZxingLite.launch(new Intent(ActivityContext, CaptureActivity.class));
    }

    @JavascriptInterface
    public void GetAppVersion() {

        ActivityContext.runOnUiThread(() ->{
            String packageName = ActivityContext.getPackageName();
            CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('app_package', '"+ packageName+"') ;");
            CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('app_version', '"+ ActivityContext.getString(R.string.app_version_name)+"') ;");
        });
    }

    @Override
    public void RegisterOnActivityCreated(){

        super.RegisterOnActivityCreated();

        _arcZxingLite= ActivityContext.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            String resultS = CameraScan.parseScanResult(data);
            if( null == resultS){
                resultS="CANCELED";
            }

            CurrentWebView.loadUrl("javascript:Forguncy.Page.getCellByLocation("+_scanResultCell+").setValue('" + resultS + "')");

            CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('scan_result', '"+ resultS+"') ;");


        });
    }


}
