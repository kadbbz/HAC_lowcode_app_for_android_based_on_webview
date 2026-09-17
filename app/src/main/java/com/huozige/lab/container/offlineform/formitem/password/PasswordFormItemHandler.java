package com.huozige.lab.container.offlineform.formitem.password;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemViewType;
import com.huozige.lab.container.offlineform.formitem.text.TextFormItemHandler;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;

public class PasswordFormItemHandler extends TextFormItemHandler {
    private static final String PASSWORD_MASK_CHAR = "\u2022";

    @Override
    public String getType() {
        return OfflineFormItemType.PASSWORD.getValue();
    }

    @Override
    public int getViewType() {
        return OfflineFormItemViewType.PASSWORD.getValue();
    }

    @Override
    public BaseFormItem fromInput(FormItemInput input) {
        TextFormItem item = (TextFormItem) super.fromInput(input);
        applyPasswordInputType(item);
        return item;
    }

    private void applyPasswordInputType(TextFormItem item) {
        item.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact) {
        if (rawValue == null || rawValue.isEmpty()) {
            return super.createReadOnlyView(context, item, "", compact);
        }
        return super.createReadOnlyView(context, item, PASSWORD_MASK_CHAR.repeat(rawValue.length()), compact);
    }
}
