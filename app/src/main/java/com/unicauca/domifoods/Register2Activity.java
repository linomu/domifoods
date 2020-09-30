package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register2Activity extends AppCompatActivity {

    EditText etPlannedDate;
    Calendar C = Calendar.getInstance();
    //private int nYarIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayUni;


    //= (EditText) view.findViewById(R.id.etPlannedDate);
    //etPlannedDate.setOnClickListener(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        etPlannedDate = findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register2Activity.this, date, C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
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

        etPlannedDate.setText(sdf.format(C.getTime()));
    }

}