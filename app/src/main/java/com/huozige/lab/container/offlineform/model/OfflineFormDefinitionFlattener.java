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

    /**
     * 按顶级节点切页。
     *
     * <p>顶级分组各自成一页，且分组自身不再渲染——把它的直接子节点重定基到 depth 0 作为本页内容，
     * 这样嵌套的子分组仍会正常渲染为分组卡片。</p>
     * <p>连续的非分组顶级节点（text / 裸 field）合并成一页；被分组隔断时另起一页。</p>
     */
    public static List<OfflineFormPage> splitIntoPages(OfflineFormStep step) {
        List<OfflineFormPage> pages = new ArrayList<>();
        if (step == null || step.getItems() == null) {
            return pages;
        }

        // 当前累积的散项页（连续的非分组顶级节点）。
        OfflineFormNode scatterRoot = null;
        List<OfflineFormDisplayItem> scatterItems = new ArrayList<>();
        int[] scatterDisplayId = new int[]{0};

        for (OfflineFormNode node : step.getItems()) {
            if (node == null) {
                continue;
            }
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                if (node.getField() == null) {
                    continue;
                }
                if (scatterRoot == null) {
                    scatterRoot = node;
                }
                scatterItems.add(OfflineFormDisplayItem.field(node, 0, scatterDisplayId[0]++));
                continue;
            }
            if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                if (scatterRoot == null) {
                    scatterRoot = node;
                }
                scatterItems.add(OfflineFormDisplayItem.text(node, 0, scatterDisplayId[0]++));
                continue;
            }

            // 分组（以及未知类型，按 flattenNodes 的口径一律视为分组）：先结算散项页，再为本分组开新页。
            if (!scatterItems.isEmpty()) {
                pages.add(new OfflineFormPage(scatterRoot, scatterItems));
                scatterRoot = null;
                scatterItems = new ArrayList<>();
                scatterDisplayId[0] = 0;
            }
            List<OfflineFormDisplayItem> groupItems = new ArrayList<>();
            flattenNodes(node.getChildren(), 0, groupItems, new int[]{0});
            pages.add(new OfflineFormPage(node, groupItems));
        }

        if (!scatterItems.isEmpty()) {
            pages.add(new OfflineFormPage(scatterRoot, scatterItems));
        }
        return pages;
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
