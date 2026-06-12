package com.huozige.lab.container.offlineform.formitem;

public enum OfflineFormItemType {
    TEXT("textItem"),
    PASSWORD("passwordItem"),
    SELECT("selectItem"),
    DATE_PICKER("datePicker"),
    TIME_PICKER("timePicker"),
    IMAGE("imageItem"),
    FILE("fileItem");

    private final String value;

    OfflineFormItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
