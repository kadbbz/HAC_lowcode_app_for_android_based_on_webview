package com.huozige.lab.container.offlineform.model.formitem;

public class TextFormItem extends BaseFormItem {
    private String value = "";
    private int inputType = android.text.InputType.TYPE_CLASS_TEXT;
    private int maxLength = -1;
    private int minLength = -1;
    private String regexPattern;

    public TextFormItem(String id, String title, String hint, boolean required) {
        super(id, title, hint, required);
    }

    @Override
    public int getItemType() {
        return TYPE_TEXT;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean validate() {
        clearError();

        if (isRequired() && (value == null || value.trim().isEmpty())) {
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

    // 扩展方法
    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public int getInputType() {
        return inputType;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public boolean isPassword() {
        return (inputType & android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD) == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }
}
