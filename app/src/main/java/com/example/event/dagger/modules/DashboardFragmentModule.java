package com.example.event.dagger.modules;

import com.example.event.ui.dashboard.DashboardFragment;
import com.example.event.ui.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DashboardFragmentModule {
    @ContributesAndroidInjector
    public abstract DashboardFragment contributeDashboardFragmentInjector();
}