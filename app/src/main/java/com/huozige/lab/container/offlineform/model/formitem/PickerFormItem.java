package com.huozige.lab.container.offlineform.model.formitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickerFormItem extends BaseFormItem {
    private String value = "";

    public PickerFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && (value == null || value.trim().isEmpty())) {
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
