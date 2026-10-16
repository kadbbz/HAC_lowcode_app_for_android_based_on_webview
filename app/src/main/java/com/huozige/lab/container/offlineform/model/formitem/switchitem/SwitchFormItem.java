package com.huozige.lab.container.offlineform.model.formitem.switchitem;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import lombok.Getter;
import lombok.Setter;

/**
 * 单项布尔勾选：本质是“单个 checkbox”，可呈现为开关或标准复选框（仅外观不同）。
 *
 * <p>取值：选中/开 = {@code "true"}；未选中/关 = {@code "false"}。</p>
 *
 * <p><b>空值口径：</b>未选中（{@code false}）一律视为“空（未填）”。因此：
 * 未选中不会让所在填报单元被判为“碰过”（与文本框等一致，未碰过则豁免校验）；
 * 作为“填报标识”时，未选中也不算“已填”；进度、仅未填均按此口径。</p>
 *
 * <p>“必填”按值判定：{@link #isRequiredSatisfied()}（继承默认）要求必须选中/打开。</p>
 */
@Getter
@Setter
public class SwitchFormItem extends BaseFormItem {
    private String value = "false";
    // 呈现样式：switch（开关）/ checkbox（标准复选框）。仅影响外观。
    private String style = SwitchFormItemOptions.STYLE_SWITCH;

    public SwitchFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return value;
    }

    public boolean isCheckboxStyle() {
        return SwitchFormItemOptions.STYLE_CHECKBOX.equalsIgnoreCase(style);
    }

    public boolean isChecked() {
        return isCheckedValue(value);
    }

    public void setChecked(boolean checked) {
        this.value = checked ? "true" : "false";
    }

    /**
     * 解析“开/关”取值：1/true/yes/y/是 视为开，其余（0/false/no/n/否/空）视为关。
     * 供默认值等外部输入使用。
     */
    public static boolean isCheckedValue(String raw) {
        if (raw == null) {
            return false;
        }
        String normalized = raw.trim().toLowerCase();
        return "true".equals(normalized)
                || "1".equals(normalized)
                || "yes".equals(normalized)
                || "y".equals(normalized)
                || "是".equals(normalized);
    }

    @Override
    public String getDisplayValue() {
        // 供二次确认等展示使用：以“是/否”呈现。
        return isChecked() ? "是" : "否";
    }

    @Override
    public boolean isEmpty() {
        // 未选中（false）一律视为“空/未填”。
        return !isChecked();
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && isEmpty()) {
            setErrorMessage((isCheckboxStyle() ? "请勾选" : "请确认") + getTitle());
            return false;
        }
        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }
}
