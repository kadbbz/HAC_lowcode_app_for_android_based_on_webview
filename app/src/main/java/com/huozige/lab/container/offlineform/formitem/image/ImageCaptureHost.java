package com.huozige.lab.container.offlineform.formitem.image;

import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;

public interface ImageCaptureHost {
    void captureImage(ImageFormItem item, ImageCaptureCallback callback);
}
