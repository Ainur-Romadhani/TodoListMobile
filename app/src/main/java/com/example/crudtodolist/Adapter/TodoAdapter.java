package com.example.crudtodolist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.crudtodolist.EditTodo;
import com.example.crudtodolist.Menutodo;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Model.User;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.example.crudtodolist.R;
import com.example.crudtodolist.Register;
import com.example.crudtodolist.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends BaseAdapter {

    Context context;
    List<Todo> list;
    Api api;
    Call<ResponseBody> call;
    SharedPrefManager sharedPrefManager;

    public TodoAdapter(Context context, List<Todo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.daftar_todo,null);
        }
        sharedPrefManager = new SharedPrefManager(context);
        String emailuser = sharedPrefManager.getSpEmail();
        Todo todo = list.get(position);
        TextView userid = convertView.findViewById(R.id.userid);
        TextView nama = convertView.findViewById(R.id.nama);
        TextView  start = convertView.findViewById(R.id.start);
        TextView end = convertView.findViewById(R.id.end);
        TextView idtodos = convertView.findViewById(R.id.idtodos);
        TextView proggress = convertView.findViewById(R.id.proggress);
        TextView createby = convertView.findViewById(R.id.createby);
        ImageButton update = convertView.findViewById(R.id.btnedit);
        ImageButton delete = convertView.findViewById(R.id.btndel);
        userid.setText(todo.getUser_id());
        nama.setText(todo.getName());
        start.setText(todo.getStart_date());
        end.setText(todo.getEnd_date());
        proggress.setText(todo.getProggress());
        createby.setText(todo.getCreate_by());
        idtodos.setText(todo.getId_todos());
        api = RetrofitClient.createService(Api.class);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(context, EditTodo.class);
               i.putExtra("idtodos",idtodos.getText().toString());
               i.putExtra("nametodo",nama.getText().toString());
               i.putExtra("start",start.getText().toString());
               i.putExtra("end",end.getText().toString());
               i.putExtra("proggress",proggress.getText().toString());
               i.putExtra("updateby",emailuser);
               context.startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = api.delete(idtodos.getText().toString(),emailuser);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;
    }
}
