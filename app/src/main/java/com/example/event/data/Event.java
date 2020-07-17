package com.example.event.data;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class Event {

    private int id;
    private String title;
    private String content;
    private OtherUser createdUser;
    private String status;
    private List<OtherUser> attnUsers;
    private String created_time;
    private String venue;
    private String start_time;
    private String end_time;

    @Inject
    public Event(){}

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId() == event.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedUser(OtherUser createdUser) {
        this.createdUser = createdUser;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAttnUsers(List<OtherUser> attnUsers) {
        this.attnUsers = attnUsers;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public OtherUser getCreatedUser() {
        return createdUser;
    }

    public String getStatus() {
        return status;
    }

    public List<OtherUser> getAttnUsers() {
        return attnUsers;
    }


}
