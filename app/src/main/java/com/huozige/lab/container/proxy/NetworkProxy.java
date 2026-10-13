package com.huozige.lab.container.proxy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.XLog;
import com.huozige.lab.container.HACApplication;
import com.huozige.lab.container.platform.CallbackParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 让页面能够感知当前网络连接状态（弱网策略的基础能力）。
 * <p>
 * 设计约束（见 docs/weak-network-phase1-offline-form-download.md）：
 * 1. 仅提供网络"类型"与"是否计量"等信息，不做信号强度、不做质量探测；
 * 2. 不接管任何下载/上传，只回答"当前网络是什么状态"；
 * 3. 无需新增权限，仅使用已声明的 ACCESS_NETWORK_STATE。
 * <p>
 * 对外 API：
 * - network.getStatusAsync(ticket)：一次性查询当前状态
 * - network.watchAsync(ticket)：订阅网络变化，变化时用同一 ticket 反复回调
 * - network.unwatchAsync(ticket)：取消订阅
 */
public class NetworkProxy extends AbstractProxy {

    private static final String TRANSPORT_NONE = "none";
    private static final String TRANSPORT_WIFI = "wifi";
    private static final String TRANSPORT_CELLULAR = "cellular";
    private static final String TRANSPORT_ETHERNET = "ethernet";
    private static final String TRANSPORT_VPN = "vpn";
    private static final String TRANSPORT_OTHER = "other";

    private final Handler _mainHandler = new Handler(Looper.getMainLooper());
    private final List<String> _watchTickets = new ArrayList<>();

    private ConnectivityManager _connectivityManager;
    private ConnectivityManager.NetworkCallback _networkCallback;

    /** 最近一次派发出去的状态"签名"（不含时间戳），用于对抖动去重 */
    private String _lastStatusSignature;

    @Override
    public String getName() {
        return "network";
    }

    /**
     * 获取注册表中的网络代理实例，供原生内部逻辑（如下载兜底）判断网络状态。
     *
     * @return 实例；未注册时为 null
     */
    public static NetworkProxy getInstance() {
        for (AbstractProxy proxy : ProxyRegister.getInstance().getAllProxies()) {
            if (proxy instanceof NetworkProxy) {
                return (NetworkProxy) proxy;
            }
        }
        return null;
    }

    /**
     * 当前是否已连接网络。
     */
    public boolean isConnected() {
        return buildCurrentStatus().connected;
    }

    /**
     * 当前是否为计量网络（流量/热点）。连接不可用时返回 false。
     */
    public boolean isMetered() {
        return buildCurrentStatus().metered;
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        // 每次页面（Activity）重建都意味着 WebView 重新加载，旧的订阅 ticket 已失效，清空避免回调到已销毁的页面。
        synchronized (_watchTickets) {
            _watchTickets.clear();
        }
        _lastStatusSignature = null;
        ensureNetworkCallbackRegistered();
    }

    @Override
    public void onActivityDestroy() {
        unregisterNetworkCallback();
    }

    /**
     * 注册默认网络回调，幂等。回调统一派发到主线程。
     */
    private synchronized void ensureNetworkCallbackRegistered() {
        if (_networkCallback != null) {
            return;
        }

        if (_connectivityManager == null) {
            _connectivityManager = (ConnectivityManager) HACApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (_connectivityManager == null) {
            XLog.e("NetworkProxy：无法获取 ConnectivityManager，网络状态订阅不可用。");
            return;
        }

        _networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                dispatchStatusChanged();
            }

            @Override
            public void onLost(Network network) {
                dispatchStatusChanged();
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                dispatchStatusChanged();
            }
        };

        try {
            // 使用带 Handler 的重载，回调运行在主线程，且不依赖调用线程的 Looper。
            _connectivityManager.registerDefaultNetworkCallback(_networkCallback, _mainHandler);
        } catch (Exception e) {
            XLog.e("NetworkProxy：注册网络状态监听失败。\r\n%s", e);
            _networkCallback = null;
        }
    }

    private synchronized void unregisterNetworkCallback() {
        if (_connectivityManager != null && _networkCallback != null) {
            try {
                _connectivityManager.unregisterNetworkCallback(_networkCallback);
            } catch (Exception e) {
                XLog.e("NetworkProxy：注销网络状态监听失败。\r\n%s", e);
            }
        }
        _networkCallback = null;
        synchronized (_watchTickets) {
            _watchTickets.clear();
        }
    }

    /**
     * 网络状态发生变化时，向所有订阅者派发最新状态。相同状态会被去重，避免抖动刷屏。
     */
    private void dispatchStatusChanged() {
        NetworkStatus status = buildCurrentStatus();
        if (status.getSignature().equals(_lastStatusSignature)) {
            return;
        }
        _lastStatusSignature = status.getSignature();

        List<String> tickets;
        synchronized (_watchTickets) {
            if (_watchTickets.isEmpty()) {
                return;
            }
            tickets = new ArrayList<>(_watchTickets);
        }

        String payload = status.toJson();
        for (String ticket : tickets) {
            if (ticket == null || ticket.isEmpty()) {
                continue;
            }
            try {
                callback(ticket, CallbackParams.success(payload));
            } catch (Exception e) {
                XLog.e("NetworkProxy：派发网络状态回调失败。\r\n%s", e);
            }
        }
    }

    /**
     * 一次性查询当前网络状态。
     */
    @JavascriptInterface
    public void getStatusAsync(String ticket) {
        writeInfoLog("NetworkGetStatus");
        registryForFeatureUsageAnalyze("use_network_status_feature", "getStatus");

        callback(ticket, CallbackParams.success(buildCurrentStatus().toJson()));
    }

    /**
     * 订阅网络状态变化。注册后立即回一次当前状态。
     */
    @JavascriptInterface
    public void watchAsync(String ticket) {
        writeInfoLog("NetworkWatch");
        registryForFeatureUsageAnalyze("use_network_status_feature", "watch");

        if (ticket == null || ticket.isEmpty()) {
            writeErrorLog("NetworkProxy：watchAsync 的 ticket 为空，忽略。");
            return;
        }

        ensureNetworkCallbackRegistered();

        synchronized (_watchTickets) {
            if (!_watchTickets.contains(ticket)) {
                _watchTickets.add(ticket);
            }
        }

        // 立即回一次当前状态，避免页面在等待首次变化时处于"未知"。
        callback(ticket, CallbackParams.success(buildCurrentStatus().toJson()));
    }

    /**
     * 取消订阅。
     */
    @JavascriptInterface
    public void unwatchAsync(String ticket) {
        writeInfoLog("NetworkUnwatch");
        registryForFeatureUsageAnalyze("use_network_status_feature", "unwatch");

        if (ticket == null || ticket.isEmpty()) {
            return;
        }

        synchronized (_watchTickets) {
            _watchTickets.remove(ticket);
        }
        callback(ticket, CallbackParams.success("success"));
    }

    /**
     * 同步读取当前网络状态。
     */
    private NetworkStatus buildCurrentStatus() {
        boolean connected = false;
        String transport = TRANSPORT_NONE;
        boolean metered = false;
        boolean validated = false;

        try {
            if (_connectivityManager == null) {
                _connectivityManager = (ConnectivityManager) HACApplication.getInstance()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            if (_connectivityManager != null) {
                Network activeNetwork = _connectivityManager.getActiveNetwork();
                NetworkCapabilities capabilities = activeNetwork == null
                        ? null
                        : _connectivityManager.getNetworkCapabilities(activeNetwork);
                if (capabilities != null) {
                    connected = true;
                    transport = resolveTransport(capabilities);
                    metered = !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
                    validated = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                }
            }
        } catch (Exception e) {
            XLog.e("NetworkProxy：读取网络状态失败。\r\n%s", e);
        }

        return new NetworkStatus(connected, transport, metered, validated);
    }

    /**
     * 判定传输类型。优先级：VPN / 以太网 / WiFi / 蜂窝 / 其他。
     */
    private String resolveTransport(NetworkCapabilities capabilities) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            return TRANSPORT_VPN;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return TRANSPORT_ETHERNET;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return TRANSPORT_WIFI;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return TRANSPORT_CELLULAR;
        }
        return TRANSPORT_OTHER;
    }

    /**
     * 网络状态值对象。signature 用于去重，toJson 用于回传页面。
     */
    private static class NetworkStatus {

        private final boolean connected;
        private final String transport;
        private final boolean metered;
        private final boolean validated;

        NetworkStatus(boolean connected, String transport, boolean metered, boolean validated) {
            this.connected = connected;
            this.transport = transport;
            this.metered = metered;
            this.validated = validated;
        }

        String getSignature() {
            return connected + "|" + transport + "|" + metered + "|" + validated;
        }

        String toJson() {
            JSONObject json = new JSONObject();
            json.put("connected", connected);
            json.put("transport", transport);
            json.put("metered", metered);
            json.put("validated", validated);
            json.put("timestamp", System.currentTimeMillis());
            return json.toJSONString();
        }
    }
}
