package com.example.event;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_username_editText)
    EditText register_user_name_editText;

    @BindView(R.id.register_password_editText)
    EditText register_password_editText;

    @BindView(R.id.register_email_editText)
    EditText register_email_editText;

    @Inject
    AccountService accountService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @OnClick(R.id.register_button)
    public void register(){
        if(TextUtils.isEmpty(register_email_editText.getText())
        || TextUtils.isEmpty(register_password_editText.getText())
        || TextUtils.isEmpty(register_user_name_editText.getText())){
            Toast.makeText(RegisterActivity.this,"不得为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String username = register_user_name_editText.getText().toString();
        String password = register_password_editText.getText().toString();
        String email = register_email_editText.getText().toString();

        accountService.register(username,password,email).enqueue(new Callback<Receive<User>>() {
            @Override
            public void onResponse(Call<Receive<User>> call, Response<Receive<User>> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Receive<User>> call, Throwable t) {

            }
        });
    }
}
