package com.huozige.lab.container.proxy;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.utilities.MiscUtilities;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.InputMismatchException;

/**
 * 操作文件
 * 1.15.0
 * hac_file.loadFile(fileUri)：读取文件并返回Object Url
 * hac_file.loadLatestFile()：读取上次操作的文件并返回Object Url
 * hac_file.drawWatermarkForImage(fileUri, watermark, isWatermarkTileMode, color, fileUriLocation)：为图片加水印并返回文件地址
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
        var uri = MiscUtilities.getLatestFile();
        if (uri != null) {
            return loadFile(uri.toString());
        } else {
            XLog.e("应用启动后暂未执行过文件相关操作，无法读取");
            getInterop().writeLogIntoConsole("应用启动后暂未执行过文件相关操作，无法读取");
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

        try {

            XLog.v("开始读取文件：" + fileUri);

            ContentResolver cR = getInterop().getActivityContext().getContentResolver();
            String type = cR.getType(Uri.parse(fileUri));

            if (type == null) {
                type = "application/octet-stream";
            }

            XLog.v("该文件的MIME为：" + type);

            // 形如 data:image/jpg;base64,AA67...
            String objUrl = "data:"
                    + type
                    + ";base64,"
                    + Base64.getEncoder().encodeToString(MiscUtilities.readFileToByteArray(getInterop().getActivityContext(), Uri.parse(fileUri)));

            XLog.v("文件读取完毕，Object Url的总长度为：" + objUrl.length());

            return objUrl;
        } catch (Exception e) {
            XLog.e("读取文件时出错：" + fileUri, e);
            getInterop().writeLogIntoConsole("读取文件时出错：" + fileUri + " \r\n" + e);

            return null;
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
        try {
            if (fileUri == null || fileUri.isEmpty()) {
                fileUri = MiscUtilities.getLatestFile().toString();
            }

            byte[] source = MiscUtilities.readFileToByteArray(this.getInterop().getActivityContext(), Uri.parse(fileUri));
            Bitmap b = BitmapFactory.decodeByteArray(source, 0, source.length);

            if (b == null) {
                throw new InputMismatchException("该文件不是有效的图片类型。");
            }

            XLog.v("准备加水印：" + watermark);

            var fontSize = Integer.max(b.getHeight() / 200, 20);
            var fontColor = Color.LTGRAY; // 默认颜色
            if (color != null && !color.isEmpty()) {
                fontColor = Color.parseColor(color);
            }

            WatermarkText watermarkText = new WatermarkText(watermark)
                    .setTextColor(fontColor)
                    .setTextShadow(0.1f, (float) fontSize / 4, (float) fontSize / 4, Color.WHITE)
                    .setTextAlpha(150)
                    .setPositionX(0.5)
                    .setPositionY(0.5)
                    .setTextSize(fontSize);

            if (isWatermarkTileMode) {
                watermarkText.setRotation(30);
            }

            var builder = WatermarkBuilder
                    .create(this.getInterop().getActivityContext(), b)
                    .loadWatermarkText(watermarkText)
                    .setTileMode(isWatermarkTileMode);

            b = builder.getWatermark().getOutputImage();

            // 创建图片文件
            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_WM.jpg");
            FileOutputStream fileStream = new FileOutputStream(imageFile);
            XLog.v("水印添加完毕，准备存储为新文件：" + imageFile.getPath());

            // 将 Bitmap 保存到文件
            b.compress(Bitmap.CompressFormat.JPEG, 100, fileStream);

            // 通知系统相册刷新，异步操作，无需等待
            MediaScannerConnection.scanFile(this.getInterop().getActivityContext(),
                    new String[]{imageFile.getAbsolutePath()}, null,
                    (path, uri) -> {
                        // 图片保存成功后的回调
                        // 可以在这里进行额外的操作，比如显示图片等
                        XLog.v("图片已被相册收录：%s", uri);
                    });

            Uri resultFile = MiscUtilities.toUri(this.getInterop().getActivityContext(), imageFile.getPath());
            MiscUtilities.registryLatestFile(resultFile);

            XLog.v("水印处理完毕，即将返回");
            this.getInterop().setInputValue(fileUriLocation, resultFile.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
