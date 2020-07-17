package com.example.event.dagger.modules;

import com.example.event.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginActivityModule {
    @ContributesAndroidInjector
    public abstract LoginActivity contributeLoginActivityInjector();
}
