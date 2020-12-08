package com.example.crudtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crudtodolist.Adapter.TodoAdapter;
import com.example.crudtodolist.Adapter.UserAdapter;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Model.User;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserAdapter UserAdapter;
    private ListView listView;
    TextView emailuser;
    ImageButton BtnLogout;
    Call<List<User>> call;
    Api service;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);
        String email = sharedPrefManager.getSpEmail();

        emailuser = (TextView)findViewById(R.id.emailuser);
        BtnLogout = (ImageButton)findViewById(R.id.BtnLogout);
        listView = (ListView)findViewById(R.id.list);
        service = RetrofitClient.createService(Api.class);

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Keluar Dari Aplikasi ??")
                        .setConfirmText("Yes!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent i = new Intent(MainActivity.this,Login.class);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_CEK_LOGIN,false);
                                startActivity(i);
                                finish();
                            }
                        }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                        .show();
            }
        });

        emailuser.setText(email);
        SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Tunggu Sebentar");
        pDialog.setCancelable(false);
        pDialog.show();
        call = service.datauser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                isiData(response.body());
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(TAG, t.getMessage());
                pDialog.dismiss();
            }
        });

        if (!sharedPrefManager.getSPCekLogin()){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

    }
    private void isiData(List<User> user){
        UserAdapter = new UserAdapter(getApplication(), user);
        listView.setAdapter(UserAdapter);
    }
}