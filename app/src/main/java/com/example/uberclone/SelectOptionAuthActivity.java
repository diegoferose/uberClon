package com.example.uberclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uberclone.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {
    /*--------------------------------------------------------------------------------------------*/
    /*CLASE: SelectOptionAuthActivity-------------------------------------------------------------*/
    /*FUNCION: Contiene los metodos necesarios para escoger entre registrar o el login------------*/
    /*-------------------------------------------------------------------------------------------*/

    /*ZONA DE CREACION DECLARACION DE VARIABLES*/
    Toolbar mToolbar;
    Button mButtonGoToLogin;
    Button mButtonGoToRegister;
    /*----------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);
        //se implemente toolbar en esta vista
        MyToolbar.show(this, "Seleccione una opcion", true);

        //se instancian los botones
        mButtonGoToLogin = findViewById(R.id.btnToGoLogin);
        mButtonGoToRegister = findViewById(R.id.btnToGoRegister);

        //se crean un envento listener para el boton mButtonGoToLogin
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        //se crean un envento listener para el boton mButtonGoToRegister
        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

    }

    /*------------------------------------------------------------------------------------*/
    /*METODO: goToLogin-------------------------------------------------------------------*/
    /*FUNCION: se encarga de redirigir al loguinActivity----------------------------------*/
    /*------------------------------------------------------------------------------------*/
    public void goToLogin (){
        Intent intent = new Intent(SelectOptionAuthActivity.this, loguinActivity.class);
        startActivity(intent);
    }
    /*---------------------------------------------------------------------------------------*/
    /*METODO: goToRegister-------------------------------------------------------------------*/
    /*FUNCION: se encarga de redirigir al RegisterActivity-----------------------------------*/
    /*---------------------------------------------------------------------------------------*/
    public void goToRegister (){
        Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}