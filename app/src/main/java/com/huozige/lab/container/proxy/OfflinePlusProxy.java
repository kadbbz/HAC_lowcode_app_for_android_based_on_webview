package com.huozige.lab.container.proxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.R;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.PatternInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.utilities.StringUtils;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFactory;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class OfflinePlusProxy extends AbstractProxy{


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
    public void offlinePlusAddPatternAsync(String input, String manualPdfUrl, String signatureBase64) {
        writeInfoLog("OfflinePlusAddPattern");

        PatternInput inputObj = JSON.parseObject(input, PatternInput.class);

        registryCallbackTicket(inputObj.ticket);

        Context context = this.getWebView().getContext();
        try {
            parseJsonToFile(context, inputObj);
            saveSignature(context, inputObj.patternId, signatureBase64);
        } catch (Exception e) {
            finishAddPattern(null, CallbackParams.error(e.toString()), context.getString(R.string.offline_error_add_pattern_failed, e));
            return;
        }

        if (StringUtils.isNullOrBlank(manualPdfUrl)) {
            OfflineFormFileHelper.deleteManualPdfFile(context, inputObj.patternId);
            finishAddPattern(null, CallbackParams.success("success"), null);
            return;
        }

        String currentUrl = getCurrentUrlOnUiThread();
        ProgressDialog progressDialog = showManualDownloadDialog(context);
        new Thread(() -> {
            try {
                saveManualPdf(context, inputObj.patternId, manualPdfUrl, currentUrl, progressDialog);
                finishAddPattern(progressDialog, CallbackParams.success("success"), null);
            } catch (Exception e) {
                finishAddPattern(progressDialog, CallbackParams.error(e.toString()), context.getString(R.string.offline_error_manual_download_failed, e));
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

        callback(CallbackParams.success(buildExportRecordsResult(projectId)));
    }

    private String buildExportRecordsResult(String projectId) {
        if (projectId == null || projectId.isEmpty()) {
            return "";
        }

        Context context = this.getWebView().getContext();
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        List<OfflineFormRecord> records = filterSubmittedRecords(OfflineFormFileHelper.readRecords(context, projectId));
        result.put("projectId", projectId);
        result.put("records", records);
        result.put("signature", readSignatureDataUrl(context, projectId));
        return result.toJSONString();
    }

    @JavascriptInterface
    public String offlinePlusMarkRecordExported(String projectId, String recordId) {
        writeInfoLog("OfflinePlusMarkRecordExported");

        if (projectId == null || projectId.isEmpty()) {
            return "projectId is empty.";
        }
        if (recordId == null || recordId.isEmpty()) {
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

    private void saveManualPdf(Context context, String patternId, String manualPdfUrl, String currentUrl, ProgressDialog progressDialog) throws IOException {
        OfflineFormFileHelper.deleteManualPdfFile(context, patternId);
        if (StringUtils.isNullOrBlank(manualPdfUrl)) {
            return;
        }

        File targetFile = OfflineFormFileHelper.getManualPdfFile(context, patternId);
        File tempFile = OfflineFormFileHelper.getManualPdfTempFile(context, patternId);

        File parentFile = targetFile.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }

        HttpURLConnection connection = null;
        try {
            String resolvedManualPdfUrl = resolveManualPdfUrl(manualPdfUrl, currentUrl);
            connection = (HttpURLConnection) new URL(resolvedManualPdfUrl).openConnection();
            String cookie = CookieManager.getInstance().getCookie(resolvedManualPdfUrl);
            if (StringUtils.isNotBlank(cookie)) {
                connection.setRequestProperty("Cookie", cookie);
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                throw new IOException(context.getString(R.string.offline_error_manual_download_failed_with_code, responseCode));
            }

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

            if (!tempFile.renameTo(targetFile)) {
                throw new IOException(context.getString(R.string.offline_error_manual_save_failed));
            }
        } catch (IOException e) {
            OfflineFormFileHelper.deleteManualPdfFile(context, patternId);
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String resolveManualPdfUrl(String manualPdfUrl, String currentUrl) throws IOException {
        return StringUtils.isNotBlank(currentUrl)
                ? new URL(new URL(currentUrl), manualPdfUrl).toString()
                : new URL(manualPdfUrl).toString();
    }

    private void saveSignature(Context context, String patternId, String signatureBase64) throws IOException {
        if (StringUtils.isNullOrBlank(signatureBase64)) {
            OfflineFormFileHelper.deleteSignatureFile(context, patternId);
            return;
        }
        OfflineFormFileHelper.writeSignatureBase64(context, patternId, signatureBase64);
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
        return String.format(java.util.Locale.US, "%.2f MB", bytes / 1024.0 / 1024.0);
    }

    private void finishAddPattern(ProgressDialog progressDialog, CallbackParams callbackParams, String errorLog) {
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
