package com.huozige.lab.container.platform.hzg;

import android.net.Uri;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.platform.AbstractStaticFilesCacheFilter;

/**
 * 活字格专用的缓存过滤器
 */
public class HZGCacheFilter extends AbstractStaticFilesCacheFilter {


    /**
     * 支持的活字格版本
     */
    final static String[] SUPPORTED_VERSIONS = {
            "9.0.6.0", // 9.0
            "9.0.103.0",// 9.1
            "10.0.2.0",}; // 10.0

    /**
     * 执行缓存检查
     *
     * @param url 原始URL
     * @return 命中的缓存或空引用
     */
    @Override
    public CacheHint filter(Uri url) {

        // Bundle
        CacheHint bundle = filterByType(url, "/%s/Resources/Bundle/", "hzg_bundle_cache_%s/");
        if (bundle != null) return bundle;

        // Scripts
        return filterByType(url, "/%s/Resources/Scripts/", "hzg_scripts_cache_%s/");
    }

    /**
     * 检查特定PATH的文件请求是否有可用缓存
     *
     * @param url         请求URL
     * @param originalTpl URL的PATH模板
     * @param cacheTpl    本地缓存文件的PATH模板
     * @return 缓存信息或空引用
     */
    CacheHint filterByType(Uri url, String originalTpl, String cacheTpl) {

        // 依次处理各个版本
        for (String version : SUPPORTED_VERSIONS
        ) {
            // 拼接出该版本的路径
            String url_path_prefix = String.format(originalTpl, version);
            String cache_prefix = String.format(cacheTpl, version);

            if (url.getPath() != null && url.getPath().startsWith(url_path_prefix)) {

                // 获取文件名
                String fileName = url.getLastPathSegment();
                XLog.v("命中本地缓存，不再从网络获取，Url: " + fileName);

                // 构建返回对象
                CacheHint result = new CacheHint();
                result.FileName = fileName;
                result.LocalFilePath = url.getPath().replace(url_path_prefix, cache_prefix); // 通过Path进行替换，可以避免Query的影响
                result.Encoding = "UTF-8";

                if (fileName != null) {
                    if (fileName.toLowerCase().endsWith("css")) {
                        result.MIME = "text/css";
                    } else if (fileName.toLowerCase().endsWith("js")) {
                        result.MIME = "application/x-javascript";
                    } else if (fileName.toLowerCase().endsWith("json")) {
                        result.MIME = "application/json";
                    } else if (fileName.toLowerCase().endsWith("xml")) {
                        result.MIME = "text/xml";
                    } else if (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg")) {
                        result.MIME = "image/jpeg";
                    } else if (fileName.toLowerCase().endsWith("png")) {
                        result.MIME = "image/png";
                    } else {
                        result.MIME = "text/plain"; // 默认值
                    }
                }

                return result;
            }
        }

        // 没有匹配的话，返回空引用
        return null;
    }
}
