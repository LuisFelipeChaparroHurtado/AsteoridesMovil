package com.prueba.asteroides.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class UsersDb extends DbHelper{
    Context context;
    public UsersDb(@Nullable Context context) {

        super(context);
        this.context = context;
    }


    public  long create(String email, String encrypted_password, String first_name, String last_name,
                        String identification, String created_at, String updated_at){

        long id = 0;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("encrypted_password", encrypted_password);
        values.put("first_name", first_name);
        values.put("last_name", last_name);
        values.put("identification", identification);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        id = db.insert(users, null, values);

        return id;

    }


    public boolean verify_user(String email, String password){
        boolean exist = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + users + " WHERE email = "+email+" AND encrypted_password = "+password, null);
        if(cursor.moveToFirst()){
            exist = true;
        }
        cursor.close();
        return exist;
    }

    public String id_user(String email){
        String id_user = "";
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + users + " WHERE email = "+email, null);
        if(cursor.moveToFirst()){
            do{
                id_user = String.valueOf(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return id_user;
    }

}
