package com.huozige.lab.container.offlineform.formitem.switchitem;

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
import com.huozige.lab.container.offlineform.model.formitem.switchitem.SwitchFormItem;
import com.huozige.lab.container.offlineform.model.formitem.switchitem.SwitchFormItemOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class SwitchFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.SWITCH.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.SWITCH.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        SwitchFormItem item = new SwitchFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        SwitchFormItemOptions options = (SwitchFormItemOptions) input.options;
        if (options != null) {
            item.setStyle(options.normalizedStyle());
        }
        // 先定样式再定值：开关未选中存 false、复选框未选中存空。
        item.setChecked(input.value != null && !input.value.isEmpty()
                && SwitchFormItem.isCheckedValue(input.value));
        return item;
    }

    @Override
    public Class<?> getOptionsClass() {
        return SwitchFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        SwitchFormItem switchItem = (SwitchFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(switchItem);
        SwitchFormItemOptions options = new SwitchFormItemOptions();
        options.setStyle(switchItem.getStyle());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, JSONObject.toJSON(options));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_switch, parent, false);
        return new SwitchViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        boolean checked = "true".equalsIgnoreCase(rawValue);
        return ReadOnlyFormItemViews.createCompactValueView(context, context.getString(
                checked ? R.string.offline_switch_on : R.string.offline_switch_off));
    }
}
