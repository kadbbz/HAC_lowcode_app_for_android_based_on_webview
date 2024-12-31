package com.huozige.lab.container.proxy.support.capture;

import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.zlw.main.recorderlib.RecordManager;

public class AudioRecorderActivity extends BaseActivityNoActionBar {

    public final static String BUNDLE_EXTRA_RESULT_FILE_URI = "file";

    private boolean _isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);

        setTitle(getString(R.string.ui_title_recorder_activity));
        findViewById(R.id.button_cancel).setOnClickListener((e)-> stop());
    }


    @Override
    protected  void onResume(){
        super.onResume();
        start();
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }


    private void start(){

        RecordManager.getInstance().setRecordResultListener(result -> {

            var path = result.getPath();
            XLog.v("音频录制完成，文件保存在："+path);
            DeviceUtility.registryLatestFile(DeviceUtility.pathToUri(path));

            Intent res = new Intent();
            res.putExtra(BUNDLE_EXTRA_RESULT_FILE_URI, DeviceUtility.pathToUri(path));
            setResult(RESULT_OK, res);
            finish();
        });

        XLog.v("开始录音");
        _isRecording = true;
        RecordManager.getInstance().start();
    }

    private void stop(){

        if(_isRecording){
            RecordManager.getInstance().stop();
        }
    }
}