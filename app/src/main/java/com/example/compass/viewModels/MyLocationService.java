package com.example.compass.viewModels;

import android.location.Location;
import io.reactivex.Flowable;

public interface MyLocationService {

    Flowable<Location> getLocation();

    void start();

}

