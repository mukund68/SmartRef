package com.example.android.smartrefrigerator.ApiHandler;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHandler{

    private static JsonPlaceHolderApi jsonPlaceHolderApi;

    private static JsonPlaceHolderApi initialise()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Log.d("BeforeRetrofit", "BeforeRetrofit");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.234:5000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("AfterRetrofit", "AfterRetrofit");
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        return jsonPlaceHolderApi;
    }

    public static JsonPlaceHolderApi getJsonPlaceHolderApi()
    {
        if (jsonPlaceHolderApi == null)
            jsonPlaceHolderApi = initialise();
        return jsonPlaceHolderApi;
    }

}
