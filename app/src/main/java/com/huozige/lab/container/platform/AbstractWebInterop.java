package com.huozige.lab.container.platform;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.webview.HACWebView;

/**
 * 与HTML元素直接交互的操作接口
 */
public abstract class AbstractWebInterop {


    protected HACWebView webView; // 浏览器内核

    /**
     * 设置某个输入类HTML元素的值
     *
     * @param identity HTML元素的标识（如ID、名称等）
     * @param rawValue 需要设置的值
     * @throws IllegalStateException 当前状态下不可用时抛出该异常
     */
    public abstract void setInputValue(String identity, Object rawValue) throws IllegalStateException;

    /**
     * 触发回调
     * @param identity 回调的唯一标识
     * @param params 需要传递给回调的参数
     * @throws IllegalStateException 当前状态下不可用时抛出该异常
     */
    public abstract void callback(String identity, CallbackParams params) throws IllegalStateException;

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void writeLogIntoConsole(String logContent) {
        executeJavaScript("console.log('" + removeJSKeyWords(logContent) + "')");
    }

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void writeErrorLogIntoConsole(String logContent) {
        executeJavaScript("console.error('" + removeJSKeyWords(logContent) + "')");
    }

    /**
     * 在浏览器执行JavaScript语句
     *
     * @param jsSegment 自定义脚本
     */
    public void executeJavaScript(String jsSegment) {
        getActivityContext().runOnUiThread(() -> webView.evaluateJavascript(jsSegment, (result) -> XLog.v("在浏览器执行脚本完成：" + jsSegment + " >> " + result)));

        XLog.v("开始在浏览器中执行脚本：" + jsSegment);
    }

    /**
     * 弹出Toast消息
     *
     * @param text 消息
     */
    public void showToast(String text) {
        getActivityContext().runOnUiThread(() -> Toast.makeText(getActivityContext(), text, Toast.LENGTH_LONG).show());
        XLog.v("以Toast形式弹出消息：" + text);
    }

    /**
     * 去掉JavaScript的关键字，替换为HTML编码
     *
     * @param str 原始字符串
     * @return 可以直接拼接到JS的字符串
     */
    private String removeJSKeyWords(String str) {

        int length = str.length();
        int newLength = length;
        boolean someCharacterEscaped = false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case 11:
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        break;
                    default:
                        newLength--;
                        someCharacterEscaped = true;
                        break;
                }
            else
                switch (c) {
                    case '"':
                        newLength += 5;
                        someCharacterEscaped = true;
                        break;

                    case '&':
                    case '\'':
                        newLength += 4;
                        someCharacterEscaped = true;
                        break;

                    case '<':
                    case '>':
                        newLength += 3;
                        someCharacterEscaped = true;
                        break;
                }
        }
        if (!someCharacterEscaped)
            return str;

        StringBuilder sb = new StringBuilder(newLength);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        sb.append(c);
                        break;
                }
            else
                switch (c) {
                    case '"':
                        sb.append("&quot;");
                        break;

                    case '\'':
                        sb.append("&apos;");
                        break;

                    case '&':
                        sb.append("&amp;");
                        break;

                    case '<':
                        sb.append("&lt;");
                        break;

                    case '>':
                        sb.append("&gt;");
                        break;

                    default:
                        sb.append(c);
                        break;
                }
        }
        return sb.toString();
    }

    /**
     * 获取当前浏览器所在的Activity
     *
     * @return 可用的AppCompatActivity对象
     */
    public AppCompatActivity getActivityContext() {
        return (AppCompatActivity) webView.getContext();
    }

    /**
     * 初始化浏览器
     *
     * @param webView 浏览器实例
     */
    public void setWebView(HACWebView webView) {
        this.webView = webView;
    }

    /**
     * 获取当前运行的浏览器
     *
     * @return 浏览器实例
     */
    public HACWebView getWebView() {
        return this.webView;
    }


}
