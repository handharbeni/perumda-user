package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.adapter.AdapterPasangBaru;
import com.mhandharbeni.perumda.adapter.AdapterPengaduan;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.ResponsePasangBaru;
import com.mhandharbeni.perumda.room.entity.data.DataPasangBaru;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPasangBaruFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;
    View view;
    private AdapterPasangBaru adapterPasangBaru;
    private LinearLayoutManager linearLayoutManager;
    private List<DataPasangBaru> listPasangBaru = new ArrayList<>();

    @BindView(R.id.rvPasangBaru)
    RecyclerView rvPasangBaru;
    @BindView(R.id.loading)
    ProgressBar loading;

    public ListPasangBaruFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);

        view = inflater.inflate(R.layout.activity_list_pasangbaru, container, false);
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

    private void initAdapter(){
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        adapterPasangBaru = new AdapterPasangBaru(getActivity().getApplicationContext(), listPasangBaru, getActivity());
        rvPasangBaru.setAdapter(adapterPasangBaru);
        rvPasangBaru.setLayoutManager(linearLayoutManager);
    }

    private void initData(){
        loading.setVisibility(View.VISIBLE);
        rvPasangBaru.setVisibility(View.GONE);
        interfaceService.getListPasangBaru(
                AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, ""),
                AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "")
        ).enqueue(new Callback<ResponsePasangBaru>() {
            @Override
            public void onResponse(Call<ResponsePasangBaru> call, Response<ResponsePasangBaru> response) {
                loading.setVisibility(View.GONE);
                rvPasangBaru.setVisibility(View.VISIBLE);
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        listPasangBaru.clear();
                        listPasangBaru.addAll(response.body().getData());
                        adapterPasangBaru.update(listPasangBaru);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePasangBaru> call, Throwable t) {
                loading.setVisibility(View.GONE);
                rvPasangBaru.setVisibility(View.VISIBLE);
            }
        });
    }

}
