package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 表单校验的纯逻辑层。
 *
 * <p>只负责"按填报单元(group)判定是否通过"，不触碰任何 UI（不切换筛选、不渲染、不滚动）。
 * 校验规则与 {@code CustomFormActivity} 既有实现保持一致：只有"碰过的"单元才校验，空单元豁免。</p>
 */
public final class OfflineFormValidator {

    private OfflineFormValidator() {
    }

    /**
     * 校验整张表单。
     *
     * @param definition      表单定义（其中字段对象持有当前值）
     * @param keepErrorState  true = 保留校验产生的错误信息（提交按钮用）；false = 校验后清空错误（派生状态用）
     */
    public static Result evaluate(OfflineFormDefinition definition, boolean keepErrorState) {
        List<BaseFormItem> invalidFields = new ArrayList<>();
        int firstInvalidStepIndex = -1;

        if (definition == null || definition.getSteps() == null) {
            return new Result(true, invalidFields, firstInvalidStepIndex);
        }

        for (int stepIndex = 0; stepIndex < definition.getSteps().size(); stepIndex++) {
            OfflineFormStep step = definition.getSteps().get(stepIndex);
            for (List<OfflineFormDisplayItem> unit : collectValidationUnits(step)) {
                if (!hasFilledField(unit)) {
                    // 空单元豁免：没碰过的填报单元不参与校验。
                    clearValidationErrors(unit);
                    continue;
                }
                boolean unitValid = true;
                for (OfflineFormDisplayItem displayItem : unit) {
                    if (!displayItem.isField()) {
                        continue;
                    }
                    BaseFormItem formItem = displayItem.getFormItem();
                    if (!formItem.validate()) {
                        unitValid = false;
                        invalidFields.add(formItem);
                    } else {
                        formItem.clearError();
                    }
                }
                if (!unitValid && firstInvalidStepIndex < 0) {
                    firstInvalidStepIndex = stepIndex;
                }
            }
        }

        if (!keepErrorState) {
            clearAllErrors(definition);
        }

        return new Result(invalidFields.isEmpty(), invalidFields, firstInvalidStepIndex);
    }

    public static Result evaluate(OfflineFormDefinition definition) {
        return evaluate(definition, false);
    }

    /**
     * 按"填报单元"聚合：最外层 group + 其后所有子节点为一个单元；最外层字段单独为一个单元。
     */
    public static List<List<OfflineFormDisplayItem>> collectValidationUnits(OfflineFormStep step) {
        List<OfflineFormDisplayItem> displayItems = OfflineFormDefinitionFlattener.flattenStep(step);
        List<List<OfflineFormDisplayItem>> validationUnits = new ArrayList<>();
        for (int i = 0; i < displayItems.size(); i++) {
            OfflineFormDisplayItem displayItem = displayItems.get(i);
            if (displayItem.getDepth() != 0) {
                continue;
            }
            if (displayItem.isGroup()) {
                List<OfflineFormDisplayItem> groupItems = new ArrayList<>();
                groupItems.add(displayItem);
                int nextIndex = i + 1;
                while (nextIndex < displayItems.size() && displayItems.get(nextIndex).getDepth() > 0) {
                    groupItems.add(displayItems.get(nextIndex));
                    nextIndex++;
                }
                validationUnits.add(groupItems);
                i = nextIndex - 1;
            } else if (displayItem.isField()) {
                List<OfflineFormDisplayItem> fieldItem = new ArrayList<>();
                fieldItem.add(displayItem);
                validationUnits.add(fieldItem);
            }
        }
        return validationUnits;
    }

    private static boolean hasFilledField(List<OfflineFormDisplayItem> displayItems) {
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField() && !displayItem.getFormItem().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void clearValidationErrors(List<OfflineFormDisplayItem> displayItems) {
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField()) {
                displayItem.getFormItem().clearError();
            }
        }
    }

    private static void clearAllErrors(OfflineFormDefinition definition) {
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(definition)) {
            if (item != null) {
                item.clearError();
            }
        }
    }

    /** 校验结果：是否通过、失败字段列表、首个失败步骤下标。 */
    public static final class Result {
        private final boolean valid;
        private final List<BaseFormItem> invalidFields;
        private final int firstInvalidStepIndex;

        Result(boolean valid, List<BaseFormItem> invalidFields, int firstInvalidStepIndex) {
            this.valid = valid;
            this.invalidFields = invalidFields == null ? Collections.emptyList() : invalidFields;
            this.firstInvalidStepIndex = firstInvalidStepIndex;
        }

        public boolean isValid() {
            return valid;
        }

        public List<BaseFormItem> getInvalidFields() {
            return invalidFields;
        }

        public int getInvalidCount() {
            return invalidFields.size();
        }

        public int getFirstInvalidStepIndex() {
            return firstInvalidStepIndex;
        }
    }
}
