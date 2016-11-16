package com.example.jihad.smartevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Map;

public class RegistrationFormActivity extends AppCompatActivity {

    int gender;// 0 = man 1 = woman

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.man:
                if (checked)
                    gender = 0;
                break;
            case R.id.woman:
                if (checked)
                    gender = 1;
                break;
        }
    }

    public void singUp(View view){
        Map<String, String> parameters = new HashMap<>();
        Intent intent = new Intent(this, RegistrationFormActivity.class);

        parameters = new HashMap<>();


        startActivity(intent);
    }
}
