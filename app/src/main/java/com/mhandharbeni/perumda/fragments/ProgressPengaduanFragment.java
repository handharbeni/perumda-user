package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
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
import com.mhandharbeni.perumda.adapter.AdapterProgressPengaduan;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseProgressPengaduan;
import com.mhandharbeni.perumda.room.entity.data.DataListPengaduan;
import com.mhandharbeni.perumda.room.entity.data.DataProgressPengaduan;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressPengaduanFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.listProgress)
    RecyclerView listProgress;

    DataListPengaduan dataListPengaduan;

    private LinearLayoutManager linearLayoutManager;
    private AdapterProgressPengaduan adapterProgressPengaduan;
    private List<DataProgressPengaduan> dataProgressPengaduans = new ArrayList<>();

    private View view;
    private Unbinder unbinder;

    public ProgressPengaduanFragment(DataListPengaduan dataListPengaduan){
        this.dataListPengaduan = dataListPengaduan;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.progress_list_pengaduan, container, false);
        interfaceService = Client.buildService(InterfaceService.class);
        unbinder = ButterKnife.bind(this, view);

//        dataListPengaduan = (DataListPengaduan) getActivity().getIntent().getSerializableExtra(Constant.SERIALIZABLE_PENGADUAN);

        initAdapter();
        initDataProgress();
//        initDataDummy();

        return view;
    }

    private void initAdapter(){
        adapterProgressPengaduan = new AdapterProgressPengaduan(dataProgressPengaduans, getActivity().getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        listProgress.setLayoutManager(linearLayoutManager);
        listProgress.setAdapter(adapterProgressPengaduan);
    }

    private void initDataDummy(){
        dataProgressPengaduans = new ArrayList<>();
        for (int i=0;i<10;i++){
            DataProgressPengaduan dumyProgress = new DataProgressPengaduan();
            dumyProgress.setNo(i);
            dumyProgress.setTindakan("TINDAKAN KE "+i);
            dataProgressPengaduans.add(dumyProgress);
        }

        Toast.makeText(getActivity().getApplicationContext(), "Size "+dataProgressPengaduans.size(), Toast.LENGTH_SHORT).show();

        adapterProgressPengaduan.updateData(dataProgressPengaduans);
    }

    private void initDataProgress(){
        loading.setVisibility(View.VISIBLE);
        listProgress.setVisibility(View.GONE);

        String token = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        String nopengaduan = dataListPengaduan.getNopengaduan();
        Call<ResponseProgressPengaduan> call = interfaceService.getProgressPengaduan(
                token,
                nohp,
                nopengaduan
        );
        call.enqueue(new Callback<ResponseProgressPengaduan>() {
            @Override
            public void onResponse(Call<ResponseProgressPengaduan> call, Response<ResponseProgressPengaduan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        adapterProgressPengaduan.updateData(response.body().getData());
                        listProgress.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseProgressPengaduan> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
            }
        });
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
