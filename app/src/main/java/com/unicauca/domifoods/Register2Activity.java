package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.dialogs.SimpleDialog;
import com.unicauca.domifoods.interfaces.Listener;
import com.unicauca.domifoods.modelsUser.Create_user_request;
import com.unicauca.domifoods.modelsUser.Create_user_response;
import com.unicauca.domifoods.modelsUser.Login_request;
import com.unicauca.domifoods.modelsUser.Login_response;
import com.unicauca.domifoods.modelsUser.User_client_register;
import com.unicauca.domifoods.modelsUser.User_restaurant_register;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener {


    private static final String USER_BIRTHDAY = "user_birthday" ;
    private static final String USER_EMAIL = "user_email";
    private static final String USER_ADDRESS = "user_address";
    private static final String USER_GENDER = "user_gender";
    private static final String USER_APP = "user_app";
    private static final String USER_PASS1 = "password_one";
    private static final String USER_PASS2 = "password_two";
    public static final String SESSION_LOGIN = "PreferecesLogin";
    public static final String LOGIN_TOKEN = "login_token";
    public static final String LOGIN_DOCUMENT = "login_document";

    public String id, information;

    private String user_name, user_last_name, user_id, phone, kind_of_id;
    private String user_birthday, user_email, user_address, user_gender, user_password, user_app,password_one, password_two;

    private Button btn_birth_day, btn_register;
    private EditText et_user_email, et_user_address, et_user_password1, et_user_password2, et_user_app;
    private RadioButton radio_user_gender_male;
    private RadioButton radio_user_gender_female;
    private ProgressDialog progDailog;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferencesLogin;
    private SharedPreferences.Editor editorLogin;

    public Create_user_response create_user_response;
    public static String message_from_server;

    Calendar C = Calendar.getInstance();
    //private int nYarIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayUni;


    //= (EditText) view.findViewById(R.id.btn_birth_day);
    //btn_birth_day.setOnClickListener(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);



        /*In the next method, I make the initialization of variables*/
        initializationVariables();
        //Retrieve the information through out the bundle object
        retrieveInformation();

    }

    private boolean sessionState() {
        boolean sessionState = false;
        String token = sharedpreferencesLogin.getString(LOGIN_TOKEN,"");
        if(!token.equals("")){
            sessionState=true;
        }
        return sessionState;
    }

    private void setUpSharedPreferences() {
        sharedpreferences = getSharedPreferences(Register1Activity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        sharedpreferencesLogin = getSharedPreferences(SESSION_LOGIN, Context.MODE_PRIVATE);
        editorLogin = sharedpreferencesLogin.edit();
    }
    private void updateFormFromSharedPreference(){
        btn_birth_day.setText(sharedpreferences.getString(USER_BIRTHDAY,""));
        et_user_email.setText(sharedpreferences.getString(USER_EMAIL,""));
        et_user_address.setText(sharedpreferences.getString(USER_ADDRESS,""));
        String gender = sharedpreferences.getString(USER_GENDER,"M");
        if(gender.equals("M")){
            radio_user_gender_male.setChecked(true);
            radio_user_gender_female.setChecked(false);
        }else{
            radio_user_gender_male.setChecked(false);
            radio_user_gender_female.setChecked(true);
        }
        et_user_app.setText(sharedpreferences.getString(USER_APP,""));
        et_user_password1.setText(sharedpreferences.getString(USER_PASS1,""));
        et_user_password2.setText(sharedpreferences.getString(USER_PASS2,""));


    }
    private void saveFormIntoSharedPreferences(){
        getValuesFromInputForm();
        editor.putString(USER_BIRTHDAY,user_birthday);
        editor.putString(USER_EMAIL,user_email);
        editor.putString(USER_ADDRESS, user_address);
        editor.putString(USER_GENDER, user_gender);
        editor.putString(USER_APP, user_app);
        editor.putString(USER_PASS1,password_one);
        editor.putString(USER_PASS2,password_two);
        editor.commit();
    }
    private void getValuesFromInputForm(){
        user_birthday = btn_birth_day.getText().toString().trim();
        user_email = et_user_email.getText().toString().trim();
        user_address = et_user_address.getText().toString().trim();
        user_app = et_user_app.getText().toString().trim();
        password_one = et_user_password1.getText().toString().trim();
        password_two = et_user_password2.getText().toString().trim();
        if (radio_user_gender_female.isChecked()) {
            user_gender = "F";
        } else {
            user_gender = "M";
        }

    }
    private void cleanForms(){

        btn_birth_day.setText("");
        et_user_email.setText("");
        et_user_address.setText("");
        et_user_app.setText("");
        et_user_password1.setText("");
        et_user_password2.setText("");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("msg_lino","Onbackpress");
        saveFormIntoSharedPreferences();
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
    private void initializationVariables() {
        et_user_email = findViewById(R.id.et_user_email);
        et_user_address = findViewById(R.id.et_user_address);
        btn_birth_day = findViewById(R.id.btn_user_birth_day);
        btn_birth_day.setOnClickListener(this);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        radio_user_gender_female = findViewById(R.id.radio_female);
        radio_user_gender_male = findViewById(R.id.radio_male);
        et_user_password1 = findViewById(R.id.et_user_password1);
        et_user_password2 = findViewById(R.id.et_user_password2);
        et_user_app = findViewById(R.id.et_userapp);
    }
    private void retrieveInformation() {
        Bundle bundle = getIntent().getExtras();
        user_name = bundle.getString(Register1Activity.USER_NAME);
        user_last_name = bundle.getString(Register1Activity.USER_LASTNAME);
        user_id = bundle.getString(Register1Activity.USER_ID);
        phone = bundle.getString(Register1Activity.USER_PHONE);
        kind_of_id = bundle.getString(Register1Activity.USER_KIND_ID);
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            C.set(Calendar.YEAR, year);
            C.set(Calendar.MONTH, monthOfYear);
            C.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

    };
    private void actualizarInput() {
        //String formatoDeFecha = "MM/dd/yy"; //In which you need put here
        String formatoDeFecha = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);
        btn_birth_day.setText(sdf.format(C.getTime()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpSharedPreferences();
        if(sessionState()){
            startActivity(new Intent(Register2Activity.this, MainActivity.class));
            finish();
        }
        updateFormFromSharedPreference();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:

                if (isTheFormComplete()==true) {
                    saveFormIntoSharedPreferences();
                    startProgressDialog();
                    //goodMethod();
                    //callWebServiceUserRegister();
                    WebServiceUserRegister obj = new WebServiceUserRegister();
                    try {
                        create_user_response = new Create_user_response();
                        create_user_response = obj.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {e.printStackTrace();}

                    if(create_user_response !=null){

                        id = create_user_response.getId();
                        //Si id es null, significa que el usuario ya está siendo utilizado por otra persona
                        if(id==null){
                            Log.i("Retrofit","El usuario ya exixte");
                            Toast.makeText(this, "El usuario ya existe 🤣 ", Toast.LENGTH_LONG).show();
                            stopProgressDialog();
                        }else{
                            Log.i("Retrofit", "El usuario no existe: ID: "+id+" Puedo Seguir al segundo Post");
                            //Ultimo post
                            user_restaurant_register(id);
                        }

                    }else{
                        //Hubo un fallo de que ya existe
                        Log.i("Retrofit","El username está repetido");
                    }

                }
                break;
            case R.id.btn_user_birth_day:
                new DatePickerDialog(Register2Activity.this, R.style.DialogTheme, date, C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void user_restaurant_register(String id) {
        Log.i("Retrofit", "Entré al user_restaurant_register wer service con el siguiente id:" + id);
        User_restaurant_register user_restaurant_register = new User_restaurant_register(id, kind_of_id, user_id, user_name, user_last_name, user_gender, phone, user_birthday, user_email, user_address);
        Call<User_restaurant_register> call = RetrofitClient.getInstance().getApi().user_restaurant_register(user_restaurant_register);
        call.enqueue(new Callback<User_restaurant_register>() {
            @Override
            public void onResponse(Call<User_restaurant_register> call, Response<User_restaurant_register> response) {
                try {
                    if (response.body() != null) {
                        Log.i("Retrofit", "Los campos del formulario se encuentran bien.");
                        User_restaurant_register obj = response.body();
                        user_client_register(obj.getDocument());
                    } else {
                        String s = response.errorBody().string();
                        //Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                        message_from_server = s;

                        stopProgressDialog();
                        Log.i("Retrofit", "Errores del Segundo consumo: " + s);
                        SimpleDialog simpleDialog = new SimpleDialog();
                        //Por medio de este set, le estoy pasando informacion al Dialog
                        simpleDialog.setMensaje_from_server(s);
                        FragmentManager fm = getSupportFragmentManager();
                        simpleDialog.show(fm,"LoginDialog");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void user_client_register(String document) {
                User_client_register user_client_register = new User_client_register(document);
                Call<User_client_register> call = RetrofitClient.getInstance().getApi().user_client_register(user_client_register);
                call.enqueue(new Callback<User_client_register>() {
                    @Override
                    public void onResponse(Call<User_client_register> call, Response<User_client_register> response) {
                        try {
                            if (response.body() != null) {
                                User_client_register obj = response.body();
                                Log.i("Retrofit", "Finalicé el tercer consumo");
                                stopProgressDialog();
                                cleanSharedPreferencesFile();
                                updateFormFromSharedPreference();
                                //Se debe realizar el llamado al login
                                //Llamo a login para que solucione, retorne un nuevo id
                                Login_request login_request = new Login_request(user_app,password_one);
                                Call<Login_response> login = RetrofitClient.getInstance().getApi().loginFull(login_request);
                                login.enqueue(new Callback<Login_response>() {

                                    @Override
                                    public void onResponse(Call<Login_response> call, Response<Login_response> response) {
                                        String s = "";
                                        if(response.body()!=null){
                                            Login_response login_response = response.body();
                                            //crear el archivo shared
                                            editorLogin.putString(LOGIN_TOKEN,login_response.getToken());
                                            editorLogin.putString(LOGIN_DOCUMENT,login_response.getDocument());
                                            editorLogin.commit();
                                            startActivity(new Intent(Register2Activity.this, MainActivity.class));
                                            finish();

                                        }
                                        else{
                                            try {
                                                s =response.errorBody().string();
                                                Log.i("Retrofit", "Uso del api de Login: "+s);
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
                                        Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();

                                    }
                                });


                            } else {
                                String s = response.errorBody().string();
                                //Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                                et_user_address.setText(s);


                                Log.i("Retrofit", "Tercer consumo: " + s);


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<User_client_register> call, Throwable t) {
                        stopProgressDialog();
                        Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();

                    }
                 });
            }

            @Override
            public void onFailure(Call<User_restaurant_register> call, Throwable t) {
                stopProgressDialog();
                Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void cleanSharedPreferencesFile() {
        Log.i("Retrofit", "ya está limpio el archivoShared");
        editor.clear();
        editor.commit();
    }

    private void goodMethod() {
        Call<ResponseBody> call = RetrofitClient.getInstance().
                getApi().
                singUpUserRegisterTwo(user_app, "", user_password, user_password);
        //Next, I am going to execute the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {
                        String s = response.body().string();
                        Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                        Log.i("Retrofit", s);
                    } else {
                        String s = response.errorBody().string();
                        Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();

                        Log.i("Retrofit", s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopProgressDialog();
                t.getMessage();
                Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void callWebServiceUserRegister() {

        Create_user_request create_user_request = new Create_user_request(user_app, "", user_password, user_password);
        Call<Create_user_response> call = RetrofitClient.getInstance().getApi().singUpUserRegister(create_user_request);
        call.enqueue(new Callback<Create_user_response>() {
            @Override
            public void onResponse(Call<Create_user_response> call, Response<Create_user_response> response) {
                /*
                if(!response.isSuccessful()){
                    Log.i("Retrofit", "code: "+response.code());
                    try {
                        Log.i("Retrofit",  response.errorBody().string());
                        Toast.makeText(Register2Activity.this, ""+response.errorBody().string(), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //return;
                }else {
                    //Toast.makeText(Register2Activity.this, "Well Done!", Toast.LENGTH_SHORT).show();
                    create_user_response = response.body();
                    //Toast.makeText(Register2Activity.this, "code: "+response.code() + "\nid: "+create_user_response.getId()+"\nAnswer:"+create_user_response.getAnswer(), Toast.LENGTH_SHORT).show();
//                Log.i("Retrofit", "code: "+response.code() + "\nid: "+create_user_response[0].getId()+"\nAnswer:"+create_user_response[0].getAnswer());
                }

                */

                try {
                    if (response.body() != null) {
                        create_user_response = response.body();
                        //Toast.makeText(Register2Activity.this, create_user_response.getId(), Toast.LENGTH_SHORT).show();
                        Log.i("Retrofit", create_user_response.getAnswer());
                        user_restaurant_register(create_user_response.getId());

                    } else {
                        String s = response.errorBody().string();
                        //Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                        et_user_address.setText(s);
                        Log.i("Retrofit", s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void user_restaurant_register(String id) {
                Toast.makeText(Register2Activity.this, "Aqui tengo su id: " + id, Toast.LENGTH_SHORT).show();
                Log.i("Retrofit", "Aqui tengo su id: " + id);
                User_restaurant_register user_restaurant_register = new User_restaurant_register(id, kind_of_id, user_id, user_name, user_last_name, user_gender, phone, user_birthday, user_email, user_address);
                Call<User_restaurant_register> call = RetrofitClient.getInstance().getApi().user_restaurant_register(user_restaurant_register);
                call.enqueue(new Callback<User_restaurant_register>() {
                    @Override
                    public void onResponse(Call<User_restaurant_register> call, Response<User_restaurant_register> response) {
                        try {
                            if (response.body() != null) {
                                User_restaurant_register obj = response.body();
                                //Toast.makeText(Register2Activity.this, create_user_response.getId(), Toast.LENGTH_SHORT).show();
                                Log.i("Retrofit", obj.getFirst_name());

                                Log.i("Retrofit", "Segundo Consumo");
                                user_client_register(obj.getDocument());
                            } else {
                                String s = response.errorBody().string();
                                //Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                                et_user_address.setText(s);
                                Log.i("Retrofit", "Segundo consumo: " + s);


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    private void user_client_register(String document) {
                        User_client_register user_client_register = new User_client_register(document);
                        Call<User_client_register> call = RetrofitClient.getInstance().getApi().user_client_register(user_client_register);
                        call.enqueue(new Callback<User_client_register>() {
                            @Override
                            public void onResponse(Call<User_client_register> call, Response<User_client_register> response) {
                                try {
                                    if (response.body() != null) {
                                        User_client_register obj = response.body();
                                        //Toast.makeText(Register2Activity.this, create_user_response.getId(), Toast.LENGTH_SHORT).show();
                                        Log.i("Retrofit", obj.getId_user_restaurant());

                                        Log.i("Retrofit", "Tercer consumo");
                                        stopProgressDialog();
                                        cleanForms();


                                    } else {
                                        String s = response.errorBody().string();
                                        //Toast.makeText(Register2Activity.this, s, Toast.LENGTH_SHORT).show();
                                        et_user_address.setText(s);
                                        Log.i("Retrofit", "Tercer consumo: " + s);


                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<User_client_register> call, Throwable t) {
                                stopProgressDialog();
                                Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User_restaurant_register> call, Throwable t) {
                        stopProgressDialog();
                        Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<Create_user_response> call, Throwable t) {
                stopProgressDialog();
                Toast.makeText(Register2Activity.this, "(⊙_⊙;)\nTenemos un fallo: No hay conexión a internet o el servidor no responde.\n ¯"+'\\'+"_(ツ)_/¯", Toast.LENGTH_LONG).show();
            }


        });


    }

    private boolean isTheFormComplete() {
        boolean isTheFormComplete = true;
       getValuesFromInputForm();

        if (user_birthday.isEmpty()) {
            btn_birth_day.setError(getResources().getString(R.string.msg_required_failed));
            btn_birth_day.requestFocus();
            isTheFormComplete = false;
        }
        if (user_email.isEmpty()) {
            et_user_email.setError(getResources().getString(R.string.msg_required_failed));
            et_user_email.requestFocus();
            isTheFormComplete = false;
        }
        if (user_address.isEmpty()) {
            et_user_address.setError(getResources().getString(R.string.msg_required_failed));
            et_user_address.requestFocus();
            isTheFormComplete = false;
        }

        if (user_app.isEmpty()) {
            et_user_app.setError(getResources().getString(R.string.msg_required_failed));
            et_user_app.requestFocus();
            isTheFormComplete = false;
        }

        if (password_one.isEmpty()) {
            et_user_password1.setError(getResources().getString(R.string.msg_required_failed));
            et_user_password1.requestFocus();
            isTheFormComplete = false;
        }
        if (password_two.isEmpty()) {
            et_user_password2.setError(getResources().getString(R.string.msg_required_failed));
            et_user_password2.requestFocus();
            isTheFormComplete = false;
        }
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(user_email);

        if (!mather.find() && !user_email.isEmpty()) {
            et_user_email.setError(getResources().getString(R.string.msg_email_failed));
            et_user_email.requestFocus();
            isTheFormComplete = false;
        }
        if (!password_one.isEmpty() && !password_two.isEmpty()) {
            if (!password_one.equals(password_two)) {
                et_user_password2.setError(getResources().getString(R.string.msg_password_failed));
                et_user_password2.requestFocus();
                et_user_password2.setText("");
                isTheFormComplete = false;
            } else {
                user_password = et_user_password1.getText().toString().trim();
            }

        }
        return isTheFormComplete;
    }


    private class WebServiceUserRegister extends AsyncTask<Void, Void, Create_user_response>{

        @Override
        protected Create_user_response doInBackground(Void... voids) {
            Create_user_request create_user_request = new Create_user_request(user_app, "", user_password, user_password);
            Call<Create_user_response> call = RetrofitClient.getInstance().getApi().singUpUserRegister(create_user_request);
            try {

                return call.execute().body();
            }catch (IOException e){
                Log.i("Error",e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(Create_user_response create_user_response) {
            if(create_user_response!=null) {
                    Log.i("Retrofit", create_user_response.toString());
                    information += create_user_response.toString()+"\n\n";

                //textView.setText(information);
            }else{
                Log.i("Retrofit", "El api user_register falló");
            }

        }
    }

}

