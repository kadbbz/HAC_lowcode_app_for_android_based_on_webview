package com.huozige.lab.container.webview;

import java.lang.reflect.Type;

/**
 * 与HTML元素直接交互的操作接口
 */
public abstract class BaseHTMLInterop {

    /**
     * 浏览器
     */
    public HACWebView WebView;

    /**
     * 构造函数
     * @param webView 执行操作的浏览器
     */
    public BaseHTMLInterop(HACWebView webView){
        WebView = webView;
    }

    /**
     * 设置某个输入类HTML元素的值
     * @param identity HTML元素的标识（如ID、名称等）
     * @param rawValue 需要设置的值
     */
    public abstract void setInputValue(String identity, Object rawValue);

    /**
     * 获取某个HTML元素的值
     * @param identity HTML元素的标识（如ID、名称等）
     * @return 该HTML元素的值
     */
    public abstract String getElementValue(String identity);
}
