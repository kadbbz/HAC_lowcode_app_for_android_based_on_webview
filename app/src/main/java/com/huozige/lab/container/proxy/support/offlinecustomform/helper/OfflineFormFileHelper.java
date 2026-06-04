package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;

import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndex;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormRecord;

import org.json.JSONObject;

import java.io.File;

public class OfflineFormFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String DEFINITION_FILE = "definition.json";
    private static final String RECORDS_DIR = "records";

    public static OfflineFormDefinitionIndex readDefinitionIndex(Context context) {
        JSONObject jsonObject = JsonFileHelper.readJsonFromExternalStorage(context, JsonFileHelper.FILE_NAME_OFFLINE_LIST);
        if (jsonObject == null) {
            return new OfflineFormDefinitionIndex(null);
        }
        return OfflineFormJsonSerializer.parseJsonToDefinitionIndex(jsonObject);
    }

    public static void writeDefinitionIndex(Context context, OfflineFormDefinitionIndex index) {
        JsonFileHelper.writeJsonToExternalStorage(context, JsonFileHelper.FILE_NAME_OFFLINE_LIST, OfflineFormJsonSerializer.parseDefinitionIndexToJson(index));
    }

    public static void writeDefinition(Context context, String patternId, OfflineFormDefinitionFile definitionFile) {
        JsonFileHelper.writeJsonToFile(getDefinitionFile(context, patternId), OfflineFormJsonSerializer.parseDefinitionFileToJson(definitionFile));
    }

    public static OfflineFormDefinitionFile readDefinition(Context context, String patternId) {
        JSONObject jsonObject = JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
        return jsonObject == null ? null : OfflineFormJsonSerializer.parseJsonToDefinitionFile(jsonObject);
    }

    public static void writeRecord(Context context, OfflineFormRecord record) {
        JsonFileHelper.writeJsonToFile(new File(getRecordsDir(context, record.getPatternId()), record.getRecordId() + ".json"), OfflineFormJsonSerializer.parseRecordToJson(record));
    }

    public static boolean deletePatternDirectory(Context context, String patternId) {
        return JsonFileHelper.deleteFileOrDirectory(getPatternDir(context, patternId));
    }

    private static File getDefinitionFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), DEFINITION_FILE);
    }

    private static File getRecordsDir(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), RECORDS_DIR);
    }

    private static File getPatternDir(Context context, String patternId) {
        return new File(new File(context.getExternalFilesDir(null), ROOT_DIR), sanitizePathSegment(patternId));
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
