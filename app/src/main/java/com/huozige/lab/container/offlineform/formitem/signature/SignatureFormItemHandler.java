package com.huozige.lab.container.offlineform.formitem.signature;

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
import com.huozige.lab.container.offlineform.model.formitem.image.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageWatermarkOptions;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItemOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class SignatureFormItemHandler implements OfflineFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.SIGNATURE.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.SIGNATURE.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        SignatureFormItem item = new SignatureFormItem(getType(), input.itemId, input.title, input.hint, input.required);
        SignatureFormItemOptions options = (SignatureFormItemOptions) input.options;
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
        return SignatureFormItemOptions.class;
    }

    @Override
    public JSONObject toJson(BaseFormItem item) {
        SignatureFormItem signatureItem = (SignatureFormItem) item;
        JSONObject jsonObject = OfflineFormItemJsonHelper.buildBaseOutput(signatureItem);
        JSONObject options = new JSONObject();

        ImageCompressionOptions compression = signatureItem.getCompression();
        if (compression != null) {
            options.put("compression", compression);
        }

        ImageWatermarkOptions watermark = signatureItem.getWatermark();
        if (watermark != null && (watermark.isEnableTimestamp()
                || watermark.getItems() != null && !watermark.getItems().isEmpty())) {
            JSONObject watermarkJson = new JSONObject();
            watermarkJson.put(OfflineFormItemJsonKeys.FIELD_ENABLE_TIMESTAMP, watermark.isEnableTimestamp());
            watermarkJson.put(OfflineFormItemJsonKeys.FIELD_ITEMS, watermark.getItems());
            options.put(OfflineFormItemJsonKeys.FIELD_WATERMARK, watermarkJson);
        }
        jsonObject.put(OfflineFormItemJsonKeys.FIELD_OPTIONS, options);
        return jsonObject;
    }

    @Override
    public BaseViewHolder createEditViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_form_signature, parent, false);
        return new SignatureViewHolder(view);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        return ReadOnlyFormItemViews.createCompactValueView(
                context,
                SignatureFormItem.parseImages(rawValue).isEmpty()
                        ? ""
                        : context.getString(R.string.offline_text_signature_set));
    }
}
