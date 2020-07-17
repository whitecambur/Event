package com.example.event;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.event.api.EventService;
import com.example.event.api.Receive;
import com.example.event.data.Event;
import com.example.event.data.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {

    @BindView(R.id.create_event_title_editText)
    EditText titleEditText;

    @BindView(R.id.create_event__venue_editText)
    EditText venueEditText;

    @BindView(R.id.create_event_start_time_editText)
    EditText startEditText;

    @BindView(R.id.create_event_end_time_editText)
    EditText endEditText;

    @BindView(R.id.create_event_status_editText)
    EditText statusEditText;

    @BindView(R.id.create_event_content)
    EditText contentEditText;

    @Inject
    EventService eventService;

    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    @OnClick(R.id.create_event_button)
    public void create(){
        if(TextUtils.isEmpty(titleEditText.getText())
        || TextUtils.isEmpty(statusEditText.getText())
        || TextUtils.isEmpty(startEditText.getText())
        || TextUtils.isEmpty(endEditText.getText())
        || TextUtils.isEmpty(venueEditText.getText())
        || TextUtils.isEmpty(contentEditText.getText())){
            Toast.makeText(CreateEventActivity.this,"不得为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String title = titleEditText.getText().toString();
        String status = statusEditText.getText().toString();
        String venue = venueEditText.getText().toString();
        String start = startEditText.getText().toString();
        String end = endEditText.getText().toString();
        String content = contentEditText.getText().toString();

        eventService.createEvent(user.getId(),title,venue,status,start,end,content).enqueue(new Callback<Receive<Event>>() {
            @Override
            public void onResponse(Call<Receive<Event>> call, Response<Receive<Event>> response) {
                if(response.isSuccessful()){
                    int eventID = response.body().getData().getId();

                    Intent intent = new Intent(CreateEventActivity.this,EventDetailActivity.class);
                    intent.putExtra("event_id",eventID);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Receive<Event>> call, Throwable t) {

            }
        });



    }
}
