package com.huozige.lab.container.offlineform.formitem;

public enum OfflineFormItemViewType {
    TEXT(1),
    PASSWORD(2),
    SELECT(3);

    private final int value;

    OfflineFormItemViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
