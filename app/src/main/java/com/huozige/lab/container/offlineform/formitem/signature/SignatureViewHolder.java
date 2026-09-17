package com.huozige.lab.container.offlineform.formitem.signature;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public class SignatureViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvRequired;
    private final LinearLayout userList;
    private final TextView tvError;

    private SignatureFormItem signatureItem;

    public SignatureViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        userList = itemView.findViewById(R.id.signature_user_list);
        tvError = itemView.findViewById(R.id.tv_error);
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getBindingAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        signatureItem = (SignatureFormItem) item;
        tvTitle.setText(signatureItem.getTitle());
        tvRequired.setVisibility(signatureItem.isRequired() ? View.VISIBLE : View.GONE);
        renderUsers();
        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (signatureItem != null && signatureItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(signatureItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    private void renderUsers() {
        userList.removeAllViews();
        if (signatureItem == null || signatureItem.getUsers() == null || signatureItem.getUsers().isEmpty()) {
            TextView emptyView = new TextView(itemView.getContext());
            emptyView.setPadding(0, 12, 0, 12);
            emptyView.setText(R.string.offline_text_no_signature_users);
            emptyView.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_content));
            userList.addView(emptyView);
            return;
        }

        for (String userName : signatureItem.getUsers()) {
            View row = LayoutInflater.from(itemView.getContext())
                    .inflate(R.layout.custom_form_item_signature_user_row, userList, false);
            TextView nameView = row.findViewById(R.id.signature_user_name);
            TextView statusView = row.findViewById(R.id.signature_status);
            boolean signed = signatureItem.getSignature(userName) != null;
            nameView.setText(userName);
            statusView.setText(signed
                    ? R.string.offline_text_signature_set
                    : R.string.offline_text_signature_not_set);
            statusView.setTextColor(signed
                    ? itemView.getContext().getColor(R.color.huozige_blue)
                    : Color.GRAY);
            row.setContentDescription(itemView.getContext().getString(
                    R.string.offline_cd_signature_user_status,
                    userName,
                    statusView.getText()));
            row.setOnClickListener(v -> captureSignature(userName));
            userList.addView(row);
        }
    }

    private void captureSignature(String userName) {
        if (!(itemView.getContext() instanceof SignatureCaptureHost)) {
            Toast.makeText(itemView.getContext(), R.string.offline_toast_signature_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        ((SignatureCaptureHost) itemView.getContext()).captureSignature(signatureItem, userName, signature -> {
            if (signature == null) {
                return;
            }
            signatureItem.setSignature(userName, signature);
            signatureItem.clearError();
            renderUsers();
            updateErrorState();
            notifySignatureChanged();
        });
    }

    private void notifySignatureChanged() {
        if (itemView.getContext() instanceof SignatureCaptureHost) {
            ((SignatureCaptureHost) itemView.getContext()).onSignatureChanged(signatureItem);
        }
    }
}
