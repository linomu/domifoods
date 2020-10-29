package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.apiUser.ApiUser;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.dialogs.DialogIpHost;
import com.unicauca.domifoods.dialogs.NoticeDialogListener;
import com.unicauca.domifoods.dialogs.SimpleDialog;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.modelsUser.GetRestaurant;
import com.unicauca.domifoods.modelsUser.Login_request;
import com.unicauca.domifoods.modelsUser.Login_response;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, NoticeDialogListener {


    Button buttonIngresar;
    Button buttonRegistrarse;
    private ImageView img_logo;
    private EditText et_user, et_password;
    private ProgressDialog progDailog;
    private TextView tv_ip_host;
    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;
    private static final String IP_HOST = "ip_host";
    private SharedPreferences sharedpreferencesIpHost;
    private SharedPreferences.Editor editorIpHost;
    public static String ip_host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializationVariables();

    }

    /*El método initializationVariables() no recibe parámetros y su objetivo es inicializar todas las variables que se van
    * a utilizar en esta actividad. Mediante este proceso, es posible conocer la parte java con la parte XML */
    private void initializationVariables() {
        tv_ip_host = findViewById(R.id.tv_iphost);
        img_logo = findViewById(R.id.img_logo);
        img_logo.setOnClickListener(this);
        buttonIngresar = findViewById(R.id.buttonIngresar);
        buttonIngresar.setOnClickListener(this);
        buttonRegistrarse = findViewById(R.id.buttonRegistrarse);
        buttonRegistrarse.setOnClickListener(this);
        et_user = findViewById(R.id.et_user_login);
        et_password = findViewById(R.id.et_user_password_login);
    }

    /*Este método permite iniciar un diálogo que indica que una notificación está "Cargando..."*/
    private void startProgressDialog() {
        progDailog = new ProgressDialog(this);
        progDailog.setMessage(getResources().getString(R.string.loading));
        //progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        progDailog.show();
    }

    /*Este método permite detener un diálogo que indica que una notificación está "Cargando..."*/
    private void stopProgressDialog() {
        progDailog.dismiss();
    }

    /*El método setUpSharedPreferences() tiene como finalidad almacenar de manera local el texto de cada uno de los
    * campos del primer formulario. Los datos se almacenan en un archivo XML SharedPreferences*/
    private void setUpSharedPreferences() {
        sharedpreferencesLogin = getSharedPreferences(Register2Activity.SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
        sharedpreferencesIpHost = getSharedPreferences(IP_HOST, Context.MODE_PRIVATE);
        editorIpHost = sharedpreferencesIpHost.edit();
    }

    /*El método sessionState() tiene como objetivo retornar a través de una variable booleana si existe o no
     una sesion activa en el sistema, si al validar  ya existe una sesión activa,éste metodo redirecciona al usuario
     al siguiente formulario, si por el contrario no existe una sesión activa el valor de retorno sería false.*/
    private boolean sessionState() {
        boolean sessionState = false;
        String token = sharedpreferencesLogin.getString(Register2Activity.LOGIN_TOKEN, "");
        if (!token.equals("")) {
            sessionState = true;
        }
        return sessionState;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpSharedPreferences();
        updateIpHostStaticVariable();
        RetrofitClient.getInstance(getApplicationContext());


        if (sessionState()) {
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

    /*El método  updateIpHostStaticVariable() tiene como finalidad obtener la dirección ip almacena en un archivo
    * sharedPreference y mostrarla através de un TextView, esto con el fin de que el desarrollador pueda identificar
    * gráficamente la dirección ip sobre la cual está realizando la conexión al servidor local*/
    private void updateIpHostStaticVariable() {
        ip_host = sharedpreferencesIpHost.getString("ip_host", "192.168.0.1");
        tv_ip_host.setText(ip_host);
    }

    /*El método onClick(View view) tiene como finalidad capturar el evento OnClick que el usuario genera. Dependiendo de
    * la vista seleccionada se ejecturá una respectiva acción.*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_logo:
                DialogIpHost objDialogLogin = new DialogIpHost();
                //Por medio de este set, le estoy pasando informacion al Dialog
                FragmentManager fm = getSupportFragmentManager();
                objDialogLogin.show(fm, "Dialog");
                break;
            case R.id.buttonIngresar:

                //Llamo a login para que solucione, retorne un nuevo id
                String user = et_user.getText().toString().trim();
                String pass = et_password.getText().toString().trim();
                if (pass.isEmpty()) {
                    et_password.setError(getString(R.string.msg_required_failed));
                    et_password.requestFocus();
                    return;

                }
                if (user.isEmpty()) {
                    et_user.setError(getString(R.string.msg_required_failed));
                    et_password.requestFocus();
                    return;
                }
                startProgressDialog();
                Login_request login_request = new Login_request(user, pass);
                ///GetRestaurant getRestaurant = new GetRestaurant();

                Call<Login_response> login = RetrofitClient.getInstance(getApplicationContext()).getApi().loginFull(login_request);
                login.enqueue(new Callback<Login_response>() {

                    @Override
                    public void onResponse(Call<Login_response> call, Response<Login_response> response) {
                        String s = "";
                        if (response.body() != null) {
                            Login_response login_response = response.body();
                            //crear el archivo shared
                            editorLogin.putString(Register2Activity.LOGIN_TOKEN, login_response.getToken());
                            editorLogin.putString(Register2Activity.LOGIN_DOCUMENT, login_response.getDocument());
                            editorLogin.commit();
                            stopProgressDialog();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();


                        } else {
                            try {
                                s = response.errorBody().string();
                                Log.i("Retrofit", "Uso del api de Login: " + s);
                                stopProgressDialog();
                                SimpleDialog simpleDialog = new SimpleDialog();
                                //Por medio de este set, le estoy pasando informacion al Dialog
                                simpleDialog.setMensaje_from_server(s);
                                FragmentManager fm = getSupportFragmentManager();
                                simpleDialog.show(fm, "LoginDialog");


                            } catch (IOException e) {
                                e.printStackTrace();
                                stopProgressDialog();
                            }
                            //Mensaje de contraseñas invalidas
                        }
                    }

                    @Override
                    public void onFailure(Call<Login_response> call, Throwable t) {
                        stopProgressDialog();

                        Log.i("msg", "Fallo: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯" + '\\' + "_(ツ)_/¯", Toast.LENGTH_LONG).show();
                    }
                });


                break;
            case R.id.buttonRegistrarse:
                Intent intent2 = new Intent(LoginActivity.this, Register1Activity.class);
                startActivity(intent2);
                break;


        }
    }


    /*Los métodos  onDialogPositiveClick(DialogFragment dialog) y onDialogNegativeClick(DialogFragment dialog) se activan
    * cuando el usuario ha decido cambiar la dirección IP */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DialogIpHost objDialog = (DialogIpHost) dialog;
        tv_ip_host.setText(objDialog.getIp_host());
        editorIpHost.putString("ip_host", objDialog.getIp_host());
        editorIpHost.commit();
        updateIpHostStaticVariable();
        RetrofitClient.getInstance(getApplicationContext());

        Intent mStartActivity = new Intent(this, LoginActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "(⌐■_■) Genio. 👏", Toast.LENGTH_SHORT).show();

    }


}