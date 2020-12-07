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

public class Register extends AppCompatActivity {

    Button registrasi;
    EditText nama,email,password;
    Api api;
    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registrasi =(Button)findViewById(R.id.daftar);
        nama = (EditText)findViewById(R.id.nama);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        api = RetrofitClient.createService(Api.class);

        nama.setText(getIntent().getStringExtra("id"));

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString().matches("")|| email.getText().toString().matches("")||password.getText().toString().matches("")){
                    Alerter.create(Register.this)
                            .setTitle("Kolom tidak boleh Kosong!")
                            .setText("Silahkan isi dengan benar !")
                            .setBackgroundColor(R.color.design_default_color_error)
                            .setIcon(R.drawable.ic_baseline_notifications_24)
                            .setDuration(5000)
                            .show();
                }
                else {
                    call = api.Register(nama.getText().toString(),email.getText().toString(),password.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                new SweetAlertDialog(Register.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Akun berhasil di daftarkan")
                                        .setConfirmText("klik untuk lanjut!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent i = new Intent(Register.this,Login.class);
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                if (response.code()== 401){

                                    Toast.makeText(Register.this, "Gagal !!!", Toast.LENGTH_SHORT).show();
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