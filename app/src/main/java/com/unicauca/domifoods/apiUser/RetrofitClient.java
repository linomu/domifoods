package com.unicauca.domifoods.apiUser;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.unicauca.domifoods.LoginActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private String ip_host;
    private String BASE_URL = "http://"+ip_host+":8000/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        ip_host= LoginActivity.ip_host;
        BASE_URL = "http://"+ip_host+":8000/";
        Log.i("msg_lino", "BASE: "+BASE_URL);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }


    public static synchronized RetrofitClient getInstance() {

        Log.i("msg_lino", "Se ha configurado la instancia de Retrofit: ");
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }

        return mInstance;
    }
    //with the following method, we will get the apiUser
    public ApiUser getApi(){
        return retrofit.create(ApiUser.class);

    }



    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }
}
