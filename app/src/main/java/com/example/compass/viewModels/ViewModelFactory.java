package com.example.compass.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private MyLocationService myLocationService;

    public ViewModelFactory(MyLocationService myLocationService) {
        this.myLocationService = myLocationService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DistanceViewModel(myLocationService);
    }
}
