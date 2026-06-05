package com.huozige.lab.container.offlineform.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class OfflineFormRecord {
    // 单条填报记录唯一标识，也是记录文件名称。
    private String recordId;
    // 该记录所属表单定义的业务唯一标识。
    private String patternId;
    // 填报时使用的表单定义版本号。
    private String schemaVersion;
    // 当前记录状态，第一阶段提交后直接保存为 submitted。
    private String status;
    // 记录创建时间戳。
    private long createdAt;
    // 记录最后更新时间戳。
    private long updatedAt;
    // 字段值集合，key 为表单项 id，value 为用户填写值。
    private Map<String, String> values;

    public OfflineFormRecord(String recordId, String patternId, String schemaVersion, String status, long createdAt, long updatedAt, Map<String, String> values) {
        this.recordId = recordId;
        this.patternId = patternId;
        this.schemaVersion = schemaVersion;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.values = values;
    }

    public static OfflineFormRecord createSubmitted(String patternId, String schemaVersion, Map<String, String> values) {
        long now = System.currentTimeMillis();
        return new OfflineFormRecord(createRecordId(), patternId, schemaVersion, "submitted", now, now, values);
    }

    public String getRecordId() { return recordId == null ? "" : recordId; }
    public String getPatternId() { return patternId == null ? "" : patternId; }
    public String getSchemaVersion() { return schemaVersion == null ? "" : schemaVersion; }
    public String getStatus() { return status == null ? "" : status; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public Map<String, String> getValues() { return values == null ? new HashMap<>() : values; }

    private static String createRecordId() {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(new Date());
        return time + "_" + UUID.randomUUID().toString();
    }
}
