package com.huozige.lab.container.offlineform.formitem.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

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

        new DatePickerDialog(itemView.getContext(), (view, year, month, dayOfMonth) -> {
            pickerItem.setValue(String.format(Locale.US, "%04d/%d/%d", year, month + 1, dayOfMonth));
            pickerItem.clearError();
            bindValue();
            updateErrorState();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);

        if (!pickerItem.isIncludeSeconds()) {
            new TimePickerDialog(itemView.getContext(), (view, hourOfDay, minute) -> {
                pickerItem.setValue(String.format(Locale.US, "%02d:%02d", hourOfDay, minute));
                pickerItem.clearError();
                bindValue();
                updateErrorState();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            return;
        }

        NumberPicker hourPicker = createTimeNumberPicker(0, 23, calendar.get(Calendar.HOUR_OF_DAY));
        NumberPicker minutePicker = createTimeNumberPicker(0, 59, calendar.get(Calendar.MINUTE));
        NumberPicker secondPicker = createTimeNumberPicker(0, 59, second);

        LinearLayout contentLayout = new LinearLayout(itemView.getContext());
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setGravity(Gravity.CENTER);
        contentLayout.setPadding(0, 24, 0, 8);
        contentLayout.addView(createTimePickerColumn(hourPicker, itemView.getContext().getString(R.string.offline_text_time_hour)));
        contentLayout.addView(createTimePickerColumn(minutePicker, itemView.getContext().getString(R.string.offline_text_time_minute)));
        contentLayout.addView(createTimePickerColumn(secondPicker, itemView.getContext().getString(R.string.offline_text_time_second)));

        new AlertDialog.Builder(itemView.getContext())
                .setView(contentLayout)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    pickerItem.setValue(String.format(Locale.US, "%02d:%02d:%02d", hourPicker.getValue(), minutePicker.getValue(), secondPicker.getValue()));
                    pickerItem.clearError();
                    bindValue();
                    updateErrorState();
                })
                .show();
    }

    private NumberPicker createTimeNumberPicker(int minValue, int maxValue, int value) {
        NumberPicker numberPicker = new NumberPicker(itemView.getContext());
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(value);
        numberPicker.setFormatter(value1 -> String.format(Locale.US, "%02d", value1));
        return numberPicker;
    }

    private LinearLayout createTimePickerColumn(NumberPicker numberPicker, String label) {
        LinearLayout columnLayout = new LinearLayout(itemView.getContext());
        columnLayout.setOrientation(LinearLayout.VERTICAL);
        columnLayout.setGravity(Gravity.CENTER);

        TextView labelView = new TextView(itemView.getContext());
        labelView.setText(label);
        labelView.setGravity(Gravity.CENTER);
        columnLayout.addView(numberPicker, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        columnLayout.addView(labelView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return columnLayout;
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
