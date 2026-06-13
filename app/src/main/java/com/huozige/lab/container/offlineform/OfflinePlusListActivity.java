package com.huozige.lab.container.offlineform;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusListActivity extends BaseActivity {
    private OfflinePlusCardAdapter _adapter;
    private List<OfflineFormDefinitionIndexItem> _cardItemList;
    private List<OfflineFormDefinitionIndexItem> _displayedCardItemList;
    private boolean _sortMode;
    private EditText _searchEditText;
    private Button _cmdSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_title_project_list);
        setContentView(R.layout.offline_form_list_activity);

        _searchEditText = findViewById(R.id.searchEditText);
        _cmdSort = findViewById(R.id.cmdSort);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _cardItemList = generateData();
        _displayedCardItemList = _cardItemList;

        _adapter = new OfflinePlusCardAdapter(_displayedCardItemList, this);
        recyclerView.setAdapter(_adapter);
        setupItemTouchHelper(recyclerView);

        setupActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (_sortMode) {
            return;
        }
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
        applySearch(_searchEditText.getText().toString());
    }

    private void setupActions() {
        _cmdSort.setOnClickListener(v -> setSortMode(!_sortMode));
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
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
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setSortMode(boolean sortMode) {
        if (sortMode && StringUtils.isNotBlank(_searchEditText.getText().toString())) {
            Toast.makeText(this, R.string.offline_toast_clear_search_before_sort, Toast.LENGTH_SHORT).show();
            return;
        }

        _sortMode = sortMode;
        _cmdSort.setText(sortMode ? R.string.offline_button_finish_sort : R.string.offline_button_sort);
        _adapter.setSortMode(sortMode);

        if (sortMode) {
            Toast.makeText(this, R.string.offline_toast_drag_to_sort, Toast.LENGTH_SHORT).show();
        } else {
            saveList();
            Toast.makeText(this, R.string.offline_toast_sort_saved, Toast.LENGTH_SHORT).show();
        }
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
        OfflineFormFileHelper.writeDefinitionOrder(this, _cardItemList);
    }

}
