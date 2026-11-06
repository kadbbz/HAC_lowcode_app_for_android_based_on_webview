package com.huozige.lab.container.offlineform.model;

/**
 * 搜索结果中的一条字段命中记录。
 *
 * <p>用于「搜索结果列表 + 点击定位」：{@link #getParentFieldId()} 是表单里可直接定位的顶层字段 id
 * （列表模板子字段时是父列表字段），{@link #getTargetFieldId()} 是实际要高亮的字段 id
 * （列表模板子字段时是行内子字段 id）。</p>
 */
public class OfflineFormSearchResult {

    public static final String TYPE_FIELD = "field";
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_TEXT = "text";

    private final String type;
    private final OfflineFormNode node;

    private final String parentFieldId;
    private final String targetFieldId;
    private final String title;
    private final String path;
    private final String pageLabel;
    private final int pageIndex;
    private final String valueText;
    private final boolean required;
    private final boolean listChild;

    public OfflineFormSearchResult(String type,
                                   OfflineFormNode node,
                                   String parentFieldId,
                                   String targetFieldId,
                                   String title,
                                   String path,
                                   String pageLabel,
                                   int pageIndex,
                                   String valueText,
                                   boolean required,
                                   boolean listChild) {
        this.type = type;
        this.node = node;
        this.parentFieldId = parentFieldId;
        this.targetFieldId = targetFieldId;
        this.title = title;
        this.path = path;
        this.pageLabel = pageLabel;
        this.pageIndex = pageIndex;
        this.valueText = valueText;
        this.required = required;
        this.listChild = listChild;
    }

    public String getType() {
        return type;
    }

    public OfflineFormNode getNode() {
        return node;
    }

    public String getParentFieldId() {
        return parentFieldId;
    }

    public String getTargetFieldId() {
        return targetFieldId;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getPageLabel() {
        return pageLabel;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public String getValueText() {
        return valueText;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isListChild() {
        return listChild;
    }
}
