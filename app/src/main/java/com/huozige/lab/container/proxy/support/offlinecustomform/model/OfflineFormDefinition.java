package com.huozige.lab.container.proxy.support.offlinecustomform.model;

import java.util.ArrayList;
import java.util.List;

public class OfflineFormDefinition {
    // 表单定义的业务唯一标识，也是本地文件夹名称。
    private String patternId;
    // 表单定义版本号，由插件传入，填报记录保存时会写入记录文件。
    private String schemaVersion;
    // 表单定义名称，用于历史填报列表主标题展示。
    private String title;
    // 表单定义备注，用于历史填报列表描述展示。
    private String description;
    // 表单字段定义，原生填报页根据该列表渲染控件。
    private List<BaseFormItem> formItems;

    public OfflineFormDefinition(String patternId, String schemaVersion, String title, String description, List<BaseFormItem> formItems) {
        this.patternId = patternId;
        this.schemaVersion = schemaVersion;
        this.title = title;
        this.description = description;
        this.formItems = formItems;
    }

    public String getPatternId() { return patternId == null ? "" : patternId; }
    public String getSchemaVersion() { return schemaVersion == null ? "" : schemaVersion; }
    public String getTitle() { return title == null ? "" : title; }
    public String getDescription() { return description == null ? "" : description; }
    public List<BaseFormItem> getFormItems() { return formItems == null ? new ArrayList<>() : formItems; }
}
