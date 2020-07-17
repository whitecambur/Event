package com.example.event.dagger.modules;

import com.example.event.LoginActivity;
import com.example.event.ui.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    public abstract HomeFragment contributeHomeFragmentInjector();
}