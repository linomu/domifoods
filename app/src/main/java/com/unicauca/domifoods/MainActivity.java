package com.unicauca.domifoods;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.unicauca.domifoods.dialogs.NoticeDialogListener;


public class MainActivity extends AppCompatActivity implements NoticeDialogListener {

    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setUpSharedPreferences();
        setUpTheScreen();
    }

    private void setUpSharedPreferences() {
        sharedpreferencesLogin = getSharedPreferences(Register2Activity.SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
    }
    private void setUpTheScreen() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "presiono Si", Toast.LENGTH_SHORT).show();
        editorLogin.clear();
        editorLogin.commit();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        Toast.makeText(this, "Adios :)", Toast.LENGTH_SHORT).show();
        finish();
        setUpTheScreen();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Presiono no", Toast.LENGTH_SHORT).show();
        setUpTheScreen();
    }
}