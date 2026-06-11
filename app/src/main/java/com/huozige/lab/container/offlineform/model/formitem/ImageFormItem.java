package com.huozige.lab.container.offlineform.model.formitem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFormItem extends BaseFormItem {
    public static final int DEFAULT_MAX_COUNT = 9;

    private int maxCount = DEFAULT_MAX_COUNT;
    private ImageCompressionOptions compression = new ImageCompressionOptions();
    private List<ImageFormItemValue> images = new ArrayList<>();
    private String patternId = "";

    public ImageFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        List<String> fileNames = new ArrayList<>();
        if (images != null) {
            for (ImageFormItemValue image : images) {
                if (image != null && image.getFileName() != null && !image.getFileName().isEmpty()) {
                    fileNames.add(image.getFileName());
                }
            }
        }
        return JSON.toJSONString(fileNames);
    }

    @Override
    public boolean isEmpty() {
        return images == null || images.isEmpty();
    }

    public void setValue(String value) {
        images = parseImages(value);
    }

    public void addImage(ImageFormItemValue image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public void removeImage(ImageFormItemValue image) {
        if (images != null) {
            images.remove(image);
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

    public static List<ImageFormItemValue> parseImages(String value) {
        List<ImageFormItemValue> result = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            return result;
        }
        try {
            JSONArray array = JSON.parseArray(value);
            for (int i = 0; i < array.size(); i++) {
                String fileName = array.getString(i);
                if (fileName != null && !fileName.isEmpty()) {
                    ImageFormItemValue image = new ImageFormItemValue();
                    image.setFileName(fileName);
                    result.add(image);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return result;
    }
}
