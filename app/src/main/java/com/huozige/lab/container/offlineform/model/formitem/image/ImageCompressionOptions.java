package com.huozige.lab.container.offlineform.model.formitem.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCompressionOptions {
    public static final int DEFAULT_MAX_LONG_EDGE = 1600;
    public static final int DEFAULT_JPEG_QUALITY = 80;
    public static final int DEFAULT_MAX_FILE_SIZE_KB = 0;
    public static final int DEFAULT_MIN_QUALITY = 60;

    private boolean enableCompression;
    private int maxLongEdge = DEFAULT_MAX_LONG_EDGE;
    private int jpegQuality = DEFAULT_JPEG_QUALITY;
    private int maxFileSizeKb = DEFAULT_MAX_FILE_SIZE_KB;
    private int minQuality = DEFAULT_MIN_QUALITY;
}
