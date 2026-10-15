package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.formitem.file.FileExtensionIconView;
import com.huozige.lab.container.offlineform.formitem.list.ListViewHolder;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDisplayItem;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormProgress;
import com.huozige.lab.container.offlineform.model.OfflineFormProgressCalculator;
import com.huozige.lab.container.offlineform.model.OfflineFormProgressDisplay;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimeViews;
import com.huozige.lab.container.offlineform.util.Utils;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class FormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ROOT_GROUP = 10003;

    private List<OfflineFormDisplayItem> displayItems = new ArrayList<>();
    private List<OfflineFormDisplayItem> rootItems = new ArrayList<>();
    // 折叠状态按分组节点（对象身份）保存，跨重渲染保留；使用 identity set 以规避 OfflineFormNode 的值相等语义。
    private final Set<OfflineFormNode> collapsedGroupNodes =
            Collections.newSetFromMap(new IdentityHashMap<OfflineFormNode, Boolean>());
    // 已按其“默认折叠”配置初始化过的分组节点，保证默认值只应用一次、之后尊重用户操作。
    private final Set<OfflineFormNode> defaultCollapseInitialized =
            Collections.newSetFromMap(new IdentityHashMap<OfflineFormNode, Boolean>());
    private Map<Integer, OfflineFormItemHandler> handlersByViewType = new HashMap<>();
    private OfflineFormDefinition definition;
    private Map<String, String> formValues = new HashMap<>();
    private OnDocumentClickListener documentClickListener;
    private List<String> documentFileNames = new ArrayList<>();
    // 字段更新时间提供者：随记录变化实时取值，避免 notifyDataSetChanged 时读到旧快照。
    private FieldUpdateTimesProvider fieldUpdateTimesProvider;
    // 字段值变更监听：用于即时刷新更新时间并安排防抖落盘。
    private OnFieldChangedListener onFieldChangedListener;
    // 已锁定（二次确认后禁用）字段 provider。
    private LockedFieldIdsProvider lockedFieldIdsProvider;
    // 字段被锁定时回调外层，用于把锁定状态持久化到记录。
    private OnFieldLockedListener onFieldLockedListener;
    // 需要高亮的字段 id provider（筛选未填时标记缺填的必填项）。
    private HighlightedFieldIdsProvider highlightedFieldIdsProvider;

    public interface OnDocumentClickListener {
        void onDocumentClick(OfflineFormDisplayItem item, String fileName);
    }

    public interface OnFieldChangedListener {
        /** 用户完成一次字段值变更（用于防抖落盘）。 */
        void onFieldChanged();
    }

    public void setOnFieldChangedListener(OnFieldChangedListener listener) {
        onFieldChangedListener = listener;
    }

    public interface LockedFieldIdsProvider {
        /** 返回当前记录中已锁定（二次确认后禁用）的字段 id 列表。 */
        List<String> getLockedFieldIds();
    }

    public void setLockedFieldIdsProvider(LockedFieldIdsProvider provider) {
        lockedFieldIdsProvider = provider;
    }

    public interface OnFieldLockedListener {
        /** 字段被“二次确认后禁用”时回调（用于持久化锁定状态）。 */
        void onFieldLocked(String fieldId);
    }

    public void setOnFieldLockedListener(OnFieldLockedListener listener) {
        onFieldLockedListener = listener;
    }

    private void dispatchFieldLocked(String fieldId) {
        if (onFieldLockedListener != null) {
            onFieldLockedListener.onFieldLocked(fieldId);
        }
    }

    public interface HighlightedFieldIdsProvider {
        /** 返回需要高亮的字段 id 列表；无高亮时返回 null。 */
        List<String> getHighlightedFieldIds();
    }

    public void setHighlightedFieldIdsProvider(HighlightedFieldIdsProvider provider) {
        highlightedFieldIdsProvider = provider;
    }

    public interface FieldUpdateTimesProvider {
        /** 返回当前记录的字段更新时间（key 为字段 id，列表字段为与行对应的 List<Map>）。 */
        Map<String, Object> getFieldUpdateTimes();
    }

    public void setFieldUpdateTimesProvider(FieldUpdateTimesProvider provider) {
        fieldUpdateTimesProvider = provider;
    }

    private void dispatchFieldChanged() {
        if (onFieldChangedListener != null) {
            onFieldChangedListener.onFieldChanged();
        }
    }

    public void setProgressContext(OfflineFormDefinition definition, Map<String, String> formValues) {
        this.definition = definition;
        this.formValues = formValues == null ? new HashMap<>() : new HashMap<>(formValues);
    }

    public void setOnDocumentClickListener(OnDocumentClickListener listener) {
        documentClickListener = listener;
    }

    public void setDocumentFileName(String fileName) {
        documentFileNames.clear();
        if (fileName != null && !fileName.isEmpty()) {
            documentFileNames.add(fileName);
        }
    }

    public void setDocumentFileNames(List<String> fileNames) {
        documentFileNames = fileNames == null ? new ArrayList<>() : new ArrayList<>(fileNames);
    }

    public void setDisplayItems(List<OfflineFormDisplayItem> items) {
        displayItems = new ArrayList<>(items);
        // 不再清空折叠状态：用户折叠结果跨重渲染保留；仅首次为每个分组应用一次“默认折叠”。
        initializeCollapseState();
        rebuildRootItems();
        notifyDataSetChanged();
    }

    public List<BaseFormItem> getFormItems() {
        List<BaseFormItem> result = new ArrayList<>();
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField()) {
                result.add(displayItem.getFormItem());
            }
        }
        return result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ROOT_GROUP) {
            return new RootGroupViewHolder(createRootGroupContainer(parent));
        }
        if (viewType == OfflineFormDisplayItem.VIEW_TYPE_TEXT) {
            return new StaticTextViewHolder(createTextContainer(parent));
        }

        OfflineFormItemHandler handler = handlersByViewType.get(viewType);
        if (handler == null) {
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
        return handler.createEditViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OfflineFormDisplayItem item = rootItems.get(position);
        if (holder instanceof RootGroupViewHolder) {
            ((RootGroupViewHolder) holder).bind(item, collectGroupItems(position));
            return;
        }
        if (holder instanceof StaticTextViewHolder) {
            ((StaticTextViewHolder) holder).bind(item);
            return;
        }
        if (holder instanceof BaseViewHolder) {
            bindFieldViewHolder((BaseViewHolder) holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return rootItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        OfflineFormDisplayItem item = rootItems.get(position);
        if (item.isGroup()) {
            return VIEW_TYPE_ROOT_GROUP;
        }
        if (item.isText()) {
            return OfflineFormDisplayItem.VIEW_TYPE_TEXT;
        }
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(item.getFormItem().getItemType());
        int viewType = handler.getViewType();
        handlersByViewType.put(viewType, handler);
        return viewType;
    }

    /** 展开所有折叠的 group，便于展示校验错误。 */
    public void expandAllGroups() {
        collapsedGroupNodes.clear();
        notifyDataSetChanged();
    }

    /** 折叠所有分组（含嵌套），便于快速浏览整体结构。 */
    public void collapseAllGroups() {
        for (OfflineFormDisplayItem item : displayItems) {
            if (item.isGroup() && item.getNode() != null) {
                collapsedGroupNodes.add(item.getNode());
            }
        }
        notifyDataSetChanged();
    }

    /** 返回指定节点对应的顶级项（depth=0）在 rootItems 中的位置；未找到返回 -1。 */
    public int getRootPositionForNode(OfflineFormNode node) {
        if (node == null) {
            return -1;
        }
        for (int i = 0; i < rootItems.size(); i++) {
            if (rootItems.get(i).getNode() == node) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 为每个首次出现的分组应用一次“默认折叠”配置（并按需级联后代）；
     * 之后不再重置，以保留用户手动折叠/展开的结果。
     */
    private void initializeCollapseState() {
        for (int i = 0; i < displayItems.size(); i++) {
            OfflineFormDisplayItem item = displayItems.get(i);
            if (!item.isGroup() || item.getNode() == null) {
                continue;
            }
            OfflineFormNode node = item.getNode();
            if (defaultCollapseInitialized.contains(node)) {
                continue;
            }
            defaultCollapseInitialized.add(node);
            if (node.isDefaultCollapsed()) {
                collapseGroupWithDescendants(i);
            }
        }
    }

    /** 折叠指定分组，并把其后代分组也一并标记为折叠（父折叠时子也折叠）。 */
    private void collapseGroupWithDescendants(int groupIndex) {
        OfflineFormDisplayItem group = displayItems.get(groupIndex);
        if (group == null || !group.isGroup() || group.getNode() == null) {
            return;
        }
        collapsedGroupNodes.add(group.getNode());
        int baseDepth = group.getDepth();
        for (int i = groupIndex + 1; i < displayItems.size(); i++) {
            OfflineFormDisplayItem item = displayItems.get(i);
            if (item.getDepth() <= baseDepth) {
                break;
            }
            if (item.isGroup() && item.getNode() != null) {
                collapsedGroupNodes.add(item.getNode());
            }
        }
    }

    private void rebuildRootItems() {
        rootItems = new ArrayList<>();
        for (OfflineFormDisplayItem item : displayItems) {
            if (item.getDepth() == 0) {
                rootItems.add(item);
            }
        }
    }

    private List<OfflineFormDisplayItem> collectGroupItems(int rootPosition) {
        OfflineFormDisplayItem rootItem = rootItems.get(rootPosition);
        if (!rootItem.isGroup()) {
            return new ArrayList<>();
        }

        int rootIndex = displayItems.indexOf(rootItem);
        List<OfflineFormDisplayItem> groupItems = new ArrayList<>();
        for (int i = rootIndex + 1; i < displayItems.size(); i++) {
            OfflineFormDisplayItem item = displayItems.get(i);
            if (item.getDepth() == 0) {
                break;
            }
            groupItems.add(item);
        }
        return groupItems;
    }

    private void toggleGroup(OfflineFormDisplayItem groupItem, TriangleToggleView toggleView, LinearLayout childrenLayout, List<OfflineFormDisplayItem> childItems) {
        OfflineFormNode node = groupItem.getNode();
        if (node == null) {
            return;
        }
        if (collapsedGroupNodes.contains(node)) {
            // 展开：仅展开当前分组，其后代保持各自的折叠状态。
            collapsedGroupNodes.remove(node);
        } else {
            // 折叠：把当前分组及其后代一并标记为折叠。
            collapseGroupWithDescendants(displayItems.indexOf(groupItem));
        }
        toggleView.setCollapsed(collapsedGroupNodes.contains(node));
        renderChildrenContent(childrenLayout, childItems);
    }

    private View createRootGroupContainer(ViewGroup parent) {
        LinearLayout outer = new LinearLayout(parent.getContext());
        outer.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.setPadding(dp(parent.getContext(), 8), dp(parent.getContext(), 6), dp(parent.getContext(), 8), dp(parent.getContext(), 6));

        CardView cardView = new CardView(parent.getContext());
        cardView.setCardBackgroundColor(parent.getContext().getColor(R.color.white));
        cardView.setRadius(dp(parent.getContext(), 8));
        cardView.setCardElevation(dp(parent.getContext(), 1));
        cardView.setUseCompatPadding(true);

        LinearLayout content = new LinearLayout(parent.getContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(0, 0, 0, dp(parent.getContext(), 6));
        content.setLayoutTransition(createLayoutTransition());
        cardView.addView(content, new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        outer.addView(cardView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return outer;
    }

    private View createTextContainer(ViewGroup parent) {
        LinearLayout container = new LinearLayout(parent.getContext());
        container.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(dp(parent.getContext(), 16), dp(parent.getContext(), 8), dp(parent.getContext(), 16), dp(parent.getContext(), 8));
        return container;
    }

    private View createGroupHeader(ViewGroup parent) {
        LinearLayout container = new LinearLayout(parent.getContext());
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);
        return container;
    }

    private View createStaticTextView(ViewGroup parent) {
        LinearLayout container = new LinearLayout(parent.getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        return container;
    }

    private void bindGroupHeader(View itemView, OfflineFormDisplayItem item) {
        LinearLayout container = (LinearLayout) itemView;
        container.removeAllViews();
        int startPadding = dp(itemView.getContext(), 14 + Math.max(0, item.getDepth() - 1) * 16);
        container.setPadding(startPadding, dp(itemView.getContext(), item.getDepth() == 0 ? 12 : 8), dp(itemView.getContext(), 14), dp(itemView.getContext(), item.getDepth() == 0 ? 10 : 6));
        container.setBackground(item.getDepth() == 0 ? createRoundedBackground(itemView, R.color.white, 6) : null);

        View accentView = new View(itemView.getContext());
        GradientDrawable accentBackground = new GradientDrawable();
        accentBackground.setColor(itemView.getContext().getColor(R.color.huozige_blue));
        accentBackground.setCornerRadius(dp(itemView.getContext(), 2));
        accentView.setBackground(accentBackground);
        LinearLayout.LayoutParams accentParams = new LinearLayout.LayoutParams(dp(itemView.getContext(), 4), dp(itemView.getContext(), 18));
        accentParams.setMargins(0, 0, dp(itemView.getContext(), 8), 0);
        container.addView(accentView, accentParams);

        TextView titleView = new TextView(itemView.getContext());
        titleView.setTextColor(itemView.getContext().getColor(R.color.huozige_blue));
        titleView.setTextSize(15);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setText(item.getNode().getTitle());
        container.addView(titleView, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        TriangleToggleView toggleView = new TriangleToggleView(itemView.getContext());
        toggleView.setCollapsed(collapsedGroupNodes.contains(item.getNode()));
        container.addView(toggleView, new LinearLayout.LayoutParams(
                dp(itemView.getContext(), 28),
                dp(itemView.getContext(), 28)));
    }

    private TriangleToggleView bindGroupHeader(View itemView, OfflineFormDisplayItem item, View.OnClickListener clickListener) {
        bindGroupHeader(itemView, item);
        TriangleToggleView toggleView = (TriangleToggleView) ((LinearLayout) itemView).getChildAt(2);
        itemView.setOnClickListener(clickListener);
        return toggleView;
    }

    private void bindStaticText(View itemView, OfflineFormDisplayItem item) {
        LinearLayout container = (LinearLayout) itemView;
        container.removeAllViews();
        int startPadding = dp(itemView.getContext(), 14 + Math.max(0, item.getDepth() - 1) * 16);
        container.setPadding(startPadding, dp(itemView.getContext(), 8), dp(itemView.getContext(), 14), dp(itemView.getContext(), 10));
        container.setBackground(createRoundedBackground(itemView, R.color.offline_form_text_bg, 6));

        TextView titleView = new TextView(itemView.getContext());
        titleView.setTextColor(itemView.getContext().getColor(R.color.offline_form_text_title));
        titleView.setTextSize(13);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setText(item.getNode().getTitle());

        container.addView(titleView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        OfflineFormNode node = item.getNode();
        String textMode = node.getTextMode();
        if (textMode == null || textMode.isEmpty() || OfflineFormNode.TEXT_MODE_TEXT.equals(textMode)) {
            addTextContent(container, node.getContent());
        } else if (OfflineFormNode.TEXT_MODE_PROGRESS.equals(textMode)) {
            addProgressContent(container, itemView.getContext(), node.getProgressDisplay());
        } else if (OfflineFormNode.TEXT_MODE_DOCUMENT.equals(textMode)) {
            addDocumentContent(container, item);
        } else {
            // 未知模式按普通文本处理，保证旧版本或新增模式不会让节点内容消失。
            addTextContent(container, node.getContent());
        }
    }

    private void addTextContent(LinearLayout container, String content) {
        TextView contentView = createTextValueView(container.getContext());
        contentView.setText(content == null ? "" : content);
        container.addView(contentView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addProgressContent(LinearLayout container, Context context, OfflineFormProgressDisplay display) {
        OfflineFormProgress progress = OfflineFormProgressCalculator.calculate(definition, formValues);
        OfflineFormProgressDisplay safeDisplay = display == null ? new OfflineFormProgressDisplay() : display;
        int metricCount = (safeDisplay.isTotal() ? 1 : 0)
                + (safeDisplay.isCompleted() ? 1 : 0)
                + (safeDisplay.isCompletionRate() ? 1 : 0);
        if (metricCount == 0) {
            return;
        }

        LinearLayout metricsLayout = new LinearLayout(context);
        metricsLayout.setOrientation(LinearLayout.HORIZONTAL);
        metricsLayout.setGravity(Gravity.CENTER_VERTICAL);
        metricsLayout.setPadding(dp(context, 6), dp(context, 10), dp(context, 6), dp(context, 10));
        metricsLayout.setBackground(createRoundedBackground(context, R.color.offline_form_progress_bg, 8));

        int metricIndex = 0;
        if (safeDisplay.isTotal()) {
            metricIndex = addProgressMetric(metricsLayout, context,
                    context.getString(R.string.offline_progress_label_total),
                    String.valueOf(progress.getTotalFillItems()), false, metricIndex);
        }
        if (safeDisplay.isCompleted()) {
            metricIndex = addProgressMetric(metricsLayout, context,
                    context.getString(R.string.offline_progress_label_completed),
                    String.valueOf(progress.getFilledFillItems()), false, metricIndex);
        }
        if (safeDisplay.isCompletionRate()) {
            addProgressMetric(metricsLayout, context,
                    context.getString(R.string.offline_progress_label_completion_rate),
                    String.format(java.util.Locale.CHINA, "%.0f%%", progress.getCompletionRate()), true, metricIndex);
        }
        LinearLayout.LayoutParams metricsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        metricsParams.topMargin = dp(context, 8);
        container.addView(metricsLayout, metricsParams);
    }

    private void addDocumentContent(LinearLayout container, OfflineFormDisplayItem item) {
        List<String> fileNames = getDocumentFileNames(item);
        Context context = container.getContext();
        LinearLayout documentList = new LinearLayout(context);
        documentList.setOrientation(LinearLayout.VERTICAL);
        documentList.setPadding(dp(context, 8), dp(context, 4), dp(context, 8), dp(context, 4));
        documentList.setBackground(createRoundedBackground(context, R.color.offline_form_document_list_bg, 8));

        if (fileNames.isEmpty()) {
            TextView emptyView = new TextView(context);
            emptyView.setText(R.string.offline_document_empty_hint);
            emptyView.setTextColor(context.getColor(R.color.offline_form_document_meta));
            emptyView.setTextSize(14);
            emptyView.setGravity(Gravity.CENTER);
            emptyView.setPadding(dp(context, 10), dp(context, 14), dp(context, 10), dp(context, 14));
            documentList.addView(emptyView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else for (String fileName : fileNames) {
            LinearLayout documentRow = new LinearLayout(context);
            documentRow.setOrientation(LinearLayout.HORIZONTAL);
            documentRow.setGravity(Gravity.CENTER_VERTICAL);
            documentRow.setPadding(dp(context, 10), dp(context, 8), dp(context, 8), dp(context, 8));
            documentRow.setBackgroundResource(R.drawable.offline_document_item_bg);
            documentRow.setClickable(true);
            documentRow.setFocusable(true);
            documentRow.setContentDescription(fileName);

            FileExtensionIconView fileIcon = new FileExtensionIconView(context);
            fileIcon.setExtension(Utils.getExtension(fileName));
            documentRow.addView(fileIcon, new LinearLayout.LayoutParams(
                    dp(context, 36), dp(context, 36)));

            LinearLayout textLayout = new LinearLayout(context);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textParams.setMargins(dp(context, 10), 0, dp(context, 8), 0);

            TextView nameView = new TextView(context);
            nameView.setText(fileName);
            nameView.setTextColor(context.getColor(R.color.offline_form_document_name));
            nameView.setTextSize(14);
            nameView.setTypeface(null, Typeface.BOLD);
            nameView.setSingleLine(true);
            nameView.setEllipsize(android.text.TextUtils.TruncateAt.MIDDLE);

            TextView actionView = new TextView(context);
            actionView.setText(R.string.offline_document_preview_hint);
            actionView.setTextColor(context.getColor(R.color.offline_form_document_meta));
            actionView.setTextSize(12);
            actionView.setPadding(0, dp(context, 2), 0, 0);

            textLayout.addView(nameView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textLayout.addView(actionView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            documentRow.addView(textLayout, textParams);

            ImageView arrowView = new ImageView(context);
            arrowView.setImageResource(R.drawable.ic_offline_step_arrow_right);
            arrowView.setAlpha(0.65f);
            arrowView.setContentDescription(context.getString(R.string.offline_document_preview_hint));
            documentRow.addView(arrowView, new LinearLayout.LayoutParams(
                    dp(context, 22), dp(context, 22)));

            documentRow.setOnClickListener(v -> {
                if (documentClickListener != null) {
                    documentClickListener.onDocumentClick(item, fileName);
                }
            });

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(context, 58));
            rowParams.topMargin = dp(context, 4);
            rowParams.bottomMargin = dp(context, 4);
            documentList.addView(documentRow, rowParams);
        }

        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listParams.topMargin = dp(context, 8);
        container.addView(documentList, listParams);
    }

    private List<String> getDocumentFileNames(OfflineFormDisplayItem item) {
        List<String> fileNames = new ArrayList<>();
        String content = item.getNode() == null ? "" : item.getNode().getContent();
        if (content != null && !content.trim().isEmpty()) {
            for (String fileName : content.split("\\|", -1)) {
                if (fileName != null && !fileName.trim().isEmpty()) {
                    fileNames.add(fileName.trim());
                }
            }
        }
        if (fileNames.isEmpty()) {
            fileNames.addAll(documentFileNames);
        }
        return fileNames;
    }

    private int addProgressMetric(
            LinearLayout parent,
            Context context,
            String label,
            String value,
            boolean highlight,
            int metricIndex) {
        if (metricIndex > 0) {
            View divider = new View(context);
            divider.setBackgroundColor(context.getColor(R.color.offline_form_progress_divider));
            parent.addView(divider, new LinearLayout.LayoutParams(
                    dp(context, 1), dp(context, 34)));
        }

        LinearLayout metricLayout = new LinearLayout(context);
        metricLayout.setOrientation(LinearLayout.VERTICAL);
        metricLayout.setGravity(Gravity.CENTER);

        TextView labelView = new TextView(context);
        labelView.setText(label);
        labelView.setTextColor(context.getColor(R.color.offline_form_progress_label));
        labelView.setTextSize(12);
        labelView.setGravity(Gravity.CENTER);

        TextView valueView = new TextView(context);
        valueView.setText(value);
        valueView.setTextColor(context.getColor(highlight
                ? R.color.offline_form_progress_value_highlight
                : R.color.offline_form_progress_value));
        valueView.setTextSize(highlight ? 20 : 18);
        valueView.setTypeface(null, Typeface.BOLD);
        valueView.setGravity(Gravity.CENTER);
        metricLayout.addView(labelView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        metricLayout.addView(valueView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams metricParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        metricParams.leftMargin = dp(context, 4);
        metricParams.rightMargin = dp(context, 4);
        parent.addView(metricLayout, metricParams);
        return metricIndex + 1;
    }

    private TextView createTextValueView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(context.getColor(R.color.offline_form_text_content));
        textView.setTextSize(14);
        textView.setPadding(0, dp(context, 4), 0, 0);
        return textView;
    }

    private static GradientDrawable createRoundedBackground(Context context, int colorResId, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(context.getColor(colorResId));
        drawable.setCornerRadius(dp(context, radius));
        return drawable;
    }

    private BaseViewHolder createFieldViewHolder(ViewGroup parent, OfflineFormDisplayItem item) {
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(item.getFormItem().getItemType());
        return handler.createEditViewHolder(parent);
    }

    private void bindFieldViewHolder(BaseViewHolder holder, OfflineFormDisplayItem item, int position) {
        int startPadding = dp(holder.itemView.getContext(), 16 + Math.max(0, item.getDepth() - 1) * 16);
        holder.itemView.setPadding(startPadding, dp(holder.itemView.getContext(), 8), dp(holder.itemView.getContext(), 16), dp(holder.itemView.getContext(), 8));
        BaseFormItem formItem = item.getFormItem();
        String originalTitle = formItem.getTitle();
        String displayTitle = item.getFieldDisplayTitle();
        if ((originalTitle == null || originalTitle.isEmpty()) && displayTitle != null && !displayTitle.isEmpty()) {
            formItem.setTitle(displayTitle);
        }
        holder.setOnFieldChanged(this::dispatchFieldChanged);
        holder.setOnFieldLocked(() -> dispatchFieldLocked(formItem.getId()));
        holder.setShowFieldUpdateTime(formItem.isShowFieldUpdateTime());
        if (holder instanceof ListViewHolder) {
            // 列表字段没有单一更新时间：把各行的时间下发给 ListViewHolder，由其渲染到对应子字段。
            ((ListViewHolder) holder).setRowUpdateTimes(resolveListRowTimes(formItem.getId()));
            holder.bind(formItem, position);
        } else {
            holder.bind(formItem, position);
            OfflineFormFieldTimeViews.apply(holder.itemView,
                    formItem.isShowFieldUpdateTime() ? resolveFieldTime(formItem.getId()) : null);
        }
        formItem.setTitle(originalTitle);

        List<String> lockedFieldIds = lockedFieldIdsProvider == null ? null : lockedFieldIdsProvider.getLockedFieldIds();
        holder.setLocked(lockedFieldIds != null && lockedFieldIds.contains(formItem.getId()));

        List<String> highlightedFieldIds = highlightedFieldIdsProvider == null ? null : highlightedFieldIdsProvider.getHighlightedFieldIds();
        holder.setHighlighted(highlightedFieldIds != null && highlightedFieldIds.contains(formItem.getId()));
    }

    private Map<String, Object> currentFieldUpdateTimes() {
        return fieldUpdateTimesProvider == null ? null : fieldUpdateTimesProvider.getFieldUpdateTimes();
    }

    private Object resolveFieldTime(String fieldId) {
        Map<String, Object> times = currentFieldUpdateTimes();
        return times == null ? null : times.get(fieldId);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> resolveListRowTimes(String fieldId) {
        Object value = resolveFieldTime(fieldId);
        if (!(value instanceof List)) {
            return null;
        }
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Object row : (List<Object>) value) {
            rows.add(row instanceof Map ? (Map<String, Object>) row : null);
        }
        return rows;
    }

    private static GradientDrawable createRoundedBackground(View view, int colorResId, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(view.getContext().getColor(colorResId));
        drawable.setCornerRadius(dp(view.getContext(), radius));
        return drawable;
    }

    private static LayoutTransition createLayoutTransition() {
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(140);
        return transition;
    }

    private static class TriangleToggleView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private boolean collapsed;

        TriangleToggleView(Context context) {
            super(context);
            paint.setColor(context.getColor(R.color.huozige_blue));
            paint.setStyle(Paint.Style.FILL);
        }

        void setCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float width = getWidth();
            float height = getHeight();
            float centerX = width / 2f;
            float centerY = height / 2f;
            float size = Math.min(width, height) * 0.28f;

            Path path = new Path();
            if (collapsed) {
                path.moveTo(centerX - size, centerY - size / 2f);
                path.lineTo(centerX + size, centerY - size / 2f);
                path.lineTo(centerX, centerY + size / 2f);
            } else {
                path.moveTo(centerX - size, centerY + size / 2f);
                path.lineTo(centerX + size, centerY + size / 2f);
                path.lineTo(centerX, centerY - size / 2f);
            }
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    private class RootGroupViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout contentLayout;

        RootGroupViewHolder(View itemView) {
            super(itemView);
            CardView cardView = (CardView) ((LinearLayout) itemView).getChildAt(0);
            contentLayout = (LinearLayout) cardView.getChildAt(0);
        }

        void bind(OfflineFormDisplayItem rootItem, List<OfflineFormDisplayItem> groupItems) {
            contentLayout.removeAllViews();
            addGroupBlock(contentLayout, rootItem, groupItems);
        }
    }

    private void addGroupBlock(LinearLayout parentLayout, OfflineFormDisplayItem groupItem, List<OfflineFormDisplayItem> childItems) {
        LinearLayout groupBlock = new LinearLayout(parentLayout.getContext());
        groupBlock.setOrientation(LinearLayout.VERTICAL);

        View header = createGroupHeader(groupBlock);
        LinearLayout childrenLayout = new LinearLayout(parentLayout.getContext());
        childrenLayout.setOrientation(LinearLayout.VERTICAL);
        childrenLayout.setLayoutTransition(createLayoutTransition());

        TriangleToggleView toggleView = bindGroupHeader(header, groupItem, v -> toggleGroup(groupItem, (TriangleToggleView) ((LinearLayout) header).getChildAt(2), childrenLayout, childItems));
        toggleView.setCollapsed(collapsedGroupNodes.contains(groupItem.getNode()));
        groupBlock.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        groupBlock.addView(childrenLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        parentLayout.addView(groupBlock, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        renderChildrenContent(childrenLayout, childItems);
    }

    private void renderChildrenContent(LinearLayout childrenLayout, List<OfflineFormDisplayItem> childItems) {
        childrenLayout.removeAllViews();
        if (childItems.isEmpty() || collapsedGroupNodes.contains(findParentNode(childItems))) {
            return;
        }

        for (int i = 0; i < childItems.size(); i++) {
            OfflineFormDisplayItem child = childItems.get(i);
            if (child.isGroup()) {
                List<OfflineFormDisplayItem> nestedChildren = collectDirectChildItems(childItems, i);
                addGroupBlock(childrenLayout, child, nestedChildren);
                i += nestedChildren.size();
            } else if (child.isText()) {
                View staticText = createStaticTextView(childrenLayout);
                bindStaticText(staticText, child);
                childrenLayout.addView(staticText, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            } else if (child.isField()) {
                BaseViewHolder fieldViewHolder = createFieldViewHolder(childrenLayout, child);
                bindFieldViewHolder(fieldViewHolder, child, displayItems.indexOf(child));
                childrenLayout.addView(fieldViewHolder.itemView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private OfflineFormNode findParentNode(List<OfflineFormDisplayItem> childItems) {
        if (childItems.isEmpty()) {
            return null;
        }
        int childIndex = displayItems.indexOf(childItems.get(0));
        int childDepth = childItems.get(0).getDepth();
        for (int i = childIndex - 1; i >= 0; i--) {
            OfflineFormDisplayItem item = displayItems.get(i);
            if (item.isGroup() && item.getDepth() < childDepth) {
                return item.getNode();
            }
        }
        return null;
    }

    private List<OfflineFormDisplayItem> collectDirectChildItems(List<OfflineFormDisplayItem> items, int groupIndex) {
        List<OfflineFormDisplayItem> result = new ArrayList<>();
        OfflineFormDisplayItem group = items.get(groupIndex);
        for (int i = groupIndex + 1; i < items.size(); i++) {
            OfflineFormDisplayItem item = items.get(i);
            if (item.getDepth() <= group.getDepth()) {
                break;
            }
            result.add(item);
        }
        return result;
    }

    private class StaticTextViewHolder extends RecyclerView.ViewHolder {
        StaticTextViewHolder(View itemView) {
            super(itemView);
        }

        void bind(OfflineFormDisplayItem item) {
            bindStaticText(itemView, item);
        }
    }
}
