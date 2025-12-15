package com.huozige.lab.container.proxy.support.customform.model;

public abstract class BaseFormItem {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_SELECT = 2;
    public static final int TYPE_SWITCH = 3;
    public static final int TYPE_DATE = 4;

    private String id;
    private String title;
    private String hint;
    private boolean required;
    private String errorMessage;

    public BaseFormItem(String id, String title, String hint, boolean required) {
        this.id = id;
        this.title = title;
        this.hint = hint;
        this.required = required;
    }

    public abstract int getItemType();

    // Getter/Setter
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getHint() { return hint; }
    public boolean isRequired() { return required; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public abstract String getValue();
    public abstract boolean validate();
    public abstract void clearError();
}