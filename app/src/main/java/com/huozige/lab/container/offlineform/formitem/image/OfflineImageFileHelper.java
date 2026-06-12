package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.huozige.lab.container.offlineform.model.formitem.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public final class OfflineImageFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String FILES_DIR = "files";

    private OfflineImageFileHelper() {
    }

    public static ImageFormItemValue saveCapturedImage(Context context, String patternId, ImageFormItem item, Uri sourceUri) throws Exception {
        ImageCompressionOptions options = item.getCompression() == null ? new ImageCompressionOptions() : item.getCompression();
        File outputFile = createOutputFile(context, patternId, item.getId(), options.isEnableCompression() ? "jpg" : getSourceExtension(context, sourceUri));
        if (options.isEnableCompression()) {
            Bitmap source = decodeBitmap(context, sourceUri, options.getMaxLongEdge());
            try {
                compressToFile(source, outputFile, options);
            } finally {
                source.recycle();
            }
        } else {
            copySourceToFile(context, sourceUri, outputFile);
        }

        ImageFormItemValue value = new ImageFormItemValue();
        value.setFileName(outputFile.getName());
        return value;
    }

    public static File resolveLocalFile(Context context, String patternId, ImageFormItemValue value) {
        if (value == null || value.getFileName() == null || value.getFileName().isEmpty()) {
            return null;
        }
        return resolveLocalFile(context, patternId, value.getFileName());
    }

    public static File resolveLocalFile(Context context, String patternId, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        return new File(getFilesDir(context, patternId), fileName);
    }

    public static void deleteLocalFile(Context context, String patternId, ImageFormItemValue value) {
        File file = resolveLocalFile(context, patternId, value);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private static Bitmap decodeBitmap(Context context, Uri sourceUri, int maxLongEdge) throws Exception {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        try (InputStream input = context.getContentResolver().openInputStream(sourceUri)) {
            BitmapFactory.decodeStream(input, null, bounds);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateInSampleSize(bounds, maxLongEdge);
        try (InputStream input = context.getContentResolver().openInputStream(sourceUri)) {
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            if (bitmap == null) {
                throw new IllegalArgumentException("无法读取图片");
            }
            return bitmap;
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int maxLongEdge) {
        if (options.outWidth <= 0 || options.outHeight <= 0) {
            return 1;
        }
        if (maxLongEdge <= 0 || options.outWidth <= maxLongEdge && options.outHeight <= maxLongEdge) {
            return 1;
        }
        int sampleSize = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        while (width / sampleSize > maxLongEdge || height / sampleSize > maxLongEdge) {
            sampleSize *= 2;
        }
        return sampleSize;
    }

    private static File createOutputFile(Context context, String patternId, String fieldId, String extension) {
        File dir = getFilesDir(context, patternId);
        dir.mkdirs();
        return new File(dir, sanitizePathSegment(patternId) + "_"
                + sanitizePathSegment(fieldId) + "_"
                + UUID.randomUUID().toString() + "." + sanitizeExtension(extension));
    }

    private static void copySourceToFile(Context context, Uri sourceUri, File outputFile) throws Exception {
        try (InputStream input = context.getContentResolver().openInputStream(sourceUri);
             FileOutputStream output = new FileOutputStream(outputFile)) {
            if (input == null) {
                throw new IllegalArgumentException("无法读取图片");
            }
            byte[] buffer = new byte[8192];
            int length;
            while ((length = input.read(buffer)) >= 0) {
                output.write(buffer, 0, length);
            }
        }
    }

    private static void compressToFile(Bitmap bitmap, File outputFile, ImageCompressionOptions options) throws Exception {
        int quality = normalizeQuality(options.getJpegQuality());
        int minQuality = normalizeQuality(options.getMinQuality());
        int maxFileSizeKb = Math.max(0, options.getMaxFileSizeKb());
        do {
            try (FileOutputStream output = new FileOutputStream(outputFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output);
            }
            if (maxFileSizeKb <= 0 || outputFile.length() <= maxFileSizeKb * 1024L || quality <= minQuality) {
                return;
            }
            quality = Math.max(minQuality, quality - 10);
        } while (true);
    }

    private static int normalizeQuality(int quality) {
        if (quality <= 0) {
            return ImageCompressionOptions.DEFAULT_JPEG_QUALITY;
        }
        return Math.min(100, Math.max(1, quality));
    }

    private static File getFilesDir(Context context, String patternId) {
        return new File(context.getExternalFilesDir(null), ROOT_DIR + File.separator
                + sanitizePathSegment(patternId) + File.separator
                + FILES_DIR);
    }

    private static String getSourceExtension(Context context, Uri sourceUri) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(sourceUri));
        return extension == null || extension.isEmpty() ? "jpg" : extension;
    }

    private static String sanitizeExtension(String value) {
        if (value == null || value.isEmpty()) {
            return "jpg";
        }
        String extension = value.replaceAll("[^a-zA-Z0-9]", "");
        return extension.isEmpty() ? "jpg" : extension;
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
