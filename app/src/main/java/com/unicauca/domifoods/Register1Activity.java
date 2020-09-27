package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        buttonSiguiente = findViewById(R.id.buttonSiguiente);
        buttonSiguiente.setOnClickListener(this);

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
            case R.id.buttonSiguiente:
                Intent intent1 = new Intent(Register1Activity.this, Register2Activity.class);
                startActivity(intent1);
                break;

        }
    }
}