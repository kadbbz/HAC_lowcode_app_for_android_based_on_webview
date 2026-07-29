package com.huozige.lab.container.proxy.support.printer;

/**
 * TSPL打印命令项基类
 */
public class TSPLPrintCommandItem {

    /**
     * 项目类型：文本、条形码、二维码
     */
    private String itemType;

    /**
     * X坐标位置
     */
    private String xLocation;

    /**
     * Y坐标位置
     */
    private String yLocation;

    /**
     * 旋转角度：0, 90, 180, 270
     */
    private String rotation;

    /**
     * 内容
     */
    private String content;

    // ========== 文本类型专属字段 ==========

    /**
     * 字体名称（如：TSS24.BF2）
     */
    private String font;

    /**
     * X方向倍率（1-8）
     */
    private String xMulti;

    /**
     * Y方向倍率（1-8）
     */
    private String yMulti;

    // ========== 条形码类型专属字段 ==========

    /**
     * 条码类型（如：128, 39, EAN13等）
     */
    private String codeType;

    /**
     * 条码高度
     */
    private String codeHeight;

    /**
     * 是否可读：0-不可读，1-可读
     */
    private String readable;

    /**
     * 条码宽
     */
    private String codeWide;

    /**
     * 条码窄
     */
    private String codeNarrow;

    // ========== 二维码类型专属字段 ==========

    /**
     * 纠错级别：L(7%), M(15%), Q(25%), H(30%)
     */
    private String eccLevel;

    /**
     * 二维码单元格宽度
     */
    private String cellWidth;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getXLocation() {
        return xLocation;
    }

    public void setXLocation(String xLocation) {
        this.xLocation = xLocation;
    }

    public String getYLocation() {
        return yLocation;
    }

    public void setYLocation(String yLocation) {
        this.yLocation = yLocation;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getXMulti() {
        return xMulti;
    }

    public void setXMulti(String xMulti) {
        this.xMulti = xMulti;
    }

    public String getYMulti() {
        return yMulti;
    }

    public void setYMulti(String yMulti) {
        this.yMulti = yMulti;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeHeight() {
        return codeHeight;
    }

    public void setCodeHeight(String codeHeight) {
        this.codeHeight = codeHeight;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    public String getCodeWide() {
        return codeWide;
    }

    public void setCodeWide(String codeWide) {
        this.codeWide = codeWide;
    }

    public String getCodeNarrow() {
        return codeNarrow;
    }

    public void setCodeNarrow(String codeNarrow) {
        this.codeNarrow = codeNarrow;
    }

    public String getEccLevel() {
        return eccLevel;
    }

    public void setEccLevel(String eccLevel) {
        this.eccLevel = eccLevel;
    }

    public String getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(String cellWidth) {
        this.cellWidth = cellWidth;
    }
}