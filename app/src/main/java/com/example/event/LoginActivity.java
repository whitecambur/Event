package com.example.event;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.event.api.AccountService;
import com.example.event.api.Receive;
import com.example.event.data.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.username_editText)
    EditText username_editText;
    @BindView(R.id.password_editText)
    EditText password_editText;
    @BindView(R.id.login_button)
    Button login_button;
    @BindView(R.id.register_button)
    Button register_button;

    @Inject
    AccountService accountService;

    @Inject
    User user;

    private String userName, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @OnClick(R.id.login_button)
    public void Login(){

        userName = username_editText.getText().toString();
        password = password_editText.getText().toString();
        accountService.login(userName, password).enqueue(new retrofit2.Callback<Receive<User>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Receive<User>> call, Response<Receive<User>> response) {
                Log.e("login_activity_test","connect success");
                switch(response.code()){
                    case 200:
                        user.setUsername(response.body().getData().getUsername());
                        user.setId(response.body().getData().getId());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<Receive<User>> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "请求失败!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.register_button)
    public void Register(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
