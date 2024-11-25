package com.huozige.lab.container.proxy.support.capture;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CameraViewActivity extends BaseActivityNoActionBar {

    public static final String EXTRA_OPERATION = "op";
    public static final String OPERATION_TAKE_PHOTO = "photo";

    public static final String OPERATION_TAKE_PHOTO_SNAPSHOT = "p_snapshot";

    public static final String OPERATION_TAKE_VIDEO = "video";

    public static final String OPERATION_TAKE_VIDEO_SNAPSHOT = "v_snapshot";

    public static final String EXTRA_OUT_URI = "data-uri";

    CameraView camera;

    ImageButton ibPhoto, ibVideo, ibToggleCamera;

    boolean isSnapshot;

    private void returnTo(File resultFile) {
        Intent cameraResult = new Intent();
        Bundle bundle = new Bundle();

        // 通知系统相册刷新，异步操作，无需等待
        MediaScannerConnection.scanFile(CameraViewActivity.this,
                new String[]{
                        resultFile.getAbsolutePath()
                }, null,
                (path, uri) -> {
                    // 图片保存成功后的回调
                    // 可以在这里进行额外的操作，比如显示图片等
                    XLog.v("图片已被相册收录：%s", uri);
                });

        Uri resultFileUri = DeviceUtility.pathToUri(resultFile.getPath());
        bundle.putString(EXTRA_OUT_URI, resultFileUri.toString());
        DeviceUtility.registryLatestFile(resultFileUri);

        cameraResult.putExtras(bundle);

        setResult(RESULT_OK, cameraResult);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);

        // 处理后续工作
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {

                XLog.v("拍摄完成，即将开始后续处理");

                // 文件存入相册
                File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg");
                result.toFile(imageFile, (d) -> {

                    XLog.v("照片已保存为：" + imageFile.getPath());

                    returnTo(imageFile);
                });

            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {

                XLog.v("拍摄完成，视频已保存为：" + result.getFile().getPath());

                returnTo(result.getFile());
            }
        });

        ibPhoto = findViewById(R.id.capturePicture);
        ibVideo = findViewById(R.id.captureVideo);
        ibToggleCamera = findViewById(R.id.toggleCamera);

        // 拍照
        ibPhoto.setOnClickListener((d) -> {
            if (!isSnapshot) {
                XLog.v("开始拍摄照片（全尺寸）");
                camera.takePicture();
            } else {
                XLog.v("开始拍摄照片（小尺寸）");
                camera.takePictureSnapshot();
            }
        });

        // 录像
        ibVideo.setOnClickListener((d) -> {
            if (camera.isTakingVideo()) {
                XLog.v("停止拍摄视频");
                camera.stopVideo();
            } else {
                File tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".mp4");
                if (!isSnapshot) {
                    XLog.v("开始拍摄视频（全尺寸）");
                    camera.takeVideo(tempFile);
                } else {
                    XLog.v("开始拍摄视频（小尺寸）");
                    camera.takeVideoSnapshot(tempFile);
                }
                Toast.makeText(this, "视频录制中，再次点击停止录像", Toast.LENGTH_LONG).show();
            }
        });

        ibToggleCamera.setOnClickListener((d) -> {
            var currentF = camera.getFacing();
            if (currentF == Facing.BACK) {
                camera.setFacing(Facing.FRONT);
                XLog.v("已切换至前摄像头");
            } else {
                camera.setFacing(Facing.BACK);
                XLog.v("已切换至主摄像头");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        PermissionsUtility.asyncRequirePermissions(this, new String[]{
                Permission.CAMERA,
                Permission.RECORD_AUDIO,
                Permission.WRITE_EXTERNAL_STORAGE
        }, () -> {
            Intent intentR = getIntent();

            if (intentR != null) {

                String op = intentR.getStringExtra(EXTRA_OPERATION);
                if (op == null) op = OPERATION_TAKE_PHOTO_SNAPSHOT;

                // 调整按钮状态
                boolean isTakingPhoto = op.equalsIgnoreCase(OPERATION_TAKE_PHOTO) || op.equalsIgnoreCase(OPERATION_TAKE_PHOTO_SNAPSHOT);
                ibPhoto.setVisibility(isTakingPhoto ? View.VISIBLE : View.INVISIBLE);
                ibToggleCamera.setVisibility(isTakingPhoto ? View.VISIBLE : View.INVISIBLE);
                ibVideo.setVisibility(!isTakingPhoto ? View.VISIBLE : View.INVISIBLE);

                // 设置拍摄类型
                if (op.equals(OPERATION_TAKE_PHOTO) || op.equals(OPERATION_TAKE_PHOTO_SNAPSHOT)) {
                    camera.setMode(Mode.PICTURE);
                } else {
                    camera.setMode(Mode.VIDEO);
                }

                // 判断是否为小尺寸
                isSnapshot = (op.equalsIgnoreCase(OPERATION_TAKE_VIDEO_SNAPSHOT) || op.equalsIgnoreCase(OPERATION_TAKE_PHOTO_SNAPSHOT));
            }
        });
    }
}