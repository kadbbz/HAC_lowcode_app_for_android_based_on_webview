package com.huozige.lab.container.proxy.support.pdf;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.huozige.lab.container.R;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 下载并预览PDF的页面
 */
public class PDFPreviewActivity extends AppCompatActivity {

    private final String LOG_TAG = "HAC_PDFPreviewActivity";
    private final String FILE_NAME_PREFIX = "hac_pdf_temp_";
    private static final int MENU_ID_CLEAR = 3;
    private static final int MENU_ID_CLOSE = 2;

    public static String EXTRA_KEY_URL = "url";
    public static String EXTRA_KEY_PASSWORD = "password";

    PDFView _pdfView;
    ProgressBar _pbDownload;
    DownloadTask _task;

    /**
     * 初始化下载任务
     * 使用okdownload，解决断点续传等问题
     *
     * @param url PDF的URL地址
     */
    private void initialDownloadTask(String url, String password) {

        // 创建下载任务，做降低进度刷新频率、重命名、强制刷新、5线程
        // 为了避免文件名导致的错误，确保每次加载最新版的文件，需要重新设置文件名
        String PDF_FILE_NAME_PREFIX = FILE_NAME_PREFIX + "%s.pdf";
        _task = new DownloadTask.Builder(url, getTargetDir())
                .setMinIntervalMillisCallbackProcess(100)
                .setFilename(String.format(PDF_FILE_NAME_PREFIX, new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date())))
                .setPassIfAlreadyCompleted(false)
                .setConnectionCount(5)
                .build();

        // 加入下载队列，开始下载
        // 不能在UI线程中直接调用execute
        _task.enqueue(new DownloadListener2() {

            // 参考：https://github.com/lingochamp/okdownload/blob/master/sample/src/main/java/com/liulishuo/okdownload/sample/ContentUriActivity.java

            // 并发控制，记录已经下载的Byte数
            private final AtomicLong progress = new AtomicLong();

            // 下载组件提供的速度计算器
            private SpeedCalculator speedCalculator;

            @Override
            public void taskStart(@NonNull DownloadTask task) {

                // 开始下载，需要重置Title和速度计算器
                setTitle(R.string.title_pdf_downloading);
                speedCalculator = new SpeedCalculator();
            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {

                // 重置进度，并更新
                progress.set(0);
                calcProgressToViewAndMark(0, info.getTotalLength());
            }

            @Override
            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

                // 重置进度，并更新
                progress.set(info.getTotalOffset());
                calcProgressToViewAndMark(info.getTotalOffset(),
                        info.getTotalLength());
            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {

                // 更新速度计算器的数据
                speedCalculator.downloading(increaseBytes);

                // 更新下载速度
                final String status = speedCalculator.speed();
                setTitle(status);

                // 更新下载进度
                final long offset = progress.addAndGet(increaseBytes);

                // 展示到界面
                updateProgressToViewWithMark(offset);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {

                // 需要判断是否出错
                if (cause.equals(EndCause.COMPLETED)) {

                    if (task.getFile() != null && task.getFile().exists()) {
                        // 更新标题
                        setTitle(R.string.title_pdf_preview);

                        Log.v(LOG_TAG, "PDF file was found at: " + task.getFile());

                        try {
                            // 初始化PDF预览组件，设置文件、默认打开第一页、渲染标注和表单
                            PDFView.Configurator config = _pdfView.fromFile(task.getFile())
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true);

                            // 如果有传入密码，则设置密码
                            if (password != null && !password.isEmpty()) {
                                config.password(password);
                            }

                            // 开始展示PDF
                            config.load();

                            Log.v(LOG_TAG, "PDF file was rendered.");
                        } catch (Exception ex) {
                            Toast.makeText(PDFPreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, "Error on rendering PDF file: " + ex);
                        }

                    } else {
                        Toast.makeText(PDFPreviewActivity.this, "下载的文件被删除或移动：" + task.getFile(), Toast.LENGTH_LONG).show();
                        Log.e(LOG_TAG, "PDF file was not existed at: " + task.getFile());
                    }

                } else {
                    Toast.makeText(PDFPreviewActivity.this, "下载过程中发生错误：" + cause.name(), Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "Error on downloading: " + cause.name() + " Exception is: " + realCause);
                }

            }

            /**
             * 更新下载进度
             * @param currentOffset 已经下载的字节数
             */
            private void updateProgressToViewWithMark(long currentOffset) {
                if (_pbDownload.getTag() == null) return;

                final int shrinkRate = (int) _pbDownload.getTag();
                final int progress = (int) ((currentOffset) / shrinkRate);

                _pbDownload.setProgress(progress, true);
            }

            /**
             * 初始化下载进度条
             * @param offset 已经下载的字节数（断点续传时使用）
             * @param total 文件大小
             */
            private void calcProgressToViewAndMark(long offset, long total) {
                final int contentLengthOnInt = reducePrecision(total);
                final int shrinkRate = contentLengthOnInt == 0
                        ? 1 : (int) (total / contentLengthOnInt);
                _pbDownload.setTag(shrinkRate);
                final int progress = (int) (offset / shrinkRate);

                _pbDownload.setMax(contentLengthOnInt);
                _pbDownload.setProgress(progress, true);
            }

            private int reducePrecision(long origin) {
                if (origin <= Integer.MAX_VALUE) return (int) origin;

                int shrinkRate = 10;
                long result = origin;
                while (result > Integer.MAX_VALUE) {
                    result /= shrinkRate;
                    shrinkRate *= 5;
                }

                return (int) result;
            }
        });

        // 在下载的同时，删除昨天或更早的临时文件
        clearCache(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_preview);

        _pdfView = this.findViewById(R.id.pdfView);
        _pbDownload = this.findViewById(R.id.pbDownloading);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String url = this.getIntent().getStringExtra(EXTRA_KEY_URL);
        String pwd = this.getIntent().getStringExtra(EXTRA_KEY_PASSWORD);

        initialDownloadTask(url, pwd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_ID_CLOSE, MENU_ID_CLOSE, getString(R.string.menu_pdf_close));
        menu.add(0, MENU_ID_CLEAR, MENU_ID_CLEAR, getString(R.string.menu_pdf_clean_dir));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_CLEAR:
                clearCache(false);
                break;
            case MENU_ID_CLOSE:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private File getTargetDir() {
        File saveToDir = this.getExternalCacheDir();
        if (saveToDir == null) {
            saveToDir = this.getCacheDir();
        }

        return saveToDir;
    }

    /**
     * 删除临时文件中的PDF文件
     *
     * @param shouldKeepToday 是否保留当天下载的文件
     */
    private void clearCache(boolean shouldKeepToday) {

        // 读取临时文件列表
        File target = getTargetDir();
        File[] files = target.listFiles();

        if (files != null) {

            // 遍历文件
            for (File f : files
            ) {
                // 先判断前缀
                if (f.getName().startsWith(FILE_NAME_PREFIX)) {

                    // 再看变更时间
                    if (!shouldKeepToday || (new Date().getTime() - f.lastModified()) > 24 * 60 * 60 * 1000) {
                        try {
                            if (f.delete()) {
                                Log.v(LOG_TAG, "Cache file was removed: " + f.getName());
                            }
                        } catch (Exception ex) {
                            // 啥都不需要做
                        }
                    }
                }
            }
        }
    }

}