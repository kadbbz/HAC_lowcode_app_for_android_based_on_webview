package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 正方形帧布局：让图片区与添加瓦片在不同列宽下始终保持 1:1。
 *
 * <p>关键点：在 super.onMeasure 之前就把高度测量方式改成 EXACTLY（= 宽度），
 * 否则子 View（如 match_parent 的 ImageView）会先按 AT_MOST 测成 0/错误高度，
 * 导致 Glide 拿不到有效尺寸而一直等待布局，最终图片区域显示为空白。</p>
 */
public class SquareFrameLayout extends FrameLayout {

    public SquareFrameLayout(@NonNull Context context) {
        super(context);
    }

    public SquareFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int squareSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, squareSpec);
    }
}
