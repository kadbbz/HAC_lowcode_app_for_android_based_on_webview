package com.huozige.lab.container.offlineform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 当前离线表单记录的填报进度。完成率取 0-100 的百分比数值。 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormProgress {
    private int totalFillItems;
    private int filledFillItems;
    private double completionRate;

    public static OfflineFormProgress empty() {
        return new OfflineFormProgress(0, 0, 0d);
    }
}
