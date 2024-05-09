package com.huozige.lab.container.utilities;

import android.webkit.URLUtil;

import java.net.URL;
import java.net.URLConnection;

/**
 * 与字符串和格式处理有关的帮助类
 */
public class StringConvertUtility {

    /**
     * 获取附件类URL的文件名
     *
     * @param url      URL地址
     * @param mimeType MIME
     * @return 如果是普通地址，则调用UrlUtil的算法，否则按照活字格的规则获取
     */
    public static FileNameInfo guessFileName(String url, String mimeType) {

        // 默认实现
        String fileName = URLUtil.guessFileName(url, "", mimeType);

        // 活字格的附件名存放在download的file参数中，如https://hac.app.hzgcloud.cn/demo/FileDownloadUpload/Download?file=47916819-f90e-47f8-8079-72df4fce78ac_AppLevelSecurityProvider.zip
        if (url.toLowerCase().contains("/filedownloadupload/download?")) {
            String hzgFileName = StringConvertUtility.getUrlParameter(url, "file");
            if (hzgFileName != null && hzgFileName.split("_").length > 1) {
                fileName = hzgFileName.replace(hzgFileName.split("_")[0] + "_", "");
                mimeType = URLConnection.guessContentTypeFromName(fileName);
            }
        }

        FileNameInfo result = new FileNameInfo();
        result.fileName = fileName;
        result.mimeType = mimeType;
        return result;
    }

    /**
     * 从URL中读取特定的参数的值
     *
     * @param urlString URL
     * @param paraName  参数名称
     * @return 参数的值
     */
    public static String getUrlParameter(String urlString, String paraName) {
        URL url;
        try {
            url = new URL(urlString);
            String query = url.getQuery();
            String decodedQuery = java.net.URLDecoder.decode(query, "UTF-8");
            String[] params = decodedQuery.split("&");

            for (String param : params) {
                String[] keyValue = param.split("="); // 将参数拆分为 ["param1", "value1"] 或者 ["param2", "value2"]

                String key = keyValue[0]; // 参数的键
                String value = keyValue[1]; // 参数的值

                if (key.equalsIgnoreCase(paraName)) {
                    return value;
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * 在字符串中去除非ASCII字符和其他不能输出到屏幕的字符
     *
     * @param text 原始字符串
     * @return 处理后的字符串
     */
    public static String removeNonASCIIChars(String text) {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        // text = text.replaceAll("\\p{Cntrl}&&[^\r\n\t]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexStringToByteArray(String inHex) {

        if (inHex.toLowerCase().startsWith("0x")) {
            inHex = inHex.substring(2, inHex.length() - 2);
        }

        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Byte数组转逗号分隔的字符串
     * @param data byte数组
     * @return 字符串
     */
    public static String byteArrayToCommaSeperatedString(byte[] data) {

        if(data==null){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);

            if (i != data.length - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    /**
     * 十六进制字符串转整数
     * @param tcd 字符串，以#或0x开头时，会删除这些前缀
     * @return 整数
     */
    public static int hexStringToInteger(String tcd) {

        tcd = tcd.replace("#", "");
        tcd = tcd.replace("0x", "");

        return Integer.parseInt(tcd, 16) + 0xFF000000;
    }

    /**
     * 从传入的字符串转换为布尔值，空引用、空、0、false、no均判定为false，否则时true
     * @param text 字符串
     * @return 布尔值
     */
    public static boolean stringToBoolean(String text) {
        return text != null && !text.isEmpty() && !text.equalsIgnoreCase("0") && !text.equalsIgnoreCase("false") && !text.equalsIgnoreCase("no");
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
