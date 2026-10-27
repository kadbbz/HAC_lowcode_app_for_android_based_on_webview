package com.huozige.lab.container.offlineform.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * 底部抽屉内容高度统一策略（人员签名抽屉 / 分组目录抽屉共用）：
 * 先按内容自然高度测量，超过视窗比例上限才固定到上限，并让列表在抽屉内部滚动。
 *
 * <p>约定抽屉内容根布局为竖直 {@link LinearLayout}：顶部拖拽条 + 标题行 + 发丝线 + 可滚动列表，
 * 列表是最后一个子 View。测量完成后列表改为 0dp + weight=1，在固定高度的抽屉里吃掉剩余空间。</p>
 */
public final class OfflineFormSheetHeight {

    /** 抽屉最高占视窗高度比例，超过后列表内部滚动。 */
    private static final float MAX_HEIGHT_RATIO = 0.78f;

    private OfflineFormSheetHeight() {
    }

    /**
     * 输入型抽屉的键盘策略：对话框窗口不做 resize（避免窗口重建/内容重绘制造成闪帧），
     * 键盘出现时把整个对话框内容向上平移 IME 高度，并同步收敛内容高度；平移动画与键盘滑动
     * 使用同一时长，保证抽屉底边始终贴着键盘上沿。
     */
    public static void showWithBehaviorSizing(Context context, BottomSheetDialog dialog,
                                              View content, View scrollView) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int hardMaxHeight = Math.round(screenHeight * MAX_HEIGHT_RATIO);

        content.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams scrollParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
        scrollParams.weight = 0;
        scrollParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        scrollView.setLayoutParams(scrollParams);

        content.measure(
                View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int naturalContentHeight = content.getMeasuredHeight();
        int naturalScrollHeight = scrollView.getMeasuredHeight();
        int headerFooterHeight = Math.max(0, naturalContentHeight - naturalScrollHeight);

        applySheetHeights(content, scrollView, naturalContentHeight, naturalScrollHeight,
                headerFooterHeight, hardMaxHeight);

        dialog.setContentView(content);
        dialog.show();

        BottomSheetBehavior<FrameLayout> behavior = dialog.getBehavior();
        behavior.setSkipCollapsed(true);
        behavior.setFitToContents(true);
        behavior.setMaxHeight(hardMaxHeight);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        View decor = dialog.getWindow().getDecorView();
        ViewCompat.setOnApplyWindowInsetsListener(decor, (view, insets) -> {
            int imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
            // 对话框窗口高度通常包含导航 / 手势条区域，而键盘 inset 以内容区为基准；
            // 仅平移 ime 高度会残留这一段间距，这里把差值一并补上，让抽屉底边贴住键盘上沿。
            int extraBottom = Math.max(0, decor.getHeight() - screenHeight);
            animateKeyboardShift(decor, content, scrollView, naturalContentHeight,
                    naturalScrollHeight, headerFooterHeight, hardMaxHeight, screenHeight,
                    imeBottom, extraBottom);
            return insets;
        });
        ViewCompat.requestApplyInsets(decor);
    }

    private static ValueAnimator sKeyboardAnimator;

    /** 平移对话框内容并收敛内容高度；时长与系统键盘动画接近，二者一起上下滑动。 */
    private static void animateKeyboardShift(View decor, View content, View scrollView,
                                             int naturalContentHeight, int naturalScrollHeight,
                                             int headerFooterHeight, int hardMaxHeight,
                                             int screenHeight, int imeBottom, int extraBottom) {
        if (sKeyboardAnimator != null) {
            sKeyboardAnimator.cancel();
            sKeyboardAnimator = null;
        }
        int keyboardBottom = Math.max(0, imeBottom);
        int targetTranslateY = keyboardBottom > 0
                ? -Math.max(0, keyboardBottom - Math.max(0, extraBottom))
                : 0;
        int availableHeight = Math.max(0, screenHeight - keyboardBottom);
        int targetMaxHeight = Math.min(hardMaxHeight,
                availableHeight > 0 ? availableHeight : screenHeight);
        int targetContentHeight = Math.min(naturalContentHeight, targetMaxHeight);
        int targetScrollHeight = Math.max(0,
                Math.min(naturalScrollHeight, targetContentHeight - headerFooterHeight));

        int startTranslateY = Math.round(decor.getTranslationY());
        int startContentHeight = content.getHeight();
        int startScrollHeight = scrollView.getHeight();
        if (startTranslateY == targetTranslateY && startContentHeight == targetContentHeight
                && startScrollHeight == targetScrollHeight) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(260);
        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            decor.setTranslationY(startTranslateY
                    + (targetTranslateY - startTranslateY) * fraction);
            setHeights(content, scrollView,
                    startContentHeight
                            + Math.round((targetContentHeight - startContentHeight) * fraction),
                    Math.max(0, startScrollHeight
                            + Math.round((targetScrollHeight - startScrollHeight) * fraction)));
        });
        sKeyboardAnimator = animator;
        animator.start();
    }

    private static void applySheetHeights(View content, View scrollView, int naturalContentHeight,
                                          int naturalScrollHeight, int headerFooterHeight,
                                          int maxHeight) {
        int contentHeight = Math.min(naturalContentHeight, maxHeight);
        int scrollHeight = Math.max(0,
                Math.min(naturalScrollHeight, contentHeight - headerFooterHeight));
        setHeights(content, scrollView, contentHeight, scrollHeight);
    }

    private static void setHeights(View content, View scrollView, int contentHeight, int scrollHeight) {
        FrameLayout.LayoutParams contentParams =
                (FrameLayout.LayoutParams) content.getLayoutParams();
        contentParams.height = contentHeight;
        content.setLayoutParams(contentParams);

        LinearLayout.LayoutParams scrollParams =
                (LinearLayout.LayoutParams) scrollView.getLayoutParams();
        scrollParams.weight = 0;
        scrollParams.height = scrollHeight;
        scrollView.setLayoutParams(scrollParams);
    }

    /** 按统一策略量高并显示抽屉。 */
    public static void showWithMeasuredHeight(Context context, BottomSheetDialog dialog,
                                              View content, View scrollView) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int maxHeight = Math.round(metrics.heightPixels * MAX_HEIGHT_RATIO);

        ViewGroup.LayoutParams scrollParams = scrollView.getLayoutParams();
        boolean weighted = scrollParams instanceof LinearLayout.LayoutParams;
        if (weighted) {
            ((LinearLayout.LayoutParams) scrollParams).weight = 0;
        }
        scrollParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        scrollView.setLayoutParams(scrollParams);

        content.measure(
                View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
        int height = Math.min(content.getMeasuredHeight(), maxHeight);

        if (weighted) {
            ((LinearLayout.LayoutParams) scrollParams).weight = 1;
        }
        scrollParams.height = 0;
        scrollView.setLayoutParams(scrollParams);

        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        dialog.setContentView(content);
        dialog.show();
    }
}
