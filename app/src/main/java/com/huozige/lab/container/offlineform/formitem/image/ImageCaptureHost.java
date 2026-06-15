package com.huozige.lab.container.offlineform.formitem.image;

import com.huozige.lab.container.offlineform.formitem.AttachmentCallback;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;

import java.util.ArrayList;

public interface ImageCaptureHost {
    void captureImage(ImageFormItem item, AttachmentCallback callback);

    void uploadImage(ImageFormItem item, AttachmentCallback callback);

    void previewImages(ImageFormItem item, ArrayList<String> fileNames, int index);

    void onImagesChanged(ImageFormItem item);
}
