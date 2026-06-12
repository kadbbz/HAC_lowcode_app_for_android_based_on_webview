package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class ImageViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final LinearLayout imageList;
    private final Button btnCaptureImage;
    private final Button btnUploadImage;
    private final TextView tvError;
    private final TextView tvRequired;

    private ImageFormItem imageItem;

    public ImageViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        imageList = itemView.findViewById(R.id.image_list);
        btnCaptureImage = itemView.findViewById(R.id.btn_capture_image);
        btnUploadImage = itemView.findViewById(R.id.btn_upload_image);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        setupListeners();
    }

    private void setupListeners() {
        btnCaptureImage.setOnClickListener(v -> {
            if (!canAddImage()) {
                return;
            }
            if (!(itemView.getContext() instanceof ImageCaptureHost)) {
                Toast.makeText(itemView.getContext(), "当前页面不支持拍照", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ImageCaptureHost) itemView.getContext()).captureImage(imageItem, image -> {
                imageItem.addImage(image);
                imageItem.clearError();
                renderImages();
                updateErrorState();
            });
        });
        btnUploadImage.setOnClickListener(v -> {
            if (!canAddImage()) {
                return;
            }
            if (!(itemView.getContext() instanceof ImageCaptureHost)) {
                Toast.makeText(itemView.getContext(), "当前页面不支持图片上传", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ImageCaptureHost) itemView.getContext()).uploadImage(imageItem, image -> {
                imageItem.addImage(image);
                imageItem.clearError();
                renderImages();
                updateErrorState();
            });
        });
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        imageItem = (ImageFormItem) item;
        tvTitle.setText(imageItem.getTitle());
        tvRequired.setVisibility(imageItem.isRequired() ? View.VISIBLE : View.GONE);
        btnUploadImage.setVisibility(imageItem.isAllowImageUpload() ? View.VISIBLE : View.GONE);
        renderImages();
        updateErrorState();
    }

    private void renderImages() {
        imageList.removeAllViews();
        LinearLayout row = null;
        for (int i = 0; i < imageItem.getImages().size(); i++) {
            if (i % 2 == 0) {
                row = createImageRow();
                imageList.addView(row);
            }
            row.addView(createImageCard(imageItem.getImages().get(i), i));
        }
        if (row != null && imageItem.getImages().size() % 2 == 1) {
            row.addView(createEmptyImageSlot());
        }
        boolean canAdd = imageItem.getMaxCount() <= 0 || imageItem.getImages().size() < imageItem.getMaxCount();
        setActionButtonEnabled(btnCaptureImage, canAdd);
        setActionButtonEnabled(btnUploadImage, canAdd);
    }

    private void setActionButtonEnabled(Button button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1.0f : 0.45f);
    }

    private boolean canAddImage() {
        if (imageItem == null) {
            return false;
        }
        if (imageItem.getMaxCount() > 0 && imageItem.getImages().size() >= imageItem.getMaxCount()) {
            Toast.makeText(itemView.getContext(), "最多只能添加" + imageItem.getMaxCount() + "张图片", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private LinearLayout createImageRow() {
        Context context = itemView.getContext();
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(0, dp(context, 6), 0, dp(context, 6));
        row.setLayoutParams(rowParams);
        return row;
    }

    private View createEmptyImageSlot() {
        Context context = itemView.getContext();
        View emptySlot = new View(context);
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(0, 1, 1);
        itemParams.setMargins(dp(context, 4), 0, dp(context, 4), 0);
        emptySlot.setLayoutParams(itemParams);
        return emptySlot;
    }

    private View createImageCard(ImageFormItemValue image, int index) {
        Context context = itemView.getContext();
        CardView itemCard = new CardView(context);
        itemCard.setRadius(dp(context, 6));
        itemCard.setCardElevation(dp(context, 1));
        itemCard.setUseCompatPadding(false);
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        itemParams.setMargins(dp(context, 4), 0, dp(context, 4), 0);
        itemCard.setLayoutParams(itemParams);

        LinearLayout cardContent = new LinearLayout(context);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        itemCard.addView(cardContent, new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView preview = new ImageView(context);
        preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        File imageFile = OfflineImageFileHelper.resolveLocalFile(context, imageItem.getPatternId(), image);
        if (imageFile != null && imageFile.exists()) {
            int previewSize = dp(context, 140);
            Glide.with(itemView)
                    .load(Uri.fromFile(imageFile))
                    .override(previewSize, previewSize)
                    .centerCrop()
                    .dontAnimate()
                    .into(preview);
        } else {
            preview.setImageDrawable(null);
        }

        cardContent.addView(preview, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(context, 140)));
        preview.setOnClickListener(v -> openPreview(index));

        Button deleteButton = new Button(context);
        deleteButton.setBackgroundResource(R.drawable.offline_image_delete_button_bg);
        deleteButton.setContentDescription("删除图片");
        deleteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_image, 0, 0, 0);
        deleteButton.setCompoundDrawablePadding(dp(context, 6));
        deleteButton.setMinWidth(0);
        deleteButton.setPadding(dp(context, 8), 0, dp(context, 8), 0);
        deleteButton.setText("删除");
        deleteButton.setTextColor(context.getColor(R.color.white));
        deleteButton.setTextSize(14);
        deleteButton.setOnClickListener(v -> {
            OfflineImageFileHelper.deleteLocalFile(context, imageItem.getPatternId(), image);
            imageItem.removeImage(image);
            renderImages();
            updateErrorState();
        });
        LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(context, 36));
        cardContent.addView(deleteButton, deleteParams);

        return itemCard;
    }

    private void openPreview(int index) {
        if (imageItem == null || imageItem.getImages() == null || imageItem.getImages().isEmpty()) {
            return;
        }
        ArrayList<String> fileNames = new ArrayList<>();
        int previewIndex = 0;
        for (int i = 0; i < imageItem.getImages().size(); i++) {
            ImageFormItemValue image = imageItem.getImages().get(i);
            if (image != null && image.getFileName() != null && !image.getFileName().isEmpty()) {
                if (i < index) {
                    previewIndex++;
                }
                fileNames.add(image.getFileName());
            }
        }
        OfflineImagePreviewActivity.open(itemView.getContext(), imageItem.getPatternId(), fileNames, previewIndex);
    }

    @Override
    public void updateErrorState() {
        if (imageItem != null && imageItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(imageItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}
