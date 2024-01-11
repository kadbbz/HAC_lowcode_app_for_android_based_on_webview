package com.huozige.lab.container.proxy;

import android.content.Intent;
import com.elvishew.xlog.XLog;
import android.webkit.JavascriptInterface;

import com.huozige.lab.container.proxy.support.pdf.PDFPreviewActivity;

/**
 * 让页面具备直接预览PDF文件的能力
 * pdf.preview(url): 预览PDF文件
 */
public class PDFPreviewProxy extends AbstractProxy{

    static final String LOG_TAG = "HAC_PDFPreviewProxy"; // 日志的标识

    @Override
    public String getName() {
        return "pdf";
    }

    @JavascriptInterface
    public void preview(String url, String fileName , String password) {
        XLog.v("["+LOG_TAG+ "]使用本地组件下载和预览PDF文件：" + url);

        Intent intent = new Intent(this.getInterop().getActivityContext(), PDFPreviewActivity.class);
        intent.putExtra(PDFPreviewActivity.EXTRA_KEY_URL, url);
        intent.putExtra(PDFPreviewActivity.EXTRA_KEY_FILENAME, fileName);
        intent.putExtra(PDFPreviewActivity.EXTRA_KEY_PASSWORD, password);
        this.getInterop().getActivityContext().startActivity(intent);
    }
}
