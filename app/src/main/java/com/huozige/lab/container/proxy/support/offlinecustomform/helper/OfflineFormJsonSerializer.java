package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.huozige.lab.container.proxy.support.offlinecustomform.model.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineComputedInfo;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinition;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndex;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormRecord;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.TextFormItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class OfflineFormJsonSerializer {
    private static final String FIELD_JSON_SCHEMA = "jsonSchema";
    private static final String FIELD_COMPUTED = "computed";
    private static final String FIELD_THEME = "theme";
    private static final String FIELD_PATTERN_ID = "patternId";
    private static final String FIELD_SCHEMA_VERSION = "schemaVersion";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_RECORD_ID = "recordId";
    private static final String FIELD_CREATED_AT = "createdAt";
    private static final String FIELD_UPDATED_AT = "updatedAt";
    private static final String FIELD_VALUES = "values";
    private static final String FIELD_DEFINITION_INDEX_ITEMS = "project";
    private static final String FIELD_DEFINITION_FORM_ITEMS = "customForm";
    private static final String FIELD_ID = "id";
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

    static OfflineFormDefinitionIndex parseJsonToDefinitionIndex(JSONObject jsonObject) {

        List<OfflineFormDefinitionIndexItem> items = new ArrayList<>();

        try {
            JSONArray project = jsonObject.getJSONArray(FIELD_DEFINITION_INDEX_ITEMS);

            for (int i = 0; i < project.length(); i++) {
                JSONObject item = project.getJSONObject(i);
                items.add(new OfflineFormDefinitionIndexItem(
                        item.getString(FIELD_TITLE),
                        item.getString(FIELD_DESCRIPTION),
                        item.getString(FIELD_STATUS),
                        item.getString(FIELD_PATTERN_ID),
                        item.optString(FIELD_SCHEMA_VERSION),
                        parseJsonToComputed(item.optJSONObject(FIELD_COMPUTED))));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new OfflineFormDefinitionIndex(items);
    }

    static JSONObject parseDefinitionIndexToJson(OfflineFormDefinitionIndex index) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            for (OfflineFormDefinitionIndexItem indexItem : index.getItems()) {
                JSONObject item = new JSONObject();
                item.put(FIELD_PATTERN_ID, indexItem.getPatternId());
                item.put(FIELD_DESCRIPTION, indexItem.getDescription());
                item.put(FIELD_TITLE, indexItem.getTitle());
                item.put(FIELD_STATUS, indexItem.getStatus());
                item.put(FIELD_SCHEMA_VERSION, indexItem.getSchemaVersion());
                item.put(FIELD_COMPUTED, parseComputedToJson(indexItem.getComputed()));

                jsonArray.put(item);
            }
            jsonObject.put(FIELD_DEFINITION_INDEX_ITEMS, jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    static JSONObject parseDefinitionFileToJson(OfflineFormDefinitionFile definitionFile) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(FIELD_JSON_SCHEMA, parseDefinitionToJson(definitionFile.getJsonSchema()));
            jsonObject.put(FIELD_COMPUTED, parseComputedToJson(definitionFile.getComputed()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    static OfflineFormDefinitionFile parseJsonToDefinitionFile(JSONObject jsonObject) {
        try {
            return new OfflineFormDefinitionFile(
                    parseJsonToDefinition(jsonObject.getJSONObject(FIELD_JSON_SCHEMA)),
                    parseJsonToComputed(jsonObject.getJSONObject(FIELD_COMPUTED)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    static JSONObject parseRecordToJson(OfflineFormRecord record) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(FIELD_RECORD_ID, record.getRecordId());
            jsonObject.put(FIELD_PATTERN_ID, record.getPatternId());
            jsonObject.put(FIELD_SCHEMA_VERSION, record.getSchemaVersion());
            jsonObject.put(FIELD_STATUS, record.getStatus());
            jsonObject.put(FIELD_CREATED_AT, record.getCreatedAt());
            jsonObject.put(FIELD_UPDATED_AT, record.getUpdatedAt());
            jsonObject.put(FIELD_VALUES, new JSONObject(record.getValues()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    private static JSONObject parseDefinitionToJson(OfflineFormDefinition definition) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonObject.put(FIELD_PATTERN_ID, definition.getPatternId());
            jsonObject.put(FIELD_SCHEMA_VERSION, definition.getSchemaVersion());
            jsonObject.put(FIELD_TITLE, definition.getTitle());
            jsonObject.put(FIELD_DESCRIPTION, definition.getDescription());

            for (BaseFormItem formItem : definition.getFormItems()) {
                JSONObject customForm = buildJsonFromFormItem(formItem);
                jsonArray.put(customForm);
            }
            jsonObject.put(FIELD_DEFINITION_FORM_ITEMS, jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    private static OfflineFormDefinition parseJsonToDefinition(JSONObject jsonObject) {
        try {
            return new OfflineFormDefinition(
                    jsonObject.getString(FIELD_PATTERN_ID),
                    jsonObject.optString(FIELD_SCHEMA_VERSION),
                    jsonObject.optString(FIELD_TITLE),
                    jsonObject.optString(FIELD_DESCRIPTION),
                    parseJsonToFormItem(jsonObject.getJSONArray(FIELD_DEFINITION_FORM_ITEMS)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static OfflineComputedInfo parseJsonToComputed(JSONObject jsonObject) {
        return new OfflineComputedInfo(jsonObject == null ? "" : jsonObject.optString(FIELD_THEME));
    }

    private static JSONObject parseComputedToJson(OfflineComputedInfo computed) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(FIELD_THEME, computed.getTheme());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    private static JSONObject buildJsonFromFormItem(BaseFormItem formItem) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        // 基础字段
        jsonObject.put(FIELD_ID, formItem.getId());
        jsonObject.put(FIELD_TITLE, formItem.getTitle());
        jsonObject.put(FIELD_HINT, formItem.getHint());
        jsonObject.put(FIELD_REQUIRED, formItem.isRequired());
        jsonObject.put(FIELD_ITEM_TYPE, getItemType(formItem));

        // 值字段（如果有）
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

    private static JSONObject buildTextOptions(TextFormItem formItem) throws JSONException {

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

    private static JSONObject buildSelectOptions(SelectFormItem formItem) throws JSONException {

        JSONObject options = new JSONObject();
        JSONArray selectOptionsArray = new JSONArray();

        for (SelectFormItem.Option selectOption : formItem.getOptions()) {
            JSONObject optionJson = new JSONObject();
            optionJson.put(FIELD_VALUE, selectOption.getValue());
            optionJson.put(FIELD_LABEL, selectOption.getDisplayText());
            selectOptionsArray.put(optionJson);
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

    private static List<BaseFormItem> parseJsonToFormItem(JSONArray jsonArray) {

        List<BaseFormItem> formItems = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject formItem = jsonArray.getJSONObject(i);
                formItems.add(buildFormItem(formItem));

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return formItems;
    }

    private static BaseFormItem buildFormItem(JSONObject jsonObject) {

        try {

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

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static TextFormItem buildTextFormItem(JSONObject jsonObject) {

        try {

            String itemId = jsonObject.getString(FIELD_ID);
            String title = jsonObject.getString(FIELD_TITLE);
            String hint = jsonObject.getString(FIELD_HINT);
            boolean required = jsonObject.getBoolean(FIELD_REQUIRED);

            TextFormItem textItem = new TextFormItem(itemId, title, hint, required);

            if (jsonObject.has(FIELD_VALUE)) {
                textItem.setValue(jsonObject.getString(FIELD_VALUE));
            }

            JSONObject options = jsonObject.getJSONObject(FIELD_OPTIONS);

            if (options.has(FIELD_MIN_LENGTH)) {
                textItem.setMinLength(options.getInt(FIELD_MIN_LENGTH));
            }

            if (options.has(FIELD_MAX_LENGTH)) {
                textItem.setMaxLength(options.getInt(FIELD_MAX_LENGTH));
            }

            if (options.has(FIELD_REGEX_PATTERN)) {
                textItem.setRegexPattern(options.getString(FIELD_REGEX_PATTERN));
            }

            return textItem;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private static TextFormItem buildPasswordFormItem(JSONObject jsonObject) {
        TextFormItem textItem = buildTextFormItem(jsonObject);
        textItem.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return textItem;
    }

    private static SelectFormItem buildSelectFormItem(JSONObject jsonObject) {

        try {

            String itemId = jsonObject.getString(FIELD_ID);
            String title = jsonObject.getString(FIELD_TITLE);
            String hint = jsonObject.getString(FIELD_HINT);
            boolean required = jsonObject.getBoolean(FIELD_REQUIRED);

            SelectFormItem selectItem = new SelectFormItem(itemId, title, hint, required);

            JSONObject options = jsonObject.getJSONObject(FIELD_OPTIONS);
            JSONArray selectOptions = options.getJSONArray(FIELD_SELECT_OPTIONS);
            for (int i = 0; i < selectOptions.length(); i++) {

                JSONObject selectOption = selectOptions.getJSONObject(i);
                selectItem.addOption(selectOption.getString(FIELD_VALUE), selectOption.getString(FIELD_LABEL));

            }

            if (jsonObject.has(FIELD_VALUE)) {
                selectItem.setSelectedValue(jsonObject.getString(FIELD_VALUE));
            }

            return selectItem;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
