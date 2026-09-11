package com.huozige.lab.container.offlineform.formitem.signature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 轻量级原生签名板，避免离线表单依赖 WebView 或网络资源。
 */
public class SignaturePadView extends View {
    private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint guidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private Bitmap existingBitmap;
    private boolean hasStroke;
    private String guideText = "";

    public SignaturePadView(Context context) {
        super(context);
        init();
    }

    public SignaturePadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignaturePadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4f * getResources().getDisplayMetrics().density);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        // Keep the guide visible through the saved PNG's white background while its dark strokes stay on top.
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        guidePaint.setColor(Color.rgb(215, 220, 225));
        guidePaint.setTextAlign(Paint.Align.CENTER);
        guidePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGuideText(canvas);
        if (existingBitmap != null && !existingBitmap.isRecycled()) {
            canvas.drawBitmap(existingBitmap, null, new RectF(0, 0, getWidth(), getHeight()), bitmapPaint);
        }
        canvas.drawPath(path, strokePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                hasStroke = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                return true;
            default:
                return true;
        }
    }

    public boolean hasSignature() {
        return hasStroke || existingBitmap != null;
    }

    public boolean hasNewStroke() {
        return hasStroke;
    }

    public boolean hasExistingSignature() {
        return existingBitmap != null && !existingBitmap.isRecycled();
    }

    public void setGuideText(@Nullable String guideText) {
        this.guideText = guideText == null ? "" : guideText.trim();
        invalidate();
    }

    public void setExistingBitmap(@Nullable Bitmap bitmap) {
        releaseExistingBitmap();
        if (bitmap != null && !bitmap.isRecycled()) {
            existingBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        }
        hasStroke = false;
        invalidate();
    }

    public void clear() {
        path.reset();
        hasStroke = false;
        releaseExistingBitmap();
        invalidate();
    }

    public Bitmap exportBitmap() {
        int width = Math.max(1, getWidth());
        int height = Math.max(1, getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        if (existingBitmap != null && !existingBitmap.isRecycled()) {
            canvas.drawBitmap(existingBitmap, null, new RectF(0, 0, width, height), bitmapPaint);
        }
        canvas.drawPath(path, strokePaint);
        return result;
    }

    @Override
    protected void onDetachedFromWindow() {
        releaseExistingBitmap();
        super.onDetachedFromWindow();
    }

    private void releaseExistingBitmap() {
        if (existingBitmap != null && !existingBitmap.isRecycled()) {
            existingBitmap.recycle();
        }
        existingBitmap = null;
    }

    private void drawGuideText(Canvas canvas) {
        if (guideText.isEmpty() || getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        float textSize = getHeight() * 0.62f;
        guidePaint.setTextSize(textSize);
        float maxWidth = getWidth() * 0.78f;
        float measuredWidth = guidePaint.measureText(guideText);
        if (measuredWidth > maxWidth && measuredWidth > 0) {
            guidePaint.setTextSize(textSize * maxWidth / measuredWidth);
        }
        Paint.FontMetrics metrics = guidePaint.getFontMetrics();
        float baseline = getHeight() / 2f - (metrics.ascent + metrics.descent) / 2f;
        canvas.drawText(guideText, getWidth() / 2f, baseline, guidePaint);
    }
}
