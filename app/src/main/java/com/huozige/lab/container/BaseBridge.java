package com.huozige.lab.container;

import android.content.Intent;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * JavaScript桥的抽象类
 * 未来可以做更多功能，但暂时没有具体的实现
 */
public abstract class BaseBridge {

    /**
     * 操作的上下文
     */
    protected AppCompatActivity ActivityContext;

    /**
     * 操作的浏览器内核
     */
    protected WebView CurrentWebView;

    /**
     * 基础的构造函数
     * @param context 上下文
     * @param webView 浏览器内核
     */
    public BaseBridge(AppCompatActivity context, WebView webView) {
        ActivityContext = context;
        CurrentWebView = webView;
    }

    /**
     * 获取JS桥注册到页面时使用的名称
     */
    public abstract String GetName();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void OnActivityCreated();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void BeforeActivityDestroy();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void BeforeActivityPause();

    /**
     * 需要注册到上下文中的初始化操作
     */
    public abstract void OnActivityResumed();

    /**
     * 处理上下文转发来的Activity调用返回结果
     * @param requestCode 同onActivityResult
     * @param resultCode 同onActivityResult
     * @param data 同onActivityResult
     * @return 返回true意味着不再调用后续的处理器
     */
    public  abstract Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data);


}
