package com.example.jihad.smartevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.jihad.smartevents.R.id.ImBoredButton;
import static com.example.jihad.smartevents.R.id.createEventButton;

public class ReceptionActivity extends Activity implements View.OnClickListener{

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        //Buttons
        Button viewMyProfileButton = (Button) findViewById(R.id.viewMyProfile);
        viewMyProfileButton.setOnClickListener(this);

        Button ImBoredButton = (Button) findViewById(R.id.ImBoredButton);
        ImBoredButton.setOnClickListener(this);

        Button createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.viewMyProfile:
                //TODO
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ReceptionActivity.this);
                builder1.setMessage("Votre profil n'est pas accessible pour le moment.");
                builder1.setCancelable(true);
                builder1.setIcon(R.drawable.sorry);
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;

            case ImBoredButton:
                Intent ImBoredIntent = new Intent(ReceptionActivity.this, MapsActivity.class);
                startActivity(ImBoredIntent);
                break;

            case createEventButton:
                Intent createEventIntent = new Intent(ReceptionActivity.this, CreateEventActivity.class);
                startActivity(createEventIntent);
                break;

            default:
                break;
        }
    }

}
