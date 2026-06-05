package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.offlineform.OfflineProjectRecordActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;

import java.util.List;

// 历史填报列表的卡片适配器，只负责列表 UI 展示、拖拽排序和进入项目填报记录页。
public class OfflinePlusCardAdapter extends RecyclerView.Adapter<OfflinePlusCardAdapter.ViewHolder> {
    // 普通模式下卡片内容左侧内边距。
    private static final int CONTENT_PADDING_DP = 16;

    // 当前列表展示的表单定义索引项，数据来源于本地索引文件。
    private List<OfflineFormDefinitionIndexItem> _cardItems;
    private Context _context;
    // 排序模式：允许外部拖拽调整列表顺序，点击卡片不触发跳转。
    private boolean _sortMode;

    public OfflinePlusCardAdapter(List<OfflineFormDefinitionIndexItem> cardItems, Context context) {
        this._cardItems = cardItems;
        _context = context;
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
        holder.metaTextView.setText("项目编号：" + item.getPatternId() + "    版本号：" + item.getSchemaVersion());
        String theme = OfflineComputedHelper.resolveThemeColor(item.getComputed().getTheme());
        holder.themeView.setBackgroundColor(OfflineComputedHelper.parseColor(theme));
        holder.imageView.setImageBitmap(createIconBitmap(item.getPatternId(), item.getSchemaVersion(), theme));
        setContentPaddingStart(holder, dp(CONTENT_PADDING_DP));


        // 排序时点击不进入详情，避免拖拽过程中误打开项目填报记录页。
        holder.itemView.setOnClickListener(v -> {
            if (_sortMode) {
                return;
            }

            Intent intent = new Intent(_context, OfflineProjectRecordActivity.class);
            intent.putExtra("patternId", item.getPatternId());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("status", item.getStatus());
            intent.putExtra("schemaVersion", item.getSchemaVersion());
            _context.startActivity(intent);
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

    private int dp(int value) {
        return (int) (value * _context.getResources().getDisplayMetrics().density + 0.5f);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView metaTextView;
        ImageView imageView;
        LinearLayout contentLayout;
        View themeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            metaTextView = itemView.findViewById(R.id.metaTextView);
            imageView = itemView.findViewById(R.id.imageView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
            themeView = itemView.findViewById(R.id.themeView);
        }
    }
}
