package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemInput;

import java.util.ArrayList;
import java.util.List;

class OfflineFormJsonSerializer {
    static JSONObject prepareDefinitionFileForJson(OfflineFormDefinitionFile definitionFile) {
        OfflineFormDefinition definition = definitionFile.getJsonSchema();

        DefinitionFileJson definitionJson = new DefinitionFileJson();
        definitionJson.jsonSchema.patternId = definition.getPatternId();
        definitionJson.jsonSchema.schemaVersion = definition.getSchemaVersion();
        definitionJson.jsonSchema.title = definition.getTitle();
        definitionJson.jsonSchema.description = definition.getDescription();
        definitionJson.jsonSchema.steps = buildStepJsonList(definition.getSteps());
        definitionJson.computed = definitionFile.getComputed();
        return (JSONObject) JSON.toJSON(definitionJson);
    }

    static OfflineFormDefinitionFile restoreDefinitionFileFromJson(JSONObject definitionFileJson) {
        DefinitionFileJson definitionJson = definitionFileJson.toJavaObject(DefinitionFileJson.class);

        OfflineFormDefinitionFile definitionFile = new OfflineFormDefinitionFile();
        definitionFile.setComputed(definitionJson.computed == null ? new OfflineComputedInfo() : definitionJson.computed);

        OfflineFormDefinition definition = new OfflineFormDefinition();
        if (definitionJson.jsonSchema != null) {
            definition.setPatternId(definitionJson.jsonSchema.patternId);
            definition.setSchemaVersion(definitionJson.jsonSchema.schemaVersion);
            definition.setTitle(definitionJson.jsonSchema.title);
            definition.setDescription(definitionJson.jsonSchema.description);
            definition.setSteps(parseStepJsonList(definitionJson.jsonSchema.steps));
        }
        definitionFile.setJsonSchema(definition);
        return definitionFile;
    }

    private static List<StepJson> buildStepJsonList(List<OfflineFormStep> steps) {
        List<StepJson> stepsJson = new ArrayList<>();
        if (steps == null) {
            return stepsJson;
        }

        for (OfflineFormStep step : steps) {
            StepJson stepJson = new StepJson();
            stepJson.stepId = step.getStepId();
            stepJson.title = step.getTitle();
            stepJson.items = buildNodeJsonList(step.getItems());
            stepsJson.add(stepJson);
        }
        return stepsJson;
    }

    private static List<NodeJson> buildNodeJsonList(List<OfflineFormNode> nodes) {
        List<NodeJson> nodesJson = new ArrayList<>();
        if (nodes == null) {
            return nodesJson;
        }

        for (OfflineFormNode node : nodes) {
            NodeJson nodeJson = new NodeJson();
            nodeJson.nodeType = node.getNodeType();
            nodeJson.title = node.getTitle();
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                nodeJson.field = OfflineFormItemRegistry.toJson(node.getField());
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                nodeJson.content = node.getContent();
            } else {
                nodeJson.defaultCollapsed = node.isDefaultCollapsed();
                nodeJson.children = buildNodeJsonList(node.getChildren());
            }
            nodesJson.add(nodeJson);
        }
        return nodesJson;
    }

    private static List<OfflineFormStep> parseStepJsonList(List<StepJson> jsonArray) {
        List<OfflineFormStep> steps = new ArrayList<>();
        if (jsonArray == null) {
            return steps;
        }

        for (StepJson stepJson : jsonArray) {
            OfflineFormStep step = new OfflineFormStep();
            step.setStepId(stepJson.stepId);
            step.setTitle(stepJson.title);
            step.setItems(parseNodeJsonList(stepJson.items));
            steps.add(step);
        }
        return steps;
    }

    private static List<OfflineFormNode> parseNodeJsonList(List<NodeJson> jsonArray) {
        List<OfflineFormNode> nodes = new ArrayList<>();
        if (jsonArray == null) {
            return nodes;
        }

        for (NodeJson nodeJson : jsonArray) {
            OfflineFormNode node = new OfflineFormNode();
            node.setNodeType(nodeJson.nodeType);
            node.setTitle(nodeJson.title);
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                node.setField(OfflineFormItemRegistry.fromInput(buildInputFromJson(nodeJson.field)));
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                node.setContent(nodeJson.content);
            } else {
                node.setDefaultCollapsed(nodeJson.defaultCollapsed);
                node.setChildren(parseNodeJsonList(nodeJson.children));
            }
            nodes.add(node);
        }
        return nodes;
    }

    private static FormItemInput buildInputFromJson(JSONObject jsonObject) {
        FormItemInput input = OfflineFormItemJsonHelper.buildBaseInput(jsonObject);
        JSONObject options = jsonObject.getJSONObject(OfflineFormItemJsonKeys.FIELD_OPTIONS);
        if (options != null) {
            Class<?> optionsClass = OfflineFormItemRegistry.getOptionsClass(input.itemType);
            if (optionsClass != null) {
                input.options = options.toJavaObject(optionsClass);
            }
        }
        return input;
    }

    private static class DefinitionFileJson {
        public DefinitionJson jsonSchema = new DefinitionJson();
        public OfflineComputedInfo computed = new OfflineComputedInfo();
    }

    private static class DefinitionJson {
        public String patternId = "";
        public String schemaVersion = "";
        public String title = "";
        public String description = "";
        public List<StepJson> steps = new ArrayList<>();
    }

    private static class StepJson {
        public String stepId = "";
        public String title = "";
        public List<NodeJson> items = new ArrayList<>();
    }

    private static class NodeJson {
        public String nodeType = OfflineFormNode.TYPE_GROUP;
        public String title = "";
        public String content = "";
        public boolean defaultCollapsed;
        public JSONObject field;
        public List<NodeJson> children = new ArrayList<>();
    }
}
