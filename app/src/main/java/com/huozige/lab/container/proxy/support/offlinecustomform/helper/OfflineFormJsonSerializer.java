package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonKeys;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemJsonHelper;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;

import java.util.ArrayList;
import java.util.List;

class OfflineFormJsonSerializer {
    private static final String FIELD_JSON_SCHEMA = "jsonSchema";
    private static final String FIELD_COMPUTED = "computed";
    private static final String FIELD_THEME = "theme";
    private static final String FIELD_DISPLAY_COLUMNS = "displayColumns";
    private static final String FIELD_PATTERN_ID = "patternId";
    private static final String FIELD_SCHEMA_VERSION = "schemaVersion";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STEPS = "steps";
    private static final String FIELD_STEP_ID = "stepId";
    private static final String FIELD_ITEMS = "items";
    private static final String FIELD_NODE_TYPE = "nodeType";
    private static final String FIELD_CONTENT = "content";
    private static final String FIELD_DEFAULT_COLLAPSED = "defaultCollapsed";
    private static final String FIELD_CHILDREN = "children";
    private static final String FIELD_FIELD = "field";

    static JSONObject prepareDefinitionFileForJson(OfflineFormDefinitionFile definitionFile) {
        OfflineFormDefinition definition = definitionFile.getJsonSchema();

        JSONObject jsonSchema = new JSONObject();
        jsonSchema.put(FIELD_PATTERN_ID, definition.getPatternId());
        jsonSchema.put(FIELD_SCHEMA_VERSION, definition.getSchemaVersion());
        jsonSchema.put(FIELD_TITLE, definition.getTitle());
        jsonSchema.put(FIELD_DESCRIPTION, definition.getDescription());

        jsonSchema.put(FIELD_STEPS, buildStepsJson(definition.getSteps()));

        OfflineComputedInfo computed = definitionFile.getComputed();
        JSONObject computedJson = new JSONObject();
        computedJson.put(FIELD_THEME, computed.getTheme());
        computedJson.put(FIELD_DISPLAY_COLUMNS, computed.getDisplayColumns());

        JSONObject definitionJson = new JSONObject();
        definitionJson.put(FIELD_JSON_SCHEMA, jsonSchema);
        definitionJson.put(FIELD_COMPUTED, computedJson);
        return definitionJson;
    }

    static OfflineFormDefinitionFile restoreDefinitionFileFromJson(JSONObject definitionFileJson) {
        JSONObject jsonSchema = definitionFileJson.getJSONObject(FIELD_JSON_SCHEMA);
        JSONArray stepsJson = jsonSchema.getJSONArray(FIELD_STEPS);
        jsonSchema.remove(FIELD_STEPS);

        OfflineFormDefinitionFile definitionFile = definitionFileJson.toJavaObject(OfflineFormDefinitionFile.class);
        OfflineFormDefinition definition = jsonSchema.toJavaObject(OfflineFormDefinition.class);
        definition.setSteps(parseJsonToSteps(stepsJson));
        definitionFile.setJsonSchema(definition);
        return definitionFile;
    }

    private static JSONArray buildStepsJson(List<OfflineFormStep> steps) {
        JSONArray stepsJson = new JSONArray();
        if (steps == null) {
            return stepsJson;
        }

        for (OfflineFormStep step : steps) {
            JSONObject stepJson = new JSONObject();
            stepJson.put(FIELD_STEP_ID, step.getStepId());
            stepJson.put(FIELD_TITLE, step.getTitle());
            stepJson.put(FIELD_ITEMS, buildNodesJson(step.getItems()));
            stepsJson.add(stepJson);
        }
        return stepsJson;
    }

    private static JSONArray buildNodesJson(List<OfflineFormNode> nodes) {
        JSONArray nodesJson = new JSONArray();
        if (nodes == null) {
            return nodesJson;
        }

        for (OfflineFormNode node : nodes) {
            JSONObject nodeJson = new JSONObject();
            nodeJson.put(FIELD_NODE_TYPE, node.getNodeType());
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                nodeJson.put(FIELD_FIELD, OfflineFormItemRegistry.toJson(node.getField()));
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                nodeJson.put(FIELD_TITLE, node.getTitle());
                nodeJson.put(FIELD_CONTENT, node.getContent());
            } else {
                nodeJson.put(FIELD_TITLE, node.getTitle());
                nodeJson.put(FIELD_DEFAULT_COLLAPSED, node.isDefaultCollapsed());
                nodeJson.put(FIELD_CHILDREN, buildNodesJson(node.getChildren()));
            }
            nodesJson.add(nodeJson);
        }
        return nodesJson;
    }

    private static List<OfflineFormStep> parseJsonToSteps(JSONArray jsonArray) {
        List<OfflineFormStep> steps = new ArrayList<>();
        if (jsonArray == null) {
            return steps;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject stepJson = jsonArray.getJSONObject(i);
            OfflineFormStep step = new OfflineFormStep();
            step.setStepId(stepJson.getString(FIELD_STEP_ID));
            step.setTitle(stepJson.getString(FIELD_TITLE));
            step.setItems(parseJsonToNodes(stepJson.getJSONArray(FIELD_ITEMS)));
            steps.add(step);
        }
        return steps;
    }

    private static List<OfflineFormNode> parseJsonToNodes(JSONArray jsonArray) {
        List<OfflineFormNode> nodes = new ArrayList<>();
        if (jsonArray == null) {
            return nodes;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject nodeJson = jsonArray.getJSONObject(i);
            OfflineFormNode node = new OfflineFormNode();
            node.setNodeType(nodeJson.getString(FIELD_NODE_TYPE));
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())) {
                node.setField(OfflineFormItemRegistry.fromInput(buildInputFromJson(nodeJson.getJSONObject(FIELD_FIELD))));
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                node.setTitle(nodeJson.getString(FIELD_TITLE));
                node.setContent(nodeJson.getString(FIELD_CONTENT));
            } else {
                node.setTitle(nodeJson.getString(FIELD_TITLE));
                node.setDefaultCollapsed(nodeJson.getBooleanValue(FIELD_DEFAULT_COLLAPSED));
                node.setChildren(parseJsonToNodes(nodeJson.getJSONArray(FIELD_CHILDREN)));
            }
            nodes.add(node);
        }
        return nodes;
    }

    private static FormItemInput buildInputFromJson(JSONObject jsonObject) {
        FormItemInput input = OfflineFormItemJsonHelper.buildBaseInput(jsonObject);
        JSONObject options = jsonObject.getJSONObject(OfflineFormItemJsonKeys.FIELD_OPTIONS);
        if (options != null) {
            OfflineFormItemRegistry.getHandler(input.itemType).readInputOptions(options, input);
        }
        return input;
    }
}
