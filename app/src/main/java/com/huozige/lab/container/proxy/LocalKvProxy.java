package com.huozige.lab.container.proxy;

import android.webkit.JavascriptInterface;

import io.realm.Realm;

/**
 * 让页面存取本地KV数据库
 * localKv.upsert(key,value)：向本地数据库存入值
 * localKv.retrieve(key,cell)：从本地数据库中查找值，并写入单元格
 * localKv.upsertV(key,value,version)：向本地数据库存入值（含版本）
 * localKv.retrieveV(key,version,cell)：从本地数据库中查找特定版本的值，并写入单元格
 */
public class LocalKvProxy extends AbstractProxy {

    private static final String VERSION_NA = "N/A";
    private static final String KEY_TEMPLATE = "%s|%s";

    @Override
    public String getName() {
        return "localKv";
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
            bundle.key = String.format(KEY_TEMPLATE,getInterop().getCurrentHost(),  key);
            bundle.value = valueString;
            bundle.version= version;
            transactionRealm.insertOrUpdate(bundle);
        });
    }

    /**
     * 注册到页面的 localKv.retrieveV(key,version,cell)方法
     * 从本地数据库中查找值，并写入单元格
     * @param key Key，大小写敏感
     * @param version 版本号
     * @param cell 目标单元格
     */
    @JavascriptInterface
    public void retrieveV(String key, String version, String cell) {
        Realm.getDefaultInstance().executeTransaction (transactionRealm -> {

            // 确保按照服务器隔离，在这里拼接出真实存储的Key
            String bKey = String.format(KEY_TEMPLATE,getInterop().getCurrentHost(), key);
            LocalKv_Bundle bundle= transactionRealm.where(LocalKv_Bundle.class).equalTo("key",bKey).equalTo("version",version).findFirst();

            if(bundle !=null){
                getInterop().setInputValue(cell,bundle.value);
            }else{
                getInterop().setInputValue(cell,"");
            }
        });
    }

    @Override
    public void onActivityCreated() {

        // 在UI线程中初始化Realm
        Realm.init(getInterop().getActivityContext());

    }

    @Override
    public void onActivityDestroy() {

        if(Realm.getDefaultInstance() !=null && !Realm.getDefaultInstance().isClosed()){
            // 关闭Realm的连接
            Realm.getDefaultInstance().close();
        }
    }

}
