package com.huozige.lab.container.proxy.support.offlinecustomform.model;

import java.util.ArrayList;
import java.util.List;

public class OfflineFormDefinitionIndex {
    // 已下载表单定义列表，列表顺序即历史填报页展示顺序。
    private List<OfflineFormDefinitionIndexItem> items;

    public OfflineFormDefinitionIndex(List<OfflineFormDefinitionIndexItem> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }

    public List<OfflineFormDefinitionIndexItem> getItems() {
        return items;
    }
}
