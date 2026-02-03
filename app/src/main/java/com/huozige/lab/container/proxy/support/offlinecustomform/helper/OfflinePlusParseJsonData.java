package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.huozige.lab.container.proxy.support.offlinecustomform.dto.FormItemInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.dto.SelectOptionsInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.TextFormItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusParseJsonData {
    public static List<OfflinePlusListCardItem> parseJsonToCardList(JSONObject jsonObject) {

        List<OfflinePlusListCardItem> items = new ArrayList<>();

        try {
            JSONArray project = jsonObject.getJSONArray(JsonFileHelper.FILE_FLAG_OFFLINE_LIST);

            for (int i = 0; i < project.length(); i++) {
                JSONObject item = project.getJSONObject(i);
                items.add(new OfflinePlusListCardItem(
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("status"),
                        item.getString("patternId")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static JSONObject parseCardListToJson(List<OfflinePlusListCardItem> cardItems) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            for (OfflinePlusListCardItem offlinePlusListCardItem : cardItems) {
                JSONObject item = new JSONObject();
                item.put("patternId", offlinePlusListCardItem.getPatternId());
                item.put("description", offlinePlusListCardItem.getDescription());
                item.put("title", offlinePlusListCardItem.getTitle());
                item.put("status", offlinePlusListCardItem.getStatus());

                jsonArray.put(item);
            }
            jsonObject.put(JsonFileHelper.FILE_FLAG_OFFLINE_LIST, jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    public static JSONObject parseFormItemToJson(List<FormItemInput> formItems, String patternId) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonObject.put("patternId", patternId);

            for (FormItemInput formItem : formItems) {
                JSONObject customForm = buildJsonFromFormItem(formItem);
                jsonArray.put(customForm);
            }
            jsonObject.put(JsonFileHelper.FILE_FLAG_OFFLINE_FORM, jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

    private static JSONObject buildJsonFromFormItem(FormItemInput formItem) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        // 基础字段
        jsonObject.put("id", formItem.itemId);
        jsonObject.put("title", formItem.title);
        jsonObject.put("hint", formItem.hint);
        jsonObject.put("required", formItem.required);
        jsonObject.put("itemType", formItem.itemType);

        // 值字段（如果有）
        if (formItem.value != null) {
            jsonObject.put("value", formItem.value);
        }

        // 根据 itemType 构建 options
        switch (formItem.itemType) {
            case "textItem":
            case "passwordItem":
                jsonObject.put("options", buildTextOptions(formItem));
                break;
            case "selectItem":
                jsonObject.put("options", buildSelectOptions(formItem));
                break;
            default:
                jsonObject.put("options", new JSONObject());
                break;
        }

        return jsonObject;
    }

    private static JSONObject buildTextOptions(FormItemInput formItem) throws JSONException {

        JSONObject options = new JSONObject();

        if (formItem.checkOptions != null) {
            if (formItem.checkOptions.minLength > 0) {
                options.put("minLength", formItem.checkOptions.minLength);
            }
            if (formItem.checkOptions.maxLength > 0) {
                options.put("maxLength", formItem.checkOptions.maxLength);
            }
            if (formItem.checkOptions.regexPattern != null) {
                options.put("regexPattern", formItem.checkOptions.regexPattern);
            }
        }

        return options;
    }

    private static JSONObject buildSelectOptions(FormItemInput formItem) throws JSONException {

        JSONObject options = new JSONObject();
        JSONArray selectOptionsArray = new JSONArray();

        if (formItem.selectOptionsList != null) {
            for (SelectOptionsInput selectOption : formItem.selectOptionsList) {
                JSONObject optionJson = new JSONObject();
                optionJson.put("value", selectOption.value);
                optionJson.put("label", selectOption.label);
                selectOptionsArray.put(optionJson);
            }
        }

        options.put("selectOptions", selectOptionsArray);

        return options;
    }

    public static List<BaseFormItem>  parseJsonToFormItem(JSONArray jsonArray) {

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

            String itemType = jsonObject.getString("itemType");

            switch (itemType) {
                case "textItem":
                    return buildTextFormItem(jsonObject);
                case "passwordItem":
                    return buildPasswordFormItem(jsonObject);
                case "selectItem":
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

            String itemId = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String hint = jsonObject.getString("hint");
            boolean required = jsonObject.getBoolean("required");

            TextFormItem textItem = new TextFormItem(itemId, title, hint, required);

            if (jsonObject.has("value")) {
                textItem.setValue(jsonObject.getString("value"));
            }

            JSONObject options = jsonObject.getJSONObject("options");

            if (options.has("minLength")) {
                textItem.setMinLength(options.getInt("minLength"));
            }

            if (options.has("maxLength")) {
                textItem.setMaxLength(options.getInt("maxLength"));
            }

            if (options.has("regexPattern")) {
                textItem.setRegexPattern(options.getString("regexPattern"));
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

            String itemId = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String hint = jsonObject.getString("hint");
            boolean required = jsonObject.getBoolean("required");

            SelectFormItem selectItem = new SelectFormItem(itemId, title, hint, required);

            JSONObject options = jsonObject.getJSONObject("options");
            JSONArray selectOptions = options.getJSONArray("selectOptions");
            for (int i = 0; i < selectOptions.length(); i++) {

                JSONObject selectOption = selectOptions.getJSONObject(i);
                selectItem.addOption(selectOption.getString("value"), selectOption.getString("label"));

            }

            if (jsonObject.has("value")) {
                selectItem.setSelectedValue(jsonObject.getString("value"));
            }

            return selectItem;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
