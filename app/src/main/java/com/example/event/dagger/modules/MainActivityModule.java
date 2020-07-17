package com.example.event.dagger.modules;

import com.example.event.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivityModule();
}