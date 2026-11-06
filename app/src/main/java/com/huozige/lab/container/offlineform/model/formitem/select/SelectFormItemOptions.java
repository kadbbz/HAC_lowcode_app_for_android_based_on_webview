package com.huozige.lab.container.offlineform.model.formitem.select;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectFormItemOptions {
    private List<SelectOptionsInput> selectOptions = new ArrayList<>();
}
