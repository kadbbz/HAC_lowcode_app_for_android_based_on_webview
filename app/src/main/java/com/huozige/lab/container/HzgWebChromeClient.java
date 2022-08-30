package com.huozige.lab.container;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public class HzgWebChromeClient extends WebChromeClient {

    MainActivity _activity;

    HzgWebChromeClient(MainActivity activity){
        _activity = activity;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        _activity.setTitle(title);
    }
}
