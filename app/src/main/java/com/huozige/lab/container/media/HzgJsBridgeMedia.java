package com.huozige.lab.container.media;

import android.content.Intent;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.BaseBridge;

public class HzgJsBridgeMedia extends BaseBridge {

    /**
     * 基础的构造函数
     *
     * @param context 上下文
     * @param webView 浏览器内核
     */
    public HzgJsBridgeMedia(AppCompatActivity context, WebView webView) {
        super(context, webView);
    }

    @Override
    public String GetName() {
        return "media";
    }

    @Override
    public void OnActivityCreated() {

    }

    @Override
    public void BeforeActivityDestroy() {

    }

    @Override
    public void BeforeActivityPause() {

    }

    @Override
    public void OnActivityResumed() {

    }

    @Override
    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data) {
        return null;
    }
}
