package com.example.compass.viewModels;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compass.BuildConfig;
import com.example.compass.MainActivity;
import com.example.compass.R;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Element;
import com.example.compass.repository.DistanceRepository;

import static android.content.ContentValues.TAG;
import static io.reactivex.internal.util.NotificationLite.getValue;

public class DistanceViewModel extends AndroidViewModel {


    private LiveData<DistanceResponseModel> distanceLiveData;
    private DistanceRepository distanceRepository;
    private Context context;
    String secretValue = "";
    String destinations = "Szczecin";

    public DistanceViewModel(Application context) {
        super(context);
        this.context = context;
        secretValue = context.getString(R.string.google_maps_api_key);
        distanceRepository = DistanceRepository.getInstance();
        distanceLiveData = distanceRepository.getDistanceResponseModel();

        //First argument is your origin, second is your destination(hardcoded for now). Third is API key.
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
