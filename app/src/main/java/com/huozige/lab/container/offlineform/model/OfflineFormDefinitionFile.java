package com.huozige.lab.container.offlineform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormDefinitionFile {
    // 插件下发的表单定义内容。
    private OfflineFormDefinition jsonSchema;
    // HAC 本地计算出的展示信息。
    private OfflineComputedInfo computed = new OfflineComputedInfo();

}
