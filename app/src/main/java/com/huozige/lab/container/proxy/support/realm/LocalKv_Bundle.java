package com.huozige.lab.container.proxy.support.realm;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * 存取KV的数据结构
 */
public class LocalKv_Bundle extends RealmObject {

    public LocalKv_Bundle(){

    }

    @PrimaryKey
    public String key;
    @Required
    public String value;
    @Required
    @Index
    public String version;
}
