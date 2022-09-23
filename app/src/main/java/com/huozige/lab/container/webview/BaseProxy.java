package com.huozige.lab.container.webview;

import android.content.Intent;

/**
 * JavaScript桥的抽象类
 * 未来可以做更多功能，但暂时没有具体的实现
 */
public abstract class BaseProxy {

    /**
     * 注册到的浏览器
     */
    protected HACWebView CurrentWebView;

    /**
     * 用来操作HTML内容的接口
     */
    protected BaseHTMLInterop CurrentHTMLInterop;

    /**
     * 基础的构造函数
     * @param webView 浏览器内核
     * @param interop HTML内容操作接口
     */
    public BaseProxy(HACWebView webView, BaseHTMLInterop interop) {
        CurrentWebView = webView;
        CurrentHTMLInterop = interop;
    }

    /**
     * 获取JS桥注册到页面时使用的名称
     */
    public abstract String getName();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void onActivityCreated();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void beforeActivityDestroy();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void beforeActivityPause();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void onActivityResumed();

    /**
     * 处理上下文转发来的Activity调用返回结果
     * @param requestCode 同onActivityResult
     * @param resultCode 同onActivityResult
     * @param data 同onActivityResult
     * @return 返回true意味着不再调用后续的处理器
     */
    public  abstract Boolean processActivityResult(int requestCode, int resultCode, Intent data);


}
