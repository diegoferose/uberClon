package com.example.uberclone.activities.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.uberclone.R;
import com.example.uberclone.providers.AuthProvider;
import com.example.uberclone.providers.ClientBookingProvider;
import com.example.uberclone.providers.GeofireProvider;

public class NotificationBookingActivity extends AppCompatActivity {

    private TextView mTextViewDestination;
    private TextView mTextViewOrigin;
    private TextView mTextViewMin;
    private TextView mTextViewCounter;
    private TextView mTextViewDistance;
    private Button mbuttonAccept;
    private Button mbuttonCancel;
    private ClientBookingProvider mClientBookingProvider;
    private GeofireProvider mGeofireprovider;
    private AuthProvider mAuthProvider;

    private String mExtraIdClient;
    private String mExtraOrigin;
    private String mExtraDestination;
    private String mExtraMin;
    private String mExtraDistance;

    private MediaPlayer mMediaplayer;

    private int mCounter = 10;
    private Handler mHandler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCounter = mCounter -1;
            mTextViewCounter.setText(String.valueOf(mCounter));
            if (mCounter > 0) {
                initTimer();
            }
            else {
                cancelBooking();
            }

        }
    };

    private void initTimer(){
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_booking);

        mTextViewDestination = findViewById(R.id.textViewDestination);
        mTextViewOrigin = findViewById(R.id.textViewOrigin);
        mTextViewMin = findViewById(R.id.textViewMin);
        mTextViewCounter = findViewById(R.id.textViewCounter);
        mTextViewDistance = findViewById(R.id.textViewDistance);
        mbuttonAccept = findViewById(R.id.btnAcceptBooking);
        mbuttonCancel = findViewById(R.id.btnCancelBooking);

        mExtraIdClient = getIntent().getStringExtra("idClient");
        mExtraOrigin = getIntent().getStringExtra("origin");
        mExtraDestination = getIntent().getStringExtra("destination");
        mExtraMin = getIntent().getStringExtra("min");
        mExtraDistance = getIntent().getStringExtra("distance");

        mTextViewDestination.setText(mExtraDestination);
        mTextViewOrigin.setText(mExtraOrigin);
        mTextViewMin.setText(mExtraMin);
        mTextViewDistance.setText(mExtraDistance);

        mMediaplayer = MediaPlayer.create(this, R.raw.ringtone);
        mMediaplayer.setLooping(true);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );

        initTimer();

        mbuttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptBooking();
            }
        });
        
        mbuttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking();
            }
        });
    }

    private void cancelBooking() {
        if (mHandler != null) mHandler.removeCallbacks(runnable);
        mClientBookingProvider = new ClientBookingProvider();
        mClientBookingProvider.updateStatus(mExtraIdClient, "cancel");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(2);
        Intent intent = new Intent(NotificationBookingActivity.this, MapDriverActivity.class);
        startActivity(intent);
        finish();
    }

    private void acceptBooking() {
        if (mHandler != null) mHandler.removeCallbacks(runnable);
        mAuthProvider = new AuthProvider();
        mGeofireprovider = new GeofireProvider("active_drivers");
        mGeofireprovider.removeLocation(mAuthProvider.getId());

        mClientBookingProvider = new ClientBookingProvider();
        mClientBookingProvider.updateStatus(mExtraIdClient, "accept");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(2);

        Intent intent1 = new Intent(NotificationBookingActivity.this, MapDriverBookingActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.setAction(Intent.ACTION_RUN);
        intent1.putExtra("idClient",mExtraIdClient);
        startActivity(intent1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaplayer != null){
            if (mMediaplayer.isPlaying()){
                mMediaplayer.pause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaplayer != null){
            if (mMediaplayer.isPlaying()){
                mMediaplayer.release();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaplayer != null){
            if (!mMediaplayer.isPlaying()){
                mMediaplayer.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) mHandler.removeCallbacks(runnable);

        if(mMediaplayer != null){
            if (mMediaplayer.isPlaying()){
                mMediaplayer.pause();
            }
        }
    }
}