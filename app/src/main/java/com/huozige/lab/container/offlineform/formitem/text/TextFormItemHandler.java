package com.huozige.lab.container.offlineform.formitem.text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.huozige.lab.container.offlineform.model.formitem.FormItemValidationInput;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class TextFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.TEXT.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.TEXT.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        TextFormItem item = new TextFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        applyInputOptions(item, input);
        return item;
    }

    @Override
    public void readInputOptions(JSONObject options, FormItemInput input) {
        FormItemValidationInput validationInput = new FormItemValidationInput();
        validationInput.minLength = options.getIntValue(OfflineFormItemJsonKeys.FIELD_MIN_LENGTH);
        validationInput.maxLength = options.getIntValue(OfflineFormItemJsonKeys.FIELD_MAX_LENGTH);
        validationInput.regexPattern = options.getString(OfflineFormItemJsonKeys.FIELD_REGEX_PATTERN);
        input.checkOptions = validationInput;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        TextFormItem textItem = (TextFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(textItem);
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, buildTextOptions(textItem));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_text, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return ReadOnlyFormItemViews.createCompactValueView(context, rawValue);
    }

    protected void applyInputOptions(TextFormItem item, FormItemInput input) {
        if (input.value != null) {
            item.setValue(input.value);
        }
        if (input.checkOptions == null) {
            return;
        }
        if (input.checkOptions.minLength > 0) {
            item.setMinLength(input.checkOptions.minLength);
        }
        if (input.checkOptions.maxLength > 0) {
            item.setMaxLength(input.checkOptions.maxLength);
        }
        if (input.checkOptions.regexPattern != null) {
            item.setRegexPattern(input.checkOptions.regexPattern);
        }
    }
    protected JSONObject buildTextOptions(TextFormItem item) {
        JSONObject options = new JSONObject();
        if (item.getMinLength() > 0) {
            options.put(OfflineFormItemJsonKeys.FIELD_MIN_LENGTH, item.getMinLength());
        }
        if (item.getMaxLength() > 0) {
            options.put(OfflineFormItemJsonKeys.FIELD_MAX_LENGTH, item.getMaxLength());
        }
        if (item.getRegexPattern() != null) {
            options.put(OfflineFormItemJsonKeys.FIELD_REGEX_PATTERN, item.getRegexPattern());
        }
        return options;
    }
}
