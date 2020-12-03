package com.example.compass.requests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private final static String BASE_URL = "https://maps.googleapis.com/";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static ApiService apiService = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }
}