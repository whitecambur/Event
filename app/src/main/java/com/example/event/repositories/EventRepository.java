package com.example.event.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.event.api.EventService;
import com.example.event.api.Receive;
import com.example.event.data.Event;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class EventRepository {
    private EventService eventService;

    @Inject
    public EventRepository(EventService eventService){ this.eventService =eventService; }

    public LiveData<Event> getEvent(int eventID){
        final MutableLiveData<Event> event = new MutableLiveData<>();

        eventService.eventDetail(eventID).enqueue(new Callback<Receive<Event>>(){
            @Override
            public void onResponse(Call<Receive<Event>> call, Response<Receive<Event>> response){
                Log.e("eventDetailActivity","onresponse");
                if(response.isSuccessful()){
                    event.setValue(response.body().getData());
                    Log.e("eventDetailActivity","success");
                }
            }

            @Override
            public void onFailure(Call<Receive<Event>> call, Throwable t){
                Log.e("eventDetailActivity","failure");
            }
        });
        return event;
    }
}
