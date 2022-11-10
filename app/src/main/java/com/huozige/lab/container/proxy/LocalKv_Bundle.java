package com.huozige.lab.container.proxy;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * 存取KV的数据结构
 */
public class LocalKv_Bundle extends RealmObject {

    @PrimaryKey
    public String key;
    @Required
    public String value;
    @Required
    @Index
    public String version;
}
