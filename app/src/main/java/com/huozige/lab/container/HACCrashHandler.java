package com.huozige.lab.container;

import android.app.Application;
import android.content.Intent;

import com.elvishew.xlog.XLog;

import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * 全局挂载的未捕获异常处理机制
 */
public class HACCrashHandler implements Thread.UncaughtExceptionHandler {


    Application _context;

    public HACCrashHandler(Application context) {
        _context = context;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {

        XLog.e("出现未捕获的异常：" + throwable.getMessage(), throwable);

        String message = "应用出现严重错误，请拍摄本界面或截屏后，与技术支持人员联系。";
        message += "\r\n\n";
        message += throwable.getMessage();
        message += "\r\n";
        message += throwable.toString();

        Toast.makeText(this._context, message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(_context, ShowErrorActivity.class);
        intent.putExtra(ShowErrorActivity.EXTRA_KEY_MESSAGE, message);

        _context.startActivity(intent);
    }
}
