package com.huozige.lab.container.offlineform.formitem.file;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileFormItemValue;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.utilities.DeviceUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FileViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvRequired;
    private final RecyclerView fileList;
    private final Button btnUploadFile;
    private final TextView tvError;
    private final FileListAdapter fileListAdapter = new FileListAdapter();

    private FileFormItem fileItem;

    public FileViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        fileList = itemView.findViewById(R.id.file_list);
        btnUploadFile = itemView.findViewById(R.id.btn_upload_file);
        tvError = itemView.findViewById(R.id.tv_error);
        setupFileList();
        setupListeners();
    }

    private void setupFileList() {
        fileList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        fileList.setAdapter(fileListAdapter);
        fileList.setNestedScrollingEnabled(false);
    }

    private void setupListeners() {
        btnUploadFile.setOnClickListener(v -> {
            if (!canAddFile(true)) {
                return;
            }
            if (!(itemView.getContext() instanceof FileUploadHost)) {
                Toast.makeText(itemView.getContext(), "当前页面不支持文件上传", Toast.LENGTH_SHORT).show();
                return;
            }
            ((FileUploadHost) itemView.getContext()).uploadFile(fileItem, this::addFiles);
        });
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        fileItem = (FileFormItem) item;
        tvTitle.setText(fileItem.getTitle());
        tvRequired.setVisibility(fileItem.isRequired() ? View.VISIBLE : View.GONE);
        renderFiles();
        updateErrorState();
    }

    private void addFiles(List<FileFormItemValue> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (FileFormItemValue file : files) {
            fileItem.addFile(file);
        }
        fileItem.clearError();
        renderFiles();
        updateErrorState();
    }

    private void renderFiles() {
        fileListAdapter.notifyDataSetChanged();
        setActionButtonEnabled(btnUploadFile, canAddFile(false));
    }

    private boolean canAddFile(boolean showMessage) {
        if (fileItem == null) {
            return false;
        }
        int maxCount = fileItem.getFileItemConfig() == null ? 0 : fileItem.getFileItemConfig().getMaxCount();
        if (maxCount > 0 && fileItem.getFiles().size() >= maxCount) {
            if (showMessage) {
                Toast.makeText(itemView.getContext(), "最多只能上传" + maxCount + "个文件", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private void setActionButtonEnabled(Button button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1.0f : 0.45f);
    }

    @Override
    public void updateErrorState() {
        if (fileItem != null && fileItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(fileItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    private List<Map.Entry<String, String>> getFileEntries() {
        return fileItem == null || fileItem.getFiles() == null
                ? new ArrayList<>()
                : new ArrayList<>(fileItem.getFiles().entrySet());
    }

    private class FileListAdapter extends RecyclerView.Adapter<FileCardViewHolder> {
        @NonNull
        @Override
        public FileCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_form_item_file_row, parent, false);
            return new FileCardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FileCardViewHolder holder, int position) {
            holder.bind(getFileEntries().get(position));
        }

        @Override
        public int getItemCount() {
            return fileItem == null || fileItem.getFiles() == null ? 0 : fileItem.getFiles().size();
        }
    }

    private class FileCardViewHolder extends RecyclerView.ViewHolder {
        private final FileExtensionIconView fileIconView;
        private final TextView fileNameView;
        private final TextView fileMetaView;
        private final Button deleteButton;

        FileCardViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIconView = itemView.findViewById(R.id.fileIconView);
            fileNameView = itemView.findViewById(R.id.fileNameView);
            fileMetaView = itemView.findViewById(R.id.fileMetaView);
            deleteButton = itemView.findViewById(R.id.deleteFileButton);
        }

        void bind(Map.Entry<String, String> entry) {
            fileIconView.setExtension(OfflineFileHelper.getExtension(entry.getKey()));
            fileNameView.setText(entry.getKey());
            File file = OfflineFileHelper.resolveLocalFile(itemView.getContext(), fileItem.getPatternId(), entry.getValue());
            fileMetaView.setText(buildFileMeta(entry.getKey(), file));
            itemView.setOnClickListener(v -> openFile(entry.getKey(), file));
            deleteButton.setOnClickListener(v -> {
                OfflineFileHelper.deleteLocalFile(itemView.getContext(), fileItem.getPatternId(), entry.getValue());
                fileItem.removeFile(entry.getKey());
                renderFiles();
                updateErrorState();
                notifyFilesChanged();
            });
        }
    }

    private void openFile(String originalName, File file) {
        if (file == null || !file.exists()) {
            Toast.makeText(itemView.getContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri fileUri = DeviceUtility.pathToUri(file.getPath());
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setDataAndType(fileUri, getMimeType(originalName));
        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            Intent chooser = Intent.createChooser(openIntent, "打开文件");
            chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            itemView.getContext().startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(itemView.getContext(), "没有可打开该文件的应用", Toast.LENGTH_SHORT).show();
        }
    }

    private String buildFileMeta(String originalName, File file) {
        String extension = OfflineFileHelper.getExtension(originalName);
        String size = file != null && file.exists() ? formatSize(file.length()) : "文件不存在";
        return extension.isEmpty() ? size : extension.toUpperCase() + " · " + size;
    }

    private String getMimeType(String fileName) {
        String extension = OfflineFileHelper.getExtension(fileName);
        if (extension.isEmpty()) {
            return "*/*";
        }
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase(Locale.ROOT));
        return mimeType == null || mimeType.isEmpty() ? "*/*" : mimeType;
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024f);
        }
        return String.format("%.1f MB", bytes / 1024f / 1024f);
    }

    private void notifyFilesChanged() {
        if (itemView.getContext() instanceof FileUploadHost) {
            ((FileUploadHost) itemView.getContext()).onFilesChanged(fileItem);
        }
    }
}
