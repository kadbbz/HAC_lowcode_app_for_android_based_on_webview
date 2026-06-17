package com.huozige.lab.container.offlineform.formitem.image;

import com.huozige.lab.container.offlineform.formitem.AttachmentCallback;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;

import java.util.ArrayList;

/**
 * 支持拍照和上传图片的接口
 */
public interface ImageCaptureHost {
    void captureImage(ImageFormItem item, AttachmentCallback callback);

    void uploadImage(ImageFormItem item, AttachmentCallback callback);

    void previewImages(ImageFormItem item, ArrayList<String> fileNames, int index);

    void onImagesChanged(ImageFormItem item);
}
