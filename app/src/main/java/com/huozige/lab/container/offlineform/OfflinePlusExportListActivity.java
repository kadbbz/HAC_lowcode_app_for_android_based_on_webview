package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionIndexItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusExportCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormExportStatusHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusExportListActivity extends BaseActivity {
    public static final String EXTRA_SELECTED_PATTERN_IDS = "selectedPatternIds";

    private OfflinePlusExportCardAdapter _adapter;
    private List<OfflineFormDefinitionIndexItem> _cardItemList;
    private List<OfflineFormDefinitionIndexItem> _displayedCardItemList;
    private boolean _showUnexportedOnly = true;
    private EditText _searchEditText;
    private TextView _cmdFilterUnexported;
    private TextView _cmdFilterAll;
    private CheckBox _cmdSelectAll;
    private Button _cmdExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("\u9009\u62e9\u5bfc\u51fa\u8868\u5355");
        setContentView(R.layout.offline_form_export_list_activity);

        _searchEditText = findViewById(R.id.searchEditText);
        _cmdFilterUnexported = findViewById(R.id.cmdFilterUnexported);
        _cmdFilterAll = findViewById(R.id.cmdFilterAll);
        _cmdSelectAll = findViewById(R.id.cmdSelectAll);
        _cmdExport = findViewById(R.id.cmdExport);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _cardItemList = generateData();
        _displayedCardItemList = _cardItemList;

        _adapter = new OfflinePlusExportCardAdapter(_displayedCardItemList, this, this::updateActions);
        recyclerView.setAdapter(_adapter);

        setupActions();
        applySearch(_searchEditText.getText().toString());
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
        applySearch(_searchEditText.getText().toString());
    }

    private void setupActions() {
        _cmdFilterUnexported.setOnClickListener(v -> setFilterMode(true));
        _cmdFilterAll.setOnClickListener(v -> setFilterMode(false));
        _cmdSelectAll.setOnClickListener(v -> toggleDisplayedSelection());
        _cmdExport.setOnClickListener(v -> finishWithSelectedItems());
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

    private void setFilterMode(boolean showUnexportedOnly) {
        if (_showUnexportedOnly == showUnexportedOnly) {
            return;
        }

        _showUnexportedOnly = showUnexportedOnly;
        applySearch(_searchEditText.getText().toString());
        updateActions();
    }

    private void toggleDisplayedSelection() {
        boolean shouldSelect = !_adapter.areAllSelected(_displayedCardItemList);
        _adapter.setSelected(_displayedCardItemList, shouldSelect);
        updateActions();
    }

    private void finishWithSelectedItems() {
        ArrayList<String> selectedPatternIds = _adapter.getSelectedPatternIdsInOrder(_cardItemList);
        if (selectedPatternIds.isEmpty()) {
            Toast.makeText(this, "\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u4e2a\u8868\u5355", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent result = new Intent();
        result.putStringArrayListExtra(EXTRA_SELECTED_PATTERN_IDS, selectedPatternIds);
        setResult(RESULT_OK, result);
        finish();
    }

    private void applySearch(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        _displayedCardItemList = new ArrayList<>();
        for (OfflineFormDefinitionIndexItem item : _cardItemList) {
            if (_showUnexportedOnly && !OfflineFormExportStatusHelper.hasUnexportedRecords(this, item)) {
                continue;
            }
            if (normalizedKeyword.isEmpty()
                    || containsKeyword(item.getTitle(), normalizedKeyword)
                    || containsKeyword(item.getDescription(), normalizedKeyword)) {
                _displayedCardItemList.add(item);
            }
        }

        _adapter.updateData(_displayedCardItemList);
        updateActions();
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private void updateActions() {
        if (_adapter == null) {
            return;
        }
        _cmdFilterUnexported.setSelected(_showUnexportedOnly);
        _cmdFilterAll.setSelected(!_showUnexportedOnly);
        boolean hasDisplayedItems = _displayedCardItemList != null && !_displayedCardItemList.isEmpty();
        _cmdSelectAll.setEnabled(hasDisplayedItems);
        boolean allDisplayedSelected = _adapter.areAllSelected(_displayedCardItemList);
        _cmdSelectAll.setChecked(allDisplayedSelected);
        _cmdSelectAll.setText(allDisplayedSelected ? "\u53d6\u6d88\u5168\u9009" : "\u5168\u9009");
        _cmdExport.setEnabled(_adapter.getSelectedCount() > 0);
    }
}
