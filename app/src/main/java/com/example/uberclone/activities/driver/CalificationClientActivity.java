package com.example.uberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberclone.Models.ClientBooking;
import com.example.uberclone.Models.HistoryBooking;
import com.example.uberclone.R;
import com.example.uberclone.activities.client.CalificationDriverActivity;
import com.example.uberclone.activities.client.MapClientActivity;
import com.example.uberclone.providers.ClientBookingProvider;
import com.example.uberclone.providers.HistoryBookingProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class CalificationClientActivity extends AppCompatActivity {

    private TextView mTextViewOrigin;
    private TextView mTextViewDestination;
    private RatingBar mRatinBar;
    private Button mButtonCalification;

    private ClientBookingProvider mClientBookingProvider;

    private String mExtraClientId;

    private HistoryBooking mHistoryBooking;
    private HistoryBookingProvider mHistoryBookingProvider;

    private float mcalification = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calification_client);

        mTextViewOrigin = findViewById(R.id.textViewOriginCalification);
        mTextViewDestination = findViewById(R.id.textViewDestinationCalification);
        mRatinBar = findViewById(R.id.ratingbarCalification);
        mButtonCalification = findViewById(R.id.btnCalification);

        mClientBookingProvider = new ClientBookingProvider();
        mHistoryBookingProvider = new HistoryBookingProvider();

        mExtraClientId = getIntent().getStringExtra("idClient");

        mRatinBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float calification, boolean fromUser) {
                mcalification = calification;

            }
        });

        mButtonCalification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calificate();
            }
        });

        getClientBooking();
    }

    private void getClientBooking(){
        mClientBookingProvider.getClientBooking(mExtraClientId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ClientBooking clientBooking = snapshot.getValue(ClientBooking.class);
                    mTextViewOrigin.setText(clientBooking.getOrigin());
                    mTextViewDestination.setText(clientBooking.getDestination());
                    mHistoryBooking = new HistoryBooking(
                            clientBooking.getIdHistoryBooking(),
                            clientBooking.getIdClient(),
                            clientBooking.getIdDriver(),
                            clientBooking.getDestination(),
                            clientBooking.getOrigin(),
                            clientBooking.getTime(),
                            clientBooking.getKm(),
                            clientBooking.getStatus(),
                            clientBooking.getOriginLat(),
                            clientBooking.getOriginLng(),
                            clientBooking.getDestinationLat(),
                            clientBooking.getDestinationLng()
                    );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void calificate() {
        if (mcalification > 0){
            mHistoryBooking.setCalificationClient(mcalification);
            mHistoryBooking.setTimestamp(new Date().getTime());
            mHistoryBookingProvider.getHistoryBooking(mHistoryBooking.getIdHistoryBooking()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        mHistoryBookingProvider.updateCalificationClient(mHistoryBooking.getIdHistoryBooking(),mcalification).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CalificationClientActivity.this,"La calificacion se guardo correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CalificationClientActivity.this, MapDriverActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    else {
                        mHistoryBookingProvider.create(mHistoryBooking).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CalificationClientActivity.this,"La calificacion se guardo correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CalificationClientActivity.this, MapDriverActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else {
            Toast.makeText(this,"Debes Ingresar la calification", Toast.LENGTH_SHORT).show();
        }


    }
}