package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.io.File;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class ImageViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final LinearLayout imageList;
    private final Button btnCaptureImage;
    private final TextView tvError;
    private final TextView tvRequired;

    private ImageFormItem imageItem;

    public ImageViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        imageList = itemView.findViewById(R.id.image_list);
        btnCaptureImage = itemView.findViewById(R.id.btn_capture_image);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        setupListeners();
    }

    private void setupListeners() {
        btnCaptureImage.setOnClickListener(v -> {
            if (imageItem == null) {
                return;
            }
            if (imageItem.getMaxCount() > 0 && imageItem.getImages().size() >= imageItem.getMaxCount()) {
                Toast.makeText(itemView.getContext(), "最多只能添加" + imageItem.getMaxCount() + "张图片", Toast.LENGTH_SHORT).show();
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
        btnCaptureImage.setText("拍照");
        renderImages();
        updateErrorState();
    }

    private void renderImages() {
        imageList.removeAllViews();
        for (ImageFormItemValue image : imageItem.getImages()) {
            imageList.addView(createImageRow(image));
        }
        btnCaptureImage.setEnabled(imageItem.getMaxCount() <= 0 || imageItem.getImages().size() < imageItem.getMaxCount());
    }

    private View createImageRow(ImageFormItemValue image) {
        Context context = itemView.getContext();
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, dp(context, 6), 0, dp(context, 6));

        ImageView preview = new ImageView(context);
        preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        File imageFile = OfflineImageFileHelper.resolveLocalFile(context, imageItem.getPatternId(), image);
        if (imageFile != null && imageFile.exists()) {
            preview.setImageURI(Uri.fromFile(imageFile));
        }
        row.addView(preview, new LinearLayout.LayoutParams(dp(context, 56), dp(context, 56)));

        TextView nameView = new TextView(context);
        nameView.setText(image.getFileName());
        nameView.setTextSize(13);
        nameView.setSingleLine(true);
        nameView.setPadding(dp(context, 10), 0, dp(context, 8), 0);
        row.addView(nameView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView deleteView = new TextView(context);
        deleteView.setText("删除");
        deleteView.setTextColor(context.getColor(R.color.huozige_blue));
        deleteView.setTypeface(null, Typeface.BOLD);
        deleteView.setGravity(Gravity.CENTER);
        deleteView.setPadding(dp(context, 8), dp(context, 8), dp(context, 8), dp(context, 8));
        deleteView.setOnClickListener(v -> {
            imageItem.removeImage(image);
            renderImages();
            updateErrorState();
        });
        row.addView(deleteView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return row;
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
