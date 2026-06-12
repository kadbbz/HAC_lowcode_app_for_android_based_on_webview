package com.huozige.lab.container.offlineform.model.formitem;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageWatermarkOptions {
    private boolean enableTimestamp;
    private List<ImageWatermarkField> items = new ArrayList<>();
}
