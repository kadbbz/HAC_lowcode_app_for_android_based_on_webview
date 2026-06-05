package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.SelectViewHolder;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BaseFormItem> formItems = new ArrayList<>();

    public void setFormItems(List<BaseFormItem> items) {
        this.formItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addFormItem(BaseFormItem item) {
        formItems.add(item);
        notifyItemInserted(formItems.size() - 1);
    }

    public List<BaseFormItem> getFormItems() {
        return new ArrayList<>(formItems);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case BaseFormItem.TYPE_TEXT:
                View textView = inflater.inflate(R.layout.custom_form_item_form_text, parent, false);
                return new TextViewHolder(textView);

            case BaseFormItem.TYPE_SELECT:
                View selectView = inflater.inflate(R.layout.custom_form_item_form_select, parent, false);
                return new SelectViewHolder(selectView);

//            case BaseFormItem.TYPE_SWITCH:
//                View switchView = inflater.inflate(R.layout.item_form_switch, parent, false);
//                return new SwitchViewHolder(switchView);
//
//            case BaseFormItem.TYPE_DATE:
//                View dateView = inflater.inflate(R.layout.item_form_date, parent, false);
//                return new DateViewHolder(dateView);

            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        BaseFormItem item = formItems.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return formItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return formItems.get(position).getItemType();
    }

    // 验证所有表单项
    public boolean validateAll() {
        boolean isValid = true;

        for (int i = 0; i < formItems.size(); i++) {
            BaseFormItem item = formItems.get(i);
            if (!item.validate()) {
                isValid = false;
                // 更新对应项的UI
                notifyItemChanged(i);
            } else {
                item.clearError();
                notifyItemChanged(i);
            }
        }

        return isValid;
    }

    // 收集表单数据
    public Map<String, String> collectFormData() {
        Map<String, String> formData = new HashMap<>();

        for (BaseFormItem item : formItems) {
            formData.put(item.getId(), item.getValue());
        }

        return formData;
    }
}