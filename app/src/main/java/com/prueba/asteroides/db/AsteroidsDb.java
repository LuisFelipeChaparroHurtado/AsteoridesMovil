package com.prueba.asteroides.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.prueba.asteroides.entity.Asteroids;

import java.util.ArrayList;

public class AsteroidsDb extends DbHelper{
    Context context;
    public AsteroidsDb(@Nullable Context context) {

        super(context);
        this.context = context;
    }


    public  long create(int id, String name, String nasa_jpl_url, String absolute_magnitude_h, int user_id){
        long idv = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("nasa_jpl_url", nasa_jpl_url);
        values.put("absolute_magnitude_h", absolute_magnitude_h);
        values.put("user_id", user_id);
        idv = db.insert(asteroids, null, values);

        return idv;

    }

    public ArrayList<Asteroids> allDates(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Asteroids> listaRegistros = new ArrayList<>();
        Asteroids asteroids = null;
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + asteroids + " WHERE user_id = "+ id, null);
        if(cursor.moveToFirst()){
            do{
                asteroids = new Asteroids();
                asteroids.setId(cursor.getInt(0));
                asteroids.setName(cursor.getString(1));
                asteroids.setNasa_jpl_url(cursor.getString(2));
                asteroids.setAbsolute_magnitude_h(cursor.getString(3));
                listaRegistros.add(asteroids);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaRegistros;
    }
}
