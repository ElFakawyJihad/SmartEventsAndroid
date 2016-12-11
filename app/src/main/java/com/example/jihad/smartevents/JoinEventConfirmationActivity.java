package com.example.jihad.smartevents;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import static com.example.jihad.smartevents.R.id.goHomepageButton;

public class JoinEventConfirmationActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event_confirmation);

        //Buttons
        Button goHomepageEventButton = (Button) findViewById(goHomepageButton);
        goHomepageEventButton.setOnClickListener(this);
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
}
