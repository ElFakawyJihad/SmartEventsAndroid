package com.example.jihad.smartevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import static com.example.jihad.smartevents.R.id.eventTime;


public class CreateEventActivity extends Activity implements View.OnClickListener {

    private String result, title, eventCategory, description, date, capacity, localisation;
    private Spinner SpinnerEventCategory;
    private EditText eventTitle, eventDescription, eventDate, eventTime, eventCapacity, eventLocalisation;
    private Double lat = 0.0, lng = 0.0;

    String connectionState = null;


    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText txtDate, txtTime;


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
        eventTime = (EditText) findViewById(R.id.eventTime);
        eventCapacity = (EditText) findViewById(R.id.eventCapacity);
        eventLocalisation = (EditText) findViewById(R.id.eventLocalisation);

        eventDate.setOnClickListener(this);
        eventTime.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.eventDate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.eventTime:
                final Calendar t = Calendar.getInstance();
                mHour = t.get(Calendar.HOUR_OF_DAY);
                mMinute = t.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int lengthHour = String.valueOf(hourOfDay).length();
                                int lengthMinute = String.valueOf(minute).length();
                                if(lengthHour==1 && lengthMinute==1){
                                    eventTime.setText("0"+hourOfDay + ":0" + minute+":00");
                                }
                                else if(lengthHour==1){
                                    eventTime.setText("0"+hourOfDay + ":" + minute+":00");
                                } else if(lengthMinute==1){
                                    eventTime.setText(hourOfDay + ":0" + minute+":00");
                                } else
                                eventTime.setText(hourOfDay + ":" + minute+":00");
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();


                break;

            case R.id.createEventButton:

                //Toast.makeText(this,"clic sur le bouton create..",Toast.LENGTH_LONG).show();

                title = eventTitle.getText().toString();
                eventCategory = SpinnerEventCategory.getSelectedItem().toString();
                description = eventDescription.getText().toString();
                date = eventDate.getText().toString()+" "+eventTime.getText().toString();
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
                final Intent EventCreationInfoIntent = new Intent(CreateEventActivity.this, ReceptionActivity.class);
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


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateEventActivity.this);
                        builder1.setMessage("L'événement a bien été créé, vous allez être redirigé à la page d'accueil.");
                        builder1.setCancelable(true);
                        builder1.setTitle("Evénement créé");
                        builder1.setIcon(R.drawable.succes);
                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                        startActivity(EventCreationInfoIntent);
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
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
