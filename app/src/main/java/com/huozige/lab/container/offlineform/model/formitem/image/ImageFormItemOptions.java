package com.huozige.lab.container.offlineform.model.formitem.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFormItemOptions {
    private int maxCount;
    private boolean allowImageUpload;
    private ImageCompressionOptions compression;
    private ImageWatermarkOptions watermark;
}
