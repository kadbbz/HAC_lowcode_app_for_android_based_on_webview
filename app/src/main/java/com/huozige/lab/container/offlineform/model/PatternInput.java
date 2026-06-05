package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PatternInput {
    public String ticket = "";

    public String patternId = "";
    public String schemaVersion = "";
    public String title = "";
    public String description = "";

    public List<FormItemInput> formItems = new ArrayList<>();
}
