package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        Intent objIntent = new Intent(Splash.this, MainActivity.class);
        startActivity(objIntent);
        finish();
    }
}