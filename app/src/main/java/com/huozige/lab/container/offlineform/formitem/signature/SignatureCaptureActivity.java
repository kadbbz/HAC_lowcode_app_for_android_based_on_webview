package com.huozige.lab.container.offlineform.formitem.signature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 横屏全屏签名页。这里只负责采集原始签名，最终附件保存和水印处理仍由表单页完成。
 */
public class SignatureCaptureActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "signature-title";
    public static final String EXTRA_USER_NAME = "signature-user-name";
    public static final String EXTRA_DISCLAIMER = "signature-disclaimer";
    public static final String EXTRA_EXISTING_PATH = "signature-existing-path";
    public static final String EXTRA_OUTPUT_PATH = "signature-output-path";
    public static final String EXTRA_UNCHANGED = "signature-unchanged";

    public static Intent createIntent(Context context, String title, String userName, String disclaimer, String existingPath) {
        Intent intent = new Intent(context, SignatureCaptureActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_DISCLAIMER, disclaimer);
        intent.putExtra(EXTRA_EXISTING_PATH, existingPath);
        return intent;
    }

    private SignaturePadView signaturePad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hideSystemBars();
        setContentView(R.layout.offline_signature_capture_activity);

        signaturePad = findViewById(R.id.signature_pad);
        TextView titleView = findViewById(R.id.signature_title);
        TextView disclaimerView = findViewById(R.id.signature_disclaimer);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String disclaimer = getIntent().getStringExtra(EXTRA_DISCLAIMER);
        titleView.setText(userName == null || userName.isEmpty()
                ? title == null || title.isEmpty() ? getString(R.string.offline_title_signature) : title
                : userName);
        if (disclaimer == null || disclaimer.trim().isEmpty()) {
            disclaimerView.setVisibility(View.GONE);
        } else {
            disclaimerView.setText(disclaimer);
            disclaimerView.setVisibility(View.VISIBLE);
        }
        signaturePad.setGuideText(userName);
        loadExistingSignature();

        findViewById(R.id.button_cancel_signature).setOnClickListener(v -> finish());
        findViewById(R.id.button_clear_signature).setOnClickListener(v -> signaturePad.clear());
        findViewById(R.id.button_confirm_signature).setOnClickListener(v -> confirmSignature());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemBars();
        }
    }

    private void confirmSignature() {
        if (!signaturePad.hasSignature()) {
            Toast.makeText(this, R.string.offline_hint_signature, Toast.LENGTH_SHORT).show();
            return;
        }

        if (signaturePad.hasExistingSignature() && !signaturePad.hasNewStroke()) {
            Intent result = new Intent();
            result.putExtra(EXTRA_UNCHANGED, true);
            setResult(Activity.RESULT_OK, result);
            finish();
            return;
        }

        Bitmap bitmap = signaturePad.exportBitmap();
        File outputFile = new File(getCacheDir(), "offline_signature_" + System.currentTimeMillis() + ".png");
        try (FileOutputStream output = new FileOutputStream(outputFile)) {
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) {
                throw new IllegalStateException("签名图片保存失败");
            }
            Intent result = new Intent();
            result.putExtra(EXTRA_OUTPUT_PATH, outputFile.getAbsolutePath());
            setResult(Activity.RESULT_OK, result);
            finish();
        } catch (Exception e) {
            if (outputFile.exists()) {
                outputFile.delete();
            }
            Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
        } finally {
            bitmap.recycle();
        }
    }

    private void loadExistingSignature() {
        String existingPath = getIntent().getStringExtra(EXTRA_EXISTING_PATH);
        if (existingPath == null || existingPath.isEmpty()) {
            return;
        }
        File existingFile = new File(existingPath);
        if (!existingFile.exists()) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(existingPath);
        if (bitmap == null) {
            return;
        }
        signaturePad.setExistingBitmap(bitmap);
        bitmap.recycle();
    }

    private void hideSystemBars() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
