package com.example.jihad.smartevents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private Marker marker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private HashMap<String, JSONObject> markerEventRelations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        //Buttons
        Button addEventButton = (Button) findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(this);

        Button joinEventButton = (Button) findViewById(R.id.joinEvent);
        joinEventButton.setOnClickListener(this);

        //User information
        Intent intent = getIntent();
        String firstName = intent.getStringExtra(ConstantesActivity.FIRST_NAME);
        TextView firstNameText = (TextView) findViewById(R.id.userName);
        firstNameText.setText(firstName);

        this.markerEventRelations = new HashMap<String, JSONObject>();
        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Recuperer la derniere position
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //Nettoyer la Map
        mMap.clear();
        // Recuperer la position actuel
        LatLng latLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        //Preparer le marqueur de position
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong); Toast.makeText(this, mLastLocation.getLatitude()+" "+mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
        markerOptions.title("Moi "+mLastLocation.getLatitude()+" "+mLastLocation.getLongitude());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        myMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12));

        try {
            //LatLng event1 = new LatLng(50.608770, 3.0643230);
            LatLng event2 = new LatLng(-34.001, 151.025);

            String eventJsonMock1 = "{" +
                    "\"id\" : \"1\", " +
                    "\"title\" : \"Tournoi de Volley-ball\"," +
                    "\"latitude\" : \"50.63\", " +
                    "\"longitude\" : \"3.064\", " +
                    "\"description\" : \"Le tournoi de l'année à ne pas rater\", " +
                    "\"category\" : \"Sport\", " +
                    "\"place\" : \"3\" " +
                    "}";
            JSONObject event1 = new JSONObject(eventJsonMock1);

            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(event1.getString("latitude")), Double.parseDouble(event1.getString("longitude"))))
                    .title(event1.getString("title") + "(" + event1.getString("place") + " place(s) restante(s))")
                    .snippet(event1.getString("description"))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            this.markerEventRelations.put(marker.getId(), event1);


            marker = mMap.addMarker(new MarkerOptions()
                    .position(event2)
                    .title("Tournoi de Badminton (42 places restantes)")
                    .snippet("Le lieu de rencontre de tous les émotions")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            this.markerEventRelations.put(marker.getId(), event1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Toast.makeText(this, "Test on connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Toast.makeText(this, "Test connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Test connection failed", Toast.LENGTH_LONG).show();
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

        //Ici créer des évènements fictifs

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Votre localisation GPS est désactivée.", Toast.LENGTH_LONG).show();
            return;
        }

        mMap.setMyLocationEnabled(true);

        this.mGoogleApiClient.connect();

        //Toast.makeText(this, "Pfff", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.showEventDetails);

        //Vérifie s'il s'agit du marqueur de l'utilisateur
        if(!marker.equals(this.myMarker)) {
            TextView title = (TextView) findViewById(R.id.eventTitle);
            TextView category = (TextView) findViewById(R.id.eventCategory);
            TextView description = (TextView) findViewById(R.id.eventDescription);


            JSONObject event = this.markerEventRelations.get(marker.getId());

            try {
                title.setText(event.getString("title"));
                category.setText(event.getString("category"));
                description.setText(event.getString("description"));

                ll.setVisibility(View.VISIBLE);
            } catch(Exception e) {
                e.printStackTrace();
            }

        } else {
            ll.setVisibility(View.GONE);
        }

        //Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addEventButton:
                Intent createEventIntent = new Intent(MapsActivity.this, CreateEventActivity.class);
                startActivity(createEventIntent);
                break;
            case R.id.joinEvent:
                Intent joinEventConfirmationIntent = new Intent(MapsActivity.this, JoinEventConfirmationActivity.class);
                startActivity(joinEventConfirmationIntent);
                break;
            default:
                break;
        }
    }

}
