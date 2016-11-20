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
import com.example.jihad.smartevents.REST.RESTInterface;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText email = null;
    EditText password = null;
    private UserRest rest = new UserRest();

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

        //TODO A modifier en Utilisant la classe UserREST quand le serveur sera disponible
        Map<String, String> parameters = new HashMap<String, String>();
        String userMail = "dureyantonin@gmail.com";
        String userPassword = "azerty01";
        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD, userPassword);

        String result = RESTInterface.post(ConstantesRest.CONNECTIONURL, parameters);
        //result = "{message: 'OK'}";
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject message = jsonObject.getJSONObject("message");

            if(message.toString().equals(ConstantesRest.IDENTIFICATIONOK)) {
                connectionState = ConstantesActivity.CONNECTIONOK;
                sndActivity.putExtra(ConstantesActivity.EMAIL, connectionState);
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
