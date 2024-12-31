package com.huozige.lab.container.proxy;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.Permission;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.capture.AudioRecorderActivity;

/**
 * 操作音频录音
 * 1.18.0
 * audio.record(cellTag)：录音并返回mp3文件
 * audio.recordAsync(ticket)：异步录音
 */
public class AudioRecorderProxy extends AbstractProxy {

    ActivityResultLauncher<Intent> _arcRecorder;


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
        asyncRequirePermissions(new String[]{Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE}, () -> _arcRecorder.launch(createIntent(AudioRecorderActivity.class)));
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        super.onActivityCreated(activity);
        _arcRecorder = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                var file = result.getData().getStringExtra(AudioRecorderActivity.BUNDLE_EXTRA_RESULT_FILE_URI);
                writeInfoLog("录音操作完成，保存到：" + file);
                // 从Intent中读取图片和视频的URI
                callback(CallbackParams.success(file));
            } else {
                writeInfoLog("用户取消，没有完成录音。");
            }

        });
    }
}
