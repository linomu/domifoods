package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.dialogs.SimpleDialog;
import com.unicauca.domifoods.modelsUser.Login_request;
import com.unicauca.domifoods.modelsUser.Login_response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonIngresar;
    Button buttonRegistrarse;
    private EditText et_user, et_password;
    private ProgressDialog progDailog;

    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;

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
        et_user = findViewById(R.id.et_user_login);
        et_password = findViewById(R.id.et_user_password_login);
    }
    private void startProgressDialog() {
        progDailog = new ProgressDialog(this);
        progDailog.setMessage(getResources().getString(R.string.loading));
        //progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        progDailog.show();
    }
    private void stopProgressDialog(){
        progDailog.dismiss();
    }
    private void setUpSharedPreferences() {
        sharedpreferencesLogin = getSharedPreferences(Register2Activity.SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
    }

    private boolean sessionState() {
        boolean sessionState = false;
        String token = sharedpreferencesLogin.getString(Register2Activity.LOGIN_TOKEN,"");
        if(!token.equals("")){
            sessionState=true;
        }
        return sessionState;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpSharedPreferences();
        if(sessionState()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
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

                //Llamo a login para que solucione, retorne un nuevo id
                String user = et_user.getText().toString().trim();
                String pass = et_password.getText().toString().trim();
                if(pass.isEmpty()){
                    et_password.setError(getString(R.string.msg_required_failed));
                    et_password.requestFocus();
                    return;

                }
                if(user.isEmpty()){
                    et_user.setError(getString(R.string.msg_required_failed));
                    et_password.requestFocus();
                    return;
                }
                startProgressDialog();
                Login_request login_request = new Login_request(user,pass);
                Call<Login_response> login = RetrofitClient.getInstance().getApi().loginFull(login_request);
                login.enqueue(new Callback<Login_response>() {

                    @Override
                    public void onResponse(Call<Login_response> call, Response<Login_response> response) {
                        String s = "";
                        if(response.body()!=null){
                            Login_response login_response = response.body();
                            //crear el archivo shared
                            editorLogin.putString(Register2Activity.LOGIN_TOKEN,login_response.getToken());
                            editorLogin.putString(Register2Activity.LOGIN_DOCUMENT,login_response.getDocument());
                            editorLogin.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            stopProgressDialog();

                        }
                        else{
                            try {
                                s =response.errorBody().string();
                                Log.i("Retrofit", "Uso del api de Login: "+s);
                                stopProgressDialog();
                                SimpleDialog simpleDialog = new SimpleDialog();
                                //Por medio de este set, le estoy pasando informacion al Dialog
                                simpleDialog.setMensaje_from_server(s);
                                FragmentManager fm = getSupportFragmentManager();
                                simpleDialog.show(fm,"LoginDialog");



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Mensaje de contraseñas invalidas
                        }
                    }

                    @Override
                    public void onFailure(Call<Login_response> call, Throwable t) {
                        stopProgressDialog();
                        Toast.makeText(LoginActivity.this, "Tenemos un fallo extraño", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            case R.id.buttonRegistrarse:
                Intent intent2 = new Intent(LoginActivity.this, Register1Activity.class);
                startActivity(intent2);
                break;


        }
    }




}