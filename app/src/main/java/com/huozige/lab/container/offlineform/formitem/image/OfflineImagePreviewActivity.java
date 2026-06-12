package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;

import java.io.File;
import java.util.ArrayList;

import io.getstream.photoview.PhotoView;

public class OfflineImagePreviewActivity extends BaseActivityNoActionBar {
    private static final String EXTRA_PATTERN_ID = "patternId";
    public static final String EXTRA_FILE_NAMES = "fileNames";
    private static final String EXTRA_INDEX = "index";

    private ViewPager2 viewPager;
    private TextView indexTextView;
    private PreviewAdapter adapter;
    private String patternId;
    private ArrayList<String> fileNames = new ArrayList<>();

    public static Intent createIntent(Context context, String patternId, ArrayList<String> fileNames, int index) {
        Intent intent = new Intent(context, OfflineImagePreviewActivity.class);
        intent.putExtra(EXTRA_PATTERN_ID, patternId);
        intent.putStringArrayListExtra(EXTRA_FILE_NAMES, fileNames);
        intent.putExtra(EXTRA_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_image_preview_activity);

        patternId = getIntent().getStringExtra(EXTRA_PATTERN_ID);
        ArrayList<String> inputFileNames = getIntent().getStringArrayListExtra(EXTRA_FILE_NAMES);
        if (inputFileNames != null) {
            fileNames = inputFileNames;
        }
        if (fileNames.isEmpty()) {
            finish();
            return;
        }

        int initialIndex = Math.max(0, Math.min(getIntent().getIntExtra(EXTRA_INDEX, 0), fileNames.size() - 1));
        indexTextView = findViewById(R.id.previewIndexTextView);
        viewPager = findViewById(R.id.previewViewPager);
        adapter = new PreviewAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(initialIndex, false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndex(position);
            }
        });

        findViewById(R.id.previewBackButton).setOnClickListener(v -> finish());
        findViewById(R.id.previewRotateLeftButton).setOnClickListener(v -> adapter.rotateCurrent(-90));
        findViewById(R.id.previewResetButton).setOnClickListener(v -> adapter.resetCurrent());
        findViewById(R.id.previewRotateRightButton).setOnClickListener(v -> adapter.rotateCurrent(90));
        findViewById(R.id.previewDeleteButton).setOnClickListener(v -> deleteCurrentImage());

        updateIndex(initialIndex);
    }

    private void updateIndex(int index) {
        indexTextView.setText((index + 1) + " / " + fileNames.size());
    }

    private void deleteCurrentImage() {
        int position = viewPager.getCurrentItem();
        if (position < 0 || position >= fileNames.size()) {
            return;
        }
        String fileName = fileNames.remove(position);
        OfflineImageFileHelper.deleteLocalFile(this, patternId, fileName);
        setResultChanged();
        if (fileNames.isEmpty()) {
            finish();
            return;
        }
        adapter.remove(position);
        int nextPosition = Math.min(position, fileNames.size() - 1);
        viewPager.setCurrentItem(nextPosition, false);
        updateIndex(nextPosition);
    }

    private void setResultChanged() {
        Intent data = new Intent();
        data.putStringArrayListExtra(EXTRA_FILE_NAMES, new ArrayList<>(fileNames));
        setResult(RESULT_OK, data);
    }

    private class PreviewAdapter extends RecyclerView.Adapter<PreviewViewHolder> {
        private final ArrayList<Float> rotations = new ArrayList<>();
        private PreviewViewHolder currentHolder;

        PreviewAdapter() {
            for (int i = 0; i < fileNames.size(); i++) {
                rotations.add(0.0f);
            }
        }

        @NonNull
        @Override
        public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FrameLayout container = new FrameLayout(parent.getContext());
            container.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            PhotoView photoView = new PhotoView(parent.getContext());
            photoView.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            photoView.setMaximumScale(6.0f);
            container.addView(photoView);
            return new PreviewViewHolder(container, photoView);
        }

        @Override
        public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
            if (position == viewPager.getCurrentItem()) {
                currentHolder = holder;
            }
            holder.photoView.setRotationTo(rotations.get(position));
            holder.photoView.setScale(1.0f, false);

            File imageFile = OfflineImageFileHelper.resolveLocalFile(
                    OfflineImagePreviewActivity.this,
                    patternId,
                    fileNames.get(position));
            if (imageFile == null || !imageFile.exists()) {
                holder.photoView.setImageDrawable(null);
                Toast.makeText(OfflineImagePreviewActivity.this, "图片文件不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            Glide.with(holder.photoView)
                    .load(Uri.fromFile(imageFile))
                    .fitCenter()
                    .into(holder.photoView);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull PreviewViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            if (holder.getBindingAdapterPosition() == viewPager.getCurrentItem()) {
                currentHolder = holder;
            }
        }

        @Override
        public int getItemCount() {
            return fileNames.size();
        }

        void remove(int position) {
            rotations.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount() - position);
        }

        void rotateCurrent(float degrees) {
            int position = viewPager.getCurrentItem();
            rotations.set(position, rotations.get(position) + degrees);
            PreviewViewHolder holder = findCurrentHolder();
            if (holder != null) {
                holder.photoView.setRotationTo(rotations.get(position));
            }
        }

        void resetCurrent() {
            int position = viewPager.getCurrentItem();
            rotations.set(position, 0.0f);
            PreviewViewHolder holder = findCurrentHolder();
            if (holder != null) {
                holder.photoView.setRotationTo(0);
                holder.photoView.setScale(1.0f, true);
            }
        }

        private PreviewViewHolder findCurrentHolder() {
            return currentHolder != null && currentHolder.getBindingAdapterPosition() == viewPager.getCurrentItem()
                    ? currentHolder
                    : null;
        }
    }

    private static class PreviewViewHolder extends RecyclerView.ViewHolder {
        private final PhotoView photoView;

        PreviewViewHolder(@NonNull FrameLayout itemView, PhotoView photoView) {
            super(itemView);
            this.photoView = photoView;
        }
    }
}
