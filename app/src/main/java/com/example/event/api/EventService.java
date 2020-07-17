package com.example.event.api;

import com.example.event.data.Event;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {
    @FormUrlEncoded
    @POST("event/create")
    Call<Receive<Event>> createEvent(@Field("id") int id,@Field("title") String title,@Field("venue") String venue,
                                     @Field("status") String status, @Field("start") String start,
                                     @Field("end") String end,@Field("content") String content);

    @GET("event/{event_id}/detail")
    Call<Receive<Event>> eventDetail(@Path("event_id") int eventID);

    @FormUrlEncoded
    @POST("event/attn")
    Call<Receive<Integer>> attnEvent(@Field("id") int id, @Field("event_id") int eventID);

    @FormUrlEncoded
    @POST("event/deattn")
    Call<Receive<Integer>> deattnEvent(@Field("id") int id, @Field("event_id") int eventID);

    @FormUrlEncoded
    @POST("event/waitattn")
    Call<Receive<Integer>> waitAttnEvent(@Field("id") int id, @Field("event_id") int eventID);

    @FormUrlEncoded
    @POST("event/dewaitattn")
    Call<Receive<Integer>> dewaitAttnEvent(@Field("id") int id, @Field("event_id") int eventID);
}
