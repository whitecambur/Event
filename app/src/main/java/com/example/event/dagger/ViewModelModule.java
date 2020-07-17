package com.example.event.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.event.dagger.annotations.ViewModelKey;
import com.example.event.dagger.lifecyle.ViewModelFactory;
import com.example.event.viewmodels.EventDetailViewModel;
import com.example.event.viewmodels.MainDashboardViewModel;
import com.example.event.viewmodels.MainHomeViewModel;
import com.example.event.viewmodels.MainNotificationsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainHomeViewModel.class)
    abstract ViewModel bindMainHomeViewModel(MainHomeViewModel mainHomeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainDashboardViewModel.class)
    abstract ViewModel bindMainDashboardViewModel(MainDashboardViewModel mainDashboardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainNotificationsViewModel.class)
    abstract ViewModel bindMainNotificationsViewModel(MainNotificationsViewModel mainNotificationsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailViewModel.class)
    abstract ViewModel bindEventDetailViewModel(EventDetailViewModel eventDetailViewModel);
}
