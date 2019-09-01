package com.mhandharbeni.perumda.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.mhandharbeni.perumda.worker.LoketWorker;
import com.mhandharbeni.perumda.worker.UnitWorker;

import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;

public class CoreApplication extends Application {
    public static Context context;
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);
        startWorker();
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    private void startWorker(){
        PeriodicWorkRequest unitPeriodic = new PeriodicWorkRequest.Builder(UnitWorker.class, 20, TimeUnit.MINUTES).addTag("UnitWorker").build();
        PeriodicWorkRequest loketPeriodic = new PeriodicWorkRequest.Builder(LoketWorker.class, 20, TimeUnit.MINUTES).addTag("LoketWorker").build();
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork("UnitWorker", ExistingPeriodicWorkPolicy.KEEP, unitPeriodic);
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork("LoketWorker", ExistingPeriodicWorkPolicy.KEEP, loketPeriodic);

    }
}
