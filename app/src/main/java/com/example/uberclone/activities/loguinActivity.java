package com.example.uberclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.uberclone.R;
import com.example.uberclone.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class loguinActivity extends AppCompatActivity {
    /*--------------------------------------------------------------------------------------------*/
    /*CLASE: loguinActivity-----------------------------------------------------------------------*/
    /*FUNCION: Contiene los metodos necesarios para loguearse en firebase-------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /*ZONA DE CREACION DECLARACION DE VARIABLES*/
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    AlertDialog mDialog;
    /*----------------------------------------*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

        //se implemente toolbar en esta vista
        MyToolbar.show(this, "Login de usuario", true);

        //se instancian los objetos necesarios
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDialog = new SpotsDialog.Builder().setContext(loguinActivity.this).setMessage("Espere un momento").build();

        //se crean un envento listener para el boton mButtonLogin
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /*------------------------------------------------------------------------------------*/
    /*METODO: login-----------------------------------------------------------------------*/
    /*FUNCION: se encarga de realizar el login en  de firebade----------------------------*/
    /*------------------------------------------------------------------------------------*/
    private void login () {
        //ser obtiene el valor digitado por el usuario en los inputs y se guardan en variables
         String email = mTextInputEmail.getText().toString();
         String password = mTextInputPassword.getText().toString();

        //se valida que todos los campos hayan sido diligenciados
         if (!email.isEmpty() && !password.isEmpty()) {
             //se valida que el password tenga 6 o mas caracteres. si es menor firebase no lo va a aceptar
             if (password.length() >= 6){
                 // Muestra el Progess Dialog
                 mDialog.show();

                 //manda a loguear el usuario en firebase
                 mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             //el login se realiz correctamente
                             Toast.makeText(loguinActivity.this,"El login se realizo exitosamente",Toast.LENGTH_SHORT).show();
                         }
                         else {
                             //el login no se realizo
                             Toast.makeText(loguinActivity.this,"La email o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                         }
                         //oculta el Progess Dialog
                         mDialog.dismiss(); // No se muestra el Progess Dialog

                     }
                 });

             }
             else {
                 Toast.makeText(loguinActivity.this,"La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
             }
         }
         else {
             Toast.makeText(loguinActivity.this,"El email y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
         }

    }
    }
