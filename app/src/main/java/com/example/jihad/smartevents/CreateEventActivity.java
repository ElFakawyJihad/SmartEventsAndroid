package com.example.jihad.smartevents;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void createEvent(View button){
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
    }


}
