package com.example.compass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.compass.models.DistanceResponseModel;
import com.example.compass.viewModels.DistanceViewModel;
import com.example.compass.viewModels.ViewModelFactory;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    private DistanceViewModel distanceViewModel;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.distance_id);
        EditText editText = findViewById(R.id.editText_id);
        Button button = findViewById(R.id.button_id);

        distanceViewModel= new ViewModelProvider(this).get(DistanceViewModel.class);
//        distanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DistanceViewModel.class);
        requestPermission();
        distanceViewModel.getDistanceResponseModel().observe(this, new Observer<DistanceResponseModel>() {
            @Override
            public void onChanged(DistanceResponseModel distanceResponseModel) {
                textView.setText(distanceViewModel.showDestination(distanceResponseModel));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO zrobic obsluge blednych inputow
                distanceViewModel.setDestination(editText.getText().toString());
            }
        });
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            distanceViewModel.startPullLocation();

        } else {

            String[] a = new String[1];
            a[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            requestPermissions(a, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10 && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            distanceViewModel.startPullLocation();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
