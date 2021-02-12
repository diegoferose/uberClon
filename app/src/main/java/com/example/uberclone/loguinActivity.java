package com.example.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class loguinActivity extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    AlertDialog mDialog;
    //Se crea Toolbar
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Instanciamos el progess dialog y colocamos el texto que sale E.C
        mDialog = new SpotsDialog.Builder().setContext(loguinActivity.this).setMessage("Espere un momento").build();
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login () {
         String email = mTextInputEmail.getText().toString();
         String password = mTextInputPassword.getText().toString();

         if (!email.isEmpty() && !password.isEmpty()) {
             if (password.length() >= 6){
                 mDialog.show(); // Muestra el Progess Dialog
                 mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(loguinActivity.this,"El login se realizo exitosamente",Toast.LENGTH_SHORT).show();
                         }
                         else {
                             Toast.makeText(loguinActivity.this,"La email o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                         }
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
