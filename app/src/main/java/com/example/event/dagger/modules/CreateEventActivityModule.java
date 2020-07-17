package com.example.event.dagger.modules;

import com.example.event.CreateEventActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CreateEventActivityModule {
    @ContributesAndroidInjector
    public abstract CreateEventActivity contributeCreateEventActivityInjector();
}