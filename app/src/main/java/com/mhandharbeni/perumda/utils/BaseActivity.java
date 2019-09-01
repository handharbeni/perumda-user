package com.mhandharbeni.perumda.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.idProfile)
    CircleImageView idProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        reloadImageProfile();
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
