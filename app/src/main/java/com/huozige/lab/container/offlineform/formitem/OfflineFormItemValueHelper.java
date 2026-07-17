package com.huozige.lab.container.offlineform.formitem;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;

import java.util.List;
import java.util.Map;

public final class OfflineFormItemValueHelper {
    private OfflineFormItemValueHelper() {
    }

    public static void applyValue(BaseFormItem formItem, String value) {
        if (formItem == null || value == null) {
            return;
        }
        if (formItem instanceof TextFormItem) {
            ((TextFormItem) formItem).setValue(value);
        } else if (formItem instanceof SelectFormItem) {
            ((SelectFormItem) formItem).setSelectedValue(value);
        } else if (formItem instanceof PickerFormItem) {
            ((PickerFormItem) formItem).setValue(value);
        } else if (formItem instanceof ImageFormItem) {
            ((ImageFormItem) formItem).setValue(value);
        } else if (formItem instanceof FileFormItem) {
            ((FileFormItem) formItem).setValue(value);
        } else if (formItem instanceof ListFormItem) {
            ((ListFormItem) formItem).setValue(value);
        }
    }

    public static void applyValues(List<BaseFormItem> formItems, Map<String, String> values) {
        if (formItems == null || values == null) {
            return;
        }
        for (BaseFormItem formItem : formItems) {
            if (formItem != null) {
                applyValue(formItem, values.get(formItem.getId()));
            }
        }
    }

    public static void applyPatternId(BaseFormItem formItem, String patternId) {
        if (formItem instanceof ImageFormItem) {
            ((ImageFormItem) formItem).setPatternId(patternId);
        } else if (formItem instanceof FileFormItem) {
            ((FileFormItem) formItem).setPatternId(patternId);
        } else if (formItem instanceof ListFormItem) {
            ((ListFormItem) formItem).setPatternId(patternId);
        }
    }

    public static void applyPatternId(List<BaseFormItem> formItems, String patternId) {
        if (formItems == null) {
            return;
        }
        for (BaseFormItem formItem : formItems) {
            applyPatternId(formItem, patternId);
        }
    }
}
