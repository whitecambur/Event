package com.example.event.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.event.R;
import com.example.event.data.Event;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>{
    private List<Event> events;
    private List<Event> cueEvents;

    static class ViewHolder extends RecyclerView.ViewHolder {
       TextView textView;
       ImageView imageView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.event_title_TextView);
            imageView = (ImageView)view.findViewById(R.id.event_title_mark);
        }
    }

    public DashboardAdapter(List<Event> events) { this.events = events; }

    public void setEvents(List<Event> events) { this.events = events; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_title,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        cueEvents = new ArrayList<>();
        holder.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Event event = events.get(pos);
                if(cueEvents.contains(event)){
                    cueEvents.remove(event);
                    holder.imageView.setVisibility(View.GONE);
                }
                else{
                    cueEvents.add(event);
                    holder.imageView.setVisibility(View.VISIBLE);
                }
                Log.e("dashboardadapter",String.valueOf(cueEvents.size()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Event event = events.get(position);
        holder.textView.setText(event.getTitle());
        holder.imageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {return events.size(); }

    public int getCueItemCount(){ return cueEvents.size(); }

    public List<Event> getCueEvents(){ return cueEvents; }

}
