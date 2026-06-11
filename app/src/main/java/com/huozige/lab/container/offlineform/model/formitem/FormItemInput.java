package com.huozige.lab.container.offlineform.model.formitem;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FormItemInput {
    public String itemId = "";
    public String title = "";
    public String hint = "";
    public boolean required;
    public String itemType = "";
    public String value = "";

    public FormItemValidationInput checkOptions;
    @JSONField(name = "options")
    public List<SelectOptionsInput> selectOptionsList = new ArrayList<>();
    public boolean includeSeconds;
    public int maxCount;
    public boolean allowImageUpload;
    public ImageCompressionOptions compression;
}
