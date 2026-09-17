package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PatternStepInput {
    public String stepId = "";
    public String title = "";
    public List<PatternNodeInput> items = new ArrayList<>();
}
