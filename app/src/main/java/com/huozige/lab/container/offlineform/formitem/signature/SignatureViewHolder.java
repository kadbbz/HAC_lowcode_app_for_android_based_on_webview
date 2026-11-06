package com.huozige.lab.container.offlineform.formitem.signature;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 签名项：不套卡片，与普通字段一样直接平铺。
 *
 * <ul>
 *     <li>第 1 行：待签 / 已签人数（同时作为可点击提示，右侧箭头）；</li>
 *     <li>第 2 行：签名进度条；</li>
 *     <li>整块点击从底部弹出「人员签名」抽屉，单签名人与多签名人行为一致。</li>
 * </ul>
 */
public class SignatureViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvRequired;
    private final TextView summaryTitle;
    private final TextView emptyView;
    private final TextView tvError;

    private SignatureFormItem signatureItem;
    private SignatureSheet signatureSheet;

    public SignatureViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        summaryTitle = itemView.findViewById(R.id.signature_summary_title);
        emptyView = itemView.findViewById(R.id.signature_empty);
        tvError = itemView.findViewById(R.id.tv_error);
        itemView.setOnClickListener(v -> showSheet());
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                dismissSheet();
            }
        });
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
        render();
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

    private void render() {
        List<String> users = signatureItem == null ? null : signatureItem.getUsers();
        if (users == null || users.isEmpty()) {
            summaryTitle.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            itemView.setContentDescription(null);
            return;
        }

        int total = users.size();
        int signed = Math.max(0, Math.min(signatureItem.getSignedCount(), total));
        int pending = total - signed;

        summaryTitle.setVisibility(View.VISIBLE);
        summaryTitle.setText(itemView.getContext()
                .getString(R.string.offline_signature_pending_signed_count, pending, signed));
        emptyView.setVisibility(View.GONE);
        itemView.setContentDescription(itemView.getContext()
                .getString(R.string.offline_cd_signature_field, pending, signed));
    }

    private void showSheet() {
        List<String> users = signatureItem == null ? null : signatureItem.getUsers();
        if (users == null || users.isEmpty()) {
            return;
        }
        // 抽屉可能已被用户下滑 / 点遮罩关闭：只有仍显示时才复用，否则重新弹出。
        if (signatureSheet != null && signatureSheet.isShowing()) {
            signatureSheet.refresh();
            return;
        }
        signatureSheet = SignatureSheet.show(
                itemView.getContext(), signatureItem, this::requestSign);
    }

    private void dismissSheet() {
        if (signatureSheet != null) {
            signatureSheet.dismiss();
            signatureSheet = null;
        }
    }

    /** 签名 / 重签入口：已签人员先二次确认，未签人员直接进入签名页。 */
    private void requestSign(String userName) {
        if (signatureItem == null || userName == null) {
            return;
        }
        if (signatureItem.getSignature(userName) != null) {
            confirmResign(userName);
        } else {
            captureSignature(userName);
        }
    }

    private void confirmResign(String userName) {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_signature_resign_title)
                .setMessage(itemView.getContext().getString(R.string.offline_signature_resign_message, userName))
                .setPositiveButton(R.string.offline_signature_resign, (dialog, which) -> captureSignature(userName))
                .setNegativeButton(R.string.offline_button_cancel, null)
                .show();
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
            render();
            updateErrorState();
            if (signatureSheet != null) {
                signatureSheet.refresh();
            }
            notifySignatureChanged();
        });
    }

    private void notifySignatureChanged() {
        notifyFieldChanged();
        if (itemView.getContext() instanceof SignatureCaptureHost) {
            ((SignatureCaptureHost) itemView.getContext()).onSignatureChanged(signatureItem);
        }
    }
}
