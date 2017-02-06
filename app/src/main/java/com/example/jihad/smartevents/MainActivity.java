package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;
import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.model.Message;
import com.example.jihad.smartevents.rest.UserRest;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {
    public static String USER_EMAIL = "";

    EditText email = null;
    EditText password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parse
        ParseObject.registerSubclass(Message.class);

        // Initialize Parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AIzaSyCP78t8IYEBsnJeVw0zRu6UnEkAnWi4nIw") // should correspond to APP_ID env variable
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://simplechatclient.herokuapp.com/parse/").build());


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);

        Button registerButton = (Button) findViewById(R.id.subscribeButton);
        registerButton.setOnClickListener(this);

}

    public void onClick(View v){

        if(v.getId() == R.id.login){
            String connectionState = null;
            String userMail = email.getText().toString();
            String userPassword = password.getText().toString();

            //String userMail = "dureyantonin@gmail.com";
            //String userPassword = "azerty01";

            String result = new UserRest().connection(userMail,userPassword);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");


                if(message.equals(ConstantesRest.IDENTIFICATIONOK)) {
                    MainActivity.USER_EMAIL = userMail;
                    JSONObject data = jsonObject.getJSONObject("data");
                    Intent receptionIntent = new Intent(MainActivity.this, ReceptionActivity.class);

                    connectionState = ConstantesActivity.CONNECTIONOK;
                    receptionIntent.putExtra(ConstantesActivity.EMAIL, data.getString("email"));
                    receptionIntent.putExtra(ConstantesActivity.FIRST_NAME, data.getString("first_name"));
                    receptionIntent.putExtra(ConstantesActivity.LAST_NAME, data.getString("last_name"));

                    //On peut récupérer les autres données
                    startActivity(receptionIntent);
                } else {
                    connectionState = ConstantesActivity.CONNECTIONKO;
                }


            } catch (Exception e) {
                //Exception à gérer
            }

            Toast.makeText(this, connectionState, Toast.LENGTH_LONG).show();

        }else if(v.getId() == R.id.subscribeButton){
            Intent registrationFormActivity = new Intent(MainActivity.this, RegistrationFormActivity.class);
            startActivity(registrationFormActivity);
        }

    }
}
