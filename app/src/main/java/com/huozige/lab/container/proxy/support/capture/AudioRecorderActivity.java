package com.huozige.lab.container.proxy.support.capture;

import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.zlw.main.recorderlib.RecordManager;

/**
 * 录音的等待页面
 */
public class AudioRecorderActivity extends BaseActivityNoActionBar {

    /**
     * 返回值
     */
    public final static String BUNDLE_EXTRA_RESULT_FILE_URI = "file";

    private boolean _isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);

        setTitle(getString(R.string.ui_title_recorder_activity));
        // 点击停止按钮的处理逻辑
        findViewById(R.id.button_cancel).setOnClickListener((e)-> stop());
    }


    @Override
    protected  void onResume(){
        super.onResume();

        // 进入页面，就开始录音
        start();
    }

    @Override
    protected void onPause() {

        // 导航出该页面时，再检查一次是否退出录音
        stop();
        super.onPause();
    }


    private void start(){

        // 处理录音完成的回调。从调用停止录音到保存成文件需要一段时间
        RecordManager.getInstance().setRecordResultListener(result -> {

            // 记录日志
            var path = result.getPath();
            XLog.v("音频录制完成，文件保存在："+path);

            // 将生成的文件注册到HAC里面，为“打开文件”提供默认值
            DeviceUtility.registryLatestFile(DeviceUtility.pathToUri(path));

            // 返回
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