package com.huozige.lab.container.offlineform.formitem.list;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.List;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

/**
 * 列表表单项：每个列表项只占一行（序号徽标 + 项标题 + 首字段值摘要 + 右箭头），点击行从底部
 * 弹出 {@link ListItemEditSheet} 编辑该项全部子字段；底部保留虚线「添加」瓦片。
 *
 * <p>添加后自动打开新行的编辑抽屉；抽屉取消且该行仍为空时自动回滚删除空行。
 * 删除入口在抽屉右上角，仍走二次确认，并受最少/最多项数限制。</p>
 */
public class ListViewHolder extends BaseViewHolder {
    private static final int ROW_HEIGHT_DP = 56;
    private static final int ROW_START_INSET_DP = 12;

    private final LinearLayout rootLayout;
    private final TextView titleView;
    private final TextView requiredView;
    private final TextView countView;
    private final LinearLayout rowsLayout;
    private final TextView hintView;
    private final LinearLayout addButtonLayout;
    private final TextView addButton;
    private final TextView errorView;

    private ListFormItem listItem;
    // 与行一一对应的子字段更新时间；由上层在 bind 前下发。
    private List<Map<String, Object>> rowUpdateTimes;
    private ListItemEditSheet editSheet;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView;
        titleView = new TextView(itemView.getContext());
        requiredView = new TextView(itemView.getContext());
        countView = new TextView(itemView.getContext());
        rowsLayout = new LinearLayout(itemView.getContext());
        hintView = new TextView(itemView.getContext());
        addButtonLayout = new LinearLayout(itemView.getContext());
        addButton = new TextView(itemView.getContext());
        errorView = new TextView(itemView.getContext());
        setupView();
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                dismissEditSheet();
            }
        });
    }

    public static View createView(ViewGroup parent) {
        LinearLayout root = new LinearLayout(parent.getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        root.setPadding(
                parent.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_horizontal),
                parent.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_vertical),
                parent.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_horizontal),
                parent.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_vertical));
        root.setMinimumHeight(
                parent.getResources().getDimensionPixelSize(R.dimen.offline_form_item_min_height));
        return root;
    }

    private void setupView() {
        LinearLayout titleRow = new LinearLayout(itemView.getContext());
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);

        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                itemView.getResources().getDimensionPixelSize(R.dimen.offline_form_field_label_text_size));
        titleView.setTextColor(itemView.getContext().getColor(R.color.offline_form_sec));
        titleRow.addView(titleView, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        requiredView.setText("*");
        requiredView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                itemView.getResources().getDimensionPixelSize(R.dimen.offline_form_field_label_text_size));
        requiredView.setTextColor(itemView.getContext().getColor(R.color.offline_form_danger));
        requiredView.setTypeface(null, Typeface.BOLD);
        requiredView.setVisibility(View.GONE);
        titleRow.addView(requiredView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        countView.setTextSize(12f);
        countView.setTextColor(itemView.getContext().getColor(R.color.offline_form_sec));
        LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        countParams.setMarginStart(dp(itemView.getContext(), 8));
        titleRow.addView(countView, countParams);

        rowsLayout.setOrientation(LinearLayout.VERTICAL);

        hintView.setTextSize(11.5f);
        hintView.setTextColor(itemView.getContext().getColor(R.color.offline_form_muted));
        hintView.setVisibility(View.GONE);

        // 虚线整宽容器 + 居中的「+ 添加」：icon 与文字作为一组居中，而不是图标贴左侧。
        addButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        addButtonLayout.setGravity(Gravity.CENTER);
        addButtonLayout.setBackgroundResource(R.drawable.offline_list_add_button_bg);
        addButtonLayout.setContentDescription(itemView.getContext().getString(R.string.offline_cd_add_list_row));
        addButtonLayout.setOnClickListener(v -> addRow());

        AppCompatImageView addIcon = new AppCompatImageView(itemView.getContext());
        addIcon.setImageResource(R.drawable.ic_offline_plus_small);
        addIcon.setColorFilter(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        addIcon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        addButtonLayout.addView(addIcon, new LinearLayout.LayoutParams(
                dp(itemView.getContext(), 14), dp(itemView.getContext(), 14)));

        addButton.setTextSize(13f);
        addButton.setTypeface(null, Typeface.BOLD);
        addButton.setTextColor(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        addButton.setText(R.string.offline_button_add_list_item);
        LinearLayout.LayoutParams addTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addTextParams.setMarginStart(dp(itemView.getContext(), 6));
        addButtonLayout.addView(addButton, addTextParams);

        errorView.setTextColor(itemView.getContext().getColor(R.color.offline_form_danger));
        errorView.setTextSize(12f);
        errorView.setVisibility(View.GONE);

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.bottomMargin =
                itemView.getResources().getDimensionPixelSize(R.dimen.offline_form_item_label_gap);
        rootLayout.addView(titleRow, titleParams);

        rootLayout.addView(rowsLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        hintParams.topMargin = dp(itemView.getContext(), 9);
        rootLayout.addView(hintView, hintParams);

        LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(itemView.getContext(), 44));
        addParams.topMargin = dp(itemView.getContext(), 10);
        rootLayout.addView(addButtonLayout, addParams);

        LinearLayout.LayoutParams errorParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        errorParams.topMargin = dp(itemView.getContext(), 6);
        rootLayout.addView(errorView, errorParams);
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    /** 下发与行一一对应的子字段更新时间，需在 {@link #bind} 之前调用。 */
    public void setRowUpdateTimes(List<Map<String, Object>> rowUpdateTimes) {
        this.rowUpdateTimes = rowUpdateTimes;
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        listItem = (ListFormItem) item;
        titleView.setText(listItem.getTitle());
        requiredView.setVisibility(listItem.isRequired() ? View.VISIBLE : View.GONE);
        addButton.setText(resolveAddButtonText());
        renderRows();
        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (listItem != null && listItem.getErrorMessage() != null) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(listItem.getErrorMessage());
        } else {
            errorView.setVisibility(View.GONE);
        }
    }

    private void addRow() {
        if (listItem == null) {
            return;
        }
        if (listItem.addRow() == null) {
            Toast.makeText(itemView.getContext(),
                    itemView.getContext().getString(R.string.offline_list_toast_max, listItem.getMaxCount()),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        listItem.clearError();
        renderRows();
        updateErrorState();
        notifyOuterFieldChanged();
        int newIndex = listItem.getRowCount() - 1;
        // 添加后直接进入新行的编辑抽屉，减少一次点击。
        openRowSheet(newIndex, true);
    }

    private void renderRows() {
        rowsLayout.removeAllViews();
        if (listItem == null) {
            return;
        }
        List<ListFormItem.ListFormItemRow> rows = listItem.getRows();
        for (int i = 0; i < rows.size(); i++) {
            if (i > 0) {
                rowsLayout.addView(createDivider());
            }
            rowsLayout.addView(createRowView(rows.get(i), i));
        }
        updateStatusViews();
    }

    /** 刷新右上角数量、添加按钮可见性与底部提示（空态 / 达上限 / 不足最小项数）。 */
    private void updateStatusViews() {
        int count = listItem.getRows().size();
        int maxCount = listItem.getMaxCount();
        int minCount = listItem.getMinCount();
        if (maxCount > 0) {
            countView.setText(count >= maxCount
                    ? itemView.getContext().getString(R.string.offline_list_count_full, count, maxCount)
                    : itemView.getContext().getString(R.string.offline_list_count, count, maxCount));
        } else {
            countView.setText(itemView.getContext().getString(R.string.offline_list_count_unlimited, count));
        }

        addButtonLayout.setVisibility(listItem.canAddRow() ? View.VISIBLE : View.GONE);

        if (count == 0) {
            hintView.setVisibility(View.VISIBLE);
            hintView.setText(R.string.offline_list_empty_hint);
        } else if (maxCount > 0 && count >= maxCount) {
            hintView.setVisibility(View.VISIBLE);
            hintView.setText(R.string.offline_list_hint_max_reached);
        } else if (minCount > 0 && count < minCount) {
            hintView.setVisibility(View.VISIBLE);
            hintView.setText(itemView.getContext().getString(R.string.offline_list_hint_min, minCount));
        } else {
            hintView.setVisibility(View.GONE);
        }
    }

    private View createRowView(ListFormItem.ListFormItemRow row, int index) {
        LinearLayout rowLayout = new LinearLayout(itemView.getContext());
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(Gravity.CENTER_VERTICAL);
        rowLayout.setMinimumHeight(dp(itemView.getContext(), ROW_HEIGHT_DP));
        rowLayout.setPadding(dp(itemView.getContext(), ROW_START_INSET_DP), 0,
                dp(itemView.getContext(), 4), 0);
        rowLayout.setBackgroundResource(resolveSelectableItemBackground());
        rowLayout.setContentDescription(itemView.getContext()
                .getString(R.string.offline_list_item_summary_empty));
        rowLayout.setOnClickListener(v -> openRowSheet(index, false));

        TextView indexView = new TextView(itemView.getContext());
        indexView.setText(String.valueOf(index + 1));
        indexView.setTextSize(11f);
        indexView.setTypeface(null, Typeface.BOLD);
        indexView.setTextColor(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        indexView.setGravity(Gravity.CENTER);
        indexView.setBackgroundResource(R.drawable.offline_list_index_badge_bg);
        indexView.setMinWidth(dp(itemView.getContext(), 20));
        indexView.setPadding(dp(itemView.getContext(), 5), 0, dp(itemView.getContext(), 5), 0);
        rowLayout.addView(indexView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dp(itemView.getContext(), 20)));

        TextView rowTitle = new TextView(itemView.getContext());
        String firstFieldValue = formatRowValue(row);
        // 有内容时直接用第一个子字段的填写内容覆盖项标题；未填写时用「项标题」兜底。
        rowTitle.setText(firstFieldValue.isEmpty() ? resolveRowTitle(index) : firstFieldValue);
        rowTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                itemView.getResources().getDimensionPixelSize(R.dimen.offline_form_field_value_text_size));
        rowTitle.setTextColor(itemView.getContext().getColor(
                firstFieldValue.isEmpty() ? R.color.offline_form_muted : R.color.offline_form_ink));
        rowTitle.setSingleLine(true);
        rowTitle.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams rowTitleParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1);
        rowTitleParams.setMarginStart(dp(itemView.getContext(), 10));
        rowLayout.addView(rowTitle, rowTitleParams);

        TextView summaryView = new TextView(itemView.getContext());
        summaryView.setText(formatRowStatus(row));
        summaryView.setTextSize(12f);
        summaryView.setTextColor(itemView.getContext().getColor(R.color.offline_form_muted));
        summaryView.setSingleLine(true);
        summaryView.setEllipsize(TextUtils.TruncateAt.END);
        summaryView.setMaxWidth(dp(itemView.getContext(), 140));
        LinearLayout.LayoutParams summaryParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        summaryParams.setMarginStart(dp(itemView.getContext(), 8));
        rowLayout.addView(summaryView, summaryParams);

        AppCompatImageView chevron = new AppCompatImageView(itemView.getContext());
        chevron.setImageResource(R.drawable.ic_offline_chevron_right_24);
        chevron.setColorFilter(itemView.getContext().getColor(R.color.offline_form_muted));
        chevron.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        chevron.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        LinearLayout.LayoutParams chevronParams = new LinearLayout.LayoutParams(
                dp(itemView.getContext(), 22), dp(itemView.getContext(), 22));
        chevronParams.setMarginStart(dp(itemView.getContext(), 4));
        rowLayout.addView(chevron, chevronParams);
        return rowLayout;
    }

    /** 行首字段内容：用于覆盖列表项标题；未填写时返回空串（由标题兜底）。 */
    private String formatRowValue(ListFormItem.ListFormItemRow row) {
        List<BaseFormItem> fields = OfflineFormDefinitionFlattener.flattenFields(row.getNodes());
        if (fields.isEmpty()) {
            return "";
        }
        BaseFormItem firstField = fields.get(0);
        String value = firstField.getValue();
        if (StringUtils.isNullOrBlank(value) || "[]".equals(value.trim())
                || "null".equalsIgnoreCase(value.trim())
                || "false".equalsIgnoreCase(value.trim())) {
            return "";
        }
        if (value.startsWith("[")) {
            try {
                int count = com.alibaba.fastjson.JSON.parseArray(value).size();
                return itemView.getContext().getString(R.string.offline_list_count_unlimited, count);
            } catch (RuntimeException ignored) {
                return value;
            }
        }
        if ("true".equalsIgnoreCase(value)) {
            return itemView.getContext().getString(R.string.offline_switch_on);
        }
        if ("false".equalsIgnoreCase(value)) {
            return itemView.getContext().getString(R.string.offline_switch_off);
        }
        return value;
    }

    /** 行右侧填写状态：有任一子字段内容为「已填写」，否则「未填写」。 */
    private String formatRowStatus(ListFormItem.ListFormItemRow row) {
        return isRowEmpty(row)
                ? itemView.getContext().getString(R.string.offline_list_item_summary_empty)
                : itemView.getContext().getString(R.string.offline_list_item_status_filled);
    }

    private View createDivider() {
        View divider = new View(itemView.getContext());
        divider.setBackgroundResource(R.drawable.offline_form_row_divider);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(itemView.getContext(), 1)));
        return divider;
    }

    private int resolveSelectableItemBackground() {
        TypedValue outValue = new TypedValue();
        if (itemView.getContext().getTheme().resolveAttribute(
                android.R.attr.selectableItemBackground, outValue, true)) {
            return outValue.resourceId;
        }
        return 0;
    }

    private void openRowSheet(int index, boolean newRow) {
        if (listItem == null || index < 0 || index >= listItem.getRowCount()) {
            return;
        }
        dismissEditSheet();
        Map<String, Object> times = rowUpdateTimes != null && index < rowUpdateTimes.size()
                ? rowUpdateTimes.get(index) : null;
        editSheet = ListItemEditSheet.show(itemView.getContext(), listItem, index,
                resolveRowTitle(index), times, getFieldChangedCallback(),
                new ListItemEditSheet.Listener() {
                    @Override
                    public void onClosed(int rowIndex, boolean committed) {
                        boolean removed = false;
                        // 只有「新增且取消」的空行才回滚删除；已有空行保持原样。
                        if (newRow && !committed && rowIndex < listItem.getRowCount()
                                && isRowEmpty(listItem.getRows().get(rowIndex))) {
                            removed = listItem.removeRow(rowIndex);
                        }
                        renderRows();
                        updateErrorState();
                        notifyOuterFieldChanged();
                        if (!removed && rowIndex < listItem.getRowCount()) {
                            scrollToRow(rowIndex);
                        }
                    }

                    @Override
                    public void onDeleteConfirmed(int rowIndex) {
                        if (listItem.removeRow(rowIndex)) {
                            renderRows();
                            updateErrorState();
                            notifyOuterFieldChanged();
                        }
                    }
                });
    }

    private void dismissEditSheet() {
        if (editSheet != null && editSheet.isShowing()) {
            editSheet.dismiss();
        }
        editSheet = null;
    }

    private boolean isRowEmpty(ListFormItem.ListFormItemRow row) {
        for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
            String value = field.getValue();
            if (StringUtils.isNullOrBlank(value)) {
                continue;
            }
            String trimmed = value.trim();
            // 开关 / 勾选默认 false 不算内容；空数组 / null 也不算。
            if ("[]".equals(trimmed) || "null".equalsIgnoreCase(trimmed)
                    || "false".equalsIgnoreCase(trimmed)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private void scrollToRow(int index) {
        if (index < 0 || index >= rowsLayout.getChildCount()) {
            return;
        }
        // 行之间夹着分隔线，实际下标为 2 * index。
        View rowView = rowsLayout.getChildAt(index * 2);
        rowView.post(() -> rowView.requestRectangleOnScreen(
                new Rect(0, 0, rowView.getWidth(), rowView.getHeight())));
    }

    private String resolveAddButtonText() {
        if (listItem != null && !StringUtils.isNullOrBlank(listItem.getAddButtonText())) {
            return listItem.getAddButtonText();
        }
        return itemView.getContext().getString(R.string.offline_button_add_list_item);
    }

    private String resolveRowTitle(int index) {
        if (listItem != null && !StringUtils.isNullOrBlank(listItem.getItemTitle())) {
            return listItem.getItemTitle() + " " + (index + 1);
        }
        return itemView.getContext().getString(R.string.offline_text_list_item_title, index + 1);
    }
}
