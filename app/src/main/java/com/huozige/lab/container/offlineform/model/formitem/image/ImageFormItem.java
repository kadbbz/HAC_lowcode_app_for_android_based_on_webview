package com.huozige.lab.container.offlineform.model.formitem.image;

import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.Utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFormItem extends BaseFormItem {
    public static final int DEFAULT_MAX_COUNT = 0;

    private int maxCount = DEFAULT_MAX_COUNT;
    private boolean allowImageUpload;
    private ImageCompressionOptions compression = new ImageCompressionOptions();
    private ImageWatermarkOptions watermark = new ImageWatermarkOptions();
    private List<AttachmentFormItemValue> images = new ArrayList<>();
    private String patternId = "";

    public ImageFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return Utils.serializeAttachmentValues(images);
    }

    @Override
    public boolean isEmpty() {
        return images == null || images.isEmpty();
    }

    public void setValue(String value) {
        images = parseImages(value);
    }

    public void addImage(AttachmentFormItemValue image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public void removeImage(AttachmentFormItemValue image) {
        if (images != null) {
            images.remove(image);
        }
    }

    public void setImagesFromFileNames(List<String> fileNames) {
        images = new ArrayList<>();
        if (fileNames == null) {
            return;
        }
        for (String fileName : fileNames) {
            if (fileName != null && !fileName.isEmpty()) {
                AttachmentFormItemValue image = new AttachmentFormItemValue();
                image.setFileName(fileName);
                images.add(image);
            }
        }
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && isEmpty()) {
            setErrorMessage("请添加" + getTitle());
            return false;
        }
        if (maxCount > 0 && images != null && images.size() > maxCount) {
            setErrorMessage("最多只能添加" + maxCount + "张图片");
            return false;
        }
        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }

    public static List<AttachmentFormItemValue> parseImages(String value) {
        return Utils.parseAttachmentValues(value, image -> image.getFileName() != null && !image.getFileName().isEmpty());
    }
}
