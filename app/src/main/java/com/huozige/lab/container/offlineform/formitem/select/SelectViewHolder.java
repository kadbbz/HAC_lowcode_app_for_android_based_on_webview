package com.huozige.lab.container.offlineform.formitem.select;

import android.view.View;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormConfirmHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemConfirmOptions;
import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectViewHolder extends BaseViewHolder {
    private TextView tvTitle;
    private TextView tvValue;
    private TextView tvError;
    private TextView tvRequired;

    private SelectFormItem selectItem;

    public SelectViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvValue = itemView.findViewById(R.id.tv_value);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);

        setupListeners();
    }

    private void setupListeners() {
        View clickableView = itemView.findViewById(R.id.select_container);
        if (clickableView == null) {
            clickableView = itemView;
        }

        clickableView.setOnClickListener(v -> {
            if (selectItem != null && !isLocked()) {
                showOptionsDialog();
            }
        });

        tvValue.setOnClickListener(v -> {
            if (selectItem != null && !isLocked()) {
                showOptionsDialog();
            }
        });
    }

    private void showOptionsDialog() {
        if (selectItem == null) return;

        List<SelectFormItem.Option> options = selectItem.getOptions();
        if (options.isEmpty()) {
            tvError.setText(R.string.offline_error_select_no_options);
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        List<String> labels = new ArrayList<>();
        for (SelectFormItem.Option option : options) {
            labels.add(option.getDisplayText());
        }

        int selectedIndex = -1;
        String currentValue = selectItem.getValue();
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getValue().equals(currentValue)) {
                selectedIndex = i;
                break;
            }
        }

        // 底部抽屉滚轮选择；点「确定」才提交，取消 / 下滑关闭视为未选择。
        OfflineSelectWheelSheet.show(itemView.getContext(), selectItem.getTitle(), labels, selectedIndex,
                position -> {
                    if (position < 0 || position >= options.size()) {
                        return;
                    }
                    requestSelection(options.get(position));
                });
    }

    /**
     * 用户选定某项后：与当前值相同视为未变更；否则按配置弹二次确认，
     * 确认通过后才写入新值并刷新更新时间。
     */
    private void requestSelection(SelectFormItem.Option selected) {
        if (selected == null || isLocked()) {
            return;
        }
        if (Objects.equals(selected.getValue(), selectItem.getValue())) {
            return;
        }
        OfflineFormConfirmHelper.show(itemView.getContext(), selectItem, selected.getDisplayText(),
                () -> applySelection(selected), this::bindValue);
    }

    private void applySelection(SelectFormItem.Option selected) {
        selectItem.setSelectedValue(selected.getValue());
        tvValue.setText(selected.getDisplayText());
        tvValue.setHint("");
        selectItem.clearError();
        updateErrorState();
        notifyFieldChanged();

        FormItemConfirmOptions confirmOptions = selectItem.getConfirmOptions();
        if (confirmOptions != null && confirmOptions.isEnabled() && confirmOptions.isDisableAfterConfirm()) {
            setLocked(true);
            notifyFieldLocked();
        }
    }

    @Override
    protected void applyLockedState() {
        boolean locked = isLocked();
        View clickableView = itemView.findViewById(R.id.select_container);
        View clickTarget = clickableView == null ? itemView : clickableView;
        clickTarget.setEnabled(!locked);
        clickTarget.setClickable(!locked);
        tvValue.setEnabled(!locked);
        tvValue.setAlpha(locked ? 0.6f : 1f);
    }

    @Override
    public void bind(BaseFormItem item) {
        this.selectItem = (SelectFormItem) item;
        bind(this.selectItem, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        this.selectItem = (SelectFormItem) item;

        tvTitle.setText(selectItem.getTitle());
        tvRequired.setVisibility(selectItem.isRequired() ? View.VISIBLE : View.GONE);
        bindValue();

        updateErrorState();
    }

    /** 按模型当前值刷新显示（用于 bind 与取消确认后的回滚）。 */
    private void bindValue() {
        if (selectItem == null) {
            return;
        }
        if (selectItem.getValue() != null && !selectItem.getValue().isEmpty()) {
            tvValue.setText(selectItem.getSelectedDisplayText());
            tvValue.setHint("");
        } else {
            tvValue.setText("");
            // 配置未传水印时给一个兜底水印，避免字段看起来是空的。
            String hint = selectItem.getHint();
            tvValue.setHint(hint == null || hint.trim().isEmpty()
                    ? itemView.getContext().getString(R.string.offline_hint_select)
                    : hint);
        }
    }

    @Override
    public void updateErrorState() {
        // 选择框不再套卡片/边框，错误态只用下方的红色错误文案表达（与其它字段一致）。
        if (selectItem != null && selectItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(selectItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}
