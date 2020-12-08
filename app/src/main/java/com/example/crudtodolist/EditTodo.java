package com.example.crudtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTodo extends AppCompatActivity {
    DatePickerDialog piker;
    EditText editname,editstart,editend,editproggres;
    TextView id_todos;
    Button edit;
    Api api;
    Call<ResponseBody> call;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        sharedPrefManager = new SharedPrefManager(this);
        String emailuser = sharedPrefManager.getSpEmail();

        edit=(Button)findViewById(R.id.edittodo);
        id_todos=(TextView)findViewById(R.id.id_todos);
        editname=(EditText)findViewById(R.id.nameedit);
        editstart = (EditText)findViewById(R.id.startedit);
        editend = (EditText)findViewById(R.id.end_dateedit);
        editproggres=(EditText)findViewById(R.id.proggressedit);

        id_todos.setText(getIntent().getStringExtra("idtodos"));
        editname.setText(getIntent().getStringExtra("nametodo"));
        editstart.setText(getIntent().getStringExtra("start"));
        editend.setText(getIntent().getStringExtra("end"));
        editproggres.setText(getIntent().getStringExtra("proggress"));
        api = RetrofitClient.createService(Api.class);

        editstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                piker = new DatePickerDialog(EditTodo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editstart.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                piker.show();
            }
        });

        editend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                piker = new DatePickerDialog(EditTodo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editend.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                piker.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = api.updatetodo(id_todos.getText().toString(),
                        editname.getText().toString(),
                        editstart.getText().toString(),
                        editend.getText().toString(),
                        editproggres.getText().toString(),emailuser);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            new SweetAlertDialog(EditTodo.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Data berhasil di Update")
                                    .setConfirmText("klik untuk lanjut!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            Intent i = new Intent(EditTodo.this,Menutodo.class);
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }
}