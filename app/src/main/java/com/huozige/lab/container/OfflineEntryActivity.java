package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OfflineEntryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ui_menu_offlineplus);
        setContentView(R.layout.offline_entry_activity);

        View historyEntry = findViewById(R.id.cmdOfflineHistory);
        View newEntry = findViewById(R.id.cmdOfflineNew);

        historyEntry.setOnClickListener(v -> animateEntryClick(v, () -> startActivity(new Intent(this, OfflinePlusListActivity.class))));
        newEntry.setOnClickListener(v -> animateEntryClick(v, () -> {
            // 第一阶段只做入口占位；后续进入选择表单定义并新建填报。
        }));
    }

    private void animateEntryClick(View view, Runnable action) {
        view.animate()
                .scaleX(0.98f)
                .scaleY(0.98f)
                .setDuration(80)
                .withEndAction(() -> view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(80)
                        .withEndAction(action)
                        .start())
                .start();
    }
}
