package com.example.event.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.event.EventDetailActivity;
import com.example.event.R;
import com.example.event.api.RecService;
import com.example.event.api.Receive;
import com.example.event.data.Event;
import com.example.event.data.User;
import com.example.event.viewmodels.MainDashboardViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainDashboardViewModel mainDashboardViewModel;

    @Inject
    RecService recService;

    private DashboardAdapter adapter;

    private Button button;
    private TextView textView;

    @Inject
    User user;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView dashboardRecycleView = root.findViewById(R.id.main_dashboard_recycle);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        dashboardRecycleView.setLayoutManager(layoutManager);
        this.adapter = new DashboardAdapter(new ArrayList<Event>());
        dashboardRecycleView.setAdapter(this.adapter);
        dashboardRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mainDashboardViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainDashboardViewModel.class);
        mainDashboardViewModel.init(user.getId());
        mainDashboardViewModel.getWaitEvents().observe(this,new Observer<List<Event>>(){
            @Override
            public void onChanged(List<Event> events){
                adapter.setEvents(events);
                adapter.notifyDataSetChanged();
            }
        });
        mainDashboardViewModel.refreshWaitEvents();
        button = root.findViewById(R.id.main_dashboard_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Event> events = adapter.getCueEvents();

                List<Integer> eventIDs = new ArrayList<>();
                for(int i=0;i<events.size();i++){
                    eventIDs.add(events.get(i).getId());
                }
                Gson gson = new Gson();
                String eventIDString = gson.toJson(eventIDs);
                Log.e("dashboardFragment",eventIDString);
                recService.getRec(user.getId(),eventIDString).enqueue(new Callback<Receive<Event>>(){
                    @Override
                    public void onResponse(Call<Receive<Event>> call, Response<Receive<Event>> response){
                        if(response.isSuccessful()) {
                            int eventID = response.body().getData().getId();
                            Activity activity = (Activity)v.getContext();
                            Intent intent = new Intent(activity, EventDetailActivity.class);
                            intent.putExtra("event_id",eventID);
                            activity.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Receive<Event>> call, Throwable t){

                    }
                });
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