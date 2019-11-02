package com.mhandharbeni.perumda.fragments;

import android.content.Intent;
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
import com.mhandharbeni.perumda.adapter.AdapterBerita;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseBerita;
import com.mhandharbeni.perumda.room.entity.ResponsePesan;
import com.mhandharbeni.perumda.room.entity.data.DataBerita;
import com.mhandharbeni.perumda.sub_activity.DataBeritaActivity;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaFragment extends BottomSheetDialogFragment implements AdapterBerita.AdapterBeritaInterface {
    private InterfaceService interfaceService;
    private View view;
    private Unbinder unbinder;


    private List<DataBerita> listBerita = new ArrayList<>();
    private AdapterBerita adapterBerita;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.rvBerita)
    RecyclerView rvBerita;


    public BeritaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);
        view = inflater.inflate(R.layout.activity_list_berita, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAdapter();
        initData();
    }

    private void initAdapter(){
        adapterBerita = new AdapterBerita(getActivity().getApplicationContext(), listBerita, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvBerita.setLayoutManager(linearLayoutManager);
        rvBerita.setAdapter(adapterBerita);
    }

    private void initData(){
        loading.setVisibility(View.VISIBLE);

        interfaceService.getDataBerita(
                AppPreferences
                        .getInstance(
                                getActivity().getApplicationContext()
                        )
                        .getPref(Constant.TOKEN_PDAM, "")
        ).enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        listBerita.clear();
                        listBerita.addAll(response.body().getDataBerita());
                        adapterBerita.update(listBerita);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        try {
            unbinder.unbind();
        }catch (Exception ignored){}
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        try {
            unbinder.unbind();
        }catch (Exception ignored){}
        super.onDestroyView();
    }

    @Override
    public void onBeritaClick(DataBerita dataBerita) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DataBeritaActivity.class);
        intent.putExtra(Constant.SERIALIZABLE_BERITA, dataBerita);
        getActivity().startActivity(intent);
    }
}
