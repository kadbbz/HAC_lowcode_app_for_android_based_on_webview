package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PatternInput {
    public String ticket = "";

    public String patternId = "";
    public String schemaVersion = "";
    public String title = "";
    public String description = "";

    public List<OfflineFormCardStyle> cardStyle = new ArrayList<>();
    public List<PatternStepInput> steps = new ArrayList<>();
    public List<String> displayColumns = new ArrayList<>();
}


