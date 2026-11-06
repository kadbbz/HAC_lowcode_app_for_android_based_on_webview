package com.huozige.lab.container.offlineform.util;

import android.content.Context;
import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.utilities.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Utils {
    public static final String ROOT_DIR = "offlinePlus";
    public static final String FILES_DIR = "files";

    public static File resolveLocalFile(Context context, String patternId, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        return new File(getFilesDir(context, patternId), fileName);
    }

    public static void copyUriToFile(Context context, Uri sourceUri, File outputFile, String readFailedMessage, int maxFileSizeKb, String fileSizeExceededMessage) throws Exception {
        try (InputStream input = context.getContentResolver().openInputStream(sourceUri);
             FileOutputStream output = new FileOutputStream(outputFile)) {
            if (input == null) {
                throw new IllegalArgumentException(readFailedMessage);
            }
            byte[] buffer = new byte[8192];
            int length;
            long copiedBytes = 0;
            long maxBytes = maxFileSizeKb <= 0 ? 0 : maxFileSizeKb * 1024L;
            while ((length = input.read(buffer)) >= 0) {
                copiedBytes += length;
                if (maxBytes > 0 && copiedBytes > maxBytes) {
                    throw new IllegalArgumentException(fileSizeExceededMessage);
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

    public static String serializeAttachmentValues(List<AttachmentFormItemValue> values) {
        return JSON.toJSONString(values == null ? new ArrayList<>() : values);
    }

    public static List<AttachmentFormItemValue> parseAttachmentValues(String value, Predicate<AttachmentFormItemValue> validator) {
        List<AttachmentFormItemValue> result = new ArrayList<>();
        if (StringUtils.isNullOrBlank(value)) {
            return result;
        }
        try {
            List<AttachmentFormItemValue> attachments = JSON.parseArray(value, AttachmentFormItemValue.class);
            if (attachments == null) {
                return result;
            }
            for (AttachmentFormItemValue attachment : attachments) {
                if (attachment != null && validator.test(attachment)) {
                    result.add(attachment);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return result;
    }

    public static File getFilesDir(Context context, String patternId) {
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

    public static String sanitizeExtension(String value, String defaultExtension) {
        if (value == null || value.isEmpty()) {
            return defaultExtension;
        }
        String extension = value.replaceAll("[^a-zA-Z0-9]", "");
        return extension.isEmpty() ? defaultExtension : extension;
    }

    public static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
