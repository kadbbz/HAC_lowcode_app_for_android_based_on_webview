package com.huozige.lab.container.proxy.support.offlinecustomform.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.ODateUtils;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    // 用户完成一次字段值变更时回调外层（用于防抖落盘）。
    private Runnable onFieldChanged;
    // 字段被“二次确认后禁用”时回调外层（用于把锁定状态持久化到记录）。
    private Runnable onFieldLocked;
    // 是否展示该字段的更新时间（字段级开关，由适配器在 bind 前下发）。
    private boolean showFieldUpdateTime;
    // 是否已因二次确认而被禁用。由适配器在 bind 后下发。
    private boolean locked;
    // 字段行原始背景，用于高亮解除后还原。
    private final Drawable originalBackground;
    // 是否处于高亮状态（筛选时标记缺填的必填项）。由适配器在 bind 后下发。
    private boolean highlighted;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.originalBackground = itemView.getBackground();
    }

    public abstract void bind(BaseFormItem item);

    public abstract void bind(BaseFormItem item, int position);

    public abstract void updateErrorState();

    /** 设置字段值变更回调；由适配器在 bind 前下发。 */
    public void setOnFieldChanged(Runnable listener) {
        onFieldChanged = listener;
    }

    /** 设置字段锁定回调；由适配器在 bind 前下发。 */
    public void setOnFieldLocked(Runnable listener) {
        onFieldLocked = listener;
    }

    /** 设置是否展示该字段的更新时间；由适配器在 bind 前下发。 */
    public void setShowFieldUpdateTime(boolean showFieldUpdateTime) {
        this.showFieldUpdateTime = showFieldUpdateTime;
    }

    /** 设置锁定状态并立即应用到视图；由适配器在 bind 后下发。 */
    public void setLocked(boolean locked) {
        this.locked = locked;
        applyLockedState();
    }

    public boolean isLocked() {
        return locked;
    }

    /** 子类覆盖：根据 {@link #isLocked()} 切换禁用/只读的交互与视觉。默认无操作。 */
    protected void applyLockedState() {
    }

    /** 设置高亮状态并立即应用到视图；由适配器在 bind 后下发。 */
    public void setHighlighted(boolean highlighted) {
        if (this.highlighted == highlighted) {
            return;
        }
        this.highlighted = highlighted;
        applyHighlightState();
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    /** 子类可覆盖以定制高亮表现；默认切换字段行根布局背景。 */
    protected void applyHighlightState() {
        if (highlighted) {
            itemView.setBackgroundResource(R.drawable.offline_form_item_highlight_bg);
        } else {
            itemView.setBackground(originalBackground);
        }
    }

    /**
     * 用户完成一次字段值变更时调用：开关开启时立即把本字段的更新时间刷新为当前时间，
     * 并通知外层安排落盘，避免"只有重新进入表单才显示更新时间"。
     */
    protected void notifyFieldChanged() {
        if (showFieldUpdateTime) {
            OfflineFormFieldTimeViews.apply(itemView, ODateUtils.now());
        }
        notifyOuterFieldChanged();
    }

    /** 仅通知外层安排落盘（用于没有单一更新时间的列表字段等）。 */
    protected void notifyOuterFieldChanged() {
        if (onFieldChanged != null) {
            onFieldChanged.run();
        }
    }

    /** 通知外层该字段已被“二次确认后禁用”，需要持久化锁定状态。 */
    protected void notifyFieldLocked() {
        if (onFieldLocked != null) {
            onFieldLocked.run();
        }
    }

    /** 供容器型 ViewHolder 把同一回调下发给子字段 ViewHolder。 */
    protected final Runnable getFieldChangedCallback() {
        return onFieldChanged;
    }
}
