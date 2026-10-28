package com.huozige.lab.container.offlineform.model.formitem.radio;

import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItemOptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RadioFormItemOptions extends SelectFormItemOptions {
    public static final String DIRECTION_HORIZONTAL = "horizontal";
    public static final String DIRECTION_VERTICAL = "vertical";

    /** 单选项排列方向，未指定或非法值按纵向处理。 */
    private String direction = DIRECTION_VERTICAL;

    public boolean isHorizontal() {
        return DIRECTION_HORIZONTAL.equalsIgnoreCase(direction);
    }

    public String normalizedDirection() {
        return isHorizontal() ? DIRECTION_HORIZONTAL : DIRECTION_VERTICAL;
    }
}
