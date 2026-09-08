package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import static com.huozige.lab.container.offlineform.util.Utils.ROOT_DIR;

import android.content.Context;

import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionOrder;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormProgress;
import com.huozige.lab.container.offlineform.model.OfflineFormProgressCalculator;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.offlineform.formitem.file.OfflineFileHelper;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImageFileHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfflineFormFileHelper {

    private static final String DEFINITION_FILE = "definition.json";
    private static final String ORDER_FILE = "order.json";
    private static final String RECORDS_DIR = "records";
    private static final String MANUAL_FILE = "manual.pdf";
    private static final String MANUAL_TEMP_FILE = "manual.pdf.tmp";
    private static final String MANUAL_FILES_DIR = "manuals";
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
        JSONObject jsonObject = JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
        return jsonObject == null ? null : OfflineFormJsonSerializer.restoreDefinitionFileFromJson(jsonObject);
    }

    public static JSONObject readRawDefinitionJson(Context context, String patternId) {
        return JsonFileHelper.readJsonFromFile(getDefinitionFile(context, patternId));
    }

    public static String getDefinitionFilePath(Context context, String patternId) {
        return getDefinitionFile(context, patternId).getAbsolutePath();
    }

    public static File getManualPdfFile(Context context, String patternId) {
        File legacyFile = new File(getPatternDir(context, patternId), MANUAL_FILE);
        if (legacyFile.exists()) {
            return legacyFile;
        }

        List<File> manualFiles = getManualFiles(context, patternId);
        return manualFiles.isEmpty() ? legacyFile : manualFiles.get(0);
    }

    public static File getManualPdfTempFile(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), MANUAL_TEMP_FILE);
    }

    public static List<File> getManualFiles(Context context, String patternId) {
        List<File> manualFiles = new ArrayList<>();
        File manualFilesDir = getManualFilesDir(context, patternId);
        File[] files = manualFilesDir.listFiles(file -> file.isFile()
                && !file.getName().startsWith(".")
                && !file.getName().endsWith(".tmp"));
        if (files != null) {
            Collections.addAll(manualFiles, files);
            Collections.sort(manualFiles, (left, right) -> left.getName().compareToIgnoreCase(right.getName()));
        }
        if (manualFiles.isEmpty()) {
            File legacyFile = new File(getPatternDir(context, patternId), MANUAL_FILE);
            if (legacyFile.exists() && legacyFile.isFile()) {
                manualFiles.add(legacyFile);
            }
        }
        return manualFiles;
    }

    public static File getManualFilesDir(Context context, String patternId) {
        return new File(getPatternDir(context, patternId), MANUAL_FILES_DIR);
    }

    public static File getManualFile(Context context, String patternId, String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }
        String safeFileName = fileName.replace('\\', '/');
        int separatorIndex = safeFileName.lastIndexOf('/');
        if (separatorIndex >= 0) {
            safeFileName = safeFileName.substring(separatorIndex + 1);
        }
        if (safeFileName.isEmpty() || ".".equals(safeFileName) || "..".equals(safeFileName)) {
            return null;
        }
        return new File(getManualFilesDir(context, patternId), safeFileName);
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
        JsonFileHelper.deleteFileOrDirectory(getManualFilesDir(context, patternId));
    }

    public static void deleteSignatureFile(Context context, String patternId) {
        JsonFileHelper.deleteFileOrDirectory(getSignatureFile(context, patternId));
    }

    public static void writeRecord(Context context, OfflineFormRecord record) {
        JsonFileHelper.writeObjectToFile(new File(getRecordsDir(context, record.getPatternId()), record.getRecordId() + ".json"), record);
        refreshProgress(context, record.getPatternId());
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
            refreshProgress(context, patternId);
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
                JSONObject jsonObject = JsonFileHelper.readJsonFromFile(new File(patternDir, DEFINITION_FILE));
                if (jsonObject != null) {
                    OfflineFormDefinitionFile definitionFile = OfflineFormJsonSerializer.restoreDefinitionFileFromJson(jsonObject);
                    OfflineFormDefinition definition = definitionFile.getJsonSchema();
                    if (refreshDefinitionProgress(context, definitionFile)) {
                        writeDefinition(context, definition.getPatternId(), definitionFile);
                    }
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

    /** 依据当前表单最近更新的记录刷新并持久化列表需要的进度元数据。 */
    public static void refreshProgress(Context context, String patternId) {
        if (patternId == null || patternId.isEmpty()) {
            return;
        }
        OfflineFormDefinitionFile definitionFile = readDefinition(context, patternId);
        if (definitionFile == null) {
            return;
        }
        if (refreshDefinitionProgress(context, definitionFile)) {
            writeDefinition(context, patternId, definitionFile);
        }
    }

    private static boolean refreshDefinitionProgress(Context context, OfflineFormDefinitionFile definitionFile) {
        if (definitionFile == null || definitionFile.getJsonSchema() == null) {
            return false;
        }
        if (definitionFile.getComputed() == null) {
            definitionFile.setComputed(new OfflineComputedInfo());
        }

        List<OfflineFormRecord> records = readRecords(context, definitionFile.getJsonSchema().getPatternId());
        OfflineFormRecord latestRecord = records.isEmpty() ? null : records.get(0);
        OfflineFormProgress progress = OfflineFormProgressCalculator.calculate(
                definitionFile.getJsonSchema(), latestRecord);
        OfflineComputedInfo computed = definitionFile.getComputed();
        boolean changed = computed.getTotalFillItems() != progress.getTotalFillItems()
                || computed.getFilledFillItems() != progress.getFilledFillItems()
                || Double.compare(computed.getCompletionRate(), progress.getCompletionRate()) != 0;
        computed.setTotalFillItems(progress.getTotalFillItems());
        computed.setFilledFillItems(progress.getFilledFillItems());
        computed.setCompletionRate(progress.getCompletionRate());
        return changed;
    }

    private static void deleteRecordAttachmentFiles(Context context, String patternId, OfflineFormRecord record) {
        if (record == null || record.getValues() == null || record.getValues().isEmpty()) {
            return;
        }
        OfflineFormDefinitionFile definitionFile = readDefinition(context, patternId);
        if (definitionFile == null || definitionFile.getJsonSchema() == null || definitionFile.getJsonSchema().getSteps() == null) {
            return;
        }
        JSONObject values = new JSONObject();
        values.putAll(record.getValues());
        for (OfflineFormStep step : definitionFile.getJsonSchema().getSteps()) {
            if (step != null) {
                deleteAttachmentFilesFromNodes(context, patternId, step.getItems(), values);
            }
        }
    }

    private static void deleteAttachmentFilesFromNodes(Context context, String patternId, List<OfflineFormNode> nodes, JSONObject values) {
        if (nodes == null || values == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field != null && field.getId() != null && !field.getId().isEmpty()) {
                String rawValue = values.getString(field.getId());
                if (field instanceof SignatureFormItem) {
                    SignatureFormItem.parseAttachments(rawValue).forEach(signature ->
                            OfflineImageFileHelper.deleteLocalFile(context, patternId, signature.getFileName()));
                } else if (field instanceof ImageFormItem) {
                    ImageFormItem.parseImages(rawValue).forEach(image ->
                            OfflineImageFileHelper.deleteLocalFile(context, patternId, image.getFileName()));
                } else if (OfflineFormItemType.FILE.getValue().equals(field.getItemType())) {
                    FileFormItem.parseAttachments(rawValue).forEach(file ->
                            OfflineFileHelper.deleteLocalFile(context, patternId, file.getFileName()));
                } else if (field instanceof ListFormItem) {
                    deleteListAttachmentFiles(context, patternId, (ListFormItem) field, rawValue);
                }
            }
            deleteAttachmentFilesFromNodes(context, patternId, node.getChildren(), values);
        }
    }

    private static void deleteListAttachmentFiles(Context context, String patternId, ListFormItem listItem, String rawValue) {
        JSONArray rows = ListFormItem.parseRows(rawValue);
        for (int i = 0; i < rows.size(); i++) {
            JSONObject rowValues = rows.getJSONObject(i);
            if (rowValues != null) {
                deleteAttachmentFilesFromNodes(context, patternId, listItem.getTemplateNodes(), rowValues);
            }
        }
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
