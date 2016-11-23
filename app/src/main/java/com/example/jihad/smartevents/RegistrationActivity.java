package com.example.jihad.smartevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class RegistrationActivity extends AppCompatActivity {



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

            try {
                String result=new UserRest().inscription(email,password);
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
