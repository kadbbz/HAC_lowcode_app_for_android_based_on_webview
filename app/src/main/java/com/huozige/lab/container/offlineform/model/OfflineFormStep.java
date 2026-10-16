package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OfflineFormStep {
    private String stepId = "";
    private String title = "";
    private List<OfflineFormNode> items = new ArrayList<>();
}
