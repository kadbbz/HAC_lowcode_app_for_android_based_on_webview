package com.huozige.lab.container.offlineform.formitem.radio;

import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormConfirmHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemConfirmOptions;
import com.huozige.lab.container.offlineform.model.formitem.radio.RadioFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Objects;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class RadioViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final RadioGroup radioGroup;
    private final TextView tvError;
    private final TextView tvRequired;
    private RadioFormItem radioItem;
    private boolean binding;

    public RadioViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        radioGroup = itemView.findViewById(R.id.radio_group);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding || radioItem == null || checkedId == -1 || isLocked()) return;
            RadioButton checked = group.findViewById(checkedId);
            if (checked == null) return;
            String newValue = String.valueOf(checked.getTag());
            if (Objects.equals(newValue, radioItem.getValue())) return;
            // 二次确认：确认后才写入新值并刷新更新时间；取消则回滚勾选。
            OfflineFormConfirmHelper.show(itemView.getContext(), radioItem, checked.getText().toString(),
                    () -> applyCheckedValue(newValue),
                    () -> restoreCheckedValue(radioItem.getValue()));
        });
    }

    /**
     * 确认通过后写入新值；按配置禁用时同时锁定并通知外层持久化。
     */
    private void applyCheckedValue(String value) {
        radioItem.setSelectedValue(value);
        radioItem.clearError();
        updateErrorState();
        notifyFieldChanged();

        FormItemConfirmOptions confirmOptions = radioItem.getConfirmOptions();
        if (confirmOptions != null && confirmOptions.isEnabled() && confirmOptions.isDisableAfterConfirm()) {
            setLocked(true);
            notifyFieldLocked();
        }
    }

    /** 取消确认时把勾选回滚到原值，且不触发任何字段变更回调。 */
    private void restoreCheckedValue(String value) {
        binding = true;
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (Objects.equals(String.valueOf(radioButton.getTag()), value)) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }
        binding = false;
    }

    @Override
    protected void applyLockedState() {
        boolean locked = isLocked();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            child.setEnabled(!locked);
            child.setAlpha(locked ? 0.6f : 1f);
        }
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        radioItem = (RadioFormItem) item;
        tvTitle.setText(radioItem.getTitle());
        tvRequired.setVisibility(radioItem.isRequired() ? View.VISIBLE : View.GONE);
        binding = true;
        radioGroup.removeAllViews();
        radioGroup.setOrientation(radioItem.isHorizontal() ? RadioGroup.HORIZONTAL : RadioGroup.VERTICAL);
        List<RadioFormItem.Option> options = radioItem.getOptions();
        int checkedId = -1;
        for (RadioFormItem.Option option : options) {
            RadioButton radioButton = new RadioButton(itemView.getContext());
            radioButton.setText(option.getDisplayText());
            radioButton.setTag(option.getValue());
            radioButton.setGravity(Gravity.CENTER_VERTICAL);
            radioButton.setId(View.generateViewId());
            radioButton.setPadding(0, dp(itemView.getContext(), 4), dp(itemView.getContext(), 12), dp(itemView.getContext(), 4));
            radioGroup.addView(radioButton, new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            if (Objects.equals(option.getValue(), radioItem.getValue())) {
                checkedId = radioButton.getId();
            }
        }
        if (checkedId != -1) radioGroup.check(checkedId);
        binding = false;
        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (radioItem != null && radioItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(radioItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}
