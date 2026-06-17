package com.huozige.lab.container.offlineform.model;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;
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
                buildSteps(input.steps)
        );
    }

    private static List<OfflineFormStep> buildSteps(List<PatternStepInput> steps) {
        List<OfflineFormStep> result = new ArrayList<>();
        if (steps == null) {
            return result;
        }

        for (PatternStepInput stepInput : steps) {
            OfflineFormStep step = new OfflineFormStep();
            step.setStepId(stepInput.stepId);
            step.setTitle(stepInput.title);
            step.setItems(buildNodes(stepInput.items));
            result.add(step);
        }

        return result;
    }

    private static List<OfflineFormNode> buildNodes(List<PatternNodeInput> nodes) {
        List<OfflineFormNode> result = new ArrayList<>();
        if (nodes == null) {
            return result;
        }

        for (PatternNodeInput nodeInput : nodes) {
            OfflineFormNode node = new OfflineFormNode();
            node.setNodeType(nodeInput.nodeType);
            node.setTitle(nodeInput.title);
            node.setContent(nodeInput.content);
            node.setDefaultCollapsed(nodeInput.defaultCollapsed);
            if (OfflineFormNode.TYPE_FIELD.equals(nodeInput.nodeType) && nodeInput.field != null) {
                node.setField(buildFormItem(nodeInput.field));
            } else {
                node.setChildren(buildNodes(nodeInput.children));
            }
            result.add(node);
        }

        return result;
    }

    private static BaseFormItem buildFormItem(FormItemInput formItem) {
        return OfflineFormItemRegistry.fromInput(formItem);
    }
}
