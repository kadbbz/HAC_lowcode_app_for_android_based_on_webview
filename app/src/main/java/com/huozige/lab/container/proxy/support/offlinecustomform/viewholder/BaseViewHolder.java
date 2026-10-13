package com.huozige.lab.container.proxy.support.offlinecustomform.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.ODateUtils;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    // 用户完成一次字段值变更时回调外层（用于防抖落盘）。
    private Runnable onFieldChanged;

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

    /**
     * 用户完成一次字段值变更时调用：立即把本字段的更新时间刷新为当前时间，
     * 并通知外层安排落盘，避免"只有重新进入表单才显示更新时间"。
     */
    protected void notifyFieldChanged() {
        OfflineFormFieldTimeViews.apply(itemView, ODateUtils.now());
        notifyOuterFieldChanged();
    }

    /** 仅通知外层安排落盘（用于没有单一更新时间的列表字段等）。 */
    protected void notifyOuterFieldChanged() {
        if (onFieldChanged != null) {
            onFieldChanged.run();
        }
    }

    /** 供容器型 ViewHolder 把同一回调下发给子字段 ViewHolder。 */
    protected final Runnable getFieldChangedCallback() {
        return onFieldChanged;
    }
}
