package com.huozige.lab.container.utilities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 颜色相关的帮助类
 */
public class ColorUtility {

    // 灰度值的阈值，高于高值意味着深色，否则为暗色
    public static double GRAYSCALE_THRESHOLD_BRIGHT_COLOR = 122.5;

    /**
     * 获取灰度值
     *
     * @param color 色彩
     * @return 灰度值（0-255）
     */
    public static double getGrayScale(@ColorInt int color) {
        return getGrayScale(Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * 获取灰度值
     *
     * @param red   红色分量
     * @param green 绿色分量
     * @param blue  蓝色分量
     * @return 灰度值（0-255）
     */
    public static double getGrayScale(int red, int green, int blue) {
        return (red * 0.299) + (green * 0.587) + (blue * 0.114);
    }

    /**
     * 重置图片的颜色
     *
     * @param orig     原始图片
     * @param colorRes 新的颜色
     * @return 处理后的图片
     */
    public static Drawable resetImageTintColorTo(Drawable orig, @ColorInt int colorRes) {
        Drawable wrap = DrawableCompat.wrap(orig).mutate();
        //将Drawable重新着色
        DrawableCompat.setTintList(wrap, ColorStateList.valueOf(colorRes));
        DrawableCompat.setTint(wrap, colorRes);
        return wrap;
    }
}
