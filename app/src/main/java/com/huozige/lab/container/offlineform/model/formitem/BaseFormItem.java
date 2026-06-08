package com.huozige.lab.container.offlineform.model.formitem;

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
    private String errorMessage;

    public BaseFormItem(String itemType, String id, String title, String hint, boolean required) {
        this.itemType = itemType;
        this.id = id;
        this.title = title;
        this.hint = hint;
        this.required = required;
    }

    public abstract String getValue();
    public abstract boolean validate();
    public abstract void clearError();
}
