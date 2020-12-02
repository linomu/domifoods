package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.unicauca.domifoods.R;
import com.unicauca.domifoods.Register2Activity;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.modelsOrder.OrderRequest;
import com.unicauca.domifoods.modelsOrder.OrderResponse;


import com.unicauca.domifoods.modelsOrder.ProductOrderRequest;
import com.unicauca.domifoods.modelsOrder.ProductOrderResponse;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;
import com.unicauca.domifoods.modelsUser.Client_detail_response;
import com.unicauca.domifoods.views.OrderSent;
import com.unicauca.domifoods.views.ShoppingCartActivity;


import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String OBSERVACIONES = "observaciones";
    public static final String DIRECCION = "direccion";

    private double latitud, longitud;
    private String direccion, obsevaciones;
    private ProgressDialog progDailog;

    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;
    private Button btn_payment;
    private TextView tv_total_pagar;

    TextView textView25;

    int total_productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        configurarSharedPreferences();
        total_productos = ShoppingCartActivity.products.size();
        tv_total_pagar = findViewById(R.id.tv_total_pagar);
        tv_total_pagar.setText("$" +ShoppingCartActivity.sumTotal);

        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1. Metodo post para obtener el id del cliente: Debo tener el documento
                String id = sharedpreferencesLogin.getString(Register2Activity.LOGIN_DOCUMENT,"");
                startProgressDialog();
                idClientFromDocument(Integer.parseInt(id));

            }
        });

        textView25 = findViewById(R.id.textView25);

        //1 Organizando el objeto para el primer posto
        String id = sharedpreferencesLogin.getString(Register2Activity.LOGIN_DOCUMENT,"");
        if(id.equals("")){
            Toast.makeText(this, "No tengo el id", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "ID Cliente: "+id, Toast.LENGTH_LONG).show();
        }
        latitud = getIntent().getDoubleExtra(LATITUD,0);
        longitud = getIntent().getDoubleExtra(LONGITUD,0);
        direccion = getIntent().getStringExtra(DIRECCION);
        obsevaciones = getIntent().getStringExtra(OBSERVACIONES);


        textView25.setText("ID _CLIENTE:"+ id+"\nLatitud: "+latitud+"\n"+"Longitud: "+longitud+"\n"+"Dirección: "+direccion+"\n"+"Observaciones: "+obsevaciones);


    }


    public void idClientFromDocument(int document) {
        Log.i("payment", "Estoy Dentro de idClientFromDocument con document: "+document);
        Call<Client_detail_response> call = RetrofitClient.getInstance(this).getApi().getClientDetailByDocument(document);
        call.enqueue(new Callback<Client_detail_response>() {
            @Override
            public void onResponse(Call<Client_detail_response> call, Response<Client_detail_response> response) {
                Log.i("payment", "I'm inside OnResponse getClientDetail");
                if (response.isSuccessful()) {
                    Log.i("payment", "The response getClientDetail was successful. Code: " + response.code());
                    Client_detail_response Client_detail_response = response.body();
                    Log.i("payment", "getClientDetail info:" + Client_detail_response.toString());
                    //2. Metodo post para crear una orden, aqui debo tener el precio calculado

                    createOrder(Client_detail_response.getId());


                    
                } else {
                    Log.i("payment", "The response getClientDetail wasn't successful. Code: " + response.code());
                    Toast.makeText(PaymentActivity.this, "The response getClientDetail wasn't successful. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<Client_detail_response> call, Throwable t) {
                Log.i("Lino", "OnFailure SetUpRestaurant Info: " + t.getMessage());
                Toast.makeText(PaymentActivity.this, "OnFailure SetUpRestaurant Info: " + t.getMessage(), Toast.LENGTH_LONG).show();
                stopProgressDialog();
            }
        });
    }

    private void createOrder(int idClient) {
        OrderRequest orderRequest = new OrderRequest(ShoppingCartActivity.sumTotal,0,direccion,longitud,latitud,obsevaciones,idClient);

        Call<OrderResponse> createOrder = RetrofitClient.getInstance(getApplicationContext()).getApi().createOrder(orderRequest);
        createOrder.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                String s = "";
                if (response.body() != null) {
                    OrderResponse orderResponse = response.body();
                    //crear el archivo shared
                   Log.i("payment","id de la orden> "+orderResponse.getId());
                    //3. For para el post de producto
                    createOrderProduct(orderResponse.getId());



                } else {
                    try {
                        s = response.errorBody().string();
                        Toast.makeText(PaymentActivity.this, "Uso del api de createOrder: " + s, Toast.LENGTH_LONG).show();
                        Log.i("payment", "Uso del api de createOrder: " + s);
                        stopProgressDialog();
                    } catch (IOException e) {
                        e.printStackTrace();
                        stopProgressDialog();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.i("payment", "Fallo: " + t.getMessage());
                Toast.makeText(PaymentActivity.this, "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                stopProgressDialog();
            }
        });
    }

    private void createOrderProduct(int idOrder) {


        for(ProductShoppingCart orderProduct : ShoppingCartActivity.products){

            ProductOrderRequest productOrderRequest = new ProductOrderRequest(orderProduct.getCant(), orderProduct.getSubtotal(),idOrder, orderProduct.getId());
            Call<ProductOrderResponse> createOrderProduct = RetrofitClient.getInstance(getApplicationContext()).getApi().createProductOrder(productOrderRequest);
            createOrderProduct.enqueue(new Callback<ProductOrderResponse>() {
                @Override
                public void onResponse(Call<ProductOrderResponse> call, Response<ProductOrderResponse> response) {
                    String s = "";
                    if (response.body() != null) {
                        ProductOrderResponse productOrderResponse = response.body();
                        //crear el archivo shared
                        Log.i("payment","Producto almacenado> id de la ProductOrder> "+productOrderResponse.getId());
                        if(total_productos==1){
                            Log.i("payment","Ya se ingresó el ultimo producto :)");
                            ShoppingCartActivity.products = new ArrayList<>();
                            ShoppingCartActivity.sumTotal=0;
                            stopProgressDialog();
                            Toast.makeText(getApplicationContext(), "Su orden se ha registrado exitosamente! 😁", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PaymentActivity.this, OrderSent.class));
                        }
                        total_productos--;
                    } else {
                        try {
                            s = response.errorBody().string();
                            Toast.makeText(PaymentActivity.this, "Uso del api de ProductOrder: " + s, Toast.LENGTH_LONG).show();
                            Log.i("payment", "Uso del api de ProductOrder: " + s);
                            stopProgressDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                            stopProgressDialog();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ProductOrderResponse> call, Throwable t) {
                    Log.i("payment", "Fallo en ProductOrder: " + t.getMessage());
                    Toast.makeText(PaymentActivity.this, "Fallo en ProductOrder: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void configurarSharedPreferences() {
        sharedpreferencesLogin = getSharedPreferences(Register2Activity.SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    private void stopProgressDialog(){
        progDailog.dismiss();
    }

    private void startProgressDialog() {
        progDailog = new ProgressDialog(this);
        progDailog.setMessage(getResources().getString(R.string.loading));
        //progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        progDailog.show();
    }

        /*
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        String hora = hourFormat.format(date);


        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

        System.out.println(objDate);
        String strDateFormat = "hh: mm: ss a dd-MMM-aaaa"; // El formato de fecha está especificado
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto
        System.out.println(objSDF.format(objDate));

        // textView is the TextView view that should display it
        textView25.setText(currentDateTimeString);
        textView25.setText(textView25.getText()+ "\n"+fecha+"\n"+hora+"\n"+objSDF.format(objDate));
        */


}