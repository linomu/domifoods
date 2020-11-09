package com.unicauca.domifoods.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.unicauca.domifoods.MainActivity;
import com.unicauca.domifoods.R;

import java.util.Timer;
import java.util.TimerTask;

public class OrderSent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sent);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent objIntent = new Intent(OrderSent.this, MainActivity.class);
                startActivity(objIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tarea,2500);
    }
}