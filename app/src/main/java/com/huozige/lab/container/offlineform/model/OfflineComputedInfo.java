package com.huozige.lab.container.offlineform.model;

public class OfflineComputedInfo {
    // HAC 本地为表单定义计算出的主题色，用于列表卡片边条和头像。
    private String theme;

    public OfflineComputedInfo(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme == null ? "" : theme;
    }
}
