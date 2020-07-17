package com.example.event.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.event.data.Event;
import com.example.event.repositories.RecRepository;

import java.util.List;

import javax.inject.Inject;

public class MainHomeViewModel extends ViewModel{
    private RecRepository recRepository;
    private LiveData<List<Event>> events;
    private LiveData<Event> event;
    private MutableLiveData<Integer> userID;

    @Inject
    public MainHomeViewModel(RecRepository recRepository) { this.recRepository = recRepository; }

    public void init(int userID){
        this.userID = new MutableLiveData<>();
        this.events = Transformations.switchMap(this.userID, new Function<Integer, LiveData<List<Event>>>(){
            @Override
            public LiveData<List<Event>> apply(Integer userID){
                return recRepository.getRecEvents();
            }
        });
        this.userID.setValue(userID);
    }

    public LiveData<List<Event>> getEvents(){
        return this.events;
    }

    public void refreshEvents(){
        this.userID.setValue(this.userID.getValue());
    }
}