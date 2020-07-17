package com.example.event.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.event.data.Event;
import com.example.event.repositories.RecRepository;
import com.example.event.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class MainDashboardViewModel extends ViewModel {

    private RecRepository recRepository;
    private UserRepository userRepository;
    private LiveData<List<Event>> events;
    private MutableLiveData<Integer> userID;

    @Inject
    public MainDashboardViewModel(RecRepository recRepository, UserRepository userRepository){
        this.recRepository = recRepository;
        this.userRepository = userRepository;
    }

    public void init(int userID){
        this.userID = new MutableLiveData<>();
        this.events = Transformations.switchMap(this.userID,new Function<Integer, LiveData<List<Event>>>(){
            @Override
            public LiveData<List<Event>> apply(Integer userID){
                return userRepository.getWaitEvents(userID);
            }
        });
        this.userID.setValue(userID);
    }

    public LiveData<List<Event>> getWaitEvents() { return this.events; }

    public void refreshWaitEvents(){
        this.userID.setValue(this.userID.getValue());
    }

}
