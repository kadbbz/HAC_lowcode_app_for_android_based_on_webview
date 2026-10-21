package com.huozige.lab.container.offlineform.formitem.list;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.List;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

/**
 * 列表表单项：每个列表项渲染为「序号行块」——序号徽标 + 行标题 + 40dp 删除热区，
 * 行内子字段之间用 1dp 分隔线隔开；底部是虚线整宽的「添加」瓦片。
 *
 * <p>删除走二次确认弹窗；达到最大项数隐藏添加入口，达到最小项数禁用删除。</p>
 */
public class ListViewHolder extends BaseViewHolder {
    private static final int ROW_GAP_DP = 8;
    private static final int DELETE_TOUCH_DP = 40;

    private final LinearLayout rootLayout;
    private final TextView titleView;
    private final TextView requiredView;
    private final TextView countView;
    private final LinearLayout rowsLayout;
    private final TextView hintView;
    private final TextView addButton;
    private final TextView errorView;

    private ListFormItem listItem;
    // 与行一一对应的子字段更新时间；由上层在 bind 前下发。
    private List<Map<String, Object>> rowUpdateTimes;

    public ListViewHolder(View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView;
        titleView = new TextView(itemView.getContext());
        requiredView = new TextView(itemView.getContext());
        countView = new TextView(itemView.getContext());
        rowsLayout = new LinearLayout(itemView.getContext());
        hintView = new TextView(itemView.getContext());
        addButton = new TextView(itemView.getContext());
        errorView = new TextView(itemView.getContext());
        setupView();
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

        titleView.setTextSize(12.5f);
        titleView.setTextColor(itemView.getContext().getColor(R.color.offline_form_muted));
        titleRow.addView(titleView, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        requiredView.setText("*");
        requiredView.setTextSize(12.5f);
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

        addButton.setTextSize(13f);
        addButton.setTypeface(null, Typeface.BOLD);
        addButton.setTextColor(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        addButton.setGravity(Gravity.CENTER);
        addButton.setBackgroundResource(R.drawable.offline_list_add_button_bg);
        addButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_offline_plus_small, 0, 0, 0);
        addButton.setCompoundDrawablePadding(dp(itemView.getContext(), 6));
        addButton.setText(R.string.offline_button_add_list_item);
        addButton.setContentDescription(itemView.getContext().getString(R.string.offline_cd_add_list_row));
        addButton.setOnClickListener(v -> addRow());

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
        rootLayout.addView(addButton, addParams);

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
        scrollToLastRow();
    }

    private void scrollToLastRow() {
        if (rowsLayout.getChildCount() == 0) {
            return;
        }
        View lastRow = rowsLayout.getChildAt(rowsLayout.getChildCount() - 1);
        lastRow.post(() -> lastRow.requestRectangleOnScreen(
                new Rect(0, 0, lastRow.getWidth(), lastRow.getHeight())));
    }

    private void confirmDeleteRow(int index) {
        if (listItem == null || !listItem.canRemoveRow()) {
            return;
        }
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_confirm_delete_list_row_title)
                .setMessage(R.string.offline_confirm_delete_list_row_message)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> {
                    if (listItem.removeRow(index)) {
                        renderRows();
                        updateErrorState();
                        notifyOuterFieldChanged();
                    }
                })
                .setNegativeButton(R.string.offline_button_cancel, null)
                .show();
    }

    private void renderRows() {
        rowsLayout.removeAllViews();
        if (listItem == null) {
            return;
        }
        List<ListFormItem.ListFormItemRow> rows = listItem.getRows();
        for (int i = 0; i < rows.size(); i++) {
            rowsLayout.addView(createRowView(rows.get(i), i), createRowLayoutParams(i));
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

        addButton.setVisibility(listItem.canAddRow() ? View.VISIBLE : View.GONE);

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

    private LinearLayout.LayoutParams createRowLayoutParams(int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, index == 0 ? 0 : dp(itemView.getContext(), ROW_GAP_DP), 0, 0);
        return params;
    }

    private View createRowView(ListFormItem.ListFormItemRow row, int index) {
        LinearLayout rowLayout = new LinearLayout(itemView.getContext());
        rowLayout.setOrientation(LinearLayout.VERTICAL);
        int padding = dp(itemView.getContext(), 12);
        rowLayout.setPadding(padding, padding, padding, padding);
        rowLayout.setBackgroundResource(R.drawable.offline_list_row_bg);

        LinearLayout header = new LinearLayout(itemView.getContext());
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView indexView = new TextView(itemView.getContext());
        indexView.setText(String.valueOf(index + 1));
        indexView.setTextSize(11f);
        indexView.setTypeface(null, Typeface.BOLD);
        indexView.setTextColor(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        indexView.setGravity(Gravity.CENTER);
        indexView.setBackgroundResource(R.drawable.offline_list_index_badge_bg);
        indexView.setMinWidth(dp(itemView.getContext(), 20));
        indexView.setPadding(dp(itemView.getContext(), 5), 0, dp(itemView.getContext(), 5), 0);
        header.addView(indexView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dp(itemView.getContext(), 20)));

        TextView rowTitle = new TextView(itemView.getContext());
        rowTitle.setText(resolveRowTitle(index));
        rowTitle.setTextSize(13.5f);
        rowTitle.setTypeface(null, Typeface.BOLD);
        rowTitle.setTextColor(itemView.getContext().getColor(R.color.offline_form_ink));
        rowTitle.setSingleLine(true);
        rowTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams rowTitleParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1);
        rowTitleParams.setMarginStart(dp(itemView.getContext(), 8));
        header.addView(rowTitle, rowTitleParams);

        ImageView deleteAction = new AppCompatImageView(itemView.getContext());
        deleteAction.setImageResource(R.drawable.ic_delete_image);
        deleteAction.setColorFilter(itemView.getContext().getColor(R.color.offline_form_danger));
        deleteAction.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        deleteAction.setPadding(dp(itemView.getContext(), 11), dp(itemView.getContext(), 11),
                dp(itemView.getContext(), 11), dp(itemView.getContext(), 11));
        deleteAction.setContentDescription(itemView.getContext()
                .getString(R.string.offline_cd_delete_list_row, index + 1));
        deleteAction.setBackgroundResource(resolveBorderlessSelectableBackground());
        boolean canRemove = listItem.canRemoveRow();
        deleteAction.setEnabled(canRemove);
        deleteAction.setAlpha(canRemove ? 1f : 0.45f);
        deleteAction.setOnClickListener(v -> confirmDeleteRow(index));
        header.addView(deleteAction, new LinearLayout.LayoutParams(
                dp(itemView.getContext(), DELETE_TOUCH_DP),
                dp(itemView.getContext(), DELETE_TOUCH_DP)));

        rowLayout.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        boolean hasContent = row.getNodes() != null && !row.getNodes().isEmpty();
        if (hasContent) {
            rowLayout.addView(createDividerView(), createDividerLayoutParams());
        }

        LinearLayout content = new LinearLayout(itemView.getContext());
        content.setOrientation(LinearLayout.VERTICAL);
        Map<String, Object> rowTimes = rowUpdateTimes != null && index < rowUpdateTimes.size()
                ? rowUpdateTimes.get(index) : null;
        renderNodes(content, row.getNodes(), 0, rowTimes);
        rowLayout.addView(content, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return rowLayout;
    }

    private int resolveBorderlessSelectableBackground() {
        TypedValue outValue = new TypedValue();
        if (itemView.getContext().getTheme().resolveAttribute(
                android.R.attr.selectableItemBackgroundBorderless, outValue, true)) {
            return outValue.resourceId;
        }
        return 0;
    }

    private View createDividerView() {
        View divider = new View(itemView.getContext());
        divider.setBackgroundColor(itemView.getContext().getColor(R.color.offline_form_line));
        return divider;
    }

    private LinearLayout.LayoutParams createDividerLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(itemView.getContext(), 1));
        params.setMargins(0, dp(itemView.getContext(), 10), 0, dp(itemView.getContext(), 2));
        return params;
    }

    private void renderNodes(LinearLayout parent, List<OfflineFormNode> nodes, int depth, Map<String, Object> rowTimes) {
        if (nodes == null) {
            return;
        }
        int added = 0;
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (depth == 0 && added > 0) {
                // 行内顶层子字段之间用细线分隔，符合“行块”而不是“卡中卡”的语言。
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(itemView.getContext(), 1));
                dividerParams.setMargins(0, dp(itemView.getContext(), 8), 0, dp(itemView.getContext(), 2));
                parent.addView(createDividerView(), dividerParams);
            }
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType()) && node.getField() != null) {
                parent.addView(createFieldView(parent, node, depth, rowTimes), createChildLayoutParams());
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                parent.addView(createTextView(node, depth), createChildLayoutParams());
            } else {
                parent.addView(createGroupView(node, depth, rowTimes), createChildLayoutParams());
            }
            added++;
        }
    }

    private LinearLayout.LayoutParams createChildLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, dp(itemView.getContext(), 6), 0, 0);
        return params;
    }

    private View createFieldView(ViewGroup parent, OfflineFormNode node, int depth, Map<String, Object> rowTimes) {
        BaseFormItem formItem = node.getField();
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(formItem.getItemType());
        BaseViewHolder holder = handler.createEditViewHolder(parent);
        holder.itemView.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, dp(itemView.getContext(), 4));

        String originalTitle = formItem.getTitle();
        if (StringUtils.isNullOrBlank(originalTitle) && !StringUtils.isNullOrBlank(node.getTitle())) {
            formItem.setTitle(node.getTitle());
        }
        holder.bind(formItem, -1);
        holder.setShowFieldUpdateTime(formItem.isShowFieldUpdateTime());
        OfflineFormFieldTimeViews.apply(holder.itemView,
                formItem.isShowFieldUpdateTime() && rowTimes != null ? rowTimes.get(formItem.getId()) : null);
        holder.setOnFieldChanged(getFieldChangedCallback());
        formItem.setTitle(originalTitle);
        return holder.itemView;
    }

    private View createGroupView(OfflineFormNode node, int depth, Map<String, Object> rowTimes) {
        LinearLayout groupLayout = new LinearLayout(itemView.getContext());
        groupLayout.setOrientation(LinearLayout.VERTICAL);
        groupLayout.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, dp(itemView.getContext(), 4));

        TextView groupTitle = new TextView(itemView.getContext());
        groupTitle.setText(node.getTitle());
        groupTitle.setTextSize(13);
        groupTitle.setTypeface(null, Typeface.BOLD);
        groupTitle.setTextColor(itemView.getContext().getColor(R.color.offline_form_brand_deep));
        groupLayout.addView(groupTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        renderNodes(groupLayout, node.getChildren(), depth + 1, rowTimes);
        return groupLayout;
    }

    private View createTextView(OfflineFormNode node, int depth) {
        LinearLayout textLayout = new LinearLayout(itemView.getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, dp(itemView.getContext(), 4));

        TextView title = new TextView(itemView.getContext());
        title.setText(node.getTitle());
        title.setTextSize(13);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(itemView.getContext().getColor(R.color.offline_form_ink));
        TextView content = new TextView(itemView.getContext());
        content.setText(node.getContent());
        content.setTextSize(13);
        content.setTextColor(itemView.getContext().getColor(R.color.offline_form_sec));
        content.setPadding(0, dp(itemView.getContext(), 2), 0, 0);

        textLayout.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textLayout.addView(content, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return textLayout;
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
