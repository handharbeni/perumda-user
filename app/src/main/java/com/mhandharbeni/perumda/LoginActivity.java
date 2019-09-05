package com.mhandharbeni.perumda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.mhandharbeni.perumda.network.ClientService;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseLogin;
import com.mhandharbeni.perumda.room.entity.data.DataLogin;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    InterfaceService interfaceService;


    @BindView(R.id.nohandphone)
    EditText nohandphone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    AppCompatButton btn_login;
    @BindView(R.id.textregister)
    TextView textregister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        hideAppBar();
//        hideProfile();
    }
    @OnClick(R.id.btn_login)
    public void click(){
        interfaceService = ClientService.createService(InterfaceService.class);
        Call<ResponseLogin> login = interfaceService.login(nohandphone.getText().toString(), password.getText().toString());
        login.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        DataLogin dataLogin = response.body().getData();
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_EMAIL, dataLogin.getEmail());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_ID, dataLogin.getId());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_NAMA_DEPAN, dataLogin.getNamadepan());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_NAMA_BELAKANG, dataLogin.getNamabelakang());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_NOHP, nohandphone.getText().toString());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_PASSWORD, password.getText().toString());
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_IMAGE, dataLogin.getImage()!=null?dataLogin.getImage():Constant.DEFAULT_IMAGE_ACCOUNT);
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.PROFILE_ALAMAT, dataLogin.getAlamat()!=null?dataLogin.getAlamat():"");
                        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.IS_LOGGEDIN, true);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.textregister)
    public void gotoRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
