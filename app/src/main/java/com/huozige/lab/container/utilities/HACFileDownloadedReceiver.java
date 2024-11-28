package com.huozige.lab.container.utilities;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.elvishew.xlog.XLog;

import java.util.Map;

public class HACFileDownloadedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

            DownloadManager dm = (android.app.DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

            for (Map.Entry<Long, HACDownloadTask> entry : HACDownloadManager.getTasks().entrySet()
            ) {
                Cursor cursor = dm.query(new DownloadManager.Query().setFilterById(entry.getKey()));

                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        HACDownloadTask task = entry.getValue();

                        int statusColumn = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int reasonColumn = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);

                        if (statusColumn >= 0) {
                            int status = cursor.getInt(statusColumn);
                            int reason = cursor.getInt(reasonColumn);
                            task.statusCode = status;
                            HACDownloadManager.getTasks().remove(task.taskId);
                            switch (status) {
                                case DownloadManager.STATUS_FAILED:
                                    XLog.e("文件下载失败：" + task.fileName + "，标识为" + task.taskId + "，原因是" + reason);
                                    //下载失败
                                    task.handler.onError(task.fileName, task.url);

                                    break;
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    XLog.v("文件下载成功：" + task.fileName + "，标识为" + task.taskId);
                                    //下载成功

                                    Uri target = dm.getUriForDownloadedFile(task.taskId);
                                    DeviceUtility.registryLatestFile(target);
                                    task.handler.onSuccess(target);

                                    break;
                            }
                        }
                    }
                    cursor.close();
                }
            }
        }
    }
}