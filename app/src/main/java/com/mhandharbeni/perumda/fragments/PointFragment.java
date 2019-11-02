package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponsePoint;
import com.mhandharbeni.perumda.room.entity.data.DataPoint;
import com.mhandharbeni.perumda.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;
    private View view;

    private DataPoint dataPoint;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.pointtitle)
    TextView pointTitle;
    @BindView(R.id.pointvalue)
    TextView pointValue;
    @BindView(R.id.pointstatus)
    TextView pointStatus;

    public PointFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);
        view = inflater.inflate(R.layout.activity_view_point, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData(){
        loading.setVisibility(View.VISIBLE);
        interfaceService.getPoint(
                AppPreferences
                        .getInstance(
                                getActivity().getApplicationContext()
                        ).getPref(Constant.TOKEN_PDAM, "")
        ).enqueue(new Callback<ResponsePoint>() {
            @Override
            public void onResponse(Call<ResponsePoint> call, Response<ResponsePoint> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        dataPoint = response.body().getData();
                        pointTitle.setText(dataPoint.getTitle());
                        pointValue.setText(String.valueOf(dataPoint.getPoint()));
                        pointStatus.setText(dataPoint.getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePoint> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });
    }
}
