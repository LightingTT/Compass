package com.example.compass.requests;

import com.example.compass.models.DistanceResponseModel;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("maps/api/distancematrix/json")
    Single<DistanceResponseModel> getDistanceModelListRx(@Query("origins") String origins,
                                                         @Query("destinations") String destinations,
                                                         @Query("key") String key);

}
