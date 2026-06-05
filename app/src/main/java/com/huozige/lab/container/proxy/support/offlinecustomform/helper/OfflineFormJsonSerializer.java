package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;

import java.util.ArrayList;
import java.util.List;

class OfflineFormJsonSerializer {
    private static final String FIELD_JSON_SCHEMA = "jsonSchema";
    private static final String FIELD_COMPUTED = "computed";
    private static final String FIELD_THEME = "theme";
    private static final String FIELD_PATTERN_ID = "patternId";
    private static final String FIELD_SCHEMA_VERSION = "schemaVersion";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_DEFINITION_FORM_ITEMS = "customForm";
    private static final String FIELD_ID = "id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_HINT = "hint";
    private static final String FIELD_REQUIRED = "required";
    private static final String FIELD_ITEM_TYPE = "itemType";
    private static final String FIELD_VALUE = "value";
    private static final String FIELD_OPTIONS = "options";
    private static final String FIELD_MIN_LENGTH = "minLength";
    private static final String FIELD_MAX_LENGTH = "maxLength";
    private static final String FIELD_REGEX_PATTERN = "regexPattern";
    private static final String FIELD_SELECT_OPTIONS = "selectOptions";
    private static final String FIELD_LABEL = "label";
    private static final String TYPE_TEXT = "textItem";
    private static final String TYPE_PASSWORD = "passwordItem";
    private static final String TYPE_SELECT = "selectItem";

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
            customForm.add(buildJsonFromFormItem(formItem));
        }
        jsonSchema.put(FIELD_DEFINITION_FORM_ITEMS, customForm);

        OfflineComputedInfo computed = definitionFile.getComputed();
        JSONObject computedJson = new JSONObject();
        computedJson.put(FIELD_THEME, computed.getTheme());

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

    private static JSONObject buildJsonFromFormItem(BaseFormItem formItem) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(FIELD_ID, formItem.getId());
        jsonObject.put(FIELD_TITLE, formItem.getTitle());
        jsonObject.put(FIELD_HINT, formItem.getHint());
        jsonObject.put(FIELD_REQUIRED, formItem.isRequired());
        jsonObject.put(FIELD_ITEM_TYPE, getItemType(formItem));

        if (formItem.getValue() != null) {
            jsonObject.put(FIELD_VALUE, formItem.getValue());
        }

        if (formItem instanceof SelectFormItem) {
            jsonObject.put(FIELD_OPTIONS, buildSelectOptions((SelectFormItem) formItem));
        } else if (formItem instanceof TextFormItem) {
            jsonObject.put(FIELD_OPTIONS, buildTextOptions((TextFormItem) formItem));
        } else {
            jsonObject.put(FIELD_OPTIONS, new JSONObject());
        }

        return jsonObject;
    }

    private static JSONObject buildTextOptions(TextFormItem formItem) {
        JSONObject options = new JSONObject();

        if (formItem.getMinLength() > 0) {
            options.put(FIELD_MIN_LENGTH, formItem.getMinLength());
        }
        if (formItem.getMaxLength() > 0) {
            options.put(FIELD_MAX_LENGTH, formItem.getMaxLength());
        }
        if (formItem.getRegexPattern() != null) {
            options.put(FIELD_REGEX_PATTERN, formItem.getRegexPattern());
        }

        return options;
    }

    private static JSONObject buildSelectOptions(SelectFormItem formItem) {
        JSONObject options = new JSONObject();
        JSONArray selectOptionsArray = new JSONArray();

        for (SelectFormItem.Option selectOption : formItem.getOptions()) {
            JSONObject optionJson = new JSONObject();
            optionJson.put(FIELD_VALUE, selectOption.getValue());
            optionJson.put(FIELD_LABEL, selectOption.getDisplayText());
            selectOptionsArray.add(optionJson);
        }

        options.put(FIELD_SELECT_OPTIONS, selectOptionsArray);
        return options;
    }

    private static String getItemType(BaseFormItem formItem) {
        if (formItem instanceof SelectFormItem) {
            return TYPE_SELECT;
        }
        if (formItem instanceof TextFormItem && ((TextFormItem) formItem).isPassword()) {
            return TYPE_PASSWORD;
        }
        return TYPE_TEXT;
    }

    private static List<BaseFormItem> parseJsonToFormItems(JSONArray jsonArray) {
        List<BaseFormItem> formItems = new ArrayList<>();
        if (jsonArray == null) {
            return formItems;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            formItems.add(buildFormItem(jsonArray.getJSONObject(i)));
        }
        return formItems;
    }

    private static BaseFormItem buildFormItem(JSONObject jsonObject) {
        String itemType = jsonObject.getString(FIELD_ITEM_TYPE);

        switch (itemType) {
            case TYPE_TEXT:
                return buildTextFormItem(jsonObject);
            case TYPE_PASSWORD:
                return buildPasswordFormItem(jsonObject);
            case TYPE_SELECT:
                return buildSelectFormItem(jsonObject);
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
    }

    private static TextFormItem buildTextFormItem(JSONObject jsonObject) {
        TextFormItem textItem = new TextFormItem(
                jsonObject.getString(FIELD_ID),
                jsonObject.getString(FIELD_TITLE),
                jsonObject.getString(FIELD_HINT),
                jsonObject.getBooleanValue(FIELD_REQUIRED));

        if (jsonObject.containsKey(FIELD_VALUE)) {
            textItem.setValue(jsonObject.getString(FIELD_VALUE));
        }

        JSONObject options = jsonObject.getJSONObject(FIELD_OPTIONS);
        if (options == null) {
            return textItem;
        }

        if (options.containsKey(FIELD_MIN_LENGTH)) {
            textItem.setMinLength(options.getIntValue(FIELD_MIN_LENGTH));
        }
        if (options.containsKey(FIELD_MAX_LENGTH)) {
            textItem.setMaxLength(options.getIntValue(FIELD_MAX_LENGTH));
        }
        if (options.containsKey(FIELD_REGEX_PATTERN)) {
            textItem.setRegexPattern(options.getString(FIELD_REGEX_PATTERN));
        }

        return textItem;
    }

    private static TextFormItem buildPasswordFormItem(JSONObject jsonObject) {
        TextFormItem textItem = buildTextFormItem(jsonObject);
        textItem.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return textItem;
    }

    private static SelectFormItem buildSelectFormItem(JSONObject jsonObject) {
        SelectFormItem selectItem = new SelectFormItem(
                jsonObject.getString(FIELD_ID),
                jsonObject.getString(FIELD_TITLE),
                jsonObject.getString(FIELD_HINT),
                jsonObject.getBooleanValue(FIELD_REQUIRED));

        JSONObject options = jsonObject.getJSONObject(FIELD_OPTIONS);
        JSONArray selectOptions = options == null ? null : options.getJSONArray(FIELD_SELECT_OPTIONS);
        if (selectOptions != null) {
            for (int i = 0; i < selectOptions.size(); i++) {
                JSONObject selectOption = selectOptions.getJSONObject(i);
                selectItem.addOption(selectOption.getString(FIELD_VALUE), selectOption.getString(FIELD_LABEL));
            }
        }

        if (jsonObject.containsKey(FIELD_VALUE)) {
            selectItem.setSelectedValue(jsonObject.getString(FIELD_VALUE));
        }

        return selectItem;
    }
}
