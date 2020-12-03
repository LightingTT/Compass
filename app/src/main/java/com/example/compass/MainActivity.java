package com.example.compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.compass.models.DistanceResponseModel;
import com.example.compass.requests.ApiService;
import com.example.compass.requests.ServiceGenerator;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final static String AUTH_KEY = "AIzaSyA-HI74tKvtYIivojxBq24_qW81vTGIwHU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getModel();

    }

    private Location getCurrentLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) return null;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

    private String getCurrentCoordinates() {

        Location location = getCurrentLocation();
        if (location == null) {
            Log.d(TAG, "getCurrentCoordinates: location is NULL");
        }
        return location.getLatitude() + "," + location.getLongitude();
    }

    private void getModel() {

        ApiService apiService = ServiceGenerator.getApiService();
        Call<DistanceResponseModel> responseCall = apiService.getDistanceModelList("szczecin", "gdynia", "AIzaSyCLCSMKae79KqCeI7lC9sG-ucTv5qk40MA");
        responseCall.enqueue(new Callback<DistanceResponseModel>() {
            @Override
            public void onResponse(Call<DistanceResponseModel> call, Response<DistanceResponseModel> response) {

                    Log.d(TAG, "onResponse: server response: " + response.errorBody());
                    int dupa = response.code();
                    Log.d(TAG, "http code: " + dupa);
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: " + response.body().toString());

                    }
                    DistanceResponseModel distanceResponseModel = response.body();
                    List<DistanceResponseModel> distanceResponseModels = new ArrayList<>();

                    Log.d(TAG, "onResponse: Retrived distance models " + distanceResponseModels.toString());

                }

            @Override
            public void onFailure(Call<DistanceResponseModel> call, Throwable t) {

            }
        });


    }
}