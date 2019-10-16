package com.mhandharbeni.perumda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mhandharbeni.perumda.network.ClientService;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.network.model.GeneralResponse;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.ResponseImageSlider;
import com.mhandharbeni.perumda.room.entity.ResponseLoket;
import com.mhandharbeni.perumda.room.entity.ResponseToken;
import com.mhandharbeni.perumda.room.entity.ResponseUnit;
import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;
import com.mhandharbeni.perumda.room.entity.data.DataLoket;
import com.mhandharbeni.perumda.room.entity.data.DataToken;
import com.mhandharbeni.perumda.room.entity.data.DataUnit;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.CoreApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    InterfaceService interfaceService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interfaceService = ClientService.createService(InterfaceService.class);
        setContentView(R.layout.activity_splash_screen);
        initToken();
    }

    private void initDataUnit(){
        String token = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        Call<ResponseUnit> call = interfaceService.getUnit(token, nohp);
        call.enqueue(new Callback<ResponseUnit>() {
            @Override
            public void onResponse(Call<ResponseUnit> call, Response<ResponseUnit> response) {

                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        List<DataUnit> listUnit = response.body().getData();
                        AppDb.getInstance(getApplicationContext()).unitInterfaceDao().insertAll(listUnit);
                        initDataLoket();
                    }else{
                        initDataSplash();
                    }
                }else{
                    initDataSplash();
                }
            }

            @Override
            public void onFailure(Call<ResponseUnit> call, Throwable t) {
                initDataSplash();
            }
        });
    }

    private void initDataLoket(){
        String token = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        Call<ResponseLoket> call = interfaceService.getLoket(token, nohp);
        call.enqueue(new Callback<ResponseLoket>() {
            @Override
            public void onResponse(Call<ResponseLoket> call, Response<ResponseLoket> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        List<DataLoket> listLoket = response.body().getData();
                        AppDb.getInstance(getApplicationContext()).loketInterfaceDao().insertAll(listLoket);
                        initDataSplash();
                    }else{
                        initDataSplash();
                    }
                }else{
                    initDataSplash();
                }
            }

            @Override
            public void onFailure(Call<ResponseLoket> call, Throwable t) {
                initDataSplash();
            }
        });
    }

    private void initDataSplash(){
        Call<ResponseImageSlider> call = interfaceService.getImageSlider("haha");
        call.enqueue(new Callback<ResponseImageSlider>() {
            @Override
            public void onResponse(Call<ResponseImageSlider> call, Response<ResponseImageSlider> response) {
                new Handler().postDelayed(() -> {
                    if (AppPreferences.getInstance(getApplicationContext()).getPref(Constant.IS_LOGGEDIN, false)){
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }else{
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }
                    finish();
                },2000);
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        List<DataImageSlider> listSlider = response.body().getData();
                        AppDb.getInstance(getApplicationContext()).imageSliderInterfaceDao().insertAll(listSlider);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseImageSlider> call, Throwable t) {
                new Handler().postDelayed(() -> {
                    if (AppPreferences.getInstance(getApplicationContext()).getPref(Constant.IS_LOGGEDIN, false)){
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }else{
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }
                    finish();
                },2000);
            }
        });
    }

    private void initToken(){
        if (AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "NULL").equalsIgnoreCase("NULL")){
            Call<ResponseToken> getToken = interfaceService.cToken(CoreApplication.getDeviceId());
            getToken.enqueue(new Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode().equalsIgnoreCase("200")){
                            AppPreferences.getInstance(getApplicationContext())
                                    .setPref(Constant.TOKEN_PDAM, response.body().getData().getToken());
                        }
                    }
                    initDataUnit();
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    initDataUnit();
                }
            });
        }else{
            initDataUnit();
        }
    }
}
