package com.huozige.lab.container;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import com.hjq.permissions.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HzgWebChromeClient extends WebChromeClient {

    String _title;
    MainActivity _activity;
    ValueCallback<Uri[]> _filePathCallback;
    ActivityResultLauncher<String> _contentChooser;

    static final  int REQUEST_CODE_PICK_PHOTO_VIDEO = 20001;
    static final  int REQUEST_CODE_OTHER_FILES = 20002;

    HzgWebChromeClient(MainActivity activity){
        _activity = activity;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress < 100){
            _title = _activity.getString(R.string.ui_title_progressing,newProgress);
            _activity.setTitle(_title);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(title.toLowerCase().startsWith("http://") || title.toLowerCase().startsWith("https://") ){
            _activity.setTitle(_activity.getString(R.string.ui_title_loading));
        }else{
            _title = title;
            _activity.setTitle(title);
        }
    }

    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_activity.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_activity.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        final EditText input = new EditText(_activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm(input.getText().toString()))
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

        _filePathCallback = filePathCallback;

        if(Arrays.asList(fileChooserParams.getAcceptTypes()).contains("image/*")){

            // 照片
            Matisse.from(_activity)
                    .choose(MimeType.ofImage(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                    new CaptureStrategy(true, "com.huozige.lab.container"))
                    .maxSelectable(1)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_PICK_PHOTO_VIDEO);
        }else if(Arrays.asList(fileChooserParams.getAcceptTypes()).contains("video/*")){

            // 照片
            Matisse.from(_activity)
                    .choose(MimeType.ofVideo(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            new CaptureStrategy(true, "com.huozige.lab.container"))
                    .maxSelectable(1)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_PICK_PHOTO_VIDEO);
        }else{

            _contentChooser.launch("*/*");

        }


        return true;
    }

    public void RegistryLaunchersOnCreated(){
        // GetContent creates an ActivityResultLauncher<String> to allow you to pass
        // in the mime type you'd like to allow the user to select
        _contentChooser = _activity.registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        List<Uri> mSelected = new ArrayList<>();
                        mSelected.add(uri);
                        // Handle the returned Uri
                        _filePathCallback.onReceiveValue(mSelected.toArray(new Uri[0]));
                    }
                });
    }

    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == REQUEST_CODE_PICK_PHOTO_VIDEO && resultCode == _activity.RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            _filePathCallback.onReceiveValue(mSelected.toArray(new Uri[0]));
        }

        if(requestCode == REQUEST_CODE_OTHER_FILES && resultCode == _activity.RESULT_OK){
            Uri[] results = null;
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }

            _filePathCallback.onReceiveValue(results);
        }

        return false;
    }

    class GifSizeFilter extends Filter {

        private int mMinWidth;
        private int mMinHeight;
        private int mMaxSize;

        GifSizeFilter(int minWidth, int minHeight, int maxSizeInBytes) {
            mMinWidth = minWidth;
            mMinHeight = minHeight;
            mMaxSize = maxSizeInBytes;
        }

        @Override
        public Set<MimeType> constraintTypes() {
            return new HashSet<MimeType>() {{
                add(MimeType.GIF);
            }};
        }

        @Override
        public IncapableCause filter(Context context, Item item) {
            if (!needFiltering(context, item))
                return null;

            Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
            if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
                return new IncapableCause(IncapableCause.DIALOG, context.getString(R.string.feature_pick_photo_error_gif, mMinWidth,
                        String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
            }
            return null;
        }

    }


    static class UriHelper
    {
        public static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
            String filePath = null;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // 如果是document类型的 uri, 则通过document id来进行处理
                String documentId = DocumentsContract.getDocumentId(uri);
                if (isMediaDocument(uri)) { // MediaProvider
                    // 使用':'分割
                    String id = documentId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = {id};
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                    filePath = getDataColumn(context, contentUri, null, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())){
                // 如果是 content 类型的 Uri
                filePath = getDataColumn(context, uri, null, null);
            } else if ("file".equals(uri.getScheme())) {
                // 如果是 file 类型的 Uri,直接获取图片对应的路径
                filePath = uri.getPath();
            }
            return filePath;
        }

        private static String getDataColumn(Context context, Uri uri,
                                            String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column ="_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }


        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is MediaProvider
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is DownloadsProvider
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

    }
}
