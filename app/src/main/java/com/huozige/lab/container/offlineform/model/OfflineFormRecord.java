package com.huozige.lab.container.offlineform.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
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
    // 字段级更新时间，仅 App 内部维护，结构"与 values 平行"：
    // 普通字段 -> ODate 数值；列表字段 -> List<Map<子字段id, 时间>>（与行一一对应）。
    private Map<String, Object> fieldUpdateTimes = new HashMap<>();
    // 已二次确认并锁定的字段 id 集合，仅 App 内部维护，不参与导出。
    private List<String> lockedFieldIds = new ArrayList<>();

    public static OfflineFormRecord createSubmitted(String patternId, String schemaVersion, Map<String, String> values) {
        long now = System.currentTimeMillis();
        return new OfflineFormRecord(createRecordId(), patternId, schemaVersion, OfflineFormRecordStatus.SUBMITTED, now, now, values, new HashMap<>(), new ArrayList<>());
    }

    public static OfflineFormRecord createDraft(String patternId, String schemaVersion, Map<String, String> values) {
        long now = System.currentTimeMillis();
        return new OfflineFormRecord(createRecordId(), patternId, schemaVersion, OfflineFormRecordStatus.DRAFT, now, now, values, new HashMap<>(), new ArrayList<>());
    }

    private static String createRecordId() {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.CHINA).format(new Date());
        return time + "_" + UUID.randomUUID().toString();
    }
}
