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
     * @param keepErrorState  true = 保留校验产生的错误信息（显式校验入口用）；false = 校验后清空错误（派生状态用）
     */
    public static Result evaluate(OfflineFormDefinition definition, boolean keepErrorState) {
        List<BaseFormItem> invalidFields = new ArrayList<>();

        if (definition == null || definition.getSteps() == null) {
            return new Result(true, invalidFields, -1);
        }

        int firstInvalidStepIndex = -1;
        for (int stepIndex = 0; stepIndex < definition.getSteps().size(); stepIndex++) {
            OfflineFormStep step = definition.getSteps().get(stepIndex);
            if (!evaluateUnits(collectValidationUnits(step), invalidFields) && firstInvalidStepIndex < 0) {
                firstInvalidStepIndex = stepIndex;
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
     * 校验单个 Step（底部「保存」在平铺布局下使用）：只校验当前 Step 内"碰过的"填报单元。
     *
     * @param stepIndex      当前 Step 下标，失败时写入结果的 firstInvalidStepIndex
     * @param keepErrorState true = 保留错误信息用于界面提示；false = 校验后清空错误
     */
    public static Result evaluateStep(OfflineFormStep step, int stepIndex, boolean keepErrorState) {
        List<BaseFormItem> invalidFields = new ArrayList<>();
        if (step == null) {
            return new Result(true, invalidFields, -1);
        }
        boolean valid = evaluateUnits(collectValidationUnits(step), invalidFields);
        if (!keepErrorState) {
            clearErrors(OfflineFormDefinitionFlattener.flattenFields(step.getItems()));
        }
        return new Result(valid, invalidFields, valid ? -1 : stepIndex);
    }

    /**
     * 校验单个分页页（底部「保存」在分页布局下使用）：只校验当前页（当前表单组）内"碰过的"填报单元。
     *
     * <p>分组页按"整组一个填报单元"判定，与整表校验口径一致；散项页按每个顶级字段各自成单元判定。</p>
     *
     * @param page           当前页；为空时视为通过（当前筛选结果没有可保存内容）
     * @param stepIndex      当前 Step 下标，失败时写入结果的 firstInvalidStepIndex
     * @param keepErrorState true = 保留错误信息用于界面提示；false = 校验后清空错误
     */
    public static Result evaluatePage(OfflineFormPage page, int stepIndex, boolean keepErrorState) {
        List<BaseFormItem> invalidFields = new ArrayList<>();
        if (page == null) {
            return new Result(true, invalidFields, -1);
        }

        List<List<OfflineFormDisplayItem>> units;
        OfflineFormNode rootNode = page.getRootNode();
        if (rootNode != null && OfflineFormNode.TYPE_GROUP.equals(rootNode.getNodeType())) {
            // 分组页 = 一个完整填报单元：组内任一字段碰过，就整组参与校验。
            units = Collections.singletonList(page.getItems());
        } else {
            // 散项页：每个顶级字段各自为一个填报单元。
            units = collectDisplayItemUnits(page.getItems());
        }

        boolean valid = evaluateUnits(units, invalidFields);
        if (!keepErrorState) {
            clearErrors(flattenPageFields(page));
        }
        return new Result(valid, invalidFields, valid ? -1 : stepIndex);
    }

    /**
     * 按"填报单元"聚合：最外层 group + 其后所有子节点为一个单元；最外层字段单独为一个单元。
     */
    public static List<List<OfflineFormDisplayItem>> collectValidationUnits(OfflineFormStep step) {
        return collectDisplayItemUnits(OfflineFormDefinitionFlattener.flattenStep(step));
    }

    /**
     * 校验一组填报单元：空单元豁免（没碰过不参与校验），其余单元内每个字段都执行校验。
     *
     * @return 全部单元是否通过
     */
    private static boolean evaluateUnits(List<List<OfflineFormDisplayItem>> units, List<BaseFormItem> invalidFields) {
        boolean allValid = true;
        for (List<OfflineFormDisplayItem> unit : units) {
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
            if (!unitValid) {
                allValid = false;
            }
        }
        return allValid;
    }

    /** 把已拍平的展示项按"顶层项"聚合成填报单元（顶层 group 连同其后所有子节点、顶层字段各自成组）。 */
    private static List<List<OfflineFormDisplayItem>> collectDisplayItemUnits(List<OfflineFormDisplayItem> displayItems) {
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

    private static List<BaseFormItem> flattenPageFields(OfflineFormPage page) {
        List<BaseFormItem> fields = new ArrayList<>();
        for (OfflineFormDisplayItem displayItem : page.getItems()) {
            if (displayItem.isField() && displayItem.getFormItem() != null) {
                fields.add(displayItem.getFormItem());
            }
        }
        return fields;
    }

    private static boolean hasFilledField(List<OfflineFormDisplayItem> displayItems) {
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField() && displayItem.getFormItem().countsAsTouched()) {
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
        clearErrors(OfflineFormDefinitionFlattener.flattenFields(definition));
    }

    private static void clearErrors(List<BaseFormItem> items) {
        for (BaseFormItem item : items) {
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
