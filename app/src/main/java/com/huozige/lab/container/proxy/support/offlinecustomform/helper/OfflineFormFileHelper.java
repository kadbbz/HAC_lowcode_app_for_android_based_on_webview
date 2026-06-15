package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import static com.huozige.lab.container.offlineform.util.Utils.ROOT_DIR;

import android.content.Context;

import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionOrder;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.file.OfflineFileHelper;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImageFileHelper;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OfflineFormFileHelper {

    private static final String DEFINITION_FILE = "definition.json";
    private static final String ORDER_FILE = "order.json";
    private static final String RECORDS_DIR = "records";
    private static final String MANUAL_FILE = "manual.pdf";
    private static final String MANUAL_TEMP_FILE = "manual.pdf.tmp";
    private static final String SIGNATURE_FILE = "signature.png";

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

    public static File getManualPdfFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), MANUAL_FILE);
    }

    public static File getManualPdfTempFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), MANUAL_TEMP_FILE);
    }

    public static File getSignatureFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), SIGNATURE_FILE);
    }

    public static void writeSignatureBase64(Context context, String patternId, String signatureBase64) throws IOException {
        writeBytesToFile(getSignatureFile(context, patternId), Base64.getDecoder().decode(stripBase64Prefix(signatureBase64)));
    }

    public static void deleteManualPdfFile(Context context, String patternId) {
        JsonFileHelper.deleteFileOrDirectory(getManualPdfFile(context, patternId));
        JsonFileHelper.deleteFileOrDirectory(getManualPdfTempFile(context, patternId));
    }

    public static void deleteSignatureFile(Context context, String patternId) {
        JsonFileHelper.deleteFileOrDirectory(getSignatureFile(context, patternId));
    }

    public static void writeRecord(Context context, OfflineFormRecord record) {
        JsonFileHelper.writeObjectToFile(new File(getRecordsDir(context, record.getPatternId()), record.getRecordId() + ".json"), record);
    }

    public static OfflineFormRecord readRecord(Context context, String patternId, String recordId) {
        if (patternId == null || patternId.isEmpty() || recordId == null || recordId.isEmpty()) {
            return null;
        }
        return JsonFileHelper.readObjectFromFile(new File(getRecordsDir(context, patternId), recordId + ".json"), OfflineFormRecord.class);
    }

    public static boolean deleteRecord(Context context, String patternId, String recordId) {
        if (patternId == null || patternId.isEmpty() || recordId == null || recordId.isEmpty()) {
            return false;
        }
        File recordFile = new File(getRecordsDir(context, patternId), recordId + ".json");
        OfflineFormRecord record = JsonFileHelper.readObjectFromFile(recordFile, OfflineFormRecord.class);
        boolean deleted = JsonFileHelper.deleteFileOrDirectory(recordFile);
        if (deleted) {
            deleteRecordAttachmentFiles(context, patternId, record);
        }
        return deleted;
    }

    public static int deleteRecordsByStatus(Context context, String patternId, OfflineFormRecordStatus status) {
        int deletedCount = 0;
        for (OfflineFormRecord record : readRecords(context, patternId)) {
            if (record.getStatus() == status && deleteRecord(context, patternId, record.getRecordId())) {
                deletedCount++;
            }
        }
        return deletedCount;
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

    private static void deleteRecordAttachmentFiles(Context context, String patternId, OfflineFormRecord record) {
        if (record == null || record.getValues() == null || record.getValues().isEmpty()) {
            return;
        }
        Map<String, String> attachmentFieldTypes = readAttachmentFieldTypes(context, patternId);
        for (Map.Entry<String, String> entry : attachmentFieldTypes.entrySet()) {
            String rawValue = record.getValues().get(entry.getKey());
            if (OfflineFormItemType.IMAGE.getValue().equals(entry.getValue())) {
                for (ImageFormItemValue image : ImageFormItem.parseImages(rawValue)) {
                    OfflineImageFileHelper.deleteLocalFile(context, patternId, image);
                }
            } else if (OfflineFormItemType.FILE.getValue().equals(entry.getValue())) {
                for (String fileName : FileFormItem.parseFiles(rawValue).values()) {
                    OfflineFileHelper.deleteLocalFile(context, patternId, fileName);
                }
            }
        }
    }

    private static Map<String, String> readAttachmentFieldTypes(Context context, String patternId) {
        Map<String, String> fieldTypes = new HashMap<>();
        OfflineFormDefinitionFile definitionFile = readDefinition(context, patternId);
        if (definitionFile == null || definitionFile.getJsonSchema() == null || definitionFile.getJsonSchema().getSteps() == null) {
            return fieldTypes;
        }
        for (OfflineFormStep step : definitionFile.getJsonSchema().getSteps()) {
            if (step != null) {
                collectAttachmentFieldTypes(step.getItems(), fieldTypes);
            }
        }
        return fieldTypes;
    }

    private static void collectAttachmentFieldTypes(List<OfflineFormNode> nodes, Map<String, String> fieldTypes) {
        if (nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field != null
                    && isAttachmentFieldType(field.getItemType())
                    && field.getId() != null
                    && !field.getId().isEmpty()) {
                fieldTypes.put(field.getId(), field.getItemType());
            }
            collectAttachmentFieldTypes(node.getChildren(), fieldTypes);
        }
    }

    private static boolean isAttachmentFieldType(String itemType) {
        return OfflineFormItemType.IMAGE.getValue().equals(itemType)
                || OfflineFormItemType.FILE.getValue().equals(itemType);
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

    private static void writeBytesToFile(File file, byte[] bytes) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(bytes);
        }
    }

    private static String stripBase64Prefix(String value) {
        if (value == null) {
            return "";
        }
        int commaIndex = value.indexOf(",");
        return value.startsWith("data:") && commaIndex >= 0 ? value.substring(commaIndex + 1) : value;
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
