package com.example.event.dagger.modules;

import com.example.event.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class RegisterActivityModule {
    @ContributesAndroidInjector
    public abstract RegisterActivity contributeRegisterActivityInjector();

}