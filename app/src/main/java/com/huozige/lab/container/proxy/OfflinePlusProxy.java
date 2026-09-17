package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.OfflinePlusExportListActivity;
import com.huozige.lab.container.offlineform.formitem.file.OfflineFileHelper;
import com.huozige.lab.container.offlineform.formitem.signature.SignatureGuideRenderer;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemType;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.PatternInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.utilities.StringUtils;
import com.huozige.lab.container.utilities.HACDownloadManager;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFactory;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormProgress;
import com.huozige.lab.container.offlineform.model.OfflineFormProgressCalculator;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.util.Utils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class OfflinePlusProxy extends AbstractProxy{

    private ActivityResultLauncher<Intent> _exportMultipleRecordsLauncher;

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _exportMultipleRecordsLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                callback(CallbackParams.error("Export canceled."));
                return;
            }

            ArrayList<String> projectIds = result.getData().getStringArrayListExtra(OfflinePlusExportListActivity.EXTRA_SELECTED_PATTERN_IDS);
            if (projectIds == null || projectIds.isEmpty()) {
                callback(CallbackParams.error("No form selected."));
                return;
            }

            exportMultipleRecords(projectIds);
        });
    }

    @Override
    public void beforeActivityPause() {
        super.beforeActivityPause();
    }

    @Override
    public String getName() {
        return "offlinePlus";
    }

    @JavascriptInterface
    public void offlinePlusGetPatternsAsync(String ticket) {
        writeInfoLog("OfflinePlusGetPatterns");
        registryCallbackTicket(ticket);

        Context context = this.getWebView().getContext();
        List<OfflineFormDefinitionIndexItem> definitions = OfflineFormFileHelper.readDefinitions(context);
        List<String> patternIds = new ArrayList<>();
        for (OfflineFormDefinitionIndexItem definition : definitions) {
            patternIds.add(definition.getPatternId());
        }
        callback(CallbackParams.success(JSON.toJSONString(patternIds)));
    }

    @JavascriptInterface
    public void offlinePlusAddPatternAsync(String input) {
        writeInfoLog("OfflinePlusAddPattern");

        PatternInput inputObj = JSON.parseObject(input, PatternInput.class);

        registryCallbackTicket(inputObj.ticket);

        Context context = this.getWebView().getContext();
        try {
            parseJsonToFile(context, inputObj);
        } catch (Exception e) {
            finishAddPattern(null, CallbackParams.error(e.toString()), context.getString(R.string.offline_error_add_pattern_failed, e));
            return;
        }

        // 手册 PDF 改由 offlinePlusDownloadManualPdfAsync 单独下载，添加表单时不再阻塞等待手册。
        finishAddPattern(null, CallbackParams.success("success"), null);
    }

    /**
     * 下载并绑定离线表单的说明文档。
     */
    @JavascriptInterface
    public void offlinePlusDownloadManualPdfAsync(
            String patternId,
            String[] manualPdfUrl,
            String ticket) {
        writeInfoLog("OfflinePlusDownloadManualPdfAsync");
        registryCallbackTicket(ticket);

        Context context = this.getWebView().getContext();
        if (StringUtils.isNullOrBlank(patternId)) {
            finishManualDownload(null, CallbackParams.error("patternId is empty."), null);
            return;
        }
        if (OfflineFormFileHelper.readDefinition(context, patternId) == null) {
            finishManualDownload(null, CallbackParams.error("patternId does not exist."), null);
            return;
        }
        if (manualPdfUrl == null || manualPdfUrl.length == 0) {
            OfflineFormFileHelper.deleteManualPdfFile(context, patternId);
            finishManualDownload(null, CallbackParams.success("success"), null);
            return;
        }

        String currentUrl = getCurrentUrlOnUiThread();
        ProgressDialog progressDialog = showManualDownloadDialog(context);
        new Thread(() -> {
            try {
                saveManualDocuments(context, patternId, manualPdfUrl, currentUrl, progressDialog);
                finishManualDownload(progressDialog, CallbackParams.success("success"), null);
            } catch (Exception e) {
                finishManualDownload(progressDialog, CallbackParams.error(e.toString()), context.getString(R.string.offline_error_manual_download_failed, e));
            }
        }).start();
    }
    private String getCurrentUrlOnUiThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return this.getWebView().getUrl();
        }

        AtomicReference<String> urlRef = new AtomicReference<>("");
        CountDownLatch latch = new CountDownLatch(1);
        runOnUiThread(() -> {
            urlRef.set(this.getWebView().getUrl());
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return urlRef.get();
    }

    @JavascriptInterface
    public String offlinePlusGetRecords(String projectId) {
        writeInfoLog("OfflinePlusGetRecords");

        if (projectId == null || projectId.isEmpty()) {
            return "";
        }

        Context context = this.getWebView().getContext();
        List<OfflineFormRecord> records = filterSubmittedRecords(OfflineFormFileHelper.readRecords(context, projectId));
        return JSON.toJSONString(records);
    }

    @JavascriptInterface
    public void offlinePlusExportRecordsAsync(String projectId, String ticket) {
        writeInfoLog("OfflinePlusExportRecordsAsync");
        registryCallbackTicket(ticket);

        if (StringUtils.isNullOrBlank(projectId)) {
            callback(CallbackParams.error("projectId is empty."));
            return;
        }

        callback(CallbackParams.success(buildExportRecordsResult(projectId)));
    }

    @JavascriptInterface
    public void offlinePlusExportMultipleRecordsAsync(String ticket) {
        writeInfoLog("OfflinePlusExportMultipleRecordsAsync");
        registryCallbackTicket(ticket);

        runOnUiThread(() -> {
            if (_exportMultipleRecordsLauncher == null) {
                callback(CallbackParams.error("Export launcher is not initialized."));
                return;
            }

            _exportMultipleRecordsLauncher.launch(createIntent(OfflinePlusExportListActivity.class));
        });
    }

    @JavascriptInterface
    public String offlinePlusLoadAttachment(String projectId, String localName) {
        writeInfoLog("OfflinePlusLoadAttachment:" + localName);

        if (StringUtils.isNullOrBlank(projectId) || StringUtils.isNullOrBlank(localName)) {
            return "";
        }

        try {
            File file = Utils.resolveLocalFile(this.getWebView().getContext(), projectId, localName);
            if (file == null || !file.exists() || !file.isFile()) {
                return "";
            }
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(Utils.getExtension(localName));
            if (StringUtils.isNullOrBlank(mimeType)) {
                mimeType = "application/octet-stream";
            }
            SignatureExportInfo signatureInfo = findSignatureExportInfo(this.getWebView().getContext(), projectId, localName);
            if (signatureInfo != null) {
                return buildSignatureDataUrl(file, signatureInfo);
            }
            return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(readAllBytes(file));
        } catch (Exception e) {
            writeErrorLog("读取离线附件失败：" + localName + "，详情：" + e);
            return "";
        }
    }

    private String buildSignatureDataUrl(File file, SignatureExportInfo signatureInfo) throws Exception {
        Bitmap source = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (source == null) {
            return "";
        }
        Bitmap result = null;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            canvas.drawColor(Color.WHITE);
            SignatureGuideRenderer.draw(canvas, result.getWidth(), result.getHeight(),
                    signatureInfo.userName, new Paint(Paint.ANTI_ALIAS_FLAG));
            // Preserve the guide through the white PNG background, with signature strokes above it.
            Paint signaturePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
            signaturePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            canvas.drawBitmap(source, null,
                    new android.graphics.RectF(0, 0, result.getWidth(), result.getHeight()), signaturePaint);
            drawSignatureDisclaimer(canvas, result, signatureInfo.disclaimer);
            if (!result.compress(Bitmap.CompressFormat.PNG, 100, output)) {
                throw new IOException("Unable to encode signature PNG");
            }
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(output.toByteArray());
        } finally {
            source.recycle();
            if (result != null) {
                result.recycle();
            }
        }
    }

    private void drawSignatureDisclaimer(Canvas canvas, Bitmap bitmap, String disclaimer) {
        if (StringUtils.isNullOrBlank(disclaimer)) {
            return;
        }
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(95, 99, 104));
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f,
                getWebView().getResources().getDisplayMetrics()));
        float left = 12f * getWebView().getResources().getDisplayMetrics().density;
        float top = 10f * getWebView().getResources().getDisplayMetrics().density;
        int width = Math.max(1, (int) (bitmap.getWidth() - left));
        StaticLayout layout = StaticLayout.Builder.obtain(disclaimer, 0, disclaimer.length(), paint, width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setIncludePad(true)
                .build();
        canvas.save();
        canvas.translate(left, top);
        layout.draw(canvas);
        canvas.restore();
    }

    private SignatureExportInfo findSignatureExportInfo(Context context, String projectId, String localName) {
        OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(context, projectId);
        if (definitionFile == null || definitionFile.getJsonSchema() == null
                || definitionFile.getJsonSchema().getSteps() == null) {
            return null;
        }
        for (OfflineFormRecord record : OfflineFormFileHelper.readRecords(context, projectId)) {
            if (record == null || record.getValues() == null) {
                continue;
            }
            for (OfflineFormStep step : definitionFile.getJsonSchema().getSteps()) {
                SignatureExportInfo info = findSignatureExportInfo(step == null ? null : step.getItems(), record.getValues(), localName);
                if (info != null) {
                    return info;
                }
            }
        }
        return null;
    }

    private SignatureExportInfo findSignatureExportInfo(List<OfflineFormNode> nodes, Map<String, String> values, String localName) {
        if (nodes == null) {
            return null;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field instanceof SignatureFormItem) {
                List<SignatureFormItemValue> signatures = SignatureFormItem.parseSignatures(values.get(field.getId()), null);
                for (SignatureFormItemValue signature : signatures) {
                    if (signature != null && localName.equals(signature.getFileName())) {
                        return new SignatureExportInfo(signature.getUserName(), ((SignatureFormItem) field).getDisclaimer());
                    }
                }
            } else if (field instanceof ListFormItem) {
                for (Object row : ListFormItem.parseRows(values.get(field.getId()))) {
                    if (!(row instanceof JSONObject)) {
                        continue;
                    }
                    JSONObject rowValues = (JSONObject) row;
                    Map<String, String> childValues = new HashMap<>();
                    for (String key : rowValues.keySet()) {
                        childValues.put(key, rowValues.getString(key));
                    }
                    SignatureExportInfo info = findSignatureExportInfo(
                            ((ListFormItem) field).getTemplateNodes(), childValues, localName);
                    if (info != null) {
                        return info;
                    }
                }
            }
            SignatureExportInfo childInfo = findSignatureExportInfo(node.getChildren(), values, localName);
            if (childInfo != null) {
                return childInfo;
            }
        }
        return null;
    }

    private static final class SignatureExportInfo {
        private final String userName;
        private final String disclaimer;

        private SignatureExportInfo(String userName, String disclaimer) {
            this.userName = userName;
            this.disclaimer = disclaimer;
        }
    }

    private String buildExportRecordsResult(String projectId) {
        Context context = this.getWebView().getContext();
        List<OfflineFormRecord> records = readSubmittedRecords(context, projectId);
        return buildExportRecordsResultObject(context, projectId, records).toJSONString();
    }

    private String buildExportRecordsResults(List<String> projectIds) {
        Context context = this.getWebView().getContext();
        JSONArray results = new JSONArray();
        if (projectIds == null) {
            return results.toJSONString();
        }

        for (String projectId : projectIds) {
            if (StringUtils.isNullOrBlank(projectId)) {
                continue;
            }
            List<OfflineFormRecord> records = readSubmittedRecords(context, projectId);
            results.add(buildExportRecordsResultObject(context, projectId, records));
        }
        return results.toJSONString();
    }

    private JSONObject buildExportRecordsResultObject(Context context, String projectId, List<OfflineFormRecord> records) {
        JSONObject result = new JSONObject();
        OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(context, projectId);
        Map<String, String> attachmentFieldTypes = readAttachmentFieldTypes(definitionFile);
        normalizeEmptyAttachmentValues(records, attachmentFieldTypes);
        result.put("projectId", projectId);
        OfflineFormProgress progress = calculateExportProgress(definitionFile, records);
        result.put("totalFillItems", progress.getTotalFillItems());
        result.put("filledFillItems", progress.getFilledFillItems());
        result.put("completionRate", progress.getCompletionRate());
        result.put("records", buildExportRecords(records, definitionFile));
        result.put("attachments", buildExportAttachments(records, definitionFile));
        result.put("signature", readSignatureDataUrl(context, projectId));
        return result;
    }

    private OfflineFormProgress calculateExportProgress(
            OfflineFormDefinitionFile definitionFile,
            List<OfflineFormRecord> records) {
        if (definitionFile == null || definitionFile.getJsonSchema() == null
                || records == null || records.isEmpty()) {
            return OfflineFormProgress.empty();
        }
        // 导出结果包含多条记录时，进度按每条记录分别统计后汇总，避免只返回最近一条记录的进度。
        int total = 0;
        int filled = 0;
        for (OfflineFormRecord record : records) {
            OfflineFormProgress recordProgress = OfflineFormProgressCalculator.calculate(
                    definitionFile.getJsonSchema(), record);
            total += recordProgress.getTotalFillItems();
            filled += recordProgress.getFilledFillItems();
        }
        double completionRate = total == 0 ? 0d : filled * 100d / total;
        return new OfflineFormProgress(total, filled, completionRate);
    }

    /**
     * 将本地记录中的 key-value 字段转换为导出用的字段对象，附带表单定义中的 ODate 更新时间。
     * 本地记录文件仍保持 Map<String, String>，避免影响离线填报和历史记录编辑。
     */
    private JSONArray buildExportRecords(List<OfflineFormRecord> records, OfflineFormDefinitionFile definitionFile) {
        JSONArray result = new JSONArray();
        if (records == null) {
            return result;
        }

        for (OfflineFormRecord record : records) {
            if (record == null) {
                continue;
            }
            JSONObject recordJson = new JSONObject();
            recordJson.put("recordId", record.getRecordId());
            recordJson.put("patternId", record.getPatternId());
            recordJson.put("schemaVersion", record.getSchemaVersion());
            recordJson.put("status", record.getStatus());
            recordJson.put("createdAt", record.getCreatedAt());
            recordJson.put("updatedAt", record.getUpdatedAt());
            recordJson.put("values", buildExportValues(record.getValues(), definitionFile));
            result.add(recordJson);
        }
        return result;
    }

    private JSONObject buildExportValues(Map<String, String> values, OfflineFormDefinitionFile definitionFile) {
        JSONObject result = new JSONObject();
        if (values == null || values.isEmpty()) {
            return result;
        }

        List<OfflineFormStep> steps = definitionFile == null || definitionFile.getJsonSchema() == null
                ? null : definitionFile.getJsonSchema().getSteps();
        if (steps != null) {
            for (OfflineFormStep step : steps) {
                if (step != null) {
                    collectExportValues(result, values, step.getItems());
                }
            }
        }

        // 对定义中不存在的历史字段也保留原始值，避免导出时丢数据。
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), buildExportFieldValue(entry.getValue(), null));
            }
        }
        return result;
    }

    private void collectExportValues(JSONObject target, Map<String, String> values, List<OfflineFormNode> nodes) {
        if (target == null || values == null || nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field != null && !StringUtils.isNullOrBlank(field.getId())) {
                String rawValue = values.get(field.getId());
                target.put(field.getId(), buildExportFieldValue(rawValue, field));
            }
            collectExportValues(target, values, node.getChildren());
        }
    }

    private JSONObject buildExportFieldValue(String rawValue, BaseFormItem field) {
        JSONObject valueObject = new JSONObject();
        if (field instanceof ListFormItem) {
            valueObject.put("value", buildExportListValue((ListFormItem) field, rawValue));
        } else {
            valueObject.put("value", rawValue == null ? "" : rawValue);
        }
        if (field != null && field.getUpdateTime() != null) {
            valueObject.put("updateTime", field.getUpdateTime());
        }
        return valueObject;
    }

    private JSONArray buildExportListValue(ListFormItem listItem, String rawValue) {
        JSONArray result = new JSONArray();
        JSONArray rows = ListFormItem.parseRows(rawValue);
        for (int i = 0; i < rows.size(); i++) {
            JSONObject rowValue = rows.getJSONObject(i);
            if (rowValue == null) {
                continue;
            }
            JSONObject exportedRow = new JSONObject();
            collectExportRowValues(exportedRow, rowValue, listItem.getTemplateNodes());
            for (String fieldId : rowValue.keySet()) {
                if (!exportedRow.containsKey(fieldId)) {
                    exportedRow.put(fieldId, buildExportFieldValue(rowValue.getString(fieldId), null));
                }
            }
            result.add(exportedRow);
        }
        return result;
    }

    private void collectExportRowValues(JSONObject target, JSONObject rowValues, List<OfflineFormNode> nodes) {
        if (target == null || rowValues == null || nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field != null && !StringUtils.isNullOrBlank(field.getId())) {
                target.put(field.getId(), buildExportFieldValue(rowValues.getString(field.getId()), field));
            }
            collectExportRowValues(target, rowValues, node.getChildren());
        }
    }

    private List<OfflineFormRecord> readSubmittedRecords(Context context, String projectId) {
        return filterSubmittedRecords(OfflineFormFileHelper.readRecords(context, projectId));
    }

    private void exportMultipleRecords(List<String> projectIds) {
        new Thread(() -> {
            try {
                callback(CallbackParams.success(buildExportRecordsResults(projectIds)));
            } catch (Exception e) {
                callback(CallbackParams.error(e.toString()));
            }
        }).start();
    }

    private void normalizeEmptyAttachmentValues(List<OfflineFormRecord> records, Map<String, String> attachmentFieldTypes) {
        if (records == null || records.isEmpty() || attachmentFieldTypes.isEmpty()) {
            return;
        }

        for (OfflineFormRecord record : records) {
            if (record == null || record.getValues() == null || record.getValues().isEmpty()) {
                continue;
            }
            for (String fieldId : attachmentFieldTypes.keySet()) {
                String value = record.getValues().get(fieldId);
                if ("[]".equals(value) || "{}".equals(value)) {
                    record.getValues().put(fieldId, "");
                }
            }
        }
    }

    private JSONArray buildExportAttachments(List<OfflineFormRecord> records, OfflineFormDefinitionFile definitionFile) {
        JSONArray attachments = new JSONArray();
        if (records == null || records.isEmpty()
                || definitionFile == null
                || definitionFile.getJsonSchema() == null
                || definitionFile.getJsonSchema().getSteps() == null) {
            return attachments;
        }

        for (OfflineFormRecord record : records) {
            if (record == null || record.getValues() == null || record.getValues().isEmpty()) {
                continue;
            }
            JSONObject values = new JSONObject();
            values.putAll(record.getValues());
            JSONArray rootPath = new JSONArray();
            rootPath.add(record.getRecordId());
            for (OfflineFormStep step : definitionFile.getJsonSchema().getSteps()) {
                if (step != null) {
                    collectExportAttachments(attachments, rootPath, step.getItems(), values);
                }
            }
        }
        return attachments;
    }

    private void collectExportAttachments(JSONArray attachments, JSONArray pathPrefix, List<OfflineFormNode> nodes, JSONObject values) {
        if (nodes == null || values == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            BaseFormItem field = node.getField();
            if (field != null && !StringUtils.isNullOrBlank(field.getId())) {
                String rawValue = values.getString(field.getId());
                JSONArray fieldPath = appendPath(pathPrefix, field.getId());
                if (isAttachmentFieldType(field.getItemType())) {
                    addAttachments(attachments, fieldPath, field.getId(), field.getItemType(), rawValue);
                } else if (field instanceof ListFormItem) {
                    collectListExportAttachments(attachments, fieldPath, (ListFormItem) field, rawValue);
                }
            }
            collectExportAttachments(attachments, pathPrefix, node.getChildren(), values);
        }
    }

    private void collectListExportAttachments(JSONArray attachments, JSONArray listPath, ListFormItem listItem, String rawValue) {
        JSONArray rows = ListFormItem.parseRows(rawValue);
        for (int i = 0; i < rows.size(); i++) {
            JSONObject rowValues = rows.getJSONObject(i);
            if (rowValues == null) {
                continue;
            }
            collectExportAttachments(attachments, appendPath(listPath, i), listItem.getTemplateNodes(), rowValues);
        }
    }

    private JSONArray appendPath(JSONArray pathPrefix, Object value) {
        JSONArray path = new JSONArray();
        if (pathPrefix != null) {
            path.addAll(pathPrefix);
        }
        path.add(value);
        return path;
    }

    private void addAttachments(JSONArray attachments, JSONArray path, String fieldId, String fieldType, String rawValue) {
        boolean imageField = OfflineFormItemType.IMAGE.getValue().equals(fieldType);
        boolean signatureField = OfflineFormItemType.SIGNATURE.getValue().equals(fieldType);
        if (signatureField) {
            addSignatureAttachments(attachments, path, fieldId, rawValue);
            return;
        }

        List<AttachmentFormItemValue> attachmentValues = imageField
                ? ImageFormItem.parseImages(rawValue) : FileFormItem.parseAttachments(rawValue);
        for (int i = 0; i < attachmentValues.size(); i++) {
            AttachmentFormItemValue attachmentValue = attachmentValues.get(i);
            if (attachmentValue == null) {
                continue;
            }
            String originalName = attachmentValue.getOriginalName();
            String localName = attachmentValue.getFileName();
            if (StringUtils.isNullOrBlank(localName) || !imageField && StringUtils.isNullOrBlank(originalName)) {
                continue;
            }

            com.alibaba.fastjson.JSONObject attachment = new com.alibaba.fastjson.JSONObject();
            attachment.put("path", path);
            attachment.put("type", imageField || signatureField ? "image" : "file");
            attachment.put("localName", localName);
            attachment.put("recordId", path == null || path.isEmpty() ? "" : path.getString(0));
            attachment.put("fieldId", fieldId);
            if (!StringUtils.isNullOrBlank(originalName)) {
                attachment.put("originalName", originalName);
            }
            attachment.put("fileName", localName);
            attachments.add(attachment);
        }
    }

    private void addSignatureAttachments(JSONArray attachments, JSONArray path, String fieldId, String rawValue) {
        List<SignatureFormItemValue> signatures = SignatureFormItem.parseSignatures(rawValue, null);
        for (int i = 0; i < signatures.size(); i++) {
            SignatureFormItemValue signature = signatures.get(i);
            if (signature == null || StringUtils.isNullOrBlank(signature.getFileName())) {
                continue;
            }

            com.alibaba.fastjson.JSONObject attachment = new com.alibaba.fastjson.JSONObject();
            // 每张签名使用独立路径，避免上传端按同一路径将多个文件名拼接为 a.png|b.png。
            attachment.put("path", appendPath(path, i));
            attachment.put("type", "image");
            attachment.put("localName", signature.getFileName());
            attachment.put("recordId", path == null || path.isEmpty() ? "" : path.getString(0));
            attachment.put("fieldId", fieldId);
            String userName = signature.getUserName();
            String originalName = StringUtils.isNullOrBlank(userName) ? "签名" : userName;
            attachment.put("originalName", originalName + ".png");
            attachment.put("fileName", signature.getFileName());
            if (signature.getUpdateTime() != null) {
                attachment.put("updateTime", signature.getUpdateTime());
            }
            attachments.add(attachment);
        }
    }

    private Map<String, String> readAttachmentFieldTypes(OfflineFormDefinitionFile definitionFile) {
        Map<String, String> fieldTypes = new HashMap<>();
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

    private void collectAttachmentFieldTypes(List<OfflineFormNode> nodes, Map<String, String> fieldTypes) {
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
                    && !StringUtils.isNullOrBlank(field.getId())) {
                fieldTypes.put(field.getId(), field.getItemType());
            }
            collectAttachmentFieldTypes(node.getChildren(), fieldTypes);
        }
    }

    private boolean isAttachmentFieldType(String itemType) {
        return OfflineFormItemType.IMAGE.getValue().equals(itemType)
                || OfflineFormItemType.SIGNATURE.getValue().equals(itemType)
                || OfflineFormItemType.FILE.getValue().equals(itemType);
    }

    private byte[] readAllBytes(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = input.read(buffer)) >= 0) {
                output.write(buffer, 0, length);
            }
            return output.toByteArray();
        }
    }

    @JavascriptInterface
    public void offlinePlusMarkRecordExportedAsync(String projectId, String recordIds, String ticket) {
        writeInfoLog("OfflinePlusMarkRecordExportedAsync");
        registryCallbackTicket(ticket);

        callback(CallbackParams.success(markRecordsExported(projectId, recordIds)));
    }

    private String markRecordsExported(String projectId, String recordIds) {
        if (StringUtils.isNullOrBlank(recordIds)) {
            return "recordIds is empty.";
        }

        List<String> errors = new ArrayList<>();
        boolean hasRecordId = false;
        for (String recordId : recordIds.split(",")) {
            String trimmedRecordId = recordId.trim();
            if (StringUtils.isNullOrBlank(trimmedRecordId)) {
                continue;
            }
            hasRecordId = true;

            String result = markRecordExported(projectId, trimmedRecordId);
            if (!"success".equals(result)) {
                errors.add(trimmedRecordId + ": " + result);
            }
        }

        if (!hasRecordId) {
            return "recordIds is empty.";
        }
        if (errors.isEmpty()) {
            return "success";
        }
        return joinLines(errors);
    }

    private String markRecordExported(String projectId, String recordId) {
        if (StringUtils.isNullOrBlank(projectId)) {
            return "projectId is empty.";
        }
        if (StringUtils.isNullOrBlank(recordId)) {
            return "recordId is empty.";
        }

        Context context = this.getWebView().getContext();
        OfflineFormRecord record = OfflineFormFileHelper.readRecord(context, projectId, recordId);
        if (record == null) {
            return "record not found.";
        }
        if (record.getStatus() == OfflineFormRecordStatus.EXPORTED) {
            return "record already exported.";
        }
        if (record.getStatus() != OfflineFormRecordStatus.SUBMITTED) {
            return "record is not submitted.";
        }

        record.setStatus(OfflineFormRecordStatus.EXPORTED);
        OfflineFormFileHelper.writeRecord(context, record);
        return "success";
    }

    @JavascriptInterface
    public void offlinePlusDeleteReadRecordsAsync(String projectId, String recordIds, String ticket) {
        writeInfoLog("OfflinePlusDeleteReadRecordsAsync");
        registryCallbackTicket(ticket);

        callback(CallbackParams.success(deleteReadRecords(projectId, recordIds)));
    }

    private String deleteReadRecords(String projectId, String recordIds) {
        if (StringUtils.isNullOrBlank(projectId)) {
            return "projectId is empty.";
        }

        Context context = this.getWebView().getContext();
        if (StringUtils.isNullOrBlank(recordIds)) {
            OfflineFormFileHelper.deleteRecordsByStatus(context, projectId, OfflineFormRecordStatus.EXPORTED);
            return "success";
        }

        List<String> errors = new ArrayList<>();
        boolean hasRecordId = false;
        for (String recordId : recordIds.split(",")) {
            String trimmedRecordId = recordId.trim();
            if (StringUtils.isNullOrBlank(trimmedRecordId)) {
                continue;
            }
            hasRecordId = true;

            OfflineFormRecord record = OfflineFormFileHelper.readRecord(context, projectId, trimmedRecordId);
            if (record != null && record.getStatus() == OfflineFormRecordStatus.EXPORTED) {
                OfflineFormFileHelper.deleteRecord(context, projectId, trimmedRecordId);
            } else {
                errors.add(trimmedRecordId + ": record is not read.");
            }
        }

        if (!hasRecordId) {
            return "recordIds is empty.";
        }
        if (errors.isEmpty()) {
            return "success";
        }
        return joinLines(errors);
    }

    @JavascriptInterface
    public void offlinePlusDeleteProjectAsync(String projectId, String ticket) {
        writeInfoLog("OfflinePlusDeleteProjectAsync");
        registryCallbackTicket(ticket);

        callback(CallbackParams.success(deleteProject(projectId)));
    }

    private String deleteProject(String projectId) {
        if (StringUtils.isNullOrBlank(projectId)) {
            return "projectId is empty.";
        }

        Context context = this.getWebView().getContext();
        if (!OfflineFormFileHelper.deletePatternDirectory(context, projectId)) {
            return "delete failed.";
        }

        OfflineFormFileHelper.removeDefinitionOrder(context, projectId);
        return "success";
    }

    private String joinLines(List<String> values) {
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            if (result.length() > 0) {
                result.append("\n");
            }
            result.append(value);
        }
        return result.toString();
    }

    private List<OfflineFormRecord> filterSubmittedRecords(List<OfflineFormRecord> records) {
        List<OfflineFormRecord> submittedRecords = new ArrayList<>();
        for (OfflineFormRecord record : records) {
            if (record.getStatus() == OfflineFormRecordStatus.SUBMITTED) {
                submittedRecords.add(record);
            }
        }
        return submittedRecords;
    }

    private void parseJsonToFile(Context context, PatternInput input) {
        OfflineFormDefinition definition = OfflineFormDefinitionFactory.fromPatternInput(input);

        OfflineFormDefinitionIndexItem listItem = updateDefinitionOrder(context, input, definition);

        parseJsonToCustomFormFile(context, input, definition, listItem);
    }

    private OfflineFormDefinitionIndexItem updateDefinitionOrder(Context context, PatternInput input, OfflineFormDefinition definition) {

        List<OfflineFormDefinitionIndexItem> list = OfflineFormFileHelper.readDefinitions(context);
        OfflineFormDefinitionFile oldDefinition = OfflineFormFileHelper.readDefinition(context, input.patternId);
        String theme = oldDefinition == null ? "" : oldDefinition.getComputed().getTheme();
        List<String> displayColumns = oldDefinition == null ? buildDefaultDisplayColumns(definition) : oldDefinition.getComputed().getDisplayColumns();
        int recordPageSize = oldDefinition == null ? OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE : oldDefinition.getComputed().getRecordPageSize();

        int oldIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            OfflineFormDefinitionIndexItem item = list.get(i);
            if (item.getPatternId().equals(input.patternId)) {
                oldIndex = i;
                if (theme.isEmpty()) {
                    theme = item.getComputed().getTheme();
                }
                break;
            }
        }

        // 同一个项目编号重复导入时沿用旧主题色；只有新增项目才按导入顺序分配主题色。
        if (theme.isEmpty()) {
            theme = OfflineComputedHelper.getThemeColor(list.size());
        }
        OfflineComputedInfo computedInfo = new OfflineComputedInfo();
        computedInfo.setTheme(theme);
        computedInfo.setDisplayColumns(displayColumns);
        computedInfo.setRecordPageSize(recordPageSize);
        OfflineFormDefinitionIndexItem newPattern = new OfflineFormDefinitionIndexItem(input.title, input.description, "", input.patternId, input.schemaVersion, computedInfo);
        if (oldIndex >= 0) {
            list.set(oldIndex, newPattern);
        } else {
            list.add(newPattern);
        }

        OfflineFormFileHelper.writeDefinitionOrder(context, list);
        return newPattern;

    }

    private void parseJsonToCustomFormFile(Context context, PatternInput input, OfflineFormDefinition definition, OfflineFormDefinitionIndexItem listItem) {

        OfflineFormDefinitionFile definitionFile = new OfflineFormDefinitionFile(definition, listItem.getComputed());
        OfflineFormFileHelper.writeDefinition(context, input.patternId, definitionFile);
    }

    private void saveManualDocuments(
            Context context,
            String patternId,
            String[] manualPdfUrls,
            String currentUrl,
            ProgressDialog progressDialog) throws IOException {
        // 一次调用代表一组完整资源，先清理旧文件，避免旧说明文档残留在列表中。
        OfflineFormFileHelper.deleteManualPdfFile(context, patternId);
        if (manualPdfUrls == null || manualPdfUrls.length == 0) {
            return;
        }

        List<String> urls = new ArrayList<>();
        for (String url : manualPdfUrls) {
            if (StringUtils.isNotBlank(url)) {
                urls.add(url.trim());
            }
        }
        if (urls.isEmpty()) {
            return;
        }

        File manualFilesDir = OfflineFormFileHelper.getManualFilesDir(context, patternId);
        if (!manualFilesDir.exists() && !manualFilesDir.mkdirs()) {
            throw new IOException(context.getString(R.string.offline_error_manual_save_failed));
        }

        List<String> usedFileNames = new ArrayList<>();
        try {
            for (int index = 0; index < urls.size(); index++) {
                String resolvedUrl = resolveManualPdfUrl(urls.get(index), currentUrl);
                HttpURLConnection connection = null;
                File tempFile = null;
                try {
                    connection = (HttpURLConnection) new URL(resolvedUrl).openConnection();
                    String cookie = CookieManager.getInstance().getCookie(resolvedUrl);
                    if (StringUtils.isNotBlank(cookie)) {
                        connection.setRequestProperty("Cookie", cookie);
                    }
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode < 200 || responseCode >= 300) {
                        throw new IOException(context.getString(R.string.offline_error_manual_download_failed_with_code, responseCode));
                    }

                    String fileName = HACDownloadManager.parseContentDisposition(
                            connection.getHeaderField("Content-Disposition"));
                    if (StringUtils.isNullOrBlank(fileName)) {
                        fileName = getFileNameFromUrl(resolvedUrl, index);
                    }
                    fileName = makeUniqueFileName(fileName, usedFileNames);
                    File targetFile = OfflineFormFileHelper.getManualFile(context, patternId, fileName);
                    if (targetFile == null) {
                        throw new IOException(context.getString(R.string.offline_error_manual_save_failed));
                    }
                    tempFile = new File(manualFilesDir, "." + targetFile.getName() + ".tmp");

                    int contentLength = connection.getContentLength();
                    try (InputStream input = connection.getInputStream();
                         FileOutputStream output = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[8192];
                        long totalRead = 0;
                        long startTime = System.currentTimeMillis();
                        updateManualDownloadProgress(progressDialog, contentLength, totalRead, startTime);
                        int read;
                        while ((read = input.read(buffer)) >= 0) {
                            output.write(buffer, 0, read);
                            totalRead += read;
                            updateManualDownloadProgress(progressDialog, contentLength, totalRead, startTime);
                        }
                    }

                    Files.move(tempFile.toPath(), targetFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                    usedFileNames.add(targetFile.getName());
                } finally {
                    if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
                        writeErrorLog("删除离线说明文档临时文件失败：" + tempFile.getAbsolutePath());
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        } catch (IOException e) {
            OfflineFormFileHelper.deleteManualPdfFile(context, patternId);
            throw e;
        }
    }

    private String getFileNameFromUrl(String url, int index) {
        try {
            String path = new URL(url).getPath();
            int separatorIndex = path == null ? -1 : path.lastIndexOf('/');
            String fileName = separatorIndex >= 0 ? path.substring(separatorIndex + 1) : path;
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
            return StringUtils.isNotBlank(fileName) ? fileName : "document-" + (index + 1) + ".pdf";
        } catch (Exception ignored) {
            return "document-" + (index + 1) + ".pdf";
        }
    }

    private String makeUniqueFileName(String fileName, List<String> usedFileNames) {
        String normalized = fileName == null ? "" : fileName.replace('\\', '/');
        int separatorIndex = normalized.lastIndexOf('/');
        if (separatorIndex >= 0) {
            normalized = normalized.substring(separatorIndex + 1);
        }
        if (StringUtils.isNullOrBlank(normalized) || ".".equals(normalized) || "..".equals(normalized)) {
            normalized = "document.pdf";
        }

        String baseName = normalized;
        String extension = "";
        int dotIndex = normalized.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = normalized.substring(0, dotIndex);
            extension = normalized.substring(dotIndex);
        }
        String candidate = normalized;
        int suffix = 2;
        while (usedFileNames.contains(candidate)) {
            candidate = baseName + " (" + suffix++ + ")" + extension;
        }
        return candidate;
    }

    private String resolveManualPdfUrl(String manualPdfUrl, String currentUrl) throws IOException {
        return StringUtils.isNotBlank(currentUrl)
                ? new URL(new URL(currentUrl), manualPdfUrl).toString()
                : new URL(manualPdfUrl).toString();
    }

    private String readSignatureDataUrl(Context context, String patternId) {
        File signatureFile = OfflineFormFileHelper.getSignatureFile(context, patternId);
        if (!signatureFile.exists()) {
            return "";
        }

        try (InputStream input = new FileInputStream(signatureFile);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = input.read(buffer)) >= 0) {
                output.write(buffer, 0, read);
            }
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(output.toByteArray());
        } catch (IOException e) {
            writeErrorLog("读取离线表单签名失败：" + e);
            return "";
        }
    }

    private ProgressDialog showManualDownloadDialog(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return createManualDownloadDialog(context);
        }

        AtomicReference<ProgressDialog> dialogRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        runOnUiThread(() -> {
            dialogRef.set(createManualDownloadDialog(context));
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return dialogRef.get();
    }

    private ProgressDialog createManualDownloadDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.offline_dialog_manual_download_title));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    private void updateManualDownloadProgress(ProgressDialog progressDialog, int contentLength, long totalRead, long startTime) {
        if (progressDialog == null) {
            return;
        }
        runOnUiThread(() -> {
            long elapsedMillis = Math.max(1, System.currentTimeMillis() - startTime);
            double speedBytesPerSecond = totalRead * 1000.0 / elapsedMillis;
            if (contentLength > 0) {
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(contentLength);
                progressDialog.setProgress((int) Math.min(totalRead, contentLength));
                progressDialog.setProgressNumberFormat(formatBytesAsMb(totalRead) + "/" + formatBytesAsMb(contentLength));
            } else {
                progressDialog.setIndeterminate(true);
            }
            progressDialog.setMessage(formatBytesAsMb(totalRead) + "，" + formatBytesAsMb(speedBytesPerSecond) + "/s");
        });
    }

    private String formatBytesAsMb(double bytes) {
        return String.format(java.util.Locale.CHINA, "%.2f MB", bytes / 1024.0 / 1024.0);
    }

    private void finishManualDownload(ProgressDialog progressDialog, CallbackParams callbackParams, String errorLog) {
        finishAsyncOperation(progressDialog, callbackParams, errorLog);
    }

    private void finishAddPattern(ProgressDialog progressDialog, CallbackParams callbackParams, String errorLog) {
        finishAsyncOperation(progressDialog, callbackParams, errorLog);
    }

    private void finishAsyncOperation(ProgressDialog progressDialog, CallbackParams callbackParams, String errorLog) {
        runOnUiThread(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (errorLog != null) {
                writeErrorLog(errorLog);
            }
            callback(callbackParams);
        });
    }

    private List<String> buildDefaultDisplayColumns(OfflineFormDefinition definition) {
        List<String> displayColumns = new ArrayList<>();
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(definition)) {
            displayColumns.add(item.getId());
        }
        return displayColumns;
    }
}
