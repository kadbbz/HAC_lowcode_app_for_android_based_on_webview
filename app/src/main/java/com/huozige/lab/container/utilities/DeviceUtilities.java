package com.huozige.lab.container.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.HACApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 设备相关的帮助类
 */
public class DeviceUtilities {

    private static boolean _isOfflineMode;

    /**
     * 设置离线模式
     *
     * @param mode 是否为离线模式
     */
    public static void setOfflineMode(boolean mode) {
        _isOfflineMode = mode;
        XLog.v("切换离线模式为：" + mode);
    }

    /**
     * 获取当前是否为离线模式
     *
     * @return 是否为离线模式
     */
    public static boolean isOfflineMode() {
        return _isOfflineMode;
    }


    /**
     * 读取当前APP的版本号
     *
     * @return 版本号
     */
    public static String getPackageVersionName() {
        String versionName = "";

        try {
            PackageInfo pinfo = HACApplication.getInstance().getPackageManager().getPackageInfo(HACApplication.getInstance().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            XLog.e("获取应用版本信息出错。 \r\n%s", e);
        }

        return versionName;
    }

    /**
     * 读取当前APP的包名
     *
     * @return 包名
     */
    public static String getPackageName() {
        return HACApplication.getInstance().getPackageName();
    }

    /**
     * 读取设备标识SSAID
     *
     * @return SSAID
     */
    public static String getSSAID() {

        @SuppressLint("HardwareIds") String id = Settings.Secure.getString(HACApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return id;
    }

    /**
     * 获取WebView的主版本
     *
     * @return 主版本
     */
    public static int getWebViewMajorVersion() {
        PackageInfo pinfo = WebView.getCurrentWebViewPackage();

        if (pinfo == null) {
            return Integer.MAX_VALUE; // 如果无法获取版本号，按照可以使用来处理
        } else {
            String major = pinfo.versionName.split("\\.")[0];
            return Integer.parseInt(major);
        }

    }

    /**
     * 获取WebView的版本号
     *
     * @return 完整的版本号
     */
    public static String getWebViewVersionName() {
        PackageInfo pinfo = WebView.getCurrentWebViewPackage();

        if (pinfo == null) {
            return "";
        } else {
            return pinfo.versionName;
        }

    }

    /**
     * 读取文件内容到Byte[]
     *
     * @param uri 目标文件
     * @return 文件的内容（Byte[]格式）
     * @throws Exception 异常
     */
    public static byte[] readFileToByteArray(Uri uri) throws Exception {
        InputStream input = HACApplication.getInstance().getContentResolver().openInputStream(uri);

        if (input == null) {
            throw new FileNotFoundException(uri.toString());
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();

        return output.toByteArray();
    }

    /**
     * 将文件路径封装为对读取更友好的Uri格式
     *
     * @param filePath 文件路径
     * @return 文件路径（Uri格式，如 content://downloads/all_downloads/103）
     */
    public static Uri pathToUri(String filePath) {

        return FileProvider.getUriForFile(HACApplication.getInstance(), HACApplication.getInstance().getApplicationInfo().packageName, new File(filePath));
    }


    /**
     * 解析Uri中文件的地址
     *
     * @param uri     原地址（Uri格式，如 content://downloads/all_downloads/103）
     * @return 文件路径
     */
    public static String uriToPath(Uri uri) {
        String path = null;

        // 只查询媒体数据的数据列
        String[] projection = {MediaStore.MediaColumns.DATA};

        try (Cursor cursor = HACApplication.getInstance().getContentResolver().query(uri, projection, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                // 获取数据列的索引，一般只有一个数据列，即索引为0
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                // 获取数据列的数据，即文件的真实路径
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            XLog.e(e);
        }

        return path;
    }

    private static Uri __latestFile;

    /**
     * 获取上次操作的文件
     *
     * @return 文件
     */
    public static Uri getLatestFile() {
        return __latestFile;
    }

    /**
     * 注册为“上次操作的文件”
     *
     * @param uri 文件
     */
    public static void registryLatestFile(Uri uri) {
        __latestFile = uri;
    }

    /**
     * 震动
     *
     * @param durationInSeconds 持续时间（秒）
     */
    public static void vibrate(long durationInSeconds) {
        Vibrator op = (Vibrator) HACApplication.getInstance().getSystemService(Context.VIBRATOR_SERVICE);

        if (op != null) {
            op.vibrate(VibrationEffect.createOneShot(1000 * durationInSeconds, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    public static int RINGTONE_TYPE_NOTIFICATION = RingtoneManager.TYPE_NOTIFICATION;
    public static int RINGTONE_TYPE_RINGTONE = RingtoneManager.TYPE_RINGTONE;
    public static int RINGTONE_TYPE_ALARM = RingtoneManager.TYPE_ALARM;

    /**
     * 播放铃声
     *
     * @param ringtoneType 铃声的类型，支持RINGTONE_TYPE_NOTIFICATION、RINGTONE_TYPE_RINGTONE和RINGTONE_TYPE_ALARM
     */
    public static void playRingtone(int ringtoneType) {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(ringtoneType);
        var op = RingtoneManager.getRingtone(HACApplication.getInstance(), ringtoneUri);

        if (op != null) {
            op.play();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (op.isPlaying()) {
                        op.stop();
                        XLog.v("铃声播放时间超过5秒，已自动停止。");
                    }
                }
            }, 5 * 1000);
        } else {
            XLog.e("无法找到需要播放的铃声：" + ringtoneUri);
        }

    }

}
