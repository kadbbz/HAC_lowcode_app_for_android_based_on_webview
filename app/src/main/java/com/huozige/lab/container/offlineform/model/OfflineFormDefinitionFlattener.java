package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import java.util.ArrayList;
import java.util.List;

public final class OfflineFormDefinitionFlattener {
    private OfflineFormDefinitionFlattener() {
    }

    public static List<OfflineFormDisplayItem> flattenStep(OfflineFormStep step) {
        List<OfflineFormDisplayItem> result = new ArrayList<>();
        if (step == null) {
            return result;
        }
        int[] nextDisplayId = new int[]{0};
        flattenNodes(step.getItems(), 0, result, nextDisplayId);
        return result;
    }

    public static List<BaseFormItem> flattenFields(OfflineFormDefinition definition) {
        List<BaseFormItem> result = new ArrayList<>();
        if (definition == null || definition.getSteps() == null) {
            return result;
        }
        for (OfflineFormStep step : definition.getSteps()) {
            collectFields(step == null ? null : step.getItems(), result);
        }
        return result;
    }

    public static List<BaseFormItem> flattenFields(List<OfflineFormNode> nodes) {
        List<BaseFormItem> result = new ArrayList<>();
        collectFields(nodes, result);
        return result;
    }

    public static List<OfflineFormDisplayItem> flattenFieldDisplayItems(OfflineFormDefinition definition) {
        List<OfflineFormDisplayItem> result = new ArrayList<>();
        if (definition == null || definition.getSteps() == null) {
            return result;
        }
        for (OfflineFormStep step : definition.getSteps()) {
            for (OfflineFormDisplayItem displayItem : flattenStep(step)) {
                if (displayItem.isField()) {
                    result.add(displayItem);
                }
            }
        }
        return result;
    }

    private static void flattenNodes(List<OfflineFormNode> nodes, int depth, List<OfflineFormDisplayItem> result, int[] nextDisplayId) {
        if (nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                if (node.getField() != null) {
                    result.add(OfflineFormDisplayItem.field(node, depth, nextDisplayId[0]++));
                }
                continue;
            }
            if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                result.add(OfflineFormDisplayItem.text(node, depth, nextDisplayId[0]++));
                continue;
            }
            result.add(OfflineFormDisplayItem.group(node, depth, nextDisplayId[0]++));
            flattenNodes(node.getChildren(), depth + 1, result, nextDisplayId);
        }
    }

    private static void collectFields(List<OfflineFormNode> nodes, List<BaseFormItem> result) {
        if (nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                if (node.getField() != null) {
                    result.add(node.getField());
                }
                continue;
            }
            collectFields(node.getChildren(), result);
        }
    }
}
