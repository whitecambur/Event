package com.example.event.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.event.R;
import com.example.event.data.Event;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Event> events;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView time;

        public ViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.event_title_time_title);
            time = (TextView)view.findViewById(R.id.event_title_time_time);

        }
    }

    public NotificationAdapter(List<Event> events) { this.events = events; }

    public void setEvents(List<Event> events) { this.events = events; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_title_time,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Event event = events.get(position);
        holder.title.setText(event.getTitle());
        holder.time.setText(event.getStart_time());
    }

    @Override
    public int getItemCount(){ return events.size(); }
}
