package com.huozige.lab.container.offlineform.formitem.radio;

import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
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
            if (binding || radioItem == null || checkedId == -1) return;
            RadioButton checked = group.findViewById(checkedId);
            if (checked != null) {
                radioItem.setSelectedValue(String.valueOf(checked.getTag()));
                radioItem.clearError();
                updateErrorState();
            }
        });
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
