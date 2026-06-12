package com.huozige.lab.container.offlineform.formitem.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import com.huozige.lab.container.offlineform.model.formitem.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FileFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.FileItemConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public final class OfflineFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String FILES_DIR = "files";

    private OfflineFileHelper() {
    }

    public static FileFormItemValue saveFile(Context context, String patternId, FileFormItem item, Uri sourceUri) throws Exception {
        String originalName = getDisplayName(context, sourceUri);
        FileItemConfig config = item.getFileItemConfig() == null ? new FileItemConfig() : item.getFileItemConfig();
        validateFile(context, sourceUri, originalName, config);

        File outputFile = createOutputFile(context, patternId, item.getId(), getExtension(originalName));
        copySourceToFile(context, sourceUri, outputFile, config.getMaxFileSizeKb());

        FileFormItemValue value = new FileFormItemValue();
        value.setOriginalName(originalName);
        value.setFileName(outputFile.getName());
        return value;
    }

    public static File resolveLocalFile(Context context, String patternId, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        return new File(getFilesDir(context, patternId), fileName);
    }

    public static void deleteLocalFile(Context context, String patternId, String fileName) {
        File file = resolveLocalFile(context, patternId, fileName);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private static void validateFile(Context context, Uri sourceUri, String originalName, FileItemConfig config) {
        if (config.getMaxFileSizeKb() > 0) {
            long fileSize = getFileSize(context, sourceUri);
            if (fileSize > config.getMaxFileSizeKb() * 1024L) {
                throw new IllegalArgumentException("文件超过大小限制：" + originalName);
            }
        }

        Set<String> allowedExtensions = parseAllowedExtensions(config.getAllowedExtensions());
        if (!allowedExtensions.isEmpty() && !allowedExtensions.contains(getExtension(originalName).toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("不支持的文件类型：" + originalName);
        }
    }

    private static Set<String> parseAllowedExtensions(String value) {
        Set<String> result = new HashSet<>();
        if (value == null || value.trim().isEmpty()) {
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
        return displayName == null || displayName.isEmpty() ? "未命名文件" : displayName;
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
        File dir = getFilesDir(context, patternId);
        dir.mkdirs();
        return new File(dir, sanitizePathSegment(patternId) + "_"
                + sanitizePathSegment(fieldId) + "_"
                + UUID.randomUUID().toString() + "." + sanitizeExtension(extension));
    }

    private static void copySourceToFile(Context context, Uri sourceUri, File outputFile, int maxFileSizeKb) throws Exception {
        try (InputStream input = context.getContentResolver().openInputStream(sourceUri);
             FileOutputStream output = new FileOutputStream(outputFile)) {
            if (input == null) {
                throw new IllegalArgumentException("无法读取文件");
            }
            byte[] buffer = new byte[8192];
            int length;
            long copiedBytes = 0;
            long maxBytes = maxFileSizeKb <= 0 ? 0 : maxFileSizeKb * 1024L;
            while ((length = input.read(buffer)) >= 0) {
                copiedBytes += length;
                if (maxBytes > 0 && copiedBytes > maxBytes) {
                    throw new IllegalArgumentException("文件超过大小限制");
                }
                output.write(buffer, 0, length);
            }
        } catch (Exception e) {
            if (outputFile.exists()) {
                outputFile.delete();
            }
            throw e;
        }
    }

    private static File getFilesDir(Context context, String patternId) {
        return new File(context.getExternalFilesDir(null), ROOT_DIR + File.separator
                + sanitizePathSegment(patternId) + File.separator
                + FILES_DIR);
    }

    public static String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int index = fileName.lastIndexOf('.');
        return index < 0 || index == fileName.length() - 1 ? "" : fileName.substring(index + 1);
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

    private static String sanitizeExtension(String value) {
        if (value == null || value.isEmpty()) {
            return "file";
        }
        String extension = value.replaceAll("[^a-zA-Z0-9]", "");
        return extension.isEmpty() ? "file" : extension;
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
