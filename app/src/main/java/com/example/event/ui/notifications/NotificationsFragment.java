package com.example.event.ui.notifications;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.event.CreateEventActivity;
import com.example.event.R;
import com.example.event.data.Event;
import com.example.event.data.User;
import com.example.event.viewmodels.MainNotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class NotificationsFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainNotificationsViewModel mainNotificationsViewModel;

    private NotificationAdapter adapter;

    @Inject
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView notificationsRecycleView = root.findViewById(R.id.main_notifications_recycle);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        notificationsRecycleView.setLayoutManager(layoutManager);
        notificationsRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        this.adapter = new NotificationAdapter(new ArrayList<Event>());
        notificationsRecycleView.setAdapter(this.adapter);

        mainNotificationsViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainNotificationsViewModel.class);
        mainNotificationsViewModel.init(user.getId());
        mainNotificationsViewModel.getEvents().observe(this,new Observer<List<Event>>(){
            @Override
            public void onChanged(List<Event> events){
                adapter.setEvents(events);
                adapter.notifyDataSetChanged();
            }
        });
        mainNotificationsViewModel.refreshEvents();

        Button create_event = root.findViewById(R.id.main_notifications_create_event);
        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity)v.getContext();
                Intent intent = new Intent(activity, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context){
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}