package com.mhandharbeni.perumda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mhandharbeni.perumda.adapter.AdapterBerita;
import com.mhandharbeni.perumda.adapter.AdapterSlider;
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
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements AdapterBerita.ListenerGangguan {
    InterfaceService interfaceService;

    @BindView(R.id.btnInfoTagihan)
    ImageView btnInfoTagihan;

    @BindView(R.id.btnListPengaduan)
    ImageView btnListPengaduan;

    @BindView(R.id.btnTambahPengaduan)
    ImageView btnTambahPengaduan;

    @BindView(R.id.btnLoket)
    ImageView getLoket;

    @BindView(R.id.idProfile)
    CircleImageView idProfile;

    @BindView(R.id.rvGangguan)
    RecyclerView rvGangguan;

    @BindView(R.id.imageSlider)
    SliderView imageSlider;

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

    @OnClick(R.id.btnLoket)
    public void showLoket(){
        startActivity(new Intent(this, MapLoketActivity.class));
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
        AdapterSlider adapterSlider = new AdapterSlider(getApplicationContext(), listImageSlider);
        imageSlider.setSliderAdapter(adapterSlider);
        imageSlider.setScrollTimeInSec(6);
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.startAutoCycle();
    }

    @Override
    public void onGangguanClick(DataGangguan dataGangguan) {
//        startActivity(new Intent(this, DetailGangguanActivity.class));
    }
}
