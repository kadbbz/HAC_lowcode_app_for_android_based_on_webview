package com.huozige.lab.container.offlineform.formitem.image;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class ImageViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final RecyclerView imageList;
    private final Button btnCaptureImage;
    private final Button btnUploadImage;
    private final TextView tvError;
    private final TextView tvRequired;
    private final ImageListAdapter imageListAdapter = new ImageListAdapter();

    private ImageFormItem imageItem;

    public ImageViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        imageList = itemView.findViewById(R.id.image_list);
        btnCaptureImage = itemView.findViewById(R.id.btn_capture_image);
        btnUploadImage = itemView.findViewById(R.id.btn_upload_image);
        tvError = itemView.findViewById(R.id.tv_error);
        tvRequired = itemView.findViewById(R.id.tv_required);
        setupImageList();
        setupListeners();
    }

    private void setupImageList() {
        imageList.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2));
        imageList.setAdapter(imageListAdapter);
        imageList.setNestedScrollingEnabled(false);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
                int from = source.getBindingAdapterPosition();
                int to = target.getBindingAdapterPosition();
                if (from == RecyclerView.NO_POSITION || to == RecyclerView.NO_POSITION || imageItem == null) {
                    return false;
                }
                ImageFormItemValue movedImage = imageItem.getImages().remove(from);
                imageItem.getImages().add(to, movedImage);
                imageListAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                notifyImagesChanged();
            }
        }).attachToRecyclerView(imageList);
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
            ((ImageCaptureHost) itemView.getContext()).captureImage(imageItem, this::addImages);
        });
        btnUploadImage.setOnClickListener(v -> {
            if (!canAddImage()) {
                return;
            }
            if (!(itemView.getContext() instanceof ImageCaptureHost)) {
                Toast.makeText(itemView.getContext(), "当前页面不支持图片上传", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ImageCaptureHost) itemView.getContext()).uploadImage(imageItem, this::addImages);
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

    private void addImages(List<ImageFormItemValue> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        for (ImageFormItemValue image : images) {
            imageItem.addImage(image);
        }
        imageItem.clearError();
        renderImages();
        updateErrorState();
    }

    private void renderImages() {
        imageListAdapter.notifyDataSetChanged();
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

    private void openPreview(int index) {
        if (imageItem == null || imageItem.getImages() == null || imageItem.getImages().isEmpty()) {
            return;
        }
        if (index == RecyclerView.NO_POSITION) {
            return;
        }
        if (!(itemView.getContext() instanceof ImageCaptureHost)) {
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
        ((ImageCaptureHost) itemView.getContext()).previewImages(imageItem, fileNames, previewIndex);
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

    private class ImageListAdapter extends RecyclerView.Adapter<ImageCardViewHolder> {
        @NonNull
        @Override
        public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_image_card, parent, false);
            return new ImageCardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageCardViewHolder holder, int position) {
            holder.bind(imageItem.getImages().get(position), position);
        }

        @Override
        public int getItemCount() {
            return imageItem == null || imageItem.getImages() == null ? 0 : imageItem.getImages().size();
        }
    }

    private class ImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView preview;
        private final TextView errorView;
        private final Button deleteButton;

        ImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.imagePreview);
            errorView = itemView.findViewById(R.id.imageErrorView);
            deleteButton = itemView.findViewById(R.id.deleteImageButton);
        }

        void bind(ImageFormItemValue image, int position) {
            errorView.setVisibility(View.GONE);
            preview.setVisibility(View.VISIBLE);
            File imageFile = OfflineImageFileHelper.resolveLocalFile(itemView.getContext(), imageItem.getPatternId(), image);
            if (imageFile != null && imageFile.exists()) {
                int previewSize = dp(itemView.getContext(), 140);
                Glide.with(itemView)
                        .load(Uri.fromFile(imageFile))
                        .override(previewSize, previewSize)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .error(R.drawable.offline_image_error_bg)
                        .into(preview);
            } else {
                preview.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }

            preview.setOnClickListener(v -> openPreview(getBindingAdapterPosition()));
            errorView.setOnClickListener(v -> openPreview(getBindingAdapterPosition()));
            deleteButton.setOnClickListener(v -> {
                int currentPosition = getBindingAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) {
                    return;
                }
                ImageFormItemValue currentImage = imageItem.getImages().get(currentPosition);
                OfflineImageFileHelper.deleteLocalFile(itemView.getContext(), imageItem.getPatternId(), currentImage);
                imageItem.removeImage(currentImage);
                renderImages();
                updateErrorState();
                notifyImagesChanged();
            });
        }
    }

    private void notifyImagesChanged() {
        if (itemView.getContext() instanceof ImageCaptureHost) {
            ((ImageCaptureHost) itemView.getContext()).onImagesChanged(imageItem);
        }
    }
}
