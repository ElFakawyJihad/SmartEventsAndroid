package com.example.jihad.smartevents.rest;

import com.example.jihad.smartevents.Constantes.ConstantesRest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jihad on 15/11/2016.
 */

public class UserRest extends RestInterface {

    public static String inscription(String userMail,String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD, password);
        //parameters.put(ConstantesRest.F)

        String result = RestInterface.post(ConstantesRest.INSCRIPTIONURL, parameters);
        return result;
    }

    public static String connection(String userMail, String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD, password);


        String result = RestInterface.post(ConstantesRest.CONNECTIONURL, parameters);
        return result;
    }

    public static String createNewEvent(String title, String eventCategory, String description, String date, String capacity, String localisation,double lat, double lng){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.eventTitle, title);
        parameters.put(eventCategory, eventCategory);
        parameters.put(ConstantesRest.eventDescription, description);
        parameters.put(ConstantesRest.eventDate, date);
        parameters.put(ConstantesRest.eventCapacity, capacity);
        parameters.put(ConstantesRest.eventLocalisation, localisation);
        parameters.put(ConstantesRest.localisationLatitude, String.valueOf(lat));
        parameters.put(ConstantesRest.localisationLongitude, String.valueOf(lng));

        String result = RestInterface.post(ConstantesRest.ADDNEWEVENTURL, parameters);
        return result;
    }

    public static String getEventNear(String latitude, String longitude) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.LATITUDE, latitude);
        parameters.put(ConstantesRest.LONGITUDE, longitude);

        String result = RestInterface.get(ConstantesRest.GET_EVENT_NEAR_URL, parameters);
        return result;
    }

    public static String joinEvent(String event_id, String email) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EVENT_ID, event_id);
        parameters.put(ConstantesRest.EMAIL, email);

        String result = RestInterface.post(ConstantesRest.JOIN_EVENT_URL, parameters);
        return result;
    }
}
