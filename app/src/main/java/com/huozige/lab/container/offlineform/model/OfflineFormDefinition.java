package com.huozige.lab.container.offlineform.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFormDefinition {
    // 布局方式：步骤内的顶级节点纵向平铺（默认，向后兼容）。
    public static final String LAYOUT_MODE_FLAT = "flat";
    // 布局方式：步骤内每个顶级节点各自成页，通过「上一项 / 下一项」翻页。
    public static final String LAYOUT_MODE_PAGED = "paged";

    // 表单定义的业务唯一标识，也是本地文件夹名称。
    private String patternId = "";
    // 表单定义版本号，由插件传入，填报记录保存时会写入记录文件。
    private String schemaVersion = "";
    // 表单定义名称，用于历史填报列表主标题展示。
    private String title = "";
    // 表单定义备注，用于历史填报列表描述展示。
    private String description = "";
    // 表单步骤定义，原生填报页根据步骤中的节点树渲染控件。
    private List<OfflineFormCardStyle> cardStyle = new ArrayList<>();
    private List<OfflineFormStep> steps = new ArrayList<>();
    // 渲染布局方式，由插件下发；未知/缺失一律回落为平铺。
    private String layoutMode = LAYOUT_MODE_FLAT;

    /**
     * 归一化布局方式：仅识别 {@link #LAYOUT_MODE_PAGED}（忽略大小写与首尾空白），
     * 其余（null / 空串 / 未知值）一律回落为 {@link #LAYOUT_MODE_FLAT}，
     * 以保证插件新增枚举值不会让旧 APP 崩溃。
     */
    public static String normalizeLayoutMode(String raw) {
        return raw != null && LAYOUT_MODE_PAGED.equalsIgnoreCase(raw.trim())
                ? LAYOUT_MODE_PAGED
                : LAYOUT_MODE_FLAT;
    }
}


