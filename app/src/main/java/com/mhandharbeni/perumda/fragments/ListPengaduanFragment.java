package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.adapter.AdapterPengaduan;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseListPengaduan;
import com.mhandharbeni.perumda.room.entity.data.DataListPengaduan;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPengaduanFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;
    View view;
    private AdapterPengaduan adapterPengaduan;
    private LinearLayoutManager linearLayoutManager;
    private List<DataListPengaduan> listPengaduan = new ArrayList<>();

    @BindView(R.id.rvPengaduan)
    RecyclerView rvPengaduan;
    @BindView(R.id.loading)
    ProgressBar loading;

    public ListPengaduanFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);

        view = inflater.inflate(R.layout.activity_list_pengaduan, container, false);
        unbinder = ButterKnife.bind(this, view);

        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
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

    public void initAdapter(){
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        adapterPengaduan = new AdapterPengaduan(getActivity().getApplicationContext(), listPengaduan, getActivity());
        rvPengaduan.setAdapter(adapterPengaduan);
        rvPengaduan.setLayoutManager(linearLayoutManager);
    }

    private void initData(){
        loading.setVisibility(View.VISIBLE);
        rvPengaduan.setVisibility(View.GONE);

        String token = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");

        Call<ResponseListPengaduan> call = interfaceService.getListPengaduan(token, nohp);
        call.enqueue(new Callback<ResponseListPengaduan>() {
            @Override
            public void onResponse(Call<ResponseListPengaduan> call, Response<ResponseListPengaduan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        Log.d(Constant.TAG, "onResponse: "+response.body().getData().size());
                        rvPengaduan.setVisibility(View.VISIBLE);
//                        listPengaduan.addAll(response.body().getData());
                        adapterPengaduan.updateData(response.body().getData());
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }

                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListPengaduan> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
            }
        });

    }
}
