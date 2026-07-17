package com.huozige.lab.container.offlineform.formitem;

public enum OfflineFormItemViewType {
    TEXT(1),
    PASSWORD(2),
    SELECT(3),
    DATE_PICKER(4),
    TIME_PICKER(5),
    IMAGE(6),
    FILE(7),
    LIST(8);

    private final int value;

    OfflineFormItemViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
