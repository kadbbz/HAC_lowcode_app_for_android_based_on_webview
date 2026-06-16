package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OfflineFormNode {
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_FIELD = "field";

    private String nodeType = TYPE_GROUP;
    private String title = "";
    private String content = "";
    private boolean defaultCollapsed;
    private BaseFormItem field;
    private List<OfflineFormNode> children = new ArrayList<>();
}
