package com.huozige.lab.container.proxy.support.printer;

/**
 * TSPL命令构建工具类
 */
public class TSPLCommandBuilder {

    /**
     * 根据TSPL打印请求构建TSPL命令字符串
     *
     * @param request TSPL打印请求对象
     * @return TSPL命令字符串
     */
    public static String buildTSPLCommands(TSPLPrintRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("TSPLPrintRequest cannot be null");
        }

        StringBuilder sb = new StringBuilder();

        // 设置标签尺寸
        String width = request.getPrinterWidthMM() != null ? request.getPrinterWidthMM() : "60";
        String height = request.getPrinterHeightMM() != null ? request.getPrinterHeightMM() : "40";
        sb.append("SIZE ").append(width).append(" mm, ").append(height).append(" mm\n");

        // 设置间隙（默认2mm）
        sb.append("GAP 2 mm, 0\n");

        // 清除缓冲区
        sb.append("CLS\n");

        // 遍历命令列表，生成对应的TSPL命令
        if (request.getCommandList() != null) {
            for (TSPLPrintCommandItem item : request.getCommandList()) {
                String command = buildCommandItem(item);
                if (command != null && !command.isEmpty()) {
                    sb.append(command).append("\n");
                }
            }
        }

        // 打印1份
        sb.append("PRINT 1\n");

        return sb.toString();
    }

    /**
     * 根据命令项类型构建单个TSPL命令
     *
     * @param item 命令项
     * @return TSPL命令字符串
     */
    private static String buildCommandItem(TSPLPrintCommandItem item) {
        if (item == null || item.getItemType() == null) {
            return null;
        }

        String x = item.getXLocation() != null ? item.getXLocation() : "0";
        String y = item.getYLocation() != null ? item.getYLocation() : "0";
        String rotation = item.getRotation() != null ? item.getRotation() : "0";

        switch (item.getItemType()) {
            case "文本":
                return buildTextCommand(item, x, y, rotation);
            case "条形码":
                return buildBarcodeCommand(item, x, y, rotation);
            case "二维码":
                return buildQrcodeCommand(item, x, y, rotation);
            default:
                return null;
        }
    }

    /**
     * 构建文本命令
     * 格式：TEXT x,y,"font",rotation,xMulti,yMulti,"content"
     */
    private static String buildTextCommand(TSPLPrintCommandItem item, String x, String y, String rotation) {
        String font = item.getFont() != null ? item.getFont() : "TSS24.BF2";
        String xMulti = item.getXMulti() != null ? item.getXMulti() : "1";
        String yMulti = item.getYMulti() != null ? item.getYMulti() : "1";
        String content = escapeString(item.getContent());

        return String.format("TEXT %s,%s,\"%s\",%s,%s,%s,\"%s\"",
                x, y, font, rotation, xMulti, yMulti, content);
    }

    /**
     * 构建条形码命令
     * 格式：BARCODE x,y,"codeType",height,readable,wide,narrow,"content"
     */
    private static String buildBarcodeCommand(TSPLPrintCommandItem item, String x, String y, String rotation) {
        String codeType = item.getCodeType() != null ? item.getCodeType() : "128";
        String height = item.getCodeHeight() != null ? item.getCodeHeight() : "100";
        String readable = item.getReadable() != null ? item.getReadable() : "0";
        String wide = item.getCodeWide() != null ? item.getCodeWide() : "2";
        String narrow = item.getCodeNarrow() != null ? item.getCodeNarrow() : "2";
        String content = escapeString(item.getContent());

        return String.format("BARCODE %s,%s,\"%s\",%s,%s,%s,%s,%s,\"%s\"",
                x, y, codeType, height, readable, rotation, narrow, wide, content);
    }

    /**
     * 构建二维码命令
     * 格式：QRCODE x,y,eccLevel,cellWidth,rotation,mask,"content"
     */
    private static String buildQrcodeCommand(TSPLPrintCommandItem item, String x, String y, String rotation) {
        String eccLevel = item.getEccLevel() != null ? item.getEccLevel() : "L";
        String cellWidth = item.getCellWidth() != null ? item.getCellWidth() : "8";
        String content = escapeString(item.getContent());

        return String.format("QRCODE %s,%s,%s,%s,A,%s,\"%s\"",
                x, y, eccLevel, cellWidth, rotation, content);
    }

    /**
     * 转义字符串中的特殊字符
     */
    private static String escapeString(String str) {
        if (str == null) {
            return "";
        }
        // TSPL命令中，双引号需要转义为两个双引号
        return str.replace("\"", "\"\"");
    }
}