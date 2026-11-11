package com.huozige.lab.container.utilities;

import android.webkit.CookieManager;

import com.elvishew.xlog.XLog;

/**
 * WebView Cookie 刷盘帮助类
 * <p>
 * WebView（Chromium）的 Cookie 采用延迟批量写盘，实测登录后约 30 秒才会写入磁盘。
 * 登录成功后的 Set-Cookie、退出登录后的删除操作，如果在这个窗口内进程被杀，
 * 磁盘上会残留旧状态——表现为“重开APP掉登录”或“退出登录后重开又自动登录”。
 * 因此需要在页面加载完成、退到后台、主动杀进程前主动刷盘。
 */
public class CookieUtility {

    private CookieUtility() {
    }

    /**
     * 在后台线程刷盘，不阻塞调用线程。适用于页面加载完成等主线程回调。
     */
    public static void flushAsync() {
        new Thread(CookieUtility::flush, "hac-cookie-flush").start();
    }

    /**
     * 同步刷盘，调用线程会阻塞到写盘完成。适用于退到后台、进程即将退出等需要确定性的场景。
     */
    public static void flush() {
        try {
            CookieManager.getInstance().flush();
            XLog.v("Cookie已刷盘");
        } catch (Exception ex) {
            XLog.e("Cookie刷盘失败。\r\n%s", ex);
        }
    }
}
