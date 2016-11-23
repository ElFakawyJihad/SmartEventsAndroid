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
import com.example.jihad.smartevents.rest.UserRest;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText email = null;
    EditText password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);

}
    

    @Override
    public void onClick(View view) {
        String connectionState = null;
        Intent sndActivity = new Intent(MainActivity.this, SecondActivity.class);
        String emailParam = email.getText().toString();
        String passwordParam = password.getText().toString();

        String userMail = "dureyantonin@gmail.com";
        String userPassword = "azerty01";

        String result =new UserRest().connection(userMail,userPassword);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");


            if(message.equals(ConstantesRest.IDENTIFICATIONOK)) {
                JSONObject data = jsonObject.getJSONObject("data");

                connectionState = ConstantesActivity.CONNECTIONOK;
                sndActivity.putExtra(ConstantesActivity.EMAIL, data.getString("email"));
                sndActivity.putExtra(ConstantesActivity.FIRST_NAME, data.getString("first_name"));
                sndActivity.putExtra(ConstantesActivity.LAST_NAME, data.getString("last_name"));

                //On peut récupérer les autres données
                startActivity(sndActivity);
            } else {
                connectionState = ConstantesActivity.CONNECTIONKO;
            }


        } catch (Exception e) {
            //Exception à gérer
        }

        Toast.makeText(this, connectionState, Toast.LENGTH_LONG).show();
    }
}
