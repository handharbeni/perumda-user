package com.mhandharbeni.perumda.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.LoginActivity;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseUpdateProfile;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;


    @BindView(R.id.imageProfile)
    CircleImageView imageProfile;
    @BindView(R.id.nosal)
    EditText nosal;
    @BindView(R.id.namadepan)
    EditText namadepan;
    @BindView(R.id.namabelakang)
    EditText namabeklakang;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.nohandphone)
    EditText nohandphone;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnSimpanProfile)
    Button simpanProfile;
    @BindView(R.id.btnLogout)
    Button logout;

    View view;
    public ProfileFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);

        view = inflater.inflate(R.layout.activity_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProfile();
    }

    private void initProfile(){
        namadepan.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_DEPAN, "-"));
        namabeklakang.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_BELAKANG, "-"));
        email.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_EMAIL, "-"));
        nohandphone.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "-"));
        alamat.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_ALAMAT, "-"));
        nosal.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOSAL, ""));
        password.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_PASSWORD, ""));
        Glide.with(getActivity().getApplicationContext())
                .load(Tools.decodeString(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT)))
                .into(imageProfile);
    }

    @OnClick(R.id.btnSimpanProfile)
    public void simpanProfile(){
        String token = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        String image = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT);

        Call<ResponseUpdateProfile> call = interfaceService.updateProfile(
                token,
                nohp,
                nosal.getText().toString(),
                namadepan.getText().toString(),
                namabeklakang.getText().toString(),
                password.getText().toString(),
                alamat.getText().toString(),
                image
        );

        call.enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        AppPreferences.getInstance(getActivity().getApplicationContext()).setPref(Constant.PROFILE_NOSAL, nosal.getText().toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Update Profile Berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Update Profile Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnLogout)
    public void logout(){
        AppPreferences.getInstance(getActivity().getApplicationContext()).setPref(Constant.IS_LOGGEDIN, false);
        getActivity().startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.imageProfile)
    public void startCamera(){
        startCameraActivity();
    }

    private void startCameraActivity(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ProfileFragment.this.startActivityForResult(intent, Constant.RESPONSE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RESPONSE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 20, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                String encodedImage =  Tools.encodeImage(bitmap);
                AppPreferences.getInstance(getActivity().getApplicationContext()).setPref(Constant.PROFILE_IMAGE, encodedImage);
                Glide.with(getActivity().getApplicationContext())
                        .load(Tools.decodeString(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_IMAGE, Constant.DEFAULT_IMAGE_ACCOUNT)))
                        .into(imageProfile);
            }
        }
    }

    @Override
    public void onDestroyView() {
        try {
            unbinder.unbind();
        } catch (Exception e){}
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        try {
            unbinder.unbind();
        } catch (Exception e){}
        super.onDestroy();
    }
}
