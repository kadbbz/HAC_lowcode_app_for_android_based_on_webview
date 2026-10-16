package com.huozige.lab.container.offlineform;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;

import com.huozige.lab.container.R;

/**
 * 自绘复选框：与 {@link OfflineSwitchView} 同风格——28×28 圆角方块，未选=白底 + 淡描边，
 * 选中=品牌蓝底 + 白色对勾；点按热区上下左右加透明留白（可见 28×28，热区 36×44）。
 */
public class OfflineCheckBoxView extends View {

    public interface OnCheckedChangeListener {
        void onCheckedChanged(OfflineCheckBoxView view, boolean isChecked);
    }

    private static final long ANIM_MS = 140L;

    private final float density;
    private final float boxSize;
    private final float cornerRadius;
    private final float touchPadV;
    private final float touchPadH;

    private final Paint boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF boxRect = new RectF();
    private final Path checkPath = new Path();

    private final int colorOn;
    private final int colorOff;
    private final int borderOff;
    private final String descOn;
    private final String descOff;

    private boolean checked;
    private float fraction; // 0 = 未选，1 = 选中
    private OnCheckedChangeListener listener;
    private ValueAnimator animator;

    public OfflineCheckBoxView(Context context) {
        this(context, null);
    }

    public OfflineCheckBoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OfflineCheckBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = getResources().getDisplayMetrics().density;
        boxSize = 28 * density;
        cornerRadius = 8 * density;
        touchPadV = 8 * density; // 28 + 16 = 44 高
        touchPadH = 4 * density; // 28 + 8 = 36 宽

        colorOn = context.getColor(R.color.huozige_blue);
        colorOff = context.getColor(R.color.white);
        borderOff = context.getColor(R.color.offline_form_line_strong);
        descOn = context.getString(R.string.offline_checkbox_state_on);
        descOff = context.getString(R.string.offline_checkbox_state_off);

        boxPaint.setStyle(Paint.Style.FILL);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(Math.max(1.5f, 1.5f * density));
        checkPaint.setStyle(Paint.Style.STROKE);
        checkPaint.setColor(Color.WHITE);
        checkPaint.setStrokeWidth(2.2f * density);
        checkPaint.setStrokeCap(Paint.Cap.ROUND);
        checkPaint.setStrokeJoin(Paint.Join.ROUND);

        setClickable(true);
        setFocusable(true);
        updateContentDescription();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = Math.round(boxSize + touchPadH * 2);
        int h = Math.round(boxSize + touchPadV * 2);
        setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = (getWidth() - boxSize) / 2f;
        float top = (getHeight() - boxSize) / 2f;
        boxRect.set(left, top, left + boxSize, top + boxSize);

        boxPaint.setColor(blend(colorOff, colorOn, fraction));
        canvas.drawRoundRect(boxRect, cornerRadius, cornerRadius, boxPaint);

        borderPaint.setColor(blend(borderOff, colorOn, fraction));
        float inset = borderPaint.getStrokeWidth() / 2f;
        RectF borderRect = new RectF(boxRect);
        borderRect.inset(inset, inset);
        canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint);

        if (fraction > 0f) {
            buildCheckPath(left, top);
            checkPaint.setAlpha(Math.round(255 * Math.min(1f, fraction)));
            canvas.drawPath(checkPath, checkPaint);
        }
    }

    private void buildCheckPath(float left, float top) {
        checkPath.reset();
        checkPath.moveTo(left + boxSize * 0.27f, top + boxSize * 0.53f);
        checkPath.lineTo(left + boxSize * 0.44f, top + boxSize * 0.70f);
        checkPath.lineTo(left + boxSize * 0.74f, top + boxSize * 0.31f);
    }

    private static int blend(int off, int on, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int a = Math.round(Color.alpha(off) + (Color.alpha(on) - Color.alpha(off)) * t);
        int r = Math.round(Color.red(off) + (Color.red(on) - Color.red(off)) * t);
        int g = Math.round(Color.green(off) + (Color.green(on) - Color.green(off)) * t);
        int b = Math.round(Color.blue(off) + (Color.blue(on) - Color.blue(off)) * t);
        return Color.argb(a, r, g, b);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean value) {
        setChecked(value, true);
    }

    /** 设置选中态；binding 回填时传 animate=false。不触发回调。 */
    public void setChecked(boolean value, boolean animate) {
        checked = value;
        float target = value ? 1f : 0f;
        updateContentDescription();
        if (!animate) {
            cancelAnimator();
            fraction = target;
            invalidate();
            return;
        }
        animateTo(target);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    private void toggle() {
        setChecked(!checked, true);
        if (listener != null) {
            listener.onCheckedChanged(this, checked);
        }
    }

    private void animateTo(float target) {
        cancelAnimator();
        animator = ValueAnimator.ofFloat(fraction, target);
        animator.setDuration(ANIM_MS);
        animator.addUpdateListener(animation -> {
            fraction = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    private void cancelAnimator() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                performClick();
                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        toggle();
        return true;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName("android.widget.CheckBox");
        info.setCheckable(true);
        info.setChecked(checked);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
    }

    private void updateContentDescription() {
        setContentDescription(checked ? descOn : descOff);
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAnimator();
        super.onDetachedFromWindow();
    }
}
