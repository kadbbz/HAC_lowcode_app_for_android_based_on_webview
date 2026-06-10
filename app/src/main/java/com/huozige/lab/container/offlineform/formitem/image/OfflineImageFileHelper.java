package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.huozige.lab.container.offlineform.model.formitem.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public final class OfflineImageFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String FILES_DIR = "files";

    private OfflineImageFileHelper() {
    }

    public static ImageFormItemValue saveCapturedImage(Context context, String patternId, String recordId, ImageFormItem item, Uri sourceUri) throws Exception {
        ImageCompressionOptions options = item.getCompression() == null ? new ImageCompressionOptions() : item.getCompression();
        Bitmap source = decodeBitmap(context, sourceUri, options.getMaxLongEdge());
        File outputFile = createOutputFile(context, patternId, recordId, item.getId());
        try {
            compressToFile(source, outputFile, options);
        } finally {
            source.recycle();
        }

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(outputFile.getAbsolutePath(), bounds);

        ImageFormItemValue value = new ImageFormItemValue();
        value.setFileId(outputFile.getName().replace(".jpg", ""));
        value.setLocalPath(toRelativePath(patternId, recordId, item.getId(), outputFile.getName()));
        value.setFileName(outputFile.getName());
        value.setMimeType("image/jpeg");
        value.setSize(outputFile.length());
        value.setWidth(bounds.outWidth);
        value.setHeight(bounds.outHeight);
        value.setCreatedAt(System.currentTimeMillis());
        return value;
    }

    public static File resolveLocalFile(Context context, ImageFormItemValue value) {
        if (value == null || value.getLocalPath() == null || value.getLocalPath().isEmpty()) {
            return null;
        }
        return new File(context.getExternalFilesDir(null), value.getLocalPath());
    }

    public static void deleteLocalFile(Context context, ImageFormItemValue value) {
        File file = resolveLocalFile(context, value);
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

    private static File createOutputFile(Context context, String patternId, String recordId, String fieldId) {
        File dir = new File(context.getExternalFilesDir(null), ROOT_DIR + File.separator
                + sanitizePathSegment(patternId) + File.separator
                + FILES_DIR + File.separator
                + sanitizePathSegment(recordId) + File.separator
                + sanitizePathSegment(fieldId));
        dir.mkdirs();
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(new Date());
        return new File(dir, "photo_" + time + "_" + UUID.randomUUID().toString() + ".jpg");
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

    private static String toRelativePath(String patternId, String recordId, String fieldId, String fileName) {
        return ROOT_DIR + "/" + sanitizePathSegment(patternId) + "/" + FILES_DIR + "/"
                + sanitizePathSegment(recordId) + "/" + sanitizePathSegment(fieldId) + "/" + fileName;
    }

    private static String sanitizePathSegment(String value) {
        if (value == null || value.isEmpty()) {
            return "_";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
