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

    /**
     * 该字段是否视为“被碰过”，从而使其所在的填报单元参与校验。
     * 默认与“是否有值”一致；开关等控件即使处于“关”，也应始终参与校验，故会覆盖为 true。
     */
    public boolean countsAsTouched() {
        return !isEmpty();
    }

    /**
     * “必填”是否已满足。默认“非空即满足”；开关等按值判定的控件会覆盖为“已打开”。
     * 供 {@code validate()} 使用。
     */
    public boolean isRequiredSatisfied() {
        return !isRequired() || !isEmpty();
    }

    /**
     * 是否含有“用户实际录入”的数据（用于决定是否要新建一条草稿记录）。
     * 默认与“非空”一致；开关按“已打开”判定（默认的“关”不算录入）。
     */
    public boolean hasUserInput() {
        return !isEmpty();
    }
}
