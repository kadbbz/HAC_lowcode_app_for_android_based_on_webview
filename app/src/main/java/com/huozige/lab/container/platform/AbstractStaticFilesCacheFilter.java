package com.huozige.lab.container.platform;

import android.net.Uri;

/**
 * 判断是否使用缓存的接口
 */
public abstract class AbstractStaticFilesCacheFilter {

    public abstract CacheHint filter(Uri url);

    /**
     * 缓存策略
     */
    public static class CacheHint{

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


