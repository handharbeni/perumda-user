package com.mhandharbeni.perumda.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mhandharbeni.perumda.utils.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {
    private static String BASE_URL = Constant.BASE_URL;
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    private static StethoInterceptor logging = new StethoInterceptor();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static void changeApiBAseUrl(String newApiBaseUrl){
        BASE_URL = newApiBaseUrl;
        builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
    }
    public static <S> S createService(Class<S> serviceClass){
        if (!httpClient.interceptors().contains(logging)){
            httpClient.addNetworkInterceptor(logging);
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
