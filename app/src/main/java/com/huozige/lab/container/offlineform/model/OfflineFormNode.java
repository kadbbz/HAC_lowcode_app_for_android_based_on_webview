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
    // 仅根节点的该配置参与统计；嵌套节点的值不会单独形成统计单位。
    private boolean enableProgress;
    // group 内字段节点的填报标识配置。
    private boolean progressIdentifier;
    private BaseFormItem field;
    private List<OfflineFormNode> children = new ArrayList<>();
}
