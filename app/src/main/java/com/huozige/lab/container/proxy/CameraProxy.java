package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.utilities.EventUtility;

/**
 * 操作摄像头
 * 1.15.0
 * camera.takePhoto(isSnapshot, filePathLocation)：拍照、加水印并返回
 * camera.takeVideo(isSnapshot, filePathLocation)：拍视频并返回
 */
public class CameraProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "camera";
    }

    ActivityResultLauncher<Intent> _imageCaptureChooser; // 拍摄照片的调用器
    String fileUriCell;

    /**
     * 拍照
     * @param isSnapshot 是否为小尺寸照片（长边1280）
     * @param fileUriLocation 存放Uri的单元格
     */
    @JavascriptInterface
    public void takePhoto(boolean isSnapshot, String fileUriLocation) {
        fileUriCell = fileUriLocation;

        // 配置参数
        Intent cameraIntent = new Intent(this.getInterop().getActivityContext(), CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, isSnapshot ? CameraViewActivity.OPERATION_TAKE_PHOTO_SNAPSHOT : CameraViewActivity.OPERATION_TAKE_PHOTO);

        // 弹出相机
        _imageCaptureChooser.launch(cameraIntent);

        EventUtility.logEvent(this.getInterop().getActivityContext(),"use_camera_feature", "takePhoto");
    }

    /**
     * 拍摄视频
     * @param isSnapshot 是否为小尺寸
     * @param filePathLocation 存放文件Uri的单元格
     */
    @JavascriptInterface
    public void takeVideo(boolean isSnapshot, String filePathLocation) {
        fileUriCell = filePathLocation;

        // 配置参数
        Intent cameraIntent = new Intent(this.getInterop().getActivityContext(), CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, isSnapshot ? CameraViewActivity.OPERATION_TAKE_VIDEO_SNAPSHOT : CameraViewActivity.OPERATION_TAKE_VIDEO);

        // 弹出相机
        _imageCaptureChooser.launch(cameraIntent);

        EventUtility.logEvent(this.getInterop().getActivityContext(),"use_camera_feature", "takeVideo");
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        _imageCaptureChooser = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                        // 从Intent中读取图片和视频的URI
                        this.getInterop().setInputValue(fileUriCell, result.getData().getStringExtra(CameraViewActivity.EXTRA_OUT_URI));

                    }
                });
    }
}
