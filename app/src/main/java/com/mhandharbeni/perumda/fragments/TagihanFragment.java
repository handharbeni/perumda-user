package com.mhandharbeni.perumda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.entity.ResponseSopp;
import com.mhandharbeni.perumda.room.entity.data.DataSopp;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanFragment extends BottomSheetDialogFragment {
    private InterfaceService interfaceService;
    private Unbinder unbinder;
    View view;

    @BindView(R.id.nosal)
    EditText nosal;
    @BindView(R.id.btnCariTagihan)
    Button btnCariTagihan;
    @BindView(R.id.txtNosal)
    TextView txtNosal;
    @BindView(R.id.txtNama)
    TextView txtNama;
    @BindView(R.id.txtAlamat)
    TextView txtAlamat;
    @BindView(R.id.txtKodeGolongan)
    TextView txtKodeGolongan;
    @BindView(R.id.txtGolongan)
    TextView txtGolongan;
    @BindView(R.id.txtPakai)
    TextView txtPakai;
    @BindView(R.id.txtSisa)
    TextView txtSisa;
    @BindView(R.id.txtHarga)
    TextView txtHarga;
    @BindView(R.id.txtAdm)
    TextView txtAdm;
    @BindView(R.id.txtAdmLayanan)
    TextView txtAdmLayanan;
    @BindView(R.id.txtNominal)
    TextView txtNominal;
    @BindView(R.id.txtStatusBayar)
    TextView txtStatusBayar;

    @BindView(R.id.cvResult)
    CardView cvResult;
    @BindView(R.id.loading)
    ProgressBar loading;

    public TagihanFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interfaceService = Client.buildService(InterfaceService.class);

        view = inflater.inflate(R.layout.activity_tagihan, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProfile();
    }

    private void initProfile(){
        nosal.setText(AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOSAL, ""));
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

    @OnClick(R.id.btnCariTagihan)
    public void cariTagihan(){
        if (Tools.validateInput(nosal)){
            loading.setVisibility(View.VISIBLE);
            cvResult.setVisibility(View.GONE);
            String sToken = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
            String sNohandphone = AppPreferences.getInstance(getActivity().getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
            String sNosal = nosal.getText().toString();
            interfaceService.getTagihan(sToken, sNohandphone, sNosal).enqueue(new Callback<ResponseSopp>() {
                @Override
                public void onResponse(Call<ResponseSopp> call, Response<ResponseSopp> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode().equalsIgnoreCase("200")){
                            DataSopp dataSopp = response.body().getData();
                            txtNosal.setText(dataSopp.getNosal());
                            txtNama.setText(dataSopp.getNama());
                            txtAlamat.setText(dataSopp.getAlamat());
                            txtKodeGolongan.setText(dataSopp.getKdgol());
                            txtGolongan.setText(dataSopp.getGolongan());
                            txtPakai.setText(dataSopp.getPakai());
                            txtSisa.setText(dataSopp.getSisa());
                            txtHarga.setText(dataSopp.getHarga());
                            txtAdm.setText(dataSopp.getAdm());
                            txtAdmLayanan.setText(dataSopp.getAdmlayan());
                            txtNominal.setText(dataSopp.getNominal());
                            txtStatusBayar.setText(dataSopp.getTg());

                            cvResult.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ResponseSopp> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    cvResult.setVisibility(View.VISIBLE);

                }
            });
        }
    }
}
