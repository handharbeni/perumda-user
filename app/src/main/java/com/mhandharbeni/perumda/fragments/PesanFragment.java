package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.mhandharbeni.perumda.adapter.AdapterPesan;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponsePesan;
import com.mhandharbeni.perumda.room.entity.data.DataPesan;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanFragment extends BottomSheetDialogFragment implements AdapterPesan.AdapterInfoInterface {
    InterfaceService interfaceService;
    private Unbinder unbinder;
    private View view;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.listinfo)
    RecyclerView rvInfo;

    private AdapterPesan adapterPesan;
    private List<DataPesan> listInfo = new ArrayList<>();

    public PesanFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);
        view = inflater.inflate(R.layout.activity_list_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initAdapter(){
        adapterPesan = new AdapterPesan(getActivity().getApplicationContext(), listInfo, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvInfo.setLayoutManager(linearLayoutManager);
        rvInfo.setAdapter(adapterPesan);
    }

    private void initData(){
        loading.setVisibility(View.VISIBLE);
        Call<ResponsePesan> callInfo = interfaceService.getInfo(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, ""));
        callInfo.enqueue(new Callback<ResponsePesan>() {
            @Override
            public void onResponse(Call<ResponsePesan> call, Response<ResponsePesan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
//                        listInfo.clear();
//                        listInfo.addAll();
                        adapterPesan.update(response.body().getData());
                        rvInfo.setVisibility(View.VISIBLE);
                    }
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponsePesan> call, Throwable t) {
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
    public void onInfoClick(DataPesan dataPesan) {

    }
}
