package com.huozige.lab.container.offlineform.model.formitem;

import lombok.Data;

@Data
public class FormItemValidationInput {
    public int minLength;

    public int maxLength;

    public String regexPattern;

}
