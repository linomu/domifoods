package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.unicauca.domifoods.R;
import com.unicauca.domifoods.Register2Activity;
import com.unicauca.domifoods.fragments.ShoppingcarFragment;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String OBSERVACIONES = "observaciones";
    public static final String DIRECCION = "direccion";

    private double latitud, longitud;
    private String direccion, obsevaciones;

    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;

    TextView textView25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        configurarSharedPreferences();

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

        //Llamada al post
        /*
                if -> exitoso
                    idorden
                     for (ProductShoppingCart producto :ShoppingcarFragment.products ){
                        OrderProduct o = new OrderProduct(idorden, producto.getId(), producto.getCant(), producto.getSubtotal())
                        Llmado al segundo Post
                       }

                 else->no se pudo crear la orden

        */





        textView25.setText("ID _CLIENTE:"+ id+"\nLatitud: "+latitud+"\n"+"Longitud: "+longitud+"\n"+"Dirección: "+direccion+"\n"+"Observaciones: "+obsevaciones);


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

    private void configurarSharedPreferences() {
        sharedpreferencesLogin = getSharedPreferences(Register2Activity.SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}