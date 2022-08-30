package com.huozige.lab.container;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import java.util.Locale;

public class HzgWebChromeClient extends WebChromeClient {

    MainActivity _activity;

    HzgWebChromeClient(MainActivity activity){
        _activity = activity;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress < 100){
            _activity.setTitle(
                    _activity.getString(R.string.ui_title_progressing,newProgress));
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(title.toLowerCase().startsWith("http://") || title.toLowerCase().startsWith("https://") ){
            _activity.setTitle(_activity.getString(R.string.ui_title_loading));
        }else{
            _activity.setTitle(title);
        }

    }
}
