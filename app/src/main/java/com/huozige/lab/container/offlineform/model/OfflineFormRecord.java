package com.huozige.lab.container.offlineform.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormRecord {
    // 单条填报记录唯一标识，也是记录文件名称。
    private String recordId = "";
    // 该记录所属表单定义的业务唯一标识。
    private String patternId = "";
    // 填报时使用的表单定义版本号。
    private String schemaVersion = "";
    // 当前记录状态，包含草稿、已提交、已导出。
    private OfflineFormRecordStatus status = OfflineFormRecordStatus.DRAFT;
    // 记录创建时间戳。
    private long createdAt;
    // 记录最后更新时间戳。
    private long updatedAt;
    // 字段值集合，key 为表单项 id，value 为用户填写值。
    private Map<String, String> values = new HashMap<>();

    public static OfflineFormRecord createSubmitted(String patternId, String schemaVersion, Map<String, String> values) {
        long now = System.currentTimeMillis();
        return new OfflineFormRecord(createRecordId(), patternId, schemaVersion, OfflineFormRecordStatus.SUBMITTED, now, now, values);
    }

    private static String createRecordId() {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(new Date());
        return time + "_" + UUID.randomUUID().toString();
    }
}
