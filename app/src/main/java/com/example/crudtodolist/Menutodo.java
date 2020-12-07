package com.example.crudtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudtodolist.Adapter.TodoAdapter;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menutodo extends AppCompatActivity {
    TextView emailtodo;
    ImageButton create,tong;
    private ListView listView;
    Call<List<Todo>> call;
    Api service;
    private TodoAdapter TodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menutodo);
        listView = (ListView) findViewById(R.id.todolist);
        emailtodo = (TextView)findViewById(R.id.emailtodo);
        create = (ImageButton)findViewById(R.id.createtodo);
        tong = (ImageButton)findViewById(R.id.tongsampah);
        emailtodo.setText(getIntent().getStringExtra("email"));
        service = RetrofitClient.createService(Api.class);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menutodo.this,CreateTodo.class);
                i.putExtra("userid",getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
        tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menutodo.this,Tong.class);
                i.putExtra("userid",getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
        SweetAlertDialog pDialog = new SweetAlertDialog(Menutodo.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Tunggu Sebentar");
        pDialog.setCancelable(false);
        pDialog.show();

        call = service.todoid(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()){
                    IsiData(response.body());
                    pDialog.dismiss();
                }
                else {
                    if (response.code()== 401){
                        Alerter.create(Menutodo.this)
                                .setTitle("Data Tidak Ada !!")
                                .setBackgroundColor(R.color.red_btn_bg_pressed_color)
                                .setIcon(R.drawable.ic_notifications)
                                .setDuration(5000)
                                .show();
                        pDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {

            }
        });
    }
    public void IsiData(List<Todo>todos){
        TodoAdapter = new TodoAdapter(getApplication(),todos);
        listView.setAdapter(TodoAdapter);
    }
}