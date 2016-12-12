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
import com.example.jihad.smartevents.rest.UserRest;
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

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker myMarker, marker, currentMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private HashMap<String, JSONObject> markerEventRelations;

    //User information
    private String firstName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        //Buttons
        Button returnToReceptionButton = (Button) findViewById(R.id.returnToReceptionButton);
        returnToReceptionButton.setOnClickListener(this);

        Button joinEventButton = (Button) findViewById(R.id.joinEvent);
        joinEventButton.setOnClickListener(this);

        //User information
        Intent intent = getIntent();
        firstName = intent.getStringExtra(ConstantesActivity.FIRST_NAME);
        email = intent.getStringExtra(ConstantesActivity.EMAIL);
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // Should we show an explanation?
            /*
            ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            Toast.makeText(this, "Votre localisation GPS est désactivée.", Toast.LENGTH_LONG).show();
            */
            //return;
        } else {
            mMap.setMyLocationEnabled(true);

            this.mGoogleApiClient.connect();
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

            Toast.makeText(this, "GPS est désactivée.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            //return;
        }

        //Recuperer la derniere position
        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //Nettoyer la Map
        //mMap.clear();
        // Recuperer la position actuel
        //LatLng latLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        Double lat = 50.2001;
        Double lng = 1.2001;
        LatLng latLong = new LatLng(lat, lng);
        //Preparer le marqueur de position
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong);
        markerOptions.title("Moi");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        myMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12));

        //String result = UserRest.getEventNear(mLastLocation.getLatitude()+"", mLastLocation.getLongitude()+"");
        String result = UserRest.getEventNear(lat+"", lng+"");


        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");

            if(message.equals("OK")) {
                JSONArray events = jsonObject.getJSONArray("result");

                for(int e = 0; e < events.length(); e++) {
                    JSONObject event = events.getJSONObject(e);

                    marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(event.getString("latitude")), Double.parseDouble(event.getString("longitude"))))
                            .title(event.getString("titre") + " (" + event.getString("nb_places") + " places restantes)")
                            .snippet(event.getString("description"))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    this.markerEventRelations.put(marker.getId(), event);
                }
            }
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

    @Override
    public boolean onMarkerClick(final Marker marker) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.showEventDetails);
        currentMarker = marker;

        //Vérifie s'il s'agit du marqueur de l'utilisateur
        if(!marker.equals(this.myMarker)) {
            TextView title = (TextView) findViewById(R.id.eventTitle);
            //TextView category = (TextView) findViewById(R.id.eventCategory);
            TextView description = (TextView) findViewById(R.id.eventDescription);
            TextView date = (TextView) findViewById(R.id.eventDate);


            JSONObject event = this.markerEventRelations.get(marker.getId());

            try {
                title.setText(event.getString("titre"));
                //category.setText("Pas de categorie bizarre");
                description.setText(event.getString("description"));
                DateTime dt = new DateTime(event.getString("date_debut"));
                date.setText(dt.getDayOfMonth()+"/"+dt.getMonthOfYear()+"/"+dt.getYear()+" à "+(dt.getHourOfDay()-1)+"H"+dt.getMinuteOfHour());

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
            case R.id.returnToReceptionButton:
                Intent receptionIntent = new Intent(MapsActivity.this, ReceptionActivity.class);
                startActivity(receptionIntent);
                break;
            case R.id.joinEvent:
                try {
                    JSONObject event = this.markerEventRelations.get(this.currentMarker.getId());

                    //Here, we join the event
                    String result = UserRest.joinEvent(event.getString("id"), this.email);
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();

                    Intent joinEventConfirmationIntent = new Intent(MapsActivity.this, JoinEventConfirmationActivity.class);
                    joinEventConfirmationIntent.putExtra(ConstantesActivity.EMAIL, this.email);
                    joinEventConfirmationIntent.putExtra("id", event.getString("id"));
                    joinEventConfirmationIntent.putExtra("titre", event.getString("titre"));
                    joinEventConfirmationIntent.putExtra("description", event.getString("description"));
                    joinEventConfirmationIntent.putExtra("date_debut", event.getString("date_debut"));
                    joinEventConfirmationIntent.putExtra("date_fin", event.getString("date_fin"));
                    joinEventConfirmationIntent.putExtra("latitude", event.getString("latitude"));
                    joinEventConfirmationIntent.putExtra("longitude", event.getString("longitude"));
                    joinEventConfirmationIntent.putExtra("lieu_name", event.getString("lieu_name"));

                    startActivity(joinEventConfirmationIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

}
