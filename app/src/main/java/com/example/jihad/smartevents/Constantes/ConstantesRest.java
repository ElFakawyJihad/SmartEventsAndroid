package com.example.jihad.smartevents.Constantes;

/**
 * Created by Jihad on 15/11/2016.
 */

public class ConstantesRest {
    public static final String GETMESSAGE               = "message";
    public static final String EMAIL                    = "email";
    public static final String PASSWORD                 = "password";
    public static final String FIRST_NAME               = "firstname";
    public static final String LAST_NAME                = "lastname";
    public static final String BIRTHDAY                 = "birthday";
    public static final String GENDER                   = "gender";

    //Constantes Inscription-----------------------------------
    public static final String INSCRIPTIONURL           = "https://smarteventiagl.herokuapp.com/inscription";
    public static final String INSCRIPTIONOK            = "OK";

    //Constantes Connection------------------------------------
    public static final String CONNECTIONURL            = "https://smarteventiagl.herokuapp.com/connection";
    public static final String IDENTIFICATIONOK         = "OK";


    //Constantes Création d'un évenement------------------------
    public static final String CREATE_EVENT_URL         = "https://smarteventiagl.herokuapp.com/create_event";
    public static final String CREATEEVENTOK            = "OK";
    public static final String eventTitle               = "eventTitle";
    public static final String eventCategory            = "eventCategory";
    public static final String eventDescription         = "eventDescription";
    public static final String eventDate                = "eventDate";
    public static final String eventCapacity            = "eventCapacity";
    public static final String eventLocalisation        = "eventLocalisation";
    public static final String localisationLatitude     = "localisationLat";
    public static final String localisationLongitude    = "localisationLong";

    //Constantes Avoir les évènements proches-------------------
    public static final String GET_EVENT_NEAR_URL       = "https://smarteventiagl.herokuapp.com/get_event_near";
    public static final String LATITUDE                 = "latitude";
    public static final String LONGITUDE                = "longitude";

    //Constantes Joindre évènement-----------------------------
    public static final String JOIN_EVENT_URL           = "https://smarteventiagl.herokuapp.com/join_event";
    public static final String EVENT_ID                 = "event_id";



}
