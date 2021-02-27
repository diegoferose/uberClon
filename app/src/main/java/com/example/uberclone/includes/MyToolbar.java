package com.example.uberclone.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.uberclone.R;


public class MyToolbar {
    /*--------------------------------------------------------------------------------------------*/
    /*CLASE: MyToolbar----------------------------------------------------------------------------*/
    /*FUNCION: Contiene los metodos necesarios para mostrar el toolbar ---------------------------*/
    /*--------------------------------------------------------------------------------------------*/
    public static void show(AppCompatActivity activity, String title, boolean upButton) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}