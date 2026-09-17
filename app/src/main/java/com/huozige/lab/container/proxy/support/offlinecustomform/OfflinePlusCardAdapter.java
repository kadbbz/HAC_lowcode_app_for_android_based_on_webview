package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.CustomFormActivity;
import com.huozige.lab.container.offlineform.OfflineExportedRecordDetailActivity;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormExportStatusHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormCardStyle;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;

import java.util.List;
import java.util.Locale;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

// 历史填报列表的卡片适配器，只负责列表 UI 展示、拖拽排序和进入项目填报记录页。
public class OfflinePlusCardAdapter extends RecyclerView.Adapter<OfflinePlusCardAdapter.ViewHolder> {
    // 普通模式下卡片内容左侧内边距。
    private static final int CONTENT_PADDING_DP = 16;

    // 当前列表展示的表单定义索引项，数据来源于本地索引文件。
    private List<OfflineFormDefinitionIndexItem> _cardItems;
    private Context _context;
    private OnProjectDeletedListener _onProjectDeletedListener;
    // 排序模式：允许外部拖拽调整列表顺序，点击卡片不触发跳转。
    private boolean _sortMode;

    public OfflinePlusCardAdapter(List<OfflineFormDefinitionIndexItem> cardItems, Context context) {
        this(cardItems, context, null);
    }

    public OfflinePlusCardAdapter(List<OfflineFormDefinitionIndexItem> cardItems, Context context, OnProjectDeletedListener onProjectDeletedListener) {
        this._cardItems = cardItems;
        _context = context;
        _onProjectDeletedListener = onProjectDeletedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_form_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfflineFormDefinitionIndexItem item = _cardItems.get(position);

        OfflineFormDefinition definition = readDefinition(item);
        List<OfflineFormCardStyle> styles = definition == null ? null : definition.getCardStyle();
        boolean dynamic = styles != null && !styles.isEmpty();
        holder.dynamicCardLayout.removeAllViews();
        holder.dynamicCardLayout.setVisibility(dynamic ? View.VISIBLE : View.GONE);
        holder.titleTextView.setVisibility(dynamic ? View.GONE : View.VISIBLE);
        holder.descriptionTextView.setVisibility(dynamic ? View.GONE : View.VISIBLE);
        holder.metaTextView.setVisibility(dynamic ? View.GONE : View.VISIBLE);
        holder.progressLayout.setVisibility(dynamic ? View.GONE : View.VISIBLE);
        if (dynamic) renderCardStyle(holder, item, definition, styles);
        else {
            holder.titleTextView.setText(item.getTitle());
            holder.descriptionTextView.setText(item.getDescription());
            holder.metaTextView.setText(OfflineFormExportStatusHelper.buildProjectMetaText(_context, item));
        }
        OfflineComputedInfo computed = item.getComputed() == null ? new OfflineComputedInfo() : item.getComputed();
        holder.totalProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_total, computed.getTotalFillItems()));
        holder.filledProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_filled, computed.getFilledFillItems()));
        holder.rateProgressTextView.setText(_context.getString(
                R.string.offline_text_progress_rate,
                String.format(Locale.CHINA, "%.0f%%", computed.getCompletionRate())));
        boolean exported = OfflineFormExportStatusHelper.isExported(_context, item);
        setContentPaddingStart(holder, dp(_context, CONTENT_PADDING_DP));


        // 排序时点击不进入填报，避免拖拽过程中误打开项目填报页。
        holder.itemView.setOnClickListener(v -> {
            if (_sortMode) {
                return;
            }
            if (exported) {
                return;
            }

            openLatestRecord(item);
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

    public void setSortMode(boolean sortMode) {
        _sortMode = sortMode;
        notifyDataSetChanged();
    }

    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= _cardItems.size() || toPosition >= _cardItems.size()) {
            return;
        }

        OfflineFormDefinitionIndexItem item = _cardItems.remove(fromPosition);
        _cardItems.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void setContentPaddingStart(ViewHolder holder, int paddingStart) {
        holder.contentLayout.setPadding(paddingStart, holder.contentLayout.getPaddingTop(), holder.contentLayout.getPaddingRight(), holder.contentLayout.getPaddingBottom());
    }

    private OfflineFormDefinition readDefinition(OfflineFormDefinitionIndexItem item) {
        if (item == null || item.getPatternId() == null || item.getPatternId().isEmpty()) {
            return null;
        }
        OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(_context, item.getPatternId());
        return definitionFile == null ? null : definitionFile.getJsonSchema();
    }

    private void renderCardStyle(ViewHolder holder,
                                 OfflineFormDefinitionIndexItem item,
                                 OfflineFormDefinition definition,
                                 List<OfflineFormCardStyle> styles) {
        OfflineComputedInfo computed = item.getComputed() == null
                ? new OfflineComputedInfo() : item.getComputed();
        boolean showDelete = false;
        for (OfflineFormCardStyle style : styles) {
            if (style == null || style.getType() == null) {
                continue;
            }

            if ("system".equalsIgnoreCase(style.getType())
                    && "delete".equalsIgnoreCase(style.getProperty())) {
                showDelete = true;
                continue;
            }

            if ("system".equalsIgnoreCase(style.getType())
                    && "progress".equalsIgnoreCase(style.getProperty())) {
                addProgressLayout(holder.dynamicCardLayout, computed);
                continue;
            }

            TextView valueView = new TextView(_context);
            valueView.setText(resolveCardText(style, item, definition));
            valueView.setTextSize(resolveCardFontSize(style.getFontSize()));
            valueView.setTypeface(Typeface.DEFAULT,
                    style.isBold() ? Typeface.BOLD : Typeface.NORMAL);
            valueView.setTextColor(resolveCardTextColor(style.getColor()));
            valueView.setPadding(0, dp(_context, 4), 0, dp(_context, 4));
            holder.dynamicCardLayout.addView(valueView,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (showDelete && !_sortMode) {
            addDeleteAction(holder.dynamicCardLayout, item);
        }
    }

    private void addProgressLayout(LinearLayout parent, OfflineComputedInfo computed) {
        LinearLayout progressLayout = new LinearLayout(_context);
        progressLayout.setOrientation(LinearLayout.HORIZONTAL);
        progressLayout.setBaselineAligned(false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = dp(_context, 10);
        parent.addView(progressLayout, layoutParams);

        addProgressText(progressLayout,
                _context.getString(R.string.offline_text_progress_total, computed.getTotalFillItems()),
                Gravity.START);
        addProgressText(progressLayout,
                _context.getString(R.string.offline_text_progress_filled, computed.getFilledFillItems()),
                Gravity.CENTER);
        addProgressText(progressLayout,
                _context.getString(R.string.offline_text_progress_rate,
                        String.format(Locale.CHINA, "%.0f%%", computed.getCompletionRate())),
                Gravity.END);
    }

    private void addProgressText(LinearLayout parent, String text, int gravity) {
        TextView textView = new TextView(_context);
        textView.setText(text);
        textView.setTextColor(android.graphics.Color.DKGRAY);
        textView.setTextSize(12f);
        textView.setGravity(gravity);
        parent.addView(textView, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    }

    private void addDeleteAction(LinearLayout parent, OfflineFormDefinitionIndexItem item) {
        LinearLayout actionLayout = new LinearLayout(_context);
        actionLayout.setGravity(Gravity.END);

        LinearLayout.LayoutParams actionLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionLayoutParams.topMargin = dp(_context, 8);
        parent.addView(actionLayout, actionLayoutParams);

        TextView deleteButton = new TextView(_context);
        deleteButton.setText(R.string.offline_button_delete);
        deleteButton.setTextColor(_context.getColor(R.color.red));
        deleteButton.setTextSize(14f);
        deleteButton.setGravity(Gravity.CENTER);
        deleteButton.setMinHeight(dp(_context, 40));
        deleteButton.setPadding(dp(_context, 12), 0, dp(_context, 12), 0);
        deleteButton.setCompoundDrawablePadding(dp(_context, 4));
        deleteButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_delete_image, 0, 0, 0);
        deleteButton.setCompoundDrawableTintList(
                ColorStateList.valueOf(_context.getColor(R.color.red)));
        deleteButton.setBackgroundResource(R.drawable.offline_list_delete_button_bg);
        deleteButton.setOnClickListener(v -> confirmDeleteConfig(item));
        actionLayout.addView(deleteButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private String resolveCardText(OfflineFormCardStyle style,
                                   OfflineFormDefinitionIndexItem item,
                                   OfflineFormDefinition definition) {
        String type = style.getType();
        if ("text".equalsIgnoreCase(type)) {
            return style.getContent() == null ? "" : style.getContent();
        }
        if (!"form".equalsIgnoreCase(type) || definition == null) {
            return "";
        }
        String property = style.getProperty();
        if ("patternId".equals(property)) return definition.getPatternId();
        if ("title".equals(property)) return definition.getTitle();
        if ("description".equals(property)) return definition.getDescription();
        if ("schemaVersion".equals(property)) return definition.getSchemaVersion();
        if ("status".equals(property)) {
            return OfflineFormExportStatusHelper.buildExportStatusText(_context, item);
        }
        return "";
    }

    private float resolveCardFontSize(String fontSize) {
        if ("small".equalsIgnoreCase(fontSize)) return 12f;
        if ("large".equalsIgnoreCase(fontSize)) return 18f;
        return 14f;
    }

    private int resolveCardTextColor(String color) {
        if ("secondary".equalsIgnoreCase(color)) {
            return _context.getColor(R.color.gray);
        }
        return _context.getColor(R.color.black);
    }

    private void confirmDeleteConfig(OfflineFormDefinitionIndexItem item) {
        if (item.getPatternId() == null || item.getPatternId().isEmpty()) {
            Toast.makeText(_context, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(_context)
                .setTitle(R.string.offline_dialog_delete_title)
                .setMessage(R.string.offline_dialog_delete_config_message)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteConfig(item))
                .setNegativeButton(R.string.ui_button_cancel, null)
                .show();
    }

    private void deleteConfig(OfflineFormDefinitionIndexItem item) {
        if (!OfflineFormFileHelper.deletePatternDirectory(_context, item.getPatternId())) {
            Toast.makeText(_context, R.string.offline_toast_delete_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        OfflineFormFileHelper.removeDefinitionOrder(_context, item.getPatternId());
        Toast.makeText(_context, R.string.offline_toast_delete_success, Toast.LENGTH_SHORT).show();
        if (_onProjectDeletedListener != null) {
            _onProjectDeletedListener.onProjectDeleted(item);
            return;
        }

        int position = findItemPosition(item);
        if (position >= 0) {
            _cardItems.remove(position);
            notifyItemRemoved(position);
        } else {
            notifyDataSetChanged();
        }
    }

    private int findItemPosition(OfflineFormDefinitionIndexItem item) {
        for (int i = 0; i < _cardItems.size(); i++) {
            if (item.getPatternId().equals(_cardItems.get(i).getPatternId())) {
                return i;
            }
        }
        return -1;
    }

    private void openNewRecord(OfflineFormDefinitionIndexItem item) {
        Intent intent = new Intent(_context, CustomFormActivity.class);
        putProjectExtras(intent, item);
        _context.startActivity(intent);
    }

    private void openLatestRecord(OfflineFormDefinitionIndexItem item) {
        List<OfflineFormRecord> records = OfflineFormFileHelper.readRecords(_context, item.getPatternId());
        if (records.isEmpty()) {
            openNewRecord(item);
            return;
        }

        OfflineFormRecord latestRecord = records.get(0);
        if (latestRecord.getStatus() == OfflineFormRecordStatus.EXPORTED) {
            openExportedRecordDetail(item, latestRecord);
            return;
        }

        if (!item.getSchemaVersion().equals(latestRecord.getSchemaVersion())) {
            Toast.makeText(_context, R.string.offline_toast_old_record_edit_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(_context, CustomFormActivity.class);
        putProjectExtras(intent, item);
        intent.putExtra("schemaVersion", latestRecord.getSchemaVersion());
        intent.putExtra(CustomFormActivity.EXTRA_RECORD_ID, latestRecord.getRecordId());
        _context.startActivity(intent);
    }

    private void openExportedRecordDetail(OfflineFormDefinitionIndexItem item, OfflineFormRecord record) {
        Intent intent = new Intent(_context, OfflineExportedRecordDetailActivity.class);
        intent.putExtra(OfflineExportedRecordDetailActivity.EXTRA_PATTERN_ID, item.getPatternId());
        intent.putExtra(OfflineExportedRecordDetailActivity.EXTRA_RECORD_ID, record.getRecordId());
        _context.startActivity(intent);
    }

    private void putProjectExtras(Intent intent, OfflineFormDefinitionIndexItem item) {
        intent.putExtra("patternId", item.getPatternId());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("schemaVersion", item.getSchemaVersion());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView metaTextView;
        TextView totalProgressTextView;
        TextView filledProgressTextView;
        TextView rateProgressTextView;
        LinearLayout contentLayout;
        LinearLayout dynamicCardLayout;
        LinearLayout progressLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            metaTextView = itemView.findViewById(R.id.metaTextView);
            totalProgressTextView = itemView.findViewById(R.id.totalProgressTextView);
            filledProgressTextView = itemView.findViewById(R.id.filledProgressTextView);
            rateProgressTextView = itemView.findViewById(R.id.rateProgressTextView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
            dynamicCardLayout = itemView.findViewById(R.id.dynamicCardLayout);
            progressLayout = itemView.findViewById(R.id.progressLayout);
        }
    }

    public interface OnProjectDeletedListener {
        void onProjectDeleted(OfflineFormDefinitionIndexItem item);
    }
}



