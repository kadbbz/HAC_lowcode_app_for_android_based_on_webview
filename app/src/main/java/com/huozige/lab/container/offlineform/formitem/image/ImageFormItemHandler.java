package com.huozige.lab.container.offlineform.formitem.image;

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
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItemOptions;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageWatermarkOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.List;

public class ImageFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.IMAGE.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.IMAGE.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        ImageFormItem item = new ImageFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        ImageFormItemOptions options = (ImageFormItemOptions) input.options;
        if (options != null && options.getMaxCount() > 0) {
            item.setMaxCount(options.getMaxCount());
        }
        if (options != null) {
            item.setAllowImageUpload(options.isAllowImageUpload());
        }
        if (options != null && options.getCompression() != null) {
            item.setCompression(options.getCompression());
        }
        if (options != null && options.getWatermark() != null) {
            item.setWatermark(options.getWatermark());
        }
        item.setValue(input.value);
        return item;
    }

    @Override
    public Class<?> getOptionsClass() {
        return ImageFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        ImageFormItem imageItem = (ImageFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(imageItem);
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, buildOptions(imageItem));
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        List<AttachmentFormItemValue> images = ImageFormItem.parseImages(rawValue);
        return ReadOnlyFormItemViews.createCompactValueView(context, images.isEmpty() ? "" : context.getString(R.string.offline_text_image_count, images.size()));
    }

    private JSONObject buildOptions(ImageFormItem item) {
        ImageFormItemOptions options = new ImageFormItemOptions();
        options.setMaxCount(item.getMaxCount());
        options.setAllowImageUpload(item.isAllowImageUpload());
        options.setCompression(item.getCompression());

        ImageWatermarkOptions watermark = item.getWatermark();
        if (watermark != null && (watermark.isEnableTimestamp() || watermark.getItems() != null && !watermark.getItems().isEmpty())) {
            options.setWatermark(watermark);
        }
        return (JSONObject) JSONObject.toJSON(options);
    }

}
