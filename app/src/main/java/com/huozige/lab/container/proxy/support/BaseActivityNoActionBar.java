package com.huozige.lab.container.proxy.support;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivityNoActionBar extends AppCompatActivity {

    @Override
    protected void onResume(){
        super.onResume();
        ActionBar aBar = getSupportActionBar();
        if (aBar != null) aBar.hide();
    }
}
