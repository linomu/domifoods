package com.unicauca.domifoods;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Register1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        Intent objIntent = new Intent(Register1Activity.this, MainActivity.class);
        startActivity(objIntent);
        finish();
    }
}
