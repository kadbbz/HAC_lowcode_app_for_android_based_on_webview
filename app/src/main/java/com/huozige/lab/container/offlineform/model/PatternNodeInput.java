package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PatternNodeInput {
    public String nodeType = OfflineFormNode.TYPE_GROUP;
    public String title = "";
    public String content = "";
    public boolean defaultCollapsed;
    public FormItemInput field;
    public List<PatternNodeInput> children = new ArrayList<>();
}
