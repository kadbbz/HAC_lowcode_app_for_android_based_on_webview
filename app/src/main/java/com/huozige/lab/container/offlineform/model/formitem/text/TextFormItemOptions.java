package com.huozige.lab.container.offlineform.model.formitem.text;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextFormItemOptions {
    private int minLength;
    private int maxLength;
    private String regexPattern;
}
