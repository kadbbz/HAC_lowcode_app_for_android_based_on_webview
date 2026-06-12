package com.huozige.lab.container.offlineform.model.formitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileItemConfig {
    private int maxCount;
    private String allowedExtensions = "";
    private int maxFileSizeKb;
}
