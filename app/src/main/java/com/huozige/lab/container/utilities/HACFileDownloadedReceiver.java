package com.huozige.lab.container.utilities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.util.Map;

public class HACFileDownloadedReceiver extends BroadcastReceiver {

    static final String LOG_TAG = "HAC_HACFileDownloadedReceiver"; // 日志的标识

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

            for (Map.Entry<Long, HACDownloadTask> entry: HACDownloadManager.getTasks().entrySet()
            ) {
                Cursor cursor = HACDownloadManager.getDownloadManager().query(new DownloadManager.Query().setFilterById(entry.getKey()));

                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        HACDownloadTask task = entry.getValue();

                        int statusColumn = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int reasonColumn = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);

                        if (statusColumn >= 0) {
                            int status = cursor.getInt(statusColumn);
                            int reason =  cursor.getInt(reasonColumn);
                            task.statusCode = status;
                            HACDownloadManager.getTasks().remove(task.taskId);
                            switch (status) {
                                case DownloadManager.STATUS_FAILED:
                                    Log.e(LOG_TAG, "文件下载失败："+ task.fileName+"，标识为"+task.taskId+"，原因是"+reason);
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
}