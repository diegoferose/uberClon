package com.example.uberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberclone.Models.Client;
import com.example.uberclone.Models.Driver;
import com.example.uberclone.R;

import com.example.uberclone.includes.MyToolbar;
import com.example.uberclone.providers.AuthProvider;
import com.example.uberclone.providers.DriverProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    /*ZONA DE CREACION DECLARACION DE VARIABLES*/

    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputVehicleBrand;
    TextInputEditText mTextInputVehiclePlate;
    TextInputEditText mTextInputPassword;
    AlertDialog mDialog;
    /*----------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        //se implemente toolbar en esta vista
        MyToolbar.show(this, "Registro de conductor", true);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        mDialog = new SpotsDialog.Builder().setContext(RegisterDriverActivity.this).setMessage("Espere un momento").build();
        mButtonRegister = findViewById(R.id.btnRegister);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputVehicleBrand = findViewById(R.id.textInputVehicleBrand);
        mTextInputVehiclePlate = findViewById(R.id.textInputVehiclePlate);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRegister();
            }
        });


    }
    /*------------------------------------------------------------------------------------*/
    /*METODO: registerUser----------------------------------------------------------------*/
    /*FUNCION: se encarga de registrar un usuario en el autentication de firebade---------*/
    /*------------------------------------------------------------------------------------*/
    void clickRegister() {
        //ser obtiene el valor digitado por el usuario en los inputs y se guardan en variables
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String vehicleBrand = mTextInputVehicleBrand.getText().toString();
        final String vehiclePlate = mTextInputVehiclePlate.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        //se valida que todos los campos hayan sido diligenciados
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()  && !vehicleBrand.isEmpty()  && !vehiclePlate.isEmpty()) {
            //se valida que el password tenga 6 o mas caracteres. si es menor firebase no lo va a aceptar
            if (password.length() >= 6) {
                mDialog.show();
                //manda a registrar un usuario en el autentication de firebade
                register(name,email,password,vehicleBrand,vehiclePlate);
            }
            else {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, final String email, String password, String vehicleBrand, String vehiclePlate) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    //el registro en firebase se realizo con exito
                    //se obtiene el id del usuario recien registrado, esto es para que el usuario que creemos en users tenga el mismo id
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Driver client = new Driver(id, name, email, vehicleBrand, vehiclePlate);
                    //se llama el metodo saveUser con los datos obtenidos
                    create(client);
                }
                else {
                    //el registro en firebase no se realizo con exito
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    //el guardado en firebase se realizo con exito
                    Toast.makeText(RegisterDriverActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this,MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    //el registro en firebase no se realizo con exito
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}