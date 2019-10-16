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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponsePasangBaru;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasangBaruFragment extends BottomSheetDialogFragment {
    InterfaceService interfaceService;
    private Unbinder unbinder;
    View view;

    @BindView(R.id.nama)
    EditText nama;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.noktp)
    EditText noktp;
    @BindView(R.id.alamatpersil)
    EditText alamatpersil;
    @BindView(R.id.nohandphone)
    EditText nohp;
    @BindView(R.id.fotoktp)
    ImageView fotoktp;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    String encodedFotoKTP;

    public PasangBaruFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);
        view = inflater.inflate(R.layout.activity_pasang_baru, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProfile();
    }

    private void initProfile(){
        nama.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_DEPAN, "")+" "+AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_BELAKANG, ""));
        alamat.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_ALAMAT, ""));
        nohp.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, ""));
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

    @OnClick(R.id.fotoktp)
    public void getFotoKtp(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PasangBaruFragment.this.startActivityForResult(intent, Constant.RESPONSE_CODE);
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
                encodedFotoKTP = encodedImage;
                Glide.with(getActivity().getApplicationContext())
                        .load(Tools.decodeString(encodedImage))
                        .into(fotoktp);
            }
        }
    }

    @OnClick(R.id.btnSimpan)
    public void simpanPasangBaru(){
        String token = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        Call<ResponsePasangBaru> call = interfaceService.postPasangBaru(
                nama.getText().toString(),
                alamat.getText().toString(),
                noktp.getText().toString(),
                nohp.getText().toString(),
                alamatpersil.getText().toString(),
                encodedFotoKTP,
                token
        );

        call.enqueue(new Callback<ResponsePasangBaru>() {
            @Override
            public void onResponse(Call<ResponsePasangBaru> call, Response<ResponsePasangBaru> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        Toast.makeText(getActivity().getApplicationContext(), "Pengajuan Pasang Baru Berhasil", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Pengajuan pasang Baru Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Pengajuan pasang Baru Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePasangBaru> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Pengajuan pasang Baru Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
