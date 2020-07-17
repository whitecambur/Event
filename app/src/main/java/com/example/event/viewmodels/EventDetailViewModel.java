package com.example.event.viewmodels;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.event.api.AccountService;
import com.example.event.api.EventService;
import com.example.event.data.Event;
import com.example.event.repositories.EventRepository;
import com.example.event.repositories.RecRepository;
import com.example.event.repositories.UserRepository;

import javax.inject.Inject;

public class EventDetailViewModel extends ViewModel {
    private EventRepository eventRepository;
    private UserRepository userRepository;

    private LiveData<Event> event;
    private LiveData<Integer> Button;

    private MutableLiveData<Integer> eventID;

    @Inject
    public EventDetailViewModel(EventRepository eventRepository,UserRepository userRepository){
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public void init(int userID,int eventID){
        this.eventID = new MutableLiveData<>();
        this.event = Transformations.switchMap(this.eventID, new Function<Integer, LiveData<Event>>() {
            @Override
            public LiveData<Event> apply(Integer eventID) {
                return eventRepository.getEvent(eventID);
            }
        });

        this.Button = Transformations.switchMap(this.eventID, new Function<Integer, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(Integer eventID) {
                return userRepository.isAttn(userID,eventID);

            }
        });

        this.eventID.setValue(eventID);

    }

    public LiveData<Event> getEvent() { return this.event; }

    public LiveData<Integer> getButton() { return this.Button; }

    public void refreshEvent() { this.eventID.setValue(this.eventID.getValue()); }
}
