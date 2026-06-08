package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.FormItemInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

public interface OfflineFormItemHandler {
    /**
     * 返回插件 JSON 中用于区分表单项类型的 itemType。
     */
    String getType();

    /**
     * 返回 RecyclerView 用于区分可编辑 ViewHolder 类型的固定值。
     */
    int getViewType();

    /**
     * 将插件命令传入的表单项 DTO 转成 APP 内部表单项模型。
     */
    BaseFormItem fromInput(FormItemInput input);

    /**
     * 将本地 definition.json 中该类型专属的 options 写回统一输入模型。
     */
    void readInputOptions(JSONObject options, FormItemInput input);

    /**
     * 将 APP 内部表单项模型转换成本地 definition.json 中保存的 JSON。
     */
    JSONObject toJson(BaseFormItem item);

    /**
     * 创建填报页面中可编辑状态的 ViewHolder。
     */
    BaseViewHolder createEditViewHolder(ViewGroup parent);

    /**
     * 创建历史记录表或详情页中只读状态的表单项 View。
     */
    View createReadOnlyView(Context context, BaseFormItem item, String rawValue, boolean compact);
}
