package com.example.event.dagger;

import android.app.Application;

import com.example.event.App;
import com.example.event.api.AccountService;
import com.example.event.api.EventService;
import com.example.event.api.RecService;
import com.example.event.data.User;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {
    @Binds
    @Singleton
    abstract Application provideApplicationContext(App app);

    @Singleton
    @Provides
    public static User provideUser(){ return new User(); }

    @Singleton
    @Provides
    public static Retrofit provideRetrofit(){
        return new Retrofit.Builder().baseUrl("http://10.0.2.2:8000/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Singleton
    @Provides
    public static RecService provideRecService(Retrofit retrofit) {
        return retrofit.create(RecService.class);
    }

    @Singleton
    @Provides
    public static AccountService provideAccountService(Retrofit retrofit){
        return retrofit.create(AccountService.class);
    }

    @Singleton
    @Provides
    public static EventService provideEventService(Retrofit retrofit){
        return retrofit.create(EventService.class);
    }
}
