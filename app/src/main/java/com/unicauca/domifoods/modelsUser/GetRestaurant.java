package com.unicauca.domifoods.modelsUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class GetRestaurant extends AsyncTask<Void, Void, String>{

    private List<Restaurant> httpList;
    private RecyclerView httpRecycler;
    private RecyclerView.Adapter httpAdapter;
    private Context httpContext;
    ProgressDialog progressDialog;

    public GetRestaurant(List<Restaurant> httpList, RecyclerView httpRecycler, RecyclerView.Adapter httpAdapter, Context httpContext) {
        this.httpList = httpList;
        this.httpRecycler = httpRecycler;
        this.httpAdapter = httpAdapter;
        this.httpContext = httpContext;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "downloading restaurants", "please wait");
    }
    @Override
    protected String doInBackground(Void... params) {
        String result = null;
        try {
            String wsURL = "http://192.168.1.55:8000/restaurants/api/";
            URL url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
            result = inputStringToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected  void onPostExecute(String s){
        super.onPostExecute(s);
        progressDialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(URLDecoder.decode(s,"UTF-8"));
            JSONArray jsonArray = jsonObject.getJSONArray("restaurants");

            for(int i=0; i<jsonArray.length();i++){
                int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("eId"));
                int nit = Integer.parseInt(jsonArray.getJSONObject(i).getString("eNit"));
                String name = jsonArray.getJSONObject(i).getString("cName");
                String address_location = jsonArray.getJSONObject(i).getString("cAddress_location");
                String phone_num = jsonArray.getJSONObject(i).getString("cPhone_num");
                String web_page = jsonArray.getJSONObject(i).getString("cWeb_page");
                String hours = jsonArray.getJSONObject(i).getString("cHours");
                String image = jsonArray.getJSONObject(i).getString("cImage");
                String date_creation = jsonArray.getJSONObject(i).getString("cDate_creation");
                boolean state_delete = Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("bState_delete"));
                boolean state_disponibility = Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("bState_disponibility"));
                int id_admin = Integer.parseInt(jsonArray.getJSONObject(i).getString("eId_admin"));
                this.httpList.add(new Restaurant(id, nit, name, address_location, phone_num, web_page, hours, image, date_creation, state_delete, state_disponibility, id_admin ));

            }
            //crear un nuevo adaptador
            httpAdapter = new AdapterRestaurants((ArrayList<Restaurant>) this.httpList);
            httpRecycler.setAdapter(this.httpAdapter);
        }catch (JSONException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private String inputStringToString(InputStream is){
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);
        try {
            while ((rLine = rd.readLine()) != null){
                answer.append(rLine);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return answer.toString();
    }
}














    /*


    private ArrayList<Restaurant> result;

    public ArrayList<Restaurant> getResults(){
        return result;
    }

    public void setResults(ArrayList<Restaurant> result){
        this.result = result;
    }
    private List<Restaurant> httpList;
    private RecyclerView httpRecycler;
    private RecyclerView.Adapter httpAdapter;
    private Context httpContext;
    //progressDialog progressDialog;

    */

