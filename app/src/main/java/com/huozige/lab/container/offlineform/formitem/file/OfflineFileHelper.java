package com.huozige.lab.container.offlineform.formitem.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileItemConfig;
import com.huozige.lab.container.offlineform.util.Utils;
import com.huozige.lab.container.utilities.StringUtils;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public final class OfflineFileHelper {

    private OfflineFileHelper() {
    }

    public static AttachmentFormItemValue saveFile(Context context, String patternId, FileFormItem item, Uri sourceUri) throws Exception {
        String originalName = getDisplayName(context, sourceUri);
        FileItemConfig config = item.getFileItemConfig() == null ? new FileItemConfig() : item.getFileItemConfig();
        validateFile(context, sourceUri, originalName, config);

        File outputFile = createOutputFile(context, patternId, item.getId(), Utils.getExtension(originalName));
        Utils.copyUriToFile(
                context,
                sourceUri,
                outputFile,
                context.getString(R.string.offline_error_file_read_failed),
                config.getMaxFileSizeKb(),
                context.getString(R.string.offline_error_file_size));

        AttachmentFormItemValue value = new AttachmentFormItemValue();
        value.setOriginalName(originalName);
        value.setFileName(outputFile.getName());
        return value;
    }

    public static void deleteLocalFile(Context context, String patternId, String fileName) {
        File file = Utils.resolveLocalFile(context, patternId, fileName);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private static void validateFile(Context context, Uri sourceUri, String originalName, FileItemConfig config) {
        if (config.getMaxFileSizeKb() > 0) {
            long fileSize = getFileSize(context, sourceUri);
            if (fileSize > config.getMaxFileSizeKb() * 1024L) {
                throw new IllegalArgumentException(context.getString(R.string.offline_error_file_size_with_name, originalName));
            }
        }

        Set<String> allowedExtensions = parseAllowedExtensions(config.getAllowedExtensions());
        if (!allowedExtensions.isEmpty() && !allowedExtensions.contains(Utils.getExtension(originalName).toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException(context.getString(R.string.offline_error_file_type_not_supported, originalName));
        }
    }

    private static Set<String> parseAllowedExtensions(String value) {
        Set<String> result = new HashSet<>();
        if (StringUtils.isNullOrBlank(value)) {
            return result;
        }
        String[] parts = value.split(",");
        for (String part : parts) {
            String extension = part.trim().toLowerCase(Locale.ROOT);
            if (extension.startsWith(".")) {
                extension = extension.substring(1);
            }
            if (!extension.isEmpty()) {
                result.add(extension);
            }
        }
        return result;
    }

    public static String getDisplayName(Context context, Uri sourceUri) {
        String displayName = null;
        try (Cursor cursor = context.getContentResolver().query(sourceUri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    displayName = cursor.getString(nameIndex);
                }
            }
        }
        if (displayName == null || displayName.isEmpty()) {
            displayName = sourceUri.getLastPathSegment();
        }
        return displayName == null || displayName.isEmpty() ? context.getString(R.string.offline_text_unnamed_file) : displayName;
    }

    private static long getFileSize(Context context, Uri sourceUri) {
        try (Cursor cursor = context.getContentResolver().query(sourceUri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (sizeIndex >= 0) {
                    return cursor.getLong(sizeIndex);
                }
            }
        }
        return 0;
    }

    private static File createOutputFile(Context context, String patternId, String fieldId, String extension) {
        File dir = Utils.getFilesDir(context, patternId);
        dir.mkdirs();
        return new File(dir, Utils.sanitizePathSegment(patternId) + "_"
                + Utils.sanitizePathSegment(fieldId) + "_"
                + UUID.randomUUID().toString() + "." + Utils.sanitizeExtension(extension, "file"));
    }

    public static String[] buildMimeTypes(FileItemConfig config) {
        Set<String> mimeTypes = new LinkedHashSet<>();
        Set<String> extensions = parseAllowedExtensions(config == null ? null : config.getAllowedExtensions());
        for (String extension : extensions) {
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if (mimeType != null && !mimeType.isEmpty()) {
                mimeTypes.add(mimeType);
            }
        }
        return mimeTypes.isEmpty() ? new String[]{"*/*"} : mimeTypes.toArray(new String[0]);
    }

}
