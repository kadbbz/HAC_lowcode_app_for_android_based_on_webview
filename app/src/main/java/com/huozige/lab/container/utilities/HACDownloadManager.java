package com.huozige.lab.container.utilities;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.RECEIVER_EXPORTED;

import android.app.DownloadManager;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.CookieManager;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.HACApplication;

import java.io.File;
import java.util.HashMap;

/**
 * 文件下载帮助类
 */
public class HACDownloadManager {

    public static HashMap<Long, HACDownloadTask> getTasks() {
        return __taskList;
    }


    static HACDownloadManager __instance;

    static final HashMap<Long, HACDownloadTask> __taskList = new HashMap<>();

    public static HACDownloadManager getInstance() {

        if (__instance != null) {
            return __instance;
        }

        __instance = new HACDownloadManager();
        __instance._receiver = new HACFileDownloadedReceiver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            HACApplication.getInstance().registerReceiver(__instance._receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), RECEIVER_EXPORTED);
        } else {
            HACApplication.getInstance().registerReceiver(__instance._receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }

        return __instance;
    }

    private HACFileDownloadedReceiver _receiver;

    /**
     * 执行下载
     *
     * @param url      需要被下载的URL
     * @param callback 下载成功的回调
     */
    public void startDownloadTask(String url, String mimeType, HACDownloadTask.IHACDownloadHandler callback) {

        XLog.v("即将开始下载：" + url);

        HACDownloadTask task = new HACDownloadTask();
        task.url = url;
        task.registryHandler(callback);

        // 1. 处理文件名
        FileNameInfo fileInfo = StringConvertUtility.guessFileName(url, mimeType);
        task.fileName = fileInfo.fileName;
        mimeType = fileInfo.mimeType;
        File tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), task.fileName);
        tempFile.deleteOnExit();

        // 2. 配置下载选项
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Uri.parse(url))
                .setTitle(task.fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setVisibleInDownloadsUi(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, task.fileName)
                .setAllowedOverMetered(true)
                .addRequestHeader("Accept-Content", mimeType)
                .setMimeType(mimeType)
                .setAllowedOverRoaming(true);

        request.allowScanningByMediaScanner();

        // 3. 处理授权
        String cookieString = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("Cookie", cookieString);

        // 4. 启动下载
        android.app.DownloadManager downloadManager = (android.app.DownloadManager) HACApplication.getInstance().getSystemService(DOWNLOAD_SERVICE);
        task.taskId = downloadManager.enqueue(request);

        // 5. 加入列表
        __taskList.put(task.taskId, task);

        // 6. 记录日志
        XLog.v("文件下载启动，本次下载的标识为" + task.taskId);
    }
}
