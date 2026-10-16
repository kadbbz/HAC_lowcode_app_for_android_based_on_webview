package com.huozige.lab.container.offlineform;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;

import com.huozige.lab.container.R;

/**
 * 自绘开关：比原生更大、更清楚的品牌色拨动开关。
 *
 * <p>轨道 48×28、圆角 = 高/2；圆钮 24 白色 + 淡投影，内缩 2dp；开=品牌蓝、关=深灰；
 * 点按热区上下加透明留白（可见 48×28，热区 48×44），便于 PDA 单手/戴手套操作。</p>
 */
public class OfflineSwitchView extends View {

    public interface OnCheckedChangeListener {
        void onCheckedChanged(OfflineSwitchView view, boolean isChecked);
    }

    private static final long ANIM_MS = 180L;

    private final float density;
    private final float trackWidth;
    private final float trackHeight;
    private final float knobSize;
    private final float knobInset;
    private final float touchPadV;
    private final float touchPadH;

    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint knobShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint knobRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint knobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF trackRect = new RectF();

    private final int colorOff;
    private final int colorOn;
    private final String descOn;
    private final String descOff;

    private boolean checked;
    private float fraction; // 0 = 关，1 = 开
    private boolean pressed;
    private OnCheckedChangeListener listener;
    private ValueAnimator animator;

    public OfflineSwitchView(Context context) {
        this(context, null);
    }

    public OfflineSwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OfflineSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = getResources().getDisplayMetrics().density;
        trackWidth = 48 * density;
        trackHeight = 28 * density;
        knobSize = 24 * density;
        knobInset = (trackHeight - knobSize) / 2f;
        touchPadV = 8 * density; // 28 + 16 = 44 高（点按热区）
        touchPadH = 0f; // 不额外加宽，右侧与输入框对齐

        colorOn = context.getColor(R.color.huozige_blue);
        colorOff = context.getColor(R.color.offline_form_switch_off);
        descOn = context.getString(R.string.offline_switch_state_on);
        descOff = context.getString(R.string.offline_switch_state_off);

        trackPaint.setStyle(Paint.Style.FILL);
        knobPaint.setStyle(Paint.Style.FILL);
        knobPaint.setColor(Color.WHITE);
        knobRingPaint.setStyle(Paint.Style.STROKE);
        knobRingPaint.setStrokeWidth(Math.max(1f, density));
        knobRingPaint.setColor(0x14000000);
        knobShadowPaint.setStyle(Paint.Style.FILL);
        knobShadowPaint.setColor(0x1F000000);

        setClickable(true);
        setFocusable(true);
        updateContentDescription();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = Math.round(trackWidth + touchPadH * 2);
        int h = Math.round(trackHeight + touchPadV * 2);
        setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = (getWidth() - trackWidth) / 2f;
        float top = (getHeight() - trackHeight) / 2f;
        trackRect.set(left, top, left + trackWidth, top + trackHeight);
        float radius = trackHeight / 2f;

        trackPaint.setColor(blend(colorOff, colorOn, fraction));
        canvas.drawRoundRect(trackRect, radius, radius, trackPaint);

        float cx = left + knobInset + knobSize / 2f + fraction * (trackWidth - knobSize - knobInset * 2);
        float cy = top + trackHeight / 2f;
        float kr = knobSize / 2f;
        // 投影（用偏移的半透明圆模拟，硬件加速下也可用）
        canvas.drawCircle(cx, cy + density, kr + density, knobShadowPaint);
        canvas.drawCircle(cx, cy, kr, knobPaint);
        canvas.drawCircle(cx, cy, kr - knobRingPaint.getStrokeWidth() / 2f, knobRingPaint);
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

    /** 设置选中态（默认带滑动动画）。 */
    public void setChecked(boolean value) {
        setChecked(value, true);
    }

    /** 设置选中态；binding 回填时传 animate=false，避免闪动。不触发回调。 */
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
                pressed = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_CANCEL:
                pressed = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                pressed = false;
                invalidate();
                performClick();
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
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            pressed = false;
        }
        invalidate();
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName("android.widget.Switch");
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
