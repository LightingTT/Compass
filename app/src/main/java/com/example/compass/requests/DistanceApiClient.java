package com.example.compass.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.compass.models.DistanceResponseModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DistanceApiClient {
    private static DistanceApiClient instance;
    private MutableLiveData<DistanceResponseModel> distanceLiveData;

    private DistanceApiClient() {
        distanceLiveData = new MutableLiveData<>();
    }

    public static DistanceApiClient getInstance() {
        if (instance == null) {
            instance = new DistanceApiClient();
        }
        return instance;
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceLiveData;
    }

    public void distanceResponseAPI(String origins, String destinations, String key)
    {
        ServiceGenerator.getApiService()
                .getDistanceModelListRx(origins, destinations, key)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new SingleObserver<DistanceResponseModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(DistanceResponseModel value) {
                        distanceLiveData.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }
}
