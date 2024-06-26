package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;

/**
 * 操作摄像头
 * 1.15.0
 * camera.takePhoto(isSnapshot, filePathLocation)：拍照并返回
 * camera.takeVideo(isSnapshot, filePathLocation)：拍视频并返回
 * 1.17.0
 * camera.takePhotoAsync(isSnapshot, ticket)：异步拍照并返回
 * camera.takeVideoAsync(isSnapshot, filePathLocation)：异步拍视频并返回
 */
public class CameraProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "camera";
    }

    ActivityResultLauncher<Intent> _imageCaptureChooser; // 拍摄照片的调用器

    private void startTakePhoto(boolean isSnapshot) {

        writeInfoLog("开始拍摄照片" + (isSnapshot ? "（小尺寸）" : ""));

        // 配置参数
        Intent cameraIntent = createIntent(CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, isSnapshot ? CameraViewActivity.OPERATION_TAKE_PHOTO_SNAPSHOT : CameraViewActivity.OPERATION_TAKE_PHOTO);

        // 弹出相机
        _imageCaptureChooser.launch(cameraIntent);
    }

    /**
     * 拍照
     *
     * @param isSnapshot      是否为小尺寸照片（长边1280）
     * @param fileUriLocation 存放Uri的单元格
     */
    @JavascriptInterface
    public void takePhoto(boolean isSnapshot, String fileUriLocation) {
        registryForFeatureUsageAnalyze("use_camera_feature", "takePhoto");

        registryPayloadCellLocation(fileUriLocation);

        startTakePhoto(isSnapshot);
    }

    /**
     * 拍照
     *
     * @param isSnapshot 是否为小尺寸照片（长边1280）
     * @param ticket     回调
     */
    @JavascriptInterface
    public void takePhotoAsync(boolean isSnapshot, String ticket) {
        registryForFeatureUsageAnalyze("use_camera_feature", "takePhotoAsync");

        registryCallbackTicket(ticket);

        startTakePhoto(isSnapshot);
    }

    private void startTakeVideo(boolean isSnapshot) {

        writeInfoLog("开始拍摄视频" + (isSnapshot ? "（小尺寸）" : ""));

        // 配置参数
        Intent cameraIntent = createIntent(CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, isSnapshot ? CameraViewActivity.OPERATION_TAKE_VIDEO_SNAPSHOT : CameraViewActivity.OPERATION_TAKE_VIDEO);

        // 弹出相机
        _imageCaptureChooser.launch(cameraIntent);

    }

    /**
     * 拍摄视频
     *
     * @param isSnapshot       是否为小尺寸
     * @param filePathLocation 存放文件Uri的单元格
     */
    @JavascriptInterface
    public void takeVideo(boolean isSnapshot, String filePathLocation) {
        registryForFeatureUsageAnalyze("use_camera_feature", "takeVideo");

        registryPayloadCellLocation(filePathLocation);

        startTakeVideo(isSnapshot);
    }

    /**
     * 拍摄视频
     *
     * @param isSnapshot 是否为小尺寸
     * @param ticket     回调
     */
    @JavascriptInterface
    public void takeVideoAsync(boolean isSnapshot, String ticket) {
        registryForFeatureUsageAnalyze("use_camera_feature", "takeVideoAsync");

        registryCallbackTicket(ticket);

        startTakeVideo(isSnapshot);
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _imageCaptureChooser = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                        var file = result.getData().getStringExtra(CameraViewActivity.EXTRA_OUT_URI);
                        writeInfoLog("照片/视频拍摄完成，保存到：" + file);
                        // 从Intent中读取图片和视频的URI
                        callback(CallbackParams.success(file));
                    }
                });
    }
}
