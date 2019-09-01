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
import com.mhandharbeni.perumda.room.entity.ResponseUnit;
import com.mhandharbeni.perumda.room.entity.data.DataUnit;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitWorker extends Worker {
    InterfaceService interfaceService;

    public UnitWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(Constant.TAG, "doWork: UnitWorker");
        getDataUnit();
        return Result.success();
    }

    private void getDataUnit(){
        interfaceService = Client.buildService(InterfaceService.class);
        String token = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.TOKEN_PDAM, "");
        String nohp = AppPreferences.getInstance(getApplicationContext()).getPref(Constant.PROFILE_NOHP, "");
        Call<ResponseUnit> call = interfaceService.getUnit(token, nohp);
        call.enqueue(new Callback<ResponseUnit>() {
            @Override
            public void onResponse(Call<ResponseUnit> call, Response<ResponseUnit> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        List<DataUnit> listUnit = response.body().getData();
                        AppDb.getInstance(getApplicationContext()).unitInterfaceDao().insertAll(listUnit);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUnit> call, Throwable t) {
            }
        });
    }
}
