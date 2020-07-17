package com.example.event.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.event.R;
import com.example.event.data.Event;
import com.example.event.data.User;
import com.example.event.viewmodels.MainHomeViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class HomeFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;



    private MainHomeViewModel mainHomeViewModel;

    private HomeAdapter adapter;

    @Inject
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView homeRecycleView = root.findViewById(R.id.main_home_recycle);
        SwipeRefreshLayout homeSwipeRefresh = root.findViewById(R.id.main_home_swipe);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        homeRecycleView.setLayoutManager(layoutManager);
        homeRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        this.adapter = new HomeAdapter(new ArrayList<Event>());
        homeRecycleView.setAdapter(this.adapter);

        mainHomeViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainHomeViewModel.class);
        mainHomeViewModel.init(user.getId());
        mainHomeViewModel.getEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.setEvents(events);
                adapter.notifyDataSetChanged();
                homeSwipeRefresh.setRefreshing(false);
            }
        });
        mainHomeViewModel.refreshEvents();

        homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainHomeViewModel.refreshEvents();
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