package com.example.hotbuildingsinyourarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.*;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView alt;
    private TextView longt;
    DatabaseHelper db;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ListView list;
    double a,b;
    Button show;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.editTextNumber);
        db = new DatabaseHelper(this);
        show = findViewById(R.id.button);
        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        list.setAdapter(adapter);


        longt = findViewById(R.id.textView);
        alt = findViewById(R.id.textView2);
        //showWaluty();
        show.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> x = new ArrayList<>();
                        x = db.distance(b,a,Double.parseDouble(input.getText().toString()));
                        arrayList.clear();
                        for(String asd : x)
                            arrayList.add(asd);
                        showWaluty();
                    }
                }
        );
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String text = item.toString();
                Log.d("",db.opis(text));
                String end = db.opis(text);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Description")
                        .setMessage(db.opis(text))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

            }
        });

        LocationManager lok = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener Listner = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                location.getLongitude();
                location.getLatitude();
                a = location.getLongitude();
                b = location.getLatitude();


                /*Log.d("long",location.getLongitude()+"");
                Log.d("lat",location.getLatitude()+"");*/

                longt.setText("Longitude: "+location.getLongitude());
                alt.setText("Latitude: "+location.getLatitude());

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
    public void showWaluty(){
        /*Cursor res = db.getWaluty();
        if(res.getCount() == 0){
            //pusta tabelka
            return;
        }
        arrayList.clear();
        while(res.moveToNext()){
            arrayList.add(res.getString(0));
        }*/
        adapter.notifyDataSetChanged();
    }
}
