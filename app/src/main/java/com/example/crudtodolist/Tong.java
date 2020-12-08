package com.example.crudtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudtodolist.Adapter.TongAdapter;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tong extends AppCompatActivity {
    Call<List<Todo>> call;
    Call<ResponseBody>calll;
    Api api;
    ListView tonglist;
    TongAdapter TongAdapter;
    ImageButton restoreall,deleteall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong);

        tonglist = (ListView)findViewById(R.id.tonglist);
        restoreall =(ImageButton)findViewById(R.id.restoreall);
        deleteall = (ImageButton)findViewById(R.id.deleteall);
        api = RetrofitClient.createService(Api.class);

        restoreall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calll = api.restoreall();
                calll.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(Tong.this,"Semua Data di restore",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calll = api.deleteall();
                calll.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(Tong.this,"Semua Data di delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        SweetAlertDialog pDialog = new SweetAlertDialog(Tong.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Tunggu Sebentar");
        pDialog.setCancelable(false);
        pDialog.show();

        call = api.tongtodo(getIntent().getStringExtra("userid"));
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()){
                    IsiData(response.body());
                    pDialog.dismiss();
                }
                else {
                    if (response.code()== 401){
                        Alerter.create(Tong.this)
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
                Toast.makeText(Tong.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }
    public void IsiData(List<Todo> todos){
        TongAdapter = new TongAdapter(getApplication(),todos);
        tonglist.setAdapter(TongAdapter);
    }
}