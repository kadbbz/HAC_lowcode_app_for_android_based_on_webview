package com.huozige.lab.container;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;
import com.huozige.lab.container.utilities.JsonFileHelper;

import org.json.JSONArray;
import org.json.JSONException;
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
        List<OfflinePlusListCardItem> cardItemList = generateSampleData();

        // 设置适配器
        OfflinePlusCardAdapter adapter = new OfflinePlusCardAdapter(cardItemList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<OfflinePlusListCardItem> generateSampleData() {

        JsonFileHelper.writeJsonToExternalStorage(this, JsonFileHelper.FILE_NAME_OFFLINE_LIST, createSampleJson());

        JSONObject json = JsonFileHelper.readJsonFromExternalStorage(this, JsonFileHelper.FILE_NAME_OFFLINE_LIST);

        return json == null ? new ArrayList<>() : parseJsonData(json);

    }

    // 创建示例JSON数据
    private JSONObject createSampleJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONObject item = new JSONObject();
            item.put("title", "张三");
            item.put("description", "一些描述");
            item.put("status", "未完成");
            item.put("patternId", "1");

            JSONObject item2 = new JSONObject();
            item2.put("title", "李四");
            item2.put("description", "一些描述");
            item2.put("status", "未完成");
            item2.put("patternId", "2");

            // JSONArray
            JSONArray project = new JSONArray();
            project.put(item);
            project.put(item2);
            jsonObject.put("project", project);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return jsonObject;
    }

    // 解析JSON数据
    public List<OfflinePlusListCardItem> parseJsonData(JSONObject jsonObject) {

        List<OfflinePlusListCardItem> items = new ArrayList<>();

        try {
            JSONArray project = jsonObject.getJSONArray("project");

            for (int i = 0; i < project.length(); i++) {
                JSONObject item = project.getJSONObject(i);
                items.add(new OfflinePlusListCardItem(
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("status"),
                        item.getString("patternId")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }


}
