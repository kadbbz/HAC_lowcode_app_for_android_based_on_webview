package com.huozige.lab.container.proxy;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * 操作文件
 * 1.15.0
 * hac_file.loadFile(fileUri)：读取文件并返回Object Url
 * hac_file.loadLatestFile()：读取上次操作的文件并返回Object Url
 * hac_file.drawWatermarkForImage(fileUri, watermark, isWatermarkTileMode, color, fileUriLocation)：为图片加水印并返回文件地址
 * 1.17.0
 * hac_file.drawWatermarkForImage(fileUri, watermark, isWatermarkTileMode, color, ticket)：异步为图片加水印并返回文件地址
 */
public class FileProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "hac_file";
    }

    /**
     * 读取上次操作的文件
     *
     * @return Object Url类型的文件内容
     */
    @JavascriptInterface
    public String loadLatestFile() {

        registryForFeatureUsageAnalyze("use_file_feature", "loadLatestFile");

        var uri = DeviceUtility.getLatestFile();
        if (uri != null) {
            return loadFile(uri.toString());
        } else {
            writeErrorLog("应用启动后暂未执行过文件相关操作，无法读取");
            return null;
        }
    }

    /**
     * 读取指定Uri的本地文件
     *
     * @param fileUri 文件的URI
     * @return Object Url类型的文件内容
     */
    @JavascriptInterface
    public String loadFile(String fileUri) {

        registryForFeatureUsageAnalyze("use_file_feature", "loadFile");

        try {

            writeInfoLog("开始读取文件：" + fileUri);

            ContentResolver cR = getWebView().getContext().getContentResolver();
            String type = cR.getType(Uri.parse(fileUri));

            if (type == null) {
                type = "application/octet-stream";
            }

            writeInfoLog("该文件的MIME为：" + type);

            // 形如 data:image/jpg;base64,AA67...
            String objUrl = "data:"
                    + type
                    + ";base64,"
                    + Base64.getEncoder().encodeToString(DeviceUtility.readFileToByteArray(Uri.parse(fileUri)));

            writeInfoLog("文件读取完毕，Object Url的总长度为：" + objUrl.length());

            return objUrl;
        } catch (Exception e) {
            writeErrorLog("读取文件时出错：" + fileUri + "，详情：" + e);

            return null;
        }


    }

    private void startDrawWatermarkForImage(String fileUri, String watermark, boolean isWatermarkTileMode, String color) {
        try {
            if (fileUri == null || fileUri.isEmpty()) {
                fileUri = DeviceUtility.getLatestFile().toString();
            }

            byte[] source = DeviceUtility.readFileToByteArray(Uri.parse(fileUri));
            Bitmap b = BitmapFactory.decodeByteArray(source, 0, source.length);

            if (b == null) {
                writeErrorLog("文件不是图片类型，或者图片的格式不被支持：" + fileUri);
                callback(CallbackParams.error("The file is not image or the format is not supported."));
                return;
            }

            writeInfoLog("准备加水印：" + watermark);

            var fontSize = Integer.max(b.getHeight() / 200, 20);
            var fontColor = Color.LTGRAY; // 默认颜色
            if (color != null && !color.isEmpty()) {
                fontColor = Color.parseColor(color);
            }

            WatermarkText watermarkText = new WatermarkText(watermark)
                    .setTextColor(fontColor)
                    .setTextShadow(0.1f, 1, 1, Color.WHITE)
                    .setTextAlpha(150)
                    .setPositionX(0.5)
                    .setPositionY(0.5)
                    .setTextSize(fontSize);

            if (isWatermarkTileMode) {
                watermarkText.setRotation(30);
            }

            var builder = WatermarkBuilder
                    .create(getWebView().getContext(), b)
                    .loadWatermarkText(watermarkText)
                    .setTileMode(isWatermarkTileMode);

            b = builder.getWatermark().getOutputImage();

            // 创建图片文件
            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_WM.jpg");
            FileOutputStream fileStream = new FileOutputStream(imageFile);
            writeInfoLog("水印添加完毕，准备存储为新文件：" + imageFile.getPath());

            // 将 Bitmap 保存到文件
            b.compress(Bitmap.CompressFormat.JPEG, 100, fileStream);

            // 通知系统相册刷新，异步操作，无需等待
            MediaScannerConnection.scanFile(getWebView().getContext(),
                    new String[]{imageFile.getAbsolutePath()}, null,
                    (path, uri) -> {
                        // 图片保存成功后的回调
                        // 可以在这里进行额外的操作，比如显示图片等
                        writeInfoLog("图片已被相册收录：" + uri);
                    });

            Uri resultFile = DeviceUtility.pathToUri(imageFile.getPath());
            DeviceUtility.registryLatestFile(resultFile);

            writeInfoLog("水印处理完毕，即将返回");
            callback(CallbackParams.success(resultFile.toString()));

        } catch (Exception e) {
            writeErrorLog("给图片[" + fileUri + "]添加水印时出错，详情：" + e);
            callback(CallbackParams.error(e.toString()));
        }
    }

    /**
     * 为图片加水印，将结果的Uri写入单元格
     *
     * @param fileUri             图片文件的Uri，空字符串则读取上次操作的文件
     * @param watermark           水印文字
     * @param isWatermarkTileMode 是否为平铺模式
     * @param color               水印颜色
     * @param fileUriLocation     处理后的文件路径
     */
    @JavascriptInterface
    public void drawWatermarkForImage(String fileUri, String watermark, boolean isWatermarkTileMode, String color, String fileUriLocation) {
        registryForFeatureUsageAnalyze("use_file_feature", "drawWatermarkForImage");
        registryPayloadCellLocation(fileUriLocation);
        startDrawWatermarkForImage(fileUri, watermark, isWatermarkTileMode, color);
    }

    /**
     * 异步为图片加水印，将结果的Uri写入单元格
     *
     * @param fileUri             图片文件的Uri，空字符串则读取上次操作的文件
     * @param watermark           水印文字
     * @param isWatermarkTileMode 是否为平铺模式
     * @param color               水印颜色
     * @param ticket              回调
     */
    @JavascriptInterface
    public void drawWatermarkForImageAsync(String fileUri, String watermark, boolean isWatermarkTileMode, String color, String ticket) {
        registryForFeatureUsageAnalyze("use_file_feature", "drawWatermarkForImageAsync");
        registryCallbackTicket(ticket);
        startDrawWatermarkForImage(fileUri, watermark, isWatermarkTileMode, color);
    }

}
