package com.mhandharbeni.perumda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mhandharbeni.perumda.adapter.AdapterBerita;
import com.mhandharbeni.perumda.adapter.AdapterSlider;
import com.mhandharbeni.perumda.fragments.PesanFragment;
import com.mhandharbeni.perumda.fragments.ListPengaduanFragment;
import com.mhandharbeni.perumda.fragments.PasangBaruFragment;
import com.mhandharbeni.perumda.fragments.ProfileFragment;
import com.mhandharbeni.perumda.fragments.TagihanFragment;
import com.mhandharbeni.perumda.fragments.TambahPengaduanFragment;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.ResponseGangguan;
import com.mhandharbeni.perumda.room.entity.data.DataGangguan;
import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;
import com.mhandharbeni.perumda.sub_activity.DetailGangguanActivity;
import com.mhandharbeni.perumda.sub_activity.DetailPromoActivity;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements AdapterBerita.ListenerGangguan, AdapterSlider.ListenerSlider {

    public static final String EXTRA_GANGGUAN_ITEM = "gangguan_item";
    public static final String EXTRA_GANGGUAN_IMAGE_TRANSITION_NAME = "gangguan_image_transition_name";
    public static final String EXTRA_GANGGUAN_TITLE_TRANSITION_NAME = "gangguan_title_transition_name";
    InterfaceService interfaceService;

    @BindView(R.id.btnInfoTagihan)
    ImageView btnInfoTagihan;

    @BindView(R.id.btnListPengaduan)
    ImageView btnListPengaduan;

    @BindView(R.id.btnTambahPengaduan)
    ImageView btnTambahPengaduan;

    @BindView(R.id.btnLoket)
    ImageView getLoket;

    @BindView(R.id.btnPasangBaru)
    ImageView btnPasangBaru;

    @BindView(R.id.btnBerita)
    ImageView btnBerita;

    @BindView(R.id.btnPoint)
    ImageView btnPoint;

    @BindView(R.id.idProfile)
    CircleImageView idProfile;

    @BindView(R.id.rvGangguan)
    RecyclerView rvGangguan;

    @BindView(R.id.imageSlider)
    SliderView imageSlider;

    @BindView(R.id.btnPesan)
    ImageView btnPesan;

    AdapterBerita adapterBerita;
    List<DataGangguan> listGangguan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interfaceService = Client.buildService(InterfaceService.class);
        setContentView(R.layout.activity_main_collapsing);
        ButterKnife.bind(this);
        hideAppBar();
        Dexter.withActivity(this).withPermissions(Constant.listPermission).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
        initDataGangguan();
        initSlider();
        initIcon();
    }

    private void initIcon(){
        Tools.DrawImage(getApplicationContext(), btnInfoTagihan, Constant.ICON.PEMBAYARAN.getUrl());
        Tools.DrawImage(getApplicationContext(), btnListPengaduan, Constant.ICON.STATUS_PENGADUAN.getUrl());
        Tools.DrawImage(getApplicationContext(), btnTambahPengaduan, Constant.ICON.PENGADUAN.getUrl());
        Tools.DrawImage(getApplicationContext(), getLoket, Constant.ICON.LOKET.getUrl());
        Tools.DrawImage(getApplicationContext(), btnPasangBaru, Constant.ICON.PASBA.getUrl());
        Tools.DrawImage(getApplicationContext(), btnBerita, Constant.ICON.BERITA.getUrl());
        Tools.DrawImage(getApplicationContext(), btnPoint, Constant.ICON.POINT.getUrl());
        Tools.DrawImage(getApplicationContext(), btnPesan, Constant.ICON.PESAN.getUrl());
    }

    @OnClick(R.id.idProfile)
    public void profileClick(){
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.show(getSupportFragmentManager(), profileFragment.getTag());
    }

    @OnClick(R.id.btnInfoTagihan)
    public void infoTagihanClick(){
        TagihanFragment tagihanFragment = new TagihanFragment();
        tagihanFragment.show(getSupportFragmentManager(), tagihanFragment.getTag());
    }

    @OnClick(R.id.btnListPengaduan)
    public void listPengaduanClick(){
        ListPengaduanFragment listPengaduanFragment = new ListPengaduanFragment();
        listPengaduanFragment.show(getSupportFragmentManager(), listPengaduanFragment.getTag());
    }

    @OnClick(R.id.btnTambahPengaduan)
    public void tambahPengaduan(){
        TambahPengaduanFragment tambahPengaduanFragment = new TambahPengaduanFragment();
        tambahPengaduanFragment.show(getSupportFragmentManager(), tambahPengaduanFragment.getTag());
    }

    @OnClick(R.id.btnPasangBaru)
    public void pasangBaru(){
        PasangBaruFragment pasangBaruFragment = new PasangBaruFragment();
        pasangBaruFragment.show(getSupportFragmentManager(), pasangBaruFragment.getTag());
    }

    @OnClick(R.id.btnPesan)
    public void listPasangBaru(){
//        ListPasangBaruFragment listPasangBaruFragment = new ListPasangBaruFragment();
//        listPasangBaruFragment.show(getSupportFragmentManager(), listPasangBaruFragment.getTag());
        PesanFragment pesanFragment = new PesanFragment();
        pesanFragment.show(getSupportFragmentManager(), pesanFragment.getTag());
    }

    @OnClick(R.id.btnLoket)
    public void showLoket(){
        startActivity(new Intent(this, MapLoketActivity.class));
    }

    @OnClick(R.id.imageSlider)
    public void showAllPromo(){
        startActivity(new Intent(this, DetailPromoActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initDataGangguan(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapterBerita = new AdapterBerita(listGangguan, getApplicationContext(), this);
        rvGangguan.setLayoutManager(linearLayoutManager);
        rvGangguan.setAdapter(adapterBerita);

        String token = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");

        Call<ResponseGangguan> call = interfaceService.getGangguan(token);
        call.enqueue(new Callback<ResponseGangguan>() {
            @Override
            public void onResponse(Call<ResponseGangguan> call, Response<ResponseGangguan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        adapterBerita.updateData(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGangguan> call, Throwable t) {

            }
        });
    }

    private void initSlider(){
        List<DataImageSlider> listImageSlider = new ArrayList<>(AppDb.getInstance(getApplicationContext()).imageSliderInterfaceDao().getAll());
        AdapterSlider adapterSlider = new AdapterSlider(getApplicationContext(), listImageSlider, this);
        imageSlider.setSliderAdapter(adapterSlider);
        imageSlider.setScrollTimeInSec(6);
        imageSlider.setIndicatorUnselectedColor(Color.RED);
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.startAutoCycle();
    }

    @Override
    public void onGangguanClick(int position, DataGangguan dataGangguan, ImageView imageView) {
        Intent intent = new Intent(this, DetailGangguanActivity.class);
        intent.putExtra(EXTRA_GANGGUAN_ITEM, dataGangguan);
        intent.putExtra(EXTRA_GANGGUAN_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSliderClick() {
        startActivity(new Intent(this, DetailPromoActivity.class));
    }
}
