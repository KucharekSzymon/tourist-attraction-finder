package com.example.hotbuildingsinyourarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView alt;
    private TextView longt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longt = findViewById(R.id.textView);
        alt = findViewById(R.id.textView2);

        LocationManager lok = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener Listner = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                location.getAltitude();
                location.getLongitude();
                Log.d("alt",location.getAltitude()+"");
                Log.d("long",location.getLongitude()+"");
                Log.d("acc",location.getAccuracy()+"");
                Log.d("prov",location.getProvider()+"");

                longt.setText("Longitude: "+location.getLongitude());
                alt.setText("Altitude: "+location.getAltitude());
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            },44);
            return;
        }
        lok.requestLocationUpdates(lok.GPS_PROVIDER, 0, 0, Listner);
    }
}