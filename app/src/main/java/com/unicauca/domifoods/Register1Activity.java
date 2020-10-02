package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_NAME = "user_name";
    public static final String USER_LASTNAME = "user_last_name";
    public static final String USER_KIND_ID = "user_kind_id";
    public static final String USER_ID = "user_id";
    public static final String USER_PHONE = "user_phone";

    private Button btn_next_register;
    private EditText et_user_name, et_user_lastname, et_id_user, et_phone_user;
    private Spinner sp_kind_of_id_user;
    private String user_name, user_last_name, user_id, phone, kind_of_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        /*In the next method, I make the initialization of variables*/
        initializationVariables();
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
        switch (view.getId()) {
            case R.id.btn_next_register:
                //In the next method, I'll validate that the form is filled out
                if (isTheFormComplete()==false) {
                    Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();
                    bundle.putString(USER_NAME, user_name);
                    bundle.putString(USER_LASTNAME, user_last_name);
                    bundle.putString(USER_KIND_ID, kind_of_id);
                    bundle.putString(USER_ID, user_id);
                    bundle.putString(USER_PHONE, phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;

        }
    }

    private boolean isTheFormComplete() {
        boolean isTheFormComplete = true;
        user_name = et_user_name.getText().toString().trim();
        user_last_name = et_user_lastname.getText().toString().trim();
        user_id = et_id_user.getText().toString().trim();
        phone = et_phone_user.getText().toString().trim();
        kind_of_id = sp_kind_of_id_user.getSelectedItem().toString().trim();

        if(user_name.isEmpty()){
            et_user_name.setError(getResources().getString(R.string.msg_required_filed));
            et_user_name.requestFocus();
            isTheFormComplete = false;
        }
        if(user_last_name.isEmpty()){
            et_user_lastname.setError(getResources().getString(R.string.msg_required_filed));
            et_user_lastname.requestFocus();
            isTheFormComplete = false;
        }
        if(user_id.isEmpty()){
            et_id_user.setError(getResources().getString(R.string.msg_required_filed));
            et_id_user.requestFocus();
            isTheFormComplete = false;
        }
        if(phone.isEmpty()){
            et_phone_user.setError(getResources().getString(R.string.msg_required_filed));
            et_phone_user.requestFocus();
            isTheFormComplete = false;
        }

     return isTheFormComplete;
    }
}