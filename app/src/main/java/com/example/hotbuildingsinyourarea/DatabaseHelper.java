package com.example.hotbuildingsinyourarea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "places.db";
    public static final String table_name = "places";


    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getWaluty(){
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from places",null);
        return res;
    }
    public String opis(String where){
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM places WHERE place = '"+where+"'", null);
        res.moveToFirst();

        return res.getString(3);
    }
    public ArrayList<String> distance(double lat1, double lng1, Double radius) {
        ArrayList<String> lista = new ArrayList<String>();
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from places",null);
        while(res.moveToNext()) {
            double earthRadius = 6371;
            double lat2 = res.getDouble(1);
            double lng2 = res.getDouble(2);

            double dLat = Math.toRadians(lat2 - lat1);
            double dLng = Math.toRadians(lng2 - lng1);

            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);

            double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double dist = earthRadius * c;

            //Log.d("dist",Double.toString(dist));
            if (dist <= radius)
                lista.add(res.getString(0));
        }

        return lista;
    }
}
