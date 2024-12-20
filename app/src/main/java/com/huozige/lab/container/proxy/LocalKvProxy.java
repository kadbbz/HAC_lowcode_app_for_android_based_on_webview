package com.huozige.lab.container.proxy;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.proxy.support.realm.LocalKv_Bundle;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * 让页面存取本地KV数据库
 * 1.4.0
 * localKv.upsert(key,value)：向本地数据库存入值
 * localKv.retrieve(key,cell)：从本地数据库中查找值，并写入单元格
 * localKv.remove(key)：从本地数据库中删除特定值
 * localKv.upsertV(key,value,version)：向本地数据库存入值（含版本）
 * localKv.retrieveV(key,version,cell)：从本地数据库中查找特定版本的值，并写入单元格
 * 1.15.0
 * localKv.retrieveAllKeys()：查询本地数据库中当前应用的所有键，以JSON形式返回
 */
public class LocalKvProxy extends AbstractProxy {

    private static final String VERSION_NA = "N/A";
    private static final String VALUE_NA = "DATA_NOT_FOUND";
    private static final String KEY_TEMPLATE = "%s|%s";

    private static final String VERSION_DEFAULT = "default";

    @Override
    public String getName() {
        return "localKv";
    }

    /**
     * 获取当前入口的主机名
     *
     * @return 主机名
     */
    private String getEntryHost() {

        try {
            return (new URI(getConfigManager().getEntry())).getHost();
        } catch (URISyntaxException e) {
            writeErrorLog("解析Uri过程中出错：" + e);
            return "";
        }
    }

    /**
     * 注册到页面的localKv.upsert(key,value)方法
     * 向本地数据库中存入值
     *
     * @param key         Key，大小写敏感
     * @param valueString 放入的值
     */
    @JavascriptInterface
    public void upsert(String key, String valueString) {
        upsertV(key, valueString, VERSION_NA);
    }

    /**
     * 注册到页面的localKv.retrieve(key,cell)方法
     * 从本地数据库中查找值，并写入单元格
     *
     * @param key  Key，大小写敏感
     * @param cell 目标单元格
     */
    @JavascriptInterface
    public void retrieve(String key, String cell) {
        retrieveV(key, VERSION_NA, cell);
    }

    /**
     * 注册到页面的localKv.retrieve2(key,param)方法
     * 从本地数据库中查找值，并写入变量
     *
     * @param key Key，大小写敏感
     */
    @JavascriptInterface
    public String retrieve2(String key) {
        return retrieveV2(key, VERSION_NA);
    }

    /**
     * 注册到页面的 localKv.upsertV(key,value,version)方法
     * 向本地数据库中存入值
     *
     * @param key         Key，大小写敏感
     * @param valueString 放入的值
     * @param version     版本号
     */
    @JavascriptInterface
    public void upsertV(String key, String valueString, String version) {

        registryForFeatureUsageAnalyze("use_localdb_feature", "upsert");

        if (null == version) {
            version = VERSION_DEFAULT;
        }

        String finalVersion = version;
        String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);

        writeInfoLog("更新本地缓存中的项目，键：" + bKey + "，版本：" + finalVersion + "，值：" + valueString);

        Realm.getDefaultInstance().executeTransaction(transactionRealm -> {
            LocalKv_Bundle bundle = new LocalKv_Bundle();
            bundle.key = bKey;
            bundle.value = valueString;
            bundle.version = finalVersion;
            transactionRealm.insertOrUpdate(bundle);

            writeInfoLog("本地缓存已更新");
        });
    }

    /**
     * 注册到页面的 localKv.retrieveV(key,version,cell)方法
     * 从本地数据库中查找值，并写入单元格
     *
     * @param key     Key，大小写敏感
     * @param version 版本号
     * @param cell    目标单元格，没有找到键值+版本时返回DATA_NOT_FOUND
     */
    @JavascriptInterface
    public void retrieveV(String key, String version, String cell) {

        registryForFeatureUsageAnalyze("use_localdb_feature", "retrieve");

        if (null == version) {
            version = VERSION_DEFAULT;
        }

        // 确保按照服务器隔离，在这里拼接出真实存储的Key
        String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);
        String finalVersion = version;

        writeInfoLog("从本地缓存中读取项目，键：" + bKey + "，版本：" + finalVersion);

        Realm.getDefaultInstance().executeTransaction(transactionRealm -> {

            LocalKv_Bundle bundle = transactionRealm.where(LocalKv_Bundle.class).equalTo("key", bKey).equalTo("version", finalVersion).findFirst();

            if (bundle != null) {
                writeInfoLog("从本地缓存中找到该项目的值：" + bundle.value);
                callback(cell, bundle.value);
            } else {
                writeInfoLog("本地缓存中没有找到该键：" + bKey);
                callback(cell, VALUE_NA);
            }
        });
    }

    /**
     * 注册到页面的 localKv.retrieveV(key,version,cell)方法
     * 从本地数据库中查找值，并写入变量
     *
     * @param key     Key，大小写敏感
     * @param version 版本号
     */
    @JavascriptInterface
    public String retrieveV2(String key, String version) {

        registryForFeatureUsageAnalyze("use_localdb_feature", "retrieve2");

        final String[] result = new String[1];

        if (null == version) {
            version = VERSION_DEFAULT;
        }
        // 确保按照服务器隔离，在这里拼接出真实存储的Key
        String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);
        String finalVersion = version;

        writeInfoLog("从本地缓存中读取项目，键：" + bKey + "，版本：" + finalVersion);

        Realm.getDefaultInstance().executeTransaction(transactionRealm -> {


            LocalKv_Bundle bundle = transactionRealm.where(LocalKv_Bundle.class).equalTo("key", bKey).equalTo("version", finalVersion).findFirst();

            if (bundle != null) {
                writeInfoLog("从本地缓存中找到该项目的值：" + bundle.value);
                result[0] = bundle.value;
            } else {
                writeInfoLog("本地缓存中没有找到该键：" + bKey);
                result[0] = VALUE_NA;
            }
        });

        return result[0];
    }

    /**
     * 注册到页面的 localKv.remove(key)方法
     * 删除特定键值的数据
     *
     * @param key Key，大小写敏感
     */
    @JavascriptInterface
    public void remove(String key) {

        registryForFeatureUsageAnalyze("use_localdb_feature", "remove");
        String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);

        writeInfoLog("删除本地缓存中的项目，键：" + bKey );

        Realm.getDefaultInstance().executeTransaction(transactionRealm -> {

            LocalKv_Bundle bundle = transactionRealm.where(LocalKv_Bundle.class).equalTo("key", bKey).findFirst();
            if (bundle != null) {
                bundle.deleteFromRealm();
                writeInfoLog("已经从本地缓存中移除该键");
            }
        });
    }

    /**
     * 注册到页面的 localKv.retrieveAllKeys()方法
     * 从本地数据库中查找值，并写入变量
     */
    @JavascriptInterface
    public String retrieveAllKeys() {

        final List<String> result = new ArrayList<>();

        writeInfoLog("从本地缓存中获取所有键");

        Realm.getDefaultInstance().executeTransaction(transactionRealm -> {

            var bundles = transactionRealm.where(LocalKv_Bundle.class).findAll();

            if (bundles != null && !bundles.isEmpty()) {
                bundles.forEach((d) -> result.add(d.key));
            }

            writeInfoLog("本地缓存所有键的数量：" + result.size());
        });

        return JSON.toJSONString(result.toArray(new String[1]));
    }

    @Override
    public void onActivityDestroy() {

        if (Realm.getDefaultInstance() != null && !Realm.getDefaultInstance().isClosed()) {
            // 关闭Realm的连接
            Realm.getDefaultInstance().close();
        }
    }

}
