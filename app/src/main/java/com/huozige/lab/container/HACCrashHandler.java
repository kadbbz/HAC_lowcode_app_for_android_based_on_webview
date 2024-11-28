package com.huozige.lab.container;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.utilities.EventUtility;

/**
 * 全局挂载的未捕获异常处理机制
 */
public class HACCrashHandler implements Thread.UncaughtExceptionHandler {

    @SuppressLint("StaticFieldLeak")
    private static final HACCrashHandler __INSTANCE = new HACCrashHandler();

    HACCrashHandler(){

    }

    /**
     * 获得处理器的实例
     * @return 处理器
     */
    static HACCrashHandler getInstance(){
        return __INSTANCE;
    }

    /**
     * 初始化
     * @param context 上下文，默认为Application
     */
    public void init(Context context){
        _context = context;
        _defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    Thread.UncaughtExceptionHandler _defaultHandler;
    Context _context;

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {

        // 记录使用日志
        EventUtility.logEvent("app_uncaught_exception", throwable.toString());

        // 记录文件日志
        XLog.e("出现未捕获的异常：" + throwable.getMessage(), throwable);

        // 弹出吐司提示
        Toast.makeText(_context,"出现未捕获的异常：" + throwable.getMessage(),Toast.LENGTH_LONG).show();

        // 准备文字内容
        String message = "应用出现严重错误，请拍摄本界面或截屏后，与技术支持人员联系。";
        message += "\r\n\n";
        message += throwable.getMessage();
        message += "\r\n";
        message += throwable.toString();

        // 弹出新页面
        Intent intent = new Intent(_context, ShowErrorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ShowErrorActivity.EXTRA_KEY_MESSAGE, message);

        _context.startActivity(intent);
    }
}
