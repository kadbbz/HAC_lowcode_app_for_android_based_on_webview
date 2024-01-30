package com.huozige.lab.container.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.webkit.URLUtil;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import com.elvishew.xlog.XLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 帮助类，提供一些杂项功能
 */
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
            String hzgFileName = MiscUtilities.getUrlParameter(url, "file");
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
     * @param urlString URL
     * @param paraName 参数名称
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
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {

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
    public static String bytesToHex(byte[] bytes) {
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

    /**
     * 读取当前APP的版本号
     * @param context 执行读取的上下文
     * @return 版本号
     */
    public static String getPackageVersionName(Context context) {
        String versionName = "";

        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            XLog.e("获取应用版本信息出错。 \r\n%s", e);
        }

        return versionName;
    }

    /**
     * 读取设备标识SSAID
     * @param context 执行读取的上下文
     * @return SSAID
     */
    public static String getSSAID(Activity context) {

        @SuppressLint("HardwareIds") String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return id;
    }

    /**
     * 获取WebView的主版本
     *
     * @return 主版本
     */
    public static int getWebViewMajorVersion() {
        PackageInfo pinfo = WebView.getCurrentWebViewPackage();

        if (pinfo == null) {
            return Integer.MAX_VALUE; // 如果无法获取版本号，按照可以使用来处理
        } else {
            String major = pinfo.versionName.split("\\.")[0];
            return Integer.parseInt(major);
        }

    }

    /**
     * 获取WebView的版本号
     * @return 完整的版本号
     */
    public static String getWebViewVersionName() {
        PackageInfo pinfo = WebView.getCurrentWebViewPackage();

        if (pinfo == null) {
            return "";
        } else {
            return pinfo.versionName;
        }

    }

    /**
     * 读取文件内容到Byte[]
     * @param context 执行读取的上下文
     * @param uri 目标文件
     * @return 文件的内容（Byte[]格式）
     * @throws Exception 异常
     */
    public static byte[] readFileToByteArray(Context context, Uri uri)throws Exception{
        InputStream input = context.getContentResolver().openInputStream(uri);

        if(input==null){
            throw new FileNotFoundException(uri.toString());
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();

        return output.toByteArray();
    }

    /**
     * 将文件路径封装为对读取更友好的Uri格式
     * @param context 执行封装的上下文
     * @param filePath 文件路径
     * @return 文件路径（Uri格式）
     */
    public static Uri toUri(Context context, String filePath) {

        return FileProvider.getUriForFile(context, context.getApplicationInfo().packageName, new File(filePath));

    }

    private static Uri __latestFile;

    /**
     * 获取上次操作的文件
     * @return 文件
     */
    public static Uri getLatestFile(){
        return __latestFile;
    }

    /**
     * 注册为“上次操作的文件”
     * @param uri 文件
     */
    public static void registryLatestFile(Uri uri){
        __latestFile = uri;
    }

}
