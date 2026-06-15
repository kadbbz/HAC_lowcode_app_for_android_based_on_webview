package com.huozige.lab.container.offlineform.formitem.file;

import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;

/**
 * 文件上传回调。
 */
public interface FileUploadHost {
    void uploadFile(FileFormItem item, FileUploadCallback callback);

    void onFilesChanged(FileFormItem item);
}
