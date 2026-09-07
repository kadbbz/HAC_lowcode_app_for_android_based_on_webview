package com.huozige.lab.container.offlineform.formitem.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

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
        Calendar calendar = createInitialCalendar();

        DatePickerDialog dialog = new DatePickerDialog(
                itemView.getContext(),
                (view, year, month, dayOfMonth) -> {
                    if (pickerItem.isIncludeTime()) {
                        showDateTimePicker(year, month, dayOfMonth, calendar);
                    } else {
                        commitValue(String.format(Locale.CHINA, "%04d/%d/%d", year, month + 1, dayOfMonth));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle(R.string.offline_title_select_date);
        dialog.show();
    }

    private void showDateTimePicker(int year, int month, int dayOfMonth, Calendar calendar) {
        NumberPicker hourPicker = createTimeNumberPicker(0, 23, calendar.get(Calendar.HOUR_OF_DAY));
        NumberPicker minutePicker = createTimeNumberPicker(0, 59, calendar.get(Calendar.MINUTE));
        NumberPicker secondPicker = createTimeNumberPicker(0, 59, calendar.get(Calendar.SECOND));

        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_title_select_time)
                .setView(createTimePickerLayout(
                        hourPicker,
                        minutePicker,
                        secondPicker,
                        String.format(Locale.CHINA, "%d年%d月%d日", year, month + 1, dayOfMonth)))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    commitValue(String.format(
                            Locale.CHINA,
                            "%04d/%d/%d %02d:%02d:%02d",
                            year,
                            month + 1,
                            dayOfMonth,
                            hourPicker.getValue(),
                            minutePicker.getValue(),
                            secondPicker.getValue()));
                })
                .show();
    }

    private void showTimePicker() {
        Calendar calendar = createInitialCalendar();

        if (!pickerItem.isIncludeSeconds()) {
            new TimePickerDialog(itemView.getContext(), (view, hourOfDay, minute) -> {
                commitValue(String.format(Locale.CHINA, "%02d:%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            return;
        }

        NumberPicker hourPicker = createTimeNumberPicker(0, 23, calendar.get(Calendar.HOUR_OF_DAY));
        NumberPicker minutePicker = createTimeNumberPicker(0, 59, calendar.get(Calendar.MINUTE));
        NumberPicker secondPicker = createTimeNumberPicker(0, 59, calendar.get(Calendar.SECOND));

        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_title_select_time)
                .setView(createTimePickerLayout(hourPicker, minutePicker, secondPicker, null))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    commitValue(String.format(
                            Locale.CHINA,
                            "%02d:%02d:%02d",
                            hourPicker.getValue(),
                            minutePicker.getValue(),
                            secondPicker.getValue()));
                })
                .show();
    }

    private LinearLayout createTimePickerLayout(
            NumberPicker hourPicker,
            NumberPicker minutePicker,
            NumberPicker secondPicker,
            String selectedDate) {
        LinearLayout contentLayout = new LinearLayout(itemView.getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp(itemView.getContext(), 20), 0, dp(itemView.getContext(), 20), dp(itemView.getContext(), 8));

        if (selectedDate != null) {
            contentLayout.addView(createSelectedDateView(selectedDate));
        }

        LinearLayout pickerRow = new LinearLayout(itemView.getContext());
        pickerRow.setOrientation(LinearLayout.HORIZONTAL);
        pickerRow.setGravity(Gravity.CENTER);
        pickerRow.addView(createTimePickerColumn(hourPicker, itemView.getContext().getString(R.string.offline_text_time_hour)), createTimeColumnParams());
        pickerRow.addView(createTimeSeparator());
        pickerRow.addView(createTimePickerColumn(minutePicker, itemView.getContext().getString(R.string.offline_text_time_minute)), createTimeColumnParams());
        pickerRow.addView(createTimeSeparator());
        pickerRow.addView(createTimePickerColumn(secondPicker, itemView.getContext().getString(R.string.offline_text_time_second)), createTimeColumnParams());
        contentLayout.addView(pickerRow, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return contentLayout;
    }

    private TextView createSelectedDateView(String selectedDate) {
        TextView dateView = new TextView(itemView.getContext());
        dateView.setText(selectedDate);
        dateView.setTextColor(itemView.getContext().getColor(R.color.huozige_blue));
        dateView.setTextSize(16);
        dateView.setGravity(Gravity.CENTER);
        dateView.setPadding(0, dp(itemView.getContext(), 10), 0, dp(itemView.getContext(), 10));

        GradientDrawable background = new GradientDrawable();
        background.setColor(itemView.getContext().getColor(R.color.offline_form_group_bg));
        background.setCornerRadius(dp(itemView.getContext(), 8));
        dateView.setBackground(background);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(itemView.getContext(), 8);
        dateView.setLayoutParams(params);
        return dateView;
    }

    private LinearLayout.LayoutParams createTimeColumnParams() {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    }

    private TextView createTimeSeparator() {
        TextView separator = new TextView(itemView.getContext());
        separator.setText(":");
        separator.setTextSize(28);
        separator.setGravity(Gravity.CENTER);
        separator.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_title));
        return separator;
    }

    private void commitValue(String value) {
        pickerItem.setValue(value);
        pickerItem.clearError();
        bindValue();
        updateErrorState();
    }

    private NumberPicker createTimeNumberPicker(int minValue, int maxValue, int value) {
        NumberPicker numberPicker = new NumberPicker(itemView.getContext());
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setFormatter(value1 -> String.format(Locale.CHINA, "%02d", value1));
        numberPicker.setValue(value);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        return numberPicker;
    }

    private LinearLayout createTimePickerColumn(NumberPicker numberPicker, String label) {
        LinearLayout columnLayout = new LinearLayout(itemView.getContext());
        columnLayout.setOrientation(LinearLayout.VERTICAL);
        columnLayout.setGravity(Gravity.CENTER);

        TextView labelView = new TextView(itemView.getContext());
        labelView.setText(label);
        labelView.setGravity(Gravity.CENTER);
        labelView.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_content));
        labelView.setTextSize(12);
        columnLayout.addView(numberPicker, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        columnLayout.addView(labelView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return columnLayout;
    }

    private Calendar createInitialCalendar() {
        Calendar calendar = Calendar.getInstance();
        String value = pickerItem == null ? null : pickerItem.getValue();
        if (value == null || value.trim().isEmpty()) {
            return calendar;
        }

        String[] patterns = pickerMode == PickerMode.DATE
                ? new String[]{"yyyy/M/d HH:mm:ss", "yyyy/M/d"}
                : new String[]{"HH:mm:ss", "HH:mm"};
        for (String pattern : patterns) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
                dateFormat.setLenient(false);
                Date parsedValue = dateFormat.parse(value);
                if (parsedValue != null) {
                    calendar.setTime(parsedValue);
                    return calendar;
                }
            } catch (ParseException ignored) {
                // 尝试下一个兼容格式。
            }
        }
        return calendar;
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
