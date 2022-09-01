package com.huozige.lab.container.pda;


import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.MainActivity;

public class HzgJsBridgePDA extends BaseBridge {

    ActivityResultLauncher _arcScanner;

    public HzgJsBridgePDA(MainActivity activity,WebView webView)
    {
        super(activity,webView);
        Name = "pda";
    }

    @Override
    public void RegisterOnActivityCreated(){

        super.RegisterOnActivityCreated();

        _arcScanner = ActivityContext.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();

            int code  = result.getResultCode();

            if(code == WaitForScannerBroadcastActivity.SCAN_STATUS_OK){
                String resultS = data.getBundleExtra(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT).getString(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT);
                CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('scan_status', 0) ;");
                CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('scan_result', '"+ resultS+"') ;");
            }else{
                CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('scan_result', '') ;");
                CurrentWebView.loadUrl("javascript: (new Forguncy.ForguncyCommandHelper()).setVariableValue('scan_status', "+ code+") ;");
            }
        });
    }

    @JavascriptInterface
    public void scan() {
         _arcScanner.launch(new Intent(ActivityContext,WaitForScannerBroadcastActivity.class));
    }


}
