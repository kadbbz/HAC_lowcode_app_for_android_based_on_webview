package com.huozige.lab.container;

import android.webkit.WebView;

/**
 * 活字格页面交互相关的帮助类
 */
public class HzgWebInteropHelpers {

    /**
     * 将特定的值写入到页面单元格中
     *
     * @param target       执行操作的浏览器内核
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param rawValue     除字符串外的值类型
     */
    public static void WriteRawValueIntoCell(WebView target, String cellLocation, String rawValue) {

        target.loadUrl("javascript:Forguncy.Page.getCellByLocation(" + cellLocation + ").setValue(" + rawValue + ")");
    }

    /**
     * 将字符串写入到页面单元格中
     *
     * @param target       执行操作的浏览器内核
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param strValue     字符串类型
     */
    public static void WriteStringValueIntoCell(WebView target, String cellLocation, String strValue) {
        HzgWebInteropHelpers.WriteRawValueIntoCell(target, cellLocation, "'" + strValue + "'");
    }

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param target     执行操作的浏览器
     * @param logContent 日志的内容
     */
    public static void WriteLogIntoConsole(WebView target, String logContent) {
        target.loadUrl("javascript:console.log('" + logContent + "')");
    }

    /**
     * 向浏览器输出错误信息，供调试使用
     *
     * @param target     执行操作的浏览器
     * @param logContent 日志的内容
     */
    public static void WriteErrorIntoConsole(WebView target, String logContent) {
        target.loadUrl("javascript:console.error('" + logContent + "')");
    }

}
