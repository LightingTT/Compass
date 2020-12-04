package com.example.compass.viewModels;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.compass.BuildConfig;
import com.example.compass.R;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Element;
import com.example.compass.repository.DistanceRepository;

public class DistanceViewModel extends ViewModel {

    private LiveData<DistanceResponseModel> distanceLiveData;
    private DistanceRepository distanceRepository;
    private Context context;
    String getSecretValue;

    public DistanceViewModel(Context context) {
        this.context = context;
        getSecretValue = context.getString(R.string.google_maps_api_key);
        distanceRepository = DistanceRepository.getInstance();
        distanceLiveData = distanceRepository.getDistanceResponseModel();
        distanceRepository.distanceResponseAPI("sikorki, szczecin", "wojska polskiego 100, szczecin", getSecretValue);
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceLiveData;
    }

    public String showDestination(DistanceResponseModel distanceResponseModel){
        Element element = distanceResponseModel.getRows().get(0).getElements().get(0);
        return element.getDistance().getText() + "\n" + element.getDuration().getText();
    }

    private Location getCurrentLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) return null;

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

    private String getCurrentCoordinates() {
        Location location = getCurrentLocation();
        if (location != null) {
            return location.getLatitude() + "," + location.getLongitude();
        }
        return "";
    }

}
