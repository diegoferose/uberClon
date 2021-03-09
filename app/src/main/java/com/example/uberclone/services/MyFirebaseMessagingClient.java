package com.example.uberclone.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.uberclone.channel.NotificacionHelper;
import com.google.android.gms.common.api.Api;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

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
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");


        if(title != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                showNotificationApiOreo(title,body);
            }
            else {
                showNotification(title,body);
            }
        }
    }

    private void showNotification(String title, String body) {
        PendingIntent intent = PendingIntent.getActivity(getBaseContext(),0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificacionHelper notificacionHelper = new NotificacionHelper(getBaseContext());
        NotificationCompat.Builder builder = notificacionHelper.getNotificationOldAPI(title,body, intent, sound);
        notificacionHelper.getManager().notify(1, builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationApiOreo(String title, String body) {
        PendingIntent intent = PendingIntent.getActivity(getBaseContext(),0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificacionHelper notificacionHelper = new NotificacionHelper(getBaseContext());
        Notification.Builder builder = notificacionHelper.getNotification(title, body, intent, sound);
        notificacionHelper.getManager().notify(1, builder.build());
    }
}
