package com.example.event.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.event.R;
import com.example.event.api.AccountService;
import com.example.event.api.Receive;
import com.example.event.data.Event;
import com.example.event.data.User;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private AccountService accountService;

    @Inject
    public UserRepository(AccountService accountService){
        this.accountService = accountService;
    }

    @Inject
    User user;

    public LiveData<List<Event>> getWaitEvents(int userID){
        final MutableLiveData<List<Event>> events = new MutableLiveData<>();
        accountService.getWaitEvent(userID).enqueue(new Callback<Receive<List<Event>>>(){
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

    public LiveData<Integer> isAttn(int userID,int eventID){
        final MutableLiveData<Integer> text = new MutableLiveData<>();
        accountService.isAttn(eventID,userID).enqueue(new Callback<Receive<Integer>>(){
            @Override
            public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                if(response.isSuccessful()){
                    switch(response.body().getData()){
                        case 0:
                            text.setValue(0);
                            break;
                        case 1:
                            text.setValue(1);
                            Log.e("userRepository",String.valueOf(1));
                            break;
                        case -1:
                            text.setValue(-1);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Receive<Integer>> call, Throwable t){

            }
        });
        return text;
    }

    public LiveData<List<Event>> getEvents(int userID){
        final MutableLiveData<List<Event>> events = new MutableLiveData<>();
        accountService.getEvent(userID).enqueue(new Callback<Receive<List<Event>>>(){
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
