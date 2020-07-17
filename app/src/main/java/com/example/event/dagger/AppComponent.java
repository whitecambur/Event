package com.example.event.dagger;

import com.example.event.App;
import com.example.event.CreateEventActivity;
import com.example.event.EventDetailActivity;
import com.example.event.RegisterActivity;
import com.example.event.dagger.modules.CreateEventActivityModule;
import com.example.event.dagger.modules.DashboardFragmentModule;
import com.example.event.dagger.modules.EventDetailActivityModule;
import com.example.event.dagger.modules.HomeFragmentModule;
import com.example.event.dagger.modules.LoginActivityModule;
import com.example.event.dagger.modules.MainActivityModule;
import com.example.event.dagger.modules.NotificationsFragmentModule;
import com.example.event.dagger.modules.RegisterActivityModule;
import com.example.event.ui.notifications.NotificationsFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules={
        AndroidInjectionModule.class,
        AppModule.class,

        LoginActivityModule.class,
        MainActivityModule.class,
        EventDetailActivityModule.class,
        CreateEventActivityModule.class,
        RegisterActivityModule.class,

        HomeFragmentModule.class,
        DashboardFragmentModule.class,
        NotificationsFragmentModule.class,

        ViewModelModule.class
})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {}

}
