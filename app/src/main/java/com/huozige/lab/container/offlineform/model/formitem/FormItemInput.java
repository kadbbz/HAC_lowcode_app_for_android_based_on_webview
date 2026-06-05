package com.huozige.lab.container.offlineform.model.formitem;

import java.util.List;

public class FormItemInput {
    public String itemId;
    public String title;
    public String hint = "";
    public boolean required;
    public String itemType;
    public String value;

    public FormItemValidationInput checkOptions;
    public List<SelectOptionsInput> selectOptionsList;
}
