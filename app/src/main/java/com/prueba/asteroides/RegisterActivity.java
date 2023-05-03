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

import com.prueba.asteroides.db.AsteroidsDb;
import com.prueba.asteroides.db.UsersDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;

public class RegisterActivity extends AppCompatActivity {

    EditText email, first_name, last_name, identification, encrypted_password, confirm_password;
    Button button_register;
    TextView button_session_to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button_session_to = findViewById(R.id.button_session_to);
        email = findViewById(R.id.edittex_email);
        first_name = findViewById(R.id.edittext_first_name);
        last_name = findViewById(R.id.edittext_last_name);
        identification = findViewById(R.id.edittext_identification);
        encrypted_password = findViewById(R.id.edittext_password);
        confirm_password = findViewById(R.id.edittext_confirm_password);

        button_register = findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    register_user();
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_session_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session_to();
            }
        });

    }


    private void session_to(){
        Intent intent = new Intent(RegisterActivity.this, SessionStartActivity.class);
        startActivity(intent);
        finish();
    }


    private void register_user(){

        if(encrypted_password.getText().toString().equals(confirm_password.getText().toString())){
            UsersDb usersDb = new UsersDb(RegisterActivity.this);

            long id = usersDb.create(email.getText().toString(), encrypted_password.getText().toString(),
                    first_name.getText().toString(), last_name.getText().toString(), identification.getText().toString(), actualDate(), actualDate());
            if(id>0){
                Toast.makeText(this, "Se registro el usuario", Toast.LENGTH_SHORT).show();
                /*SharedPreferences preferencesUserSession = getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editUser = preferencesUserSession.edit();
                editUser.putString("id", String.valueOf(id));
                editUser.commit();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();*/
            }else{
                Toast.makeText(this, "No se pudo", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegisterActivity.this, "La confirmación de contraseña y la contraseña no coinciden. Intentalo de nuevo", Toast.LENGTH_LONG).show();
        }
    }

    private String actualDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }
}