package com.huozige.lab.container.proxy.support.offlinecustomform.viewholder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.ODateUtils;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    // 高亮闪光：与“上一项/下一项”高亮一致的前景色淡出，持续 1 秒。
    private static final int HIGHLIGHT_DURATION_MS = 1000;
    private static final int HIGHLIGHT_START_ALPHA = 72;

    // 用户完成一次字段值变更时回调外层（用于防抖落盘）。
    private Runnable onFieldChanged;
    // 字段被“二次确认后禁用”时回调外层（用于把锁定状态持久化到记录）。
    private Runnable onFieldLocked;
    // 是否展示该字段的更新时间（字段级开关，由适配器在 bind 前下发）。
    private boolean showFieldUpdateTime;
    // 是否已因二次确认而被禁用。由适配器在 bind 后下发。
    private boolean locked;
    // 高亮闪光动画及其原始前景，用于取消或结束时还原。
    private Drawable originalForeground;
    private ValueAnimator highlightAnimator;

    public BaseViewHolder(View itemView) {
        super(itemView);
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
        if (highlighted) {
            startHighlightFlash();
        } else {
            stopHighlightFlash();
        }
    }

    public boolean isHighlighted() {
        return highlightAnimator != null;
    }

    /**
     * 播放一次高亮闪光：与“上一项/下一项”高亮一致——整行叠加湖蓝前景色，
     * 透明度从 {@link #HIGHLIGHT_START_ALPHA} 淡出到 0，持续 {@link #HIGHLIGHT_DURATION_MS} 毫秒。
     */
    private void startHighlightFlash() {
        stopHighlightFlash();
        originalForeground = itemView.getForeground();
        ColorDrawable highlight = new ColorDrawable(itemView.getContext().getColor(R.color.huozige_blue));
        highlight.setAlpha(HIGHLIGHT_START_ALPHA);
        itemView.setForeground(highlight);

        highlightAnimator = ValueAnimator.ofInt(HIGHLIGHT_START_ALPHA, 0);
        highlightAnimator.setDuration(HIGHLIGHT_DURATION_MS);
        highlightAnimator.addUpdateListener(animation -> highlight.setAlpha((int) animation.getAnimatedValue()));
        highlightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                itemView.setForeground(originalForeground);
                highlightAnimator = null;
            }
        });
        highlightAnimator.start();
    }

    private void stopHighlightFlash() {
        if (highlightAnimator != null) {
            highlightAnimator.cancel();
            highlightAnimator = null;
            itemView.setForeground(originalForeground);
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
