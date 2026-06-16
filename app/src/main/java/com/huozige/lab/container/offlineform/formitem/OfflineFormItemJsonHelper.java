package com.huozige.lab.container.offlineform.formitem;

import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;

public final class OfflineFormItemJsonHelper {
    private OfflineFormItemJsonHelper() {
    }

    public static JSONObject buildBaseOutput(BaseFormItem item) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_ID, item.getId());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_TITLE, item.getTitle());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_HINT, item.getHint());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_REQUIRED, item.isRequired());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_ITEM_TYPE, item.getItemType());
        if (item.getValue() != null) {
            jsonObject.put(OfflineFormItemJsonKeys.FIELD_VALUE, item.getValue());
        }
        return jsonObject;
    }

    public static FormItemInput buildBaseInput(JSONObject jsonObject) {
        FormItemInput input = new FormItemInput();
        input.itemId = jsonObject.getString(OfflineFormItemJsonKeys.FIELD_ID);
        input.title = jsonObject.getString(OfflineFormItemJsonKeys.FIELD_TITLE);
        input.hint = jsonObject.getString(OfflineFormItemJsonKeys.FIELD_HINT);
        input.required = jsonObject.getBooleanValue(OfflineFormItemJsonKeys.FIELD_REQUIRED);
        input.itemType = jsonObject.getString(OfflineFormItemJsonKeys.FIELD_ITEM_TYPE);
        input.value = jsonObject.getString(OfflineFormItemJsonKeys.FIELD_VALUE);
        return input;
    }
}
