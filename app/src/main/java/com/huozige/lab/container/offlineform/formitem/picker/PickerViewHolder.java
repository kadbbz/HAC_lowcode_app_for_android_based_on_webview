package com.huozige.lab.container.offlineform.formitem.picker;

import android.view.View;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    /** 日期：从底部弹出滚轮抽屉，选完点「确定」才提交。 */
    private void showDatePicker() {
        OfflineWheelPickerSheet.showDate(
                itemView.getContext(),
                createInitialCalendar(),
                pickerItem.isIncludeTime(),
                pickerItem.getDateRangeMode(),
                pickerItem.getMinDate(),
                pickerItem.getMaxDate(),
                this::commitValue);
    }

    /** 时间：与日期共用同一套底部滚轮抽屉，保证交互与视觉一致。 */
    private void showTimePicker() {
        OfflineWheelPickerSheet.showTime(
                itemView.getContext(),
                createInitialCalendar(),
                pickerItem.isIncludeSeconds(),
                this::commitValue);
    }

    private void commitValue(String value) {
        pickerItem.setValue(value);
        pickerItem.clearError();
        bindValue();
        updateErrorState();
        notifyFieldChanged();
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
        int valueColor = itemView.getContext().getColor(R.color.offline_form_ink);
        if (pickerItem != null && pickerItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(pickerItem.getErrorMessage());
            valueColor = itemView.getContext().getColor(R.color.offline_form_danger);
        } else {
            tvError.setVisibility(View.GONE);
        }
        tvValue.setTextColor(valueColor);
    }

}
