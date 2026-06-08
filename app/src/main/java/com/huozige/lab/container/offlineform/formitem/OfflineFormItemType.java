package com.huozige.lab.container.offlineform.formitem;

public enum OfflineFormItemType {
    TEXT("textItem"),
    PASSWORD("passwordItem"),
    SELECT("selectItem");

    private final String value;

    OfflineFormItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
