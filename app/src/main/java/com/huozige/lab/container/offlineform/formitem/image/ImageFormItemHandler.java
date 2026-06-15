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
import com.huozige.lab.container.offlineform.model.formitem.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageWatermarkOptions;
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
        if (input.maxCount > 0) {
            item.setMaxCount(input.maxCount);
        }
        item.setAllowImageUpload(input.allowImageUpload);
        if (input.compression != null) {
            item.setCompression(input.compression);
        }
        if (input.watermark != null) {
            item.setWatermark(input.watermark);
        }
        item.setValue(input.value);
        return item;
    }

    @Override
    public void readInputOptions(JSONObject options, FormItemInput input) {
        input.maxCount = options.getIntValue(OfflineFormItemJsonKeys.FIELD_MAX_COUNT);
        input.allowImageUpload = options.getBooleanValue(OfflineFormItemJsonKeys.FIELD_ALLOW_IMAGE_UPLOAD);
        JSONObject compressionJson = options.getJSONObject(OfflineFormItemJsonKeys.FIELD_COMPRESSION);
        if (compressionJson != null) {
            input.compression = compressionJson.toJavaObject(ImageCompressionOptions.class);
        }
        JSONObject watermarkJson = options.getJSONObject(OfflineFormItemJsonKeys.FIELD_WATERMARK);
        if (watermarkJson != null) {
            input.watermark = watermarkJson.toJavaObject(ImageWatermarkOptions.class);
        }
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
        JSONObject options = new JSONObject();
        options.put(OfflineFormItemJsonKeys.FIELD_MAX_COUNT, item.getMaxCount());
        if (item.isAllowImageUpload()) {
            options.put(OfflineFormItemJsonKeys.FIELD_ALLOW_IMAGE_UPLOAD, true);
        }

        ImageCompressionOptions compression = item.getCompression() == null ? new ImageCompressionOptions() : item.getCompression();
        JSONObject compressionJson = new JSONObject();
        compressionJson.put(OfflineFormItemJsonKeys.FIELD_ENABLE_COMPRESSION, compression.isEnableCompression());
        compressionJson.put(OfflineFormItemJsonKeys.FIELD_MAX_LONG_EDGE, compression.getMaxLongEdge());
        compressionJson.put(OfflineFormItemJsonKeys.FIELD_JPEG_QUALITY, compression.getJpegQuality());
        compressionJson.put(OfflineFormItemJsonKeys.FIELD_MAX_FILE_SIZE_KB, compression.getMaxFileSizeKb());
        compressionJson.put(OfflineFormItemJsonKeys.FIELD_MIN_QUALITY, compression.getMinQuality());
        options.put(OfflineFormItemJsonKeys.FIELD_COMPRESSION, compressionJson);

        ImageWatermarkOptions watermark = item.getWatermark();
        if (watermark != null && (watermark.isEnableTimestamp() || watermark.getItems() != null && !watermark.getItems().isEmpty())) {
            JSONObject watermarkJson = new JSONObject();
            watermarkJson.put(OfflineFormItemJsonKeys.FIELD_ENABLE_TIMESTAMP, watermark.isEnableTimestamp());
            watermarkJson.put(OfflineFormItemJsonKeys.FIELD_ITEMS, watermark.getItems());
            options.put(OfflineFormItemJsonKeys.FIELD_WATERMARK, watermarkJson);
        }
        return options;
    }
}
