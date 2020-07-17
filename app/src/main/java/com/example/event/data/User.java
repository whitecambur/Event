package com.example.event.data;

import java.util.List;

import javax.inject.Inject;

public class User {
    private int id;
    private String username;
    private String token;
    private List<Event> wait_events;
    private List<Event> events;

    @Inject
    public User(){}

    public List<Event> getWait_events() {
        return wait_events;
    }

    public void setWait_events(List<Event> wait_events) {
        this.wait_events = wait_events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
