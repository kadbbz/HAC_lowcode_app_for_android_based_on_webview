package com.huozige.lab.container.offlineform.model.formitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFormItemValue {
    private String fileId;
    private String localPath;
    private String fileName;
    private String mimeType;
    private long size;
    private int width;
    private int height;
    private long createdAt;
}
