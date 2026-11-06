package com.huozige.lab.container.offlineform.formitem.switchitem;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;

import com.huozige.lab.container.R;

/**
 * 离线填报页的自绘开关。
 *
 * <p>视觉：圆角轨道 + 完全内嵌在轨道内的圆形拨手（拨手直径小于轨道高度、四周留 inset），
 * 与「拨手比轨道大、放大绘制后溢出父布局」的 {@code SwitchCompat} 方案不同：
 * 控件宽高就是轨道尺寸（拨手已计入），绘制不会越界，因此在「字段名在上、开关在下」的上下布局里
 * 不会被父布局 {@code clipChildren} 裁掉，也不需要按拨手外溢量做光学内缩。</p>
 *
 * <p>交互：继承 {@link CompoundButton}，点击切换、无障碍状态（开关 / 选中）由系统处理；
 * 用户点击时拨手做 180ms 平移动画，代码回填（bind / 回滚）时直接跳变，避免 RecyclerView 复用时闪烁。</p>
 */
public class OfflineSwitchView extends CompoundButton {

    private static final long TOGGLE_ANIMATION_DURATION_MS = 180;
    // 拨手视觉尺寸收在轨道内，但触摸热区至少 48dp，符合移动端触控标准。
    private static final int MIN_TOUCH_TARGET_DP = 48;

    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint thumbShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF trackRect = new RectF();

    private final int trackWidth;
    private final int trackHeight;
    private final int thumbDiameter;
    private final int thumbInset;
    private final int minTouchTarget;
    private final float thumbShadowOffset;

    private final int trackOnColor;
    private final int trackOnPressedColor;
    private final int trackOffColor;
    private final int trackOffPressedColor;
    private final int trackOnDisabledColor;
    private final int trackOffDisabledColor;

    // 0 = 关闭（拨手在左），1 = 开启（拨手在右）。
    private float thumbFraction;
    private ValueAnimator toggleAnimator;

    public OfflineSwitchView(Context context) {
        this(context, null);
    }

    public OfflineSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OfflineSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        trackWidth = getResources().getDimensionPixelSize(R.dimen.offline_form_switch_width);
        trackHeight = getResources().getDimensionPixelSize(R.dimen.offline_form_switch_height);
        thumbDiameter = getResources().getDimensionPixelSize(R.dimen.offline_form_switch_thumb_size);
        thumbInset = getResources().getDimensionPixelSize(R.dimen.offline_form_switch_thumb_inset);
        minTouchTarget = Math.round(MIN_TOUCH_TARGET_DP * getResources().getDisplayMetrics().density);
        thumbShadowOffset = Math.max(1f, getResources().getDisplayMetrics().density * 0.5f);

        trackOnColor = ContextCompat.getColor(context, R.color.offline_switch_track_on);
        trackOnPressedColor = ContextCompat.getColor(context, R.color.offline_switch_track_on_pressed);
        trackOffColor = ContextCompat.getColor(context, R.color.offline_switch_track_off);
        trackOffPressedColor = ContextCompat.getColor(context, R.color.offline_switch_track_off_pressed);
        trackOnDisabledColor = ContextCompat.getColor(context, R.color.offline_switch_track_on_disabled);
        trackOffDisabledColor = ContextCompat.getColor(context, R.color.offline_switch_track_off_disabled);

        thumbPaint.setStyle(Paint.Style.FILL);
        thumbPaint.setColor(ContextCompat.getColor(context, R.color.offline_switch_thumb));
        thumbShadowPaint.setStyle(Paint.Style.FILL);
        thumbShadowPaint.setColor(0x14000000);

        setBackground(null);
        setPadding(0, 0, 0, 0);
        setGravity(Gravity.CENTER);
        setClickable(true);
        setFocusable(true);
        setMinimumWidth(0);
        setMinimumHeight(0);
        thumbFraction = isChecked() ? 1f : 0f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 宽高即轨道尺寸：拨手完全内嵌，不需要额外放大或留白。
        setMeasuredDimension(
                resolveSize(trackWidth, widthMeasureSpec),
                resolveSize(trackHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        float radius = height / 2f;
        trackRect.set(0f, 0f, width, height);
        trackPaint.setColor(resolveTrackColor());
        canvas.drawRoundRect(trackRect, radius, radius, trackPaint);

        float thumbRadius = Math.min(thumbDiameter / 2f, (height - 2f * thumbInset) / 2f);
        float travel = Math.max(0f, width - 2f * thumbInset - 2f * thumbRadius);
        float fraction = getLayoutDirection() == LAYOUT_DIRECTION_RTL ? 1f - thumbFraction : thumbFraction;
        float thumbCenterX = thumbInset + thumbRadius + travel * fraction;
        float thumbCenterY = height / 2f;

        // 拨手轻微投影：让白色拨手在浅色轨道上仍有层次，同时完全落在轨道内。
        canvas.drawCircle(thumbCenterX, thumbCenterY + thumbShadowOffset,
                thumbRadius, thumbShadowPaint);
        canvas.drawCircle(thumbCenterX, thumbCenterY, thumbRadius, thumbPaint);
    }

    private int resolveTrackColor() {
        if (!isEnabled()) {
            // 禁用态：品牌色 / 轨道色按 40% 淡化，拨手保持纯白（参考 antd-mobile 禁用态）。
            return isChecked() ? trackOnDisabledColor : trackOffDisabledColor;
        }
        if (isChecked()) {
            return isPressed() ? trackOnPressedColor : trackOnColor;
        }
        return isPressed() ? trackOffPressedColor : trackOffColor;
    }

    /**
     * 拨手绘制在轨道之上（互相重叠），声明为重叠渲染，保证父级对控件设置 alpha 时按整层合成，
     * 避免逐绘制指令乘 alpha 导致轨道颜色从拨手透出来。
     */
    @Override
    public boolean hasOverlappingRendering() {
        return true;
    }

    @Override
    public void toggle() {
        // 只有用户点击（CompoundButton#performClick 调 toggle）才播放动画。
        setCheckedInternal(!isChecked(), true);
    }

    @Override
    public void setChecked(boolean checked) {
        // 代码回填（bind / 回滚 / 恢复状态）直接跳变，避免 RecyclerView 复用与数据刷新时的位移动画。
        setCheckedInternal(checked, false);
    }

    private void setCheckedInternal(boolean checked, boolean animate) {
        if (isChecked() == checked) {
            return;
        }
        super.setChecked(checked);
        cancelToggleAnimation();

        float target = checked ? 1f : 0f;
        if (!animate || !isAttachedToWindow()) {
            thumbFraction = target;
            invalidate();
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(thumbFraction, target);
        animator.setDuration(TOGGLE_ANIMATION_DURATION_MS);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            thumbFraction = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
        toggleAnimator = animator;
    }

    private void cancelToggleAnimation() {
        if (toggleAnimator != null) {
            toggleAnimator.cancel();
            toggleAnimator = null;
        }
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        cancelToggleAnimation();
        thumbFraction = isChecked() ? 1f : 0f;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelToggleAnimation();
        super.onDetachedFromWindow();
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        // 按下时轨道颜色加深，替代系统按钮的 ripple 反馈。
        invalidate();
    }

    /** 视觉仅 30dp 高，但命中区域扩到 48dp，保证 PDA / 触屏上易点。 */
    @Override
    public void getHitRect(Rect outRect) {
        super.getHitRect(outRect);
        int horizontal = Math.max(0, (minTouchTarget - outRect.width()) / 2);
        int vertical = Math.max(0, (minTouchTarget - outRect.height()) / 2);
        outRect.inset(-horizontal, -vertical);
    }

    /** 无障碍服务按「开关」播报（默认会按 CompoundButton 的复选框语义播报）。 */
    @Override
    public CharSequence getAccessibilityClassName() {
        return "android.widget.Switch";
    }
}
