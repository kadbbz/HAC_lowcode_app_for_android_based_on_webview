package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private static final int VIEW_TYPE_PAGE = 10004;
    private static final int VIEW_TYPE_SCATTER_CARD = 10005;
    /** 行分隔线高度：分页布局与平铺布局统一为 1dp 发丝线。 */
    private static final int ROW_DIVIDER_HEIGHT_DP = 1;
    /** 平铺布局卡片之间的垂直间距，与分页布局内容卡片的外边距一致。 */
    private static final int CARD_VERTICAL_GAP_DP = 12;

    // 逐组分页布局：每页只有一个表单组。
    private boolean pagedMode;
    private List<OfflineFormDisplayItem> displayItems = new ArrayList<>();
    private List<OfflineFormDisplayItem> rootItems = new ArrayList<>();
    // 平铺布局的卡片分段：根分组各自一张卡片，连续的非分组根级节点合并为一张散项卡片。
    private final List<FlatCard> flatCards = new ArrayList<>();

    /**
     * 平铺布局的一张卡片：根分组卡片，或连续散项合并成的卡片（与分页布局的页面切分规则一致）。
     */
    private static final class FlatCard {
        private final OfflineFormDisplayItem groupItem;
        private final int groupRootPosition;
        private final List<OfflineFormDisplayItem> scatterItems;

        private FlatCard(OfflineFormDisplayItem groupItem, int groupRootPosition,
                         List<OfflineFormDisplayItem> scatterItems) {
            this.groupItem = groupItem;
            this.groupRootPosition = groupRootPosition;
            this.scatterItems = scatterItems;
        }

        static FlatCard group(OfflineFormDisplayItem groupItem, int groupRootPosition) {
            return new FlatCard(groupItem, groupRootPosition, null);
        }

        static FlatCard scatter(List<OfflineFormDisplayItem> scatterItems) {
            return new FlatCard(null, -1, scatterItems);
        }

        boolean isGroupCard() {
            return groupItem != null;
        }
    }
    // 折叠状态按分组节点（对象身份）保存，跨重渲染保留；使用 identity set 以规避 OfflineFormNode 的值相等语义。
    private final Set<OfflineFormNode> collapsedGroupNodes =
            Collections.newSetFromMap(new IdentityHashMap<OfflineFormNode, Boolean>());
    // 已按其“默认折叠”配置初始化过的分组节点，保证默认值只应用一次、之后尊重用户操作。
    private final Set<OfflineFormNode> defaultCollapseInitialized =
            Collections.newSetFromMap(new IdentityHashMap<OfflineFormNode, Boolean>());
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

    /** 切换逐组分页渲染：每页只有一个表单组，内容直接平铺在页面上（无卡片）。 */
    public void setPagedMode(boolean paged) {
        this.pagedMode = paged;
    }

    public void setDisplayItems(List<OfflineFormDisplayItem> items) {
        displayItems = new ArrayList<>(items);
        // 不再清空折叠状态：用户折叠结果跨重渲染保留；仅首次为每个分组应用一次“默认折叠”。
        initializeCollapseState();
        rebuildRootItems();
        rebuildFlatCards();
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
        if (viewType == VIEW_TYPE_PAGE) {
            return new PageViewHolder(createPageContainer(parent));
        }
        if (viewType == VIEW_TYPE_ROOT_GROUP || viewType == VIEW_TYPE_SCATTER_CARD) {
            return new FlatCardViewHolder(createFlatCardContainer(parent));
        }
        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (pagedMode) {
            ((PageViewHolder) holder).bind(displayItems);
            return;
        }
        FlatCard card = flatCards.get(position);
        applyFlatCardSpacing(holder.itemView, position);
        FlatCardViewHolder cardHolder = (FlatCardViewHolder) holder;
        if (card.isGroupCard()) {
            cardHolder.bindGroupCard(card.groupItem, collectGroupItems(card.groupRootPosition));
        } else {
            cardHolder.bindScatterCard(card.scatterItems);
        }
    }

    @Override
    public int getItemCount() {
        if (pagedMode) {
            return displayItems.isEmpty() ? 0 : 1;
        }
        return flatCards.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (pagedMode) {
            return VIEW_TYPE_PAGE;
        }
        return flatCards.get(position).isGroupCard() ? VIEW_TYPE_ROOT_GROUP : VIEW_TYPE_SCATTER_CARD;
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

    /** 返回指定根级节点所在的平铺卡片下标；未找到返回 -1。 */
    public int getFlatCardPositionForNode(OfflineFormNode node) {
        if (node == null) {
            return -1;
        }
        for (int i = 0; i < flatCards.size(); i++) {
            FlatCard card = flatCards.get(i);
            if (card.isGroupCard()) {
                if (card.groupItem.getNode() == node) {
                    return i;
                }
                continue;
            }
            for (OfflineFormDisplayItem item : card.scatterItems) {
                if (item.getNode() == node) {
                    return i;
                }
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

    /**
     * 平铺布局的卡片分段：根分组各自一张卡片；连续的非分组根级节点（散项文本 / 裸字段）
     * 合并为一张散项卡片，与分页布局的页面切分规则一致。
     */
    private void rebuildFlatCards() {
        flatCards.clear();
        List<OfflineFormDisplayItem> scatterItems = new ArrayList<>();
        for (int i = 0; i < rootItems.size(); i++) {
            OfflineFormDisplayItem item = rootItems.get(i);
            if (item.isGroup()) {
                flushScatterCard(scatterItems);
                flatCards.add(FlatCard.group(item, i));
            } else {
                scatterItems.add(item);
            }
        }
        flushScatterCard(scatterItems);
    }

    private void flushScatterCard(List<OfflineFormDisplayItem> scatterItems) {
        if (scatterItems.isEmpty()) {
            return;
        }
        flatCards.add(FlatCard.scatter(new ArrayList<>(scatterItems)));
        scatterItems.clear();
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

    private void toggleGroup(OfflineFormDisplayItem groupItem, View header, LinearLayout childrenLayout,
                             List<OfflineFormDisplayItem> childItems, boolean topLevelGroup) {
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
        boolean collapsed = collapsedGroupNodes.contains(node);
        setGroupHeaderCollapsed(header, collapsed);
        renderChildrenContent(childrenLayout, childItems);
        if (!topLevelGroup) {
            styleNestedContent(childrenLayout, collapsed);
        } else {
            // 组头下分隔线随展开状态显隐：折叠 / 空分组时隐藏，避免与外层根级分隔线叠成双线。
            setGroupHeaderDividerVisible(header, !collapsed && !childItems.isEmpty());
        }
    }

    /**
     * 分页模式下的页面容器：整页就是当前表单组的全部内容。
     *
     * <p>一页只有一个表单组，容器本身就是页面，不再套白色圆角卡片；内容按表单项顺序直接排列，
     * 表单项之间的间距由表单项自身的上下内边距控制，项与项之间统一画 1dp 通栏发丝线。</p>
     */
    private View createPageContainer(ViewGroup parent) {
        Context context = parent.getContext();
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // 页面底色：与页面顶部 Tab / 指示器 / 筛选行一致，形成整页白色表单。
        container.setBackgroundColor(context.getColor(R.color.white));
        return container;
    }

    /**
     * 平铺布局的卡片容器：白色 + 12dp 圆角，与分页布局的内容卡片同款。
     * 卡片内容（分组头 + 字段行 / 散项行）由 {@link FlatCardViewHolder} 绑定。
     */
    private View createFlatCardContainer(ViewGroup parent) {
        Context context = parent.getContext();
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        card.setBackgroundResource(R.drawable.offline_form_content_bg);
        card.setClipToOutline(true);
        return card;
    }

    /** 平铺布局卡片间距：首张卡片由外层内容区 12dp 外边距提供上间距，其余卡片之间留 12dp。 */
    private void applyFlatCardSpacing(View cardView, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) cardView.getLayoutParams();
        int topMargin = position == 0 ? 0 : dp(cardView.getContext(), CARD_VERTICAL_GAP_DP);
        if (params.topMargin != topMargin) {
            params.topMargin = topMargin;
            cardView.setLayoutParams(params);
        }
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

    /**
     * 分组折叠行左侧的品牌色竖条：根分组用实心品牌色，嵌套子分组用淡化版，
     * 两者行高/字号之外的「标识」差异用于区分层级。
     */
    private void addGroupAccentBar(LinearLayout container, boolean topLevelGroup) {
        Context context = container.getContext();
        View accentView = new View(context);
        accentView.setBackgroundResource(topLevelGroup
                ? R.drawable.offline_form_group_accent
                : R.drawable.offline_form_group_accent_nested);
        accentView.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        LinearLayout.LayoutParams accentParams = new LinearLayout.LayoutParams(
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_accent_width),
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_accent_height));
        accentParams.setMarginEnd(context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_group_accent_gap));
        container.addView(accentView, accentParams);
    }

    /** 分组折叠行右侧的折叠箭头（平铺顶层分组与嵌套分组共用）。 */
    private void addGroupToggleChevron(LinearLayout container) {
        Context context = container.getContext();
        ImageView chevron = new ImageView(context);
        chevron.setImageResource(R.drawable.ic_offline_chevron_right_24);
        chevron.setColorFilter(context.getColor(R.color.offline_form_muted));
        LinearLayout.LayoutParams chevronParams = new LinearLayout.LayoutParams(
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_toggle_size),
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_toggle_size));
        chevronParams.setMarginStart(context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_group_toggle_gap));
        container.addView(chevron, chevronParams);
    }

    private void bindGroupHeader(View itemView, OfflineFormDisplayItem item, boolean topLevelGroup) {
        itemView.setTag(R.id.offline_node_tag, item.getNode());
        Context context = itemView.getContext();
        LinearLayout container = (LinearLayout) itemView;
        container.removeAllViews();
        container.setBackground(null);

        if (!topLevelGroup) {
            bindNestedGroupHeader(container, item);
            return;
        }

        // 平铺布局的顶层分组折叠行与嵌套分组同一套手风琴样式：行高 56dp、左侧根分组实心品牌色竖条、右侧折叠箭头；
        // 标题保持 18sp 加粗单行，下方由独立发丝线与组内字段分隔。
        container.setMinimumHeight(context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_group_header_min_height));
        container.setPadding(
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_header_padding_horizontal),
                0,
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_header_padding_end),
                0);
        addGroupAccentBar(container, true);

        TextView titleView = new TextView(context);
        titleView.setTextColor(context.getColor(R.color.offline_form_ink));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_page_title_text_size));
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setSingleLine(true);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setText(item.getNode().getTitle());
        container.addView(titleView, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));

        addGroupToggleChevron(container);
    }

    /**
     * 嵌套子分组的手风琴式表头：整行「淡化主题色竖条 + 标题 + 右侧细箭头」；
     * 与顶层分组折叠行共用行高与箭头规格，用「淡化标识 + 常规字重」与根分组区分层级。
     */
    private void bindNestedGroupHeader(LinearLayout container, OfflineFormDisplayItem item) {
        Context context = container.getContext();
        container.setGravity(Gravity.CENTER_VERTICAL);
        container.setMinimumHeight(context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_group_header_min_height));
        container.setPadding(
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_header_padding_horizontal),
                0,
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_group_header_padding_end),
                0);
        addGroupAccentBar(container, false);

        TextView titleView = new TextView(context);
        titleView.setText(item.getNode().getTitle());
        titleView.setTextColor(context.getColor(R.color.offline_form_ink));
        // 与其它表单项的正文同字号（字段值 17sp），仅用常规字重 + 主题色竖条标识分组。
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDimensionPixelSize(R.dimen.offline_form_field_value_text_size));
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        container.addView(titleView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        addGroupToggleChevron(container);
    }

    /** 表头折叠态：顶层分组与嵌套分组共用右侧箭头，折叠 = 向下、展开 = 向上。 */
    private void setGroupHeaderCollapsed(View header, boolean collapsed) {
        if (!(header instanceof LinearLayout)) {
            return;
        }
        // 表头结构：[... , 右向箭头]，箭头始终是最后一个子 View。
        LinearLayout container = (LinearLayout) header;
        View child = container.getChildCount() == 0
                ? null
                : container.getChildAt(container.getChildCount() - 1);
        if (child != null) {
            child.setRotation(collapsed ? 90f : -90f);
        }
    }

    /** 顶层分组表头下方通栏发丝线的显隐（与分页布局 Group Header 下的 divider 同款）。 */
    private void setGroupHeaderDividerVisible(View header, boolean visible) {
        Object tag = header.getTag(R.id.offline_group_header_divider_tag);
        if (tag instanceof View) {
            ((View) tag).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void styleNestedContent(LinearLayout childrenLayout, boolean collapsed) {
        // 嵌套内容背景与父节点一致（父卡片白底）；展开时顶部画一条左右缩进 16dp 的分隔线
        // （即「子项标题下方」的分隔线），折叠时整块收掉。
        if (collapsed) {
            childrenLayout.setBackground(null);
            childrenLayout.setPadding(0, 0, 0, 0);
            return;
        }
        childrenLayout.setBackgroundResource(R.drawable.offline_form_nested_top_divider_bg);
        childrenLayout.setPadding(dp(childrenLayout.getContext(), 16), dp(childrenLayout.getContext(), 5),
                dp(childrenLayout.getContext(), 16), dp(childrenLayout.getContext(), 10));
    }

    private View bindGroupHeader(View itemView, OfflineFormDisplayItem item,
                                 boolean topLevelGroup, View.OnClickListener clickListener) {
        bindGroupHeader(itemView, item, topLevelGroup);
        itemView.setOnClickListener(clickListener);
        return itemView;
    }

    private void bindStaticText(View itemView, OfflineFormDisplayItem item) {
        itemView.setTag(R.id.offline_node_tag, item.getNode());
        Context context = itemView.getContext();
        LinearLayout container = (LinearLayout) itemView;
        container.removeAllViews();
        // 与可编辑表单项共用同一套左右基线与上下内边距。
        container.setPadding(
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_horizontal),
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_vertical),
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_horizontal),
                context.getResources().getDimensionPixelSize(R.dimen.offline_form_item_padding_vertical));
        container.setBackground(null);

        OfflineFormNode node = item.getNode();
        String textMode = node.getTextMode();
        if (OfflineFormNode.TEXT_MODE_PROGRESS.equals(textMode)) {
            addProgressContent(container, context, node.getProgressDisplay());
        } else if (OfflineFormNode.TEXT_MODE_DOCUMENT.equals(textMode)) {
            addDocumentContent(container, item);
        } else {
            // 普通文本（含未知模式）渲染为「只读字段」，保证内容不会消失。
            addReadOnlyRow(container, node);
        }
    }

    /** 普通文本行：只读字段样式——标签在上（灰）、值在下（深），与可编辑字段同构。 */
    private void addReadOnlyRow(LinearLayout container, OfflineFormNode node) {
        Context context = container.getContext();
        String title = node.getTitle();
        String content = node.getContent() == null ? "" : node.getContent();
        boolean hasTitle = title != null && !title.isEmpty();

        if (!hasTitle && content.isEmpty()) {
            return;
        }

        LinearLayout column = new LinearLayout(context);
        column.setOrientation(LinearLayout.VERTICAL);

        if (hasTitle) {
            TextView labelView = new TextView(context);
            labelView.setText(title);
            labelView.setTextColor(context.getColor(R.color.offline_form_sec));
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                    .getDimensionPixelSize(R.dimen.offline_form_field_label_text_size));
            column.addView(labelView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (!content.isEmpty()) {
            TextView valueView = new TextView(context);
            valueView.setText(content);
            valueView.setTextColor(context.getColor(R.color.offline_form_ink));
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                    .getDimensionPixelSize(R.dimen.offline_form_field_value_text_size));
            valueView.setLineSpacing(dp(context, 2), 1f);
            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (hasTitle) {
                valueParams.topMargin = context.getResources()
                        .getDimensionPixelSize(R.dimen.offline_form_item_label_gap);
            }
            column.addView(valueView, valueParams);
        }

        container.addView(column, new LinearLayout.LayoutParams(
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
            nameView.setEllipsize(TextUtils.TruncateAt.MIDDLE);

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
        // 行内边距由字段布局自身统一控制（16dp / 13dp）；嵌套层级由分组引导线表达，不再按 depth 缩进。
        BaseFormItem formItem = item.getFormItem();
        // 打上字段 id 标记：保存校验失败时据此在视图树里定位字段并滚动高亮。
        holder.itemView.setTag(R.id.offline_field_id_tag, formItem.getId());
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

    private class PageViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout contentLayout;

        PageViewHolder(View itemView) {
            super(itemView);
            contentLayout = (LinearLayout) itemView;
        }

        void bind(List<OfflineFormDisplayItem> pageItems) {
            // 分页下整页只有一个表单组：表单项顺序排列，项与项之间统一画通栏发丝线。
            renderChildrenContent(contentLayout, pageItems);
        }
    }

    /** 平铺布局的卡片：根分组卡片（可折叠）或连续散项合并成的卡片。 */
    private class FlatCardViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout contentLayout;

        FlatCardViewHolder(View itemView) {
            super(itemView);
            contentLayout = (LinearLayout) itemView;
        }

        void bindGroupCard(OfflineFormDisplayItem groupItem, List<OfflineFormDisplayItem> groupItems) {
            contentLayout.removeAllViews();
            addGroupBlock(contentLayout, groupItem, groupItems, true);
        }

        void bindScatterCard(List<OfflineFormDisplayItem> scatterItems) {
            contentLayout.removeAllViews();
            renderChildrenContent(contentLayout, scatterItems);
        }
    }

    private void addGroupBlock(LinearLayout parentLayout, OfflineFormDisplayItem groupItem,
                               List<OfflineFormDisplayItem> childItems, boolean topLevelGroup) {
        Context context = parentLayout.getContext();
        boolean collapsed = collapsedGroupNodes.contains(groupItem.getNode());
        LinearLayout groupBlock = new LinearLayout(context);
        groupBlock.setOrientation(LinearLayout.VERTICAL);

        LinearLayout childrenLayout = new LinearLayout(context);
        childrenLayout.setOrientation(LinearLayout.VERTICAL);
        childrenLayout.setLayoutTransition(createLayoutTransition());
        if (!topLevelGroup) {
            styleNestedContent(childrenLayout, collapsed);
        }

        View header = createGroupHeader(groupBlock);
        bindGroupHeader(header, groupItem, topLevelGroup);
        setGroupHeaderCollapsed(header, collapsed);
        header.setOnClickListener(v -> toggleGroup(groupItem, header, childrenLayout, childItems, topLevelGroup));

        groupBlock.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        if (topLevelGroup) {
            // 顶层分组表头下的 1dp 通栏发丝线：与分页布局 Group Header 下的 divider 一致。
            // 独立 View 而不是子内容容器背景：空容器带背景时有 1dp 内在最小高度，折叠后会残留并叠成双线。
            View headerDivider = new View(context);
            headerDivider.setBackgroundResource(R.drawable.offline_form_row_divider);
            headerDivider.setVisibility(collapsed || childItems.isEmpty() ? View.GONE : View.VISIBLE);
            header.setTag(R.id.offline_group_header_divider_tag, headerDivider);
            groupBlock.addView(headerDivider, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(context, ROW_DIVIDER_HEIGHT_DP)));
        }
        groupBlock.addView(childrenLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        parentLayout.addView(groupBlock, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        renderChildrenContent(childrenLayout, childItems);
    }

    /** 行与行之间的发丝分隔线：分页布局与平铺布局统一为 1dp 通栏（同名同色同高）。 */
    private void addRowDivider(LinearLayout parent) {
        Context context = parent.getContext();
        View divider = new View(context);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(context, ROW_DIVIDER_HEIGHT_DP)));
        divider.setBackgroundResource(R.drawable.offline_form_row_divider);
        parent.addView(divider);
    }

    /**
     * 逐行渲染一组展示项：项与项之间统一画 1dp 通栏发丝分隔线。
     * 分页布局的页内容与平铺布局的分组内容共用这一套渲染规则。
     */
    private void renderChildrenContent(LinearLayout childrenLayout, List<OfflineFormDisplayItem> childItems) {
        childrenLayout.removeAllViews();
        if (childItems.isEmpty() || collapsedGroupNodes.contains(findParentNode(childItems))) {
            return;
        }

        boolean first = true;
        for (int i = 0; i < childItems.size(); i++) {
            OfflineFormDisplayItem child = childItems.get(i);
            if (!first) {
                addRowDivider(childrenLayout);
            }
            first = false;
            if (child.isGroup()) {
                List<OfflineFormDisplayItem> nestedChildren = collectDirectChildItems(childItems, i);
                addGroupBlock(childrenLayout, child, nestedChildren, false);
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
}
