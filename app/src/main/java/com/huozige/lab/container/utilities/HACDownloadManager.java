package com.huozige.lab.container.utilities;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.RECEIVER_NOT_EXPORTED;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载帮助类
 */
public class HACDownloadManager {

    public interface IHACDownloadHandler{
        void onSuccess(File targetFile);
        void onError(String fileName, String url);
    }

    public static class HACDownloadTask{
        public long taskId;
        public String url;
        public String fileName;
        public File targetFile;
        public int statusCode;
        private IHACDownloadHandler handler;

        public void registryHandler(IHACDownloadHandler handler) {
            this.handler = handler;
        }

    }

    static final String LOG_TAG = "HAC_DownloadManager"; // 日志的标识

    static HACDownloadManager __instance;

    static final HashMap<Long,HACDownloadTask> __taskList = new HashMap<>();

    public static HACDownloadManager getInstance(Context context) {

        if(__instance !=null){
            return __instance;
        }

        __instance = new HACDownloadManager();
        __instance._innerManager = (android.app.DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        __instance._receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

                    for (Map.Entry<Long,HACDownloadTask> entry: __taskList.entrySet()
                         ) {
                        Cursor cursor = __instance._innerManager.query(new DownloadManager.Query().setFilterById(entry.getKey()));

                        if (cursor != null) {
                            if (cursor.moveToFirst()) {

                                HACDownloadTask task = entry.getValue();

                                int statusColumn = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                                if (statusColumn >= 0) {
                                    int status = cursor.getInt(statusColumn);
                                    task.statusCode = status;

                                    switch (status) {
                                        case DownloadManager.STATUS_FAILED:
                                            Log.e(LOG_TAG, "文件下载失败："+ task.fileName+"，标识为"+task.taskId);
                                            //下载失败
                                            task.handler.onError(task.fileName, task.url);
                                            break;
                                        case DownloadManager.STATUS_SUCCESSFUL:
                                            Log.v(LOG_TAG, "文件下载成功：" + task.targetFile+"，标识为"+task.taskId);
                                            //下载成功
                                            task.handler.onSuccess(task.targetFile);
                                            break;
                                    }
                                }
                            }
                            cursor.close();
                        }
                    }
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(__instance._receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), RECEIVER_NOT_EXPORTED);
        } else {
            context.registerReceiver(__instance._receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }

        return __instance;
    }

    private DownloadManager _innerManager;

    private BroadcastReceiver _receiver;

    /**
     * 执行下载
     *
     * @param context     操作上下文
     * @param url         需要被下载的URL
     * @param callback    下载成功的回调
     */
    public void startDownloadTask(Context context, String url, IHACDownloadHandler callback) {

        Log.v(LOG_TAG, "即将开始下载：" + url);

        HACDownloadTask task = new HACDownloadTask();
        task.url = url;
        task.registryHandler(callback);

        // 0. 准备下载文件夹
        boolean isReady = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        // 1. 处理文件名
        task.fileName = MiscUtilities.guessFileName(url);
        task.targetFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), task.fileName);

        Log.v(LOG_TAG, "下载目录准备就绪：" + task.targetFile + "文件夹是否为本次新建：" + isReady);

        // 2. 配置下载选项
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Uri.parse(url))
                .setTitle(task.fileName)
                .setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setVisibleInDownloadsUi(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, task.fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        request.allowScanningByMediaScanner();

        // 3. 处理授权
        String cookieString = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie", cookieString);

        // 4. 启动下载
        android.app.DownloadManager downloadManager = (android.app.DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        task.taskId = downloadManager.enqueue(request);

        // 5. 加入列表
        __taskList.put(task.taskId, task);

        // 6. 记录日志
        Log.v(LOG_TAG, "文件下载启动，本次下载的标识为" + task.taskId);
    }
}
