package com.example.compass.viewModels;

import android.location.Location;

import androidx.lifecycle.LifecycleObserver;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MyLocationService extends LifecycleObserver{

    Flowable<Location> getLocation();

    void start();
    void updatePermission(boolean newState);

}

