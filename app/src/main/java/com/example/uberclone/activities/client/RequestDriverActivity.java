package com.example.uberclone.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uberclone.R;
import com.example.uberclone.providers.GeofireProvider;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

public class RequestDriverActivity extends AppCompatActivity {

    private LottieAnimationView mAnimation;
    private TextView mTextViewLookingFor;
    private Button mButtonCancelRequest;

    private GeofireProvider mGeofireProvider;

    //Variables para recibir latitud y longitud
    private double mExtraOriginLat;
    private double mExtraOriginLng;
    private LatLng mOriginLatLng;
    private double mRadius = 0.1;
    private boolean mDriverFound = false;
    private String mIdDriverFound = "";
    private LatLng mDriverFoundLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_driver);

        mAnimation = findViewById(R.id.animation);
        mTextViewLookingFor = findViewById(R.id.textViewLookingFor);
        mButtonCancelRequest = findViewById(R.id.btnCancelRequest);

        mAnimation.playAnimation();

        mExtraOriginLat = getIntent().getDoubleExtra("origin_lat",0);
        mExtraOriginLng = getIntent().getDoubleExtra("origin_lng",0);
        mOriginLatLng = new LatLng(mExtraOriginLat,mExtraOriginLng);

        mGeofireProvider = new GeofireProvider();

        getClosesDriver();

    }

    //METODO PARA OBTENER EL CONDUICTOR MAS CERCANO
    private void getClosesDriver(){
        mGeofireProvider.getActiveDrivers(mOriginLatLng,mRadius).addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //BUSCAMOS EL CONDUCTOR MAS CERCANO
                if(!mDriverFound){
                    mDriverFound = true;
                    mIdDriverFound = key;
                    mDriverFoundLatLng = new LatLng(location.latitude,location.longitude);
                    mTextViewLookingFor.setText("CONDUCTOR ENCONTRADO \n ESPERANDO RESPUESTA");

                    Log.d("DRIVER","ID: " + mIdDriverFound);
                }

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                //INGRESA CUANDO TERMINA LA BUSQUEDA DEL CONDUCTOR EN UN RADIO 0.1 KM
                if (!mDriverFound){
                    mRadius = mRadius + 0.1f;

                    //NO ENCONTRO NINGUN CONDUCTOR

                    if (mRadius > 5 ){
                        mTextViewLookingFor.setText("NO SE ENCONTRO UN CONDUCTOR");
                        Toast.makeText(RequestDriverActivity.this, "NO SE ENCONTRO UN CONDUCTOR", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        getClosesDriver();
                    }
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

    }
}