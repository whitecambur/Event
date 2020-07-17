package com.example.event.data;

import javax.inject.Inject;

public class OtherUser {
    private String username;
    private int id;

    @Inject
    public OtherUser(){}

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
