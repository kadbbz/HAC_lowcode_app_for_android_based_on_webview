package com.huozige.lab.container;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.FileNameGenerator;
import com.elvishew.xlog.printer.file.writer.SimpleWriter;
import com.huozige.lab.container.utilities.BroadcastDispatcher;
import com.huozige.lab.container.utilities.ConfigManager;
import com.huozige.lab.container.utilities.DeviceUtility;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 应用的起点
 */
public class HACApplication extends Application {

    static HACApplication  __instance;

    /**
     * 获取应用级上下文的实例
     * @return 应用级上下文
     */
    public static HACApplication getInstance(){
        return __instance;
    }

    static final long MAX_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    final static String LOG_TAG = "HAC_Application";

    @Override
    public void onCreate() {
        super.onCreate();

        __instance = this;

        ConfigManager.init(this);

        initLogger();

        XLog.v(">>>>>>> 应用启动 <<<<<<<");

        BroadcastDispatcher.getInstance().init(this);

        XLog.v("全局广播派发器初始化完成");

        HACCrashHandler.getInstance().init(this);

        XLog.v("全局异常处理挂载完成");

        initReadlm();

        XLog.v("Realm初始化完成");

        JPushInterface.init(this);

        XLog.v("JPush初始化完成");

        RecordManager.getInstance().init(this, BuildConfig.DEBUG);
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.MP3);

        XLog.v("录音服务初始化完成");
    }

    private void initLogger() {
        // 初始化日志记录器
        LogConfiguration logConfig = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : ConfigManager.getInstance().getShouldLogAllEntry() ? LogLevel.ALL : LogLevel.ERROR)
                .tag(LOG_TAG)                                            // Specify TAG, default: "X-LOG"
                .enableThreadInfo()                                    // Enable thread info, disabled by default
                .enableStackTrace(2)                                   // Enable stack trace info with depth 2, disabled by default
                .enableBorder()                                        // Enable border, disabled by default
                .build();
        Printer androidPrinter = new AndroidPrinter(true);         // Printer that print the log using android.util.Log
        Printer filePrinter = new FilePrinter                      // Printer that print(save) the log to file
                .Builder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath())
                .fileNameGenerator(new HACFileNameGenerator())
                .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))
                .flattener(new ClassicFlattener())
                .writer(new SimpleWriter() {
                    @Override
                    public void onNewFileCreated(File file) {
                        super.onNewFileCreated(file);
                        final String header = "\n>>>>>>>>>>>>>>>> File Header >>>>>>>>>>>>>>>>" +
                                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                                "\nDevice Model       : " + Build.MODEL +
                                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                                "\nApp VersionName    : " + DeviceUtility.getPackageVersionName() +
                                "\nWebView Version    : " + DeviceUtility.getWebViewVersionName() +
                                "\n<<<<<<<<<<<<<<<< File Header <<<<<<<<<<<<<<<<\n\n";
                        appendLog(header);
                    }
                })
                .build();

        XLog.init(
                logConfig,
                androidPrinter,
                filePrinter);
    }

    private void initReadlm() {
        // 初始化Realm
        Realm.init(this);

        // 升级数据结构时，丢弃原有数据
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static class HACFileNameGenerator implements FileNameGenerator {

        @Override
        public boolean isFileNameChangeable() {
            return true;
        }

        /**
         * Generate a file name which represent a specific log level.
         */
        @Override
        public String generateFileName(int logLevel, long timestamp) {
            return "HAC_Log_" + LogLevel.getLevelName(logLevel) + "_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";
        }
    }
}
