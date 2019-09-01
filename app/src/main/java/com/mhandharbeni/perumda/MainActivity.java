package com.mhandharbeni.perumda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mhandharbeni.perumda.fragments.ListPengaduanFragment;
import com.mhandharbeni.perumda.fragments.PasangBaruFragment;
import com.mhandharbeni.perumda.fragments.ProfileFragment;
import com.mhandharbeni.perumda.fragments.TagihanFragment;
import com.mhandharbeni.perumda.fragments.TambahPengaduanFragment;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
