package com.example.event;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.event.api.AccountService;
import com.example.event.api.EventService;
import com.example.event.api.Receive;
import com.example.event.data.Event;
import com.example.event.data.OtherUser;
import com.example.event.data.User;
import com.example.event.viewmodels.EventDetailViewModel;
import com.example.event.viewmodels.MainHomeViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    @BindView(R.id.event_detail_status)
    TextView event_detail_status;
    @BindView(R.id.event_detail_content)
    TextView event_detail_content;
    @BindView(R.id.event_detail_attnuser)
    TextView event_detail_attnuser;
    @BindView(R.id.event_detail_created_user)
    TextView event_detail_created_user;

    @BindView(R.id.event_detail_wait_attn)
    Button event_detail_wait_attn;

    @BindView(R.id.event_detail_attn)
    Button event_detail_attn;

    private int eventID;
    private Event event;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    User user;
    @Inject
    EventService eventService;
    @Inject
    AccountService accountService;

    private EventDetailViewModel eventDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        Intent receivedIntent = this.getIntent();
        eventID =receivedIntent.getIntExtra("event_id",-1);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        this.event_detail_wait_attn.setText(getString(R.string.nowaitattn_button));
        this.event_detail_attn.setText(getString(R.string.noattn_button));
        Log.e("eventDetailActivity", this.event_detail_attn.getText().toString());

        eventDetailViewModel = ViewModelProviders.of(this,viewModelFactory).get(EventDetailViewModel.class);
        eventDetailViewModel.init(user.getId(),eventID);
        eventDetailViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                refreshEventDetail(event);
            }
        });
        eventDetailViewModel.getButton().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                refreshButton(integer);
            }
        });

        eventDetailViewModel.refreshEvent();

    }

    public void refreshButton(Integer integer){
        switch(integer){
            case 0:
                this.event_detail_wait_attn.setText(getString(R.string.nowaitattn_button));
                this.event_detail_attn.setText(getString(R.string.noattn_button));
                Log.e("eventDetailActivity",this.event_detail_attn.getText().toString());
                return;
            case 1:
                this.event_detail_attn.setText(getString(R.string.attn_button));
                return;
            case -1:
                this.event_detail_wait_attn.setText(getString(R.string.waitattn_button));
                return;
        }

    }

    public void refreshEventDetail(Event event){
        this.event = event;
        this.setTitle(event.getTitle());
        List<OtherUser> attnUsers = event.getAttnUsers();
        String attnUserNames = "";
        for(int i =0;i<attnUsers.size();i++){
            attnUserNames = attnUserNames.concat(attnUsers.get(i).getUsername()).concat(",");
        }
        this.event_detail_attnuser.setText(attnUserNames);
        this.event_detail_content.setText(event.getContent());
        this.event_detail_status.setText(event.getStatus());
        this.event_detail_created_user.setText(event.getCreatedUser().getUsername());
    }

    @OnClick(R.id.event_detail_attn)
    public void attn(){
        accountService.isAttn(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>(){
            @Override
            public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                if(response.isSuccessful()){
                    switch(response.body().getData()){
                        case 0:
                            eventService.attnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>(){
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                                    if(response.isSuccessful()){
                                        event_detail_attn.setText(getString(R.string.attn_button));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t){

                                }
                            });
                            break;
                        case 1:
                            eventService.deattnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>(){
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                                    if(response.isSuccessful()){
                                        if (response.body().getCode()==1){
                                            event_detail_attn.setText(getString(R.string.noattn_button));

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t){

                                }
                            });
                            break;
                        case -1:
                            eventService.attnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>() {
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response) {
                                    if(response.isSuccessful()){
                                        event_detail_attn.setText(getString(R.string.attn_button));
                                        event_detail_wait_attn.setText(getString(R.string.nowaitattn_button));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t) {

                                }
                            });
                    }
                }
            }

            @Override
            public void onFailure(Call<Receive<Integer>> call, Throwable t){

            }
        });

    }

    @OnClick(R.id.event_detail_wait_attn)
    public void waitAttn(){

        accountService.isAttn(eventID,user.getId()).enqueue(new Callback<Receive<Integer>>() {
            public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getData()) {
                        case 0:
                            eventService.waitAttnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>() {
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                                    if(response.isSuccessful()){
                                        event_detail_wait_attn.setText(getString(R.string.waitattn_button));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t){

                                }

                            });
                            break;
                        case -1:
                            eventService.dewaitAttnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>() {
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response){
                                    if(response.isSuccessful()){
                                        event_detail_wait_attn.setText(getString(R.string.nowaitattn_button));

                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t){

                                }

                            });
                            break;
                        case 1:
                            eventService.waitAttnEvent(user.getId(),eventID).enqueue(new Callback<Receive<Integer>>() {
                                @Override
                                public void onResponse(Call<Receive<Integer>> call, Response<Receive<Integer>> response) {
                                    if(response.isSuccessful()){
                                        event_detail_wait_attn.setText(getString(R.string.waitattn_button));
                                        event_detail_attn.setText(getString(R.string.noattn_button));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receive<Integer>> call, Throwable t) {

                                }
                            });

                    }
                }

            }
            @Override
            public void onFailure(Call<Receive<Integer>> call, Throwable t){

            }
        });


    }

}
