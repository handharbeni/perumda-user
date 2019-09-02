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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.network.model.GeneralResponse;
import com.mhandharbeni.perumda.preferences.AppPreferences;
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

public class TambahPengaduanFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;
    View view;

    @BindView(R.id.nosal)
    EditText nosal;
    @BindView(R.id.nama)
    EditText nama;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.nohandphone)
    EditText nohandphone;
    @BindView(R.id.isiaduan)
    EditText isiaduan;
    @BindView(R.id.jnsaduan)
    ChipGroup jnsaduan;
    @BindView(R.id.foto)
    ImageView foto;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    String encodedFoto = "";

    public TambahPengaduanFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);
        view = inflater.inflate(R.layout.activity_tambah_pengaduan, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProfile();
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
    private void initProfile(){
        nosal.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOSAL, ""));
        nama.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_DEPAN, "")+" "+AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NAMA_BELAKANG, ""));
        alamat.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_ALAMAT, ""));
        nohandphone.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, ""));
    }

    @OnClick(R.id.btnSimpan)
    public void postPengaduan(){
        try {
            String lat = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.LAST_LATITUDE, "0.0");
            String lng = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.LAST_LONGITUDE, "0.0");
            Chip chip = view.findViewById(jnsaduan.getCheckedChipId());
            String token = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
            String nohandphone = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
            Call<GeneralResponse> call = interfaceService.postPengaduan(
                    token,
                    nohandphone,
                    nosal.getText().toString(),
                    nama.getText().toString(),
                    alamat.getText().toString(),
                    chip.getText().toString().equalsIgnoreCase("TEKNIS")?"TKNS":"ADM",
                    isiaduan.getText().toString(),
                    encodedFoto,
                    lat+";"+lng
            );
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode().equalsIgnoreCase("200")){
                            Toast.makeText(getActivity().getApplicationContext(), "Post Sukses", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Post Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Post Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Post Gagal", Toast.LENGTH_SHORT).show();
                }
            });
            Toast.makeText(getActivity().getApplicationContext(), chip.getText(), Toast.LENGTH_SHORT).show();chip.getText();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Cek Inputan Anda Kembali", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.foto)
    public void getFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        TambahPengaduanFragment.this.startActivityForResult(intent, Constant.RESPONSE_CODE);
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
                encodedFoto = encodedImage;
                Glide.with(getActivity().getApplicationContext())
                        .load(Tools.decodeString(encodedImage))
                        .into(foto);
            }
        }
    }
}
