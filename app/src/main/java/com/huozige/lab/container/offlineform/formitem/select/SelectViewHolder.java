package com.huozige.lab.container.offlineform.formitem.select;

import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.List;

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
            if (selectItem != null) {
                showOptionsDialog();
            }
        });

        tvValue.setOnClickListener(v -> {
            if (selectItem != null) {
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

        String[] items = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            items[i] = options.get(i).getDisplayText();
        }

        int selectedIndex = -1;
        String currentValue = selectItem.getValue();
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getValue().equals(currentValue)) {
                selectedIndex = i;
                break;
            }
        }

        new AlertDialog.Builder(itemView.getContext())
                .setTitle(itemView.getContext().getString(R.string.offline_dialog_select_title, selectItem.getTitle()))
                .setSingleChoiceItems(items, selectedIndex, (dialog, which) -> {
                    SelectFormItem.Option selected = options.get(which);
                    selectItem.setSelectedValue(selected.getValue());
                    tvValue.setText(selected.getDisplayText());
                    tvValue.setHint("");
                    selectItem.clearError();
                    updateErrorState();
                    dialog.dismiss();
                })
                .show();
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

        if (selectItem.getValue() != null && !selectItem.getValue().isEmpty()) {
            tvValue.setText(selectItem.getSelectedDisplayText());
            tvValue.setHint("");
        } else {
            tvValue.setText("");
            tvValue.setHint(selectItem.getHint());
        }

        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (selectItem != null && selectItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(selectItem.getErrorMessage());
            tvValue.setBackgroundResource(R.drawable.custom_form_bg_edittext_error);
        } else {
            tvError.setVisibility(View.GONE);
            tvValue.setBackgroundResource(R.drawable.custom_form_bg_edittext_normal);
        }
    }
}
