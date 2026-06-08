package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.password.PasswordFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.select.SelectFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.text.TextFormItemHandler;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class OfflineFormItemRegistry {
    private static final Map<String, OfflineFormItemHandler> HANDLERS_BY_TYPE = new HashMap<>();

    static {
        register(new SelectFormItemHandler());
        register(new PasswordFormItemHandler());
        register(new TextFormItemHandler());
    }

    private OfflineFormItemRegistry() {
    }

    public static void register(OfflineFormItemHandler handler) {
        HANDLERS_BY_TYPE.put(handler.getType(), handler);
    }

    public static Map<String, OfflineFormItemHandler> getHandlers() {
        return Collections.unmodifiableMap(HANDLERS_BY_TYPE);
    }

    public static OfflineFormItemHandler getHandler(String type) {
        OfflineFormItemHandler handler = HANDLERS_BY_TYPE.get(type);
        if (handler != null) {
            return handler;
        }
        throw new IllegalArgumentException("Unknown item type: " + type);
    }

    public static int getViewType(BaseFormItem item) {
        return getHandler(item.getItemType()).getViewType();
    }

    public static BaseFormItem fromInput(FormItemInput input) {
        OfflineFormItemHandler handler = HANDLERS_BY_TYPE.get(input.itemType);
        if (handler == null) {
            handler = getHandler(OfflineFormItemType.TEXT.getValue());
        }
        return handler.fromInput(input);
    }

    public static JSONObject toJson(BaseFormItem item) {
        return getHandler(item.getItemType()).toJson(item);
    }

    public static View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return getHandler(item.getItemType()).createReadOnlyView(context, item, rawValue, compact);
    }
}
