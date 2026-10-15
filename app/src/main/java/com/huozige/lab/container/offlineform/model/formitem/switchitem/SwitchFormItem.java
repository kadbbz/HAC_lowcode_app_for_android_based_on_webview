package com.huozige.lab.container.offlineform.model.formitem.switchitem;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import lombok.Getter;
import lombok.Setter;

/**
 * 单项布尔勾选：本质是“单个 checkbox”，可呈现为开关或标准复选框。
 *
 * <p><b>两种呈现的取值/空值语义不同：</b></p>
 * <ul>
 *     <li><b>开关</b>：始终有值——开 = {@code "true"}，关 = {@code "false"}；未选中也算“已填”，
 *     其所在填报单元总是参与校验（保证“必填=必须打开”生效），且不会出现在“仅未填”。</li>
 *     <li><b>标准复选框</b>：未选中 = 空（{@code ""}）；与文本框等口径一致，未选中不计已填、可进“仅未填”。</li>
 * </ul>
 */
@Getter
@Setter
public class SwitchFormItem extends BaseFormItem {
    private String value = "false";
    // 呈现样式：switch（开关）/ checkbox（标准复选框）。
    private String style = SwitchFormItemOptions.STYLE_SWITCH;

    public SwitchFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return value;
    }

    public boolean isSwitchStyle() {
        return !SwitchFormItemOptions.STYLE_CHECKBOX.equalsIgnoreCase(style);
    }

    public boolean isCheckboxStyle() {
        return SwitchFormItemOptions.STYLE_CHECKBOX.equalsIgnoreCase(style);
    }

    public boolean isChecked() {
        return isCheckedValue(value);
    }

    public void setChecked(boolean checked) {
        // 开关始终有值（true/false）；复选框未选中即“空”。
        if (isCheckboxStyle()) {
            this.value = checked ? "true" : "";
        } else {
            this.value = checked ? "true" : "false";
        }
    }

    /**
     * 解析“开/关”取值：1/true/yes/y/是 视为开，其余（0/false/no/n/否/空）视为关。
     * 供默认值等外部输入使用，实际写入时按呈现样式归一化（开关为 true/false，复选框为 true/空）。
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
        // 开关始终有值；复选框未选中视为空。
        return isCheckboxStyle() && !isChecked();
    }

    @Override
    public boolean countsAsTouched() {
        // 开关始终参与校验；复选框按是否选中（与空值口径一致）。
        return isSwitchStyle() || isChecked();
    }

    @Override
    public boolean isRequiredSatisfied() {
        // 必填 = 必须选中/打开（两种呈现一致）。
        return !isRequired() || isChecked();
    }

    @Override
    public boolean hasUserInput() {
        // 只按“已打开”判定是否有用户录入：默认的“关/未选中”不算录入，避免仅打开表单就生成草稿。
        return isChecked();
    }

    @Override
    public boolean validate() {
        clearError();
        if (!isRequiredSatisfied()) {
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
