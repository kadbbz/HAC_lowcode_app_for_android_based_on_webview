package com.huozige.lab.container.offlineform.model.formitem.common;

import lombok.Data;

@Data
public class FormItemInput {
    public String itemId = "";
    public String title = "";
    public String hint = "";
    public boolean required;
    public String itemType = "";
    public String value = "";
    // 是否在填报页展示该字段的更新时间。
    public boolean showFieldUpdateTime;
    public Object options;
    // 组件定义的最后更新时间，使用 ODate；为空时由构建流程生成当前时间。
    public Double updateTime;
}
