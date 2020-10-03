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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener {


    private String user_name, user_last_name, user_id, phone, kind_of_id;
    private String user_birthday, user_email, user_address, user_gender, user_password;
    
    private Button btn_birth_day, btn_register;
    private EditText et_user_email, et_user_address, et_user_password1, et_user_password2;
    private RadioButton radio_user_gender_male;
    private RadioButton radio_user_gender_female;

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
        btn_birth_day = findViewById(R.id.btn_user_birth_day);
        btn_birth_day.setOnClickListener(this);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        radio_user_gender_female = findViewById(R.id.radio_female);
        radio_user_gender_male = findViewById(R.id.radio_male);
        et_user_password1 = findViewById(R.id.et_user_password1);
        et_user_password2 = findViewById(R.id.et_user_password2);
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
        String formatoDeFecha = "dd-MM-yyyy"; //In which you need put here
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
            case  R.id.btn_register:
                if (isTheFormComplete()) {
                    Toast.makeText(this, "Listo!", Toast.LENGTH_SHORT).show();
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
        user_address = et_user_address.getText().toString().trim();
        if(radio_user_gender_female.isChecked()){
            user_gender = "F";
        }else{
            user_gender = "M";
        }
        String password_one, password_two;
        password_one = et_user_password1.getText().toString().trim();
        password_two = et_user_password2.getText().toString().trim();

        if(user_birthday.isEmpty()){
            btn_birth_day.setError(getResources().getString(R.string.msg_required_failed));
            btn_birth_day.requestFocus();
            isTheFormComplete = false;
        }
        if(user_email.isEmpty()){
            et_user_email.setError(getResources().getString(R.string.msg_required_failed));
            et_user_email.requestFocus();
            isTheFormComplete = false;
        }
        if(user_address.isEmpty()){
            et_user_address.setError(getResources().getString(R.string.msg_required_failed));
            et_user_address.requestFocus();
            isTheFormComplete = false;
        }
        if(password_one.isEmpty()){
            et_user_password1.setError(getResources().getString(R.string.msg_required_failed));
            et_user_password1.requestFocus();
            isTheFormComplete = false;
        }
        if(password_two.isEmpty()){
            et_user_password2.setError(getResources().getString(R.string.msg_required_failed));
            et_user_password2.requestFocus();
            isTheFormComplete = false;
        }
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(user_email);

        if (!mather.find() && !user_email.isEmpty()) {
            et_user_email.setError(getResources().getString(R.string.msg_email_failed));
            et_user_email.requestFocus();
            isTheFormComplete = false;
        }
        if(!password_one.isEmpty() && !password_two.isEmpty()){
            if(!password_one.equals(password_two)){
                et_user_password2.setError(getResources().getString(R.string.msg_password_failed));
                et_user_password2.requestFocus();
                et_user_password2.setText("");
                isTheFormComplete = false;
            }
            else{
                user_password = et_user_password1.getText().toString().trim();
            }

        }
        return isTheFormComplete;
    }
}