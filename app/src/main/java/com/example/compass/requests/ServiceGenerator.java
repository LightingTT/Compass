package com.example.compass.requests;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private final static String BASE_URL = "https://maps.googleapis.com/";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = retrofitBuilder
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static ApiService apiService = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }
}