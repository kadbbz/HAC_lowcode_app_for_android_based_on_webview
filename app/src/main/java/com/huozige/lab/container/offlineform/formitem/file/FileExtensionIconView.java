package com.huozige.lab.container.offlineform.formitem.file;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.huozige.lab.container.R;

public class FileExtensionIconView extends View {
    private final Paint filePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint foldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String extension = "";

    public FileExtensionIconView(Context context) {
        super(context);
        init();
    }

    public FileExtensionIconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        filePaint.setColor(getContext().getColor(R.color.huozige_blue));
        filePaint.setStyle(Paint.Style.FILL);
        foldPaint.setColor(getContext().getColor(R.color.white));
        foldPaint.setAlpha(190);
        foldPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(getContext().getColor(R.color.white));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setExtension(String extension) {
        String value = extension == null ? "" : extension.trim().toUpperCase();
        if (value.startsWith(".")) {
            value = value.substring(1);
        }
        this.extension = value.length() > 4 ? value.substring(0, 4) : value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float foldSize = Math.min(width, height) * 0.28f;
        float radius = Math.min(width, height) * 0.08f;

        Path filePath = new Path();
        filePath.moveTo(radius, 0);
        filePath.lineTo(width - foldSize, 0);
        filePath.lineTo(width, foldSize);
        filePath.lineTo(width, height - radius);
        filePath.quadTo(width, height, width - radius, height);
        filePath.lineTo(radius, height);
        filePath.quadTo(0, height, 0, height - radius);
        filePath.lineTo(0, radius);
        filePath.quadTo(0, 0, radius, 0);
        filePath.close();
        canvas.drawPath(filePath, filePaint);

        Path foldPath = new Path();
        foldPath.moveTo(width - foldSize, 0);
        foldPath.lineTo(width, foldSize);
        foldPath.lineTo(width - foldSize, foldSize);
        foldPath.close();
        canvas.drawPath(foldPath, foldPaint);

        if (!extension.isEmpty()) {
            textPaint.setTextSize(Math.max(9f, width / 4.6f));
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float baseline = height * 0.65f - (metrics.ascent + metrics.descent) / 2f;
            canvas.drawText(extension, width / 2f, baseline, textPaint);
        }
    }
}
