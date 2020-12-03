package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.viewModels.DistanceViewModel;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.distance_id);

        subscribeObservers();
    }

    private void subscribeObservers()
    {
        DistanceViewModel distanceViewModel = new DistanceViewModel(this);
        distanceViewModel.getDistanceResponseModel().observe(this, new Observer<DistanceResponseModel>() {
            @Override
            public void onChanged(DistanceResponseModel distanceResponseModel) {
                textView.setText(distanceViewModel.showDestination(distanceResponseModel));
            }
        });
    }
}