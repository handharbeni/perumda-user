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
import com.mhandharbeni.perumda.adapter.AdapterInfo;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseInfo;
import com.mhandharbeni.perumda.room.entity.data.DataInfo;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.fabric.sdk.android.Fabric.TAG;

public class InfoFragment extends BottomSheetDialogFragment implements AdapterInfo.AdapterInfoInterface {
    InterfaceService interfaceService;
    private Unbinder unbinder;
    private View view;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.listinfo)
    RecyclerView rvInfo;

    private AdapterInfo adapterInfo;
    private List<DataInfo> listInfo = new ArrayList<>();

    public InfoFragment() {
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
        adapterInfo = new AdapterInfo(getActivity().getApplicationContext(), listInfo, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvInfo.setLayoutManager(linearLayoutManager);
        rvInfo.setAdapter(adapterInfo);
    }

    private void initData(){
        for (int i=0; i<10;i++){
            DataInfo dataInfo = new DataInfo();
            dataInfo.setTitle(String.valueOf(i));
            dataInfo.setDescription(String.valueOf(i));
            dataInfo.setStatus(String.valueOf(i));
            dataInfo.setDate(String.valueOf(i));
            listInfo.add(dataInfo);
        }
        adapterInfo.update(listInfo);
//        loading.setVisibility(View.VISIBLE);
//        Call<ResponseInfo> callInfo = interfaceService.getInfo(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, ""));
//        callInfo.enqueue(new Callback<ResponseInfo>() {
//            @Override
//            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
//                if (response.isSuccessful()){
//                    if (response.body().getCode().equalsIgnoreCase("200")){
//                        listInfo.clear();
//                        listInfo.addAll(response.body().getData());
//                        adapterInfo.update(listInfo);
//                        rvInfo.setVisibility(View.VISIBLE);
//                        Log.d(TAG, "onResponse: "+String.valueOf(response.body().getData().size()));
//                    }
//                }
//                loading.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseInfo> call, Throwable t) {
//                loading.setVisibility(View.GONE);
//            }
//        });

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
    public void onInfoClick(DataInfo dataInfo) {

    }
}
