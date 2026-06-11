package com.huozige.lab.container.offlineform.model.formitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextFormItem extends BaseFormItem {
    private String value = "";
    private int inputType = android.text.InputType.TYPE_CLASS_TEXT;
    private int maxLength = -1;
    private int minLength = -1;
    private String regexPattern;

    public TextFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public boolean validate() {
        clearError();

        if (isRequired() && isEmpty()) {
            setErrorMessage("此项为必填项");
            return false;
        }

        if (minLength > 0 && value.length() < minLength) {
            setErrorMessage("至少需要" + minLength + "个字符");
            return false;
        }

        if (maxLength > 0 && value.length() > maxLength) {
            setErrorMessage("不能超过" + maxLength + "个字符");
            return false;
        }

        if (regexPattern != null && !value.matches(regexPattern)) {
            setErrorMessage("格式不正确");
            return false;
        }

        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }

    public boolean isPassword() {
        return (inputType & android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD) == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }
}
