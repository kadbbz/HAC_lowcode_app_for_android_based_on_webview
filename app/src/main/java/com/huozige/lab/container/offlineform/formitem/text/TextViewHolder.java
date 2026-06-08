package com.huozige.lab.container.offlineform.formitem.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class TextViewHolder extends BaseViewHolder {
    private TextView tvTitle;
    private EditText etInput;
    private TextView tvError;
    private TextView tvRequired;

    private TextFormItem item;

    public TextViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        etInput = itemView.findViewById(R.id.et_input);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);

        setupListeners();
    }

    private void setupListeners() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (item != null) {
                    item.setValue(s.toString());
                    updateErrorState();
                }
            }
        });
    }

    @Override
    public void bind(BaseFormItem item) {
        this.item = (TextFormItem) item;
        bind(this.item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        TextFormItem textItem = (TextFormItem) item;
        this.item = textItem;

        tvTitle.setText(textItem.getTitle());
        etInput.setHint(textItem.getHint());
        etInput.setText(textItem.getValue() != null ? textItem.getValue() : "");
        etInput.setInputType(textItem.getInputType());
        tvRequired.setVisibility(textItem.isRequired() ? View.VISIBLE : View.GONE);

        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (item == null) return;

        if (item.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(item.getErrorMessage());
            etInput.setBackgroundResource(R.drawable.custom_form_bg_edittext_error);
        } else {
            tvError.setVisibility(View.GONE);
            etInput.setBackgroundResource(R.drawable.custom_form_bg_edittext_normal);
        }
    }
}
