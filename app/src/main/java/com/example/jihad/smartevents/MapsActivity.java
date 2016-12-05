package com.example.jihad.smartevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        Intent intent = getIntent();

        String firstName = intent.getStringExtra(ConstantesActivity.FIRST_NAME);
        TextView firstNameText = (TextView) findViewById(R.id.userName);
        firstNameText.setText(firstName);
        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        Button addEventButtonButton = (Button) findViewById(R.id.addEventButton);
        addEventButtonButton.setOnClickListener(this);
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
        LatLng sydney = new LatLng(-34, 151);
        LatLng event1 = new LatLng(-34.01, 151.03);
        LatLng event2 = new LatLng(-34.001, 151.025);


        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

        // Add a marker in Sydney and move the camera
        myMarker = mMap.addMarker(new MarkerOptions().position(sydney)
                .position(sydney)
                .title("Me")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.addMarker(new MarkerOptions()
                .position(event1)
                .title("Tournoi de Volley-ball (6 places restantes)")
                .snippet("Le lieu de rencontre de tous les émotions")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions()
                .position(event2)
                .title("Tournoi de Badminton (42 places restantes)")
                .snippet("Le lieu de rencontre de tous les émotions")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        //Toast.makeText(this, "Pfff", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.showEventDetails);

        if(!marker.equals(this.myMarker)) {
            TextView text = (TextView) findViewById(R.id.eventTitle);
            ll.setVisibility(View.VISIBLE);

            text.setText(marker.getTitle());
        } else {
            ll.setVisibility(View.GONE);
        }

        //Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public void onClick(View view) {

        Intent sndActivity = new Intent(MapsActivity.this, CreateEventActivity.class);
        startActivity(sndActivity);



        Toast.makeText(this, "ajouter event", Toast.LENGTH_LONG).show();
    }

}
