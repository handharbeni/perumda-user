package com.mhandharbeni.perumda.sub_activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.adapter.AdapterSliderPromo;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;
import com.mhandharbeni.perumda.utils.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPromoActivity extends BaseActivity {
    AdapterSliderPromo adapterSliderPromo;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.rvPromo)
    RecyclerView rvPromo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        ButterKnife.bind(this);

        hideAppBar();
        initDataPromo();
    }

    private void initDataPromo(){
        List<DataImageSlider> listImageSlider = AppDb.getInstance(getApplicationContext()).imageSliderInterfaceDao().getAll();
        adapterSliderPromo = new AdapterSliderPromo(getApplicationContext(), listImageSlider);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPromo.setLayoutManager(linearLayoutManager);
        rvPromo.setAdapter(adapterSliderPromo);
    }
}
