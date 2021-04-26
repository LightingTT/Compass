package com.example.compass.viewModels;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;

import static android.content.ContentValues.TAG;

public class MyLocationServiceClass implements MyLocationService {

    //Observable.
    PublishProcessor<Location> stream = PublishProcessor.create();
    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            stream.onNext(location);
            Log.d(TAG, "onLocationChanged: stream.onNext(location) called");
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    private LocationManager locationManager;
    private boolean flag;
    private Context context;

    public MyLocationServiceClass(Context context, LocationManager locationManager, boolean flag) {
        this.context = context;
        this.locationManager = locationManager;
        this.flag = flag;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        if (flag) {
            start();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        locationManager.removeUpdates(listener);
    }

    /*
        Transform stream from PublishSubject to Flowable.
        BackpressureStrategy.LATEST will force the source to keep only the latest events, thus overwriting any previous values if the consumer can't keep up.
        Makes sense to do this with updating location.
     */
    @Override
    public Flowable<Location> getLocation() {
        return stream.hide();
    }

    @Override
    public void start() {
        long minTimeMs = 10000;
        float minDistanceM = 5.5F;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Log.d(TAG, "start(): requestLocationUpdates called.");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMs, minDistanceM, listener);

    }

    @Override
    public void updatePermission(boolean newState) {
        flag = newState;
    }




}
