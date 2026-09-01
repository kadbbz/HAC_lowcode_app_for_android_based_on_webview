package com.huozige.lab.container.offlineform.formitem.text;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;
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
        etInput.addOnLayoutChangeListener((v, left, top, right, bottom,
                oldLeft, oldTop, oldRight, oldBottom) -> updateInputGravity());
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
                    etInput.post(TextViewHolder.this::updateInputGravity);
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
        configureInput(textItem);
        etInput.setText(textItem.getValue() != null ? textItem.getValue() : "");
        etInput.post(this::updateInputGravity);
        tvRequired.setVisibility(textItem.isRequired() ? View.VISIBLE : View.GONE);

        updateErrorState();
    }

    private void configureInput(TextFormItem textItem) {
        int inputType = textItem.getInputType();
        if (textItem.isPassword()) {
            etInput.setInputType(inputType);
            etInput.setSingleLine(true);
            etInput.setMaxLines(1);
            etInput.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            return;
        }

        // Ordinary text fields should wrap long content and grow with the number
        // of lines, while retaining the original single-line minimum height.
        etInput.setInputType(inputType | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        etInput.setSingleLine(false);
        etInput.setMaxLines(Integer.MAX_VALUE);
        etInput.setHorizontallyScrolling(false);
        etInput.setGravity(Gravity.TOP | Gravity.START);
    }

    private void updateInputGravity() {
        if (item == null || item.isPassword()) {
            return;
        }

        CharSequence text = etInput.getText();
        boolean multiLine = etInput.getLineCount() > 1
                || (text != null && text.toString().contains("\n"));
        int gravity = (multiLine ? Gravity.TOP : Gravity.CENTER_VERTICAL) | Gravity.START;
        if (etInput.getGravity() != gravity) {
            etInput.setGravity(gravity);
        }
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
