package com.example.nepalaya.ApiService;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper<T> {
    private static ApiHelper helper;
    private static Retrofit retrofit;
    private final String BASE_URL = "https://nepalayacafe.com.np/";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private ApiHelper() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static ApiHelper getInstance() {
        if (helper == null)
            helper = new ApiHelper();
        return helper;
    }
    public T getService(Class className) {
        return (T) retrofit.create(className);
    }
}
