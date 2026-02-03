package com.huozige.lab.container.proxy.support.offlinecustomform.dto;

import java.util.List;

public class FormItemInput {
    public String itemId;
    public String title;
    public String hint = "";
    public boolean required;
    public String itemType;
    public String value;

    public CheckOptionsInput checkOptions;
    public List<SelectOptionsInput> selectOptionsList;
}
