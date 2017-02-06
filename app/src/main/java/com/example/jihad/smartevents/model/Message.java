package com.example.jihad.smartevents.model;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String USER_ID_KEY = "userId";

    public static final String TEXT = "text";
    public static final String USER_EMAIL = "user_email";
    public static final String DATE = "date";

    /* getters */
    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getText() {
        return getString(TEXT);
    }

    public String getUserEmail() {
        return getString(USER_EMAIL);
    }

    /* setters */
    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setText(String text) {
        put(TEXT, text);
    }

    public void setUserEmail(String userEmail) {
        put(USER_EMAIL, userEmail);
    }

    public void setCreatedAt(Date createdAt) {
        createdAt = createdAt;
    }
}
