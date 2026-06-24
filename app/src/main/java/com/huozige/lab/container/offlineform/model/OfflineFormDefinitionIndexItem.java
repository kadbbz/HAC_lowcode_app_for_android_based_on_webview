package com.huozige.lab.container.offlineform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormDefinitionIndexItem {
    // 表单定义名称，用于历史填报列表主标题展示。
    private String title = "";
    // 表单定义备注，用于历史填报列表描述展示。
    private String description = "";
    // 预留状态字段，后续区分草稿、已提交、已同步等状态。
    private String status = "";
    // 表单定义的业务唯一标识，也是本地文件夹名称。
    private String patternId = "";
    // 表单定义版本号，由插件传入，填报记录保存时会写入记录文件。
    private String schemaVersion = "";
    // HAC 本地计算出的展示信息。
    private OfflineComputedInfo computed = new OfflineComputedInfo();

}
