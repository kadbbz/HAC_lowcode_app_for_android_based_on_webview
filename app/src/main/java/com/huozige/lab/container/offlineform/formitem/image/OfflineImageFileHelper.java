package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.ImageWatermarkField;
import com.huozige.lab.container.offlineform.model.formitem.ImageWatermarkOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class OfflineImageFileHelper {
    private static final String ROOT_DIR = "offlinePlus";
    private static final String FILES_DIR = "files";

    private OfflineImageFileHelper() {
    }

    public static ImageFormItemValue saveImage(Context context, String patternId, ImageFormItem item, Uri sourceUri) throws Exception {
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

        return buildValue(outputFile);
    }

    private static ImageFormItemValue buildValue(File outputFile) {
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
        deleteFile(file);
    }

    public static void deleteLocalFile(Context context, String patternId, String fileName) {
        File file = resolveLocalFile(context, patternId, fileName);
        deleteFile(file);
    }

    private static void deleteFile(File file) {
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
                throw new IllegalArgumentException(context.getString(R.string.offline_error_image_read_failed));
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
                throw new IllegalArgumentException(context.getString(R.string.offline_error_image_read_failed));
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

    public static void writeWatermarkToFile(Context context, Uri sourceUri, File outputFile, List<String> watermarkLines) throws Exception {
        writeWatermarkToFile(context, sourceUri, outputFile, watermarkLines, new ImageCompressionOptions());
    }

    private static void writeWatermarkToFile(Context context, Uri sourceUri, File outputFile, List<String> watermarkLines, ImageCompressionOptions options) throws Exception {
        if (watermarkLines == null || watermarkLines.isEmpty()) {
            return;
        }
        Bitmap source = decodeBitmap(context, sourceUri, options.isEnableCompression() ? options.getMaxLongEdge() : 0);
        Bitmap watermarked = null;
        try {
            watermarked = addWatermark(source, watermarkLines);
            saveWatermarkedToFile(watermarked, outputFile, options);
        } finally {
            if (watermarked != null && watermarked != source) {
                watermarked.recycle();
            }
            source.recycle();
        }
    }

    private static Bitmap addWatermark(Bitmap source, List<String> lines) {
        Bitmap result = source.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(result);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Math.max(result.getWidth() / 24f, 28f));
        textPaint.setShadowLayer(4f, 1f, 1f, Color.BLACK);

        float padding = Math.max(result.getWidth() / 40f, 24f);
        float textWidth = getMaxTextWidth(textPaint, lines);
        float maxTextWidth = result.getWidth() - padding * 2;
        if (textWidth > maxTextWidth && textWidth > 0) {
            textPaint.setTextSize(textPaint.getTextSize() * maxTextWidth / textWidth);
        }

        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.argb(110, 0, 0, 0));
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float lineHeight = metrics.descent - metrics.ascent;
        float backgroundHeight = lineHeight * lines.size() + padding;
        canvas.drawRect(0, result.getHeight() - backgroundHeight, result.getWidth(), result.getHeight(), backgroundPaint);
        float baseline = result.getHeight() - backgroundHeight + padding / 2f - metrics.ascent;
        for (String line : lines) {
            canvas.drawText(line, padding, baseline, textPaint);
            baseline += lineHeight;
        }
        return result;
    }

    private static float getMaxTextWidth(Paint paint, List<String> lines) {
        float maxWidth = 0;
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, paint.measureText(line));
        }
        return maxWidth;
    }

    private static void saveWatermarkedToFile(Bitmap bitmap, File outputFile, ImageCompressionOptions options) throws Exception {
        if (options.isEnableCompression()) {
            compressToFile(bitmap, outputFile, options);
            return;
        }
        try (FileOutputStream output = new FileOutputStream(outputFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        }
    }

    public static ArrayList<String> buildWatermarkCustomLines(ImageFormItem item) {
        ImageWatermarkOptions watermark = item == null ? null : item.getWatermark();
        ArrayList<String> lines = new ArrayList<>();
        if (watermark == null) {
            return lines;
        }
        if (watermark.getItems() != null) {
            for (ImageWatermarkField itemField : watermark.getItems()) {
                String line = buildWatermarkCustomLine(itemField);
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    public static boolean isTimestampWatermarkEnabled(ImageFormItem item) {
        ImageWatermarkOptions watermark = item == null ? null : item.getWatermark();
        return watermark != null && watermark.isEnableTimestamp();
    }

    private static String buildWatermarkCustomLine(ImageWatermarkField item) {
        if (item == null) {
            return "";
        }
        String key = normalizeWatermarkText(item.getKey());
        String value = normalizeWatermarkText(item.getValue());
        if (key.isEmpty() && value.isEmpty()) {
            return "";
        }
        return key + "：" + value;
    }

    private static String normalizeWatermarkText(String value) {
        return value == null ? "" : value.trim();
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
