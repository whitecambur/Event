package com.example.event.dagger.modules;

import com.example.event.ui.home.HomeFragment;
import com.example.event.ui.notifications.NotificationsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotificationsFragmentModule {
    @ContributesAndroidInjector
    public abstract NotificationsFragment contributeNotificationsFragmentInjector();
}