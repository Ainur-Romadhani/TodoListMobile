package com.example.crudtodolist.Network;

import com.example.crudtodolist.Model.Todo;
import com.example.crudtodolist.Model.User;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("user")
    Call<List<User>> datauser();

    @FormUrlEncoded
    @POST("todoid")
    Call<List<Todo>>todoid(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("datauser")
    Call<List<User>> userlogin(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> Register
            (@Field("name") String name,
             @Field("email") String email,
             @Field("password") String password);

    @FormUrlEncoded
    @POST("loginuser")
    Call<ResponseBody>loginuser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("tong")
    Call<List<Todo>>tongtodo(@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("create")
    Call<ResponseBody>tambahtodo(
            @Field("name")String name,
            @Field("start_date")String start_date,
            @Field("end_date")String end_date,
            @Field("proggress")String proggress,
            @Field("user_id")String user_id,
            @Field("create_by")String create_by
            );
    @FormUrlEncoded
    @POST("update")
    Call<ResponseBody>updatetodo(
            @Field("id_todos")String id_todos,
            @Field("name")String name,
            @Field("start_date")String start_date,
            @Field("end_date")String end_date,
            @Field("proggress")String proggress,
            @Field("update_by")String update_by
    );

    @FormUrlEncoded
    @POST("delete")
    Call<ResponseBody>delete(
            @Field("id_todos")String id_todos,
            @Field("delete_by")String delete_by
    );

    @FormUrlEncoded
    @POST("restore")
    Call<ResponseBody>restore(
            @Field("id_todos")String id_todos
    );

    @FormUrlEncoded
    @POST("permanen")
    Call<ResponseBody>permanen(
            @Field("id_todos")String id_todos
    );

    @POST("deleteall")
    Call<ResponseBody>deleteall();

    @POST("restoreall")
    Call<ResponseBody>restoreall();
}
