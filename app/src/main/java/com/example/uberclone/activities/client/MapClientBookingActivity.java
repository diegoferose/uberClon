package com.example.uberclone.activities.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.uberclone.R;
import com.example.uberclone.providers.AuthProvider;
import com.example.uberclone.providers.GeofireProvider;
import com.example.uberclone.providers.TokenProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import java.util.ArrayList;
import java.util.List;

public class MapClientBookingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private AuthProvider mAuthProvider;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocation;

    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEST_CODE = 2;
    private Marker mMarker;

    private GeofireProvider mGeoFireProvider;

    private TokenProvider mTokenProvider;

    private LatLng mCurrentLatLng;
    private List<Marker> mDriversMarkers = new ArrayList<>();
    private boolean mIsFirstTime = true;

    //Variables para el Place autocomplete
    private PlacesClient mPlaces;
    private AutocompleteSupportFragment mAutocomplete;
    private AutocompleteSupportFragment mAutocompleteDestination;

    //Varibales que guarda el origen y el destino del cliente
    private String mOrigin;
    private LatLng mOriginLatLng;

    private String mDestination;
    private LatLng mDestinationLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client_booking);

        mAuthProvider = new AuthProvider();
        mGeoFireProvider = new GeofireProvider("working_drivers");
        mTokenProvider = new TokenProvider();

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        //Instanciamos varables Place Autocomplete
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }

        mPlaces = Places.createClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


    }
}