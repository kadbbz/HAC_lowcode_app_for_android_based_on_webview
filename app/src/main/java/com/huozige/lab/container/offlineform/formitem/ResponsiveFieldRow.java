package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.huozige.lab.container.R;

/**
 * 「标签 + 控件」的响应式行容器（全局规则，当前仅开关组件使用）。
 *
 * <p>宽度足够时左右排列（标签在左、控件在右）；空间不足时自动改为上下排列（标签在上、控件整宽）。
 * 是否放得下按「标签自然宽度 ≤ 行宽 × {@code offline_form_field_inline_label_max_ratio}」且
 * 「标签宽 + 水平间距 + 控件最小可用宽度 ≤ 行宽」判定。</p>
 *
 * <p>子 View 约定（必须且只能有两个）：第 1 个为标签组，第 2 个为控件；
 * 控件的 {@code layout_width="match_parent"} 表示左右排列时撑满剩余宽度，
 * {@code wrap_content} 表示按自身宽度靠右对齐（开关用这种方式）。标签在左右排列时垂直居中。</p>
 */
public class ResponsiveFieldRow extends ViewGroup {

    // 左右排列时标签最多占行宽的比例（超过即认为放不下）。
    private final float labelMaxWidthRatio;
    // 左右排列时标签与控件之间的水平间距。
    private final int horizontalGap;
    // 上下排列时标签与控件之间的垂直间距。
    private final int verticalGap;
    // 左右排列时的最小行高（控件高度优先）。
    private final int inlineMinHeight;
    // 上下排列时的最小行高（标签 + 间距 + 控件）。
    private final int stackedMinHeight;
    // 左右排列时控件需要的最小可用宽度，可在布局里用 app:rf_controlMinWidth 覆盖。
    private final int controlMinWidth;

    // 本次测量结果：是否左右排列。
    private boolean inline;

    public ResponsiveFieldRow(Context context) {
        this(context, null);
    }

    public ResponsiveFieldRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponsiveFieldRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 默认值来自资源，保证「全局规则」只有一处定义。
        labelMaxWidthRatio = context.getResources()
                .getFraction(R.fraction.offline_form_field_inline_label_max_ratio, 1, 1);
        horizontalGap = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_inline_gap);
        verticalGap = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_item_label_gap);
        inlineMinHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_inline_min_height);
        stackedMinHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_stacked_min_height);
        int defaultControlMinWidth = context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_control_min_width_switch);
        int controlMinWidth = defaultControlMinWidth;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ResponsiveFieldRow);
            controlMinWidth = array.getDimensionPixelSize(
                    R.styleable.ResponsiveFieldRow_rf_controlMinWidth, defaultControlMinWidth);
            array.recycle();
        }
        this.controlMinWidth = controlMinWidth;
    }

    /** 当前是否以左右排列渲染（供调试/测试使用）。 */
    public boolean isInline() {
        return inline;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        View label = getChildAt(0);
        View control = getChildAt(1);
        if (label == null || control == null) {
            setMeasuredDimension(resolveSize(widthSize, widthMeasureSpec),
                    resolveSize(getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
            return;
        }

        int available = Math.max(0, widthSize - getPaddingLeft() - getPaddingRight());

        // 1) 先按自然宽度测量标签，判断左右排列是否放得下。
        measureNatural(label);
        int labelNaturalWidth = label.getMeasuredWidth();
        boolean labelAllowInline = labelNaturalWidth <= available * labelMaxWidthRatio;
        boolean controlFitsInline =
                labelNaturalWidth + horizontalGap + controlMinWidth <= available;
        boolean showInline = labelAllowInline && controlFitsInline;
        inline = showInline;

        int desiredHeight;
        if (showInline) {
            // 左右：控件占剩余宽度或按自身宽度靠右，标签垂直居中。
            int remaining = Math.max(0, available - labelNaturalWidth - horizontalGap);
            measureControlInline(control, remaining);
            desiredHeight = Math.max(
                    Math.max(label.getMeasuredHeight(), control.getMeasuredHeight()),
                    inlineMinHeight);
        } else {
            // 上下：标签整宽，控件换行到下方。
            measureFullWidth(label, available);
            measureControlStacked(control, available);
            desiredHeight = Math.max(
                    label.getMeasuredHeight() + verticalGap + control.getMeasuredHeight(),
                    stackedMinHeight);
        }

        setMeasuredDimension(
                resolveSize(widthSize, widthMeasureSpec),
                resolveSize(desiredHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View label = getChildAt(0);
        View control = getChildAt(1);
        if (label == null || control == null) {
            return;
        }
        int contentHeight = b - t - getPaddingTop() - getPaddingBottom();

        if (inline) {
            int labelHeight = label.getMeasuredHeight();
            int labelTop = getPaddingTop() + Math.max(0, (contentHeight - labelHeight) / 2);
            label.layout(getPaddingLeft(), labelTop,
                    getPaddingLeft() + label.getMeasuredWidth(), labelTop + labelHeight);

            // 控件右对齐：撑满剩余宽度时正好与标签右边缘的间距对齐，按自身宽度时贴右侧。
            int controlWidth = control.getMeasuredWidth();
            int controlHeight = control.getMeasuredHeight();
            int controlLeft = r - l - getPaddingRight() - controlWidth;
            int controlTop = getPaddingTop() + Math.max(0, (contentHeight - controlHeight) / 2);
            control.layout(controlLeft, controlTop,
                    controlLeft + controlWidth, controlTop + controlHeight);
        } else {
            int labelHeight = label.getMeasuredHeight();
            label.layout(getPaddingLeft(), getPaddingTop(),
                    getPaddingLeft() + label.getMeasuredWidth(), getPaddingTop() + labelHeight);
            int controlTop = getPaddingTop() + labelHeight + verticalGap;
            int controlWidth = control.getMeasuredWidth();
            control.layout(getPaddingLeft(), controlTop,
                    getPaddingLeft() + controlWidth, controlTop + control.getMeasuredHeight());
        }
    }

    /** 按内容自然尺寸测量（不受父级宽度限制）。 */
    private void measureNatural(View child) {
        int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);
    }

    /** 上下排列时标签占满一行。 */
    private void measureFullWidth(View child, int available) {
        int widthSpec = MeasureSpec.makeMeasureSpec(available, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);
    }

    /** 左右排列时的控件：match_parent 撑满剩余宽度，否则按自身宽度（靠右对齐）。 */
    private void measureControlInline(View control, int remaining) {
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        boolean fill = control.getLayoutParams() != null
                && control.getLayoutParams().width == LayoutParams.MATCH_PARENT;
        int widthSpec = fill
                ? MeasureSpec.makeMeasureSpec(remaining, MeasureSpec.EXACTLY)
                : MeasureSpec.makeMeasureSpec(remaining, MeasureSpec.AT_MOST);
        control.measure(widthSpec, heightSpec);
    }

    /** 上下排列时的控件：match_parent 占整宽，否则按自身宽度。 */
    private void measureControlStacked(View control, int available) {
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        boolean fill = control.getLayoutParams() != null
                && control.getLayoutParams().width == LayoutParams.MATCH_PARENT;
        int widthSpec = fill
                ? MeasureSpec.makeMeasureSpec(available, MeasureSpec.EXACTLY)
                : MeasureSpec.makeMeasureSpec(available, MeasureSpec.AT_MOST);
        control.measure(widthSpec, heightSpec);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p != null;
    }
}
