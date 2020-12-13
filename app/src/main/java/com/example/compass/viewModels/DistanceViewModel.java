package com.example.compass.viewModels;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compass.R;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Element;
import com.example.compass.repository.DistanceRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                    public Single<DistanceResponseModel> apply(Location location) throws Exception {
                        return distanceRepository.distanceResponseAPI(location.getLatitude() + "," + location.getLongitude(), getDestinations(), "AIzaSyA-HI74tKvtYIivojxBq24_qW81vTGIwHU");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<DistanceResponseModel>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(DistanceResponseModel distanceResponseModel) {
                distanceLiveData.postValue(distanceResponseModel);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void setDestination(String destination){
        this.destinations = destination;
    }

    private String getDestinations(){
        return destinations;
    }


    public String showDestination(DistanceResponseModel distanceResponseModel){
        Element element = distanceResponseModel.getRows().get(0).getElements().get(0);
        return element.getDistance().getText() + "\n" + element.getDuration().getText();
    }

//    private Location getCurrentLocation() {
//        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ContextCompat.checkSelfPermission(context,  Manifest.permission.ACCESS_FINE_LOCATION);
//            return null;
//        }
//
//        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//    }
//
//    private String getCurrentCoordinate() {
//        Location location = getCurrentLocation();
//        if (location == null) return "";
//        return location.getLatitude()+","+ location.getLongitude();
//    }

    //TODO
    //requestLocationUpdates for periodic updates
    //locationListener
    //Edit text with button listener
}
