package com.huozige.lab.container.offlineform.model;

public class OfflineFormDefinitionFile {
    // 插件下发的表单定义内容。
    private OfflineFormDefinition jsonSchema;
    // HAC 本地计算出的展示信息。
    private OfflineComputedInfo computed;

    public OfflineFormDefinitionFile(OfflineFormDefinition jsonSchema, OfflineComputedInfo computed) {
        this.jsonSchema = jsonSchema;
        this.computed = computed;
    }

    public OfflineFormDefinition getJsonSchema() { return jsonSchema; }
    public OfflineComputedInfo getComputed() { return computed == null ? new OfflineComputedInfo("") : computed; }
}
