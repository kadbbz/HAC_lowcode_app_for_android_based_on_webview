package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;

import java.util.ArrayList;
import java.util.List;

class OfflineFormJsonSerializer {
    private static final String FIELD_JSON_SCHEMA = "jsonSchema";
    private static final String FIELD_COMPUTED = "computed";
    private static final String FIELD_THEME = "theme";
    private static final String FIELD_DISPLAY_COLUMNS = "displayColumns";
    private static final String FIELD_PATTERN_ID = "patternId";
    private static final String FIELD_SCHEMA_VERSION = "schemaVersion";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_DEFINITION_FORM_ITEMS = "customForm";

    static JSONObject prepareDefinitionFileForJson(OfflineFormDefinitionFile definitionFile) {
        OfflineFormDefinition definition = definitionFile.getJsonSchema();

        JSONObject jsonSchema = new JSONObject();
        jsonSchema.put(FIELD_PATTERN_ID, definition.getPatternId());
        jsonSchema.put(FIELD_SCHEMA_VERSION, definition.getSchemaVersion());
        jsonSchema.put(FIELD_TITLE, definition.getTitle());
        jsonSchema.put(FIELD_DESCRIPTION, definition.getDescription());

        JSONArray customForm = new JSONArray();
        // formItems 是 BaseFormItem 多态列表，这里保留显式转换，避免 fastjson 直接序列化出 Java 内部字段。
        for (BaseFormItem formItem : definition.getFormItems()) {
            customForm.add(OfflineFormItemRegistry.toJson(formItem));
        }
        jsonSchema.put(FIELD_DEFINITION_FORM_ITEMS, customForm);

        OfflineComputedInfo computed = definitionFile.getComputed();
        JSONObject computedJson = new JSONObject();
        computedJson.put(FIELD_THEME, computed.getTheme());
        computedJson.put(FIELD_DISPLAY_COLUMNS, computed.getDisplayColumns());

        JSONObject definitionJson = new JSONObject();
        definitionJson.put(FIELD_JSON_SCHEMA, jsonSchema);
        definitionJson.put(FIELD_COMPUTED, computedJson);
        return definitionJson;
    }

    static OfflineFormDefinitionFile restoreDefinitionFileFromJson(JSONObject definitionFileJson) {
        JSONObject jsonSchema = definitionFileJson.getJSONObject(FIELD_JSON_SCHEMA);
        JSONArray customForm = jsonSchema.getJSONArray(FIELD_DEFINITION_FORM_ITEMS);
        jsonSchema.remove(FIELD_DEFINITION_FORM_ITEMS);

        OfflineFormDefinitionFile definitionFile = definitionFileJson.toJavaObject(OfflineFormDefinitionFile.class);
        OfflineFormDefinition definition = jsonSchema.toJavaObject(OfflineFormDefinition.class);
        definition.setFormItems(parseJsonToFormItems(customForm));
        definitionFile.setJsonSchema(definition);
        return definitionFile;
    }

    private static List<BaseFormItem> parseJsonToFormItems(JSONArray jsonArray) {
        List<BaseFormItem> formItems = new ArrayList<>();
        if (jsonArray == null) {
            return formItems;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            formItems.add(OfflineFormItemRegistry.fromInput(buildInputFromJson(jsonArray.getJSONObject(i))));
        }
        return formItems;
    }

    private static FormItemInput buildInputFromJson(JSONObject jsonObject) {
        FormItemInput input = OfflineFormItemJsonHelper.buildBaseInput(jsonObject);
        JSONObject options = jsonObject.getJSONObject(OfflineFormItemJsonKeys.FIELD_OPTIONS);
        if (options != null) {
            OfflineFormItemRegistry.getHandler(input.itemType).readInputOptions(options, input);
        }
        return input;
    }
}
