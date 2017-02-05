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

        String result = post(ConstantesRest.INSCRIPTIONURL, parameters);
        return result;
    }

    public static String connection(String userMail, String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD, password);


        String result = post(ConstantesRest.CONNECTIONURL, parameters);
        return result;
    }

    public static String createEvent(String title, String eventCategory, String description, String date, String capacity, String localisation,double lat, double lng){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.eventTitle, title);
        parameters.put(ConstantesRest.eventCategory, eventCategory);
        parameters.put(ConstantesRest.eventDescription, description);
        parameters.put(ConstantesRest.eventDate, date);
        parameters.put(ConstantesRest.eventCapacity, capacity);
        parameters.put(ConstantesRest.eventLocalisation, localisation);
        parameters.put(ConstantesRest.localisationLatitude, String.valueOf(lat));
        parameters.put(ConstantesRest.localisationLongitude, String.valueOf(lng));

        String result = post(ConstantesRest.CREATE_EVENT_URL, parameters);
        return result;
    }

    public static String getEventNear(String latitude, String longitude) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.LATITUDE, latitude);
        parameters.put(ConstantesRest.LONGITUDE, longitude);

        String result = get(ConstantesRest.GET_EVENT_NEAR_URL, parameters);
        return result;
    }

    public static String joinEvent(String event_id, String email) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EVENT_ID, event_id);
        parameters.put(ConstantesRest.EMAIL, email);

        String result = post(ConstantesRest.JOIN_EVENT_URL, parameters);
        return result;
    }

    public static String getEventMessages(String event_id) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EVENT_CHAT_ID, event_id);

        String result = post(ConstantesRest.GET_EVENT_MESSAGES_URL, parameters);
        return result;
    }

    public static String addEventMessage(String event_id, String text, String user_email, String date) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EVENT_CHAT_ID, event_id);
        parameters.put(ConstantesRest.MESSAGE_CONTENT, text);
        parameters.put(ConstantesRest.MESSAGE_USER_EMAIL, user_email);
        parameters.put(ConstantesRest.MESSAGE_DATE, date);

        String result = post(ConstantesRest.ADD_EVENT_MESSAGE_URL, parameters);
        return result;
    }
}
