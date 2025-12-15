package com.huozige.lab.container.proxy.support.customform.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.customform.model.BaseFormItem;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(BaseFormItem item);

    public abstract void bind(BaseFormItem item, int position);

    public abstract void updateErrorState();
}
