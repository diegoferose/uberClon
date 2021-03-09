package com.example.uberclone.services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*--------------------------------------------------------------------------------------------*/
/*CLASE: MyFirebaseMessagingClient------------------------------------------------------------*/
/*FUNCION: Contiene los metodos necesarios para realizar los mensajes push--------------------*/
/*--------------------------------------------------------------------------------------------*/


public class MyFirebaseMessagingClient extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
