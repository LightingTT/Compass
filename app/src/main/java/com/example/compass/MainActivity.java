package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.TextView;
import com.example.compass.models.DistanceResponseModel;
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