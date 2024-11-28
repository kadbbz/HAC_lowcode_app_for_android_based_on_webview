package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.HACQRCodeScanActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.king.camera.scan.CameraScan;
import com.king.zxing.util.CodeUtils;

/**
 * 操作摄像头
 * 1.15.0
 * camera.takePhoto(isSnapshot, filePathLocation)：拍照并返回
 * camera.takeVideo(isSnapshot, filePathLocation)：拍视频并返回
 * 1.17.0
 * camera.takePhotoAsync(isSnapshot, ticket)：异步拍照并返回
 * camera.takeVideoAsync(isSnapshot, filePathLocation)：异步拍视频并返回
 * 1.19.0
 * camera。scanCodeAsync（supportPickupFromGallery, ticket）：异步扫描二维码
 */
public class CameraProxy extends AbstractProxy {
    @Override
    public String getName() {
        return "camera";
    }

    ActivityResultLauncher<Intent> _imageCaptureChooser; // 拍摄照片的调用器
    ActivityResultLauncher<Intent> _arcZxingLite; // 调用摄像头扫码页面的调用器
    ActivityResultLauncher<String> _contentChooser; // 从相册中选取照片的调用器

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

    @JavascriptInterface
    public void scanCodeAsync(boolean supportPickupFromGallery, String ticket) {
        registryForFeatureUsageAnalyze("use_camera_feature", "scanCodeAsync");

        registryCallbackTicket(ticket);

        if (supportPickupFromGallery) {
            riseActionSheet();
        } else {
            scanViaCamera();
        }

    }

    private void riseActionSheet() {
        Dialog dialog;
        View inflate;

        dialog = new Dialog(this.getWebView().getContext(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this.getWebView().getContext()).inflate(R.layout.dialog_scancode_source, getWebView().findViewById(android.R.id.content), false);
        //初始化控件
        TextView choosePhoto = inflate.findViewById(R.id.choosePhoto);
        TextView takePhoto = inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(v -> {
            writeInfoLog("扫描相册中的图片");
            scanViaGallery();
            dialog.dismiss();
        });
        takePhoto.setOnClickListener(v -> {
            writeInfoLog("拍摄照片并扫描");
            scanViaCamera();
            dialog.dismiss();
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //设置Dialog距离底部的距离
            lp.y = 20;
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
        }

        dialog.show();//显示对话框
    }

    /**
     * 通过摄像头扫码
     */
    private void scanViaCamera() {
        _arcZxingLite.launch(createIntent(HACQRCodeScanActivity.class));
    }

    private void scanViaGallery() {
        _contentChooser.launch("image/*");
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
                    } else {
                        writeInfoLog("用户取消，没有拍摄图片/视频。");
                    }
                });

        _arcZxingLite = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            // 按照ZXingLite文档获取和解析扫码结果数据，如果出错或者取消，默认为空字符串，同官方APP
            Intent data = result.getData();
            String resultS = "";

            if (null != data) {
                writeInfoLog("ZXing拍摄完成，即将开始分析。");

                resultS = CameraScan.parseScanResult(data);

                writeInfoLog("ZXing分析完成，结果为：" + resultS);
            } else {

                writeInfoLog("ZXing扫描出错或用户主动取消");
            }

            callback(CallbackParams.success(resultS));
        });

        // 创建文件选择页面启动器
        _contentChooser = activity.registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    // GetContent是单选，如果用户取消时，会返回null
                    if (null != uri) {
                        writeInfoLog("图片选取完成，即将开始读取：" + uri);
                        DeviceUtility.registryLatestFile(uri);

                        try {
                            Bitmap image = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                            writeInfoLog("图片读取完成，即将调用ZXing分析：" + uri);

                            String result = CodeUtils.parseCode(image);
                            writeInfoLog("ZXing分析完成，结果为：" + result);

                            callback(CallbackParams.success(result));
                        } catch (Exception ex) {
                            callback(CallbackParams.error(ex.toString()));
                        }
                    } else {
                        writeInfoLog("用户取消，没有选择图片。");
                    }

                });
    }
}
