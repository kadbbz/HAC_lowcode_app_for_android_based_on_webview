package com.huozige.lab.container.offlineform.formitem.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.PickerFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.Calendar;
import java.util.Locale;

public class PickerViewHolder extends BaseViewHolder {
    private final PickerMode pickerMode;
    private final TextView tvTitle;
    private final TextView tvValue;
    private final TextView tvError;
    private final TextView tvRequired;

    private PickerFormItem pickerItem;

    public PickerViewHolder(View itemView, PickerMode pickerMode) {
        super(itemView);
        this.pickerMode = pickerMode;
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvValue = itemView.findViewById(R.id.tv_value);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        setupListeners();
    }

    private void setupListeners() {
        View clickableView = itemView.findViewById(R.id.picker_container);
        clickableView.setOnClickListener(v -> showPicker());
        tvValue.setOnClickListener(v -> showPicker());
    }

    private void showPicker() {
        if (pickerItem == null) {
            return;
        }
        if (pickerMode == PickerMode.DATE) {
            showDatePicker();
        } else {
            showTimePicker();
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        String value = pickerItem.getValue();
        if (value != null && value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] parts = value.split("-");
            calendar.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
        }

        new DatePickerDialog(itemView.getContext(), (view, year, month, dayOfMonth) -> {
            pickerItem.setValue(String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth));
            pickerItem.clearError();
            bindValue();
            updateErrorState();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        String value = pickerItem.getValue();
        if (value != null && value.matches("\\d{2}:\\d{2}")) {
            String[] parts = value.split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
        }

        new TimePickerDialog(itemView.getContext(), (view, hourOfDay, minute) -> {
            pickerItem.setValue(String.format(Locale.US, "%02d:%02d", hourOfDay, minute));
            pickerItem.clearError();
            bindValue();
            updateErrorState();
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        pickerItem = (PickerFormItem) item;
        tvTitle.setText(pickerItem.getTitle());
        tvRequired.setVisibility(pickerItem.isRequired() ? View.VISIBLE : View.GONE);
        bindValue();
        updateErrorState();
    }

    private void bindValue() {
        String value = pickerItem.getValue();
        if (value != null && !value.isEmpty()) {
            tvValue.setText(value);
            tvValue.setHint("");
        } else {
            tvValue.setText("");
            tvValue.setHint(pickerItem.getHint());
        }
    }

    @Override
    public void updateErrorState() {
        if (pickerItem != null && pickerItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(pickerItem.getErrorMessage());
            tvValue.setBackgroundResource(R.drawable.custom_form_bg_edittext_error);
        } else {
            tvError.setVisibility(View.GONE);
            tvValue.setBackgroundResource(R.drawable.custom_form_bg_edittext_normal);
        }
    }
}
