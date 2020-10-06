package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.unicauca.domifoods.interfaces.Listener;


public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_NAME = "user_name";
    public static final String USER_LASTNAME = "user_last_name";
    public static final String USER_KIND_ID = "user_kind_id";
    public static final String USER_ID = "user_id";
    public static final String USER_PHONE = "user_phone";
    public static final String MyPREFERENCES = "formPreferences";


    private Button btn_next_register;
    private EditText et_user_name, et_user_lastname, et_id_user, et_phone_user;
    private Spinner sp_kind_of_id_user;
    private String user_name, user_last_name, user_id, phone, kind_of_id;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        setUpSharedPreferences();
        /*In the next method, I make the initialization of variables*/
        initializationVariables();

    }

    private void setUpSharedPreferences() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }


    private void initializationVariables() {
        et_user_name = findViewById(R.id.et_user_name);
        et_user_lastname = findViewById(R.id.et_user_lastname);
        et_id_user = findViewById(R.id.et_user_id);
        et_phone_user = findViewById(R.id.et_user_phone);
        sp_kind_of_id_user = findViewById(R.id.sp_kind_of_id);
        btn_next_register = findViewById(R.id.btn_next_register);
        btn_next_register.setOnClickListener(this);
    }

    private void updateFormFromSharedPreference(){
        Log.i("msg_lino","updateFormFromSharedPreference");
        et_user_name.setText(sharedpreferences.getString(USER_NAME,""));
        et_user_lastname.setText(sharedpreferences.getString(USER_LASTNAME,""));
        String kind_of_id = sharedpreferences.getString(USER_KIND_ID,"C.C");
        if(kind_of_id.equals("C.C")){
            sp_kind_of_id_user.setSelection(0);
        }else{
            sp_kind_of_id_user.setSelection(1);
        }
        et_id_user.setText(sharedpreferences.getString(USER_ID,""));
        et_phone_user.setText(sharedpreferences.getString(USER_PHONE,""));


    }
    @Override
    protected void onStart() {
        super.onStart();
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
            case R.id.btn_next_register:
                //In the next method, I'll validate that the form is filled out
                if (isTheFormComplete()==true) {
                    Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();
                    bundle.putString(USER_NAME, user_name);
                    bundle.putString(USER_LASTNAME, user_last_name);
                    bundle.putString(USER_KIND_ID, kind_of_id);
                    bundle.putString(USER_ID, user_id);
                    bundle.putString(USER_PHONE, phone);
                    intent.putExtras(bundle);
                    saveFormIntoSharedPreferences();
                    startActivity(intent);
                }
                break;

        }
    }

    private void saveFormIntoSharedPreferences() {
        getValuesFromInputForm();
        editor.putString(USER_NAME, user_name);
        editor.putString(USER_LASTNAME, user_last_name);
        editor.putString(USER_KIND_ID, kind_of_id);
        editor.putString(USER_ID, user_id);
        editor.putString(USER_PHONE, phone);
        editor.commit();
        Log.i("msg_lino","saveFormIntoSharedPreferences");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("msg_lino","Onbackpress");
        saveFormIntoSharedPreferences();
    }

    private boolean isTheFormComplete() {
        boolean isTheFormComplete = true;
        getValuesFromInputForm();

        if (user_name.isEmpty()) {
            et_user_name.setError(getResources().getString(R.string.msg_required_failed));
            et_user_name.requestFocus();
            isTheFormComplete = false;
        }
        if (user_last_name.isEmpty()) {
            et_user_lastname.setError(getResources().getString(R.string.msg_required_failed));
            et_user_lastname.requestFocus();
            isTheFormComplete = false;
        }
        if (user_id.isEmpty()) {
            et_id_user.setError(getResources().getString(R.string.msg_required_failed));
            et_id_user.requestFocus();
            isTheFormComplete = false;
        }
        if (phone.isEmpty()) {
            et_phone_user.setError(getResources().getString(R.string.msg_required_failed));
            et_phone_user.requestFocus();
            isTheFormComplete = false;
        }

        return isTheFormComplete;
    }

    private void getValuesFromInputForm() {
        user_name = et_user_name.getText().toString().trim();
        user_last_name = et_user_lastname.getText().toString().trim();
        user_id = et_id_user.getText().toString().trim();
        phone = et_phone_user.getText().toString().trim();
        kind_of_id = sp_kind_of_id_user.getSelectedItem().toString().trim();
    }
}