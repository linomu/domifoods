package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener {


    private String user_name, user_last_name, user_id, phone, kind_of_id;
    private String user_birthday, user_email, user_address, user_gender, user_password;
    
    private Button btn_birth_day, btn_register;
    private EditText et_user_email, et_user_address;
    private RadioGroup radioGroup_gender;
    private RadioButton et_user_gender;

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
/*
        new DatePickerDialog(Register2Activity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //DO SOMETHING
            }
        }, 2015, 02, 26).show();

*/

    }

    private void initializationVariables() {
        et_user_email = findViewById(R.id.et_user_email);
        et_user_address = findViewById(R.id.et_user_address);
        radioGroup_gender = findViewById(R.id.radio_gender);
        btn_birth_day = findViewById(R.id.btn_user_birth_day);
        btn_birth_day.setOnClickListener(this);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
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
        String formatoDeFecha = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        btn_birth_day.setText(sdf.format(C.getTime()));
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
            case  R.id.btn_next_register:
                if (isTheFormComplete()==false) {

                }
                break;
            case R.id.btn_user_birth_day:
                new DatePickerDialog(Register2Activity.this, R.style.DialogTheme, date, C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private boolean isTheFormComplete() {
        boolean isTheFormComplete = true;
        user_email = et_user_email.getText().toString().trim();
        user_birthday = btn_birth_day.getText().toString().trim();
        
        return isTheFormComplete;
    }
}