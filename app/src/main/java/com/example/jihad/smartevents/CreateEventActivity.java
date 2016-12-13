package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONObject;

import java.util.List;


public class CreateEventActivity extends Activity implements View.OnClickListener {

    private String result, title, eventCategory, description, date, capacity, localisation;
    private Spinner SpinnerEventCategory;
    private EditText eventTitle, eventDescription, eventDate, eventCapacity, eventLocalisation;
    private Double lat = 0.0, lng = 0.0;

    String connectionState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Buttons
        Button createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(this);

        //get Views
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        SpinnerEventCategory = (Spinner) findViewById(R.id.SpinnerEventCategory);
        eventDescription = (EditText) findViewById(R.id.eventDescription);
        eventDate = (EditText) findViewById(R.id.eventDate);
        eventCapacity = (EditText) findViewById(R.id.eventCapacity);
        eventLocalisation = (EditText) findViewById(R.id.eventLocalisation);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.createEventButton:

                //Toast.makeText(this,"clic sur le bouton create..",Toast.LENGTH_LONG).show();

                title = eventTitle.getText().toString();
                eventCategory = SpinnerEventCategory.getSelectedItem().toString();
                description = eventDescription.getText().toString();
                date = eventDate.getText().toString();
                capacity = eventCapacity.getText().toString();
                localisation = eventLocalisation.getText().toString();

                //localisation = "1 Rue Jules Guesde, 59155 Faches-Thumesnil";
                //localisation = "7 Avenue Paul Langevin, 59650 Villeneuve-d'Ascq";
                //eventCategory = "0";
                //date = "2016-12-13T01:56:00.000Z";

                try {
                    Geocoder gc = new Geocoder(this);
                    List<Address> list = gc.getFromLocationName(localisation, 1);
                    Address add = list.get(0);

                    //String locality = add.getLocality();
                    //Toast.makeText(this,locality,Toast.LENGTH_LONG).show();

                    lat = add.getLatitude();
                    lng = add.getLongitude();

                    //Toast.makeText(this,lat+" "+lng,Toast.LENGTH_LONG).show();
                }  catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(this,"Exception: "+lat+" "+lng,Toast.LENGTH_LONG).show();
                }



                String result = UserRest.createEvent(title,eventCategory,description,date,capacity,localisation,lat,lng);
                Intent EventCreationInfoIntent = new Intent(CreateEventActivity.this, ReceptionActivity.class);
                //Toast.makeText(this,result,Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");

                    if(message.equals(ConstantesRest.CREATEEVENTOK)) {
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventTitle, title);
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventCategory, eventCategory);
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventDescription, description);
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventDate, date);
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventCapacity, capacity);
                        EventCreationInfoIntent.putExtra(ConstantesRest.eventLocalisation, localisation);
                        EventCreationInfoIntent.putExtra(ConstantesRest.localisationLatitude, lat);
                        EventCreationInfoIntent.putExtra(ConstantesRest.localisationLongitude, lng);

                        startActivity(EventCreationInfoIntent);
                    } else {
                        Toast.makeText(this,"Veuillez remplir tous les champs du formulaire de création.",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Erreur lors de la création de l'évenement. Veuillez corriger les informations soumises dans le formulaire de création.",Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;
        }
    }


}
