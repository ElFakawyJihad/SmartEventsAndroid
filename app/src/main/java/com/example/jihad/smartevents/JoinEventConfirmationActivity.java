package com.example.jihad.smartevents;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.jihad.smartevents.R.id.goHomepageButton;

public class JoinEventConfirmationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private String firstName;
    private String email;
    private String id;
    private String titre;
    private String description;
    private String date_debut;
    private String date_fin;
    private String latitude;
    private String longitude;
    private String organisateur_id;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event_confirmation);

        Intent intent = getIntent();
        //User information
        firstName = intent.getStringExtra(ConstantesActivity.FIRST_NAME);
        email = intent.getStringExtra(ConstantesActivity.EMAIL);

        //Event information
        id = intent.getStringExtra("id");
        titre = intent.getStringExtra("titre");
        description = intent.getStringExtra("description");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_LONG).show();

        //Set view
        TextView firstNameText = (TextView) findViewById(R.id.userName);
        firstNameText.setText(firstName);
        TextView titreText = (TextView) findViewById(R.id.eventTitle);
        titreText.setText(titre);
        TextView descriptionText = (TextView) findViewById(R.id.eventDescription);
        descriptionText.setText(description);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //Buttons
        Button goHomepageEventButton = (Button) findViewById(goHomepageButton);
        goHomepageEventButton.setOnClickListener(this);

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        LatLng latLong = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        //Preparer le marqueur de position

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong);
        markerOptions.title(titre);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker marker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goHomepageButton:
                Intent createEventIntent = new Intent(JoinEventConfirmationActivity.this, MapsActivity.class);
                startActivity(createEventIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionSuspended(int cause) {
        Toast.makeText(this, "Test connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Test connection failed", Toast.LENGTH_LONG).show();
    }

}
