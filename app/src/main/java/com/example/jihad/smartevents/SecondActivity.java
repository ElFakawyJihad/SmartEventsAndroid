package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;

/**
 * Created by Jose on 23/10/2016.
 */

public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent intent = getIntent();

        String email = intent.getStringExtra(ConstantesActivity.EMAIL);
        String firstName = intent.getStringExtra(ConstantesActivity.FIRST_NAME);
        String lastName = intent.getStringExtra(ConstantesActivity.LAST_NAME);

        TextView emailText = (TextView) findViewById(R.id.email);
        TextView firstNameText = (TextView) findViewById(R.id.first_name);
        TextView lastNameText = (TextView) findViewById(R.id.last_name);
        emailText.setText(email);
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
    }
}
