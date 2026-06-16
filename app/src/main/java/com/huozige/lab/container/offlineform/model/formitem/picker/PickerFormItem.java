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
