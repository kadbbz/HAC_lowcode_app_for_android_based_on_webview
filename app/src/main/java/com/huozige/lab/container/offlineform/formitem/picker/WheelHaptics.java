package com.huozige.lab.container.offlineform.formitem.picker;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

/**
 * 滚轮逐项滚动时的触感反馈。
 *
 * <p>每跨过一项触发一次短促振动，产生「咔哒」的手感。为避免快速惯性滚动时高频振动把马达
 * 打成蜂鸣，按 {@link #MIN_INTERVAL_MS} 做节流；同时遵循系统「触感反馈」开关，用户关闭后不再振动。
 */
final class WheelHaptics {

    /** 两次振动的最小间隔，快速滑行时把最高频率压到约 20 次/秒。 */
    private static final long MIN_INTERVAL_MS = 50;

    /** Android 8~9 没有预置 TICK 效果，用 10ms 的短振动近似。 */
    private static final long TICK_DURATION_MS = 10;

    private final Vibrator vibrator;
    private final VibrationEffect effect;
    private final boolean enabled;

    private long lastTickAt = Long.MIN_VALUE / 2;

    WheelHaptics(Context context) {
        vibrator = context.getSystemService(Vibrator.class);
        boolean supported = vibrator != null && vibrator.hasVibrator() && isSystemHapticFeedbackEnabled(context);
        enabled = supported;
        if (!supported) {
            effect = null;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);
        } else {
            effect = VibrationEffect.createOneShot(TICK_DURATION_MS, VibrationEffect.DEFAULT_AMPLITUDE);
        }
    }

    /** 跨过一项时调用；节流窗口内的调用会被丢弃。 */
    void tick() {
        if (!enabled) {
            return;
        }
        long now = SystemClock.uptimeMillis();
        if (now - lastTickAt < MIN_INTERVAL_MS) {
            return;
        }
        lastTickAt = now;
        try {
            vibrator.vibrate(effect);
        } catch (RuntimeException ignored) {
            // 个别设备马达驱动异常时静默降级，不影响选择器主流程。
        }
    }

    private static boolean isSystemHapticFeedbackEnabled(Context context) {
        try {
            return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.HAPTIC_FEEDBACK_ENABLED,
                    1) != 0;
        } catch (RuntimeException ignored) {
            return true;
        }
    }
}
