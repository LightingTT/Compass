package com.example.compass.viewModels;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;

public class MyLocationServiceClass implements MyLocationService, LifecycleObserver {

    Context context;
    PublishSubject<Location> stream = PublishSubject.create(); // Creates and returns a new PublishSubject.
    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            stream.onNext(location);
        }
    };

    private LocationManager locationManager;
    private boolean flag;

    public MyLocationServiceClass(LocationManager locationManager, boolean flag) {
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
        return stream.toFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public void start() {
        long minTimeMs = 10000;
        float minDistanceM = 5.5F;
        flag = true;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMs, minDistanceM, listener);

    }
}
