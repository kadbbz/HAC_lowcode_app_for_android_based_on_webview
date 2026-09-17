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
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItemOptions;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;
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
    public Class<?> getOptionsClass() {
        return TextFormItemOptions.class;
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
        TextFormItemOptions options = (TextFormItemOptions) input.options;
        if (options == null) {
            return;
        }
        if (options.getMinLength() > 0) {
            item.setMinLength(options.getMinLength());
        }
        if (options.getMaxLength() > 0) {
            item.setMaxLength(options.getMaxLength());
        }
        if (options.getRegexPattern() != null) {
            item.setRegexPattern(options.getRegexPattern());
        }
    }
    protected JSONObject buildTextOptions(TextFormItem item) {
        TextFormItemOptions options = new TextFormItemOptions();
        options.setMinLength(item.getMinLength());
        options.setMaxLength(item.getMaxLength());
        options.setRegexPattern(item.getRegexPattern());
        return (JSONObject) JSONObject.toJSON(options);
    }
}
