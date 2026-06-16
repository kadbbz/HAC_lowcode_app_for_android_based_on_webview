package com.huozige.lab.container.offlineform.model.formitem.file;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileFormItem extends BaseFormItem {
    private FileItemConfig fileItemConfig = new FileItemConfig();
    private List<AttachmentFormItemValue> files = new ArrayList<>();
    private String patternId = "";

    public FileFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return JSON.toJSONString(files == null ? new ArrayList<>() : files);
    }

    @Override
    public boolean isEmpty() {
        return files == null || files.isEmpty();
    }

    public void setValue(String value) {
        files = parseAttachments(value);
    }

    public void addFile(AttachmentFormItemValue file) {
        if (file == null || file.getOriginalName() == null || file.getOriginalName().isEmpty()
                || file.getFileName() == null || file.getFileName().isEmpty()) {
            return;
        }
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }

    public void removeFile(String originalName) {
        if (files != null) {
            files.removeIf(file -> file != null && originalName.equals(file.getOriginalName()));
        }
    }

    public boolean containsOriginalName(String originalName) {
        if (files == null) {
            return false;
        }
        for (AttachmentFormItemValue file : files) {
            if (file != null && originalName.equals(file.getOriginalName())) {
                return true;
            }
        }
        return false;
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

    public static List<AttachmentFormItemValue> parseAttachments(String value) {
        List<AttachmentFormItemValue> result = new ArrayList<>();
        if (StringUtils.isNullOrBlank(value)) {
            return result;
        }
        try {
            List<AttachmentFormItemValue> attachments = JSON.parseArray(value, AttachmentFormItemValue.class);
            if (attachments == null) {
                return result;
            }
            for (AttachmentFormItemValue attachment : attachments) {
                if (attachment != null
                        && attachment.getOriginalName() != null && !attachment.getOriginalName().isEmpty()
                        && attachment.getFileName() != null && !attachment.getFileName().isEmpty()) {
                    result.add(attachment);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return result;
    }
}
