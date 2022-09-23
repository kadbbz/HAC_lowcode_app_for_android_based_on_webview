package com.huozige.lab.container.hzg;

import com.huozige.lab.container.webview.proxy.ValueBundleProxy;
import com.huozige.lab.container.webview.BaseHTMLInterop;
import com.huozige.lab.container.webview.HACWebView;

import java.util.UUID;

/**
 * 活字格页面专用
 * 与页面上的单元格进行交互，获取或设置值
 */
public class HZGWebInterop extends BaseHTMLInterop {

    /**
     * 构造函数
     *
     * @param webView 执行操作的浏览器
     */
    public HZGWebInterop(HACWebView webView) {

        super(webView);
    }

    /**
     * 设置指定单元格的值
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param rawValue 需要设置的值，统一按照字符串处理
     */
    @Override
    public void setInputValue(String cellLocation, Object rawValue) {

        // 直接转换为字符串
        rawValue = rawValue.toString();

        WebView.executeJavaScript("Forguncy.Page.getCellByLocation(" + cellLocation + ").setValue('" +rawValue + "')");
    }

    /**
     * 获取指定单元格的值
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @return 转换为字符串的值
     */
    @Override
    public String getElementValue(String cellLocation) {

        // 原理是通过JavaScript对象作为“桥”，传回通过JavaScript获取到的值

        // 1. 创建桥
        ValueBundleProxy proxy = new ValueBundleProxy();

        // 2. 注册时，需要为每一次调用指定一个不同的名称，避免冲突
        String jsoName = "HzgWebInterop_"+UUID.randomUUID().toString();

        // 3. 将桥注册到浏览器
        WebView.addJavascriptInterface(proxy,jsoName);

        // 4. 执行JS代码，获取单元格的值，然后传给桥
        WebView.executeJavaScript("window."+jsoName+".SetValue(Forguncy.Page.getCellByLocation(" + cellLocation + ").getValue())");

        // 5. 执行完成后，需要从浏览器中注销桥
        WebView.removeJavascriptInterface(jsoName);

        // 6. 从桥中获取单元格的值
        return proxy.Value;
    }

}
