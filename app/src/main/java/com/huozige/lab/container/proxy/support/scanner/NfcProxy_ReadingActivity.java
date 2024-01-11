package com.huozige.lab.container.proxy.support.scanner;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import com.elvishew.xlog.XLog;
import android.view.View;
import android.widget.Toast;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;

import java.util.ArrayList;

/**
 *
 * 实现方法参考：<a href="https://blog.csdn.net/qq_37565670/article/details/119240737">...</a>
 */
public class NfcProxy_ReadingActivity extends BaseActivity {

    public final static int SCAN_STATUS_OK = 0;
    public final static int SCAN_STATUS_NA = -10;
    public final static int SCAN_STATUS_CANCEL = -1;
    public final static String BUNDLE_EXTRA_RESULT_TAG_ID = "tag_id";
    public final static String BUNDLE_EXTRA_RESULT_NDEF_MSG = "ndef_msg";
    public final static String LOG_TAG = "HAC_NfcReadingActivity";

    NfcAdapter mNfcAdapter;
    PendingIntent pIntent;

    /**
     * 定义按钮的事件，处理用户主动取消
     */
    View.OnClickListener _cancelButtonClick = view -> {

        // 设置状态后，关闭当前页面
        Intent intentR = new Intent();
        setResult(SCAN_STATUS_CANCEL, intentR);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_proxy_waiting);

        setTitle(getString(R.string.ui_title_nfc_activity));
        findViewById(R.id.button_cancel).setOnClickListener(_cancelButtonClick);

        initNfc();
    }

    /**
     * 页面恢复时，启动监听
     */
    @Override
    public void onResume() {

        super.onResume();

        if (mNfcAdapter != null) {
            //添加intent-filter
            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            IntentFilter tag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            IntentFilter[] filters = new IntentFilter[]{ndef, tag, tech};

            //添加 ACTION_TECH_DISCOVERED 情况下所能读取的NFC格式，这里列的比较全，实际我这里是没有用到的，因为测试的卡是NDEF的
            String[][] techList = new String[][]{
                    new String[]{
                            "android.nfc.tech.Ndef",
                            "android.nfc.tech.NfcA",
                            "android.nfc.tech.NfcB",
                            "android.nfc.tech.NfcF",
                            "android.nfc.tech.NfcV",
                            "android.nfc.tech.NdefFormatable",
                            "android.nfc.tech.MifareClassic",
                            "android.nfc.tech.MifareUltralight",
                            "android.nfc.tech.NfcBarcode"
                    }
            };
            mNfcAdapter.enableForegroundDispatch(this, pIntent, filters, techList);
            XLog.v("["+LOG_TAG+ "]NFC读取已启动");
        }
    }

    /**
     * 页面离开时，取消监听
     */
    @Override
    protected void onPause() {

        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
            XLog.v("["+LOG_TAG+ "]已停止读取NFC");
        }

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //这里必须setIntent，set NFC事件响应后的intent才能拿到数据
        setIntent(intent);

        XLog.v(LOG_TAG,"扫描到NFC标签，即将读取");
        Intent data = getIntent();
        Tag tag = data.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String tagInHex = bytesToHex(tag.getId());

        XLog.v(LOG_TAG,"NFC标签的TAG ID为：" + tagInHex);

        ArrayList<String> processedMessages = new ArrayList<>();
        Parcelable[] rawMessages = data.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMessages != null) {
            XLog.v(LOG_TAG,"扫描到NFC标签的NDEF消息，共："+ rawMessages.length+"条");

            for (Parcelable rawMessage : rawMessages) {
                NdefMessage msg = (NdefMessage) rawMessage;
                byte[] bytes = msg.toByteArray();
                processedMessages.add(new String(bytes));
            }
        }else{
            XLog.v(LOG_TAG,"NFC标签的NDEF消息为空");
        }

        // 将其打包发给调用者
        Intent res = new Intent();
        res.putExtra(BUNDLE_EXTRA_RESULT_TAG_ID, tagInHex);
        res.putExtra(BUNDLE_EXTRA_RESULT_NDEF_MSG, String.join( ",",processedMessages));
        setResult(SCAN_STATUS_OK, res);
        finish();

    }

    /**
     * 初始化NFC设备
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    private void initNfc(){

        XLog.v(LOG_TAG,"开始初始化NFC适配器");

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // 检查NFC是否可用
        if(mNfcAdapter == null){
            XLog.v(LOG_TAG,"NFC不可用：getDefaultAdapter返回为空");
            Toast.makeText(this,getString(R.string.ui_message_nfc_not_available),  Toast.LENGTH_SHORT).show();

            // 设置状态后，关闭当前页面
            Intent intentR = new Intent();
            setResult(SCAN_STATUS_NA, intentR);
            finish();
        }else{
            XLog.v(LOG_TAG,"NFC就绪，设置Intent响应");
            pIntent = PendingIntent.getActivity(this, 0,
                    //在Manifest里或者这里设置当前activity启动模式，否则每次向阳NFC事件，activity会重复创建
                    // 当然也要按照具体情况来，你设置成singleTask也不是不行，
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_UPDATE_CURRENT );
        }
    }

    /**
     * 2进制to 16进制
     * @param src ID的数组
     * @return 转换为16进制数字的字符串
     */
    private static String bytesToHex(byte[] src){
        StringBuilder sb = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String sTemp;
        for (byte b : src) {
            sTemp = Integer.toHexString(0xFF & b);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}