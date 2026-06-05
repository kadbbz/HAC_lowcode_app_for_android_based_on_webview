package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;

import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionOrder;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfflineFormFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String DEFINITION_FILE = "definition.json";
    private static final String ORDER_FILE = "order.json";
    private static final String RECORDS_DIR = "records";

    public static List<OfflineFormDefinitionIndexItem> readDefinitions(Context context) {
        List<OfflineFormDefinitionIndexItem> definitions = readDefinitionsFromFolders(context);
        applyDefinitionOrder(context, definitions);
        return definitions;
    }

    public static void writeDefinitionOrder(Context context, List<OfflineFormDefinitionIndexItem> definitions) {
        // 排序文件只保存项目编号顺序，标题、备注、版本号等内容统一从各项目 definition.json 读取。
        OfflineFormDefinitionOrder order = new OfflineFormDefinitionOrder();
        if (definitions != null) {
            for (OfflineFormDefinitionIndexItem definition : definitions) {
                order.getPatternIds().add(definition.getPatternId());
            }
        }
        JsonFileHelper.writeObjectToFile(getOrderFile(context), order);
    }

    public static void removeDefinitionOrder(Context context, String patternId) {
        List<OfflineFormDefinitionIndexItem> definitions = readDefinitions(context);
        definitions.removeIf(definition -> patternId.equals(definition.getPatternId()));
        writeDefinitionOrder(context, definitions);
    }

    public static void writeDefinition(Context context, String patternId, OfflineFormDefinitionFile definitionFile) {
        JsonFileHelper.writeObjectToFile(getDefinitionFile(context, patternId), OfflineFormJsonSerializer.prepareDefinitionFileForJson(definitionFile));
    }

    public static OfflineFormDefinitionFile readDefinition(Context context, String patternId) {
        com.alibaba.fastjson.JSONObject jsonObject = JsonFileHelper.readFastJsonFromFile(getDefinitionFile(context, patternId));
        return jsonObject == null ? null : OfflineFormJsonSerializer.restoreDefinitionFileFromJson(jsonObject);
    }

    public static JSONObject readRawDefinitionJson(Context context, String patternId) {
        return JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
    }

    public static String getDefinitionFilePath(Context context, String patternId) {
        return getDefinitionFile(context, patternId).getAbsolutePath();
    }

    public static void writeRecord(Context context, OfflineFormRecord record) {
        JsonFileHelper.writeObjectToFile(new File(getRecordsDir(context, record.getPatternId()), record.getRecordId() + ".json"), record);
    }

    public static List<OfflineFormRecord> readRecords(Context context, String patternId) {
        List<OfflineFormRecord> records = new ArrayList<>();
        File recordsDir = getRecordsDir(context, patternId);
        File[] files = recordsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return records;
        }

        for (File file : files) {
            try {
                OfflineFormRecord record = JsonFileHelper.readObjectFromFile(file, OfflineFormRecord.class);
                if (record != null) {
                    records.add(record);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(records, (left, right) -> Long.compare(right.getUpdatedAt(), left.getUpdatedAt()));
        return records;
    }

    public static boolean deletePatternDirectory(Context context, String patternId) {
        if (patternId == null || patternId.isEmpty()) {
            return false;
        }
        return JsonFileHelper.deleteFileOrDirectory(getPatternDir(context, patternId));
    }

    private static List<OfflineFormDefinitionIndexItem> readDefinitionsFromFolders(Context context) {
        List<OfflineFormDefinitionIndexItem> definitions = new ArrayList<>();
        File[] patternDirs = getRootDir(context).listFiles(File::isDirectory);
        if (patternDirs == null) {
            return definitions;
        }

        for (File patternDir : patternDirs) {
            try {
                com.alibaba.fastjson.JSONObject jsonObject = JsonFileHelper.readFastJsonFromFile(new File(patternDir, DEFINITION_FILE));
                if (jsonObject != null) {
                    OfflineFormDefinitionFile definitionFile = OfflineFormJsonSerializer.restoreDefinitionFileFromJson(jsonObject);
                    OfflineFormDefinition definition = definitionFile.getJsonSchema();
                    definitions.add(new OfflineFormDefinitionIndexItem(
                            definition.getTitle(),
                            definition.getDescription(),
                            "",
                            definition.getPatternId(),
                            definition.getSchemaVersion(),
                            definitionFile.getComputed()));
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return definitions;
    }

    private static void applyDefinitionOrder(Context context, List<OfflineFormDefinitionIndexItem> definitions) {
        List<String> orderedPatternIds = readDefinitionOrder(context);
        if (orderedPatternIds.isEmpty() || definitions.isEmpty()) {
            return;
        }

        List<OfflineFormDefinitionIndexItem> orderedDefinitions = new ArrayList<>();
        Set<String> usedPatternIds = new HashSet<>();
        for (String patternId : orderedPatternIds) {
            for (OfflineFormDefinitionIndexItem definition : definitions) {
                if (patternId.equals(definition.getPatternId()) && !usedPatternIds.contains(patternId)) {
                    orderedDefinitions.add(definition);
                    usedPatternIds.add(patternId);
                    break;
                }
            }
        }

        for (OfflineFormDefinitionIndexItem definition : definitions) {
            if (!usedPatternIds.contains(definition.getPatternId())) {
                orderedDefinitions.add(definition);
            }
        }

        definitions.clear();
        definitions.addAll(orderedDefinitions);
    }

    private static List<String> readDefinitionOrder(Context context) {
        OfflineFormDefinitionOrder order = JsonFileHelper.readObjectFromFile(getOrderFile(context), OfflineFormDefinitionOrder.class);
        return order == null || order.getPatternIds() == null ? new ArrayList<>() : order.getPatternIds();
    }

    private static File getOrderFile(Context context) {
        return new File(getRootDir(context), ORDER_FILE);
    }

    private static File getDefinitionFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), DEFINITION_FILE);
    }

    private static File getRecordsDir(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), RECORDS_DIR);
    }

    private static File getPatternDir(Context context, String patternId) {
        return new File(getRootDir(context), sanitizePathSegment(patternId));
    }

    private static File getRootDir(Context context) {
        return new File(context.getExternalFilesDir(null), ROOT_DIR);
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
