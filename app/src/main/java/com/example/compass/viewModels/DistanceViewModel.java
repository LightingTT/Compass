package com.example.compass.viewModels;


import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Element;
import com.example.compass.repository.DistanceRepository;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class DistanceViewModel extends ViewModel {

    private final MutableLiveData<DistanceResponseModel> distanceLiveData = new MutableLiveData<>();
    private final DistanceRepository distanceRepository;
    private final MyLocationService myLocationService;
    private String destinations = "";

    public DistanceViewModel(MyLocationService myLocationService) {
        this.myLocationService = myLocationService;
        distanceRepository = DistanceRepository.getInstance();

        pullLocation();
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceLiveData;
    }

    public void startPullLocation() {
        myLocationService.start();
    }


    private void pullLocation(){

        myLocationService.getLocation()
                .flatMapSingle(new Function<Location, Single<DistanceResponseModel>>() {
                    @Override
                    public Single<DistanceResponseModel> apply(@NonNull Location location) throws Exception {
                        return distanceRepository.distanceResponseAPI(location.getLatitude() + "," + location.getLongitude(), getDestinations() , destinations)
                                .subscribeOn(Schedulers.io());
                    }
                })
                //When you subscribe to an Observable, always create a new instance
                .subscribe(new Subscriber<DistanceResponseModel>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe in DistanceViewModel called. ");
            }

            @Override
            public void onNext(DistanceResponseModel distanceResponseModel) {
                distanceLiveData.postValue(distanceResponseModel);
                Log.d(TAG, "onNext in DistanceViewModel called. ");
            }

            @Override
            public void onError(Throwable t) {
                Log.e("YOUR_APP_LOG_TAG", "I got an error:", t);

                Log.d(TAG, "ERROR in DistanceViewModel called. " + t.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete in DistanceViewModel called. ");
            }
        });
    }

    public void setDestination(String destination){
        this.destinations = destination;
    }

    private String getDestinations(){
        return destinations;
    }



    public void onPermissionGranted() {
        myLocationService.updatePermission(true);
    }


    //TODO
    //requestLocationUpdates for periodic updates
    //locationListener
    //Edit text with button listener
}
