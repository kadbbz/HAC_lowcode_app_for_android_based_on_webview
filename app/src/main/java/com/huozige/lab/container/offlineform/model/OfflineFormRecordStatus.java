package com.huozige.lab.container.offlineform.model;

/**
 * 记录状态。
 *
 * <p>v0.5.0 起状态不再是"用户动作的留痕"，而是"校验结果的推导值"：</p>
 * <ul>
 *     <li>{@link #DRAFT}：存在"碰过的"填报单元(group)未通过校验（不完整），不进导出队列。</li>
 *     <li>{@link #SUBMITTED}：所有"碰过的" group 均通过校验，进导出队列。</li>
 *     <li>{@link #EXPORTED}：已上传（只读）。</li>
 * </ul>
 */
public enum OfflineFormRecordStatus {
    DRAFT,
    SUBMITTED,
    EXPORTED
}
