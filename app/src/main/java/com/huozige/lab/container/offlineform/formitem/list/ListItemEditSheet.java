package com.huozige.lab.container.offlineform.formitem.list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;
import com.huozige.lab.container.offlineform.util.OfflineFormSheetHeight;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

/**
 * 列表项编辑抽屉：列表项只占一行，点击行后在这里编辑该项的全部子字段。
 *
 * <p>顶部为「取消 / 项标题 + 序号 / 删除」；内容沿用表单字段视图（15sp 标签 / 17sp 值 /
 * 16dp 内缩发丝线）；底部「完成」提交。取消 / 下滑 / 点遮罩关闭都会把本项值回滚到打开前的快照，
 * 新增项若取消后仍为空则由调用方删除空行。</p>
 */
public final class ListItemEditSheet {

    public interface Listener {
        /** 抽屉关闭（提交或取消）。 */
        void onClosed(int rowIndex, boolean committed);

        /** 用户在抽屉里确认删除本项。 */
        void onDeleteConfirmed(int rowIndex);
    }

    private final ListFormItem listItem;
    private final int rowIndex;
    private final Map<String, Object> rowTimes;
    private final Runnable onFieldChanged;
    private final Listener listener;

    private final BottomSheetDialog dialog;
    private final View content;
    private final TextView titleView;
    private final TextView subtitleView;
    private final ImageButton deleteButton;
    private final LinearLayout fieldsLayout;
    private final NestedScrollView scrollView;

    private final Map<String, String> snapshot = new LinkedHashMap<>();
    private boolean committed;
    private boolean deleted;

    private ListItemEditSheet(Context context, ListFormItem listItem, int rowIndex, String title,
                              Map<String, Object> rowTimes, Runnable onFieldChanged, Listener listener) {
        this.listItem = listItem;
        this.rowIndex = rowIndex;
        this.rowTimes = rowTimes;
        this.onFieldChanged = onFieldChanged;
        this.listener = listener;

        dialog = new BottomSheetDialog(context);
        content = LayoutInflater.from(context).inflate(R.layout.offline_form_list_item_sheet, null);
        titleView = content.findViewById(R.id.list_item_sheet_title);
        subtitleView = content.findViewById(R.id.list_item_sheet_subtitle);
        deleteButton = content.findViewById(R.id.list_item_sheet_delete);
        fieldsLayout = content.findViewById(R.id.list_item_sheet_content);
        scrollView = content.findViewById(R.id.list_item_sheet_scroll);

        titleView.setText(title);
        subtitleView.setText(context.getString(R.string.offline_list_item_sheet_subtitle,
                rowIndex + 1, Math.max(1, listItem.getRowCount())));

        boolean canRemove = listItem.canRemoveRow();
        deleteButton.setEnabled(canRemove);
        deleteButton.setAlpha(canRemove ? 1f : 0.4f);
        deleteButton.setOnClickListener(v -> confirmDelete());
        content.findViewById(R.id.list_item_sheet_cancel).setOnClickListener(v -> dialog.dismiss());
        content.findViewById(R.id.list_item_sheet_done).setOnClickListener(v -> {
            committed = true;
            dialog.dismiss();
        });

        snapshotValues();
        dialog.setContentView(content);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(d -> {
            if (deleted) {
                listener.onDeleteConfirmed(rowIndex);
                return;
            }
            if (!committed) {
                restoreSnapshot();
            }
            listener.onClosed(rowIndex, committed);
        });
    }

    public static ListItemEditSheet show(Context context, ListFormItem listItem, int rowIndex, String title,
                                        Map<String, Object> rowTimes, Runnable onFieldChanged,
                                        Listener listener) {
        ListItemEditSheet sheet = new ListItemEditSheet(context, listItem, rowIndex, title,
                rowTimes, onFieldChanged, listener);
        sheet.renderFields();
        if (sheet.dialog.getWindow() != null) {
            sheet.dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
        OfflineFormSheetHeight.showWithBehaviorSizing(context, sheet.dialog, sheet.content, sheet.scrollView);
        return sheet;
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    /** 搜索定位列表子字段时：抽屉打开后滚动到该子字段并闪烁。 */
    public void highlightField(String fieldId) {
        fieldsLayout.post(() -> {
            View target = findFieldViewByTag(fieldsLayout, fieldId);
            if (target == null) {
                return;
            }
            target.requestRectangleOnScreen(new Rect(0, 0, target.getWidth(), target.getHeight()));
            flashForeground(target);
        });
    }

    private View findFieldViewByTag(View view, String fieldId) {
        if (view == null || fieldId == null) {
            return null;
        }
        Object tag = view.getTag(R.id.offline_field_id_tag);
        if (fieldId.equals(tag)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                View found = findFieldViewByTag(group.getChildAt(i), fieldId);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void flashForeground(View itemView) {
        Drawable originalForeground = itemView.getForeground();
        ColorDrawable highlight = new ColorDrawable(itemView.getContext().getColor(R.color.huozige_blue));
        highlight.setAlpha(72);
        itemView.setForeground(highlight);
        ValueAnimator animator = ValueAnimator.ofInt(72, 0);
        animator.setDuration(650);
        animator.addUpdateListener(animation -> highlight.setAlpha((int) animation.getAnimatedValue()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                itemView.setForeground(originalForeground);
            }
        });
        animator.start();
    }

    private ListFormItem.ListFormItemRow currentRow() {
        List<ListFormItem.ListFormItemRow> rows = listItem.getRows();
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            return null;
        }
        return rows.get(rowIndex);
    }

    private void snapshotValues() {
        ListFormItem.ListFormItemRow row = currentRow();
        if (row == null) {
            return;
        }
        for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
            snapshot.put(field.getId(), field.getValue());
        }
    }

    private void restoreSnapshot() {
        ListFormItem.ListFormItemRow row = currentRow();
        if (row == null) {
            return;
        }
        for (BaseFormItem field : OfflineFormDefinitionFlattener.flattenFields(row.getNodes())) {
            field.clearError();
            if (snapshot.containsKey(field.getId())) {
                // 空值恢复为空串，避免列表项被标记为已填。
                String value = snapshot.get(field.getId());
                com.huozige.lab.container.offlineform.formitem.OfflineFormItemValueHelper
                        .applyValue(field, value == null ? "" : value);
            }
        }
    }

    private void confirmDelete() {
        if (!listItem.canRemoveRow()) {
            return;
        }
        new AlertDialog.Builder(content.getContext())
                .setTitle(R.string.offline_confirm_delete_list_row_title)
                .setMessage(R.string.offline_confirm_delete_list_row_message)
                .setPositiveButton(R.string.offline_button_delete, (dialogInterface, which) -> {
                    deleted = true;
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.offline_button_cancel, null)
                .show();
    }

    private void renderFields() {
        fieldsLayout.removeAllViews();
        ListFormItem.ListFormItemRow row = currentRow();
        if (row == null) {
            return;
        }
        renderNodes(row.getNodes(), false);
    }

    private void renderNodes(List<OfflineFormNode> nodes, boolean nested) {
        if (nodes == null) {
            return;
        }
        boolean first = true;
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (node.getField() != null) {
                if (!first) {
                    addDivider();
                }
                addFieldView(node.getField());
                first = false;
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                if (!first) {
                    addDivider();
                }
                addTextView(node);
                first = false;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                if (!first) {
                    addDivider();
                }
                addGroupTitle(node.getTitle());
                renderNodes(node.getChildren(), true);
                first = false;
            }
        }
    }

    private void addFieldView(BaseFormItem formItem) {
        Context context = fieldsLayout.getContext();
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(formItem.getItemType());
        BaseViewHolder holder = handler.createEditViewHolder(fieldsLayout);
        holder.itemView.setTag(R.id.offline_field_id_tag, formItem.getId());

        holder.bind(formItem, -1);
        holder.setShowFieldUpdateTime(formItem.isShowFieldUpdateTime());
        OfflineFormFieldTimeViews.apply(holder.itemView,
                formItem.isShowFieldUpdateTime() && rowTimes != null
                        ? rowTimes.get(formItem.getId()) : null);
        holder.setOnFieldChanged(onFieldChanged);
        fieldsLayout.addView(holder.itemView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addTextView(OfflineFormNode node) {
        Context context = fieldsLayout.getContext();
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(dp(context, 16), dp(context, 10), dp(context, 16), dp(context, 10));

        if (!StringUtils.isNullOrBlank(node.getTitle())) {
            TextView titleView = new TextView(context);
            titleView.setText(node.getTitle());
            titleView.setTextColor(context.getColor(R.color.offline_form_sec));
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                    .getDimensionPixelSize(R.dimen.offline_form_field_label_text_size));
            container.addView(titleView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        TextView contentView = new TextView(context);
        contentView.setText(node.getContent() == null ? "" : node.getContent());
        contentView.setTextColor(context.getColor(R.color.offline_form_ink));
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_value_text_size));
        container.addView(contentView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fieldsLayout.addView(container);
    }

    private void addGroupTitle(String title) {
        if (StringUtils.isNullOrBlank(title)) {
            return;
        }
        Context context = fieldsLayout.getContext();
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(context.getColor(R.color.offline_form_ink));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_label_text_size));
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setPadding(dp(context, 16), dp(context, 10), dp(context, 16), dp(context, 2));
        fieldsLayout.addView(titleView);
    }

    private void addDivider() {
        Context context = fieldsLayout.getContext();
        View divider = new View(context);
        divider.setBackgroundColor(context.getColor(R.color.offline_form_line));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(context, 1));
        params.setMargins(dp(context, 16), 0, dp(context, 16), 0);
        fieldsLayout.addView(divider, params);
    }
}
