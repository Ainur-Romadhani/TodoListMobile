package com.example.crudtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.tapadoo.alerter.Alerter;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTodo extends AppCompatActivity {

    Button submit;
    EditText namecreate,startcreate,endcreate,proggrescreate;
    Api api;
    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        namecreate = (EditText)findViewById(R.id.namecreate);
        startcreate=(EditText)findViewById(R.id.startcreate);
        endcreate = (EditText)findViewById(R.id.end_datecreate);
        proggrescreate=(EditText)findViewById(R.id.proggresscreate);
        submit = (Button)findViewById(R.id.submit);
        api = RetrofitClient.createService(Api.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namecreate.getText().toString().matches("")||
                        startcreate.getText().toString().matches("")||
                        endcreate.getText().toString().matches("")||
                        proggrescreate.getText().toString().matches("")){

                    Alerter.create(CreateTodo.this)
                            .setTitle("Kolom tidak boleh Kosong!")
                            .setText("Silahkan isi dengan benar !")
                            .setBackgroundColor(R.color.design_default_color_error)
                            .setIcon(R.drawable.ic_baseline_notifications_24)
                            .setDuration(5000)
                            .show();
                }
                else {
                    call = api.tambahtodo(namecreate.getText().toString(),
                            startcreate.getText().toString(),
                            endcreate.getText().toString(),
                            proggrescreate.getText().toString(),
                            getIntent().getStringExtra("userid"));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                new SweetAlertDialog(CreateTodo.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Data berhasil di tambahkan")
                                        .setConfirmText("klik untuk lanjut!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent i = new Intent(CreateTodo.this,Menutodo.class);
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                if (response.code()== 401){

                                    Toast.makeText(CreateTodo.this, "Gagal !!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}