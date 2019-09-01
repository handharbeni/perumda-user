package com.mhandharbeni.perumda;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseRegister;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.CoreApplication;
import com.mhandharbeni.perumda.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    InterfaceService interfaceService;

    @BindView(R.id.namadepan)
    EditText namadepan;
    @BindView(R.id.namabelakang)
    EditText namabelakang;
    @BindView(R.id.nohandphone)
    EditText nohandphone;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interfaceService = Client.buildService(InterfaceService.class);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        hideAppBar();
    }

    @OnClick(R.id.btnRegister)
    public void register(){
        String device_id = CoreApplication.getDeviceId();
        String token_fcm = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_FCM, "");
        Call<ResponseRegister> call = interfaceService.register(
                namadepan.getText().toString(),
                namabelakang.getText().toString(),
                nohandphone.getText().toString(),
                alamat.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                device_id,
                token_fcm
        );
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        Toast.makeText(RegisterActivity.this, "Daftar Akun Berhasil. Silakan Login", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Daftar Akun Gagal, silakan coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Daftar Akun Gagal, silakan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Daftar Akun Gagal, silakan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
