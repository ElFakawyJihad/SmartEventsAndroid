package com.example.jihad.smartevents.rest;

import com.example.jihad.smartevents.Constantes.ConstantesRest;

import java.util.HashMap;
import java.util.Map;

import static com.example.jihad.smartevents.rest.RestInterface.post;

/**
 * Created by Jihad on 15/11/2016.
 */

public class UserRest extends RestInterface {


    public String connection(String userMail, String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD,password);

        String result=post(ConstantesRest.CONNECTIONURL, parameters);
        return result;
    }
    public String inscription(String userMail,String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD,password);

        String result=post(ConstantesRest.CONNECTIONURL, parameters);
        return result;
    }

    public String createNewEvent(String title, String eventCategory, String description, String date, String capacity, String localisation,double lat, double lng){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.eventTitle, title);
        parameters.put(ConstantesRest.eventCategory, eventCategory);
        parameters.put(ConstantesRest.eventDescription, description);
        parameters.put(ConstantesRest.eventDate, date);
        parameters.put(ConstantesRest.eventCapacity, capacity);
        parameters.put(ConstantesRest.eventLocalisation, localisation);
        parameters.put(ConstantesRest.localisationLatitude, String.valueOf(lat));
        parameters.put(ConstantesRest.localisationLongitude, String.valueOf(lng));

        String result = new UserRest().post(ConstantesRest.ADDNEWEVENTURL, parameters);
        return result;
    }
}
