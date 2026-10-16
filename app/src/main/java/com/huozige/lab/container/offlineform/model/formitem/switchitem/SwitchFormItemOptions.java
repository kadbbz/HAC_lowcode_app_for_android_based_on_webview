package com.huozige.lab.container.offlineform.model.formitem.switchitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwitchFormItemOptions {
    public static final String STYLE_SWITCH = "switch";
    public static final String STYLE_CHECKBOX = "checkbox";

    /** 呈现样式：switch（开关）/ checkbox（标准复选框）。 */
    private String style = STYLE_SWITCH;

    public String normalizedStyle() {
        return STYLE_CHECKBOX.equalsIgnoreCase(style) ? STYLE_CHECKBOX : STYLE_SWITCH;
    }
}
