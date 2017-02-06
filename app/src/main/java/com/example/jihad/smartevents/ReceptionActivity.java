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
import static com.example.jihad.smartevents.R.id.button3;
import static com.example.jihad.smartevents.R.id.createEventButton;
import static com.example.jihad.smartevents.R.id.getMyEventsButton;

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

        Button getMyEventsButton = (Button) findViewById(R.id.getMyEventsButton);
        getMyEventsButton.setOnClickListener(this);

        Button logOut = (Button) findViewById(R.id.button3);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.viewMyProfile:
                //TODO
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ReceptionActivity.this);
                builder1.setIcon(R.drawable.sorry);
                builder1.setTitle("Profil");
                builder1.setMessage("Votre profil n'est pas accessible pour le moment.");
                builder1.setCancelable(true);
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

            case getMyEventsButton:
                Toast.makeText(this, "clic sur mes evenements", Toast.LENGTH_LONG).show();
                Intent getMyEventsIntent = new Intent(ReceptionActivity.this, ChatActivity.class);
                startActivity(getMyEventsIntent);
                break;

            case button3:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ReceptionActivity.this);
                builder2.setIcon(R.drawable.logout1);
                builder2.setTitle("Déconnexion");
                builder2.setMessage("Voulez-vous vraiment vous déconnecter?");
                builder2.setCancelable(true);
                builder2.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent logOut = new Intent(ReceptionActivity.this, MainActivity.class);
                                startActivity(logOut);
                                dialog.cancel();
                            }
                        });
                builder2.setNegativeButton(
                        "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder2.create();
                alert1.show();
                break;

            default:
                break;
        }
    }

}
