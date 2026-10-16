package com.huozige.lab.container.offlineform;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDisplayItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class OfflineRecordTableView {
    private static final int MENU_ID_RECORD_EDIT = 1;
    private static final int MENU_ID_RECORD_DELETE = 2;
    private static final int MENU_ID_RECORD_VIEW = 3;
    private static final int[] RECORD_PAGE_SIZE_OPTIONS = new int[]{1, 10, 20, 50, 100};

    private final Context _context;
    private final LinearLayout _container;
    private final boolean _editable;
    private final Callback _callback;

    private OfflineFormDefinitionFile _definitionFile;
    private List<OfflineFormRecord> _records = new ArrayList<>();
    private int _pageIndex;
    private LinearLayout _wrapper;
    private TableLayout _dataTableLayout;
    private TableLayout _actionTableLayout;
    private LinearLayout _paginationLayout;
    private TextView _previousButton;
    private TextView _pageInfoTextView;
    private TextView _nextButton;

    public OfflineRecordTableView(Context context, LinearLayout container, boolean editable, Callback callback) {
        _context = context;
        _container = container;
        _editable = editable;
        _callback = callback;
    }

    public void setRecords(OfflineFormDefinitionFile definitionFile, List<OfflineFormRecord> records) {
        _definitionFile = definitionFile;
        _records = records == null ? new ArrayList<>() : records;
        render();
    }

    public void clear() {
        _wrapper = null;
        _dataTableLayout = null;
        _actionTableLayout = null;
        _paginationLayout = null;
        _previousButton = null;
        _pageInfoTextView = null;
        _nextButton = null;
        _container.removeAllViews();
    }

    private void render() {
        if (_definitionFile == null || _records.isEmpty()) {
            clear();
            return;
        }
        ensureTableView();
        refreshRows();
        refreshPagination();
    }

    private void ensureTableView() {
        if (_wrapper != null) {
            return;
        }

        _wrapper = new LinearLayout(_context);
        _wrapper.setOrientation(LinearLayout.VERTICAL);
        _wrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        CardView cardView = new CardView(_context);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(_context, 4), 0, dp(_context, 8));
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(_editable ? ContextCompat.getColor(_context, android.R.color.white) : Color.rgb(245, 246, 248));
        cardView.setRadius(dp(_context, 8));
        cardView.setCardElevation(dp(_context, 2));
        cardView.setUseCompatPadding(true);
        cardView.setContentPadding(dp(_context, 8), dp(_context, 8), dp(_context, 8), dp(_context, 8));

        LinearLayout tableContainer = new LinearLayout(_context);
        tableContainer.setOrientation(LinearLayout.HORIZONTAL);
        tableContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        HorizontalScrollView scrollView = new HorizontalScrollView(_context);
        scrollView.setHorizontalScrollBarEnabled(true);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));

        _dataTableLayout = new TableLayout(_context);
        _dataTableLayout.setShrinkAllColumns(false);
        _dataTableLayout.setStretchAllColumns(false);

        _actionTableLayout = new TableLayout(_context);
        _actionTableLayout.setLayoutParams(new LinearLayout.LayoutParams(
                dp(_context, 44),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        _actionTableLayout.setShrinkAllColumns(false);
        _actionTableLayout.setStretchAllColumns(false);

        scrollView.addView(_dataTableLayout);
        tableContainer.addView(scrollView);
        tableContainer.addView(_actionTableLayout);
        cardView.addView(tableContainer);
        _wrapper.addView(cardView);
        _wrapper.addView(createPaginationBar());
        _container.addView(_wrapper);
    }

    private void refreshRows() {
        int pageSize = getRecordPageSize(_definitionFile);
        int pageCount = (_records.size() + pageSize - 1) / pageSize;
        if (_pageIndex >= pageCount) {
            _pageIndex = Math.max(0, pageCount - 1);
        }
        int fromIndex = _pageIndex * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, _records.size());
        List<OfflineFormRecord> currentPageRecords = _records.subList(fromIndex, toIndex);

        List<String> displayColumns = getDisplayColumns(_definitionFile);
        Map<String, String> columnTitles = buildColumnTitles(_definitionFile);
        Map<String, BaseFormItem> formItems = buildFormItems(_definitionFile);
        String currentSchemaVersion = _definitionFile.getJsonSchema().getSchemaVersion();

        _dataTableLayout.removeAllViews();
        _actionTableLayout.removeAllViews();
        _dataTableLayout.addView(createHeaderRow(displayColumns, columnTitles));
        _actionTableLayout.addView(createActionHeaderRow());
        for (OfflineFormRecord record : currentPageRecords) {
            _dataTableLayout.addView(createRecordRow(record, displayColumns, formItems, currentSchemaVersion));
            _actionTableLayout.addView(createActionRow(record, currentSchemaVersion));
        }
    }

    private void refreshPagination() {
        int pageSize = getRecordPageSize(_definitionFile);
        int pageCount = (_records.size() + pageSize - 1) / pageSize;
        boolean showPagination = _records.size() > pageSize;
        _paginationLayout.setVisibility(showPagination ? View.VISIBLE : View.GONE);
        if (!showPagination) {
            return;
        }
        _previousButton.setEnabled(_pageIndex > 0);
        _previousButton.setClickable(_pageIndex > 0);
        _previousButton.setTextColor(ContextCompat.getColor(_context, _pageIndex > 0 ? R.color.huozige_blue : android.R.color.darker_gray));
        _nextButton.setEnabled(_pageIndex < pageCount - 1);
        _nextButton.setClickable(_pageIndex < pageCount - 1);
        _nextButton.setTextColor(ContextCompat.getColor(_context, _pageIndex < pageCount - 1 ? R.color.huozige_blue : android.R.color.darker_gray));
        _pageInfoTextView.setText(_context.getString(R.string.offline_text_record_page_info, _pageIndex + 1, pageCount));
    }

    private View createPaginationBar() {
        LinearLayout paginationLayout = new LinearLayout(_context);
        _paginationLayout = paginationLayout;
        paginationLayout.setOrientation(LinearLayout.HORIZONTAL);
        paginationLayout.setGravity(Gravity.CENTER);
        paginationLayout.setPadding(0, dp(_context, 8), 0, dp(_context, 4));

        TextView previousButton = createPaginationButton(_context.getString(R.string.offline_button_previous_page));
        _previousButton = previousButton;
        previousButton.setOnClickListener(v -> {
            if (_pageIndex <= 0) {
                return;
            }
            _pageIndex--;
            render();
        });

        TextView pageInfoTextView = new TextView(_context);
        _pageInfoTextView = pageInfoTextView;
        pageInfoTextView.setTextColor(ContextCompat.getColor(_context, android.R.color.darker_gray));
        pageInfoTextView.setTextSize(14);
        pageInfoTextView.setGravity(Gravity.CENTER);
        pageInfoTextView.setPadding(dp(_context, 14), 0, dp(_context, 14), 0);

        TextView nextButton = createPaginationButton(_context.getString(R.string.offline_button_next_page));
        _nextButton = nextButton;
        nextButton.setOnClickListener(v -> {
            int pageSize = getRecordPageSize(_definitionFile);
            int pageCount = (_records.size() + pageSize - 1) / pageSize;
            if (_pageIndex >= pageCount - 1) {
                return;
            }
            _pageIndex++;
            render();
        });

        paginationLayout.addView(previousButton);
        paginationLayout.addView(pageInfoTextView);
        paginationLayout.addView(nextButton);
        return paginationLayout;
    }

    private TextView createPaginationButton(String text) {
        TextView button = new TextView(_context);
        button.setText(text);
        button.setTextSize(14);
        button.setGravity(Gravity.CENTER);
        button.setMinWidth(dp(_context, 72));
        button.setPadding(dp(_context, 10), dp(_context, 8), dp(_context, 10), dp(_context, 8));
        button.setBackgroundResource(android.R.drawable.list_selector_background);
        return button;
    }

    private TableRow createHeaderRow(List<String> displayColumns, Map<String, String> columnTitles) {
        TableRow row = new TableRow(_context);
        for (String fieldId : displayColumns) {
            row.addView(createCell(resolveColumnTitle(columnTitles, fieldId), true));
        }
        row.addView(createCell(_context.getString(R.string.offline_table_column_version), true));
        row.addView(createCell(_context.getString(R.string.offline_table_column_updated_at), true));
        return row;
    }

    private TableRow createActionHeaderRow() {
        TableRow row = new TableRow(_context);
        row.addView(createCell(_context.getString(R.string.offline_table_column_actions), true));
        return row;
    }

    private TableRow createRecordRow(OfflineFormRecord record, List<String> displayColumns, Map<String, BaseFormItem> formItems, String currentSchemaVersion) {
        TableRow row = new TableRow(_context);
        row.setClickable(true);
        row.setBackgroundResource(android.R.drawable.list_selector_background);
        for (String fieldId : displayColumns) {
            row.addView(createRecordValueCell(record, fieldId, formItems.get(fieldId)));
        }
        row.addView(createCell(record.getSchemaVersion(), false));
        row.addView(createCell(formatRecordTime(record.getUpdatedAt()), false));
        row.setOnClickListener(v -> {
            if (_editable) {
                _callback.onEditRecord(record, currentSchemaVersion);
                return;
            }
            _callback.onViewRecord(record);
        });
        return row;
    }

    private TableRow createActionRow(OfflineFormRecord record, String currentSchemaVersion) {
        TableRow row = new TableRow(_context);
        row.addView(createActionCell(record, currentSchemaVersion));
        return row;
    }

    private TextView createCell(String text, boolean header) {
        TextView textView = new TextView(_context);
        textView.setMinWidth(dp(_context, 120));
        textView.setMaxWidth(dp(_context, 220));
        textView.setPadding(dp(_context, 12), dp(_context, 10), dp(_context, 12), dp(_context, 10));
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(text == null ? "" : text);
        textView.setTextSize(header ? 14 : 13);
        textView.setTextColor(ContextCompat.getColor(_context, header ? android.R.color.black : android.R.color.darker_gray));
        if (header) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        return textView;
    }

    private View createRecordValueCell(OfflineFormRecord record, String fieldId, BaseFormItem formItem) {
        String rawValue = getRecordValue(record, fieldId);
        if (formItem == null) {
            return createCell(rawValue, false);
        }
        return OfflineFormItemRegistry.createReadOnlyView(_context, formItem, rawValue, true);
    }

    private View createActionCell(OfflineFormRecord record, String currentSchemaVersion) {
        LinearLayout actions = new LinearLayout(_context);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setPadding(dp(_context, 4), dp(_context, 2), dp(_context, 4), dp(_context, 2));
        actions.setMinimumWidth(dp(_context, 24));

        TextView moreButton = createActionButton(_context.getString(R.string.offline_button_more), android.R.color.black);
        moreButton.setOnClickListener(v -> showRecordActionMenu(moreButton, record, currentSchemaVersion));
        actions.addView(moreButton);

        return actions;
    }

    private TextView createActionButton(String text, int colorResId) {
        TextView button = new TextView(_context);
        button.setText(text);
        button.setTextSize(13);
        button.setTypeface(null, Typeface.BOLD);
        button.setTextColor(ContextCompat.getColor(_context, colorResId));
        button.setGravity(Gravity.CENTER);
        button.setPadding(dp(_context, 6), dp(_context, 8), dp(_context, 6), dp(_context, 8));
        button.setMinWidth(dp(_context, 36));
        button.setClickable(true);
        button.setBackgroundResource(android.R.drawable.list_selector_background);
        return button;
    }

    private void showRecordActionMenu(View anchor, OfflineFormRecord record, String currentSchemaVersion) {
        PopupMenu popupMenu = new PopupMenu(_context, anchor);
        if (_editable) {
            popupMenu.getMenu().add(0, MENU_ID_RECORD_EDIT, MENU_ID_RECORD_EDIT, R.string.offline_button_edit);
        } else {
            popupMenu.getMenu().add(0, MENU_ID_RECORD_VIEW, MENU_ID_RECORD_VIEW, R.string.offline_button_view_detail);
        }
        popupMenu.getMenu().add(0, MENU_ID_RECORD_DELETE, MENU_ID_RECORD_DELETE, R.string.offline_button_delete);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_ID_RECORD_EDIT) {
                _callback.onEditRecord(record, currentSchemaVersion);
                return true;
            }
            if (item.getItemId() == MENU_ID_RECORD_VIEW) {
                _callback.onViewRecord(record);
                return true;
            }
            if (item.getItemId() == MENU_ID_RECORD_DELETE) {
                _callback.onDeleteRecord(record);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private List<String> getDisplayColumns(OfflineFormDefinitionFile definitionFile) {
        if (definitionFile.getComputed() != null && definitionFile.getComputed().getDisplayColumns() != null) {
            return definitionFile.getComputed().getDisplayColumns();
        }
        return new ArrayList<>();
    }

    private int getRecordPageSize(OfflineFormDefinitionFile definitionFile) {
        if (definitionFile.getComputed() == null || definitionFile.getComputed().getRecordPageSize() <= 0) {
            return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
        }
        int pageSize = definitionFile.getComputed().getRecordPageSize();
        for (int option : RECORD_PAGE_SIZE_OPTIONS) {
            if (option == pageSize) {
                return pageSize;
            }
        }
        return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
    }

    private Map<String, String> buildColumnTitles(OfflineFormDefinitionFile definitionFile) {
        Map<String, String> titles = new HashMap<>();
        if (definitionFile.getJsonSchema() == null) {
            return titles;
        }
        for (OfflineFormDisplayItem displayItem : OfflineFormDefinitionFlattener.flattenFieldDisplayItems(definitionFile.getJsonSchema())) {
            titles.put(displayItem.getFormItem().getId(), displayItem.getFieldDisplayTitle());
        }
        return titles;
    }

    private Map<String, BaseFormItem> buildFormItems(OfflineFormDefinitionFile definitionFile) {
        Map<String, BaseFormItem> formItems = new HashMap<>();
        if (definitionFile.getJsonSchema() == null) {
            return formItems;
        }
        for (BaseFormItem formItem : OfflineFormDefinitionFlattener.flattenFields(definitionFile.getJsonSchema())) {
            formItems.put(formItem.getId(), formItem);
        }
        return formItems;
    }

    private String resolveColumnTitle(Map<String, String> columnTitles, String fieldId) {
        String title = columnTitles.get(fieldId);
        return title == null || title.isEmpty() ? fieldId : title;
    }

    private String getRecordValue(OfflineFormRecord record, String fieldId) {
        if (record.getValues() == null) {
            return "";
        }
        String value = record.getValues().get(fieldId);
        return value == null ? "" : value;
    }

    private String formatRecordTime(long time) {
        if (time <= 0) {
            return _context.getString(R.string.offline_text_unknown_record_time);
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    public interface Callback {
        void onEditRecord(OfflineFormRecord record, String currentSchemaVersion);

        void onViewRecord(OfflineFormRecord record);

        void onDeleteRecord(OfflineFormRecord record);
    }
}
