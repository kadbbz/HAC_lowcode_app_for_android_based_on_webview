package com.huozige.lab.container.offlineform.model.formitem.list;

import com.huozige.lab.container.offlineform.model.PatternNodeInput;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ListFormItemOptions {
    private int minCount;
    private int maxCount;
    private int defaultCount;
    private String addButtonText = "";
    private String itemTitle = "";
    private List<PatternNodeInput> children = new ArrayList<>();
    private List<PatternNodeInput> items = new ArrayList<>();

    public List<PatternNodeInput> resolveTemplateNodes() {
        if (children != null && !children.isEmpty()) {
            return children;
        }
        return items == null ? new ArrayList<>() : items;
    }
}
