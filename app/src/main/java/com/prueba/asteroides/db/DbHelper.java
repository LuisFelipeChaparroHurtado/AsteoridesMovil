package com.prueba.asteroides.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String name_db = "asteroids.db";

    public static final String users ="users";
    public static final String asteroids ="asteroids";

    public DbHelper(@Nullable Context context) {
        super(context, name_db, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ users + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "encrypted_password TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "identification TEXT NOT NULL," +
                "created_at DATE NOT NULL," +
                "updated_at DATE NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE "+ asteroids + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "nasa_jpl_url TEXT NOT NULL," +
                "absolute_magnitude_h TEXT NOT NULL," +
                "user_id TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE "+users);
        sqLiteDatabase.execSQL(" DROP TABLE "+asteroids);

        onCreate(sqLiteDatabase);
    }
}
