package com.huozige.lab.container.proxy.support.realm;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.utilities.ConfigManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import io.realm.Realm;

public class LocalKvHelper {
    private static final String VALUE_NA = "DATA_NOT_FOUND";
    private static final String KEY_TEMPLATE = "%s|%s";

    private static final String VERSION_DEFAULT = "default";
    public static void upsertVRange(Map<String, String> valueList, String version) {


        if (null == version) {
            version = VERSION_DEFAULT;
        }

        String finalVersion = version;
        XLog.v("开始批量更新本地缓存中的项目，数量" + valueList.size() + "，版本：" + finalVersion);

        Realm.getDefaultInstance().executeTransactionAsync(transactionRealm -> {

            Collection<LocalKv_Bundle> bundles = new ArrayList<>();

            valueList.forEach((String key, String valueString) -> {

                String bKey = String.format(KEY_TEMPLATE, getEntryHost(), key);

                bundles.add(new LocalKv_Bundle(bKey, valueString, finalVersion));
            });

            transactionRealm.insertOrUpdate(bundles);

            XLog.v("本地缓存已更新");
        });

    }

    private static String getEntryHost() {

        try {
            return (new URI(ConfigManager.getInstance().getEntry())).getHost();
        } catch (URISyntaxException e) {
            XLog.e("解析Uri过程中出错：" + e);
            return "";
        }
    }
}
