package com.huozige.lab.container.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * JS桥的注册机制，可获取当前版本中所有桥的列表
 */
public class ProxyRegister {

    private static ProxyRegister __instance;

    private final List<AbstractProxy> _proxies;

    /**
     * 获取全部交互代理
     * @return 代理列表
     */
    public List<AbstractProxy> getAllProxies(){
        return _proxies;
    }

    /**
     * 获取注册器实例
     * @return 当前应用的注册器
     */
    public static ProxyRegister getInstance() {

        if(__instance == null){
            __instance = new ProxyRegister();
        }

        return __instance;
    }

    private ProxyRegister(){

        this._proxies = new ArrayList<>();

        this._proxies.add(new IndexProxy()); // 兼容活字格官方APP插件

        this._proxies.add(new PDAProxy()); // PDA激光扫码
        this._proxies.add(new NfcProxy()); // 读取NFC
        this._proxies.add(new GeoProxy()); // 获取地理位置

        this._proxies.add(new DeviceInfoProxy()); // 读取设备信息

        this._proxies.add(new AppProxy()); // 操作HAC壳子的功能
        this._proxies.add(new LocalKvProxy()); // 操作本地离线存储
        this._proxies.add(new PDFPreviewProxy()); // 预览PDF文件

        this._proxies.add(new DothanPrinterProxy()); // 操作德佟打印机

        this._proxies.add(new JPushProxy()); // 操作极光推送

        __instance = this;
    }
}
