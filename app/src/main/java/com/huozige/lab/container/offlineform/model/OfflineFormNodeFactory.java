package com.huozige.lab.container.offlineform.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;

import java.util.ArrayList;
import java.util.List;

public final class OfflineFormNodeFactory {
    private OfflineFormNodeFactory() {
    }

    public static List<OfflineFormNode> buildNodes(List<PatternNodeInput> nodes) {
        List<OfflineFormNode> result = new ArrayList<>();
        if (nodes == null) {
            return result;
        }

        for (PatternNodeInput nodeInput : nodes) {
            if (nodeInput == null) {
                continue;
            }
            OfflineFormNode node = new OfflineFormNode();
            node.setNodeType(nodeInput.nodeType);
            node.setTitle(nodeInput.title);
            node.setContent(nodeInput.content);
            node.setTextMode(nodeInput.textMode == null || nodeInput.textMode.isEmpty()
                    ? OfflineFormNode.TEXT_MODE_TEXT : nodeInput.textMode);
            node.setProgressDisplay(nodeInput.progressDisplay == null
                    ? new OfflineFormProgressDisplay() : nodeInput.progressDisplay);
            node.setDefaultCollapsed(nodeInput.defaultCollapsed);
            node.setEnableProgress(nodeInput.enableProgress || nodeInput.enableProgressStatistics);
            node.setProgressIdentifier(nodeInput.progressIdentifier
                    || nodeInput.isProgressItem
                    || nodeInput.isFillProgressItem);
            if (OfflineFormNode.TYPE_FIELD.equals(nodeInput.nodeType) && nodeInput.field != null) {
                node.setField(OfflineFormItemRegistry.fromInput(nodeInput.field));
            } else {
                node.setChildren(buildNodes(nodeInput.children));
            }
            result.add(node);
        }

        return result;
    }

    public static List<OfflineFormNode> cloneNodes(List<OfflineFormNode> nodes) {
        List<OfflineFormNode> result = new ArrayList<>();
        if (nodes == null) {
            return result;
        }
        for (OfflineFormNode node : nodes) {
            if (node != null) {
                result.add(cloneNode(node));
            }
        }
        return result;
    }

    private static OfflineFormNode cloneNode(OfflineFormNode source) {
        OfflineFormNode node = new OfflineFormNode();
        node.setNodeType(source.getNodeType());
        node.setTitle(source.getTitle());
        node.setContent(source.getContent());
        node.setTextMode(source.getTextMode());
        node.setProgressDisplay(source.getProgressDisplay() == null
                ? new OfflineFormProgressDisplay()
                : new OfflineFormProgressDisplay(
                        source.getProgressDisplay().isTotal(),
                        source.getProgressDisplay().isCompleted(),
                        source.getProgressDisplay().isCompletionRate()));
        node.setDefaultCollapsed(source.isDefaultCollapsed());
        node.setEnableProgress(source.isEnableProgress());
        node.setProgressIdentifier(source.isProgressIdentifier());
        if (source.getField() != null) {
            node.setField(cloneFormItem(source.getField()));
        }
        node.setChildren(cloneNodes(source.getChildren()));
        return node;
    }

    private static BaseFormItem cloneFormItem(BaseFormItem item) {
        JSONObject jsonObject = OfflineFormItemRegistry.toJson(item);
        FormItemInput input = OfflineFormItemJsonHelper.buildBaseInput(jsonObject);
        Object options = jsonObject.get(OfflineFormItemJsonKeys.FIELD_OPTIONS);
        if (options != null) {
            Class<?> optionsClass = OfflineFormItemRegistry.getOptionsClass(input.itemType);
            input.options = optionsClass == null ? options : JSON.parseObject(JSON.toJSONString(options), optionsClass);
        }
        return OfflineFormItemRegistry.fromInput(input);
    }
}
