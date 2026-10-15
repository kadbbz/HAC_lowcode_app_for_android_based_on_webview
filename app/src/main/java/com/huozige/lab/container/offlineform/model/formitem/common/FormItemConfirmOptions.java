package com.huozige.lab.container.offlineform.model.formitem.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 字段的“修改前二次确认”配置。
 *
 * <p>仅部分组件（下拉选择、单选框）会下发该配置。启用后，用户修改字段值前先弹出确认弹窗；
 * 确认通过后才写入新值并更新时间，取消则不改动任何状态。</p>
 */
@Getter
@Setter
public class FormItemConfirmOptions {
    /** 是否启用二次确认。 */
    private boolean enabled;
    /** 弹窗标题；为空时使用默认文案。 */
    private String title;
    /** 弹窗内容；为空时使用默认文案，支持 {title}/{value}/{oldValue} 占位符。 */
    private String message;
    /** 确定按钮文字；为空时使用默认文案。 */
    private String confirmButtonText;
    /** 取消按钮文字；为空时使用默认文案。 */
    private String cancelButtonText;
    /** 确认后是否禁用该组件（默认禁用）。 */
    private boolean disableAfterConfirm = true;

    public boolean isEffective() {
        return enabled;
    }
}
