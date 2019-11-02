package com.mhandharbeni.perumda.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mhandharbeni.perumda.utils.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = Constant.BASE_URL;
//    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient builderOkHttp = new OkHttpClient.Builder()
//            .addNetworkInterceptor(new StethoInterceptor())
//            .addInterceptor(interceptor)
            .build();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builderOkHttp)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    public static <T> T buildService(Class<T> type) {
        return retrofit.create(type);
    }
}
