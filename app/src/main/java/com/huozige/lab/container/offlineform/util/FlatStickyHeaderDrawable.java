package com.huozige.lab.container.offlineform.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huozige.lab.container.R;

/**
 * 平铺布局「当前分组标题行」粘滞条背景。
 *
 * <p>由「页面灰底层 + 白色圆角卡片层 + 底部发丝线」组成：
 * 顶部固定 12dp 圆角（覆盖白色卡片内容时圆角缺口透灰才能看出圆角）；
 * 底部圆角与底部发丝线由离场进度驱动 —— 粘滞条被自己卡片尾部上推时，
 * 底部圆角从 0 逐步过渡到 12dp、底部发丝线同步淡出，形成与卡片尾部无缝衔接的离场效果。</p>
 */
public class FlatStickyHeaderDrawable extends Drawable {
    private final Paint pageFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint cardFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path shapePath = new Path();
    private final RectF bounds = new RectF();
    private final float topCornerRadius;
    private final float maxBottomCornerRadius;
    private final float dividerHeight;
    // 0 = 底部直角，1 = 底部完全 12dp 圆角（离场过程按滚动距离逐步过渡）。
    private float detachProgress;

    public FlatStickyHeaderDrawable(Context context) {
        pageFillPaint.setColor(context.getColor(R.color.offline_form_page_bg));
        cardFillPaint.setColor(context.getColor(R.color.white));
        dividerPaint.setColor(context.getColor(R.color.offline_form_line));
        topCornerRadius = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_content_corner_radius);
        maxBottomCornerRadius = topCornerRadius;
        dividerHeight = context.getResources().getDisplayMetrics().density;
    }

    /**
     * 设置离场进度：0 = 粘滞条常驻（底部直角 + 分隔线），1 = 完全离场（底部 12dp 圆角、分隔线淡出）。
     */
    public void setDetachProgress(float progress) {
        float clamped = Math.max(0f, Math.min(1f, progress));
        if (Math.abs(clamped - detachProgress) < 0.001f) {
            return;
        }
        detachProgress = clamped;
        invalidateSelf();
    }

    /** 将离场进度换算为底部圆角半径（px），供 Activity 根据滚动距离计算进度。 */
    public float getMaxBottomCornerRadius() {
        return maxBottomCornerRadius;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect b = getBounds();
        if (b.isEmpty()) {
            return;
        }
        bounds.set(b);
        canvas.drawRect(bounds, pageFillPaint);

        float bottomRadius = maxBottomCornerRadius * detachProgress;
        shapePath.reset();
        shapePath.addRoundRect(bounds, new float[]{
                topCornerRadius, topCornerRadius,
                topCornerRadius, topCornerRadius,
                bottomRadius, bottomRadius,
                bottomRadius, bottomRadius
        }, Path.Direction.CW);
        canvas.drawPath(shapePath, cardFillPaint);

        int dividerAlpha = Math.round((1f - detachProgress) * 255);
        if (dividerAlpha > 1) {
            dividerPaint.setAlpha(dividerAlpha);
            canvas.save();
            canvas.clipPath(shapePath);
            canvas.drawRect(bounds.left, bounds.bottom - dividerHeight,
                    bounds.right, bounds.bottom, dividerPaint);
            canvas.restore();
        }
    }

    @Override
    public void setAlpha(int alpha) {
        // 背景不整体调透明度：透明度只作用于分隔线，见 draw()。
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // 不支持颜色滤镜。
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
