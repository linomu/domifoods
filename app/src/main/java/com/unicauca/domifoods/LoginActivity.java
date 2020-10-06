package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonIngresar;
    Button buttonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializationVariables();

    }

    private void initializationVariables() {
        buttonIngresar = findViewById(R.id.buttonIngresar);
        buttonIngresar.setOnClickListener(this);
        buttonRegistrarse = findViewById(R.id.buttonRegistrarse);
        buttonRegistrarse.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonIngresar:
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.buttonRegistrarse:
                Intent intent2 = new Intent(LoginActivity.this, Register1Activity.class);
                startActivity(intent2);
                break;


        }
    }




}