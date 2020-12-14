package com.example.compass.repository;

import androidx.lifecycle.LiveData;

import com.example.compass.models.DistanceResponseModel;
import com.example.compass.requests.DistanceApiClient;

import io.reactivex.Single;

public class DistanceRepository {
    private static DistanceRepository instance;
    private DistanceApiClient distanceApiClient;

    public static DistanceRepository getInstance() {
        if (instance == null) {
            instance = new DistanceRepository();
        }
        return instance;
    }

    public DistanceRepository() {
        distanceApiClient = DistanceApiClient.getInstance();
    }

    public Single<DistanceResponseModel> distanceResponseAPI(String origins, String destinations, String key) {
        return distanceApiClient.distanceResponseAPI(origins, destinations, key);
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceApiClient.getDistanceResponseModel();
    }

}
