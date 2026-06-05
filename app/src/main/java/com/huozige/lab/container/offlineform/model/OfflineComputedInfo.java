package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineComputedInfo {
    // HAC 本地为表单定义计算出的主题色，用于列表卡片边条和头像。
    private String theme = "";
    // 历史填报记录表格中展示的业务字段列，只保存表单项 fieldId，固定列不放在这里。
    private List<String> displayColumns = new ArrayList<>();

}
