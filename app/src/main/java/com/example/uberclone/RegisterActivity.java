package com.example.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    //Creamos el sharedPreferences
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instanciamos shared preferences
        mPref = getApplicationContext().getSharedPreferences("typeUser" , MODE_PRIVATE);
        // Obtener Valor del Shared Preferences
        String selectedUser = mPref.getString("user" , "");
        Toast.makeText(this,"El valor seleccionado fue " + selectedUser, Toast.LENGTH_SHORT).show();
    }
}