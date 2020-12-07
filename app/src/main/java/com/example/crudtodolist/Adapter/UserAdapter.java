package com.example.crudtodolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.crudtodolist.Menutodo;
import com.example.crudtodolist.Model.User;
import com.example.crudtodolist.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    Context context;
    List<User> list;


    public UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount(){
        return  list.size();
    }
    @Override

    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override

    public View getView(int position , View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.daftar_user,null);
        }

        User user = list.get(position);
        Button todo = convertView.findViewById(R.id.btnTodo);
        TextView nama = convertView.findViewById(R.id.textnamauser);
        TextView  email = convertView.findViewById(R.id.textemailuser);
        TextView id = convertView.findViewById(R.id.textiduser);
        email.setText(user.getEmail());
        id.setText(user.getId());
        nama.setText(user.getName());

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Menutodo.class);
                i.putExtra("id",id.getText().toString());
                i.putExtra("email",email.getText().toString());
                context.startActivity(i);

            }
        });


        return  convertView;

    }
}
