package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;

/**
 * 表单渲染用的扁平节点。
 * <p>
 * definition 中的 step/items 是树形结构，RecyclerView 和当前 FormAdapter 渲染时需要按顺序遍历。
 * 这个类保存树节点拍平后的展示信息，同时保留层级深度，避免把渲染状态写回 definition 模型。
 */
public class OfflineFormDisplayItem {
    public static final int VIEW_TYPE_GROUP = 10001;
    public static final int VIEW_TYPE_TEXT = 10002;

    // group/text 节点使用，保存标题、内容、children、defaultCollapsed 等结构信息。
    private final OfflineFormNode node;
    // field 节点使用，保存具体表单项，后续交给对应表单项 handler 渲染。
    private final BaseFormItem formItem;
    // 当前节点在树中的深度，用于控制缩进和判断父子层级。
    private final int depth;
    // 当前 step 内的稳定展示编号，用于记录展开/收起状态。
    private final int displayId;

    private OfflineFormDisplayItem(OfflineFormNode node, BaseFormItem formItem, int depth, int displayId) {
        this.node = node;
        this.formItem = formItem;
        this.depth = depth;
        this.displayId = displayId;
    }

    /**
     * 创建 group 展示节点。
     */
    public static OfflineFormDisplayItem group(OfflineFormNode node, int depth, int displayId) {
        return new OfflineFormDisplayItem(node, null, depth, displayId);
    }

    /**
     * 创建普通说明文本展示节点。
     */
    public static OfflineFormDisplayItem text(OfflineFormNode node, int depth, int displayId) {
        return new OfflineFormDisplayItem(node, null, depth, displayId);
    }

    /**
     * 创建可填报字段展示节点。
     */
    public static OfflineFormDisplayItem field(OfflineFormNode node, int depth, int displayId) {
        return new OfflineFormDisplayItem(node, node.getField(), depth, displayId);
    }

    /**
     * 判断当前展示节点是否为 group。
     */
    public boolean isGroup() {
        return node != null && OfflineFormNode.TYPE_GROUP.equals(node.getNodeType());
    }

    /**
     * 判断当前展示节点是否为普通说明文本。
     */
    public boolean isText() {
        return node != null && OfflineFormNode.TYPE_TEXT.equals(node.getNodeType());
    }

    /**
     * 判断当前展示节点是否为可填报字段。
     */
    public boolean isField() {
        return formItem != null;
    }

    public String getFieldDisplayTitle() {
        if (!isField()) {
            return "";
        }
        String title = formItem.getTitle();
        if (title != null && !title.isEmpty()) {
            return title;
        }
        return node == null ? "" : node.getTitle();
    }

    /**
     * 获取 group/text 对应的原始节点。
     */
    public OfflineFormNode getNode() {
        return node;
    }

    /**
     * 获取 field 对应的表单项模型。
     */
    public BaseFormItem getFormItem() {
        return formItem;
    }

    /**
     * 获取当前展示节点的层级深度。
     */
    public int getDepth() {
        return depth;
    }

    /**
     * 获取当前展示节点在当前 step 内的展示编号。
     */
    public int getDisplayId() {
        return displayId;
    }
}
