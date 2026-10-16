package com.huozige.lab.container.offlineform.formitem.radio;

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
import com.huozige.lab.container.offlineform.model.formitem.radio.RadioFormItem;
import com.huozige.lab.container.offlineform.model.formitem.radio.RadioFormItemOptions;
import com.huozige.lab.container.offlineform.model.formitem.select.SelectOptionsInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class RadioFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.RADIO.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.RADIO.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        RadioFormItem item = new RadioFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        RadioFormItemOptions options = (RadioFormItemOptions) input.options;
        if (options != null) {
            item.setDirection(options.normalizedDirection());
            if (options.getSelectOptions() != null) {
                for (SelectOptionsInput option : options.getSelectOptions()) {
                    item.addOption(option.value, option.label);
                }
            }
        }
        if (input.value != null) {
            item.setSelectedValue(input.value);
        }
        return item;
    }

    @Override
    public Class<?> getOptionsClass() {
        return RadioFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        RadioFormItem radioItem = (RadioFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(radioItem);
        RadioFormItemOptions options = new RadioFormItemOptions();
        options.setDirection(radioItem.getDirection());
        for (RadioFormItem.Option option : radioItem.getOptions()) {
            SelectOptionsInput optionInput = new SelectOptionsInput();
            optionInput.value = option.getValue();
            optionInput.label = option.getDisplayText();
            options.getSelectOptions().add(optionInput);
        }
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, JSONObject.toJSON(options));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_radio, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        RadioFormItem radioItem = (RadioFormItem) item;
        if (rawValue != null) {
            for (RadioFormItem.Option option : radioItem.getOptions()) {
                if (rawValue.equals(option.getValue())) {
                    return ReadOnlyFormItemViews.createCompactValueView(context, option.getDisplayText());
                }
            }
        }
        return ReadOnlyFormItemViews.createCompactValueView(context, rawValue);
    }
}
