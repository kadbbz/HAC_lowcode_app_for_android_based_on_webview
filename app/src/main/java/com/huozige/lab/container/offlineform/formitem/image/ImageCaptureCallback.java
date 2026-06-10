package com.huozige.lab.container.offlineform.formitem.image;

import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;

public interface ImageCaptureCallback {
    void onImageCaptured(ImageFormItemValue image);
}
