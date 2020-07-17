package com.example.event.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.event.api.RecService;
import com.example.event.api.Receive;
import com.example.event.data.Event;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.event.data.User;
import com.google.gson.Gson;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

@Singleton
public class RecRepository {
    private RecService recService;

    @Inject
    public RecRepository(RecService recService){
        this.recService = recService;
    }

    @Inject
    User user;

    public LiveData<Event> getRecEvent(List<Event> events){
        final MutableLiveData<Event> event = new MutableLiveData<>();

        List<Integer> eventIDs = new ArrayList<>();
        for(int i=0;i<events.size();i++){
            eventIDs.add(events.get(i).getId());
        }
        Gson gson = new Gson();
        String eventIDString = gson.toJson(eventIDs);
        recService.getRec(user.getId(),eventIDString).enqueue(new Callback<Receive<Event>>(){
            @Override
            public void onResponse(Call<Receive<Event>> call, Response<Receive<Event>> response){
                if(response.isSuccessful()) {
                    event.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Receive<Event>> call, Throwable t){

            }
        });

        return event;
    }

    public LiveData<List<Event>> getRecEvents(){
        final MutableLiveData<List<Event>> events = new MutableLiveData<>();
        Log.e("RecRepository",String.valueOf(user.getId()));
        recService.getRecs(user.getId()).enqueue(new Callback<Receive<List<Event>>>(){
            @Override
            public void onResponse(Call<Receive<List<Event>>> call, Response<Receive<List<Event>>> response){
                if(response.isSuccessful()){
                    events.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Receive<List<Event>>> call, Throwable t){

            }
        });

        return events;
    }


}
