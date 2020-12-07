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
import com.example.compass.R;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Element;
import com.example.compass.repository.DistanceRepository;

public class DistanceViewModel extends AndroidViewModel {


    private LiveData<DistanceResponseModel> distanceLiveData;
    private DistanceRepository distanceRepository;
    private final Context context;

    public DistanceViewModel(Application context) {
        super(context);
        this.context = context;
        String secretValue = context.getString(R.string.google_maps_api_key);
        distanceRepository = DistanceRepository.getInstance();
        distanceLiveData = distanceRepository.getDistanceResponseModel();

        //First argument is your origin, second is your destination(hardcoded for now). Third is API key.
        String destinations = "Szczecin";
        distanceRepository.distanceResponseAPI(getCurrentCoordinate(), destinations, secretValue);
    }

    public LiveData<DistanceResponseModel> getDistanceResponseModel() {
        return distanceLiveData;
    }

    public String showDestination(DistanceResponseModel distanceResponseModel){
        Element element = distanceResponseModel.getRows().get(0).getElements().get(0);
        return element.getDistance().getText() + "\n" + element.getDuration().getText();
    }

    private Location getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context,  Manifest.permission.ACCESS_FINE_LOCATION);
            return null;
        }

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private String getCurrentCoordinate() {
        Location location = getCurrentLocation();
        if (location == null) return "";
        return location.getLatitude()+","+ location.getLongitude();
    }

    //TODO
    //requestLocationUpdates for periodic updates
    //locationListener
    //Edit text with button listener
}
