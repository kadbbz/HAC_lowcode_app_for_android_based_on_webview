package com.huozige.lab.container.offlineform.util;

import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 维护记录中的"字段级更新时间"（{@code fieldUpdateTimes}）。
 *
 * <p>仅 App 内部使用：key 结构由 App 自行维护，不构成对外契约。
 * 结构"与 values 平行"：普通字段为 ODate 数值；列表字段为与行一一对应的
 * {@code List<Map<子字段id, 时间>>}；嵌套列表按同样规则递归。</p>
 *
 * <p>打点规则：以"上次落盘值"为基准，仅对值发生变化的字段打当前时间
 * （{@code A→B→A} 会在 B 处留痕）。装载初值不算变更。</p>
 */
public final class OfflineFormFieldTimes {

    private OfflineFormFieldTimes() {
    }

    /** 采集当前表单的"路径 → 值"快照，用于与上次落盘值做差异比较（仅进程内使用）。 */
    public static Map<String, String> collectValues(OfflineFormDefinition definition) {
        Map<String, String> values = new HashMap<>();
        if (definition == null) {
            return values;
        }
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(definition)) {
            collectItemValues(item, values, "");
        }
        return values;
    }

    private static void collectItemValues(BaseFormItem item, Map<String, String> values, String pathPrefix) {
        if (item == null) {
            return;
        }
        if (item instanceof ListFormItem) {
            List<ListFormItem.ListFormItemRow> rows = ((ListFormItem) item).getRows();
            if (rows == null) {
                return;
            }
            for (int i = 0; i < rows.size(); i++) {
                String rowPrefix = pathPrefix + item.getId() + "[" + i + "].";
                for (BaseFormItem sub : OfflineFormDefinitionFlattener.flattenFields(rows.get(i).getNodes())) {
                    collectItemValues(sub, values, rowPrefix);
                }
            }
            return;
        }
        values.put(pathPrefix + item.getId(), safe(item.getValue()));
    }

    /**
     * 依据"当前值 vs 上次落盘值"刷新 {@code record.fieldUpdateTimes}，并返回本次的路径快照。
     */
    public static Map<String, String> refresh(OfflineFormRecord record,
                                              OfflineFormDefinition definition,
                                              Map<String, String> previousValues) {
        Map<String, String> current = new HashMap<>();
        if (record == null) {
            return current;
        }

        Map<String, String> safePrevious = previousValues == null ? Collections.emptyMap() : previousValues;
        Map<String, Object> previousTimes = record.getFieldUpdateTimes() == null
                ? Collections.emptyMap() : record.getFieldUpdateTimes();
        Map<String, Object> times = new HashMap<>();

        if (definition != null) {
            for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(definition)) {
                if (item == null) {
                    continue;
                }
                times.put(item.getId(), buildItemTime(item, current, safePrevious, previousTimes.get(item.getId()), ""));
            }
        }

        record.setFieldUpdateTimes(times);
        return current;
    }

    private static Object buildItemTime(BaseFormItem item,
                                        Map<String, String> current,
                                        Map<String, String> previous,
                                        Object previousTime,
                                        String pathPrefix) {
        if (item instanceof ListFormItem) {
            List<?> previousRows = previousTime instanceof List ? (List<?>) previousTime : null;
            List<Object> rowTimes = new ArrayList<>();
            List<ListFormItem.ListFormItemRow> rows = ((ListFormItem) item).getRows();
            if (rows != null) {
                for (int i = 0; i < rows.size(); i++) {
                    Map<String, Object> cellTimes = new HashMap<>();
                    Map<?, ?> previousRow = previousRows != null && i < previousRows.size()
                            ? asMap(previousRows.get(i)) : null;
                    String rowPrefix = pathPrefix + item.getId() + "[" + i + "].";
                    for (BaseFormItem sub : OfflineFormDefinitionFlattener.flattenFields(rows.get(i).getNodes())) {
                        if (sub == null) {
                            continue;
                        }
                        Object previousSubTime = previousRow == null ? null : previousRow.get(sub.getId());
                        cellTimes.put(sub.getId(), buildItemTime(sub, current, previous, previousSubTime, rowPrefix));
                    }
                    rowTimes.add(cellTimes);
                }
            }
            return rowTimes;
        }

        String path = pathPrefix + item.getId();
        String value = safe(item.getValue());
        current.put(path, value);
        if (!Objects.equals(safe(previous.get(path)), value)) {
            return ODateUtils.now();
        }
        return previousTime;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static Map<?, ?> asMap(Object value) {
        return value instanceof Map ? (Map<?, ?>) value : null;
    }
}
