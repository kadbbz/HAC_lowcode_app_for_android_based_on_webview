package com.huozige.lab.container.offlineform.model.formitem.radio;

import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItem;

public class RadioFormItem extends SelectFormItem {
    private String direction = RadioFormItemOptions.DIRECTION_VERTICAL;

    public RadioFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = RadioFormItemOptions.DIRECTION_HORIZONTAL.equalsIgnoreCase(direction)
                ? RadioFormItemOptions.DIRECTION_HORIZONTAL
                : RadioFormItemOptions.DIRECTION_VERTICAL;
    }

    public boolean isHorizontal() {
        return RadioFormItemOptions.DIRECTION_HORIZONTAL.equals(direction);
    }
}
