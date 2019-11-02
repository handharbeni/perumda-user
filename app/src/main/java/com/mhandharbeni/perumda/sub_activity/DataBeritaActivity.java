package com.mhandharbeni.perumda.sub_activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataBerita;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataBeritaActivity extends BaseActivity {
    @BindView(R.id.beritacover)
    ImageView beritaCover;

    @BindView(R.id.beritacategory)
    TextView beritaCategory;

    @BindView(R.id.beritadate)
    TextView beritaDate;

    @BindView(R.id.beritatitle)
    TextView beritaTitle;

    @BindView(R.id.beritadesc)
    TextView beritaDesc;

    private DataBerita dataBerita;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initArguments();

        setContentView(R.layout.activity_detail_berita);

        ButterKnife.bind(this);
        hideAppBar();

        initView();
    }


    private void initArguments(){
        dataBerita = (DataBerita) getIntent().getSerializableExtra(Constant.SERIALIZABLE_BERITA);
    }

    private void initView(){
        Tools.DrawImage(getApplicationContext(), beritaCover, dataBerita.getImage());
        beritaCategory.setText(dataBerita.getCategory());
        beritaDate.setText(dataBerita.getDate());
        beritaTitle.setText(dataBerita.getTitle());
        beritaDesc.setText(dataBerita.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //To support reverse transition when user clicks the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
