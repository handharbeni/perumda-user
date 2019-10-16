package com.mhandharbeni.perumda.utils;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.Optional;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.idProfile)
    CircleImageView idProfile;
    private FusedLocationProviderClient fusedLocationClient;
    private BaseActivity instance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        initActionBar();
        reloadImageProfile();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Dexter.withActivity(this).withPermissions(Constant.listPermission).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(instance, location -> {
                    try {
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.LAST_LATITUDE, String.valueOf(location.getLatitude()));
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.LAST_LONGITUDE, String.valueOf(location.getLongitude()));
                    }catch (Exception e){}
                });
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Glide.with(getApplicationContext())
                    .load(
                            Tools.decodeString(
                                    AppPreferences.getInstance(getApplicationContext())
                                            .getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT)
                            )
                    )
                    .into(idProfile);
        }catch (Exception e){}
    }

    private void initActionBar() {
        try {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.action_bar);
        }catch (Exception e){}
    }
    public void hideProfile(){
        idProfile.setVisibility(View.GONE);
    }
    public void hideAppBar(){
        try {
            getSupportActionBar().hide();
        }catch (Exception e){}
    }
    public void initImageProfile(){
        Glide.with(getApplicationContext())
                .load(
                        Tools.decodeString(
                                AppPreferences.getInstance(getApplicationContext())
                                        .getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT)
                        )
                )
                .into(idProfile);
    }
    public void reloadImageProfile(){
        AppPreferences.getInstance(getApplicationContext())
                .getPref().registerOnSharedPreferenceChangeListener((encryptedPreferences, key) -> {
                    if (key.equalsIgnoreCase(Constant.PROFILE_IMAGE)){
                        Glide.with(getApplicationContext())
                                .load(
                                        Tools.decodeString(
                                                AppPreferences.getInstance(getApplicationContext())
                                                        .getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT)
                                        )
                                )
                                .into(idProfile);
                    }
                });
    }
}
