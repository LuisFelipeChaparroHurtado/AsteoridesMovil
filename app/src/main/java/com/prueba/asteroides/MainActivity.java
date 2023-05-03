package com.prueba.asteroides;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.asteroides.adapter.AsteroidsAdapter;
import com.prueba.asteroides.db.AsteroidsDb;
import com.prueba.asteroides.entity.Asteroids;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView AsteroidsList;
    List<Asteroids> ListArrayAsteroids;
    Button download_dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download_dates = findViewById(R.id.download_dates);
        AsteroidsList = findViewById(R.id.asteroids_recycler);
        AsteroidsList.setLayoutManager(new LinearLayoutManager(this));
        list();
        download_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download_dates_asteroids();
            }
        });
    }

    private void list(){
        AsteroidsDb asteroidsDb = new AsteroidsDb(MainActivity.this);
        ListArrayAsteroids = new ArrayList<>();
        SharedPreferences preferencesId = getSharedPreferences("User", Context.MODE_PRIVATE);
        final String id = preferencesId.getString("id","Not Result");
        AsteroidsAdapter asteroidsAdapter = new AsteroidsAdapter(asteroidsDb.allDates(Integer.parseInt(id)));
        AsteroidsList.setAdapter(asteroidsAdapter);
    }
    private void download_dates_asteroids(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.nasa.gov/neo/rest/v1/feed?start_date=2023-04-26&end_date=2023-05-03&api_key=LsuR8AIYN4suEUh2q8Rf5k68vncvv8fbPw68uh9Z",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject asteroids = array.getJSONObject(i);

                                int id = asteroids.getInt("id");
                                String name = asteroids.getString("name");
                                String nasa = asteroids.getString("nasa_jpl_url");
                                String absolute = asteroids.getString("absolute_magnitude_h");

                                SharedPreferences preferencesId = getSharedPreferences("User", Context.MODE_PRIVATE);
                                final String idu = preferencesId.getString("id","Not Result");
                                AsteroidsDb asteroidsDb = new AsteroidsDb(MainActivity.this);
                                asteroidsDb.create(id, name, nasa, absolute, Integer.parseInt(idu));
                                ListArrayAsteroids.clear();
                                list();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder Bien = new AlertDialog.Builder(MainActivity.this);
                Bien.setMessage("Parece que hay problemas en la conexión a internet.")
                        .setCancelable(false)
                        .setNegativeButton("Salir", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog Titulo = Bien.create();
                Titulo.setTitle("Error de conexión");
                Titulo.show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}