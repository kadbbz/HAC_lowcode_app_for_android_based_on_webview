package com.huozige.lab.container.offlineform.model.formitem.picker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickerFormItemOptions {
    private boolean includeSeconds;
    private boolean includeTime;

    /** 日期范围模式：flexible（灵活选择，默认）或 deadline（截止日期，最晚到当前时刻）。 */
    private String dateRangeMode;

    /** 灵活选择模式下的最早可选日期，格式 yyyy/M/d，可空。 */
    private String minDate;

    /** 灵活选择模式下的最晚可选日期，格式 yyyy/M/d，可空。 */
    private String maxDate;
}
