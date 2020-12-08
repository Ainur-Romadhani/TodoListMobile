package com.example.crudtodolist;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public static final String SP_TODOLIST_APP      = "com.example.crudtodolist";
    public static final String SP_CEK_LOGIN         = "booleanValue";
    public static final String SP_EMAIL             = "email";

    //definisi id session --------------------------------------------------------------------------
    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_TODOLIST_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    //Funsi untuk menyimpan nilai string ----------------------------------------------------------
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    //Funsi untuk menyimpan nilai integer ----------------------------------------------------------
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    //Funsi untuk menyimpan nilai boleean ----------------------------------------------------------
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    //save session ---------------------------------------------------------------------------------
    public Boolean getSPCekLogin(){
        return sp.getBoolean(SP_CEK_LOGIN, false);
    }

    public String getSPToken(){
        return sp.getString(SP_EMAIL, "");
    }

}
