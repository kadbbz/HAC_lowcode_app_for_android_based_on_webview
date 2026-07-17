package com.huozige.lab.container.offlineform.model;

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
            step.setItems(OfflineFormNodeFactory.buildNodes(stepInput.items));
            result.add(step);
        }

        return result;
    }
}
