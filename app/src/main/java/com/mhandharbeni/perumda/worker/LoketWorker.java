package com.mhandharbeni.perumda.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mhandharbeni.perumda.network.Client;
import com.mhandharbeni.perumda.network.InterfaceService;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.ResponseLoket;
import com.mhandharbeni.perumda.room.entity.data.DataLoket;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoketWorker extends Worker {
    InterfaceService interfaceService;

    public LoketWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        Log.d(Constant.TAG, "doWork: LoketWorker");
        interfaceService = Client.buildService(InterfaceService.class);
        getDataLoket();
        return Result.success();
    }

    private void getDataLoket(){
        String token = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        Call<ResponseLoket> call = interfaceService.getLoket(token, nohp);
        call.enqueue(new Callback<ResponseLoket>() {
            @Override
            public void onResponse(Call<ResponseLoket> call, Response<ResponseLoket> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        List<DataLoket> listLoket = response.body().getData();
                        AppDb.getInstance(getApplicationContext()).loketInterfaceDao().insertAll(listLoket);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoket> call, Throwable t) {
            }
        });
    }
}
