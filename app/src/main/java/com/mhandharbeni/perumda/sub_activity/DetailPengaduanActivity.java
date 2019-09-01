package com.mhandharbeni.perumda.sub_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.fragments.ProgressPengaduanFragment;
import com.mhandharbeni.perumda.room.entity.data.DataListPengaduan;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import br.com.felix.imagezoom.ImageZoom;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPengaduanActivity extends BaseActivity {
    DataListPengaduan dataListPengaduan;

    @BindView(R.id.pengaduanCover)
    ImageZoom pengaduanCover;
    @BindView(R.id.iconBack)
    ImageView iconBack;
    @BindView(R.id.nopengaduan)
    TextView nopengaduan;
    @BindView(R.id.progressPengaduan)
    FloatingActionButton progressPengaduan;

    @BindView(R.id.tvnopengaduan)
    TextView tvnopengaduan;
    @BindView(R.id.tvstatuspengaduan)
    TextView tvstatuspengaduan;
    @BindView(R.id.tvtglpengaduan)
    TextView tvtglpengaduan;
    @BindView(R.id.tvnosal)
    TextView tvnosal;
    @BindView(R.id.tvnama)
    TextView tvnama;
    @BindView(R.id.tvalamat)
    TextView tvalamat;
    @BindView(R.id.tvnamapelapor)
    TextView tvnamapelapor;
    @BindView(R.id.tvalamatpelapor)
    TextView tvalamatpelapor;
    @BindView(R.id.tvnohppelapor)
    TextView tvnohppelapor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_detail_pengaduan);
        ButterKnife.bind(this);
        hideAppBar();

        Intent i = getIntent();
        dataListPengaduan = (DataListPengaduan) i.getSerializableExtra(Constant.SERIALIZABLE_PENGADUAN);
        Toast.makeText(this, dataListPengaduan.getNopengaduan(), Toast.LENGTH_SHORT).show();

        initData();
    }

    private void initData(){
        Glide.with(this)
                .asBitmap()
                .load(Tools.decodeString(dataListPengaduan.getImage()))
                .error(R.drawable.water_wallpaper)
                .into(pengaduanCover);
        nopengaduan.setText(dataListPengaduan.getNopengaduan());
        tvnopengaduan.setText(dataListPengaduan.getNopengaduan());
        tvstatuspengaduan.setText(dataListPengaduan.getStatus());
        tvtglpengaduan.setText(dataListPengaduan.getTglpengaduan());
        tvnosal.setText(dataListPengaduan.getNosal());
        tvnama.setText(dataListPengaduan.getNama());
        tvalamat.setText(dataListPengaduan.getAlamat());
        tvnamapelapor.setText(dataListPengaduan.getNamapelapor());
        tvalamatpelapor.setText(dataListPengaduan.getAlamatpelapor());
        tvnohppelapor.setText(dataListPengaduan.getHppelapor());
    }

    @OnClick(R.id.progressPengaduan)
    public void showProgress(){
        ProgressPengaduanFragment progressPengaduanFragment = new ProgressPengaduanFragment(dataListPengaduan);
        progressPengaduanFragment.show(getSupportFragmentManager(), progressPengaduanFragment.getTag());
    }

    @OnClick(R.id.iconBack)
    public void backToMain(){
        finish();
    }
}
