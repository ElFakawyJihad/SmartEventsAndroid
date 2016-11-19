package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jihad.smartevents.Constantes.ConstantesActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    public final static String EMAIL = "com.example.jose.intent.EMAIL";
    private final String validEmail = "jose97359@gmail.com";
    private final String validPassword = "lol";
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
               //On peut ajouter une connection à la base de donnée pour vérifier l'authentification
        Intent sndActivity = new Intent(MainActivity.this, SecondActivity.class);
        //TODO A modifier en Utilisant la classe UserREST quand le serveur sera disponible
        if(email.getText().toString().equals(validEmail) &&  password.getText().toString().equals(validPassword)) {
            connectionState = ConstantesActivity.CONNECTIONOK;
            sndActivity.putExtra(MainActivity.EMAIL, connectionState);
            startActivity(sndActivity);
        } else {
            connectionState = ConstantesActivity.CONNECTIONKO;
        }

        Toast.makeText(this, connectionState, Toast.LENGTH_LONG).show();
    }
}
