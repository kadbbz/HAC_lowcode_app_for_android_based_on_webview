package com.huozige.lab.container.offlineform.model.formitem.picker;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.utilities.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickerFormItem extends BaseFormItem {
    private String value = "";
    private boolean includeSeconds;
    private boolean includeTime;

    /** 日期范围模式：flexible（灵活选择，默认）或 deadline（截止日期，最晚到当前时刻）。 */
    private String dateRangeMode = "flexible";

    /** 灵活选择模式下的最早可选日期，格式 yyyy/M/d，可空。 */
    private String minDate;

    /** 灵活选择模式下的最晚可选日期，格式 yyyy/M/d，可空。 */
    private String maxDate;

    public PickerFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isNullOrBlank(value);
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && isEmpty()) {
            setErrorMessage("请选择" + getTitle());
            return false;
        }
        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }
}
