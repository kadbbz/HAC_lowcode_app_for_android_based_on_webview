package com.huozige.lab.container.offlineform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 配置填报进度文本节点展示哪些统计项。 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormProgressDisplay {
    // 未传配置时默认展示全部统计项，保持 progress 文本节点的直观行为。
    private boolean total = true;
    private boolean completed = true;
    private boolean completionRate = true;
}
