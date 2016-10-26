package com.example.jihad.smartevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Jose on 23/10/2016.
 */

public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent i = getIntent();

        String email = i.getStringExtra(MainActivity.EMAIL);

        TextView text = (TextView) findViewById(R.id.email);
        text.setText(email);
    }
}
