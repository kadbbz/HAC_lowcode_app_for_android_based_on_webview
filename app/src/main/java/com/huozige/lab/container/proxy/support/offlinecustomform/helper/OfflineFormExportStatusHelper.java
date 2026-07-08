package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;

import java.util.List;

public class OfflineFormExportStatusHelper {

    public static boolean hasUnexportedRecords(Context context, OfflineFormDefinitionIndexItem item) {
        Status status = readStatus(context, item);
        return status.submittedCount > 0 || status.totalCount == 0;
    }

    public static boolean isExported(Context context, OfflineFormDefinitionIndexItem item) {
        Status status = readStatus(context, item);
        return status.submittedCount == 0 && status.exportedCount > 0;
    }

    public static String buildProjectMetaText(Context context, OfflineFormDefinitionIndexItem item) {
        String projectMeta = context.getString(R.string.offline_text_project_meta, item.getPatternId());
        return projectMeta + "\n" + readStatus(context, item).getDisplayText();
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

        String getDisplayText() {
            if (submittedCount > 0) {
                return "\u672a\u5bfc\u51fa";
            }
            if (exportedCount > 0) {
                return "\u5df2\u5bfc\u51fa";
            }
            return "\u672a\u5bfc\u51fa";
        }
    }
}
