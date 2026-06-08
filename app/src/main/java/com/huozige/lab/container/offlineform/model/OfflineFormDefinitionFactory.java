package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;

import java.util.ArrayList;
import java.util.List;

/*“把插件传进来的 DTO 转成 APP 内部表单定义模型”的工厂类。*/

public class OfflineFormDefinitionFactory {
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
        return OfflineFormItemRegistry.fromInput(formItem);
    }
}
