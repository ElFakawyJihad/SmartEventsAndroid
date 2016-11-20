package com.example.jihad.smartevents.rest;

import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.REST.RESTInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jihad on 15/11/2016.
 */

public class UserRest extends RESTInterface{


    public boolean connection(String userMail, String password){
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(ConstantesRest.EMAIL, userMail);
        parameters.put(ConstantesRest.PASSWORD,password);

        String result=post(ConstantesRest.CONNECTIONURL, parameters);

        if (result.equals(ConstantesRest.IDENTIFICATIONOK)){
            return true;
        }
        return false;
    }

}
