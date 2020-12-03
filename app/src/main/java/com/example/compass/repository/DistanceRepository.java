package com.example.compass.repository;

import androidx.lifecycle.LiveData;

import com.example.compass.models.DistanceResponseModel;
import com.example.compass.requests.DistanceApiClient;

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

    public void distanceResponseAPI(String origins, String destinations, String key) {
        distanceApiClient.distanceResponseAPI(origins, destinations, key);
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceApiClient.getDistanceResponseModel();
    }

}
