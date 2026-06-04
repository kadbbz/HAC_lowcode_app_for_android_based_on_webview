package com.huozige.lab.container.proxy.support.offlinecustomform.model;

public class OfflineFormDefinitionIndexItem {
    // 表单定义名称，用于历史填报列表主标题展示。
    private String title;
    // 表单定义备注，用于历史填报列表描述展示。
    private String description;
    // 预留状态字段，后续区分草稿、已提交、已同步等状态。
    private String status;
    // 表单定义的业务唯一标识，也是本地文件夹名称。
    private String patternId;
    // 表单定义版本号，由插件传入，填报记录保存时会写入记录文件。
    private String schemaVersion;
    // HAC 本地计算出的展示信息。
    private OfflineComputedInfo computed;

    public OfflineFormDefinitionIndexItem(String title, String description, String status, String patternId, String schemaVersion, OfflineComputedInfo computed) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.patternId = patternId;
        this.schemaVersion = schemaVersion;
        this.computed = computed;
    }

    public String getTitle() { return title == null ? "" : title; }
    public String getDescription() { return description == null ? "" : description; }
    public String getStatus() { return status == null ? "" : status; }
    public String getPatternId() { return patternId == null ? "" : patternId; }
    public String getSchemaVersion() { return schemaVersion == null ? "" : schemaVersion; }
    public OfflineComputedInfo getComputed() { return computed == null ? new OfflineComputedInfo("") : computed; }
    public String getTheme() { return computed == null ? "" : computed.getTheme(); }
}
