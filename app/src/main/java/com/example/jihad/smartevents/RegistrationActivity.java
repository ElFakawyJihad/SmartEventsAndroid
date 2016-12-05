package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class RegistrationActivity extends Activity implements View.OnClickListener {
    private Button signUpButton = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signUpButton = (Button) findViewById(R.id.signUp);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp:
                String password = "", passwordConfirmation = "",email = "";

                email = (String) ( (EditText) findViewById(R.id.emailRegistrationConfirmation)).getText().toString();
                password = (String) ( (EditText) findViewById(R.id.passwordRegistration)).getText().toString();
                passwordConfirmation = (String) ( (EditText) findViewById(R.id.passwordRegistrationConfirmation)).getText().toString();



                if(password.equals(passwordConfirmation)){
                    Intent intent = new Intent(RegistrationActivity.this, MapsActivity.class);

                    try {
                        String result = UserRest.inscription(email, password);
                        JSONObject reponse = new JSONObject(result);
                        if(reponse.getString(ConstantesRest.GETMESSAGE).equals(ConstantesRest.INSCRIPTIONOK)) {

                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void saveMail(){
        Map<String, String> parameters = new HashMap<>();
        String password = "", passwordConfirmation = "",email = "";

        email = (String) ( (EditText) findViewById(R.id.emailRegistration)).getText().toString();
        password = (String) ( (EditText) findViewById(R.id.passwordRegistration)).getText().toString();
        passwordConfirmation = (String) ( (EditText) findViewById(R.id.passwordRegistrationConfirmation)).getText().toString();



        if(password.equals(passwordConfirmation)){
            Intent intent = new Intent(this, MapsActivity.class);

            try {
                String result = UserRest.inscription(email, password);
                JSONObject reponse = new JSONObject(result);
                if(reponse.getString(ConstantesRest.GETMESSAGE).equals(ConstantesRest.INSCRIPTIONOK)) {

                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
