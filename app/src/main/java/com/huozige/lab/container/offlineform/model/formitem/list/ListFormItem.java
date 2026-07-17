package com.huozige.lab.container.offlineform.model.formitem.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemValueHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormNodeFactory;
import com.huozige.lab.container.offlineform.model.PatternNodeInput;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListFormItem extends BaseFormItem {
    private int minCount;
    private int maxCount;
    private int defaultCount;
    private String addButtonText = "";
    private String itemTitle = "";
    private String patternId = "";
    private List<PatternNodeInput> templateInputs = new ArrayList<>();
    private List<OfflineFormNode> templateNodes = new ArrayList<>();
    private List<ListFormItemRow> rows = new ArrayList<>();

    public ListFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    @Override
    public String getValue() {
        JSONArray array = new JSONArray();
        for (ListFormItemRow row : rows) {
            JSONObject rowValue = new JSONObject();
            for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
                rowValue.put(field.getId(), field.getValue());
            }
            array.add(rowValue);
        }
        return array.toJSONString();
    }

    @Override
    public boolean isEmpty() {
        return rows == null || rows.isEmpty();
    }

    public void setValue(String value) {
        rows = new ArrayList<>();
        if (!StringUtils.isNullOrBlank(value)) {
            JSONArray array = parseRows(value);
            for (int i = 0; i < array.size(); i++) {
                JSONObject rowValue = array.getJSONObject(i);
                ListFormItemRow row = addRow();
                if (row != null && rowValue != null) {
                    applyRowValue(row, rowValue);
                }
            }
        }
        ensureDefaultRows();
        clearError();
    }

    public void ensureDefaultRows() {
        int targetCount = Math.max(0, Math.max(defaultCount, minCount));
        while (rows.size() < targetCount && canAddRow()) {
            rows.add(createRow());
        }
    }

    public boolean canAddRow() {
        return maxCount <= 0 || rows.size() < maxCount;
    }

    public boolean canRemoveRow() {
        return rows.size() > Math.max(0, minCount);
    }

    public ListFormItemRow addRow() {
        if (!canAddRow()) {
            return null;
        }
        ListFormItemRow row = createRow();
        rows.add(row);
        return row;
    }

    public boolean removeRow(int index) {
        if (canRemoveRow() && index >= 0 && index < rows.size()) {
            rows.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && rows.isEmpty()) {
            setErrorMessage("\u8bf7\u6dfb\u52a0" + getTitle());
            return false;
        }
        if (minCount > 0 && rows.size() < minCount) {
            setErrorMessage(getTitle() + "\u81f3\u5c11\u9700\u8981" + minCount + "\u9879");
            return false;
        }
        if (maxCount > 0 && rows.size() > maxCount) {
            setErrorMessage(getTitle() + "\u6700\u591a\u53ea\u80fd" + maxCount + "\u9879");
            return false;
        }

        boolean valid = true;
        for (ListFormItemRow row : rows) {
            for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
                if (!field.validate()) {
                    valid = false;
                }
            }
        }
        if (!valid) {
            setErrorMessage("\u8bf7\u68c0\u67e5" + getTitle());
        }
        return valid;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
        for (ListFormItemRow row : rows) {
            for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
                field.clearError();
            }
        }
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId == null ? "" : patternId;
        for (ListFormItemRow row : rows) {
            OfflineFormItemValueHelper.applyPatternId(
                    OfflineFormDefinitionFlattener.flattenFields(row.getNodes()),
                    this.patternId);
        }
    }

    public int getRowCount() {
        return rows == null ? 0 : rows.size();
    }

    public static JSONArray parseRows(String value) {
        if (StringUtils.isNullOrBlank(value)) {
            return new JSONArray();
        }
        try {
            return JSON.parseArray(value);
        } catch (RuntimeException e) {
            return new JSONArray();
        }
    }

    private ListFormItemRow createRow() {
        List<OfflineFormNode> rowNodes = OfflineFormNodeFactory.cloneNodes(templateNodes);
        OfflineFormItemValueHelper.applyPatternId(OfflineFormDefinitionFlattener.flattenFields(rowNodes), patternId);
        return new ListFormItemRow(rowNodes);
    }

    private void applyRowValue(ListFormItemRow row, JSONObject rowValue) {
        for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
            OfflineFormItemValueHelper.applyValue(field, rowValue.getString(field.getId()));
        }
    }

    @Getter
    public static class ListFormItemRow {
        private final List<OfflineFormNode> nodes;

        ListFormItemRow(List<OfflineFormNode> nodes) {
            this.nodes = nodes == null ? new ArrayList<>() : nodes;
        }
    }
}
