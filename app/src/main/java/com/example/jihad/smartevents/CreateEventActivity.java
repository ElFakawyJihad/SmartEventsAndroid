package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class CreateEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void createEvent(View view) throws IOException{
        String connectionState = null;
        Intent sndActivity = new Intent(CreateEventActivity.this, EventCreationInfoActivity.class);

        final EditText eventTitle = (EditText) findViewById(R.id.eventTitle);
        String title = eventTitle.getText().toString();

        final Spinner SpinnerEventCategory = (Spinner) findViewById(R.id.SpinnerEventCategory);
        String eventCategory = SpinnerEventCategory.getSelectedItem().toString();

        final EditText eventDescription = (EditText) findViewById(R.id.eventDescription);
        String description = eventDescription.getText().toString();

        final EditText eventDate = (EditText) findViewById(R.id.eventDate);
        String date = eventDate.getText().toString();

        final EditText eventCapacity = (EditText) findViewById(R.id.eventCapacity);
        String capacity = eventCapacity.getText().toString();

        final EditText eventLocalisation = (EditText) findViewById(R.id.eventLocalisation);
        String localisation = eventLocalisation.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(localisation,1);
        Address add = list.get(0);

        String locality = add.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        Toast.makeText(this,String.valueOf(lat)+"/"+String.valueOf(lng),Toast.LENGTH_LONG).show();

        String result = new UserRest().createNewEvent(title,eventCategory,description,date,capacity,localisation,lat,lng);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");


            if(message.equals(ConstantesRest.CREATEEVENTOK)) {
                JSONObject data = jsonObject.getJSONObject("data");

                connectionState = ConstantesActivity.CREATENEWEVENTOK;
                sndActivity.putExtra("titre", data.getString("eventTitle"));
                sndActivity.putExtra("catégorie", data.getString("eventCategory"));
                sndActivity.putExtra("description", data.getString("eventDescription"));
                sndActivity.putExtra("date de l'event", data.getString("eventDate"));
                sndActivity.putExtra("nombres de place", data.getString("eventCapacity"));
                sndActivity.putExtra("lieu", data.getString("eventLocalisation"));
                sndActivity.putExtra("lat", data.getString("lat"));
                sndActivity.putExtra("lng",data.getString("long"));

                startActivity(sndActivity);
            } else {
                connectionState = ConstantesActivity.CREATENEWEVENTKO;
            }


        } catch (Exception e) {
            //Exception à gérer
        }

        Toast.makeText(this, connectionState, Toast.LENGTH_LONG).show();
    }


}
