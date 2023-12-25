package com.huozige.lab.container.utilities;

import android.webkit.URLUtil;

import java.net.URL;


public class MiscUtilities {

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
     * 获取附件类URL的文件名
     * @param url URL地址
     * @return 如果是普通地址，则调用UrlUtil的算法，否则按照活字格的规则获取
     */
    public static String guessFileName(String url){
        return guessFileName(url,"");
    }

    /**
     * 获取附件类URL的文件名
     * @param url URL地址
     * @param contentDisposition Content-Disposition的内容
     * @return 如果是普通地址，则调用UrlUtil的算法，否则按照活字格的规则获取
     */
    public static String guessFileName(String url, String contentDisposition){

        String fileName = URLUtil.guessFileName(url, contentDisposition, "application/octet-stream");

        // 活字格的附件名存放在download的file参数中，如https://hac.app.hzgcloud.cn/demo/FileDownloadUpload/Download?file=47916819-f90e-47f8-8079-72df4fce78ac_AppLevelSecurityProvider.zip
        if(url.toLowerCase().contains("/filedownloadupload/download?")){
            String hzgFileName = MiscUtilities.getUrlparameter(url,"file");
            if(hzgFileName!=null && hzgFileName.split("_").length>1){
                fileName = hzgFileName.replace(hzgFileName.split("_")[0]+"_","");
            }
        }

        return  fileName;
    }

    public static String getUrlparameter(String urlString, String paraName) {
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

                if(key.equalsIgnoreCase(paraName)){
                    return  value;
                }
            }
        } catch (Exception e) {
            return  null;
        }

        return null;
    }
}
