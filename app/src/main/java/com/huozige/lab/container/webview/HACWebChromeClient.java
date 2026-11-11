package com.huozige.lab.container.webview;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.webkit.GeolocationPermissions;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.elvishew.xlog.XLog;
import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.huozige.lab.container.utilities.PermissionsUtility;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理浏览器的事件，实现弹出消息和文件上传等功能
 */
public class HACWebChromeClient extends WebChromeClient {

    String _title; // 当前页面标题，用于弹出消息等场景
    AppCompatActivity _context; // 关联的上下文，即拥有浏览器内核的页面

    ValueCallback<Uri[]> _filePathCallback; // ChromeClient的文件选择器回调

    ActivityResultLauncher<String> _contentChooser; // 选择文件的调用器
    ActivityResultLauncher<String[]> _multiFileChooser; // 多选文件的调用器
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
        //活字格中使用弹出页面——全屏覆盖模式时，指挥触发该函数，不会触发onReceivedTitle，因此这里添加else做额外处理
        if (newProgress < 100) {
            // _title = _context.getString(R.string.ui_title_loading);
            _context.setTitle(_context.getString(R.string.ui_title_loading));
        } else {
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

        boolean isMultiSelect = fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE;
        String[] acceptTypes = fileChooserParams.getAcceptTypes();
        boolean acceptsImage = acceptsMimeType(acceptTypes, "image/");
        boolean acceptsVideo = acceptsMimeType(acceptTypes, "video/");

        if (acceptsImage && fileChooserParams.isCaptureEnabled()) {
            // capture 表示网页要求直接拍摄，保留 HAC 原有行为。
            launchCameraCapture(webView);
        } else if (acceptsImage) {
            // 普通图片请求提供拍照和系统文件选择器两个入口。
            showImageSourceChooser(webView, isMultiSelect, acceptsOnlyImageTypes(acceptTypes));
        } else if (acceptsVideo) {
            // 视频仍使用原有 Matisse 选择流程。
            PermissionsUtility.asyncRequirePermissions(webView.getContext(), new String[]{
                    Permission.CAMERA,
                    Permission.WRITE_EXTERNAL_STORAGE
            }, () -> Matisse.from(_context)
                    .choose(MimeType.ofVideo(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "com.huozige.lab.container"))
                    .maxSelectable(isMultiSelect ? 99 : 1)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_PICK_PHOTO_VIDEO));
        } else {
            launchSystemFileChooser(isMultiSelect, "*/*");
        }

        return true;
    }

    private boolean acceptsMimeType(String[] acceptTypes, String prefix) {
        if (acceptTypes == null) {
            return false;
        }
        for (String acceptType : acceptTypes) {
            if (acceptType == null) {
                continue;
            }
            for (String value : acceptType.split(",")) {
                String normalized = value.trim().toLowerCase(java.util.Locale.ROOT);
                if (normalized.startsWith(prefix) || ("image/".equals(prefix) && isImageExtension(normalized))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean acceptsOnlyImageTypes(String[] acceptTypes) {
        if (acceptTypes == null || acceptTypes.length == 0) {
            return false;
        }
        boolean foundType = false;
        for (String acceptType : acceptTypes) {
            if (acceptType == null) {
                return false;
            }
            for (String value : acceptType.split(",")) {
                String normalized = value.trim().toLowerCase(java.util.Locale.ROOT);
                if (normalized.isEmpty()) {
                    return false;
                }
                foundType = true;
                if (!normalized.startsWith("image/") && !isImageExtension(normalized)) {
                    return false;
                }
            }
        }
        return foundType;
    }

    private boolean isImageExtension(String acceptType) {
        if (!acceptType.startsWith(".")) {
            return false;
        }
        String extension = acceptType.substring(1);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mimeType != null && mimeType.toLowerCase(java.util.Locale.ROOT).startsWith("image/");
    }

    private void showImageSourceChooser(WebView webView, boolean isMultiSelect, boolean onlyImages) {
        BottomSheetDialog dialog = new BottomSheetDialog(_context);
        LinearLayout content = new LinearLayout(_context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setBackgroundColor(Color.WHITE);
        content.setPadding(dp(24), dp(12), dp(24), dp(20));

        View dragHandle = new View(_context);
        dragHandle.setBackground(createRoundedBackground(Color.rgb(210, 214, 220), dp(4)));
        LinearLayout.LayoutParams handleParams = new LinearLayout.LayoutParams(dp(36), dp(4));
        handleParams.gravity = Gravity.CENTER_HORIZONTAL;
        handleParams.bottomMargin = dp(20);
        content.addView(dragHandle, handleParams);

        TextView title = new TextView(_context);
        title.setText(R.string.ui_title_image_source);
        title.setTextColor(Color.rgb(31, 41, 55));
        title.setTextSize(20);
        title.setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD);
        content.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subtitle = new TextView(_context);
        subtitle.setText(R.string.ui_subtitle_image_source);
        subtitle.setTextColor(Color.rgb(107, 114, 128));
        subtitle.setTextSize(14);
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subtitleParams.topMargin = dp(4);
        subtitleParams.bottomMargin = dp(20);
        content.addView(subtitle, subtitleParams);

        View cameraOption = createSourceOption(
                R.drawable.ic_photo,
                R.string.ui_label_camera_photo,
                R.string.ui_desc_camera_capture,
                Color.rgb(0, 161, 233), Color.WHITE,
                () -> {
                    dialog.dismiss();
                    launchCameraCapture(webView);
                });
        content.addView(cameraOption, optionLayoutParams());

        View fileOption = createSourceOption(
                R.drawable.ic_folder_image,
                R.string.ui_button_choose_file,
                R.string.ui_desc_choose_image,
                Color.rgb(232, 246, 253), Color.rgb(0, 133, 190),
                () -> {
                    dialog.dismiss();
                    launchSystemFileChooser(isMultiSelect, onlyImages ? "image/*" : "*/*");
                });
        content.addView(fileOption, optionLayoutParams());

        TextView cancel = new TextView(_context);
        cancel.setText(R.string.ui_button_cancel);
        cancel.setTextColor(Color.rgb(75, 85, 99));
        cancel.setTextSize(16);
        cancel.setGravity(Gravity.CENTER);
        cancel.setBackground(createRoundedBackground(Color.rgb(245, 247, 249), dp(16)));
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
            deliverFileChooserResult(new Uri[0]);
        });
        LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(52));
        cancelParams.topMargin = dp(4);
        content.addView(cancel, cancelParams);

        dialog.setContentView(content);
        dialog.setOnCancelListener(ignored -> deliverFileChooserResult(new Uri[0]));
        dialog.show();
    }

    private View createSourceOption(int iconResource, int titleResource, int descriptionResource,
                                    int iconBackgroundColor, int iconColor, Runnable onClick) {
        LinearLayout row = new LinearLayout(_context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(14), dp(12), dp(16), dp(12));
        row.setBackground(createRoundedBackground(Color.rgb(246, 248, 250), dp(18)));
        row.setClickable(true);
        row.setFocusable(true);

        FrameLayout iconContainer = new FrameLayout(_context);
        iconContainer.setBackground(createRoundedBackground(iconBackgroundColor, dp(24)));
        ImageView icon = new ImageView(_context);
        icon.setImageResource(iconResource);
        icon.setColorFilter(iconColor);
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(dp(24), dp(24), Gravity.CENTER);
        iconContainer.addView(icon, iconParams);
        row.addView(iconContainer, new LinearLayout.LayoutParams(dp(48), dp(48)));

        LinearLayout labels = new LinearLayout(_context);
        labels.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams labelsParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        labelsParams.leftMargin = dp(14);
        row.addView(labels, labelsParams);

        TextView title = new TextView(_context);
        title.setText(titleResource);
        title.setTextColor(Color.rgb(31, 41, 55));
        title.setTextSize(16);
        title.setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD);
        labels.addView(title);

        TextView description = new TextView(_context);
        description.setText(descriptionResource);
        description.setTextColor(Color.rgb(107, 114, 128));
        description.setTextSize(13);
        LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descriptionParams.topMargin = dp(3);
        labels.addView(description, descriptionParams);
        row.setOnClickListener(view -> onClick.run());
        return row;
    }

    private LinearLayout.LayoutParams optionLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(12);
        return params;
    }

    private GradientDrawable createRoundedBackground(int color, int radius) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(color);
        background.setCornerRadius(radius);
        return background;
    }

    private int dp(int value) {
        return (int) (value * _context.getResources().getDisplayMetrics().density + 0.5f);
    }

    private void launchCameraCapture(WebView webView) {
        PermissionsUtility.asyncRequirePermissions(webView.getContext(), new String[]{
                Permission.CAMERA,
                Permission.WRITE_EXTERNAL_STORAGE
        }, () -> {
            Intent cameraIntent = new Intent(_context, CameraViewActivity.class);
            cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, CameraViewActivity.OPERATION_TAKE_PHOTO);
            _imageCaptureChooser.launch(cameraIntent);
        });
    }

    private void launchSystemFileChooser(boolean isMultiSelect, String mimeType) {
        // 沿用 HAC 原有系统文件选择器入口。
        if (isMultiSelect) {
            _multiFileChooser.launch(new String[]{mimeType});
        } else {
            _contentChooser.launch(mimeType);
        }
    }

    private void deliverFileChooserResult(Uri[] uris) {
        ValueCallback<Uri[]> callback = _filePathCallback;
        _filePathCallback = null;
        if (callback != null) {
            callback.onReceiveValue(uris);
        }
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
                        if (uriStr != null && !uriStr.isEmpty()) {
                            Uri uri = Uri.parse(uriStr);
                            selectedFiles.add(uri);
                            DeviceUtility.registryLatestFile(uri);
                        }
                    }

                    // 让页面接手处理，每一个ChooseFile都需要有配套的onReceiveValue事件
                    deliverFileChooserResult(selectedFiles.toArray(new Uri[0]));
                });

        // 创建文件选择页面启动器（单选）
        _contentChooser = _context.registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {

                    List<Uri> selectedFiles = new ArrayList<>();

                    // GetContent是单选，如果用户取消时，会返回null
                    if (null != uri) {
                        selectedFiles.add(uri);
                        DeviceUtility.registryLatestFile(uri);
                    }

                    // 让页面接手处理，每一个ChooseFile都需要有配套的onReceiveValue事件
                    deliverFileChooserResult(selectedFiles.toArray(new Uri[0]));
                });

        // 创建多选文件选择页面启动器
        _multiFileChooser = _context.registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(),
                uris -> {
                    List<Uri> selectedFiles = new ArrayList<>();

                    if (uris != null) {
                        for (Uri uri : uris) {
                            selectedFiles.add(uri);
                            DeviceUtility.registryLatestFile(uri);
                        }
                    }

                    deliverFileChooserResult(selectedFiles.toArray(new Uri[0]));
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
            deliverFileChooserResult(selectedUris.toArray(new Uri[0]));
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
