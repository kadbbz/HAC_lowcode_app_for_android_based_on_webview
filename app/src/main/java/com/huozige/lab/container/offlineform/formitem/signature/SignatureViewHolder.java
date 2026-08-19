package com.huozige.lab.container.offlineform.formitem.signature;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImageFileHelper;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.util.Utils;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.io.File;

public class SignatureViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvRequired;
    private final TextView tvEmpty;
    private final FrameLayout signaturePreviewContainer;
    private final ImageView signaturePreview;
    private final Button btnCapture;
    private final Button btnClear;
    private final TextView tvError;

    private SignatureFormItem signatureItem;

    public SignatureViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        tvEmpty = itemView.findViewById(R.id.tv_signature_empty);
        signaturePreviewContainer = itemView.findViewById(R.id.signature_preview_container);
        signaturePreview = itemView.findViewById(R.id.signature_preview);
        btnCapture = itemView.findViewById(R.id.btn_capture_signature);
        btnClear = itemView.findViewById(R.id.btn_clear_signature);
        tvError = itemView.findViewById(R.id.tv_error);

        btnCapture.setOnClickListener(v -> captureSignature());
        signaturePreviewContainer.setOnClickListener(v -> captureSignature());
        signaturePreview.setOnClickListener(v -> captureSignature());
        tvEmpty.setOnClickListener(v -> captureSignature());
        btnClear.setOnClickListener(v -> clearSignature());
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
        renderSignature();
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

    private void captureSignature() {
        if (!(itemView.getContext() instanceof SignatureCaptureHost)) {
            Toast.makeText(itemView.getContext(), R.string.offline_toast_signature_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        ((SignatureCaptureHost) itemView.getContext()).captureSignature(signatureItem, signature -> {
            if (signature == null) {
                return;
            }
            signatureItem.setSignature(signature);
            signatureItem.clearError();
            renderSignature();
            notifySignatureChanged();
        });
    }

    private void clearSignature() {
        if (signatureItem == null || signatureItem.isEmpty()) {
            return;
        }
        AttachmentFormItemValue signature = signatureItem.getSignature();
        if (signature != null) {
            OfflineImageFileHelper.deleteLocalFile(
                    itemView.getContext(),
                    signatureItem.getPatternId(),
                    signature.getFileName());
        }
        signatureItem.clearSignature();
        renderSignature();
        notifySignatureChanged();
    }

    private void renderSignature() {
        AttachmentFormItemValue signature = signatureItem == null ? null : signatureItem.getSignature();
        File signatureFile = signature == null
                ? null
                : Utils.resolveLocalFile(itemView.getContext(), signatureItem.getPatternId(), signature.getFileName());
        boolean hasPreview = signatureFile != null && signatureFile.exists();
        signaturePreview.setVisibility(hasPreview ? View.VISIBLE : View.GONE);
        tvEmpty.setVisibility(hasPreview ? View.GONE : View.VISIBLE);
        btnClear.setEnabled(hasPreview);
        if (hasPreview) {
            Glide.with(itemView)
                    .load(Uri.fromFile(signatureFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(signaturePreview);
        } else {
            signaturePreview.setImageDrawable(null);
        }
    }

    private void notifySignatureChanged() {
        if (itemView.getContext() instanceof SignatureCaptureHost) {
            ((SignatureCaptureHost) itemView.getContext()).onSignatureChanged(signatureItem);
        }
    }
}
