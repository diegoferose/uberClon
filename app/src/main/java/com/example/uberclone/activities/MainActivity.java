package com.example.uberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uberclone.R;
import com.example.uberclone.activities.client.MapClientActivity;
import com.example.uberclone.activities.driver.MapDriverActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button mButtonDriver;
    Button mButtonClient;

    //Guardar Variable conductor cliente
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instaciamos la variable a guardar
        mPref = getApplicationContext().getSharedPreferences("typeUser" , MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();

        mButtonDriver = findViewById(R.id.btnIamDriver);
        mButtonClient = findViewById(R.id.btnIamClient);

        mButtonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valor que se le da al boton cliente
                editor.putString("user" , "client");
                // Guardamso el valor que se le dio al botonb
                editor.apply();
                goToSelectAuth();
            }
        });
        mButtonDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valor que se le da al boton Conductor
                editor.putString("user","driver");
                // Guardamso el valor que se le dio al botonb
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String typeUser = mPref.getString("user","");
            if (typeUser.equals("client")){
                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this,SelectOptionAuthActivity.class);
        startActivity(intent);

    }
}