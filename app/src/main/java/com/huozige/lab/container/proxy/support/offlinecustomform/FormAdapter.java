package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.offlineform.formitem.OfflineFormItemHandler;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BaseFormItem> formItems = new ArrayList<>();
    private Map<Integer, OfflineFormItemHandler> handlersByViewType = new HashMap<>();

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
        OfflineFormItemHandler handler = handlersByViewType.get(viewType);
        if (handler == null) {
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
        return handler.createEditViewHolder(parent);
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
        OfflineFormItemHandler handler = OfflineFormItemRegistry.getHandler(formItems.get(position).getItemType());
        int viewType = handler.getViewType();
        handlersByViewType.put(viewType, handler);
        return viewType;
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
