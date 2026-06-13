package com.huozige.lab.container.offlineform.formitem.file;

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
import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileItemConfig;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class FileFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.FILE.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.FILE.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        FileFormItem item = new FileFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        if (input.fileItemConfig != null) {
            item.setFileItemConfig(input.fileItemConfig);
        }
        item.setValue(input.value);
        return item;
    }

    @Override
    public void readInputOptions(JSONObject options, FormItemInput input) {
        JSONObject configJson = options.getJSONObject(OfflineFormItemJsonKeys.FIELD_FILE_ITEM_CONFIG);
        if (configJson != null) {
            input.fileItemConfig = configJson.toJavaObject(FileItemConfig.class);
        }
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        FileFormItem fileItem = (FileFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(fileItem);
        JSONObject options = new JSONObject();
        options.put(OfflineFormItemJsonKeys.FIELD_FILE_ITEM_CONFIG, fileItem.getFileItemConfig());
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, options);
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return ReadOnlyFormItemViews.createCompactValueView(context, context.getString(R.string.offline_text_file_count, FileFormItem.parseFiles(rawValue).size()));
    }
}
