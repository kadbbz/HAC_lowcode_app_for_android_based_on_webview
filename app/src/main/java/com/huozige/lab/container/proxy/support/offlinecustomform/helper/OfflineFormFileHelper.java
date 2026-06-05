package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;

import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;

import org.json.JSONArray;
import org.json.JSONException;
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
    private static final String FIELD_ORDER_PATTERN_IDS = "patternIds";

    public static List<OfflineFormDefinitionIndexItem> readDefinitions(Context context) {
        List<OfflineFormDefinitionIndexItem> definitions = readDefinitionsFromFolders(context);
        applyDefinitionOrder(context, definitions);
        return definitions;
    }

    public static void writeDefinitionOrder(Context context, List<OfflineFormDefinitionIndexItem> definitions) {
        // 排序文件只保存项目编号顺序，标题、备注、版本号等内容统一从各项目 definition.json 读取。
        JSONArray patternIds = new JSONArray();
        try {
            if (definitions != null) {
                for (OfflineFormDefinitionIndexItem definition : definitions) {
                    patternIds.put(definition.getPatternId());
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(FIELD_ORDER_PATTERN_IDS, patternIds);
            JsonFileHelper.writeJsonToFile(getOrderFile(context), jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeDefinitionOrder(Context context, String patternId) {
        List<OfflineFormDefinitionIndexItem> definitions = readDefinitions(context);
        definitions.removeIf(definition -> patternId.equals(definition.getPatternId()));
        writeDefinitionOrder(context, definitions);
    }

    public static void writeDefinition(Context context, String patternId, OfflineFormDefinitionFile definitionFile) {
        JsonFileHelper.writeJsonToFile(getDefinitionFile(context, patternId), OfflineFormJsonSerializer.parseDefinitionFileToJson(definitionFile));
    }

    public static OfflineFormDefinitionFile readDefinition(Context context, String patternId) {
        JSONObject jsonObject = JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
        return jsonObject == null ? null : OfflineFormJsonSerializer.parseJsonToDefinitionFile(jsonObject);
    }

    public static JSONObject readRawDefinitionJson(Context context, String patternId) {
        return JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
    }

    public static String getDefinitionFilePath(Context context, String patternId) {
        return getDefinitionFile(context, patternId).getAbsolutePath();
    }

    public static void writeRecord(Context context, OfflineFormRecord record) {
        JsonFileHelper.writeJsonToFile(new File(getRecordsDir(context, record.getPatternId()), record.getRecordId() + ".json"), OfflineFormJsonSerializer.parseRecordToJson(record));
    }

    public static List<OfflineFormRecord> readRecords(Context context, String patternId) {
        List<OfflineFormRecord> records = new ArrayList<>();
        File recordsDir = getRecordsDir(context, patternId);
        File[] files = recordsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return records;
        }

        for (File file : files) {
            JSONObject jsonObject = JsonFileHelper.readJsonFromFile(file);
            if (jsonObject != null) {
                try {
                    records.add(OfflineFormJsonSerializer.parseJsonToRecord(jsonObject));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
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
            JSONObject jsonObject = JsonFileHelper.readJsonFromFile(new File(patternDir, DEFINITION_FILE));
            if (jsonObject != null) {
                try {
                    OfflineFormDefinitionFile definitionFile = OfflineFormJsonSerializer.parseJsonToDefinitionFile(jsonObject);
                    OfflineFormDefinition definition = definitionFile.getJsonSchema();
                    definitions.add(new OfflineFormDefinitionIndexItem(
                            definition.getTitle(),
                            definition.getDescription(),
                            "",
                            definition.getPatternId(),
                            definition.getSchemaVersion(),
                            definitionFile.getComputed()));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
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
        List<String> patternIds = new ArrayList<>();
        JSONObject jsonObject = JsonFileHelper.readJsonFromFile(getOrderFile(context));
        if (jsonObject == null) {
            return patternIds;
        }

        JSONArray jsonArray = jsonObject.optJSONArray(FIELD_ORDER_PATTERN_IDS);
        if (jsonArray == null) {
            return patternIds;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            patternIds.add(jsonArray.optString(i));
        }
        return patternIds;
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
