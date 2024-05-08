package com.huozige.lab.container.utilities;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

import com.arthenica.mobileffmpeg.FFmpeg;

public class FFmpegUtility {
    public static long wavToMp3(String input, String output, FFmpegCallback callback){
        long executionId = FFmpeg.executeAsync("-i \""+ input+"\" \""+output+"\" -y", (executionId1, returnCode) -> {
            if (returnCode == RETURN_CODE_SUCCESS) {
                callback.onSuccess();
            }  else {
               callback.onError(returnCode);
            }
        });

        return executionId;
    }

    public abstract static class FFmpegCallback{
        public abstract void onSuccess();
        public abstract void onError(int code);
    }
}
