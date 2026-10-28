package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormExportStatusHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class OfflinePlusExportCardAdapter extends RecyclerView.Adapter<OfflinePlusExportCardAdapter.ViewHolder> {
    private static final int CONTENT_PADDING_DP = 16;
    private static final int SELECTED_BACKGROUND_COLOR = 0xFFEAF4FF;

    private List<OfflineFormDefinitionIndexItem> _cardItems;
    private final Context _context;
    private final Set<String> _selectedPatternIds = new HashSet<>();
    private final OnSelectionChangedListener _onSelectionChangedListener;

    public OfflinePlusExportCardAdapter(List<OfflineFormDefinitionIndexItem> cardItems, Context context, OnSelectionChangedListener onSelectionChangedListener) {
        _cardItems = cardItems;
        _context = context;
        _onSelectionChangedListener = onSelectionChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_form_export_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfflineFormDefinitionIndexItem item = _cardItems.get(position);
        boolean exported = OfflineFormExportStatusHelper.isExported(_context, item);
        // 存在未完成（DRAFT）工作记录的表单不允许导出：高亮提示并禁用勾选。
        boolean unfinished = OfflineFormExportStatusHelper.hasUnfinishedWorkingRecord(_context, item);
        boolean selectable = !exported && !unfinished;
        boolean selected = selectable && isSelected(item);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.metaTextView.setText(OfflineFormExportStatusHelper.buildProjectMetaText(_context, item));
        OfflineComputedInfo computed = item.getComputed() == null ? new OfflineComputedInfo() : item.getComputed();
        holder.totalProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_total, computed.getTotalFillItems()));
        holder.filledProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_filled, computed.getFilledFillItems()));
        holder.rateProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_rate,
                String.format(Locale.CHINA, "%.0f%%", computed.getCompletionRate())));
        holder.contentLayout.setPadding(dp(_context, CONTENT_PADDING_DP), holder.contentLayout.getPaddingTop(), holder.contentLayout.getPaddingRight(), holder.contentLayout.getPaddingBottom());
        if (unfinished) {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.cardView.setStrokeColor(_context.getColor(R.color.offline_form_card_warning_stroke));
            holder.cardView.setStrokeWidth(dp(_context, 2));
        } else {
            holder.cardView.setCardBackgroundColor(selected ? SELECTED_BACKGROUND_COLOR : Color.WHITE);
            holder.cardView.setStrokeWidth(0);
        }

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selected);
        holder.checkBox.setEnabled(selectable);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (selectable) {
                setSelected(item, isChecked);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (unfinished) {
                // 高亮的表单不可导出：点击时轻提示原因。
                Toast.makeText(_context, R.string.offline_toast_export_unfinished, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!selectable) {
                return;
            }
            setSelected(item, !isSelected(item));
        });
    }

    @Override
    public int getItemCount() {
        return _cardItems.size();
    }

    public void updateData(List<OfflineFormDefinitionIndexItem> newItems) {
        _cardItems = newItems;
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedPatternIdsInOrder(List<OfflineFormDefinitionIndexItem> orderedItems) {
        ArrayList<String> selectedPatternIds = new ArrayList<>();
        for (OfflineFormDefinitionIndexItem item : orderedItems) {
            if (isSelected(item)) {
                selectedPatternIds.add(item.getPatternId());
            }
        }
        return selectedPatternIds;
    }

    public int getSelectedCount() {
        return _selectedPatternIds.size();
    }

    public boolean areAllSelected(List<OfflineFormDefinitionIndexItem> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        boolean anySelectable = false;
        for (OfflineFormDefinitionIndexItem item : items) {
            if (!isSelectable(item)) {
                continue;
            }
            anySelectable = true;
            if (!isSelected(item)) {
                return false;
            }
        }
        return anySelectable;
    }

    public void setSelected(List<OfflineFormDefinitionIndexItem> items, boolean selected) {
        if (items == null || items.isEmpty()) {
            return;
        }
        boolean changed = false;
        for (OfflineFormDefinitionIndexItem item : items) {
            changed |= setSelectedInternal(item, selected);
        }
        if (changed) {
            notifyDataSetChanged();
            notifySelectionChanged();
        }
    }

    private void setSelected(OfflineFormDefinitionIndexItem item, boolean selected) {
        if (setSelectedInternal(item, selected)) {
            notifyDataSetChanged();
            notifySelectionChanged();
        }
    }

    private boolean setSelectedInternal(OfflineFormDefinitionIndexItem item, boolean selected) {
        if (item == null || item.getPatternId() == null || item.getPatternId().isEmpty()) {
            return false;
        }
        if (selected && !isSelectable(item)) {
            return false;
        }
        if (selected) {
            return _selectedPatternIds.add(item.getPatternId());
        }
        return _selectedPatternIds.remove(item.getPatternId());
    }

    private boolean isSelected(OfflineFormDefinitionIndexItem item) {
        return item != null && _selectedPatternIds.contains(item.getPatternId());
    }

    /** 是否可被选中导出：未导出、且不存在未完成（DRAFT）工作记录。 */
    private boolean isSelectable(OfflineFormDefinitionIndexItem item) {
        if (item == null) {
            return false;
        }
        return !OfflineFormExportStatusHelper.isExported(_context, item)
                && !OfflineFormExportStatusHelper.hasUnfinishedWorkingRecord(_context, item);
    }

    private void notifySelectionChanged() {
        if (_onSelectionChangedListener != null) {
            _onSelectionChangedListener.onSelectionChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView metaTextView;
        TextView totalProgressTextView;
        TextView filledProgressTextView;
        TextView rateProgressTextView;
        CheckBox checkBox;
        LinearLayout contentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            metaTextView = itemView.findViewById(R.id.metaTextView);
            totalProgressTextView = itemView.findViewById(R.id.totalProgressTextView);
            filledProgressTextView = itemView.findViewById(R.id.filledProgressTextView);
            rateProgressTextView = itemView.findViewById(R.id.rateProgressTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
            contentLayout = itemView.findViewById(R.id.contentLayout);
        }
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged();
    }
}
