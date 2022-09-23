package com.huozige.lab.container.webview.proxy;

import android.webkit.JavascriptInterface;

/**
 * 用于从HTML中传回字符串值的JS代理
 */
public class ValueBundleProxy {

    public String Value;

    /**
     * 将JS的值传递到Java中。
     * @param value 需要传回的值
     */
    @JavascriptInterface
    public void SetValue(String value){
        this.Value = value;
    }
}
