package com.huozige.lab.container.offlineform.formitem.signature;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/** Shared guide styling for the signing screen and exported images. */
public final class SignatureGuideRenderer {
    private SignatureGuideRenderer() {
    }

    public static void draw(Canvas canvas, int width, int height, String text, Paint paint) {
        if (text == null || text.trim().isEmpty() || width <= 0 || height <= 0) {
            return;
        }
        text = text.trim();
        paint.setColor(Color.rgb(215, 220, 225));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        float textSize = height * 0.62f;
        paint.setTextSize(textSize);
        float measuredWidth = paint.measureText(text);
        float maxWidth = width * 0.78f;
        if (measuredWidth > maxWidth) {
            paint.setTextSize(textSize * maxWidth / measuredWidth);
        }
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float baseline = height / 2f - (metrics.ascent + metrics.descent) / 2f;
        canvas.drawText(text, width / 2f, baseline, paint);
    }
}
