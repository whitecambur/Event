package com.example.event.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.event.EventDetailActivity;
import com.example.event.OtherUserDetail;
import com.example.event.R;
import com.example.event.data.Event;
import com.example.event.data.OtherUser;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Event> events;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView org;
        TextView time;
        TextView attns;

        public ViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.event_item_title);
            org = (TextView)view.findViewById(R.id.event_item_org);
            time = (TextView)view.findViewById(R.id.event_item_time);
            attns = (TextView)view.findViewById(R.id.event_item_attns);
        }
    }

    public HomeAdapter(List<Event> events){
        this.events = events;
    }

    public void setEvents(List<Event> events) { this.events = events; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int pos = holder.getAdapterPosition();
                Event event = events.get(pos);
                Activity activity = (Activity)v.getContext();
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("event_id",event.getId());
                activity.startActivity(intent);
            }
        });
        holder.org.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int pos = holder.getAdapterPosition();
                Event event = events.get(pos);
                Activity activity = (Activity)v.getContext();
                Intent intent = new Intent(activity, OtherUserDetail.class);
                intent.putExtra("org_id",event.getCreatedUser().getId());
                activity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Event event = events.get(position);
        holder.title.setText(event.getTitle());
        holder.org.setText(event.getCreatedUser().getUsername());
//        holder.time.setText(event.getStart_time().concat(event.getEnd_time()));
        holder.time.setText(event.getStart_time());
        List<OtherUser> attnUsers = event.getAttnUsers();
        if(attnUsers.size()==0){
            holder.attns.setText("None");
            return;
        }
        String attnUserNames = "";
        for(int i =0;i<attnUsers.size();i++){
            attnUserNames = attnUserNames.concat(attnUsers.get(i).getUsername()).concat(",");
        }
        holder.attns.setText(attnUserNames);
    }

    @Override
    public int getItemCount(){
        return events.size();
    }
}
