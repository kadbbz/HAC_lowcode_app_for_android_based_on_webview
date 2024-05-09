package com.huozige.lab.container.utilities;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

import com.arthenica.mobileffmpeg.FFmpeg;

/**
 * 音视频处理相关的帮助类
 */
public class FFmpegUtility {

    /**
     * 将wav格式转化为mp3格式
     * @param input wav文件
     * @param output mp3文件
     * @param callback 处理完成的回调
     */
    public static void wavToMp3(String input, String output, FFmpegCallback callback) {

        FFmpeg.executeAsync("-i \"" + input + "\" \"" + output + "\" -y", (executionId1, returnCode) -> {
            if (returnCode == RETURN_CODE_SUCCESS) {
                callback.onSuccess();
            } else {
                callback.onError(returnCode);
            }
        });
    }

    public abstract static class FFmpegCallback {
        public abstract void onSuccess();

        public abstract void onError(int code);
    }
}
