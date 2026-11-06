package com.huozige.lab.container.offlineform;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

public class ReselectSpinner extends AppCompatSpinner {
    private AdapterView.OnItemSelectedListener _listener;

    public ReselectSpinner(Context context) {
        super(context);
    }

    public ReselectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReselectSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
        _listener = listener;
    }

    @Override
    public void setSelection(int position) {
        boolean reselected = position == getSelectedItemPosition();
        super.setSelection(position);
        notifyReselected(position, reselected);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean reselected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        notifyReselected(position, reselected);
    }

    private void notifyReselected(int position, boolean reselected) {
        if (reselected && _listener != null) {
            View view = getSelectedView();
            _listener.onItemSelected(this, view, position, getSelectedItemId());
        }
    }
}
