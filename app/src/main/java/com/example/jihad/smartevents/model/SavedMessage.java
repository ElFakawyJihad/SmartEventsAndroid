package com.example.jihad.smartevents.model;



/**
 * Created by ALLO on 3/8/16.
 */

public class SavedMessage {

    private String body;

    private String userId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SavedMessage(){
        super();
    }
}
