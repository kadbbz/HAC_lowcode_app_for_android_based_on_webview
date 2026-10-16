package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 逐组分页布局中的一页。
 *
 * <p>分页语义：步骤内的每个顶级节点各成一页。当顶级节点是分组时，页面内容是该分组的直接子节点
 * （已重定基到 depth 0），分组自身不再渲染；只有嵌套的子分组才保留分组标题与折叠。</p>
 */
public class OfflineFormPage {
    // 触发本页的顶级节点，用于取标题与校验定位。
    private final OfflineFormNode rootNode;
    // 本页的展示项，已重定基到 depth 0。
    private final List<OfflineFormDisplayItem> items;

    public OfflineFormPage(OfflineFormNode rootNode, List<OfflineFormDisplayItem> items) {
        this.rootNode = rootNode;
        this.items = items == null ? new ArrayList<>() : items;
    }

    public OfflineFormNode getRootNode() {
        return rootNode;
    }

    public List<OfflineFormDisplayItem> getItems() {
        return items;
    }
}
