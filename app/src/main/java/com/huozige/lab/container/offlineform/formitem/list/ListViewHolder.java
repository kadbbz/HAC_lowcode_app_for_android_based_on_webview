package com.huozige.lab.container.offlineform.formitem.list;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.list.ListFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.List;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class ListViewHolder extends BaseViewHolder {
    private final LinearLayout rootLayout;
    private final TextView titleView;
    private final TextView requiredView;
    private final LinearLayout rowsLayout;
    private final Button addButton;
    private final TextView errorView;

    private ListFormItem listItem;

    public ListViewHolder(View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView;
        titleView = new TextView(itemView.getContext());
        requiredView = new TextView(itemView.getContext());
        rowsLayout = new LinearLayout(itemView.getContext());
        addButton = new Button(itemView.getContext());
        errorView = new TextView(itemView.getContext());
        setupView();
    }

    public static View createView(ViewGroup parent) {
        LinearLayout root = new LinearLayout(parent.getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return root;
    }

    private void setupView() {
        LinearLayout titleRow = new LinearLayout(itemView.getContext());
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);

        titleView.setTextSize(15);
        titleView.setTextColor(itemView.getContext().getColor(android.R.color.black));
        titleView.setTypeface(null, Typeface.BOLD);
        titleRow.addView(titleView, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        requiredView.setText("*");
        requiredView.setTextSize(16);
        requiredView.setTextColor(itemView.getContext().getColor(android.R.color.holo_red_dark));
        titleRow.addView(requiredView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        rowsLayout.setOrientation(LinearLayout.VERTICAL);

        addButton.setAllCaps(false);
        addButton.setTextColor(itemView.getContext().getColor(R.color.huozige_blue));
        addButton.setBackgroundResource(R.drawable.offline_list_add_button_bg);
        addButton.setOnClickListener(v -> addRow());

        errorView.setTextColor(itemView.getContext().getColor(android.R.color.holo_red_dark));
        errorView.setTextSize(12);
        errorView.setPadding(0, dp(itemView.getContext(), 4), 0, 0);

        rootLayout.addView(titleRow, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        rootLayout.addView(rowsLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0, dp(itemView.getContext(), 6), 0, 0);
        rootLayout.addView(addButton, buttonParams);
        rootLayout.addView(errorView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
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
            Toast.makeText(
                    itemView.getContext(),
                    listItem.getTitle() + "\u6700\u591a\u53ea\u80fd" + listItem.getMaxCount() + "\u9879",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        listItem.clearError();
        renderRows();
        updateErrorState();
    }

    private void renderRows() {
        rowsLayout.removeAllViews();
        if (listItem == null) {
            return;
        }
        List<ListFormItem.ListFormItemRow> rows = listItem.getRows();
        for (int i = 0; i < rows.size(); i++) {
            rowsLayout.addView(createRowView(rows.get(i), i), createRowLayoutParams());
        }
        addButton.setEnabled(listItem.canAddRow());
        addButton.setAlpha(listItem.canAddRow() ? 1.0f : 0.45f);
    }

    private LinearLayout.LayoutParams createRowLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, dp(itemView.getContext(), 8), 0, 0);
        return params;
    }

    private View createRowView(ListFormItem.ListFormItemRow row, int index) {
        LinearLayout rowLayout = new LinearLayout(itemView.getContext());
        rowLayout.setOrientation(LinearLayout.VERTICAL);
        rowLayout.setPadding(dp(itemView.getContext(), 10), dp(itemView.getContext(), 8), dp(itemView.getContext(), 10), dp(itemView.getContext(), 10));
        rowLayout.setBackground(createRowBackground());

        LinearLayout header = new LinearLayout(itemView.getContext());
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView rowTitle = new TextView(itemView.getContext());
        rowTitle.setText(resolveRowTitle(index));
        rowTitle.setTextSize(14);
        rowTitle.setTypeface(null, Typeface.BOLD);
        rowTitle.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_title));
        header.addView(rowTitle, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        ImageView deleteAction = new AppCompatImageView(itemView.getContext());
        deleteAction.setImageResource(R.drawable.ic_delete_image);
        deleteAction.setColorFilter(itemView.getContext().getColor(R.color.red));
        deleteAction.setBackgroundResource(R.drawable.offline_list_delete_action_bg);
        deleteAction.setPadding(dp(itemView.getContext(), 6), dp(itemView.getContext(), 6), dp(itemView.getContext(), 6), dp(itemView.getContext(), 6));
        deleteAction.setContentDescription(itemView.getContext().getString(R.string.offline_button_delete));
        deleteAction.setEnabled(listItem.canRemoveRow());
        deleteAction.setAlpha(listItem.canRemoveRow() ? 1.0f : 0.45f);
        deleteAction.setOnClickListener(v -> {
            if (listItem.removeRow(index)) {
                renderRows();
                updateErrorState();
            }
        });
        header.addView(deleteAction, new LinearLayout.LayoutParams(
                dp(itemView.getContext(), 32),
                dp(itemView.getContext(), 32)));
        LinearLayout.LayoutParams deleteParams = (LinearLayout.LayoutParams) deleteAction.getLayoutParams();
        deleteParams.leftMargin = dp(itemView.getContext(), 8);
        deleteAction.setLayoutParams(deleteParams);

        LinearLayout content = new LinearLayout(itemView.getContext());
        content.setOrientation(LinearLayout.VERTICAL);
        renderNodes(content, row.getNodes(), 0);

        rowLayout.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        rowLayout.addView(content, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return rowLayout;
    }

    private void renderNodes(LinearLayout parent, List<OfflineFormNode> nodes, int depth) {
        if (nodes == null) {
            return;
        }
        for (OfflineFormNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType()) && node.getField() != null) {
                parent.addView(createFieldView(parent, node, depth), createChildLayoutParams());
            } else if (OfflineFormNode.TYPE_TEXT.equals(node.getNodeType())) {
                parent.addView(createTextView(node, depth), createChildLayoutParams());
            } else {
                parent.addView(createGroupView(node, depth), createChildLayoutParams());
            }
        }
    }

    private LinearLayout.LayoutParams createChildLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, dp(itemView.getContext(), 6), 0, 0);
        return params;
    }

    private View createFieldView(ViewGroup parent, OfflineFormNode node, int depth) {
        BaseFormItem formItem = node.getField();
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(formItem.getItemType());
        BaseViewHolder holder = handler.createEditViewHolder(parent);
        holder.itemView.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, dp(itemView.getContext(), 4));

        String originalTitle = formItem.getTitle();
        if (StringUtils.isNullOrBlank(originalTitle) && !StringUtils.isNullOrBlank(node.getTitle())) {
            formItem.setTitle(node.getTitle());
        }
        holder.bind(formItem, -1);
        formItem.setTitle(originalTitle);
        return holder.itemView;
    }

    private View createGroupView(OfflineFormNode node, int depth) {
        LinearLayout groupLayout = new LinearLayout(itemView.getContext());
        groupLayout.setOrientation(LinearLayout.VERTICAL);
        groupLayout.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, 0);

        TextView groupTitle = new TextView(itemView.getContext());
        groupTitle.setText(node.getTitle());
        groupTitle.setTextSize(13);
        groupTitle.setTypeface(null, Typeface.BOLD);
        groupTitle.setTextColor(itemView.getContext().getColor(R.color.huozige_blue));
        groupLayout.addView(groupTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        renderNodes(groupLayout, node.getChildren(), depth + 1);
        return groupLayout;
    }

    private View createTextView(OfflineFormNode node, int depth) {
        LinearLayout textLayout = new LinearLayout(itemView.getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setPadding(dp(itemView.getContext(), depth * 12), dp(itemView.getContext(), 4), 0, 0);

        TextView title = new TextView(itemView.getContext());
        title.setText(node.getTitle());
        title.setTextSize(13);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_title));
        TextView content = new TextView(itemView.getContext());
        content.setText(node.getContent());
        content.setTextSize(13);
        content.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_content));
        content.setPadding(0, dp(itemView.getContext(), 2), 0, 0);

        textLayout.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textLayout.addView(content, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return textLayout;
    }

    private GradientDrawable createRowBackground() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(itemView.getContext().getColor(R.color.offline_form_text_bg));
        drawable.setCornerRadius(dp(itemView.getContext(), 6));
        drawable.setStroke(dp(itemView.getContext(), 1), itemView.getContext().getColor(R.color.offline_form_group_bg));
        return drawable;
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
