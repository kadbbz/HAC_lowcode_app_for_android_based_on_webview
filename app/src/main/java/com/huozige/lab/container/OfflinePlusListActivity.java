package com.huozige.lab.container;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.OfflinePlusCardAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OfflinePlusCardAdapter adapter;
    private List<OfflinePlusListCardItem> cardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_list_activity);

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化数据
        cardItemList = generateSampleData();

        // 设置适配器
        adapter = new OfflinePlusCardAdapter(cardItemList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<OfflinePlusListCardItem> generateSampleData() {
        List<OfflinePlusListCardItem> items = new ArrayList<>();
        items.add(new OfflinePlusListCardItem(
                "Android开发入门",
                "学习Android开发的基础知识，包括Activity、Fragment、UI组件等",
                "未填写",
                "1"
        ));
        items.add(new OfflinePlusListCardItem(
                "RecyclerView使用指南",
                "详细介绍RecyclerView的使用方法和最佳实践",
                "2024-01-14",
                "2"
        ));
        items.add(new OfflinePlusListCardItem(
                "Material Design设计原则",
                "了解Material Design的核心设计理念和实现方式",
                "2024-01-13",
                "3"
        ));
        items.add(new OfflinePlusListCardItem(
                "网络请求与数据解析",
                "学习如何使用Retrofit和Gson进行网络请求",
                "2024-01-12",
                "4"
        ));
        items.add(new OfflinePlusListCardItem(
                "数据库存储方案",
                "Room数据库的使用和最佳实践",
                "2024-01-11",
                "5"
        ));
        return items;
    }
}
