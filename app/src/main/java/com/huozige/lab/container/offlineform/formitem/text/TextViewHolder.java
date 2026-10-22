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
    // bind 期间由代码回填文本，不应视为用户变更。
    private boolean binding;
    // 是否有尚未在「失焦」时结算的输入变更（用于移开焦点后再刷新更新时间）。
    private boolean pendingTextChange;

    public TextViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        etInput = itemView.findViewById(R.id.et_input);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);

        setupListeners();
        etInput.addOnLayoutChangeListener((v, left, top, right, bottom,
                oldLeft, oldTop, oldRight, oldBottom) -> updateInputGravity());
        etInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                settleTextChangeOnBlur();
            }
        });
    }

    private void setupListeners() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (item == null) {
                    return;
                }
                etInput.post(TextViewHolder.this::updateInputGravity);
                updateErrorState();
                // bind 期间 setSingleLine / setTransformationMethod 会触发一次文本回写，
                // 此时不能用控件内容覆盖模型（否则刚装载的值会被清空）；只处理真实用户输入。
                if (!binding) {
                    item.setValue(s.toString());
                    pendingTextChange = true;
                    // 输入过程中只防抖落盘，不刷新「更新时间」；等移开焦点后再统一结算（见 settleTextChangeOnBlur）。
                    notifyOuterFieldChanged();
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

        binding = true;
        pendingTextChange = false;
        tvTitle.setText(textItem.getTitle());
        etInput.setHint(resolveHint(textItem));
        configureInput(textItem);
        etInput.setText(textItem.getValue() != null ? textItem.getValue() : "");
        etInput.post(this::updateInputGravity);
        tvRequired.setVisibility(textItem.isRequired() ? View.VISIBLE : View.GONE);
        binding = false;

        updateErrorState();
    }

    /** 配置未传水印时给一个兜底水印，避免字段看起来是空的。 */
    private String resolveHint(TextFormItem textItem) {
        String hint = textItem.getHint();
        if (hint != null && !hint.trim().isEmpty()) {
            return hint;
        }
        return itemView.getContext().getString(textItem.isPassword()
                ? R.string.offline_hint_password
                : R.string.offline_hint_input);
    }

    /**
     * 移开焦点时才结算本次输入：刷新字段更新时间并通知外层落盘。
     * 输入过程中不刷新更新时间，避免每敲一个字就变时间。
     */
    private void settleTextChangeOnBlur() {
        if (item == null || binding || !pendingTextChange) {
            return;
        }
        pendingTextChange = false;
        notifyFieldChanged();
    }

    private void configureInput(TextFormItem textItem) {
        int inputType = textItem.getInputType();
        if (textItem.isPassword()) {
            // 先切单行再设置输入类型：setSingleLine(true) 会把密码变换覆盖成单向滚动变换，
            // 顺序反过来才能保留 PasswordTransformationMethod（输入内容显示为圆点）。
            etInput.setSingleLine(true);
            etInput.setMaxLines(1);
            etInput.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            etInput.setInputType(inputType);
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

        // 输入框不再套卡片/边框，错误态只用下方的红色错误文案表达（与其它字段一致）。
        if (item.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(item.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}
