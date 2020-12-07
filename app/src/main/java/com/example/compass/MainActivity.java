package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compass.models.Distance;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.models.Duration;
import com.example.compass.models.Element;
import com.example.compass.models.Row;
import com.example.compass.repository.DistanceRepository;
import com.example.compass.viewModels.DistanceViewModel;
import com.example.compass.viewModels.ViewModelFactory;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private DistanceViewModel distanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.distance_id); //this might be databinded later

        distanceViewModel= new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(DistanceViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers()
    {
        distanceViewModel.getDistanceResponseModel().observe(this, new Observer<DistanceResponseModel>() {
            @Override
            public void onChanged(DistanceResponseModel distanceResponseModel) {
                textView.setText(distanceViewModel.showDestination(distanceResponseModel));
            }
        });
    }

}