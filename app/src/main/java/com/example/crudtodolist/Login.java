package com.example.crudtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crudtodolist.Model.User;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    Button login,register;
    EditText email,password;
    Api api;
    Call<ResponseBody> call;
    Prefrence Preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.registrasi);
        email = (EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);

        api = RetrofitClient.createService(Api.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = api.loginuser(email.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
//                            Preferences.setLoggedInUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
//                            Preferences.setLoggedInStatus(getBaseContext(),true);
                            Intent i = new Intent(Login.this,MainActivity.class);
                            i.putExtra("email",email.getText().toString());
                            startActivity(i);
                            finish();
                        }
                        else {
                            if(response.code()==401){
                                Alerter.create(Login.this)
                                        .setTitle("Email & Password Tidak Terdaftar!")
                                        .setText("Silahkan Registrasi Dulu !")
                                        .setBackgroundColor(R.color.red_btn_bg_pressed_color)
                                        .setIcon(R.drawable.ic_notifications)
                                        .setDuration(5000)
                                        .show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Alerter.create(Login.this)
                                .setTitle("Oops")
                                .setText(t.getMessage())
                                .setBackgroundColor(R.color.red_btn_bg_color)
                                .setIcon(R.drawable.ic_notifications)
                                .setDuration(5000)
                                .show();
                    }
                });
            }
        });
//        if (Preferences.getLoggedInStatus(getBaseContext())){
//            startActivity(new Intent(getBaseContext(),MainActivity.class));
//            finish();
//        }

    }
}