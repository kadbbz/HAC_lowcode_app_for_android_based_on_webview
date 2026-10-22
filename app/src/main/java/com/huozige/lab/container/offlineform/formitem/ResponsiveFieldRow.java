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
 * <p>子 View 约定：第 1 个为标签组，第 2 个为控件；第 3 个（可选）为标题行右侧的元信息
 * （当前用于「字段更新时间」）。元信息存在时固定按「标题行（标签在左、元信息在右）+ 控件下一行」
 * 排列，不再左右排列，避免元信息与控件抢同一行的右侧空间。</p>
 *
 * <p>控件的 {@code layout_width="match_parent"} 表示左右排列时撑满剩余宽度，
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

        // 第 3 个子 View（可选）是标题行右侧的元信息（如「更新于：刚刚」）：
        // 存在时固定「标题行（标签左 + 元信息右）+ 控件下一行」，避免与开关抢右侧空间。
        View meta = getChildCount() > 2 ? getChildAt(2) : null;
        boolean showMeta = meta != null && meta.getVisibility() != View.GONE;
        if (showMeta) {
            measureMeta(meta, available);
            int labelAvailable = Math.max(0, available - meta.getMeasuredWidth() - horizontalGap);
            measureAtMost(label, labelAvailable);
            measureControlStacked(control, available);
            inline = false;
            int firstLineHeight = Math.max(label.getMeasuredHeight(), meta.getMeasuredHeight());
            int desiredHeight = Math.max(firstLineHeight + verticalGap + control.getMeasuredHeight(),
                    stackedMinHeight);
            setMeasuredDimension(
                    resolveSize(widthSize, widthMeasureSpec),
                    resolveSize(desiredHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
            return;
        }

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
        View meta = getChildCount() > 2 ? getChildAt(2) : null;
        boolean showMeta = meta != null && meta.getVisibility() != View.GONE;
        if (showMeta) {
            layoutWithMeta(label, control, meta, l, t, r, b);
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

    /**
     * 标题行 = 标签（左）+ 元信息（右），二者垂直居中；控件换到下一行（match_parent 占整宽，否则靠左）。
     */
    private void layoutWithMeta(View label, View control, View meta, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int right = r - l - getPaddingRight();
        int top = getPaddingTop();
        int available = Math.max(0, right - left);
        int firstLineHeight = Math.max(label.getMeasuredHeight(), meta.getMeasuredHeight());

        int labelHeight = label.getMeasuredHeight();
        int labelTop = top + Math.max(0, (firstLineHeight - labelHeight) / 2);
        label.layout(left, labelTop, left + label.getMeasuredWidth(), labelTop + labelHeight);

        int metaWidth = meta.getMeasuredWidth();
        int metaHeight = meta.getMeasuredHeight();
        int metaLeft = right - metaWidth;
        int metaTop = top + Math.max(0, (firstLineHeight - metaHeight) / 2);
        meta.layout(metaLeft, metaTop, metaLeft + metaWidth, metaTop + metaHeight);

        int controlTop = top + firstLineHeight + verticalGap;
        boolean fill = control.getLayoutParams() != null
                && control.getLayoutParams().width == LayoutParams.MATCH_PARENT;
        int controlWidth = fill ? available : control.getMeasuredWidth();
        control.layout(left, controlTop, left + controlWidth, controlTop + control.getMeasuredHeight());
    }

    /** 按内容自然尺寸测量（不受父级宽度限制）。 */
    private void measureNatural(View child) {
        int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);
    }

    /** 以 AT_MOST 约束测量（标题行内的标签 / 元信息）。 */
    private void measureAtMost(View child, int maxWidth) {
        int widthSpec = MeasureSpec.makeMeasureSpec(Math.max(0, maxWidth), MeasureSpec.AT_MOST);
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);
    }

    /** 标题行右侧的元信息按自身宽度优先测量，空间不足时换行而不是撑出父布局。 */
    private void measureMeta(View meta, int available) {
        measureAtMost(meta, available);
    }

    /** 上下排列时标签占满一行。 */
    private void measureFullWidth(View child, int available) {
        int widthSpec = MeasureSpec.makeMeasureSpec(available, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);
    }

    /** 左右排列时的控件：match_parent 撑满剩余宽度，否则按自身宽度（靠右对齐）。 */
    private void measureControlInline(View control, int remaining) {
        int heightSpec = makeControlHeightSpec(control);
        boolean fill = control.getLayoutParams() != null
                && control.getLayoutParams().width == LayoutParams.MATCH_PARENT;
        int widthSpec = fill
                ? MeasureSpec.makeMeasureSpec(remaining, MeasureSpec.EXACTLY)
                : MeasureSpec.makeMeasureSpec(remaining, MeasureSpec.AT_MOST);
        control.measure(widthSpec, heightSpec);
    }

    /** 上下排列时的控件：match_parent 占整宽，否则按自身宽度。 */
    private void measureControlStacked(View control, int available) {
        int heightSpec = makeControlHeightSpec(control);
        boolean fill = control.getLayoutParams() != null
                && control.getLayoutParams().width == LayoutParams.MATCH_PARENT;
        int widthSpec = fill
                ? MeasureSpec.makeMeasureSpec(available, MeasureSpec.EXACTLY)
                : MeasureSpec.makeMeasureSpec(available, MeasureSpec.AT_MOST);
        control.measure(widthSpec, heightSpec);
    }

    /**
     * 控件高度的测量约束：尊重控件在布局里声明的固定高度（开关控件行声明了 46dp）。
     *
     * <p>不能用 UNSPECIFIED 测量：那会让控件行按子 View 的自然高度收缩（自绘开关只有 30dp 高），
     * 表单项的 98dp 最小高度多出来的空间会全部堆到控件下方，导致开关看起来贴着字段名、
     * 上下留白不对称。按声明高度测量后，开关在 46dp 控件行里垂直居中，上下留白一致。</p>
     */
    private int makeControlHeightSpec(View control) {
        LayoutParams params = control.getLayoutParams();
        if (params != null && params.height >= 0) {
            return MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
        }
        return MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
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
