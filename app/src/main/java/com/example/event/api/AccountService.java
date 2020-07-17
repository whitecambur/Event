package com.example.event.api;

import com.example.event.data.Event;
import com.example.event.data.OtherUser;
import com.example.event.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountService {
    @FormUrlEncoded
    @POST("account/login")
    Call<Receive<User>> login(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("account/register")
    Call<Receive<User>> register(@Field("username") String userName, @Field("password") String password, @Field("email") String email);

    @GET("account/detail/{id}")
    Call<Receive<OtherUser>> getOtherUserDetail(@Path("id") int userID);

    @FormUrlEncoded
    @POST("account/detail")
    Call<Receive<User>> getUserDetail();

    @GET("account/waitevent/{user_id}")
    Call<Receive<List<Event>>> getWaitEvent(@Path("user_id") int userID);

    @GET("account/event/{user_id}")
    Call<Receive<List<Event>>> getEvent(@Path("user_id") int userID);

    @GET("account/attn/{event_id}")
    Call<Receive<Integer>> isAttn(@Path("event_id") int eventID,@Query("id") int id);  // 0 not attn 1 attn -1 wait attn
}
