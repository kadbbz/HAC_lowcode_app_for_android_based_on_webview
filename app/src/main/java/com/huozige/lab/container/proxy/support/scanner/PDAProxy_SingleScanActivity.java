package com.huozige.lab.container.proxy.support.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.BaseActivityNoActionBar;
import com.huozige.lab.container.utilities.BroadcastDispatcher;
import com.huozige.lab.container.utilities.ConfigManager;

import java.util.UUID;

/**
 * 单次扫描：等待PDA扫码广播的页面，该页面支持用户自行取消
 */
public class PDAProxy_SingleScanActivity extends BaseActivityNoActionBar {

    public final static int SCAN_STATUS_OK = 0;
    public final static int SCAN_STATUS_CANCEL = -1;
    public final static String BUNDLE_EXTRA_RESULT = "result";
    private UUID _handlerId;

    /**
     * 定义按钮的事件，处理用户主动取消
     */
    View.OnClickListener _cancelButtonClick = view -> {

        // 设置状态后，关闭当前页面
        Intent intentR = new Intent();
        setResult(SCAN_STATUS_CANCEL, intentR);
        finish();
    };

    /**
     * 页面初始化和扫码配置初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化界面
        setContentView(R.layout.activity_single_scan);
        setTitle(getString(R.string.ui_title_scanner_activity));
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);
    }

    /**
     * 页面恢复时，启动监听
     */
    @Override
    public void onResume() {

        super.onResume();

        BroadcastDispatcher.BroadcastHandler handler = new BroadcastDispatcher.BroadcastHandler() {
            @Override
            public String getAction() {
                return ConfigManager.getInstance().getScanAction();
            }

            @Override
            public boolean handle(@Nullable Bundle extras) {

                XLog.v("收到单次扫码结果的广播");

                if (extras != null) {
                    // 按照厂商的文档，从广播中获取扫码结果
                    String result = extras.getString(ConfigManager.getInstance().getScanExtra());

                    if(result == null) result = "";

                    // 需要剔除每次扫码结果后的回车按键
                    result = result.replace("\n", "");
                    result = result.replace("\r", "");

                    XLog.v("扫码结果是：" + result);

                    // 将其打包发给调用者
                    Intent res = new Intent();
                    res.putExtra(BUNDLE_EXTRA_RESULT, result);
                    PDAProxy_SingleScanActivity.this.setResult(SCAN_STATUS_OK, res);

                    // 关闭当前页面
                    PDAProxy_SingleScanActivity.this.finish();
                }

                return false;
            }
        };

        _handlerId = handler.getID();
        BroadcastDispatcher.getInstance().register(handler);

        XLog.v("激光头扫码结果广播已注册（模态）。广播：" + ConfigManager.getInstance().getScanAction() + "，键值：" + ConfigManager.getInstance().getScanExtra());

    }

    /**
     * 页面离开时，取消监听
     */
    @Override
    protected void onPause() {
        // 取消监听
        BroadcastDispatcher.getInstance().unregister(_handlerId);

        XLog.v("已取消注册扫码结果广播");

        super.onPause();
    }

}