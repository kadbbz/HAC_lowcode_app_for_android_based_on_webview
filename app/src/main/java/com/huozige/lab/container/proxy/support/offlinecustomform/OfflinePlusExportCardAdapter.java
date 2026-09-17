package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        boolean selected = isSelected(item);

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
        holder.cardView.setCardBackgroundColor(selected ? SELECTED_BACKGROUND_COLOR : Color.WHITE);

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selected);
        holder.checkBox.setEnabled(!exported);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> setSelected(item, isChecked));
        holder.itemView.setOnClickListener(v -> {
            if (exported) {
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
        for (OfflineFormDefinitionIndexItem item : items) {
            if (!isSelected(item)) {
                return false;
            }
        }
        return true;
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
        if (selected && OfflineFormExportStatusHelper.isExported(_context, item)) {
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

    private void notifySelectionChanged() {
        if (_onSelectionChangedListener != null) {
            _onSelectionChangedListener.onSelectionChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
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
            cardView = (CardView) itemView;
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
