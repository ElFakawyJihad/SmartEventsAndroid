package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;

public class RegistrationFormActivity extends Activity implements View.OnClickListener {
    private EditText email = null;
    private EditText firstname = null;
    private EditText lastname = null;
    private EditText birthday = null;
    private int gender;// 0 = man 1 = woman
    private Button saveButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        this.email = (EditText) findViewById(R.id.emailRegistration);
        this.firstname = (EditText) findViewById(R.id.firstNameRegistration);
        this.lastname = (EditText) findViewById(R.id.lastNameRegistration);
        this.birthday = (EditText) findViewById(R.id.birthdayRegistration);

        saveButton = (Button) findViewById(R.id.nextStepRegistration);
        saveButton.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.manRegistration:
                if (checked)
                    gender = 0;
                break;
            case R.id.womanRegistration:
                if (checked)
                    gender = 1;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextStepRegistration:
                String emailContent = this.email.getText().toString();
                String firstNameContent = this.firstname.getText().toString();
                String lastNameContent = this.lastname.getText().toString();
                if(!emailContent.contains("@")) {
                    this.email.setHint(ConstantesActivity.INVALID_EMAIL);
                    this.email.setError(ConstantesActivity.INVALID_EMAIL_MESSAGE);
                    break;
                }

                Intent registrationIntent = new Intent(RegistrationFormActivity.this, RegistrationActivity.class);

                registrationIntent.putExtra(ConstantesActivity.EMAIL, emailContent);
                registrationIntent.putExtra(ConstantesActivity.FIRST_NAME, firstNameContent);
                registrationIntent.putExtra(ConstantesActivity.LAST_NAME, this.lastname.getText().toString());
                registrationIntent.putExtra(ConstantesActivity.BIRTHDAY, this.birthday.getText().toString());
                registrationIntent.putExtra(ConstantesActivity.GENDER, this.gender);

                startActivity(registrationIntent);

                Toast.makeText(this, "Passage à l'autre activité pour finaliser l'inscription", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Echec pour finaliser l'inscription", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
