package com.huozige.lab.container.offlineform.formitem.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.ReadOnlyFormItemViews;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItemOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

public abstract class PickerFormItemHandler implements OfflineFormItemHandler {
    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        PickerFormItem item = new PickerFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        if (input.value != null) {
            item.setValue(input.value);
        }
        PickerFormItemOptions options = (PickerFormItemOptions) input.options;
        if (getPickerMode() == PickerMode.DATE) {
            item.setIncludeTime(options != null && options.isIncludeTime());
            if (options != null) {
                item.setDateRangeMode(options.getDateRangeMode());
                item.setMinDate(options.getMinDate());
                item.setMaxDate(options.getMaxDate());
            }
        } else {
            item.setIncludeSeconds(options != null && options.isIncludeSeconds());
        }
        return item;
    }

    @Override
    public Class<?> getOptionsClass() {
        return PickerFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        PickerFormItem pickerItem = (PickerFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(pickerItem);
        if (getPickerMode() == PickerMode.DATE) {
            // 仅在有额外配置时才输出 options，保持默认日期项的 JSON 形状不变。
            boolean deadline = "deadline".equalsIgnoreCase(pickerItem.getDateRangeMode());
            boolean hasRange = !StringUtils.isNullOrBlank(pickerItem.getMinDate())
                    || !StringUtils.isNullOrBlank(pickerItem.getMaxDate());
            if (pickerItem.isIncludeTime() || deadline || hasRange) {
                PickerFormItemOptions options = new PickerFormItemOptions();
                options.setIncludeTime(pickerItem.isIncludeTime());
                if (deadline) {
                    options.setDateRangeMode("deadline");
                }
                if (!StringUtils.isNullOrBlank(pickerItem.getMinDate())) {
                    options.setMinDate(pickerItem.getMinDate());
                }
                if (!deadline && !StringUtils.isNullOrBlank(pickerItem.getMaxDate())) {
                    options.setMaxDate(pickerItem.getMaxDate());
                }
                jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, options);
            }
        } else if (getPickerMode() == PickerMode.TIME && pickerItem.isIncludeSeconds()) {
            PickerFormItemOptions options = new PickerFormItemOptions();
            options.setIncludeSeconds(true);
            jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, options);
        }
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_picker, parent, false);
        return new PickerViewHolder(view, getPickerMode());
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return ReadOnlyFormItemViews.createCompactValueView(context, rawValue);
    }

    protected abstract PickerMode getPickerMode();
}
