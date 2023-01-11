package com.huozige.lab.container.proxy;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.net.URI;
import java.net.URISyntaxException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 让页面存取本地KV数据库
 * localKv.upsert(key,value)：向本地数据库存入值
 * localKv.retrieve(key,cell)：从本地数据库中查找值，并写入单元格
 * localKv.remove(key)：从本地数据库中删除特定值
 * localKv.upsertV(key,value,version)：向本地数据库存入值（含版本）
 * localKv.retrieveV(key,version,cell)：从本地数据库中查找特定版本的值，并写入单元格
 */
public class LocalKvProxy extends AbstractProxy {

    private static final String VERSION_NA = "N/A";
    private static final String VALUE_NA = "DATA_NOT_FOUND";
    private static final String KEY_TEMPLATE = "%s|%s";
    static final String LOG_TAG="HAC_LocalKvProxy";

    @Override
    public String getName() {
        return "localKv";
    }

    /**
     * 获取当前入口的主机名
     * @return 主机名
     */
    private String getEntryHost(){

        try {
            return (new URI(getConfigManager().getEntry())).getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 注册到页面的localKv.upsert(key,value)方法
     * 向本地数据库中存入值
     * @param key Key，大小写敏感
     * @param valueString 放入的值
     */
    @JavascriptInterface
    public void upsert(String key, String valueString) {
        upsertV(key,valueString,VERSION_NA);
    }

    /**
     * 注册到页面的localKv.retrieve(key,cell)方法
     * 从本地数据库中查找值，并写入单元格
     * @param key Key，大小写敏感
     * @param cell 目标单元格
     */
    @JavascriptInterface
    public void retrieve(String key,String cell) {
        retrieveV(key,VERSION_NA,cell);
    }

    /**
     * 注册到页面的localKv.retrieve2(key,param)方法
     * 从本地数据库中查找值，并写入变量
     * @param key Key，大小写敏感
     */
    @JavascriptInterface
    public String retrieve2(String key) {
        return retrieveV2(key,VERSION_NA);
    }

    /**
     * 注册到页面的 localKv.upsertV(key,value,version)方法
     * 向本地数据库中存入值
     * @param key Key，大小写敏感
     * @param valueString 放入的值
     * @param version 版本号
     */
    @JavascriptInterface
    public void upsertV(String key, String valueString, String version) {
        Realm.getDefaultInstance().executeTransaction (transactionRealm -> {
            LocalKv_Bundle bundle = new LocalKv_Bundle();
            bundle.key = String.format(KEY_TEMPLATE, getEntryHost(),  key);
            bundle.value = valueString;
            bundle.version= version;
            transactionRealm.insertOrUpdate(bundle);

            Log.v(LOG_TAG,"LocalKV has been upsert with key " + key +" on " + getEntryHost());
        });
    }

    /**
     * 注册到页面的 localKv.retrieveV(key,version,cell)方法
     * 从本地数据库中查找值，并写入单元格
     * @param key Key，大小写敏感
     * @param version 版本号
     * @param cell 目标单元格，没有找到键值+版本时返回DATA_NOT_FOUND
     */
    @JavascriptInterface
    public void retrieveV(String key, String version, String cell) {
        Realm.getDefaultInstance().executeTransaction (transactionRealm -> {

            // 确保按照服务器隔离，在这里拼接出真实存储的Key
            String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);
            LocalKv_Bundle bundle= transactionRealm.where(LocalKv_Bundle.class).equalTo("key",bKey).equalTo("version",version).findFirst();

            if(bundle !=null){
                Log.v(LOG_TAG,"Data from LocalKV has been sent. Key: " + bKey);
                getInterop().setInputValue(cell,bundle.value);
            }else{
                getInterop().setInputValue(cell,VALUE_NA);
                Log.v(LOG_TAG,"Data not found in LocalKV. Key: " + bKey);
            }
        });
    }

    /**
     * 注册到页面的 localKv.retrieveV(key,version,cell)方法
     * 从本地数据库中查找值，并写入变量
     * @param key Key，大小写敏感
     * @param version 版本号
     */
    @JavascriptInterface
    public String retrieveV2(String key, String version) {

        final String[] result = new String[1];

        Realm.getDefaultInstance().executeTransaction (transactionRealm -> {

            // 确保按照服务器隔离，在这里拼接出真实存储的Key
            String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);
            LocalKv_Bundle bundle= transactionRealm.where(LocalKv_Bundle.class).equalTo("key",bKey).equalTo("version",version).findFirst();

            if(bundle !=null){
                Log.v(LOG_TAG,"Data from LocalKV has been sent. Key: " + bKey);
                result[0] =  bundle.value;
            }else{
                Log.v(LOG_TAG,"Data not found in LocalKV. Key: " + bKey);
                result[0] = VALUE_NA;
            }
        });

        return result[0];
    }

    /**
     * 注册到页面的 localKv.remove(key)方法
     * 删除特定键值的数据
     * @param key Key，大小写敏感
     */
    @JavascriptInterface
    public void remove(String key) {
        Realm.getDefaultInstance().executeTransaction (transactionRealm -> {

            // 确保按照服务器隔离，在这里拼接出真实存储的Key
            String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);
            LocalKv_Bundle bundle= transactionRealm.where(LocalKv_Bundle.class).equalTo("key",bKey).findFirst();
            if (bundle != null) {
                bundle.deleteFromRealm();
                Log.v(LOG_TAG,"Data was deleted from LocalKV. Key: " + bKey);
            }
        });
    }

    @Override
    public void onActivityCreated() {

        // 在UI线程中初始化Realm
        Realm.init(getInterop().getActivityContext());

        // 升级数据结构时，丢弃原有数据
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }

    @Override
    public void onActivityDestroy() {

        if(Realm.getDefaultInstance() !=null && !Realm.getDefaultInstance().isClosed()){
            // 关闭Realm的连接
            Realm.getDefaultInstance().close();
        }
    }

}
