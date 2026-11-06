package com.huozige.lab.container;

import androidx.appcompat.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.Result;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.analyze.Analyzer;
import com.king.zxing.BarcodeCameraScanActivity;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.analyze.MultiFormatAnalyzer;
import com.king.zxing.analyze.QRCodeAnalyzer;

/**
 * 基于摄像头的条码/二维码扫描界面
 */
public class HACQRCodeScanActivity extends BarcodeCameraScanActivity {

    /**
     * 存放条码类型的Extra名称
     */
    public static final String EXTRA_KEY_SCAN_HINTS = "hints";
    // 允许不同扫码入口单独控制是否播放扫码提示音，默认保持原有的播放行为。
    public static final String EXTRA_KEY_PLAY_BEEP = "playBeep";
    /**
     * 仅处理QR码，速度更快
     */
    public static final String EXTRA_QR_CODE_HINTS = "qr";

    @Override
    protected void onResume(){
        super.onResume();

        // 强制隐藏标题栏
        ActionBar aBar = getSupportActionBar();
        if(aBar!=null) aBar.hide();
    }

    @Override
    public void initCameraScan(@NonNull CameraScan<Result> cameraScan) {
        super.initCameraScan(cameraScan);
        // 根据调用入口传入的参数决定是否播放提示音；未传时默认播放。
        cameraScan.setPlayBeep(getIntent().getBooleanExtra(EXTRA_KEY_PLAY_BEEP, true));
    }

    @Nullable
    @Override
    public Analyzer<Result> createAnalyzer() {

        String hintsType = getIntent().getStringExtra(EXTRA_KEY_SCAN_HINTS);
        boolean isQrOnly = hintsType != null && hintsType.equals(EXTRA_QR_CODE_HINTS);

        // 初始化解码配置
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(isQrOnly ? DecodeFormatManager.QR_CODE_HINTS : DecodeFormatManager.ALL_HINTS)//如果只有识别二维码的需求，这样设置效率会更高，不设置默认为DecodeFormatManager.DEFAULT_HINTS
                .setFullAreaScan(false)//设置是否全区域识别，默认false
                .setAreaRectRatio(0.8f)//设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
                .setAreaRectVerticalOffset(0)//设置识别区域垂直方向偏移量，默认为0，为0表示居中，可以为负数
                .setAreaRectHorizontalOffset(0);//设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数
        // BarcodeCameraScanActivity默认使用的MultiFormatAnalyzer，如果只识别二维码，这里可以改为使用QRCodeAnalyzer
        return isQrOnly ? new QRCodeAnalyzer(decodeConfig) : new MultiFormatAnalyzer(decodeConfig);
    }

    @Override
    public void onScanResultCallback(@NonNull AnalyzeResult<Result> result) {
        // 停止分析
        getCameraScan().setAnalyzeImage(false);
        // 返回结果
        Intent intent = new Intent();
        intent.putExtra(CameraScan.SCAN_RESULT, result.getResult().getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
