package com.huozige.lab.container.offlineform.model.formitem.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseFormItem {
    private String itemType;
    private String id;
    private String title;
    private String hint;
    private boolean required;
    // 是否在填报页展示该字段的更新时间（字段级开关，缺省不显示）。
    private boolean showFieldUpdateTime;
    // 修改前二次确认配置（仅部分组件下发，缺省不启用）。
    private FormItemConfirmOptions confirmOptions;
    private String errorMessage;
    // 组件定义的最后更新时间，使用 OLE Automation Date（ODate）表示。
    private Double updateTime;

    public BaseFormItem(String itemType, String id, String title, String hint, boolean required) {
        this.itemType = itemType;
        this.id = id;
        this.title = title;
        this.hint = hint;
        this.required = required;
    }

    public abstract String getValue();
    public abstract boolean isEmpty();
    public abstract boolean validate();
    public abstract void clearError();

    /**
     * 用于界面展示的值文本；默认与存储值一致。
     * 选项类字段（下拉选择、单选框）会覆盖为所选选项的显示文本。
     */
    public String getDisplayValue() {
        return getValue();
    }
}
