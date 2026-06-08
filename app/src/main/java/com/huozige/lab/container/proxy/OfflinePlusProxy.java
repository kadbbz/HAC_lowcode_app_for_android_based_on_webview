package com.huozige.lab.container.proxy;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.PatternInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFactory;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;

import java.util.ArrayList;
import java.util.List;

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
    public void getAllPatternsAsync(String ticket) {

        writeInfoLog("OfflinePlusGetAllPatterns");



    }

    @JavascriptInterface
    public void offlinePlusAddPatternAsync(String input) {
        writeInfoLog("OfflinePlusAddPattern");

        PatternInput inputObj = JSON.parseObject(input, PatternInput.class);

        registryCallbackTicket(inputObj.ticket);

        parseJsonToFile(inputObj);

        callback(CallbackParams.success("success"));

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

    private void parseJsonToFile(PatternInput input) {

        Context context = this.getWebView().getContext();
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

    private List<String> buildDefaultDisplayColumns(OfflineFormDefinition definition) {
        List<String> displayColumns = new ArrayList<>();
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(definition)) {
            displayColumns.add(item.getId());
        }
        return displayColumns;
    }
}
