package com.huozige.lab.container.offlineform.formitem.file;

import com.huozige.lab.container.offlineform.formitem.AttachmentCallback;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;

/**
 * 文件上传回调。
 */
public interface FileUploadHost {
    void uploadFile(FileFormItem item, AttachmentCallback callback);

    void onFilesChanged(FileFormItem item);
}
