package com.huozige.lab.container.offlineform.formitem.picker;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

    private PickerFormItem pickerItem;

    public PickerViewHolder(View itemView, PickerMode pickerMode) {
        super(itemView);
        this.pickerMode = pickerMode;
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvValue = itemView.findViewById(R.id.tv_value);
        tvError = itemView.findViewById(R.id.tv_error);
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
        bindTitle();
        bindValue();
        updateErrorState();
    }

    /** 一行式标题：必填星号并入标题文本，保证它紧跟标签而不是被 weight 推到取值旁边。 */
    private void bindTitle() {
        String title = pickerItem.getTitle() == null ? "" : pickerItem.getTitle();
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        if (pickerItem.isRequired()) {
            int starStart = builder.length();
            builder.append(" *");
            builder.setSpan(
                    new ForegroundColorSpan(itemView.getContext().getColor(R.color.offline_form_danger)),
                    starStart,
                    builder.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvTitle.setText(builder);
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
        boolean hasError = pickerItem != null && pickerItem.getErrorMessage() != null;
        if (hasError) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(pickerItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
        // 一行式样式没有输入框背景，错误态改用取值文字变红来提示。
        tvValue.setTextColor(itemView.getContext().getColor(
                hasError ? R.color.offline_form_danger : R.color.offline_form_ink));
    }

}
