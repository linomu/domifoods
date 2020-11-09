package com.unicauca.domifoods.apiUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.unicauca.domifoods.LoginActivity;
import com.unicauca.domifoods.MainActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private final String ip_host;
    private String BASE_URL = "http://192.168.1.55:8000/";
    private static RetrofitClient mInstance;
    private final Retrofit retrofit;
    public static String url = "";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    private static Context mContext;

    private RetrofitClient() {

        ip_host= LoginActivity.ip_host;
        BASE_URL = "http://"+ip_host+":8000/";
        url = "http://"+ip_host+":8000";
        Log.i("msg_lino", "BASE: "+BASE_URL);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache());

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
    }


    public static synchronized RetrofitClient getInstance(Context context) {
       mContext = context;
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


    private Cache provideCache() {
        Cache cache = null;

        try {
            cache = new Cache(new File(mContext.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("Lino", "Could not create Cache!" + e.getMessage());
        }

        return cache;
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl;

            if (isConnected()) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            if (!isConnected()) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

    public boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) mContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("Lino", e.toString());
        }

        return false;
    }


}
