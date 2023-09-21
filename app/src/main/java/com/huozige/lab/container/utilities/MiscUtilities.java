package com.huozige.lab.container.utilities;

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
}
