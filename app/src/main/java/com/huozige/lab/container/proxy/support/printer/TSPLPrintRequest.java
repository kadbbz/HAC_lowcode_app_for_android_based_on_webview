package com.huozige.lab.container.proxy.support.printer;

import java.util.List;

/**
 * TSPL打印请求实体类
 */
public class TSPLPrintRequest {

    /**
     * 打印机MAC地址
     */
    private String macAddress;

    /**
     * 打印宽度（毫米）
     */
    private String printerWidthMM;

    /**
     * 打印高度（毫米）
     */
    private String printerHeightMM;

    /**
     * 命令列表
     */
    private List<TSPLPrintCommandItem> commandList;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPrinterWidthMM() {
        return printerWidthMM;
    }

    public void setPrinterWidthMM(String printerWidthMM) {
        this.printerWidthMM = printerWidthMM;
    }

    public String getPrinterHeightMM() {
        return printerHeightMM;
    }

    public void setPrinterHeightMM(String printerHeightMM) {
        this.printerHeightMM = printerHeightMM;
    }

    public List<TSPLPrintCommandItem> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<TSPLPrintCommandItem> commandList) {
        this.commandList = commandList;
    }
}