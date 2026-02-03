package com.huozige.lab.container;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflinePlusParseJsonData;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.JsonFileHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_list_activity);

        // 初始化RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化数据
        List<OfflinePlusListCardItem> cardItemList = generateData();

        // 设置适配器
        OfflinePlusCardAdapter adapter = new OfflinePlusCardAdapter(cardItemList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<OfflinePlusListCardItem> generateData() {

        JSONObject json = JsonFileHelper.readJsonFromExternalStorage(this, JsonFileHelper.FILE_NAME_OFFLINE_LIST);

        return json == null ? new ArrayList<>() : OfflinePlusParseJsonData.parseJsonToCardList(json);

    }

}
