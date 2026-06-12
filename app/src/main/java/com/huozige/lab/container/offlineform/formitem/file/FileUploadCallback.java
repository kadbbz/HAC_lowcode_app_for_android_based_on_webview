package com.huozige.lab.container.offlineform.formitem.file;

import com.huozige.lab.container.offlineform.model.formitem.FileFormItemValue;

import java.util.List;

public interface FileUploadCallback {
    void onFilesUploaded(List<FileFormItemValue> files);
}
