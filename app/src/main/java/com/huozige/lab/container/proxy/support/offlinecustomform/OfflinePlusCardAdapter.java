package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.CustomFormActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndexItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 历史填报列表的卡片适配器，只负责列表 UI 展示、管理勾选、拖拽排序和进入填报页。
public class OfflinePlusCardAdapter extends RecyclerView.Adapter<OfflinePlusCardAdapter.ViewHolder> {
    // 普通模式下卡片内容左侧内边距。
    private static final int CONTENT_PADDING_DP = 16;
    // 管理模式下为 checkbox 预留的额外缩进。
    private static final int MANAGE_INDENT_DP = 48;
    // 管理模式切换时 checkbox 淡入淡出和内容右移的动画时长。
    private static final long MANAGE_ANIMATION_DURATION = 180;

    // 当前列表展示的表单定义索引项，数据来源于本地索引文件。
    private List<OfflineFormDefinitionIndexItem> _cardItems;
    private Context _context;
    // 管理模式：显示 checkbox，点击卡片只切换选中状态，不进入填报页。
    private boolean _manageMode;
    // 排序模式：允许外部拖拽调整列表顺序，点击卡片不触发跳转。
    private boolean _sortMode;
    // 管理模式下已勾选的表单定义项目编号，用于批量删除。
    private final Set<String> _selectedPatternIds = new HashSet<>();

    public OfflinePlusCardAdapter(List<OfflineFormDefinitionIndexItem> cardItems, Context context) {
        this._cardItems = cardItems;
        _context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfflineFormDefinitionIndexItem item = _cardItems.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.metaTextView.setText("项目编号：" + item.getPatternId() + "    版本号：" + item.getSchemaVersion());
        String theme = item.getTheme() == null || item.getTheme().isEmpty() ? "#999999" : item.getTheme();
        holder.themeView.setBackgroundColor(Color.parseColor(theme));
        holder.imageView.setImageBitmap(createIconBitmap(item.getPatternId(), item.getSchemaVersion(), theme));
        holder.checkBoxSelect.animate().cancel();
        applyManageLayout(holder, _manageMode, false);
        // RecyclerView 会复用 ViewHolder，先清空监听再回填选中状态，避免 setChecked 误触发旧监听。
        holder.checkBoxSelect.setOnCheckedChangeListener(null);
        holder.checkBoxSelect.setChecked(_selectedPatternIds.contains(item.getPatternId()));
        holder.checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                _selectedPatternIds.add(item.getPatternId());
            } else {
                _selectedPatternIds.remove(item.getPatternId());
            }
        });


        // 排序、管理、普通填报三种状态的点击行为互斥，避免拖拽排序时误进入填报页。
        holder.itemView.setOnClickListener(v -> {
            if (_sortMode) {
                return;
            }

            if (_manageMode) {
                holder.checkBoxSelect.setChecked(!holder.checkBoxSelect.isChecked());
                return;
            }

            Intent intent = new Intent(_context, CustomFormActivity.class);
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

    public void setManageMode(boolean manageMode) {
        if (_manageMode == manageMode) {
            return;
        }
        _manageMode = manageMode;
        if (!manageMode) {
            _selectedPatternIds.clear();
        }
        // 只刷新管理模式相关 UI，触发带 payload 的 onBindViewHolder 来播放缩进和透明度动画。
        notifyItemRangeChanged(0, getItemCount(), "manageMode");
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

    public List<String> getSelectedPatternIds() {
        return new ArrayList<>(_selectedPatternIds);
    }

    // 头像不落盘保存，列表绑定时根据项目编号和版本号现场生成稳定图案。
    private Bitmap createIconBitmap(String patternId, String schemaVersion, String theme) {
        return OfflineComputedHelper.createIconBitmap(patternId + "|" + schemaVersion, theme);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        // 管理模式切换只影响 checkbox 和内容缩进，不需要重绑整张卡片的数据。
        if (payloads.contains("manageMode")) {
            applyManageLayout(holder, _manageMode, true);
            return;
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    private void applyManageLayout(ViewHolder holder, boolean manageMode, boolean animate) {
        holder.checkBoxSelect.animate().cancel();
        // 管理模式下内容整体右移，给 checkbox 留出空间，避免 checkbox 覆盖标题和描述。
        int targetPaddingStart = dp(CONTENT_PADDING_DP) + (manageMode ? dp(MANAGE_INDENT_DP) : 0);
        int currentPaddingStart = holder.contentLayout.getPaddingStart();
        float targetAlpha = manageMode ? 1f : 0f;

        holder.checkBoxSelect.setVisibility(View.VISIBLE);
        holder.checkBoxSelect.setAlpha(manageMode && !animate ? 1f : holder.checkBoxSelect.getAlpha());

        if (!animate) {
            setContentPaddingStart(holder, targetPaddingStart);
            holder.checkBoxSelect.setAlpha(targetAlpha);
            holder.checkBoxSelect.setVisibility(manageMode ? View.VISIBLE : View.GONE);
            return;
        }

        ValueAnimator animator = ValueAnimator.ofInt(currentPaddingStart, targetPaddingStart);
        animator.setDuration(MANAGE_ANIMATION_DURATION);
        animator.addUpdateListener(animation -> {
            int paddingStart = (int) animation.getAnimatedValue();
            setContentPaddingStart(holder, paddingStart);
        });
        animator.start();

        holder.checkBoxSelect.animate()
                .alpha(targetAlpha)
                .setDuration(MANAGE_ANIMATION_DURATION)
                .withEndAction(() -> holder.checkBoxSelect.setVisibility(manageMode ? View.VISIBLE : View.GONE))
                .start();
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
        CheckBox checkBoxSelect;
        ImageView imageView;
        LinearLayout contentLayout;
        View themeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            metaTextView = itemView.findViewById(R.id.metaTextView);
            checkBoxSelect = itemView.findViewById(R.id.checkBoxSelect);
            imageView = itemView.findViewById(R.id.imageView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
            themeView = itemView.findViewById(R.id.themeView);
        }
    }
}
