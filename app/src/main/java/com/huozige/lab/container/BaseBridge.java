package com.huozige.lab.container;

import android.content.Intent;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseBridge {

    protected AppCompatActivity ActivityContext;
    protected WebView CurrentWebView;
    protected String Name;

    public BaseBridge(AppCompatActivity activity, WebView webView) {
        ActivityContext = activity;
        CurrentWebView = webView;
    }

    public void RegisterOnActivityCreated(){

    }

    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data){
        return false;
    }


}
