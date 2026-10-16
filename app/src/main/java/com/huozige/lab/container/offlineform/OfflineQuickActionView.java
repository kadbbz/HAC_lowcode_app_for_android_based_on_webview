package com.huozige.lab.container.offlineform;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huozige.lab.container.R;

/**
 * 填报页边缘的悬浮快捷按钮组。
 *
 * <p>主按钮放在一个“把手”容器里：朝向屏幕内侧的一侧全圆角、朝向屏边的一侧为方角并直接连到边框。
 * 收起时把手只露圆角那一小段（半圆贴边），点击后整组移入屏内完整显示并向上弹出其它圆形按钮；
 * 2 秒无操作会自动收回。整组可拖动，松手后自动吸附到左右边缘。</p>
 */
public class OfflineQuickActionView extends FrameLayout {

    public interface ActionListener {
        void onScrollToTop();

        void onJumpToFirstRequiredEmpty();

        void onCollapseAllGroups();
    }

    private static final int ACTION_COUNT = 3;
    private static final long AUTO_COLLAPSE_DELAY_MS = 2000L;

    private final int buttonSize;
    private final int handlePadding;
    private final int gap;
    private final int margin;
    private final int touchSlop;

    private FrameLayout handleContainer;
    private LinearLayout actionContainer;
    private ImageView toggleButton;
    private final ImageView[] actionButtons = new ImageView[ACTION_COUNT];
    private boolean expanded;
    private ActionListener listener;
    private int bottomInset;
    // 当前吸附在左/右侧；收起时半圆贴边，展开时移入屏内完整显示。
    private boolean dockedLeft;
    private boolean initialDockApplied;

    private final Handler autoCollapseHandler = new Handler(Looper.getMainLooper());
    private final Runnable autoCollapseRunnable = this::collapse;

    private float downRawX;
    private float downRawY;
    private float startX;
    private float startY;
    private boolean dragging;

    public OfflineQuickActionView(Context context) {
        super(context);
        float density = context.getResources().getDisplayMetrics().density;
        buttonSize = Math.round(36 * density);
        handlePadding = Math.round(6 * density);
        gap = Math.round(8 * density);
        margin = Math.round(16 * density);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        buildViews(context);
    }

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    /** 底部避让高度（如底部导航栏），用于拖动时限制范围。 */
    public void setBottomInset(int bottomInset) {
        this.bottomInset = Math.max(0, bottomInset);
    }

    private int handleHeight() {
        return buttonSize + handlePadding * 2;
    }

    private int handleWidth() {
        return handleHeight();
    }

    public int getPreferredWidth() {
        return handleWidth();
    }

    /** 预留整组高度，保证折叠/展开时主按钮位置稳定。 */
    public int getPreferredHeight() {
        return handleHeight() + gap + ACTION_COUNT * (buttonSize + gap);
    }

    private void buildViews(Context context) {
        actionButtons[0] = createActionButton(context, R.drawable.ic_offline_quick_top,
                context.getString(R.string.offline_quick_action_top), v -> {
                    if (listener != null) {
                        listener.onScrollToTop();
                    }
                    collapse();
                });
        actionButtons[1] = createActionButton(context, R.drawable.ic_offline_quick_required,
                context.getString(R.string.offline_quick_action_required), v -> {
                    if (listener != null) {
                        listener.onJumpToFirstRequiredEmpty();
                    }
                    collapse();
                });
        actionButtons[2] = createActionButton(context, R.drawable.ic_offline_quick_collapse,
                context.getString(R.string.offline_quick_action_collapse), v -> {
                    if (listener != null) {
                        listener.onCollapseAllGroups();
                    }
                    collapse();
                });

        actionContainer = new LinearLayout(context);
        actionContainer.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(
                buttonSize, LayoutParams.WRAP_CONTENT);
        containerParams.gravity = Gravity.START | Gravity.BOTTOM;
        containerParams.leftMargin = handlePadding;
        containerParams.bottomMargin = handleHeight() + gap;
        addView(actionContainer, containerParams);

        for (ImageView actionButton : actionButtons) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
            params.bottomMargin = gap;
            actionContainer.addView(actionButton, params);
        }

        // “把手”容器：朝向屏内一侧全圆角，朝向屏边一侧方角并连到边框。
        handleContainer = new FrameLayout(context);
        handleContainer.setBackgroundResource(R.drawable.offline_quick_handle_bg_right);
        FrameLayout.LayoutParams handleParams = new FrameLayout.LayoutParams(
                handleWidth(), handleHeight());
        handleParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        addView(handleContainer, handleParams);

        toggleButton = new ImageView(context);
        toggleButton.setBackgroundResource(R.drawable.offline_quick_toggle_bg);
        toggleButton.setImageResource(R.drawable.ic_offline_quick_add);
        toggleButton.setScaleType(ImageView.ScaleType.CENTER);
        toggleButton.setContentDescription(context.getString(R.string.offline_quick_toggle));
        FrameLayout.LayoutParams toggleParams = new FrameLayout.LayoutParams(buttonSize, buttonSize);
        toggleParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        toggleParams.leftMargin = handlePadding;
        handleContainer.addView(toggleButton, toggleParams);

        handleContainer.setOnTouchListener(this::handleToggleTouch);
    }

    private ImageView createActionButton(Context context, int iconRes, String description,
                                         OnClickListener clickListener) {
        ImageView button = new ImageView(context);
        button.setBackgroundResource(R.drawable.offline_quick_item_bg);
        button.setImageResource(iconRes);
        button.setColorFilter(context.getColor(R.color.huozige_blue), PorterDuff.Mode.SRC_IN);
        button.setScaleType(ImageView.ScaleType.CENTER);
        button.setContentDescription(description);
        button.setOnClickListener(clickListener);
        button.setVisibility(View.INVISIBLE);
        button.setAlpha(0f);
        return button;
    }

    private boolean handleToggleTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downRawX = event.getRawX();
                downRawY = event.getRawY();
                startX = getX();
                startY = getY();
                dragging = false;
                // 用户开始操作：暂停自动收回计时。
                cancelAutoCollapse();
                return true;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - downRawX;
                float deltaY = event.getRawY() - downRawY;
                if (!dragging && (Math.abs(deltaX) > touchSlop || Math.abs(deltaY) > touchSlop)) {
                    dragging = true;
                }
                if (dragging) {
                    setX(startX + deltaX);
                    setY(startY + deltaY);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (dragging) {
                    snapToEdge();
                } else {
                    toggle();
                }
                dragging = false;
                // 展开态下重新开始 2 秒无操作计时；收起态则无需计时。
                if (expanded) {
                    scheduleAutoCollapse();
                } else {
                    cancelAutoCollapse();
                }
                return true;
            default:
                return false;
        }
    }

    private void toggle() {
        if (expanded) {
            collapse();
        } else {
            expand();
        }
    }

    private void expand() {
        expanded = true;
        // 展开时移入屏内，完整显示动作按钮。
        moveToDockPosition(true, true);
        toggleButton.animate().rotation(45f).setDuration(150).start();
        for (int i = 0; i < actionButtons.length; i++) {
            ImageView button = actionButtons[i];
            button.animate().cancel();
            button.setVisibility(View.VISIBLE);
            button.setAlpha(0f);
            button.setTranslationY(gap * 3f);
            button.animate().alpha(1f).translationY(0f)
                    .setDuration(160).setStartDelay(i * 30L).start();
        }
        scheduleAutoCollapse();
    }

    private void collapse() {
        expanded = false;
        cancelAutoCollapse();
        // 收起后回到半圆贴边。
        moveToDockPosition(false, true);
        toggleButton.animate().rotation(0f).setDuration(150).start();
        for (int i = 0; i < actionButtons.length; i++) {
            final ImageView button = actionButtons[i];
            button.animate().cancel();
            button.animate().alpha(0f).translationY(gap * 3f)
                    .setDuration(120).setStartDelay(i * 20L)
                    .withEndAction(() -> button.setVisibility(View.INVISIBLE))
                    .start();
        }
    }

    /** 展开后若 2 秒内无操作，自动收回为半圆贴边。 */
    private void scheduleAutoCollapse() {
        autoCollapseHandler.removeCallbacks(autoCollapseRunnable);
        autoCollapseHandler.postDelayed(autoCollapseRunnable, AUTO_COLLAPSE_DELAY_MS);
    }

    private void cancelAutoCollapse() {
        autoCollapseHandler.removeCallbacks(autoCollapseRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAutoCollapse();
        super.onDetachedFromWindow();
    }

    /** 松手后吸附到左右两侧边缘，并把纵向范围限制在可视区内。 */
    private void snapToEdge() {
        moveToDockPosition(expanded, true);
    }

    /**
     * 移动到贴边位置：收起时只露圆角那一小段（半圆），展开时移入屏内完整显示。
     *
     * @param fullyVisible 是否完整显示（展开时为 true）
     * @param animate      是否使用动画
     */
    private void moveToDockPosition(boolean fullyVisible, boolean animate) {
        View parent = (View) getParent();
        if (parent == null || parent.getWidth() == 0) {
            return;
        }
        updateDockSide();
        applyHandleOrientation();
        float targetX = targetX(fullyVisible);
        float targetY = clampY(getY());
        if (animate) {
            animate().x(targetX).y(targetY).setDuration(180).start();
        } else {
            setX(targetX);
            setY(targetY);
        }
    }

    private void updateDockSide() {
        View parent = (View) getParent();
        if (parent == null || parent.getWidth() == 0) {
            return;
        }
        dockedLeft = (getX() + getWidth() / 2f) < parent.getWidth() / 2f;
    }

    /** 依据吸附方向，把把手的圆角面朝向屏内、方角面朝向屏边，并让主按钮/子按钮贴向圆角一侧。 */
    private void applyHandleOrientation() {
        if (handleContainer == null) {
            return;
        }
        handleContainer.setBackgroundResource(dockedLeft
                ? R.drawable.offline_quick_handle_bg_left
                : R.drawable.offline_quick_handle_bg_right);

        FrameLayout.LayoutParams toggleParams = (FrameLayout.LayoutParams) toggleButton.getLayoutParams();
        toggleParams.gravity = (dockedLeft ? Gravity.END : Gravity.START) | Gravity.CENTER_VERTICAL;
        toggleParams.leftMargin = dockedLeft ? 0 : handlePadding;
        toggleParams.rightMargin = dockedLeft ? handlePadding : 0;
        toggleButton.setLayoutParams(toggleParams);

        FrameLayout.LayoutParams containerParams = (FrameLayout.LayoutParams) actionContainer.getLayoutParams();
        containerParams.gravity = (dockedLeft ? Gravity.END : Gravity.START) | Gravity.BOTTOM;
        containerParams.leftMargin = dockedLeft ? 0 : handlePadding;
        containerParams.rightMargin = dockedLeft ? handlePadding : 0;
        actionContainer.setLayoutParams(containerParams);
    }

    /**
     * 目标贴边位置：
     * <ul>
     *     <li>收起（{@code fullyVisible=false}）：只露圆角那一半，即半圆贴边；</li>
     *     <li>展开（{@code fullyVisible=true}）：完整移入屏内，方角一侧连到边框。</li>
     * </ul>
     */
    private float targetX(boolean fullyVisible) {
        View parent = (View) getParent();
        int parentWidth = parent.getWidth();
        int size = handleHeight();
        int half = size / 2;
        if (dockedLeft) {
            // 左侧吸附：方角连左边框；收起只露右侧圆角那一半。
            return fullyVisible ? 0f : -(size - half);
        }
        return parentWidth - (fullyVisible ? size : half);
    }

    private float clampY(float y) {
        View parent = (View) getParent();
        if (parent == null) {
            return y;
        }
        float minY = 0f;
        float maxY = Math.max(minY, parent.getHeight() - getHeight() - bottomInset - margin);
        return Math.max(minY, Math.min(maxY, y));
    }

    /** 首次布局后自动吸附到右侧、半圆贴边。 */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!initialDockApplied && getWidth() > 0) {
            initialDockApplied = true;
            moveToDockPosition(false, false);
        }
    }
}
