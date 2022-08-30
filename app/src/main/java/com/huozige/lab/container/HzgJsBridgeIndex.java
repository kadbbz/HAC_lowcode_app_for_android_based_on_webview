package com.huozige.lab.container;


import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class HzgJsBridgeIndex {

    Context _activity;

    HzgJsBridgeIndex(Context activity)
    {
        _activity = activity;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(_activity, toast, Toast.LENGTH_SHORT).show();
    }
}
