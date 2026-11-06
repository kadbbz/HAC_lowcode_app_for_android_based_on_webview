package com.huozige.lab.container.offlineform;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormExportStatusHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

public class OfflinePlusListActivity extends OfflineModuleActivity {
    private OfflinePlusCardAdapter _adapter;
    private List<OfflineFormDefinitionIndexItem> _cardItemList;
    private List<OfflineFormDefinitionIndexItem> _displayedCardItemList;
    // 筛选条件收在搜索框右侧的底部抽屉里，默认保持原「仅未导出」视角。
    private ExportFilter _filter = ExportFilter.UNEXPORTED;
    // 拖拽结束后才有必要落盘一次顺序，避免拖动过程中频繁写文件。
    private boolean _orderDirty;
    private EditText _searchEditText;
    private AppCompatImageButton _filterButton;

    /** 表单列表是离线填报模块首页：Header 左侧显示「应用首页」入口。 */
    @Override
    protected boolean isOfflineModuleHome() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_title_project_list);
        setContentView(R.layout.offline_form_list_activity);

        _searchEditText = findViewById(R.id.searchEditText);
        _filterButton = findViewById(R.id.buttonFilter);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _cardItemList = generateData();
        _displayedCardItemList = new ArrayList<>();

        _adapter = new OfflinePlusCardAdapter(_displayedCardItemList, this, item -> reloadList());
        recyclerView.setAdapter(_adapter);
        setupItemTouchHelper(recyclerView);

        setupActions();
        applyFilters();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    private List<OfflineFormDefinitionIndexItem> generateData() {

        return OfflineFormFileHelper.readDefinitions(this);

    }

    private void reloadList() {
        if (_adapter == null) {
            return;
        }

        _cardItemList = generateData();
        applyFilters();
    }

    private void setupActions() {
        _filterButton.setOnClickListener(v -> showFilterSheet());
        _searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        updateFilterButtonState();
    }

    /**
     * 卡片默认支持长按拖拽排序：不再需要先进入「排序」模式。
     *
     * <p>筛选 / 搜索只影响展示子集，拖拽结束后把展示顺序回填到完整列表：
     * 被隐藏的表单保持它们原来占用的位置，可见项按拖拽后的先后填回这些位置，
     * 这样清空筛选后不会出现顺序错乱。</p>
     */
    private void setupItemTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getBindingAdapterPosition();
                int toPosition = target.getBindingAdapterPosition();
                if (fromPosition == RecyclerView.NO_POSITION || toPosition == RecyclerView.NO_POSITION) {
                    return false;
                }

                _adapter.moveItem(fromPosition, toPosition);
                _orderDirty = true;
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                // 手指抬起时先结束选中态（ACTION_STATE_IDLE），此时把新的顺序落盘。
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    persistOrderIfNeeded();
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void persistOrderIfNeeded() {
        if (!_orderDirty) {
            return;
        }

        _orderDirty = false;
        applyDisplayedOrderToCardItems();
        saveList();
    }

    private void applyDisplayedOrderToCardItems() {
        IdentityHashMap<OfflineFormDefinitionIndexItem, Integer> indexByItem = new IdentityHashMap<>();
        for (int i = 0; i < _cardItemList.size(); i++) {
            indexByItem.put(_cardItemList.get(i), i);
        }

        List<Integer> visibleIndexes = new ArrayList<>(_displayedCardItemList.size());
        for (OfflineFormDefinitionIndexItem item : _displayedCardItemList) {
            Integer index = indexByItem.get(item);
            if (index == null) {
                // 契约保险：正常流程中 reloadList 会同时替换两个列表，拖拽期间不会发生；
                // 若未来出现列表被替换的情况，直接放弃本次顺序，避免用陈旧数据覆盖新列表。
                return;
            }
            visibleIndexes.add(index);
        }

        Collections.sort(visibleIndexes);
        for (int i = 0; i < visibleIndexes.size(); i++) {
            _cardItemList.set(visibleIndexes.get(i), _displayedCardItemList.get(i));
        }
    }

    private void showFilterSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View content = LayoutInflater.from(this).inflate(R.layout.offline_form_list_filter_sheet, null);
        RadioGroup optionGroup = content.findViewById(R.id.filter_option_group);
        optionGroup.check(_filter.optionViewId);

        // 点当前已选中的选项也要能关闭抽屉，因此按行处理点击而不是监听 RadioGroup 的选中变化。
        View.OnClickListener optionClickListener = v -> {
            dialog.dismiss();
            ExportFilter selected = ExportFilter.fromOptionViewId(v.getId());
            if (selected == _filter) {
                return;
            }

            _filter = selected;
            applyFilters();
            updateFilterButtonState();
        };
        for (ExportFilter filter : ExportFilter.values()) {
            RadioButton option = content.findViewById(filter.optionViewId);
            // 打开时按当前选项显式设置对勾：compound drawable 在布局时按当时状态的固有尺寸定 bounds，
            // 状态切换不会重新测量，纯 selector 方案会出现选中后对勾仍不显示的问题。
            option.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, filter == _filter ? R.drawable.ic_offline_check : 0, 0);
            option.setOnClickListener(optionClickListener);
        }

        dialog.setContentView(content);
        dialog.show();
    }

    private void updateFilterButtonState() {
        // 筛选条件不是「全部」时按钮高亮，作为列表处于筛选状态的唯一视觉提示。
        _filterButton.setSelected(_filter != ExportFilter.ALL);
        _filterButton.setContentDescription(getString(
                R.string.offline_cd_filter, getString(_filter.labelResId)));
    }

    private void applyFilters() {
        String keyword = _searchEditText.getText().toString().trim().toLowerCase();
        _displayedCardItemList = new ArrayList<>();
        for (OfflineFormDefinitionIndexItem item : _cardItemList) {
            if (!matchesFilter(item)) {
                continue;
            }
            if (keyword.isEmpty()
                    || containsKeyword(item.getTitle(), keyword)
                    || containsKeyword(item.getDescription(), keyword)) {
                _displayedCardItemList.add(item);
            }
        }

        // 适配器直接持有该列表实例（拖拽 moveItem 需就地修改），不要在这里传副本。
        _adapter.updateData(_displayedCardItemList);
    }

    private boolean matchesFilter(OfflineFormDefinitionIndexItem item) {
        switch (_filter) {
            case EXPORTED:
                return OfflineFormExportStatusHelper.isExported(this, item);
            case UNEXPORTED:
                return OfflineFormExportStatusHelper.hasUnexportedRecords(this, item);
            default:
                return true;
        }
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private void saveList() {
        OfflineFormFileHelper.writeDefinitionOrder(this, _cardItemList);
    }

    /** 表单列表筛选条件：下拉抽屉里的三个单选项。 */
    private enum ExportFilter {
        ALL(R.id.filter_option_all, R.string.offline_filter_all),
        EXPORTED(R.id.filter_option_exported, R.string.offline_filter_exported),
        UNEXPORTED(R.id.filter_option_unexported, R.string.offline_filter_unexported);

        private final int optionViewId;
        private final int labelResId;

        ExportFilter(int optionViewId, int labelResId) {
            this.optionViewId = optionViewId;
            this.labelResId = labelResId;
        }

        static ExportFilter fromOptionViewId(int optionViewId) {
            for (ExportFilter filter : values()) {
                if (filter.optionViewId == optionViewId) {
                    return filter;
                }
            }
            return ExportFilter.UNEXPORTED;
        }
    }

}
