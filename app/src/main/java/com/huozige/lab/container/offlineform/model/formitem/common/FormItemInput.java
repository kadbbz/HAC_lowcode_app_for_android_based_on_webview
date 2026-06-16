package com.huozige.lab.container.offlineform.model.formitem.common;

import lombok.Data;

@Data
public class FormItemInput {
    public String itemId = "";
    public String title = "";
    public String hint = "";
    public boolean required;
    public String itemType = "";
    public String value = "";
    public Object options;
}
