package com.huozige.lab.container.webview;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.utilities.DeviceUtilities;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理浏览器的事件，实现弹出消息和文件上传等功能
 */
public class HACWebChromeClient extends WebChromeClient {

    String _title; // 当前页面标题，用于弹出消息等场景
    AppCompatActivity _context; // 关联的上下文，即拥有浏览器内核的页面

    ValueCallback<Uri[]> _filePathCallback; // ChromeClient的文件选择器回调

    ActivityResultLauncher<String> _contentChooser; // 选择文件的调用器
    ActivityResultLauncher<Intent> _imageCaptureChooser; // 拍摄照片的调用器

    static final int REQUEST_CODE_PICK_PHOTO_VIDEO = 20001; // 选取照片和视频的标识


    /**
     * 简单的构造函数
     *
     * @param activity 上下文
     */
    public HACWebChromeClient(AppCompatActivity activity) {
        _context = activity;
    }

    /**
     * 处理浏览器加载进度
     *
     * @param view        浏览器内核
     * @param newProgress 进度（0-100）
     */
    @SuppressLint("StringFormatInvalid")
    @Override
    public void onProgressChanged(WebView view, int newProgress) {

        // 在标题上显示页面的加载状态，0-99意味着正在加载，100加载完成。
        // 加载完成的处理，放在其他事件中，这里只关注加载中
        if (newProgress < 100) {
            _title = _context.getString(R.string.ui_title_loading);
            _context.setTitle(_title);
        }

        ((HACWebView) view).setProgress(newProgress);
    }

    /**
     * 处理浏览器的标题被页面代码更新的事件
     *
     * @param view  浏览器内核
     * @param title 新的标题
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {

        // 在页面加载的过程中，标题会先被设置为URL，然后才是真正的页面标题，所以需要在这里做处理
        if (title.toLowerCase().startsWith("http://") || title.toLowerCase().startsWith("https://")) {
            _context.setTitle(_context.getString(R.string.ui_title_loading));
        } else {
            _title = title; // 更新标题缓存
            _context.setTitle(_title);
        }
    }

    /**
     * 处理JS弹窗，替换为APP的原生弹窗
     */
    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_context).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_context.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setCancelable(false)
                .show();
        return true;
    }

    /**
     * 处理JS弹窗，替换为APP的原生弹窗
     */
    @Override
    public boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_context).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_context.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_context.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    /**
     * 处理JS弹窗，替换为APP的原生弹窗
     */
    @Override
    public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_context).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_context.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_context.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    /**
     * 处理JS弹窗，替换为APP的原生弹窗
     */
    @Override
    public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        final EditText input = new EditText(_context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new AlertDialog.Builder(_context).setTitle(_title)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(_context.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm(input.getText().toString()))
                .setCancelable(false)
                .show();
        return true;
    }

    /**
     * 处理文件选择框
     * WebView默认不支持文件上传，需要通过这个方法来实现
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

        _filePathCallback = filePathCallback; // 将参数缓存起来，回调使用

        if (Arrays.asList(fileChooserParams.getAcceptTypes()).contains("video/*") || Arrays.asList(fileChooserParams.getAcceptTypes()).contains("image/*")) {

            // 需要调用摄像头，则先申请权限
            PermissionsUtility.asyncRequirePermissions(webView.getContext(), new String[]{
                    Permission.CAMERA,
                    Permission.WRITE_EXTERNAL_STORAGE
            }, () -> {

                if (Arrays.asList(fileChooserParams.getAcceptTypes()).contains("image/*")) {

                    // 兼容活字格官方Vant插件，支持“直接调出摄像头拍照上传”功能
                    if (fileChooserParams.isCaptureEnabled()) {

                        Intent cameraIntent = new Intent(this._context, CameraViewActivity.class);
                        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, CameraViewActivity.OPERATION_TAKE_PHOTO);
                        _imageCaptureChooser.launch(cameraIntent);
                    } else {
                        // 照片采用知乎的Matisse库，支持拍摄
                        Matisse.from(_context)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                        new CaptureStrategy(true, "com.huozige.lab.container"))
                                .maxSelectable(1)
                                .thumbnailScale(0.85f)
                                .imageEngine(new GlideEngine())
                                .forResult(REQUEST_CODE_PICK_PHOTO_VIDEO);
                    }
                } else {

                    // 视频也采用知乎的Matisse库，支持拍摄
                    Matisse.from(_context)
                            .choose(MimeType.ofVideo(), false)
                            .countable(true)
                            .capture(true)
                            .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                    new CaptureStrategy(true, "com.huozige.lab.container"))
                            .maxSelectable(1)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_PICK_PHOTO_VIDEO);
                }
            });
        } else {

            // 直接使用系统文件选择机制
            _contentChooser.launch("*/*");
        }

        // 标记为已经处理
        return true;
    }

    /**
     * 在上下文的OnCreate中，创建启动器。
     * 其他时机会出错。
     */
    public void registryLaunchersOnCreated() {

        // 拍摄照片页面的启动器
        _imageCaptureChooser = _context.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    List<Uri> selectedFiles = new ArrayList<>();

                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // 从Intent中读取图片URI
                        String uriStr = result.getData().getStringExtra(CameraViewActivity.EXTRA_OUT_URI);
                        var uri = Uri.parse(uriStr);
                        selectedFiles.add(uri);
                        DeviceUtilities.registryLatestFile(uri);
                    }

                    // 让页面接手处理，每一个ChooseFile都需要有配套的onReceiveValue事件
                    _filePathCallback.onReceiveValue(selectedFiles.toArray(new Uri[0]));
                });

        // 创建文件选择页面启动器
        _contentChooser = _context.registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {

                    List<Uri> selectedFiles = new ArrayList<>();

                    // GetContent是单选，如果用户取消时，会返回null
                    if (null != uri) {
                        selectedFiles.add(uri);
                        DeviceUtilities.registryLatestFile(uri);
                    }

                    // 让页面接手处理，每一个ChooseFile都需要有配套的onReceiveValue事件
                    _filePathCallback.onReceiveValue(selectedFiles.toArray(new Uri[0]));
                });
    }

    /**
     * 在上下文中，处理转发来的返回结果
     */
    public Boolean processActivityResult(int requestCode, int resultCode, Intent data) {

        // 先处理Matisse的照片和视频请求
        if (requestCode == REQUEST_CODE_PICK_PHOTO_VIDEO) {

            // 根据文档的要求，获取用户选择的照片和视频
            // 这里有可能是单个或多个
            List<Uri> selectedUris = new ArrayList<>();

            if (resultCode == RESULT_OK) {
                // 根据文档的要求，获取用户选择的照片和视频
                selectedUris = Matisse.obtainResult(data);
            }

            // 让页面接手处理，每一个ChooseFile都需要有配套的onReceiveValue事件
            _filePathCallback.onReceiveValue(selectedUris.toArray(new Uri[0]));
        }

        return false;
    }

    /**
     * 允许读取地理位置
     */
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

        PermissionsUtility.asyncRequirePermissions(_context, new String[]{
                Permission.ACCESS_FINE_LOCATION
        }, () -> {
            XLog.v("GeolocationPermissionsShowPrompt invoked");

            // 第一个参数是whether or not the origin should be allowed to use the Geolocation API
            // 第二个参数是 whether the permission should be retained beyond the lifetime of a page currently being displayed by a WebView
            callback.invoke(origin, true, false);
        });
    }

    /**
     * 允许H5使用设备资源
     *
     * @param request 权限请求
     */
    @Override
    public void onPermissionRequest(PermissionRequest request) {

        // 不阻拦权限申请
        request.grant(request.getResources());
        request.getOrigin();
    }

}
