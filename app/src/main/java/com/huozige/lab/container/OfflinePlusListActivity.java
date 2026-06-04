package com.huozige.lab.container;

import android.os.Bundle;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndex;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndexItem;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusListActivity extends BaseActivity {
    private OfflinePlusCardAdapter _adapter;
    private List<OfflineFormDefinitionIndexItem> _cardItemList;
    private List<OfflineFormDefinitionIndexItem> _displayedCardItemList;
    private boolean _sortMode;
    private ItemTouchHelper _itemTouchHelper;
    private EditText _searchEditText;
    private Button _cmdSort;
    private Button _cmdManage;
    private Button _cmdDelete;
    private Button _cmdCancelManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_title_history);
        setContentView(R.layout.offline_list_activity);

        _searchEditText = findViewById(R.id.searchEditText);
        _cmdSort = findViewById(R.id.cmdSort);
        _cmdManage = findViewById(R.id.cmdManage);
        _cmdDelete = findViewById(R.id.cmdDelete);
        _cmdCancelManage = findViewById(R.id.cmdCancelManage);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _cardItemList = generateData();
        _displayedCardItemList = _cardItemList;

        _adapter = new OfflinePlusCardAdapter(_displayedCardItemList, this);
        recyclerView.setAdapter(_adapter);
        setupItemTouchHelper(recyclerView);

        setupActions();
    }

    private List<OfflineFormDefinitionIndexItem> generateData() {

        return OfflineFormFileHelper.readDefinitionIndex(this).getItems();

    }

    private void setupActions() {
        _cmdDelete.setTextColor(Color.RED);
        _cmdSort.setOnClickListener(v -> setSortMode(!_sortMode));
        _cmdManage.setOnClickListener(v -> setManageMode(true));
        _cmdCancelManage.setOnClickListener(v -> setManageMode(false));
        _cmdDelete.setOnClickListener(v -> confirmDeleteSelectedItems());
        _searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySearch(s == null ? "" : s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupItemTouchHelper(RecyclerView recyclerView) {
        _itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean isLongPressDragEnabled() {
                return _sortMode;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (!_sortMode) {
                    return false;
                }

                _adapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
        _itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setSortMode(boolean sortMode) {
        if (sortMode && !_searchEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_clear_search_before_sort, Toast.LENGTH_SHORT).show();
            return;
        }

        _sortMode = sortMode;
        _cmdSort.setText(sortMode ? R.string.offline_button_finish_sort : R.string.offline_button_sort);
        _cmdManage.setVisibility(sortMode ? View.GONE : View.VISIBLE);
        _adapter.setSortMode(sortMode);

        if (sortMode) {
            Toast.makeText(this, R.string.offline_toast_drag_to_sort, Toast.LENGTH_SHORT).show();
        } else {
            saveList();
            Toast.makeText(this, R.string.offline_toast_sort_saved, Toast.LENGTH_SHORT).show();
        }
    }

    private void setManageMode(boolean manageMode) {
        if (manageMode && _sortMode) {
            setSortMode(false);
        }
        _cmdSort.setVisibility(manageMode ? View.GONE : View.VISIBLE);
        _cmdManage.setVisibility(manageMode ? View.GONE : View.VISIBLE);
        _cmdDelete.setVisibility(manageMode ? View.VISIBLE : View.GONE);
        _cmdCancelManage.setVisibility(manageMode ? View.VISIBLE : View.GONE);
        _adapter.setManageMode(manageMode);
    }

    private void confirmDeleteSelectedItems() {
        List<String> selectedPatternIds = _adapter.getSelectedPatternIds();
        if (selectedPatternIds.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_select_before_delete, Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.offline_dialog_delete_title)
                .setMessage(R.string.offline_dialog_delete_message)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteSelectedItems(selectedPatternIds))
                .setNegativeButton(R.string.ui_button_cancel, null)
                .show();
    }

    private void deleteSelectedItems(List<String> selectedPatternIds) {
        _cardItemList.removeIf(item -> {
            boolean selected = selectedPatternIds.contains(item.getPatternId());
            if (selected) {
                OfflineFormFileHelper.deletePatternDirectory(this, item.getPatternId());
            }
            return selected;
        });

        saveList();
        applySearch(_searchEditText.getText().toString());
        setManageMode(false);
        Toast.makeText(this, R.string.offline_toast_delete_success, Toast.LENGTH_SHORT).show();
    }

    private void applySearch(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            _displayedCardItemList = _cardItemList;
        } else {
            _displayedCardItemList = new ArrayList<>();
            for (OfflineFormDefinitionIndexItem item : _cardItemList) {
                if (containsKeyword(item.getTitle(), normalizedKeyword) || containsKeyword(item.getDescription(), normalizedKeyword)) {
                    _displayedCardItemList.add(item);
                }
            }
        }

        _adapter.updateData(_displayedCardItemList);
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private void saveList() {
        OfflineFormFileHelper.writeDefinitionIndex(this, new OfflineFormDefinitionIndex(_cardItemList));
    }

}
