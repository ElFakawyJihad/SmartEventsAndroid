package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.REST.RESTInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void createEvent(View button){
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

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("titre", title);
        parameters.put("catégorie", eventCategory);
        parameters.put("description", description);
        parameters.put("date de l'event", date);
        parameters.put("nombres de place", capacity);
        parameters.put("lieu", localisation);

        String result = RESTInterface.post(ConstantesRest.ADDNEWEVENTURL, parameters);

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

                //On peut récupérer les autres données
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
