package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PatternNodeInput {
    public String nodeType = OfflineFormNode.TYPE_GROUP;
    public String title = "";
    public String content = "";
    public boolean defaultCollapsed;
    // 根节点开启后作为一个填报进度统计单位；group 子节点使用 progressIdentifier 作为标识项。
    public boolean enableProgress;
    public boolean progressIdentifier;
    // 兼容插件端可能使用的描述性字段名，转换时统一归一化到上面两个字段。
    public boolean enableProgressStatistics;
    public boolean isProgressItem;
    public boolean isFillProgressItem;
    public FormItemInput field;
    public List<PatternNodeInput> children = new ArrayList<>();
}
