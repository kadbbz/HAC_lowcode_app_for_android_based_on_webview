package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectOptionsInput;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;

import java.util.ArrayList;
import java.util.List;

/*“把插件传进来的 DTO 转成 APP 内部表单定义模型”的工厂类。*/

public class OfflineFormDefinitionFactory {
    private static final String TYPE_TEXT = "textItem";
    private static final String TYPE_PASSWORD = "passwordItem";
    private static final String TYPE_SELECT = "selectItem";

    public static OfflineFormDefinition fromPatternInput(PatternInput input) {
        return new OfflineFormDefinition(
                input.patternId,
                input.schemaVersion,
                input.title,
                input.description,
                buildFormItems(input.formItems)
        );
    }

    private static List<BaseFormItem> buildFormItems(List<FormItemInput> formItems) {
        List<BaseFormItem> result = new ArrayList<>();
        if (formItems == null) {
            return result;
        }

        for (FormItemInput formItem : formItems) {
            result.add(buildFormItem(formItem));
        }

        return result;
    }

    private static BaseFormItem buildFormItem(FormItemInput formItem) {
        BaseFormItem result;
        switch (formItem.itemType) {
            case TYPE_TEXT:
                result = new TextFormItem(formItem.itemId, formItem.title, formItem.hint, formItem.required);
                break;
            case TYPE_PASSWORD:
                TextFormItem passwordItem = new TextFormItem(formItem.itemId, formItem.title, formItem.hint, formItem.required);
                passwordItem.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                result = passwordItem;
                break;
            case TYPE_SELECT:
                SelectFormItem selectItem = new SelectFormItem(formItem.itemId, formItem.title, formItem.hint, formItem.required);
                if (formItem.selectOptionsList != null) {
                    for (SelectOptionsInput selectOption : formItem.selectOptionsList) {
                        selectItem.addOption(selectOption.value, selectOption.label);
                    }
                }
                result = selectItem;
                break;
            default:
                result = new TextFormItem(formItem.itemId, formItem.title, formItem.hint, formItem.required);
                break;
        }

        applyDefaultValueAndRules(result, formItem);
        return result;
    }

    private static void applyDefaultValueAndRules(BaseFormItem result, FormItemInput formItem) {
        if (result instanceof TextFormItem) {
            TextFormItem textItem = (TextFormItem) result;
            if (formItem.value != null) {
                textItem.setValue(formItem.value);
            }
            if (formItem.checkOptions == null) {
                return;
            }
            if (formItem.checkOptions.minLength > 0) {
                textItem.setMinLength(formItem.checkOptions.minLength);
            }
            if (formItem.checkOptions.maxLength > 0) {
                textItem.setMaxLength(formItem.checkOptions.maxLength);
            }
            if (formItem.checkOptions.regexPattern != null) {
                textItem.setRegexPattern(formItem.checkOptions.regexPattern);
            }
        } else if (result instanceof SelectFormItem && formItem.value != null) {
            ((SelectFormItem) result).setSelectedValue(formItem.value);
        }
    }
}
