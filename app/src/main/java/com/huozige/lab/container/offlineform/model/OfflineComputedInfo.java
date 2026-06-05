package com.huozige.lab.container.offlineform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineComputedInfo {
    // HAC 本地为表单定义计算出的主题色，用于列表卡片边条和头像。
    private String theme = "";

}
