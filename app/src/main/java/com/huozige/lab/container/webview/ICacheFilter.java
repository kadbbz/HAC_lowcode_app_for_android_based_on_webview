package com.huozige.lab.container.webview;

import android.net.Uri;

/**
 * 判断是否使用缓存的接口
 */
public interface ICacheFilter {

    /**
     * 对URL进行处理，如果可以从缓存加载，则转换为缓存的URL
     * @param url 原始URL
     * @return 缓存结果，如果不能从缓存读取，请返回null
     */
    CacheHint filter(Uri url);

    /**
     * 缓存策略
     */
    class CacheHint{

        /**
         * 文件名
         */
        public String FileName;

        /**
         * 用于缓存的本地文件路径（Assets下）
         */
        public String LocalFilePath;

        /**
         * MIME
         */
        public String MIME;

        /**
         * 编码方式
         */
        public String Encoding;
    }
}


