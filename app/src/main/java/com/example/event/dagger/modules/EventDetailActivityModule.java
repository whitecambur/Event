package com.example.event.dagger.modules;

import com.example.event.EventDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EventDetailActivityModule {
    @ContributesAndroidInjector
    public abstract EventDetailActivity contributeEventDetailActivityModule();
}