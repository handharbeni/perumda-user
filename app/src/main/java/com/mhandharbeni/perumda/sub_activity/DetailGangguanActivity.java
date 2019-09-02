package com.mhandharbeni.perumda.sub_activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.utils.BaseActivity;

public class DetailGangguanActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_gangguan);
        hideAppBar();
    }
}
