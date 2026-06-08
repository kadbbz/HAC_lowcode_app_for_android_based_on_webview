package com.huozige.lab.container.offlineform.formitem.select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemViewType;
import com.huozige.lab.container.offlineform.formitem.ReadOnlyFormItemViews;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectOptionsInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class SelectFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.SELECT.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.SELECT.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        SelectFormItem item = new SelectFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        if (input.selectOptionsList != null) {
            for (SelectOptionsInput option : input.selectOptionsList) {
                item.addOption(option.value, option.label);
            }
        }
        if (input.value != null) {
            item.setSelectedValue(input.value);
        }
        return item;
    }

    @Override
    public void readInputOptions(JSONObject options, FormItemInput input) {
        JSONArray selectOptions = options.getJSONArray(OfflineFormItemJsonKeys.FIELD_SELECT_OPTIONS);
        if (selectOptions == null) {
            return;
        }

        for (int i = 0; i < selectOptions.size(); i++) {
            JSONObject optionJson = selectOptions.getJSONObject(i);
            SelectOptionsInput option = new SelectOptionsInput();
            option.value = optionJson.getString(OfflineFormItemJsonKeys.FIELD_VALUE);
            option.label = optionJson.getString(OfflineFormItemJsonKeys.FIELD_LABEL);
            input.selectOptionsList.add(option);
        }
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        SelectFormItem selectItem = (SelectFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(selectItem);
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, buildOptions(selectItem));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_select, parent, false);
        return new SelectViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return ReadOnlyFormItemViews.createCompactValueView(context, getDisplayText((SelectFormItem) item, rawValue));
    }

    private String getDisplayText(SelectFormItem selectItem, String rawValue) {
        if (rawValue == null || rawValue.isEmpty()) {
            return "";
        }
        for (SelectFormItem.Option option : selectItem.getOptions()) {
            if (rawValue.equals(option.getValue())) {
                return option.getDisplayText();
            }
        }
        return rawValue;
    }

    private JSONObject buildOptions(SelectFormItem item) {
        JSONObject options = new JSONObject();
        JSONArray selectOptionsArray = new JSONArray();
        for (SelectFormItem.Option option : item.getOptions()) {
            JSONObject optionJson = new JSONObject();
            optionJson.put(OfflineFormItemJsonKeys.FIELD_VALUE, option.getValue());
            optionJson.put(OfflineFormItemJsonKeys.FIELD_LABEL, option.getDisplayText());
            selectOptionsArray.add(optionJson);
        }
        options.put(OfflineFormItemJsonKeys.FIELD_SELECT_OPTIONS, selectOptionsArray);
        return options;
    }
}
