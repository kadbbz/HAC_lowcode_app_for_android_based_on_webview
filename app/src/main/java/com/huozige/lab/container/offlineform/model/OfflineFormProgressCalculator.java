package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.formitem.OfflineFormItemValueHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 按表单定义和一条记录的字段值计算填报进度。 */
public final class OfflineFormProgressCalculator {
    private OfflineFormProgressCalculator() {
    }

    public static OfflineFormProgress calculate(OfflineFormDefinition definition, OfflineFormRecord record) {
        return calculate(definition, record == null ? null : record.getValues());
    }

    public static OfflineFormProgress calculate(OfflineFormDefinition definition, Map<String, String> values) {
        if (definition == null || definition.getSteps() == null) {
            return OfflineFormProgress.empty();
        }

        Map<String, String> safeValues = values == null ? new HashMap<>() : values;
        int total = 0;
        int filled = 0;
        for (OfflineFormStep step : definition.getSteps()) {
            if (step == null || step.getItems() == null) {
                continue;
            }
            for (OfflineFormNode node : step.getItems()) {
                if (node == null) {
                    continue;
                }
                if (OfflineFormNode.TYPE_GROUP.equals(node.getNodeType())) {
                    // 最外层 group 是否参与统计由 enableProgress 控制。
                    if (!node.isEnableProgress()) {
                        continue;
                    }
                    total++;
                    if (hasFilledIdentifier(node, safeValues)) {
                        filled++;
                    }
                } else if (node.isProgressIdentifier()
                        && OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                        && node.getField() != null) {
                    // 最外层非 group 项是否参与统计由 progressIdentifier 控制。
                    total++;
                    if (isFilled(node.getField(), safeValues.get(node.getField().getId()))) {
                        filled++;
                    }
                }
            }
        }

        double completionRate = total == 0 ? 0d : filled * 100d / total;
        return new OfflineFormProgress(total, filled, completionRate);
    }

    private static boolean hasFilledIdentifier(OfflineFormNode group, Map<String, String> values) {
        IdentifierState state = new IdentifierState();
        collectIdentifierState(group == null ? null : group.getChildren(), values, state);
        return state.hasIdentifier && state.allFilled;
    }

    private static void collectIdentifierState(
            List<OfflineFormNode> nodes,
            Map<String, String> values,
            IdentifierState state) {
        if (nodes == null) {
            return;
        }
        for (OfflineFormNode child : nodes) {
            if (child == null) {
                continue;
            }
            if (child.isProgressIdentifier()
                    && OfflineFormNode.TYPE_FIELD.equals(child.getNodeType())
                    && child.getField() != null) {
                state.hasIdentifier = true;
                if (!isFilled(child.getField(), values.get(child.getField().getId()))) {
                    state.allFilled = false;
                }
            }
            if (OfflineFormNode.TYPE_GROUP.equals(child.getNodeType())) {
                collectIdentifierState(child.getChildren(), values, state);
            }
        }
    }

    private static class IdentifierState {
        private boolean hasIdentifier;
        private boolean allFilled = true;
    }

    private static boolean isFilled(BaseFormItem source, String rawValue) {
        if (source == null || rawValue == null) {
            return false;
        }
        List<OfflineFormNode> nodes = new ArrayList<>();
        OfflineFormNode node = new OfflineFormNode();
        node.setNodeType(OfflineFormNode.TYPE_FIELD);
        node.setField(source);
        nodes.add(node);
        List<OfflineFormNode> clonedNodes = OfflineFormNodeFactory.cloneNodes(nodes);
        if (clonedNodes.isEmpty() || clonedNodes.get(0).getField() == null) {
            return false;
        }
        BaseFormItem copy = clonedNodes.get(0).getField();
        OfflineFormItemValueHelper.applyValue(copy, rawValue);
        return !copy.isEmpty();
    }
}
