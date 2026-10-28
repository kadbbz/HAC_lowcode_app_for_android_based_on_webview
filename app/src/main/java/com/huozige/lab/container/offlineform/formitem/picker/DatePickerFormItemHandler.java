package com.huozige.lab.container.offlineform.formitem.picker;

import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemViewType;

public class DatePickerFormItemHandler extends PickerFormItemHandler {
    @Override
    public String getType() {
        return OfflineFormItemType.DATE_PICKER.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.DATE_PICKER.getValue();
    }

    @Override
    protected PickerMode getPickerMode() {
        return PickerMode.DATE;
    }
}
