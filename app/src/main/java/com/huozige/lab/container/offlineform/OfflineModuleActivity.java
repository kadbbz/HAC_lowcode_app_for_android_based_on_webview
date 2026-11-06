package com.huozige.lab.container.offlineform;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.MainActivity;
import com.huozige.lab.container.R;

/**
 * 离线填报模块页面的公共基类，负责统一的自绘 Header。
 *
 * <p>Header 结构（{@code res/layout/offline_form_header.xml}，各页面布局顶部 include）：</p>
 * <ul>
 *     <li>左侧：模块首页（离线表单列表）显示「应用首页」入口，其余子页面显示「返回」按钮；</li>
 *     <li>中间：页面标题（通过 Activity#setTitle 联动，超长省略）；</li>
 *     <li>右侧：网络状态 tag——手机当前在线显示主题色「在线」，离线显示橙色「离线」。</li>
 * </ul>
 *
 * <p>由于模块使用 {@code OfflineFormTheme}（NoActionBar），系统 ActionBar 不再显示。</p>
 */
public abstract class OfflineModuleActivity extends BaseActivity {

    /** 网络状态变化后的延迟复核间隔：规避回调时机与系统状态更新之间的瞬时错位。 */
    private static final long NETWORK_STATUS_RECHECK_DELAY_MS = 500;

    private TextView _headerTitleView;
    private TextView _networkStatusView;
    // setTitle 可能早于 setContentView 被调用（各页面习惯先 setTitle 再 setContentView），先缓存标题。
    private CharSequence _pendingTitle;
    private ConnectivityManager _connectivityManager;
    private ConnectivityManager.NetworkCallback _networkCallback;
    private final Runnable _networkStatusRecheck = this::updateNetworkStatus;

    /**
     * 当前页面是否为离线填报模块首页（离线表单列表）。
     * <p>首页 Header 左侧显示「应用首页」入口，其余页面显示返回按钮。</p>
     */
    protected boolean isOfflineModuleHome() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 模块主题已是 NoActionBar，这里兜底：避免从其它主题页面跳转时残留系统 ActionBar。
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindHeader();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        _pendingTitle = title;
        if (_headerTitleView != null) {
            _headerTitleView.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(titleId == 0 ? null : getText(titleId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面重新显示时刷新一次网络状态（回调注册失败时也能保持可用）。
        updateNetworkStatus();
    }

    @Override
    protected void onDestroy() {
        if (_networkStatusView != null) {
            _networkStatusView.removeCallbacks(_networkStatusRecheck);
        }
        unregisterNetworkCallback();
        super.onDestroy();
    }

    private void bindHeader() {
        _headerTitleView = findViewById(R.id.text_header_title);
        ImageButton leftButton = findViewById(R.id.button_header_left);
        if (_headerTitleView == null || leftButton == null) {
            // 页面布局未接入统一定制 Header（例如全屏的图片预览 / 签名页），不做任何处理。
            return;
        }

        boolean homeEntry = isOfflineModuleHome();
        if (homeEntry) {
            // 「应用首页」使用设计提供的 logo 图标：不额外设置 tint，颜色由图标自身（含 drawable 内置 tint）决定，
            // 便于日后直接替换为彩色 logo 资源。
            leftButton.setImageResource(R.drawable.home_app_logo_24);
        } else {
            leftButton.setImageResource(R.drawable.ic_offline_back_24);
            leftButton.setImageTintList(ColorStateList.valueOf(getColor(R.color.offline_form_ink)));
        }
        leftButton.setContentDescription(getString(homeEntry
                ? R.string.offline_cd_header_home : R.string.offline_cd_header_back));
        leftButton.setOnClickListener(v -> {
            if (homeEntry) {
                openAppHome();
            } else {
                // 走系统返回分发：填报页的返回会先校验 / 落盘再退出，其它页面直接 finish。
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        if (_pendingTitle != null) {
            _headerTitleView.setText(_pendingTitle);
        }

        setupNetworkStatusTag();
        // tag 宽度随「无线网络 / 蜂窝网络 / 离线」变化，标题左右内边距按 tag 实际宽度收敛，保证标题居中且不压住 tag。
        _headerTitleView.post(this::refreshHeaderTitlePadding);
    }

    /**
     * 按右侧网络状态 tag 的实际宽度重设标题左右内边距。
     * <p>标题在 Header 中水平居中：左右内边距取「基础值」与「tag 宽度 + 右外边距 + 间距」的较大者，
     * 避免「无线网络 / 蜂窝网络」这类较长文案把标题压住。</p>
     */
    private void refreshHeaderTitlePadding() {
        if (_headerTitleView == null) {
            return;
        }
        int basePadding = getResources().getDimensionPixelSize(R.dimen.offline_form_header_title_padding);
        int padding = basePadding;
        if (_networkStatusView != null) {
            int tagWidth = _networkStatusView.getMeasuredWidth();
            int marginEnd = getResources().getDimensionPixelSize(R.dimen.offline_form_header_status_margin_end);
            int gap = getResources().getDimensionPixelSize(R.dimen.offline_form_header_status_gap);
            padding = Math.max(padding, tagWidth + marginEnd + gap);
        }
        _headerTitleView.setPadding(padding, 0, padding, 0);
    }

    /** 初始化 Header 右侧的网络状态 tag，并注册网络变化监听让其实时刷新。 */
    private void setupNetworkStatusTag() {
        _networkStatusView = findViewById(R.id.text_header_network_status);
        if (_networkStatusView == null) {
            return;
        }
        updateNetworkStatus();
        registerNetworkCallback();
    }

    private void registerNetworkCallback() {
        if (_networkCallback != null) {
            return;
        }
        _connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (_connectivityManager == null) {
            return;
        }

        ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() -> onNetworkEvent(resolveActiveNetworkCapabilities()));
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() -> onNetworkEvent(null));
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                runOnUiThread(() -> onNetworkEvent(networkCapabilities));
            }
        };

        try {
            _connectivityManager.registerDefaultNetworkCallback(callback);
            _networkCallback = callback;
        } catch (Exception ignored) {
            // 注册失败时退化为 onResume 时刷新网络状态。
        }
    }

    private void unregisterNetworkCallback() {
        if (_connectivityManager != null && _networkCallback != null) {
            try {
                _connectivityManager.unregisterNetworkCallback(_networkCallback);
            } catch (Exception ignored) {
                // 忽略注销失败。
            }
        }
        _networkCallback = null;
    }

    private void updateNetworkStatus() {
        applyNetworkStatus(resolveActiveNetworkCapabilities());
    }

    /** 取默认网络的 NetworkCapabilities；无可用默认网络或读取失败时返回 null。 */
    private NetworkCapabilities resolveActiveNetworkCapabilities() {
        try {
            if (_connectivityManager == null) {
                _connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            if (_connectivityManager == null) {
                return null;
            }
            Network activeNetwork = _connectivityManager.getActiveNetwork();
            return activeNetwork == null ? null : _connectivityManager.getNetworkCapabilities(activeNetwork);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 刷新 Header 右侧状态 tag：在线时按网络类型显示「无线网络」（Wi-Fi）/「蜂窝网络」（蜂窝），
     * 其它在线传输类型统一显示「在线」；离线（无可用默认网络）显示橙色「离线」。
     *
     * <p>在线判定只要求 {@code NET_CAPABILITY_INTERNET}，不要求 {@code NET_CAPABILITY_VALIDATED}
     * （公网校验在企业内网 / PDA 专网环境常为 false，会导致在线误判为离线）。</p>
     */
    private void applyNetworkStatus(NetworkCapabilities capabilities) {
        if (_networkStatusView == null) {
            return;
        }
        boolean online = capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        int textRes;
        if (!online) {
            textRes = R.string.offline_status_offline;
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            textRes = R.string.offline_status_wifi;
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            textRes = R.string.offline_status_cellular;
        } else {
            textRes = R.string.offline_status_online;
        }
        _networkStatusView.setText(textRes);
        _networkStatusView.setBackgroundTintList(ColorStateList.valueOf(getColor(
                online ? R.color.offline_form_status_online : R.color.offline_form_status_offline)));
        // tag 文案变化会改变宽度：下一帧重新计算标题内边距。
        _networkStatusView.post(this::refreshHeaderTitlePadding);
    }

    /**
     * 网络回调里先按事件直接更新一次（避免查询时机与系统状态更新错位），
     * 再延迟复核一次兜底。
     */
    private void onNetworkEvent(NetworkCapabilities capabilities) {
        applyNetworkStatus(capabilities);
        if (_networkStatusView != null) {
            _networkStatusView.removeCallbacks(_networkStatusRecheck);
            _networkStatusView.postDelayed(_networkStatusRecheck, NETWORK_STATUS_RECHECK_DELAY_MS);
        }
    }

    /** 回到 APP 首页（浏览器容器页），并关闭当前模块页面。 */
    private void openAppHome() {
        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
}
