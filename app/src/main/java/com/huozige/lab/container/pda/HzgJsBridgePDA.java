package com.huozige.lab.container.pda;


import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.huozige.lab.container.MainActivity;

public class HzgJsBridgePDA {

    MainActivity _activity;
    WebView _webView;

    ActivityResultLauncher _arc;

    public HzgJsBridgePDA(MainActivity activity, WebView webview)
    {
        _activity = activity;
        _webView = webview;
        _arc= _activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();

            int code  = result.getResultCode();

            if(code == WaitForScannerBroadcastActivity.SCAN_STATUS_OK){
                String resultS = data.getBundleExtra(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT).getString(WaitForScannerBroadcastActivity.BUNDLE_EXTRA_RESULT);
                _webView.loadUrl(String.format("javascript:pda.scan_callback(true,%s);",resultS));
            }else{
                _webView.loadUrl("javascript:pda.scan_callback(false);");
            }
        });
    }

    @JavascriptInterface
    public void scan() {

        _activity.runOnUiThread(() -> _arc.launch(new Intent(_activity,WaitForScannerBroadcastActivity.class)));
    }


}
