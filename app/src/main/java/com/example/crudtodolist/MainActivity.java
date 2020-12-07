package com.example.crudtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crudtodolist.Adapter.TodoAdapter;
import com.example.crudtodolist.Adapter.UserAdapter;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Model.User;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserAdapter UserAdapter;
    private ListView listView;
    TextView emailuser;
    Call<List<User>> calll;
    Call<List<User>> call;
    Api service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailuser = (TextView)findViewById(R.id.emailuser);
        listView = (ListView)findViewById(R.id.list);
        service = RetrofitClient.createService(Api.class);

        calll = service.userlogin(getIntent().getStringExtra("email"));
        calll.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                emailuser.setText(response.body().get(0).getId());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        call = service.datauser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                isiData(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(TAG, t.getMessage());
            }
        });

    }
    private void isiData(List<User> user){
        UserAdapter = new UserAdapter(getApplication(), user);
        listView.setAdapter(UserAdapter);
    }
}