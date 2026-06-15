package com.huozige.lab.container.offlineform.formitem.file;

import com.huozige.lab.container.offlineform.model.formitem.FileFormItemValue;

import java.util.List;

/**
 * 文件上传回调。
 */
public interface FileUploadCallback {
    void onFilesUploaded(List<FileFormItemValue> files);
}
