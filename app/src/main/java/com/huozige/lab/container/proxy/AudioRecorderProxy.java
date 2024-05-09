package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.hjq.permissions.Permission;
import com.huozige.lab.container.R;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.DeviceUtilities;
import com.huozige.lab.container.utilities.FFmpegUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

/**
 * 操作音频录音
 * 1.18.0
 * audio.record(cellTag)：录音并返回mp3文件
 * audio.recordAsync(ticket)：异步录音
 */
public class AudioRecorderProxy extends AbstractProxy {

    String _currentWavFilePath;

    static final int __requestCode = 1001;

    @Override
    public String getName() {
        return "audio";
    }

    /**
     * 录音并将录制的mp3文件的Uri地址填充到单元格
     *
     * @param cellTag 接收文件地址的单元格
     */
    @JavascriptInterface
    public void record(String cellTag) {

        // 记录传入的Cell信息
        registryPayloadCellLocation(cellTag);

        // 记录日志
        writeInfoLog("开始录音");

        startRecording();

        registryForFeatureUsageAnalyze("use_audio_feature", "record");
    }

    /**
     * 异步开始录音，结果保存为mp3文件
     *
     * @param ticket 包含文件地址的回调
     */
    @JavascriptInterface
    public void recordAsync(String ticket) {

        // 记录传入的Cell信息
        registryCallbackTicket(ticket);

        // 记录日志
        writeInfoLog("开始录音");

        startRecording();

        registryForFeatureUsageAnalyze("use_audio_feature", "recordAsync");

    }

    private void startRecording() {

        // 需要权限
        asyncRequirePermissions(new String[]{Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE}, () -> {

            _currentWavFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".wav";
            int color = getWebView().getContext().getColor(R.color.colorAccent);

            AndroidAudioRecorder.with((Activity) getWebView().getContext())
                    // Required
                    .setFilePath(_currentWavFilePath)
                    .setColor(color)
                    .setRequestCode(__requestCode)

                    // Optional
                    .setSource(AudioSource.MIC)
                    .setChannel(AudioChannel.MONO)
                    .setAutoStart(true)
                    .setKeepDisplayOn(true)

                    // Start recording
                    .record();

            writeInfoLog("已打开录音界面，等待录制完成。");
        });
    }

    @Override
    public Boolean processActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == __requestCode) {
            if (resultCode == RESULT_OK) {

                writeInfoLog("音频录制完成，原始文件保存到：" + _currentWavFilePath + " ，即将开始压缩");

                var convertedFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "HAC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_compressed.mp3";
                FFmpegUtility.wavToMp3(_currentWavFilePath, convertedFile, new FFmpegUtility.FFmpegCallback() {
                    @Override
                    public void onSuccess() {
                        writeInfoLog("音频压缩完成，文件保存到：" + convertedFile);
                        var uri = DeviceUtilities.pathToUri(convertedFile);
                        callback(CallbackParams.success(uri.toString()));
                    }

                    @Override
                    public void onError(int code) {
                        writeErrorLog("音频压缩过程出错，错误码：" + code);
                        callback(CallbackParams.error("ffmpeg error: " + code));
                    }
                });

                return true;
            } else if (resultCode == RESULT_CANCELED) {

                writeErrorLog("录音界面关闭，用户取消或者发生了预期外的错误，错误码：" + requestCode);
                callback(CallbackParams.error(CallbackParams.CANCEL_BY_USER));
                return true;
            }
        }

        return false;
    }
}
