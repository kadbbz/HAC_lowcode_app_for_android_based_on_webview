package com.huozige.lab.container.platform;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.utilities.PermissionsUtility;

/**
 * 与HTML元素直接交互的操作接口
 */
public abstract class AbstractWebInterop {

    static final String LOG_TAG="HAC_AbstractWebInterop";

    protected WebView webView; // 浏览器内核

    /**
     * 设置某个输入类HTML元素的值
     * @param identity HTML元素的标识（如ID、名称等）
     * @param rawValue 需要设置的值
     */
    public abstract void setInputValue(String identity, Object rawValue) throws IllegalStateException;

    /**
     * 获取某个HTML元素的值
     * @param identity HTML元素的标识（如ID、名称等）
     * @return 该HTML元素的值
     */
    public abstract String getElementValue(String identity) throws IllegalStateException;

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void writeLogIntoConsole(String logContent) {
        executeJavaScript("console.log('" + removeJSKeyWords(logContent) + "')");

        Log.v(LOG_TAG,"写入浏览器日志："+logContent);
    }

    /**
     * 向浏览器输出错误信息，供调试使用
     *
     * @param logContent 日志的内容
     */
    public void writeErrorIntoConsole(String logContent) {
        executeJavaScript("console.error('" + removeJSKeyWords(logContent) + "')");

        Log.v(LOG_TAG,"写入浏览器错误日志："+logContent);
    }

    /**
     * 在浏览器执行JavaScript语句
     *
     * @param jsSegment 自定义脚本
     */
    public void executeJavaScript(String jsSegment) {
        getActivityContext().runOnUiThread(() -> webView.loadUrl("javascript:"+jsSegment));

        Log.v(LOG_TAG,"在浏览器执行脚本："+jsSegment);
    }

    /**
     * 去掉JavaScript的关键字，替换为HTML编码
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
                    default:
                        newLength--;
                        someCharacterEscaped = true;
                        break;

                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
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
     * @return 可用的AppCompatActivity对象
     */
    public AppCompatActivity getActivityContext(){
        return  (AppCompatActivity)webView.getContext();
    }

    /**
     * 申请一个敏感权限
     * @param permission 需要申请的权限（单个权限）
     * @param successAction 申请成功后执行的动作
     */
    public void requirePermission(String permission, Runnable successAction){
        PermissionsUtility.asyncRequirePermissions(webView.getContext(), new String[]{
                permission
        },successAction);
    }

    /**
     * 申请多个敏感权限
     * @param permissions 需要申请的权限
     * @param successAction 申请成功后执行的动作
     */
    public void requirePermission(String[] permissions, Runnable successAction){
        PermissionsUtility.asyncRequirePermissions(webView.getContext(), permissions,successAction);
    }

    /**
     * 获取当前页面的Host（主机名或IP地址）
     * @return Host，如果当前没有页面则返回null
     */
    public String getCurrentHost(){

        final Uri[] currentPage = new Uri[1];

        getActivityContext().runOnUiThread(()-> currentPage[0] = Uri.parse(webView.getUrl()));

        if(null!= currentPage[0]){
            return currentPage[0].getHost();
        }else{
            return null;
        }
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }
}
