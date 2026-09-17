package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OfflineFormDefinitionOrder {
    // 离线表单配置的展示顺序，只保存业务 key，具体标题和版本号从 definition.json 读取。
    private List<String> patternIds = new ArrayList<>();
}
