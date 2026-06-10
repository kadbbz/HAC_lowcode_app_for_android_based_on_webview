package com.huozige.lab.container.offlineform.formitem.picker;

import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemViewType;

public class TimePickerFormItemHandler extends PickerFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.TIME_PICKER.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.TIME_PICKER.getValue();
    }

    @Override
    protected PickerMode getPickerMode() {
        return PickerMode.TIME;
    }
}
