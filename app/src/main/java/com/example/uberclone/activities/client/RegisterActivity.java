package com.example.uberclone.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberclone.Models.Client;
import com.example.uberclone.R;
import com.example.uberclone.includes.MyToolbar;
import com.example.uberclone.providers.AuthProvider;
import com.example.uberclone.providers.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    /*------------------------------------------------------------------------------------*/
    /*CLASE: RegisterActivity-------------------------------------------------------------*/
    /*FUNCION: Contiene los metodos necesarios para el registro de usuario----------------*/
    /*------------------------------------------------------------------------------------*/

   /*ZONA DE CREACION DECLARACION DE VARIABLES*/

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;
    AlertDialog mDialog;
    /*----------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //se implemente toolbar en esta vista
        MyToolbar.show(this, "Registro de usuario", true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();
        //Instanciamos shared preferences

        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();
        mButtonRegister = findViewById(R.id.btnRegister);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
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
        final String password = mTextInputPassword.getText().toString();

        //se valida que todos los campos hayan sido diligenciados
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            //se valida que el password tenga 6 o mas caracteres. si es menor firebase no lo va a aceptar
            if (password.length() >= 6) {
                mDialog.show();
                //manda a registrar un usuario en el autentication de firebade
                register(name,email,password);
            }
            else {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, final String email, String password) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    //el registro en firebase se realizo con exito
                    //se obtiene el id del usuario recien registrado, esto es para que el usuario que creemos en users tenga el mismo id
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Client client = new Client(id, name, email);
                    //se llama el metodo saveUser con los datos obtenidos
                    create(client);
                }
                else {
                    //el registro en firebase no se realizo con exito
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    //el guardado en firebase se realizo con exito
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                }
                else {
                    //el registro en firebase no se realizo con exito
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*------------------------------------------------------------------------------------*/
    /*METODO: saveUser--------------------------------------------------------------------*/
    /*FUNCION: se encarga de registrar un usuario en el user de firebade con el nodo -----*/
    /*---------Drivers o Clients segun sea el caso----------------------------------------*/
    /*------------------------------------------------------------------------------------*/
    /*void saveUser(String id, String name, String email) {
        //se toma de la variable de sesion creada el valor que contiene
        String selectedUser = mPref.getString("user", "");

        //se crea un objeto user y se setean sus valores
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        //se valida si el valor que viene en la variable de sesion es driver
        if (selectedUser.equals("driver")) {
            //se envia a guardar en user en el nodo Drivers el objeto user creado anteriormente
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //el guardado en firebase se realizo con exito
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //el guardado no se pudo realizar
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //se valida si el valor que viene en la variable de sesion es client
        else if (selectedUser.equals("client")){
            //se envia a guardar en user en el nodo Clients el objeto user creado anteriormente
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //el guardado en firebase se realizo con exito
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //el guardado no se pudo realizar
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

}