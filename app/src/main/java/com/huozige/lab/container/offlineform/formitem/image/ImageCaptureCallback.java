package com.huozige.lab.container.offlineform.formitem.image;

import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;

import java.util.List;

public interface ImageCaptureCallback {
    void onImagesCaptured(List<ImageFormItemValue> images);
}
