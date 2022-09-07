package com.huozige.lab.container;

import android.app.Activity;
import android.webkit.WebView;

/**
 * 活字格页面交互相关的帮助类
 */
public class HzgWebInteropHelpers {

    /**
     * 将字符串写入到页面单元格中
     *
     * @param target       执行操作的浏览器内核
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param strValue     字符串类型
     */
    public static void WriteStringValueIntoCell(WebView target, String cellLocation, String strValue) {
        HzgWebInteropHelpers.WriteRawValueIntoCell(target, cellLocation, "'" + strValue+ "'");
    }

    /**
     * 将特定的值写入到页面单元格中
     *
     * @param target       执行操作的浏览器内核
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param rawValue     除字符串外的值类型
     */
    public static void WriteRawValueIntoCell(WebView target, String cellLocation, String rawValue) {
        Execute(target,"Forguncy.Page.getCellByLocation(" + cellLocation + ").setValue(" +rawValue + ")");
    }

    /**
     * 向浏览器输出日志，供调试使用
     *
     * @param target     执行操作的浏览器
     * @param logContent 日志的内容
     */
    public static void WriteLogIntoConsole(WebView target, String logContent) {
        Execute(target,"console.log('" + removeJSKeyWords(logContent) + "')");
    }

    /**
     * 向浏览器输出错误信息，供调试使用
     *
     * @param target     执行操作的浏览器
     * @param logContent 日志的内容
     */
    public static void WriteErrorIntoConsole(WebView target, String logContent) {
        Execute(target,"console.error('" + removeJSKeyWords(logContent) + "')");
    }

    /**
     * 在浏览器执行JavaScript语句
     *
     * @param target     执行操作的浏览器
     * @param jsSegment 自定义脚本
     */
    public static void Execute(WebView target, String jsSegment) {
        ((Activity) target.getContext()).runOnUiThread(() -> {
            target.loadUrl("javascript:"+jsSegment);
        });
    }


    /**
     * 去掉JavaScript的关键字，替换为HTML编码
     * @param str 原始字符串
     * @return 可以直接拼接到JS的字符串
     */
    private static String removeJSKeyWords(String str) {

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

        StringBuffer sb = new StringBuffer(newLength);
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

}
