package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;

import java.util.List;

public class OfflineFormExportStatusHelper {

    /** 是否存在未导出的数据：有草稿/待上传，或该表单尚无任何记录（尚无记录视为未导出）。 */
    public static boolean hasUnexportedRecords(Context context, OfflineFormDefinitionIndexItem item) {
        return readStatus(context, item).hasUnexportedData();
    }

    /** 是否已无可导出的数据（口径与列表状态文案一致）。 */
    public static boolean isExported(Context context, OfflineFormDefinitionIndexItem item) {
        return !readStatus(context, item).hasUnexportedData();
    }

    /**
     * 当前"工作记录"（最新一条未导出记录）是否处于未完成（DRAFT）状态。
     * 用于列表项提醒边框：存在碰过但未通过校验的填报单元时提示。
     */
    public static boolean hasUnfinishedWorkingRecord(Context context, OfflineFormDefinitionIndexItem item) {
        if (item == null || item.getPatternId() == null || item.getPatternId().isEmpty()) {
            return false;
        }
        // readRecords 已按 updatedAt 倒序排列，第一条未导出记录即最新工作记录。
        List<OfflineFormRecord> records = OfflineFormFileHelper.readRecords(context, item.getPatternId());
        for (OfflineFormRecord record : records) {
            if (record == null || record.getStatus() == null) {
                continue;
            }
            if (record.getStatus() != OfflineFormRecordStatus.EXPORTED) {
                return record.getStatus() == OfflineFormRecordStatus.DRAFT;
            }
        }
        return false;
    }

    public static String buildProjectMetaText(Context context, OfflineFormDefinitionIndexItem item) {
        String projectMeta = context.getString(R.string.offline_text_project_meta, item.getPatternId());
        return projectMeta + "\n" + readStatus(context, item).getDisplayText();
    }

    public static String buildExportStatusText(Context context, OfflineFormDefinitionIndexItem item) {
        return readStatus(context, item).getDisplayText();
    }

    private static Status readStatus(Context context, OfflineFormDefinitionIndexItem item) {
        Status status = new Status();
        if (item == null || item.getPatternId() == null || item.getPatternId().isEmpty()) {
            return status;
        }

        List<OfflineFormRecord> records = OfflineFormFileHelper.readRecords(context, item.getPatternId());
        for (OfflineFormRecord record : records) {
            if (record == null || record.getStatus() == null) {
                continue;
            }

            if (record.getStatus() == OfflineFormRecordStatus.SUBMITTED) {
                status.submittedCount++;
            } else if (record.getStatus() == OfflineFormRecordStatus.EXPORTED) {
                status.exportedCount++;
            } else if (record.getStatus() == OfflineFormRecordStatus.DRAFT) {
                status.draftCount++;
            }
            status.totalCount++;
        }
        return status;
    }

    private static class Status {
        int totalCount;
        int submittedCount;
        int exportedCount;
        int draftCount;

        /**
         * 统一口径：只要还有未导出数据（草稿或待上传），或该表单尚无任何记录，即视为"未导出"。
         * 列表筛选、状态文案、导出可选性都以此为准，避免三处口径不一致。
         */
        boolean hasUnexportedData() {
            return submittedCount > 0 || draftCount > 0 || totalCount == 0;
        }

        String getDisplayText() {
            return hasUnexportedData() ? "\u672a\u5bfc\u51fa" : "\u5df2\u5bfc\u51fa";
        }
    }
}
