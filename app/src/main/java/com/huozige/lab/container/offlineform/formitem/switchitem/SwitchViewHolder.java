package com.huozige.lab.container.offlineform.formitem.switchitem;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormConfirmHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemConfirmOptions;
import com.huozige.lab.container.offlineform.model.formitem.switchitem.SwitchFormItem;
import com.huozige.lab.container.offlineform.model.formitem.switchitem.SwitchFormItemOptions;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class SwitchViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvError;
    private final TextView tvRequired;
    private final OfflineSwitchView switchView;
    private final CheckBox checkBoxView;

    private SwitchFormItem switchItem;
    // bind 期间由代码回填选中态，不应视为用户变更。
    private boolean binding;

    public SwitchViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        switchView = itemView.findViewById(R.id.switch_view);
        checkBoxView = itemView.findViewById(R.id.checkbox_view);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (binding || switchItem == null || isLocked()) {
                return;
            }
            boolean previous = switchItem.isChecked();
            if (isChecked == previous) {
                return;
            }
            if (OfflineFormConfirmHelper.isEnabled(switchItem)) {
                // 二次确认：确认后才写入新值并刷新更新时间；取消则回滚。
                int labelRes = isChecked ? R.string.offline_switch_on : R.string.offline_switch_off;
                OfflineFormConfirmHelper.show(buttonView.getContext(), switchItem,
                        buttonView.getContext().getString(labelRes),
                        () -> applyChecked(isChecked),
                        () -> restoreChecked(previous));
            } else {
                applyChecked(isChecked);
            }
        };
        switchView.setOnCheckedChangeListener(listener);
        checkBoxView.setOnCheckedChangeListener(listener);
    }

    /** 确认通过后写入新值；按配置禁用时同时锁定并通知外层持久化。 */
    private void applyChecked(boolean checked) {
        switchItem.setChecked(checked);
        switchItem.clearError();
        updateErrorState();
        notifyFieldChanged();

        FormItemConfirmOptions confirmOptions = switchItem.getConfirmOptions();
        if (confirmOptions != null && confirmOptions.isEnabled() && confirmOptions.isDisableAfterConfirm()) {
            setLocked(true);
            notifyFieldLocked();
        }
    }

    /** 取消确认时把选中态回滚到原值，且不触发任何字段变更回调。 */
    private void restoreChecked(boolean checked) {
        binding = true;
        switchView.setChecked(checked);
        checkBoxView.setChecked(checked);
        binding = false;
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        this.switchItem = (SwitchFormItem) item;
        tvTitle.setText(switchItem.getTitle());
        tvRequired.setVisibility(switchItem.isRequired() ? View.VISIBLE : View.GONE);

        boolean useSwitch = !SwitchFormItemOptions.STYLE_CHECKBOX.equalsIgnoreCase(switchItem.getStyle());
        switchView.setVisibility(useSwitch ? View.VISIBLE : View.GONE);
        checkBoxView.setVisibility(useSwitch ? View.GONE : View.VISIBLE);

        binding = true;
        boolean checked = switchItem.isChecked();
        switchView.setChecked(checked);
        checkBoxView.setChecked(checked);
        binding = false;

        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (switchItem != null && switchItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(switchItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    @Override
    protected void applyLockedState() {
        boolean locked = isLocked();
        switchView.setEnabled(!locked);
        checkBoxView.setEnabled(!locked);
        // 开关的禁用态由 OfflineSwitchView 自己按淡化色绘制；复选框沿用整体降透明度。
        checkBoxView.setAlpha(locked ? 0.6f : 1f);
    }
}
