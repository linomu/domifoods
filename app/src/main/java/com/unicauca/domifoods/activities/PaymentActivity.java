package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.unicauca.domifoods.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    TextView textView25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        textView25 = findViewById(R.id.textView25);

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

    }
}