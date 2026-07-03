package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.CustomFormActivity;
import com.huozige.lab.container.offlineform.OfflineExportedRecordDetailActivity;
import com.huozige.lab.container.offlineform.OfflineProjectRecordActivity;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;

import java.util.List;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

// 历史填报列表的卡片适配器，只负责列表 UI 展示、拖拽排序和进入项目填报记录页。
public class OfflinePlusCardAdapter extends RecyclerView.Adapter<OfflinePlusCardAdapter.ViewHolder> {
    private static final int MENU_ID_FILL_RECORD = 1;
    private static final int MENU_ID_PROJECT_DETAIL = 2;
    private static final int MENU_ID_DELETE_CONFIG = 3;
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

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.metaTextView.setText(_context.getString(R.string.offline_text_project_meta, item.getPatternId(), item.getSchemaVersion()));
        String theme = OfflineComputedHelper.resolveThemeColor(item.getComputed().getTheme());
        holder.themeView.setBackgroundColor(OfflineComputedHelper.parseColor(theme));
        holder.imageView.setImageBitmap(createIconBitmap(item.getPatternId(), item.getSchemaVersion(), theme));
        setContentPaddingStart(holder, dp(_context, CONTENT_PADDING_DP));
        holder.actionButton.setVisibility(_sortMode ? View.GONE : View.VISIBLE);


        // 排序时点击不进入填报，避免拖拽过程中误打开项目填报页。
        holder.itemView.setOnClickListener(v -> {
            if (_sortMode) {
                return;
            }

            openLatestRecord(item);
        });
        holder.actionButton.setOnClickListener(v -> {
            if (_sortMode) {
                return;
            }

            showProjectActionMenu(holder.actionButton, item);
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

    // 头像不落盘保存，列表绑定时根据项目编号和版本号现场生成稳定图案。
    private Bitmap createIconBitmap(String patternId, String schemaVersion, String theme) {
        return OfflineComputedHelper.createIconBitmap(patternId + "|" + schemaVersion, theme);
    }

    private void setContentPaddingStart(ViewHolder holder, int paddingStart) {
        holder.contentLayout.setPadding(paddingStart, holder.contentLayout.getPaddingTop(), holder.contentLayout.getPaddingRight(), holder.contentLayout.getPaddingBottom());
    }

    private void showProjectActionMenu(View anchor, OfflineFormDefinitionIndexItem item) {
        PopupMenu popupMenu = new PopupMenu(_context, anchor);
//        popupMenu.getMenu().add(0, MENU_ID_FILL_RECORD, MENU_ID_FILL_RECORD, R.string.offline_menu_fill_record)
//                .setIcon(R.drawable.ic_offline_menu_fill);
//        popupMenu.getMenu().add(0, MENU_ID_PROJECT_DETAIL, MENU_ID_PROJECT_DETAIL, R.string.offline_menu_project_detail)
//                .setIcon(R.drawable.ic_offline_menu_detail);
        popupMenu.getMenu().add(0, MENU_ID_DELETE_CONFIG, MENU_ID_DELETE_CONFIG, R.string.offline_menu_delete_form)
                .setIcon(R.drawable.ic_offline_menu_delete);
        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(menuItem -> handleProjectActionMenuItemClick(menuItem, item));
        popupMenu.show();
    }

    private boolean handleProjectActionMenuItemClick(MenuItem menuItem, OfflineFormDefinitionIndexItem item) {
        if (menuItem.getItemId() == MENU_ID_FILL_RECORD) {
            openNewRecord(item);
            return true;
        }
        if (menuItem.getItemId() == MENU_ID_PROJECT_DETAIL) {
            openProjectRecords(item);
            return true;
        }
        if (menuItem.getItemId() == MENU_ID_DELETE_CONFIG) {
            confirmDeleteConfig(item);
            return true;
        }
        return false;
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

    private void openProjectRecords(OfflineFormDefinitionIndexItem item) {
        Intent intent = new Intent(_context, OfflineProjectRecordActivity.class);
        putProjectExtras(intent, item);
        intent.putExtra("status", item.getStatus());
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
        TextView actionButton;
        ImageView imageView;
        LinearLayout contentLayout;
        View themeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            metaTextView = itemView.findViewById(R.id.metaTextView);
            actionButton = itemView.findViewById(R.id.cmdOpenActions);
            imageView = itemView.findViewById(R.id.imageView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
            themeView = itemView.findViewById(R.id.themeView);
        }
    }

    public interface OnProjectDeletedListener {
        void onProjectDeleted(OfflineFormDefinitionIndexItem item);
    }
}
