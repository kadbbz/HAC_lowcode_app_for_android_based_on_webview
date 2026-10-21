package com.huozige.lab.container.offlineform.formitem.image;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.AttachmentCallback;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.util.Utils;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

/**
 * 图片列表项：3 列宫格（大屏 / 横屏 4 列），末尾固定一个虚线「添加」瓦片。
 *
 * <p>每张已上传图片 = 图片区 1:1 + 下沿整条删除条（PDA 场景大热区，点击后二次确认）；
 * 点击「添加」时按表单配置分流：允许图片上传 → 底部抽屉选来源，否则直接唤起相机。</p>
 */
public class ImageViewHolder extends BaseViewHolder {
    private static final int VIEW_TYPE_IMAGE = 0;
    private static final int VIEW_TYPE_ADD = 1;
    private static final int VIEW_TYPE_PROCESSING = 2;

    /** 宫格间距（dp）。 */
    private static final int GRID_SPACING_DP = 8;

    private final TextView tvTitle;
    private final TextView tvRequired;
    private final TextView tvImageCount;
    private final TextView tvImageHint;
    private final TextView tvError;
    private final RecyclerView imageList;
    private final ImageListAdapter imageListAdapter = new ImageListAdapter();

    private ImageFormItem imageItem;

    public ImageViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        tvImageCount = itemView.findViewById(R.id.tv_image_count);
        tvImageHint = itemView.findViewById(R.id.tv_image_hint);
        tvError = itemView.findViewById(R.id.tv_error);
        imageList = itemView.findViewById(R.id.image_list);
        setupImageList();
    }

    private void setupImageList() {
        int spanCount = itemView.getResources().getInteger(R.integer.offline_form_image_grid_span);
        imageList.setLayoutManager(new GridLayoutManager(itemView.getContext(), spanCount));
        imageList.setAdapter(imageListAdapter);
        imageList.setNestedScrollingEnabled(false);
        imageList.addItemDecoration(new GridSpacingItemDecoration(dp(itemView.getContext(), GRID_SPACING_DP)));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // 只有已上传图片参与拖拽排序，「添加」/「处理中」瓦片不可拖。
                if (viewHolder.getItemViewType() != VIEW_TYPE_IMAGE) {
                    return makeMovementFlags(0, 0);
                }
                return super.getMovementFlags(recyclerView, viewHolder);
            }

            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView,
                                       @NonNull RecyclerView.ViewHolder current,
                                       @NonNull RecyclerView.ViewHolder target) {
                return target.getBindingAdapterPosition() < imageCount();
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder source,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = source.getBindingAdapterPosition();
                int to = target.getBindingAdapterPosition();
                if (from == RecyclerView.NO_POSITION || to == RecyclerView.NO_POSITION || imageItem == null) {
                    return false;
                }
                if (from >= imageCount() || to >= imageCount()) {
                    return false;
                }
                AttachmentFormItemValue movedImage = imageItem.getImages().remove(from);
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
                // 拖动结束后重绑一次，保证删除条的无障碍描述与序号同步。
                renderImages();
                notifyImagesChanged();
            }
        }).attachToRecyclerView(imageList);
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
        renderImages();
        updateErrorState();
    }

    private void renderImages() {
        imageListAdapter.notifyDataSetChanged();
        updateCountAndHint();
    }

    /** 刷新右上角数量与下方提示语（空态 / 排序提示 / 已达上限）。 */
    private void updateCountAndHint() {
        int maxCount = imageItem == null ? 0 : imageItem.getMaxCount();
        int count = imageCount();
        if (maxCount > 0) {
            tvImageCount.setText(count >= maxCount
                    ? itemView.getContext().getString(R.string.offline_image_count_full, count, maxCount)
                    : itemView.getContext().getString(R.string.offline_image_count, count, maxCount));
        } else {
            tvImageCount.setText(itemView.getContext().getString(R.string.offline_image_count_unlimited, count));
        }

        if (count == 0) {
            boolean allowUpload = imageItem != null && imageItem.isAllowImageUpload();
            if (maxCount > 0) {
                tvImageHint.setText(itemView.getContext().getString(
                        allowUpload ? R.string.offline_image_hint_add_max : R.string.offline_image_hint_capture_max,
                        maxCount));
            } else {
                tvImageHint.setText(allowUpload
                        ? R.string.offline_image_hint_add : R.string.offline_image_hint_capture);
            }
        } else if (maxCount > 0 && count >= maxCount) {
            tvImageHint.setText(R.string.offline_image_hint_max_reached);
        } else {
            tvImageHint.setText(R.string.offline_image_hint_sort);
        }
    }

    private int imageCount() {
        return imageItem == null || imageItem.getImages() == null ? 0 : imageItem.getImages().size();
    }

    private boolean canAddImage() {
        if (imageItem == null) {
            return false;
        }
        return imageItem.getMaxCount() <= 0 || imageCount() < imageItem.getMaxCount();
    }

    /** 「添加」瓦片点击：按表单配置决定直接拍照还是先选来源。 */
    private void onAddClicked() {
        if (imageItem == null) {
            return;
        }
        if (!canAddImage()) {
            Toast.makeText(itemView.getContext(),
                    itemView.getContext().getString(R.string.offline_toast_max_image_count, imageItem.getMaxCount()),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(itemView.getContext() instanceof ImageCaptureHost)) {
            Toast.makeText(itemView.getContext(), R.string.offline_toast_image_capture_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageItem.isAllowImageUpload()) {
            ImageSourceSheet.show(itemView.getContext(), new ImageSourceSheet.SourceListener() {
                @Override
                public void onCameraSelected() {
                    captureImage();
                }

                @Override
                public void onGallerySelected() {
                    pickImages();
                }
            });
        } else {
            captureImage();
        }
    }

    private void captureImage() {
        if (!(itemView.getContext() instanceof ImageCaptureHost) || imageItem == null) {
            return;
        }
        ((ImageCaptureHost) itemView.getContext()).captureImage(imageItem, createAttachmentCallback(imageItem));
    }

    private void pickImages() {
        if (!(itemView.getContext() instanceof ImageCaptureHost) || imageItem == null) {
            return;
        }
        ((ImageCaptureHost) itemView.getContext()).uploadImage(imageItem, createAttachmentCallback(imageItem));
    }

    /**
     * 附件回调：拍照/相册返回后的处理开始/取消/完成都回写到字段模型上，网格据此显示或移除
     * 「处理中」占位；完成时把图片加入目标字段（即使当前 ViewHolder 已被复用也不会丢图）。
     */
    private AttachmentCallback createAttachmentCallback(final ImageFormItem targetItem) {
        return new AttachmentCallback() {
            @Override
            public void onAttachmentsSelected(List<AttachmentFormItemValue> attachments) {
                targetItem.setProcessing(false);
                if (attachments == null || attachments.isEmpty()) {
                    if (targetItem == imageItem) {
                        renderImages();
                    }
                    return;
                }
                for (AttachmentFormItemValue image : attachments) {
                    targetItem.addImage(image);
                }
                targetItem.clearError();
                if (targetItem == imageItem) {
                    renderImages();
                    updateErrorState();
                }
                // 新增图片的落盘由宿主统一 persistRecord，此处只刷新更新时间与界面。
                notifyFieldChanged();
            }

            @Override
            public void onAttachmentsProcessingStarted() {
                targetItem.setProcessing(true);
                if (targetItem == imageItem) {
                    renderImages();
                }
            }

            @Override
            public void onAttachmentsProcessingCancelled() {
                targetItem.setProcessing(false);
                if (targetItem == imageItem) {
                    renderImages();
                }
            }
        };
    }

    /** 二次确认后删除：先删本地文件，再从模型移除并刷新列表。 */
    private void confirmDeleteImage(int position) {
        if (imageItem == null || position < 0 || position >= imageCount()) {
            return;
        }
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_confirm_delete_image_title)
                .setMessage(R.string.offline_confirm_delete_image_message)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteImage(position))
                .setNegativeButton(R.string.offline_button_cancel, null)
                .show();
    }

    private void deleteImage(int position) {
        if (imageItem == null || position < 0 || position >= imageCount()) {
            return;
        }
        AttachmentFormItemValue image = imageItem.getImages().get(position);
        OfflineImageFileHelper.deleteLocalFile(itemView.getContext(), imageItem.getPatternId(), image.getFileName());
        imageItem.removeImage(image);
        renderImages();
        updateErrorState();
        notifyImagesChanged();
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
            AttachmentFormItemValue image = imageItem.getImages().get(i);
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

    private void notifyImagesChanged() {
        notifyFieldChanged();
        if (itemView.getContext() instanceof ImageCaptureHost) {
            ((ImageCaptureHost) itemView.getContext()).onImagesChanged(imageItem);
        }
    }

    /** 宫格排列：图片列表 + 末尾添加瓦片。 */
    private class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            if (position < imageCount()) {
                return VIEW_TYPE_IMAGE;
            }
            return isProcessing() ? VIEW_TYPE_PROCESSING : VIEW_TYPE_ADD;
        }

        @Override
        public int getItemCount() {
            if (imageItem == null || imageItem.getImages() == null) {
                return 0;
            }
            int count = imageItem.getImages().size();
            if (isProcessing()) {
                // 处理中：用占位替换「添加」瓦片，避免并发拍摄。
                return count + 1;
            }
            return count + (canAddImage() ? 1 : 0);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == VIEW_TYPE_ADD) {
                return new AddTileViewHolder(inflater.inflate(R.layout.custom_form_item_image_add_tile, parent, false));
            }
            if (viewType == VIEW_TYPE_PROCESSING) {
                return new ProcessingTileViewHolder(inflater.inflate(R.layout.custom_form_item_image_processing_tile, parent, false));
            }
            return new ImageTileViewHolder(inflater.inflate(R.layout.custom_form_item_image_tile, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ImageTileViewHolder) {
                ((ImageTileViewHolder) holder).bind(position);
            } else if (holder instanceof AddTileViewHolder) {
                ((AddTileViewHolder) holder).bind();
            }
        }
    }

    /** 已上传图片：图片区 + 下沿删除条；点击图片预览，点删除条二次确认。 */
    private class ImageTileViewHolder extends RecyclerView.ViewHolder {
        private final SquareFrameLayout previewContainer;
        private final ImageView preview;
        private final TextView errorView;
        private final TextView deleteBar;

        ImageTileViewHolder(@NonNull View itemView) {
            super(itemView);
            previewContainer = itemView.findViewById(R.id.image_preview_container);
            preview = itemView.findViewById(R.id.image_preview);
            errorView = itemView.findViewById(R.id.image_error_view);
            deleteBar = itemView.findViewById(R.id.image_delete_bar);
        }

        void bind(int position) {
            AttachmentFormItemValue image = imageItem.getImages().get(position);
            errorView.setVisibility(View.GONE);
            preview.setVisibility(View.VISIBLE);
            File imageFile = Utils.resolveLocalFile(itemView.getContext(), imageItem.getPatternId(),
                    image == null ? null : image.getFileName());
            if (imageFile != null && imageFile.exists()) {
                Glide.with(preview)
                        .load(Uri.fromFile(imageFile))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                // 解码失败时显示占位文案，避免图片区一片空白。
                                preview.setVisibility(View.GONE);
                                errorView.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(preview);
            } else {
                preview.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }

            deleteBar.setContentDescription(itemView.getContext()
                    .getString(R.string.offline_cd_delete_image_index, position + 1));
            previewContainer.setContentDescription(itemView.getContext()
                    .getString(R.string.offline_cd_preview_image_index, position + 1));
            previewContainer.setOnClickListener(v -> openPreview(getBindingAdapterPosition()));
            deleteBar.setOnClickListener(v -> {
                int current = getBindingAdapterPosition();
                if (current != RecyclerView.NO_POSITION) {
                    confirmDeleteImage(current);
                }
            });
        }
    }

    private boolean isProcessing() {
        return imageItem != null && imageItem.isProcessing();
    }

    /** 处理中占位瓦片：后台水印 / 压缩期间显示，处理完成后由回调替换为缩略图。 */
    private class ProcessingTileViewHolder extends RecyclerView.ViewHolder {
        ProcessingTileViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setContentDescription(itemView.getContext().getString(R.string.offline_image_processing));
        }
    }

    /** 添加瓦片：虚线 + 品牌色「+」，点击后按配置分流。 */
    private class AddTileViewHolder extends RecyclerView.ViewHolder {
        AddTileViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setContentDescription(itemView.getContext().getString(R.string.offline_cd_add_image));
            itemView.setOnClickListener(v -> onAddClicked());
        }

        void bind() {
            itemView.setContentDescription(itemView.getContext().getString(R.string.offline_cd_add_image));
        }
    }

    /** 宫格间距：列间 / 行间均为 8dp，首尾列与行内 padding 对齐、不额外留白。 */
    private static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        GridSpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == RecyclerView.NO_POSITION || !(parent.getLayoutManager() instanceof GridLayoutManager)) {
                return;
            }
            int spanCount = Math.max(1, ((GridLayoutManager) parent.getLayoutManager()).getSpanCount());
            int column = position % spanCount;
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            outRect.top = 0;
            int itemCount = state.getItemCount();
            outRect.bottom = position < itemCount - spanCount ? spacing : 0;
        }
    }
}
