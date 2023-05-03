package com.prueba.asteroides;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.asteroides.db.UsersDb;

public class SessionStartActivity extends AppCompatActivity {

    EditText email, password;
    Button button_start;
    TextView button_register_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_start);
        button_register_to = findViewById(R.id.button_register_to);
        email = findViewById(R.id.edittex_email_i);
        password = findViewById(R.id.edittext_password_i);
        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify_user();
            }
        });
        validate_session();
        button_register_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_register();
            }
        });
    }

    private void to_register (){
        Intent intent = new Intent(SessionStartActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void verify_user(){
        UsersDb usersDb = new UsersDb(SessionStartActivity.this);
        boolean exist = usersDb.verify_user(email.getText().toString(), password.getText().toString());
        if(exist){
            //podemos iniciar sesion...
            String id = usersDb.id_user(email.getText().toString());
            SharedPreferences preferencesUserSession = getSharedPreferences("User", Context.MODE_PRIVATE);
            SharedPreferences.Editor editUser = preferencesUserSession.edit();
            editUser.putString("id", id);
            editUser.commit();
            Intent intent = new Intent(SessionStartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(SessionStartActivity.this, "Parece que los datos del usuario son incorrectos.", Toast.LENGTH_SHORT).show();
            Toast.makeText(SessionStartActivity.this, "Intentalo de nuevo", Toast.LENGTH_SHORT).show();
            password.setText("");
        }
    }
    private void validate_session(){
        SharedPreferences preferencesId = getSharedPreferences("User", Context.MODE_PRIVATE);
        final String id = preferencesId.getString("id","Not Result");

        if(!id.equals("Not Result")) {
            Intent intent = new Intent(SessionStartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
