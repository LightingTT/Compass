package com.example.compass.requests;

import com.example.compass.models.DistanceResponseModel;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("maps/api/distancematrix/json")
    Call<DistanceResponseModel> getDistanceModelMap(@QueryMap Map<String, String> parameters);

    @GET("maps/api/distancematrix/json")
    Call<DistanceResponseModel> getDistanceModelList(@Query("origins") String origins,
                                                  @Query("destinations") String destinations,
                                                  @Query("key") String key);
    @GET("maps/api/distancematrix/json")
    Single<DistanceResponseModel> getDistanceModelListRx(@Query("origins") String origins,
                                                         @Query("destinations") String destinations,
                                                         @Query("key") String key);


}
