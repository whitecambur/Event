package com.example.event.api;

import com.example.event.data.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecService {
    @GET("rec/{id}")
    Call<Receive<List<Event>>> getRecs(@Path("id") int userID);

    @FormUrlEncoded
    @POST("rec/{id}")
    Call<Receive<Event>> getRec(@Path("id") int userID, @Field("events") String events);
}
