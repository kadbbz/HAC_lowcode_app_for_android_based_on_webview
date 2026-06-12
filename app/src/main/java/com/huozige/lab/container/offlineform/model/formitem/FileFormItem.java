package com.huozige.lab.container.offlineform.model.formitem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileFormItem extends BaseFormItem {
    private FileItemConfig fileItemConfig = new FileItemConfig();
    private LinkedHashMap<String, String> files = new LinkedHashMap<>();
    private String patternId = "";

    public FileFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return JSON.toJSONString(files == null ? new LinkedHashMap<>() : files);
    }

    @Override
    public boolean isEmpty() {
        return files == null || files.isEmpty();
    }

    public void setValue(String value) {
        files = parseFiles(value);
    }

    public void addFile(FileFormItemValue file) {
        if (file == null || file.getOriginalName() == null || file.getOriginalName().isEmpty()
                || file.getFileName() == null || file.getFileName().isEmpty()) {
            return;
        }
        if (files == null) {
            files = new LinkedHashMap<>();
        }
        files.put(file.getOriginalName(), file.getFileName());
    }

    public void removeFile(String originalName) {
        if (files != null) {
            files.remove(originalName);
        }
    }

    public boolean containsOriginalName(String originalName) {
        return files != null && files.containsKey(originalName);
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && isEmpty()) {
            setErrorMessage("请上传" + getTitle());
            return false;
        }
        int maxCount = fileItemConfig == null ? 0 : fileItemConfig.getMaxCount();
        if (maxCount > 0 && files != null && files.size() > maxCount) {
            setErrorMessage("最多只能上传" + maxCount + "个文件");
            return false;
        }
        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }

    public static LinkedHashMap<String, String> parseFiles(String value) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        if (value == null || value.trim().isEmpty()) {
            return result;
        }
        try {
            JSONObject object = JSON.parseObject(value);
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String fileName = String.valueOf(entry.getValue());
                    if (!entry.getKey().isEmpty() && !fileName.isEmpty()) {
                        result.put(entry.getKey(), fileName);
                    }
                }
            }
        } catch (RuntimeException ignored) {
        }
        return result;
    }
}
