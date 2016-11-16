package com.example.jihad.smartevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.jihad.smartevents.RESTInterface.get;
import static com.example.jihad.smartevents.RESTInterface.post;

public class RegistrationActivity extends AppCompatActivity {

    public final static String EMAIL = "com.example.jihad.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    private void saveMail(){
        Map<String, String> parameters = new HashMap<>();
        String password = "", passwordConfirmation = "",email = "";

        email = (String) ( (EditText) findViewById(R.id.emailRegistration)).getText().toString();
        password = (String) ( (EditText) findViewById(R.id.passwordRegistration)).getText().toString();
        passwordConfirmation = (String) ( (EditText) findViewById(R.id.passwordRegistrationConfirmation)).getText().toString();



        if(password.equals(passwordConfirmation)){
            Intent intent = new Intent(this, RegistrationFormActivity.class);

            parameters = new HashMap<>();
            parameters.put("email",email);
            parameters.put("password",password);

            try {
                JSONObject reponse = new JSONObject((String) post("http://localhost:8081/connection", parameters));
                if(reponse.getString("message").equals("OK")) {

                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
