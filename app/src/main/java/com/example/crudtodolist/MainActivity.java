package com.example.crudtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
    TextView emailuser,nameuser;
    Call<List<User>> calll;
    Call<List<User>> call;
    Api service;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);

        emailuser = (TextView)findViewById(R.id.emailuser);
        nameuser = (TextView)findViewById(R.id.nameuser);
        listView = (ListView)findViewById(R.id.list);
        service = RetrofitClient.createService(Api.class);


//        calll = service.userlogin(getIntent().getStringExtra("email"));
//        calll.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                emailuser.setText(response.body().get(0).getEmail());
//                nameuser.setText(response.body().get(0).getName());
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//
//            }
//        });
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
        }

    }
    private void isiData(List<User> user){
        UserAdapter = new UserAdapter(getApplication(), user);
        listView.setAdapter(UserAdapter);
    }
}