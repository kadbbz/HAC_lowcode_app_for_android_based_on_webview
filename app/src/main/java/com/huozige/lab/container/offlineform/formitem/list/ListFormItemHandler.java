package com.huozige.lab.container.offlineform.formitem.list;

import android.content.Context;
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
import com.huozige.lab.container.offlineform.model.OfflineFormNodeFactory;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItemOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

public class ListFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.LIST.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.LIST.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        ListFormItem item = new ListFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        ListFormItemOptions options = (ListFormItemOptions) input.options;
        if (options != null) {
            item.setMinCount(options.getMinCount());
            item.setMaxCount(options.getMaxCount());
            item.setDefaultCount(options.getDefaultCount());
            item.setAddButtonText(options.getAddButtonText());
            item.setItemTitle(options.getItemTitle());
            item.setTemplateInputs(options.resolveTemplateNodes());
            item.setTemplateNodes(OfflineFormNodeFactory.buildNodes(options.resolveTemplateNodes()));
        }
        if (StringUtils.isNullOrBlank(input.value)) {
            item.ensureDefaultRows();
        } else {
            item.setValue(input.value);
        }
        return item;
    }

    @Override
    public Class<?> getOptionsClass() {
        return ListFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        ListFormItem listItem = (ListFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(listItem);
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, buildOptions(listItem));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        return new ListViewHolder(ListViewHolder.createView(parent));
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        int count = ListFormItem.parseRows(rawValue).size();
        return ReadOnlyFormItemViews.createCompactValueView(
                context,
                count == 0 ? "" : context.getString(R.string.offline_text_list_count, count));
    }

    private JSONObject buildOptions(ListFormItem item) {
        ListFormItemOptions options = new ListFormItemOptions();
        options.setMinCount(item.getMinCount());
        options.setMaxCount(item.getMaxCount());
        options.setDefaultCount(item.getDefaultCount());
        options.setAddButtonText(item.getAddButtonText());
        options.setItemTitle(item.getItemTitle());
        options.setChildren(item.getTemplateInputs());
        return (JSONObject) JSONObject.toJSON(options);
    }
}
