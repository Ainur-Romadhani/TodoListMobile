package com.example.crudtodolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudtodolist.EditTodo;
import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Network.Api;
import com.example.crudtodolist.Network.RetrofitClient;
import com.example.crudtodolist.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TongAdapter extends BaseAdapter {

    Context context;
    List<Todo> list;
    Api api;
    Call<ResponseBody> call;

    public TongAdapter(Context context, List<Todo> list){
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
            convertView = inflater.inflate(R.layout.tongsampah,null);
        }

        Todo todo = list.get(position);
        TextView userid = convertView.findViewById(R.id.userid);
        TextView nama = convertView.findViewById(R.id.nama);
        TextView  start = convertView.findViewById(R.id.start);
        TextView end = convertView.findViewById(R.id.end);
        TextView idtodos = convertView.findViewById(R.id.idtodos);
        TextView proggress = convertView.findViewById(R.id.proggress);
        TextView delete_by = convertView.findViewById(R.id.delete_by);
        ImageButton restore = convertView.findViewById(R.id.btnrestore);
        ImageButton delete = convertView.findViewById(R.id.btndelper);
        userid.setText(todo.getUser_id());
        nama.setText(todo.getName());
        start.setText(todo.getStart_date());
        end.setText(todo.getEnd_date());
        proggress.setText(todo.getProggress());
        delete_by.setText(todo.getDelete_by());
        idtodos.setText(todo.getId_todos());
        api = RetrofitClient.createService(Api.class);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = api.restore(idtodos.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context,"Data Berhasil di Restore",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = api.permanen(idtodos.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context,"Data Berhasl di Hapus",Toast.LENGTH_SHORT).show();
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
