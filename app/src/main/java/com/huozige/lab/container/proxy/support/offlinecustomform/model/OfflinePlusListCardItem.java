package com.huozige.lab.container.proxy.support.offlinecustomform.model;

public class OfflinePlusListCardItem {
    private String title;
    private String description;
    private String status;
    private String patternId;

    public OfflinePlusListCardItem(String title, String description, String status, String patternId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.patternId = patternId;
    }

    // Getter方法
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPatternId() { return patternId; }
}
